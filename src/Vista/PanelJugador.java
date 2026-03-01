package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Avatar y Estadísticas del Jugador.
 * Representa la "tarjeta de identidad" de cada jugador en la cancha.
 * Incluye lógica de resaltado visual para indicar quién tiene el turno de lanzamiento.
 * * @author Juan
 * @version 3.0
 */
public class PanelJugador extends JPanel {

    private final JLabel iconoAvatar;
    private final JLabel etiquetaNombre;
    private final JLabel etiquetaPuntos;
    private final JLabel etiquetaIntentos;
    
    // Colores de diseño para la "Gama Alta"
    private final Color COLOR_FONDO_NORMAL = Color.WHITE;
    private final Color COLOR_FONDO_ACTIVO = new Color(225, 235, 245); // Azul grisáceo elegante
    private final Color COLOR_BORDE_NORMAL = new Color(230, 230, 230);
    private final Color COLOR_BORDE_ACTIVO = new Color(52, 152, 219);  // Azul vibrante

    /**
     * Construye la tarjeta visual del jugador con un diseño limpio y moderno.
     * @param nombre Inicial del jugador (será actualizada por el controlador).
     */
    public PanelJugador(String nombre) {
        // Configuración de la "tarjeta"
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));
        setBackground(COLOR_FONDO_NORMAL);

        // 1. EL AVATAR (Literal h: Diseño atractivo)
        iconoAvatar = new JLabel("👤", SwingConstants.CENTER);
        iconoAvatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
        iconoAvatar.setOpaque(false);

        // 2. EL NOMBRE (Destacado en la parte superior)
        etiquetaNombre = new JLabel(nombre.toUpperCase(), SwingConstants.CENTER);
        etiquetaNombre.setFont(new Font("SansSerif", Font.BOLD, 12));
        etiquetaNombre.setForeground(new Color(44, 62, 80));

        // 3. LAS ESTADÍSTICAS (Puntos e Intentos)
        JPanel contenedorStats = new JPanel(new GridLayout(2, 1, 2, 2));
        contenedorStats.setOpaque(false);
        
        etiquetaPuntos = new JLabel("Puntos: 0", SwingConstants.CENTER);
        etiquetaPuntos.setFont(new Font("Monospaced", Font.BOLD, 11));
        
        etiquetaIntentos = new JLabel("Intentos: 0", SwingConstants.CENTER);
        etiquetaIntentos.setFont(new Font("Monospaced", Font.PLAIN, 10));

        contenedorStats.add(etiquetaPuntos);
        contenedorStats.add(etiquetaIntentos);

        // Ensamblaje de la pieza
        add(etiquetaNombre, BorderLayout.NORTH);
        add(iconoAvatar, BorderLayout.CENTER);
        add(contenedorStats, BorderLayout.SOUTH);
    }

    /**
     * Cambia el aspecto visual del panel si el jugador es el que está lanzando.
     * REQUISITO: Poner más oscura/resaltada la casilla del jugador activo.
     * @param esElActivo true si el jugador tiene el turno actual.
     */
    public void setResaltado(boolean esElActivo) {
        if (esElActivo) {
            setBackground(COLOR_FONDO_ACTIVO);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDE_ACTIVO, 2),
                    new EmptyBorder(7, 7, 7, 7) // Ajuste para compensar el borde más grueso
            ));
            iconoAvatar.setText("🎯"); // Cambia el avatar por una diana al apuntar
        } else {
            setBackground(COLOR_FONDO_NORMAL);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 1),
                    new EmptyBorder(8, 8, 8, 8)
            ));
            iconoAvatar.setText("👤");
        }
        revalidate();
        repaint();
    }

    /**
     * Actualiza los contadores de rendimiento del jugador en tiempo real.
     */
    public void actualizarDatos(int puntos, int intentos) {
        etiquetaPuntos.setText("Puntos: " + puntos);
        etiquetaIntentos.setText("Lanzados: " + intentos);
    }

    /**
     * Cambia el nombre mostrado en la tarjeta.
     */
    public void actualizarNombre(String nombre) {
        this.etiquetaNombre.setText(nombre.trim().toUpperCase());
    }
}