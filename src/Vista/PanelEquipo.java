package Vista;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Panel visual de un equipo.
 * Contiene 3 jugadores y permite aplicar transparencia.
 *
 * Código de transparencia basado en:
 * Oracle Java 2D API - AlphaComposite.
 */
public class PanelEquipo extends JPanel {

    private float alpha = 1f;

    public PanelEquipo(String nombre) {
        this.setLayout(new GridLayout(3, 1, 5, 5));
        this.setBorder(BorderFactory.createTitledBorder(nombre));

        for (int i = 1; i <= 3; i++) {
            this.add(new PanelJugador("Jugador " + i));
        }
    }

    public void setTransparencia(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2);
        g2.dispose();
    }
}