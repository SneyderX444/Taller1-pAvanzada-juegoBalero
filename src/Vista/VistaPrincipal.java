package Vista;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {
    private CardLayout navegador;
    private JPanel contenedorPrincipal;
    private PanelJuego panelJuego;
    private JButton btnCargarConfig; // Botón en la pantalla de bienvenida

    public VistaPrincipal() {
        setTitle("Taller 1: El Juego del Balero - Programación Avanzada");
        setSize(1100, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        navegador = new CardLayout();
        contenedorPrincipal = new JPanel(navegador);

        // Crear Pantallas
        JPanel pnlBienvenida = crearPanelBienvenida();
        panelJuego = new PanelJuego();

        contenedorPrincipal.add(pnlBienvenida, "MENU");
        contenedorPrincipal.add(panelJuego, "JUEGO");

        add(contenedorPrincipal);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelBienvenida() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(new Color(240, 248, 255));
        
        JLabel titulo = new JLabel("BIENVENIDOS AL BALERO");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        
        btnCargarConfig = new JButton("Cargar Archivo de Configuración (.properties)");
        btnCargarConfig.setMargin(new Insets(10, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(20,20,20,20);
        pnl.add(titulo, gbc);
        gbc.gridy = 1;
        pnl.add(btnCargarConfig, gbc);
        
        return pnl;
    }
    
    // Agrega este método para compatibilidad con tu controlador
public void mostrarPanel(String nombre) {
    // Si tu controlador envía "Juego", lo pasamos a "JUEGO" que es la constante del CardLayout
    if(nombre.equalsIgnoreCase("Juego")) {
        cambiarPantalla("JUEGO");
    } else {
        cambiarPantalla(nombre.toUpperCase());
    }
}

    
    public void cambiarPantalla(String nombre) { navegador.show(contenedorPrincipal, nombre); }
    public PanelJuego getPanelJuego() { return panelJuego; }
    public JButton getBtnCargar() { return btnCargarConfig; }
    
    public JButton getBtnIniciar() {
    return btnCargarConfig; // Usamos el mismo botón que dispara la carga e inicio
    }
}
