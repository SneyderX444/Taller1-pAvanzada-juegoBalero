package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo encargado de la persistencia de los resultados del juego.
 * Guarda y consulta información en el archivo equipos_jugadores.persistence
 *
 * Formato de almacenamiento:
 * NombreEquipo;Puntaje
 *
 * @author Juan
 * @version 1.0
 */
public class Resultados {

    private static final String ARCHIVO = "equipos_jugadores.persistence";

    /**
     * Guarda un resultado en el archivo.
     */
    public void guardarResultado(String nombreEquipo, int puntaje) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(nombreEquipo + ";" + puntaje);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar resultado");
        }
    }

    /**
     * Cuenta cuántas veces ha ganado un equipo.
     */
    public int contarVictorias(String nombreEquipo) {
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos[0].equalsIgnoreCase(nombreEquipo)) {
                    contador++;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer resultados");
        }

        return contador;
    }

    /**
     * Retorna el historial completo.
     */
    public List<String> leerResultados() {
        List<String> historial = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                historial.add(linea);
            }

        } catch (IOException e) {
            System.out.println("Error al leer historial");
        }

        return historial;
    }
}
