package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Contenedor de un equipo que gestiona el efecto de difuminado (Literal g).
 */
public class PanelEquipo extends JPanel {
    private final PanelJugador[] jugadores;
    private float alpha = 1.0f; // 1.0f = Activo, 0.4f = Difuminado

    public PanelEquipo(String nombreEquipo, String[] nombres) {
        setLayout(new GridLayout(1, 3, 10, 0));
        setOpaque(false); // Necesario para que el AlphaComposite funcione correctamente
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), nombreEquipo));

        jugadores = new PanelJugador[3];
        for (int i = 0; i < 3; i++) {
            jugadores[i] = new PanelJugador(nombres[i]);
            add(jugadores[i]);
        }
    }

    /**
     * Define si el equipo está activo o difuminado.
     * @param activo true para opacidad total, false para transparencia.
     */
    public void setEstadoVisual(boolean activo) {
        this.alpha = activo ? 1.0f : 0.4f;
        repaint();
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Aplicación del requisito de transparencia/difuminado
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintChildren(g2);
        g2.dispose();
    }

    public PanelJugador getPanelJugador(int i) { return jugadores[i]; }
    public void setTransparencia(float alpha) {
    this.alpha = alpha;
    repaint();
}

public void setNombreEquipo(String nombre) {
    setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), nombre));
}
    
}
