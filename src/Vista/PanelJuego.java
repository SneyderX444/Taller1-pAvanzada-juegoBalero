package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Panel principal de la interfaz de juego.
 * Organiza la grilla de equipos, el cronómetro de turno y los controles de acción.
 * * Cumple con el patrón MVC al exponer componentes mediante getters para el controlador.
 * * @author Juan
 * @version 2.0
 */
public class PanelJuego extends JPanel {
    private final PanelEquipo[] equiposVisuales;
    private final JButton btnLanzar;
    private final JLabel lblMensaje;
    private final JLabel lblTiempo; // Requisito para el cronómetro

    public PanelJuego() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Grilla de Equipos (Centro) ---
        JPanel centro = new JPanel(new GridLayout(3, 1, 10, 10));
        equiposVisuales = new PanelEquipo[3];
        for (int i = 0; i < 3; i++) {
            equiposVisuales[i] = new PanelEquipo("Equipo " + (i + 1), 
                                 new String[]{"-", "-", "-"});
            centro.add(equiposVisuales[i]);
        }

        // --- Panel de Control e Información (Sur) ---
        JPanel sur = new JPanel(new BorderLayout(5, 5));
        
        lblMensaje = new JLabel("¡Carga los equipos para empezar!", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));

        // Cronómetro visual (Requisito Literal g)
        lblTiempo = new JLabel("Tiempo: 00", SwingConstants.CENTER);
        lblTiempo.setFont(new Font("Monospaced", Font.BOLD, 22));
        lblTiempo.setForeground(new Color(180, 0, 0));

        btnLanzar = new JButton("¡LANZAR BALERO!");
        btnLanzar.setPreferredSize(new Dimension(200, 60));
        btnLanzar.setFont(new Font("Arial", Font.BOLD, 20));
        btnLanzar.setBackground(new Color(34, 139, 34));
        btnLanzar.setForeground(Color.WHITE);
        btnLanzar.setFocusPainted(false);

        // Agrupación de etiquetas
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.add(lblMensaje);
        info.add(lblTiempo);

        sur.add(info, BorderLayout.NORTH);
        sur.add(btnLanzar, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);
    }

    /**
     * Aplica el efecto de difuminado o resaltado a los equipos.
     * @param indice El índice del equipo que debe estar opaco (activo).
     */
    public void resaltarEquipo(int indice) {
        for (int i = 0; i < equiposVisuales.length; i++) {
            // 1.0f = Opaco (Activo), 0.3f = Difuminado (Inactivo)
            equiposVisuales[i].setTransparencia(i == indice ? 1.0f : 0.3f);
        }
    }

    // --- Métodos de acceso para el Controlador (MVC) ---

    /** @return El botón que dispara la lógica de lanzamiento. */
    public JButton getBtnLanzar() { return btnLanzar; }

    /** @return La etiqueta de tiempo para que el Timer la actualice. */
    public JLabel getLblTiempo() { return lblTiempo; }

    /** @return El panel de un equipo específico para actualizar nombres o puntos. */
    public PanelEquipo getEquipo(int i) { return equiposVisuales[i]; }

    /** @param texto Mensaje de estado para el usuario. */
    public void setMensaje(String texto) { lblMensaje.setText(texto); }
}
