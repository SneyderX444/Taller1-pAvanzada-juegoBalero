package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Tablero principal de juego. 
 * Organiza los equipos y los controles de acción (Literal h).
 */
public class PanelJuego extends JPanel {
    private final PanelEquipo[] equiposVisuales;
    private final JButton btnLanzar;
    private final JLabel lblMensaje;
    private final JLabel lblTiempo;

    public PanelJuego() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Grilla Central de Equipos
        JPanel pnlEquipos = new JPanel(new GridLayout(3, 1, 15, 15));
        equiposVisuales = new PanelEquipo[3];
        for (int i = 0; i < 3; i++) {
            equiposVisuales[i] = new PanelEquipo("Equipo " + (i + 1), new String[]{"-", "-", "-"});
            pnlEquipos.add(equiposVisuales[i]);
        }

        // Panel de Control (Sur)
        JPanel pnlControl = new JPanel(new BorderLayout(10, 10));
        
        lblMensaje = new JLabel("Esperando inicio...", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("SansSerif", Font.ITALIC, 18));

        lblTiempo = new JLabel("00", SwingConstants.CENTER);
        lblTiempo.setFont(new Font("Monospaced", Font.BOLD, 40));
        lblTiempo.setForeground(new Color(200, 0, 0));

        btnLanzar = new JButton("LANZAR BALERO");
        btnLanzar.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnLanzar.setBackground(new Color(40, 167, 69));
        btnLanzar.setForeground(Color.WHITE);
        btnLanzar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlControl.add(lblMensaje, BorderLayout.NORTH);
        pnlControl.add(lblTiempo, BorderLayout.WEST);
        pnlControl.add(btnLanzar, BorderLayout.CENTER);

        add(pnlEquipos, BorderLayout.CENTER);
        add(pnlControl, BorderLayout.SOUTH);
    }

    public void resaltarEquipoActual(int indice) {
        for (int i = 0; i < equiposVisuales.length; i++) {
            equiposVisuales[i].setEstadoVisual(i == indice);
        }
    }

    // Getters para el controlador (MVC)
    public JButton getBtnLanzar() { return btnLanzar; }
    public JLabel getLblTiempo() { return lblTiempo; }
    public void setMensaje(String m) { lblMensaje.setText(m); }
    public PanelEquipo getEquipo(int i) { return equiposVisuales[i]; }
}