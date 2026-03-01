package Modelo;

import java.io.*;

/**
 * Persistencia histórica usando RandomAccessFile (RAF).
 * Ubicación: Specs/data/historico.dat (Requisito literal i).
 * @author Juan
 */
public class ResultadosRAF {
    private final String RUTA = "Specs/data/historico.dat";
    private final int TAM_NOMBRE = 40; // Tamaño fijo en bytes para el nombre

    public void guardarGanador(String nombreEquipo, int puntos) {
        File dir = new File("Specs/data");
        if (!dir.exists()) dir.mkdirs();

        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "rw")) {
            raf.seek(raf.length());
            // Escribir nombre ajustado a 20 caracteres (UTF usa 2 bytes por char + 2 de control)
            String nombreAjustado = String.format("%-20s", nombreEquipo);
            raf.writeUTF(nombreAjustado);
            raf.writeInt(puntos);
        } catch (IOException e) {
            // No usar System.out ni JOptionPane aquí (Requisito literal i)
        }
    }

    public int obtenerVictoriasAnteriores(String nombreEquipo) {
        int conteo = 0;
        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                String nombre = raf.readUTF().trim();
                raf.readInt();
                if (nombre.equalsIgnoreCase(nombreEquipo)) conteo++;
            }
        } catch (IOException e) { }
        return conteo;
    }
}