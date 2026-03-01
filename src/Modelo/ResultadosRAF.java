package Modelo;

import java.io.*;

/**
 * Clase encargada de la persistencia histórica de resultados.
 * Utiliza un archivo de acceso aleatorio (RandomAccessFile) para almacenar 
 * y consultar los ganadores de cada torneo de forma persistente.
 * * Responsabilidades:
 * - Registrar cada victoria al finalizar un juego.
 * - Contabilizar cuántas veces un equipo específico ha ganado en el pasado.
 * * @author Juan
 * @version 2.5
 */
public class ResultadosRAF {
    
    /** Ruta del archivo binario donde se guardan los records. */
    private static final String RUTA = "Specs/data/historico.dat";
    
    /** Tamaño fijo para el nombre del equipo en caracteres (alineado con format). */
    private static final int LONGITUD_NOMBRE = 20;

    /**
     * Guarda el resultado de un torneo en el archivo histórico.
     * Crea los directorios necesarios si no existen.
     * * @param nombreEquipo El nombre del equipo ganador.
     * @param puntos El puntaje total obtenido por el equipo.
     */
    public void guardarResultado(String nombreEquipo, int puntos) {
        // Asegurar que la ruta de carpetas existe
        File carpetas = new File("Specs/data");
        if (!carpetas.exists()) {
            carpetas.mkdirs();
        }

        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "rw")) {
            // Mover el puntero al final del archivo para añadir el nuevo registro
            raf.seek(raf.length());
            
            // Ajustar el nombre a una longitud fija de 20 caracteres para lectura consistente
            String nombreAjustado = String.format("%-" + LONGITUD_NOMBRE + "s", 
                (nombreEquipo.length() > LONGITUD_NOMBRE) ? 
                nombreEquipo.substring(0, LONGITUD_NOMBRE) : nombreEquipo);
            
            raf.writeUTF(nombreAjustado);
            raf.writeInt(puntos);
            
        } catch (IOException e) {
            // Se mantiene el silencio en consola por requerimiento del Literal i
        }
    }

    /**
     * Recorre el archivo histórico para contar cuántas veces ha ganado un equipo.
     * * @param nombreEquipo El nombre del equipo a buscar.
     * @return El número de victorias previas encontradas.
     */
    public int obtenerVictoriasAnteriores(String nombreEquipo) {
        int victorias = 0;
        File archivo = new File(RUTA);
        
        if (!archivo.exists()) {
            return 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                // Leer el nombre (UTF incluye 2 bytes de longitud al inicio)
                String nombreLeido = raf.readUTF().trim();
                // Leer el entero de puntos (avanza el puntero 4 bytes)
                raf.readInt(); 
                
                if (nombreLeido.equalsIgnoreCase(nombreEquipo.trim())) {
                    victorias++;
                }
            }
        } catch (IOException e) {
            // Silencio en consola según lineamientos
        }
        
        return victorias;
    }
}