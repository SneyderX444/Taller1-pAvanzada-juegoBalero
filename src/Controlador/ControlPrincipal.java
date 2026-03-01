package Controlador;

import Modelo.*;
import Vista.PanelJugador;
import Vista.VistaPrincipal;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Orquestador Principal del Sistema.
 * Único responsable de coordinar la Vista con los diferentes controladores de lógica.
 * Aplica el principio de Mediador para evitar que los modelos conozcan la vista.
 * * @author Juan
 * @version 2.2
 */
public class ControlPrincipal {

    private final VistaPrincipal vista;
    private final GestionConfiguracion gestionConfig;
    private final ControlEquipo controlEquipo;
    private final ControlJuego controlJuego;
    
    private Timer cronometro;
    private int tiempoRestante;

    public ControlPrincipal() {
        this.vista = new VistaPrincipal();
        this.gestionConfig = new GestionConfiguracion();
        this.controlEquipo = new ControlEquipo();
        this.controlJuego = new ControlJuego();

        asignarEventos();
        cargarIntegrantesAlInicio();
        this.vista.setVisible(true);
    }

    private void asignarEventos() {
        vista.getBtnCargar().addActionListener(e -> menuCargarArchivo());
        vista.getBtnIniciar().addActionListener(e -> menuIniciarPartida());
        vista.getPanelJuego().getBtnLanzar().addActionListener(e -> procesoLanzamiento());
    }

    private void menuCargarArchivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Configuración (.properties)", "properties"));

        if (chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            try {
                List<Equipo> cargados = gestionConfig.leerArchivoConfiguracion(chooser.getSelectedFile().getAbsolutePath());
                controlEquipo.setEquipos(cargados);
                sincronizarNombresVista(cargados);
                actualizarFocoVisual(-1); // Estado difuminado inicial
                JOptionPane.showMessageDialog(vista, "Configuración cargada con éxito.");
            } catch (IOException ex) {
                mostrarError("Error al procesar archivo: " + ex.getMessage());
            }
        }
    }

    private void menuIniciarPartida() {
        if (controlEquipo.getEquipos().isEmpty()) {
            mostrarError("Primero debe cargar un archivo de equipos.");
            return;
        }
        try {
            int t = Integer.parseInt(vista.getTextFieldTiempo().getText());
            controlJuego.iniciarJuego(controlEquipo.getEquipos(), t);
            vista.mostrarPanel("Juego");
            prepararTurno();
        } catch (NumberFormatException ex) {
            mostrarError("El tiempo debe ser un valor numérico.");
        }
    }

    private void procesoLanzamiento() {
        TipoEmbocada result = controlJuego.ejecutarLanzamiento();
        
        int eIdx = controlJuego.getIndiceEquipoActual();
        int jIdx = controlJuego.getIndiceJugadorActual();
        Jugador j = controlJuego.getJugadorActivo();

        // Actualización directa al componente visual del jugador actual
        PanelJugador pnl = vista.getPanelJuego().getEquipo(eIdx).getPanelJugador(jIdx);
        pnl.actualizarDatos(j.getPuntos(), j.getIntentos());
        
        vista.getPanelJuego().setMensaje("Resultado: " + result + " (+" + result.getPuntos() + " pts)");
    }

    private void prepararTurno() {
        int eIdx = controlJuego.getIndiceEquipoActual();
        Jugador j = controlJuego.getJugadorActivo();

        actualizarFocoVisual(eIdx);
        vista.getPanelJuego().setMensaje("Turno actual: " + j.getNombre());
        gestionarCronometro(controlJuego.getTiempoPorJugador());
    }

    private void gestionarCronometro(int seg) {
        if (cronometro != null) cronometro.stop();
        this.tiempoRestante = seg;
        
        cronometro = new Timer(1000, e -> {
            tiempoRestante--;
            vista.getPanelJuego().getLblTiempo().setText("Tiempo: " + tiempoRestante + "s");
            if (tiempoRestante <= 0) {
                cronometro.stop();
                avanzarTurno();
            }
        });
        cronometro.start();
    }

    private void avanzarTurno() {
        controlJuego.pasarAlSiguienteTurno();
        if (controlJuego.esFinDelJuego()) {
            cronometro.stop();
            JOptionPane.showMessageDialog(vista, "¡Juego Terminado!");
        } else {
            prepararTurno();
        }
    }

    private void actualizarFocoVisual(int activo) {
        for (int i = 0; i < 3; i++) {
            vista.getPanelJuego().getEquipo(i).setTransparencia(i == activo ? 1.0f : 0.3f);
        }
    }

    private void sincronizarNombresVista(List<Equipo> lista) {
        for (int i = 0; i < Math.min(lista.size(), 3); i++) {
            Equipo eq = lista.get(i);
            vista.getPanelJuego().getEquipo(i).setNombreEquipo(eq.getNombre());
            for (int j = 0; j < 3; j++) {
                vista.getPanelJuego().getEquipo(i).getPanelJugador(j)
                     .actualizarNombre(eq.getJugadores().get(j).getNombre());
            }
        }
    }

    private void cargarIntegrantesAlInicio() {
        File f = new File("src/Docs/Integrantes/Integrantes.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder("EQUIPO DE DESARROLLO:\n");
            String s;
            while ((s = br.readLine()) != null) sb.append("- ").append(s).append("\n");
            JOptionPane.showMessageDialog(vista, sb.toString(), "Créditos", 1);
        } catch (IOException e) { /* Falla silenciosa según Literal i */ }
    }

    private void mostrarError(String m) {
        JOptionPane.showMessageDialog(vista, m, "Error de Aplicación", 0);
    }
}
