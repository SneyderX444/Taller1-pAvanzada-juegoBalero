package Aplicacion;

import Vista.Vista;
import Controlador.ControlEquipo;

/**
 * Clase principal que inicia la aplicación.
 */
public class Launcher {

    /**
     * Método principal del programa.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        // Crear controlador principal (por ahora usamos ControlEquipo)
        ControlEquipo control = new ControlEquipo();

        // Crear vista y pasarle el controlador
        Vista vista = new Vista(control);

        // Iniciar la interfaz
        vista.iniciar();

    }
}