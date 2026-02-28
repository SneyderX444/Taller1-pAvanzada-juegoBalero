package Vista;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel que representa la pantalla principal del juego.
 *
 * Responsabilidades:
 * - Mostrar los equipos y sus jugadores en formato de grilla
 * - Mostrar información del turno actual
 * - Permitir la acción de lanzar el balero
 *
 * No contiene lógica del juego (MVC).
 *
 * Estructura visual:
 * ┌──────────────────────────────┐
 * │ Panel Equipos (Grid 1x3)     │
 * ├──────────────────────────────┤
 * │ Información del turno        │
 * │ Botón Lanzar                 │
 * └──────────────────────────────┘
 *
 * @author Juan
 * @version 1.0
 */
public class PanelJuego extends JPanel {

    // Panel donde se muestran los equipos
    private JPanel panelEquipos;

    // Panel inferior de control
    private JLabel lblEquipoTurno;
    private JLabel lblJugadorTurno;
    private JLabel lblTiempo;
    private JLabel lblIntentos;
    private JLabel lblPuntos;
    private JButton btnLanzar;

    // Arreglo para manejar los equipos visualmente
    private PanelEquipo[] equipos;

    /**
     * Constructor del panel de juego.
     */
    public PanelJuego() {
        inicializarComponentes();
        crearGrillaEquipos();
    }

    /**
     * Inicializa los componentes principales.
     */
    private void inicializarComponentes() {
        this.setLayout(new java.awt.BorderLayout());
        this.setBackground(new java.awt.Color(204, 255, 204));

        // Panel de equipos
        panelEquipos = new JPanel();
        panelEquipos.setLayout(new GridLayout(1, 3, 10, 10));
        panelEquipos.setBorder(BorderFactory.createTitledBorder("Equipos"));

        // Panel de control inferior
        JPanel panelControl = new JPanel();
        panelControl.setBorder(BorderFactory.createTitledBorder("Turno Actual"));

        lblEquipoTurno = new JLabel("Equipo: -");
        lblJugadorTurno = new JLabel("Jugador: -");
        lblTiempo = new JLabel("Tiempo: 0");
        lblIntentos = new JLabel("Intentos: 0");
        lblPuntos = new JLabel("Puntos: 0");

        btnLanzar = new JButton("Lanzar Balero");

        panelControl.add(lblEquipoTurno);
        panelControl.add(lblJugadorTurno);
        panelControl.add(lblTiempo);
        panelControl.add(lblIntentos);
        panelControl.add(lblPuntos);
        panelControl.add(btnLanzar);

        this.add(panelEquipos, java.awt.BorderLayout.CENTER);
        this.add(panelControl, java.awt.BorderLayout.SOUTH);
    }

    /**
     * Crea la grilla visual de los equipos (3 equipos).
     */
    private void crearGrillaEquipos() {
        equipos = new PanelEquipo[3];

        for (int i = 0; i < 3; i++) {
            equipos[i] = new PanelEquipo("Equipo " + (i + 1));
            panelEquipos.add(equipos[i]);
        }
    }

    /**
     * Resalta el equipo activo y difumina los demás.
     * @param indice índice del equipo activo (0–2)
     */
    public void mostrarEquipoActivo(int indice) {
        for (int i = 0; i < equipos.length; i++) {
            if (i == indice) {
                equipos[i].setTransparencia(1f);
            } else {
                equipos[i].setTransparencia(0.3f);
            }
        }
    }

    /**
     * Actualiza la información del turno en pantalla.
     */
    public void actualizarTurno(String equipo, String jugador, int tiempo, int intentos, int puntos) {
        lblEquipoTurno.setText("Equipo: " + equipo);
        lblJugadorTurno.setText("Jugador: " + jugador);
        lblTiempo.setText("Tiempo: " + tiempo);
        lblIntentos.setText("Intentos: " + intentos);
        lblPuntos.setText("Puntos: " + puntos);
    }

    /**
     * Devuelve el botón lanzar para que el controlador agregue el listener.
     */
    public JButton getBtnLanzar() {
        return btnLanzar;
    }

    /**
     * Permite acceder a los paneles de equipo.
     */
    public PanelEquipo[] getEquipos() {
        return equipos;
    }
}
