package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel {
    private PanelEquipo[] equiposVisuales;
    private JButton btnLanzar;
    private JLabel lblInfoTurno;

    public PanelJuego() {
        setLayout(new BorderLayout(20, 20));
        
        // Grilla de Equipos (3 filas, 1 columna o viceversa)
        JPanel centro = new JPanel(new GridLayout(3, 1, 10, 10));
        equiposVisuales = new PanelEquipo[3];
        
        // Inicialización dummy (se llenará con datos del .properties)
        for (int i = 0; i < 3; i++) {
            equiposVisuales[i] = new PanelEquipo("Equipo " + (i+1), 
                                 new String[]{"Jug 1", "Jug 2", "Jug 3"});
            centro.add(equiposVisuales[i]);
        }

        // Panel de Control inferior
        JPanel sur = new JPanel(new FlowLayout());
        lblInfoTurno = new JLabel("Esperando inicio...");
        btnLanzar = new JButton("¡LANZAR BALERO!");
        btnLanzar.setFont(new Font("Arial", Font.BOLD, 18));
        
        sur.add(lblInfoTurno);
        sur.add(btnLanzar);

        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);
    }

    public void resaltarEquipo(int indice) {
        for (int i = 0; i < equiposVisuales.length; i++) {
            equiposVisuales[i].setActivo(i == indice);
        }
    }

    public JButton getBtnLanzar() { return btnLanzar; }
}
