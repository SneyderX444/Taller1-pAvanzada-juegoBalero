package Modelo;

import java.io.*;

public class ResultadosRAF {
    private static final String RUTA = "Specs/data/historico.dat";

    public void guardarResultado(String nombreEquipo, int puntos) {
        new File("Specs/data").mkdirs();
        try (RandomAccessFile raf = new RandomAccessFile(RUTA, "rw")) {
            raf.seek(raf.length());
            // Escribir nombre con longitud fija de 20 caracteres
            String nombreAjustado = String.format("%-20s", (nombreEquipo.length() > 20) ? 
                                   nombreEquipo.substring(0, 20) : nombreEquipo);
            raf.writeUTF(nombreAjustado);
            raf.writeInt(puntos);
        } catch (IOException e) {
            // Silencio en consola según requerimiento literal i
        }
    }
}