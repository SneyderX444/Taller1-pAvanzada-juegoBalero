package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana raíz que gestiona el flujo de pantallas.
 */
public class VistaPrincipal extends JFrame {
    private CardLayout navegador;
    private JPanel contenedor;
    private PanelJuego panelJuego;
    private JButton btnCargar;
    private JTextField txtTiempo;

    public VistaPrincipal() {
        configurarFrame();
        inicializarComponentes();
    }

    private void configurarFrame() {
        setTitle("Torneo de Balero - Sistema MVC");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        navegador = new CardLayout();
        contenedor = new JPanel(navegador);

        panelJuego = new PanelJuego();
        contenedor.add(crearPanelBienvenida(), "MENU");
        contenedor.add(panelJuego, "JUEGO");

        add(contenedor);
    }

    private JPanel crearPanelBienvenida() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(new Color(233, 236, 239));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("CAMPEONATO DE BALERO");
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        gbc.gridy = 0; pnl.add(titulo, gbc);

        txtTiempo = new JTextField("60", 5);
        JPanel pnlInput = new JPanel();
        pnlInput.add(new JLabel("Tiempo por turno (seg):"));
        pnlInput.add(txtTiempo);
        gbc.gridy = 1; pnl.add(pnlInput, gbc);

        btnCargar = new JButton("Cargar Equipos e Iniciar");
        btnCargar.setPreferredSize(new Dimension(250, 50));
        gbc.gridy = 2; pnl.add(btnCargar, gbc);

        return pnl;
    }

    public void mostrarPanel(String nombre) {
        navegador.show(contenedor, nombre);
    }

    // Getters para el Controlador
    public PanelJuego getPanelJuego() { return panelJuego; }
    public JButton getBtnCargar() { return btnCargar; }
    public JTextField getTxtTiempo() { return txtTiempo; }
    public JButton getBtnIniciar() {
    return btnCargar; // Si usas el mismo botón para cargar e iniciar
}
    public JTextField getTextFieldTiempo() {
    return txtTiempo;
}
}
