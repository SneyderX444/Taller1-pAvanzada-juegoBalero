package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Representación visual de un jugador individual.
 * Cumple con el lineamiento de diseño atractivo (Literal h).
 */
public class PanelJugador extends JPanel {
    private final JLabel lblFoto;
    private final JLabel lblNombre;
    private final JLabel lblPuntos;
    private final JLabel lblIntentos;

    public PanelJugador(String nombre) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        setBackground(Color.WHITE);

        // Representación visual atractiva (Emoji como placeholder de foto)
        lblFoto = new JLabel("👤", SwingConstants.CENTER);
        lblFoto.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblFoto.setOpaque(true);
        lblFoto.setBackground(new Color(248, 249, 250));

        lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel pnlStats = new JPanel(new GridLayout(2, 1));
        pnlStats.setOpaque(false);
        lblPuntos = new JLabel("Pts: 0", SwingConstants.CENTER);
        lblIntentos = new JLabel("Int: 0", SwingConstants.CENTER);
        pnlStats.add(lblPuntos);
        pnlStats.add(lblIntentos);

        add(lblNombre, BorderLayout.NORTH);
        add(lblFoto, BorderLayout.CENTER);
        add(pnlStats, BorderLayout.SOUTH);
    }

    public void actualizarDatos(int puntos, int intentos) {
        lblPuntos.setText("Pts: " + puntos);
        lblIntentos.setText("Int: " + intentos);
    }

    public void setNombre(String nombre) {
        lblNombre.setText(nombre);
    }
    
    public void actualizarNombre(String nombre) {
    this.lblNombre.setText(nombre);
}
}