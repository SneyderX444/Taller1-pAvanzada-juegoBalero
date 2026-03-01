package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Representación visual de un jugador individual.
 * Incluye nombre, foto y estadísticas.
 */
public class PanelJugador extends JPanel {
    private JLabel lblFoto;
    private JLabel lblNombre;
    private JLabel lblPuntos;
    private JLabel lblIntentos;

    public PanelJugador(String nombre) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        setBackground(Color.WHITE);

        // Icono o foto (Requisito de diseño atractivo)
        lblFoto = new JLabel("👤", SwingConstants.CENTER);
        lblFoto.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        lblFoto.setOpaque(true);
        lblFoto.setBackground(new Color(245, 245, 245));

        lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));

        // Panel para datos numéricos
        JPanel panelDatos = new JPanel(new GridLayout(2, 1));
        panelDatos.setOpaque(false);
        lblPuntos = new JLabel("Pts: 0", SwingConstants.CENTER);
        lblIntentos = new JLabel("Int: 0", SwingConstants.CENTER);
        panelDatos.add(lblPuntos);
        panelDatos.add(lblIntentos);

        add(lblNombre, BorderLayout.NORTH);
        add(lblFoto, BorderLayout.CENTER);
        add(panelDatos, BorderLayout.SOUTH);
    }

    public void actualizarDatos(int puntos, int intentos) {
        lblPuntos.setText("Pts: " + puntos);
        lblIntentos.setText("Int: " + intentos);
    }
}