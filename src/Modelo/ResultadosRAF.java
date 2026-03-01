package Modelo;

import java.io.*;

/**
 * Gestor de Memoria Histórica del Torneo.
 * Esta clase actúa como el "libro de récords" del juego, utilizando un archivo 
 * de acceso aleatorio (RAF) para guardar quiénes han sido los campeones.
 * * Se cumple con el requisito de persistencia binaria y acceso aleatorio.
 * * @author Juan
 * @version 3.0
 */
public class ResultadosRAF {
    
    // Ruta solicitada para la persistencia dentro del proyecto
    private static final String RUTA_ARCHIVO = "src/Specs/Data/historico.dat";
    
    /**
     * Registra la victoria de un equipo con todos sus detalles.
     * Escribe en formato binario: Clave, Nombre Equipo, 3 Jugadores y Puntaje.
     * * @param equipoGanador El objeto equipo que acaba de ganar el torneo.
     */
    public void guardarResultado(Equipo equipoGanador) {
        // Aseguramos que la carpeta exista antes de intentar crear el archivo
        verificarOcrearDirectorios();

        try (RandomAccessFile archivoCajaRegistradora = new RandomAccessFile(RUTA_ARCHIVO, "rw")) {
            // Saltamos al final para no borrar lo anterior (append)
            archivoCajaRegistradora.seek(archivoCajaRegistradora.length());
            
            // 1. Escribir Clave Única (Hash basado en el nombre)
            String claveUnica = "ID-" + equipoGanador.getNombre().hashCode();
            archivoCajaRegistradora.writeUTF(ajustarTexto(claveUnica, 10));
            
            // 2. Escribir Nombre del Equipo
            archivoCajaRegistradora.writeUTF(ajustarTexto(equipoGanador.getNombre(), 20));
            
            // 3. Escribir Nombres de los 3 Jugadores (Requisito de estructura)
            for (int i = 0; i < 3; i++) {
                String nombreJugador = equipoGanador.getJugadores().get(i).getNombre();
                archivoCajaRegistradora.writeUTF(ajustarTexto(nombreJugador, 15));
            }
            
            // 4. Escribir Puntaje Final (4 bytes)
            archivoCajaRegistradora.writeInt(equipoGanador.getPuntajeTotal());
            
        } catch (IOException e) {
            // Silencio administrativo: No ensuciamos la consola según Literal i
        }
    }

    /**
     * Busca en el historial cuántas veces ha triunfado un equipo específico.
     * * @param nombreABuscar El nombre del equipo que queremos consultar.
     * @return Conteo total de victorias encontradas en el archivo binario.
     */
    public int obtenerVictoriasAnteriores(String nombreABuscar) {
        int conteoVictorias = 0;
        File file = new File(RUTA_ARCHIVO);
        
        if (!file.exists()) return 0;

        // "r" significa modo lectura (read only)
        try (RandomAccessFile lectorLineal = new RandomAccessFile(RUTA_ARCHIVO, "r")) {
            while (lectorLineal.getFilePointer() < lectorLineal.length()) {
                
                // LEER EN EL MISMO ORDEN QUE SE ESCRIBIÓ (Vital para no desfasar el puntero)
                lectorLineal.readUTF(); // Leemos y descartamos la Clave
                String equipoLeido = lectorLineal.readUTF().trim(); // Leemos el Equipo
                lectorLineal.readUTF(); // Descartamos Jugador 1
                lectorLineal.readUTF(); // Descartamos Jugador 2
                lectorLineal.readUTF(); // Descartamos Jugador 3
                lectorLineal.readInt(); // Descartamos el Int de puntos
                
                if (equipoLeido.equalsIgnoreCase(nombreABuscar.trim())) {
                    conteoVictorias++;
                }
            }
        } catch (IOException e) {
            // Manejo silencioso de errores de lectura
        }
        
        return conteoVictorias;
    }

    /**
     * Utilidad para garantizar que los textos tengan un tamaño uniforme en el archivo.
     */
    private String ajustarTexto(String texto, int ancho) {
        return String.format("%-" + ancho + "s", 
            (texto.length() > ancho) ? texto.substring(0, ancho) : texto);
    }

    /**
     * Crea la ruta de carpetas si el usuario no las tiene creadas.
     */
    private void verificarOcrearDirectorios() {
        File directorio = new File("src/Specs/Data");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }
}