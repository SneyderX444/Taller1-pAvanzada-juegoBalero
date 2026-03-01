package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema que gestiona la navegación entre pantallas.
 * Utiliza CardLayout para alternar entre el menú de bienvenida y el área de juego.
 * * @author Juan
 * @version 2.0
 */
public class VistaPrincipal extends JFrame {
    
    private CardLayout navegador;
    private JPanel contenedorPrincipal;
    private PanelJuego panelJuego;
    
    // Componentes de la pantalla de bienvenida
    private JButton btnCargarConfig;
    private JTextField txtTiempo; // Campo para capturar el tiempo de juego

    public VistaPrincipal() {
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Configuración básica de la ventana (título, tamaño, cierre).
     */
    private void configurarVentana() {
        setTitle("Taller 1: El Juego del Balero - Programación Avanzada");
        setSize(1100, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa el contenedor principal con CardLayout y añade las pantallas.
     */
    private void inicializarComponentes() {
        navegador = new CardLayout();
        contenedorPrincipal = new JPanel(navegador);

        // Crear las dos vistas principales
        JPanel pnlBienvenida = crearPanelBienvenida();
        panelJuego = new PanelJuego();

        // Registrar las vistas en el navegador
        contenedorPrincipal.add(pnlBienvenida, "MENU");
        contenedorPrincipal.add(panelJuego, "JUEGO");

        add(contenedorPrincipal);
    }

    /**
     * Construye el panel de bienvenida con los controles de configuración.
     * @return JPanel con el diseño de bienvenida.
     */
    private JPanel crearPanelBienvenida() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(new Color(200, 228, 215)); // Color pastel suave
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // 1. Título
        JLabel titulo = new JLabel("BIENVENIDOS AL BALERO");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        gbc.gridy = 0;
        pnl.add(titulo, gbc);

        // 2. Panel para capturar el tiempo
        JPanel pnlTiempo = new JPanel();
        pnlTiempo.setOpaque(false);
        pnlTiempo.add(new JLabel("Tiempo de juego (seg): "));
        txtTiempo = new JTextField("60", 5); // Valor por defecto 60 seg
        pnlTiempo.add(txtTiempo);
        
        gbc.gridy = 1;
        pnl.add(pnlTiempo, gbc);

        // 3. Botón de carga e inicio
        btnCargarConfig = new JButton("Cargar Configuración e Iniciar Juego");
        btnCargarConfig.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarConfig.setMargin(new Insets(10, 20, 10, 20));
        
        gbc.gridy = 2;
        pnl.add(btnCargarConfig, gbc);
        
        return pnl;
    }

    /**
     * Cambia la pantalla visible actualmente.
     * @param nombre Nombre identificador de la pantalla ("MENU" o "JUEGO").
     */
    public void mostrarPanel(String nombre) {
        navegador.show(contenedorPrincipal, nombre.toUpperCase());
    }

    // --- Getters para el Controlador (MVC) ---

    public PanelJuego getPanelJuego() { 
        return panelJuego; 
    }

    public JButton getBtnCargar() { 
        return btnCargarConfig; 
    }

    public JButton getBtnIniciar() {
        return btnCargarConfig; 
    }

    public JTextField getTextFieldTiempo() {
        return txtTiempo;
    }
}
