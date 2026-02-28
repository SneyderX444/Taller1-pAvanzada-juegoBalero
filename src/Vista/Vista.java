package Vista;

import Controlador.ControlEquipo;
import java.util.Scanner;

/**
 * Clase Vista encargada de la interacción con el usuario.
 * No contiene lógica del negocio.
 */
public class Vista {

    private ControlEquipo control;
    private Scanner scanner;

    /**
     * Constructor de la vista.
     * @param control controlador que conecta con el modelo
     */
    public Vista(ControlEquipo control) {
        this.control = control;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Método que inicia la interfaz del programa.
     */
    public void iniciar() {

        System.out.println("=================================");
        System.out.println("   JUEGO DEL BALERO - INICIO");
        System.out.println("=================================");

        // Por ahora solo prueba básica
        System.out.println("Sistema iniciado correctamente.");

        // Más adelante aquí irá el menú
    }
}