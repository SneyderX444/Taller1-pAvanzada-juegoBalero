package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel {
    private PanelEquipo[] equiposVisuales;
    private JButton btnLanzar;
    private JLabel lblMensaje;

    public PanelJuego() {
        setLayout(new BorderLayout(15, 15));
        
        // Grilla central: 3 filas para los 3 equipos
        JPanel centro = new JPanel(new GridLayout(3, 1, 10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        equiposVisuales = new PanelEquipo[3];
        // Inicialización temporal, el controlador pondrá los nombres reales
        for (int i = 0; i < 3; i++) {
            equiposVisuales[i] = new PanelEquipo("Equipo " + (i+1), 
                                 new String[]{"Jugador A", "Jugador B", "Jugador C"});
            centro.add(equiposVisuales[i]);
        }

        // Panel inferior de control
        JPanel sur = new JPanel(new BorderLayout());
        lblMensaje = new JLabel("¡Carga los equipos para empezar!", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
        
        btnLanzar = new JButton("¡LANZAR BALERO!");
        btnLanzar.setPreferredSize(new Dimension(200, 50));
        btnLanzar.setFont(new Font("Arial", Font.BOLD, 20));
        btnLanzar.setBackground(new Color(34, 139, 34));
        btnLanzar.setForeground(Color.WHITE);

        sur.add(lblMensaje, BorderLayout.NORTH);
        sur.add(btnLanzar, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);
    }

    public void resaltarEquipo(int indice) {
        for (int i = 0; i < equiposVisuales.length; i++) {
            equiposVisuales[i].setActivo(i == indice);
        }
    }

    public void setMensaje(String texto) { lblMensaje.setText(texto); }
    public JButton getBtnLanzar() { return btnLanzar; }
    public PanelEquipo getEquipo(int i) { return equiposVisuales[i]; }
}
