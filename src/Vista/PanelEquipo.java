package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelEquipo extends JPanel {
    private float alpha = 1.0f; // 1.0 = opaco, 0.3 = transparente
    private PanelJugador[] jugadores;

    public PanelEquipo(String nombreEquipo, String[] nombres) {
        setLayout(new GridLayout(1, 3, 10, 0));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), nombreEquipo));
        setOpaque(false);

        jugadores = new PanelJugador[3];
        for (int i = 0; i < 3; i++) {
            jugadores[i] = new PanelJugador(nombres[i]);
            add(jugadores[i]);
        }
    }

    // Método clave para el Requisito de Difuminado
    public void setActivo(boolean activo) {
        this.alpha = activo ? 1.0f : 0.4f;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2);
        g2.dispose();
    }

    public PanelJugador getPanelJugador(int i) { return jugadores[i]; }
    
    // Dentro de PanelEquipo.java
public void setTransparencia(float alpha) {
    this.alpha = alpha;
    this.repaint(); // Obligatorio para que Java vuelva a pintar el panel con el nuevo brillo
}

@Override
protected void paintComponent(java.awt.Graphics g) {
    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
    g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
    super.paintComponent(g2);
    g2.dispose();
}

}