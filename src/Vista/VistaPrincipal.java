package Vista;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {
    private CardLayout navegador;
    private JPanel contenedor;
    private PanelJuego panelJuego;

    public VistaPrincipal() {
        setTitle("El Balero Tradicional - Programación Avanzada");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        navegador = new CardLayout();
        contenedor = new JPanel(navegador);

        panelJuego = new PanelJuego();
        
        // Agregamos las pantallas
        contenedor.add(new JPanel(), "BIENVENIDA"); // Puedes crear un PanelBienvenida
        contenedor.add(panelJuego, "JUEGO");

        add(contenedor);
        setLocationRelativeTo(null);
    }

    public void mostrarPantalla(String nombre) {
        navegador.show(contenedor, nombre);
    }

    public PanelJuego getPanelJuego() { return panelJuego; }
}
