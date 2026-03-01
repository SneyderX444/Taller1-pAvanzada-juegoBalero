package Controlador;

import Modelo.*;
import Vista.PanelEquipo;
import Vista.PanelJugador;
import Vista.VistaPrincipal;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controlador Principal del Sistema (Mediador).
 * Coordina la comunicación entre la Vista y el Modelo, gestionando el flujo
 * del torneo, la persistencia de resultados y los requisitos de interfaz.
 * * @author Juan
 * @version 3.0
 */
public class ControlPrincipal {

    private final VistaPrincipal vista;
    private final GestionConfiguracion gestionConfig;
    private final ResultadosRAF historicoDAO;
    private Juego juego;
    private Timer cronometro;
    private int tiempoRestante;

    /**
     * Inicializa los componentes base y muestra los créditos iniciales.
     */
    public ControlPrincipal() {
        this.vista = new VistaPrincipal();
        this.gestionConfig = new GestionConfiguracion();
        this.historicoDAO = new ResultadosRAF();

        asignarEventos();
        cargarIntegrantesAlInicio();
        this.vista.setVisible(true);
    }

    /**
     * Vincula las acciones de los botones de la vista con los métodos del controlador.
     */
    private void asignarEventos() {
        vista.getBtnCargar().addActionListener(e -> menuCargarEIniciar());
        vista.getPanelJuego().getBtnLanzar().addActionListener(e -> procesoLanzamiento());
    }

    /**
     * Gestiona la carga del archivo .properties y la transición a la pantalla de juego.
     */
    private void menuCargarEIniciar() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Configuración (.properties)", "properties"));

        if (chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            try {
                List<Equipo> cargados = gestionConfig.leerArchivoConfiguracion(chooser.getSelectedFile().getAbsolutePath());
                
                if (cargados.isEmpty()) {
                    mostrarError("El archivo no contiene equipos válidos.");
                    return;
                }

                int tiempo = Integer.parseInt(vista.getTextFieldTiempo().getText());
                this.juego = new Juego(cargados, tiempo);

                actualizarInterfazNombres();
                vista.mostrarPanel("JUEGO"); 
                prepararTurno();

                JOptionPane.showMessageDialog(vista, "¡Torneo Iniciado!");
            } catch (NumberFormatException nfe) {
                mostrarError("El tiempo debe ser un valor numérico.");
            } catch (Exception ex) {
                mostrarError("Error al iniciar: " + ex.getMessage());
            }
        }
    }

    /**
     * Sincroniza los nombres de equipos y jugadores desde el modelo a la vista.
     */
    private void actualizarInterfazNombres() {
        List<Equipo> eqs = juego.getEquipos();
        for (int i = 0; i < eqs.size(); i++) {
            Equipo eq = eqs.get(i);
            PanelEquipo pnlEq = vista.getPanelJuego().getEquipo(i);
            pnlEq.setNombreEquipo(eq.getNombre());
            
            for (int j = 0; j < eq.getJugadores().size(); j++) {
                String nombreJug = eq.getJugadores().get(j).getNombre();
                pnlEq.getPanelJugador(j).actualizarNombre(nombreJug);
            }
        }
    }

    /**
     * Ejecuta la lógica de lanzamiento, actualiza puntos y muestra el resultado.
     */
    private void procesoLanzamiento() {
        if (juego == null) return;

        TipoEmbocada result = juego.lanzarBalero();
        juego.registrarResultado(result);
        
        int eIdx = juego.getIndiceEquipoActual();
        int jIdx = juego.getIndiceJugadorActual();
        Jugador j = juego.getJugadorActual();

        PanelJugador pnl = vista.getPanelJuego().getEquipo(eIdx).getPanelJugador(jIdx);
        pnl.actualizarDatos(j.getPuntos(), j.getIntentos());
        
        vista.getPanelJuego().setMensaje("Resultado: " + result.getDescripcion() + " (+" + result.getPuntos() + " pts)");
    }

    /**
     * Configura el estado visual para el nuevo turno y reinicia el cronómetro.
     */
    private void prepararTurno() {
        int eIdx = juego.getIndiceEquipoActual();
        Jugador j = juego.getJugadorActual();

        actualizarFocoVisual(eIdx);
        vista.getPanelJuego().setMensaje("Turno actual: " + j.getNombre());
        gestionarCronometro(juego.getTiempoPorJugador());
    }

    /**
     * Controla el tiempo de cada turno. Al agotarse, avanza automáticamente al siguiente jugador.
     * @param seg Segundos de duración del turno.
     */
    private void gestionarCronometro(int seg) {
        if (cronometro != null) cronometro.stop();
        this.tiempoRestante = seg;
        
        cronometro = new Timer(1000, e -> {
            tiempoRestante--;
            vista.getPanelJuego().getLblTiempo().setText(tiempoRestante + "s");
            if (tiempoRestante <= 0) {
                cronometro.stop();
                avanzarTurno();
            }
        });
        cronometro.start();
    }

    /**
     * Cambia el turno. Si no hay más jugadores, finaliza el juego y procesa al ganador.
     */
    private void avanzarTurno() {
        juego.siguienteJugador();
        if (juego.juegoTerminado()) {
            cronometro.stop();
            procesarFinalizacion();
        } else {
            prepararTurno();
        }
    }

    /**
     * Calcula el ganador, consulta el histórico en el archivo RAF y muestra los resultados finales.
     * Requisito: Mostrar cuántas veces ha ganado el equipo anteriormente.
     */
    private void procesarFinalizacion() {
        List<Equipo> lista = juego.getEquipos();
        Equipo ganador = lista.get(0);
        
        // Determinar ganador
        for (Equipo eq : lista) {
            eq.calcularPuntajeTotal();
            if (eq.getPuntajeTotal() > ganador.getPuntajeTotal()) {
                ganador = eq;
            }
        }

        // Consultar y guardar en RAF (Acceso Aleatorio)
        int victoriasPrevias = historicoDAO.obtenerVictoriasAnteriores(ganador.getNombre());
        historicoDAO.guardarResultado(ganador.getNombre(), ganador.getPuntajeTotal());

        // Construcción del mensaje final
        StringBuilder sb = new StringBuilder();
        sb.append("🏆 TORNEO FINALIZADO 🏆\n\n");
        sb.append("Ganador: ").append(ganador.getNombre()).append("\n");
        sb.append("Puntaje: ").append(ganador.getPuntajeTotal()).append(" pts\n");
        sb.append("---------------------------------\n");
        
        if (victoriasPrevias > 0) {
            sb.append("⭐ ¡Este equipo ha ganado ").append(victoriasPrevias)
              .append(victoriasPrevias == 1 ? " vez" : " veces").append(" anteriormente!");
        } else {
            sb.append("🆕 ¡Esta es la primera victoria del equipo!");
        }

        JOptionPane.showMessageDialog(vista, sb.toString(), "Resultados Históricos", 1);
        vista.mostrarPanel("MENU");
    }

    /**
     * Aplica el efecto de difuminado (Literal g) resaltando solo al equipo activo.
     */
    private void actualizarFocoVisual(int activo) {
        for (int i = 0; i < 3; i++) {
            vista.getPanelJuego().getEquipo(i).setTransparencia(i == activo ? 1.0f : 0.3f);
        }
    }

    /**
     * Carga y muestra los créditos desde el archivo de texto al iniciar la app.
     */
    private void cargarIntegrantesAlInicio() {
        File f = new File("src/Docs/Integrantes/Integrantes.txt");
        if(!f.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder("EQUIPO DE DESARROLLO:\n");
            String s;
            while ((s = br.readLine()) != null) sb.append("- ").append(s).append("\n");
            JOptionPane.showMessageDialog(vista, sb.toString(), "Créditos", 1);
        } catch (IOException e) { /* Falla silenciosa por requerimiento */ }
    }

    private void mostrarError(String m) {
        JOptionPane.showMessageDialog(vista, m, "Error", 0);
    }
}