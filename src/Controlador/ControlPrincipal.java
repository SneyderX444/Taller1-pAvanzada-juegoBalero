package Controlador;

import Modelo.*;
import Vista.PanelEquipo;
import Vista.PanelJugador;
import Vista.VistaPrincipal;
import java.io.*;
import java.awt.Color;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * El Director de Orquesta (Controlador Principal).
 * Su misión es hacer que la Vista y el Modelo hablen el mismo idioma sin tocarse.
 * Gestiona el cronómetro, la lógica de turnos y la persistencia histórica.
 * * @author Juan
 * @version 3.5 (Edición Final)
 */
public class ControlPrincipal {

    private final VistaPrincipal vista;
    private final GestionConfiguracion gestionConfig;
    private final ResultadosRAF historicoDAO;
    
    private Juego juego;
    private Timer cronometro;
    private int tiempoRestante;

    /**
     * Prepara el escenario: inicializa herramientas y muestra quién hizo el programa.
     */
    public ControlPrincipal() {
        this.vista = new VistaPrincipal();
        this.gestionConfig = new GestionConfiguracion();
        this.historicoDAO = new ResultadosRAF();

        configurarInteracciones();
        saludarYPresentarIntegrantes();
        this.vista.setVisible(true);
    }

    /**
     * Conecta los botones físicos de la interfaz con sus funciones lógicas.
     */
    private void configurarInteracciones() {
        // Al presionar "Cargar", buscamos el archivo y lanzamos el juego
        vista.getBtnCargar().addActionListener(e -> flujoCargaEInicio());
        
        // El botón de lanzar balero en la pantalla de juego
        vista.getPanelJuego().getBtnLanzar().addActionListener(e -> ejecutarLanzamiento());
    }

    /**
     * Orquestador del inicio: Selecciona archivo, divide el tiempo y arranca.
     */
    private void flujoCargaEInicio() {
        JFileChooser explorador = new JFileChooser();
        explorador.setFileFilter(new FileNameExtensionFilter("Configuración de Equipos (.properties)", "properties"));

        if (explorador.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            try {
                // 1. Intentamos leer los equipos del archivo seleccionado
                List<Equipo> equiposLeidos = gestionConfig.leerArchivoConfiguracion(explorador.getSelectedFile().getAbsolutePath());
                
                if (equiposLeidos.isEmpty()) {
                    avisarUsuario("El archivo está vacío. Por favor selecciona uno válido.");
                    return;
                }

                // 2. Lógica del Tiempo (REQUISITO: Tiempo grupal dividido entre 3)
                int tiempoTotalGrupo = Integer.parseInt(vista.getTextFieldTiempo().getText());
                int tiempoPorCadaJugador = tiempoTotalGrupo / 3;

                // 3. Creamos el objeto Juego con la repartición de tiempo lista
                this.juego = new Juego(equiposLeidos, tiempoPorCadaJugador);

                // 4. Preparamos la interfaz visual
                dibujarNombresEnPantalla();
                vista.mostrarPanel("JUEGO"); 
                prepararSiguienteTurno();

                //Muestra al jugador el tiempo que tendra cada jugador para lanzar
                JOptionPane.showMessageDialog(vista, "¡Configuración cargada! El tiempo se dividió en " + tiempoPorCadaJugador + "s por jugador.");
                
            
            //Excepciones si se ingresa un valor invalido y una expeción más general
            } catch (NumberFormatException nfe) {
                avisarUsuario("¡Error! Debes ingresar un número válido en el campo de tiempo.");
            } catch (Exception ex) {
                avisarUsuario("Algo salió mal al iniciar: " + ex.getMessage());
            }
        }
    }

    /**
     * Escribe los nombres del modelo en las etiquetas de la vista para que el usuario sepa quién es quién.
     */
    private void dibujarNombresEnPantalla() {
        List<Equipo> listaEquipos = juego.getEquipos();
        for (int i = 0; i < listaEquipos.size(); i++) {
            Equipo eq = listaEquipos.get(i);
            PanelEquipo pnl = vista.getPanelJuego().getEquipo(i);
            
            pnl.setNombreEquipo(eq.getNombre()); // Nombre del Grupo
            
            for (int j = 0; j < eq.getJugadores().size(); j++) {
                String nombreJug = eq.getJugadores().get(j).getNombre();
                pnl.getPanelJugador(j).actualizarNombre(nombreJug); // Nombre de cada integrante
            }
        }
    }

    /**
     * Acción de lanzar el balero: calcula puntos, actualiza el panel del jugador y muestra el mensaje.
     */
    private void ejecutarLanzamiento() {
        if (juego == null) return;

        // Lanzamos y registramos en el modelo
        TipoEmbocada resultado = juego.lanzarBalero();
        juego.registrarResultado(resultado);
        
        // Obtenemos quién lanzó para actualizar su cuadrito en la vista
        int idxEquipo = juego.getIndiceEquipoActual();
        int idxJugador = juego.getIndiceJugadorActual();
        Jugador lanzador = juego.getJugadorActual();

        PanelJugador casillaVisual = vista.getPanelJuego().getEquipo(idxEquipo).getPanelJugador(idxJugador);
        casillaVisual.actualizarDatos(lanzador.getPuntos(), lanzador.getIntentos());
        
        vista.getPanelJuego().setMensaje("¡" + lanzador.getNombre() + " hizo una " + resultado.getDescripcion() + "!");
    }

    /**
     * Configura el escenario para que el jugador actual lance.
     * REQUISITO: Resalta al equipo y pone más oscura la casilla del jugador activo.
     */
    private void prepararSiguienteTurno() {
        int eActivo = juego.getIndiceEquipoActual();
        int jActivo = juego.getIndiceJugadorActual();
        Jugador player = juego.getJugadorActual();

        // 1. Efecto Difuminado: Resalta al equipo completo
        aplicarEfectoEnfoque(eActivo);
        
        // 2. Resaltado Específico: Pone oscura la casilla del jugador que tiene el turno
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boolean esSuTurnoReal = (i == eActivo && j == jActivo);
                vista.getPanelJuego().getEquipo(i).getPanelJugador(j).setResaltado(esSuTurnoReal);
            }
        }
        
        vista.getPanelJuego().setMensaje("Esperando lanzamiento de: " + player.getNombre());
        iniciarCuentaRegresiva(juego.getTiempoPorJugador());
    }

    /**
     * El corazón del tiempo. Si llega a cero, el turno pasa automáticamente.
     */
    private void iniciarCuentaRegresiva(int segundos) {
        if (cronometro != null) cronometro.stop();
        this.tiempoRestante = segundos;
        
        cronometro = new Timer(1000, e -> {
            tiempoRestante--;
            vista.getPanelJuego().getLblTiempo().setText("⏱ " + tiempoRestante + "s");
            
            if (tiempoRestante <= 0) {
                cronometro.stop();
                JOptionPane.showMessageDialog(vista, "¡Se agotó el tiempo para este turno!");
                avanzarLógicaDeTurno();
            }
        });
        cronometro.start();
    }

    /**
     * Pasa al siguiente jugador o termina el juego si ya todos lanzaron.
     */
    private void avanzarLógicaDeTurno() {
        juego.siguienteJugador();
        if (juego.juegoTerminado()) {
            cronometro.stop();
            gestionarFinalDelTorneo();
        } else {
            prepararSiguienteTurno();
        }
    }

    /**
     * Cierre del torneo: Calcula ganador y usa el RAF para mostrar estadísticas históricas.
     */
    /**
     * Finaliza la competencia, determina al campeón y gestiona la persistencia histórica.
     * * Este método realiza tres tareas críticas:
     * 1. Compara los puntajes finales de todos los equipos participantes.
     * 2. Consulta el archivo binario (RAF) para obtener estadísticas previas del ganador.
     * 3. Registra la nueva victoria incluyendo Clave, Equipo, Jugadores y Puntos.
     * * Cumple con el requerimiento de mostrar el historial de victorias antes de 
     * cerrar el ciclo del juego.
     */
    private void gestionarFinalDelTorneo() {
        List<Equipo> participantes = juego.getEquipos();
        
        // Asumimos inicialmente que el primer equipo es el campeón
        Equipo campeon = participantes.get(0);
        
        // 1. Fase de Evaluación: Buscamos al equipo con el puntaje más alto
        for (Equipo eq : participantes) {
            eq.calcularPuntajeTotal(); // Aseguramos que el puntaje esté actualizado
            if (eq.getPuntajeTotal() > campeon.getPuntajeTotal()) {
                campeon = eq;
            }
        }

        // 2. Fase de Persistencia (Uso de RandomAccessFile)
        // Consultamos cuántas veces ha ganado este equipo en torneos pasados
        // Se hace ANTES de guardar la victoria actual para que el conteo sea exacto
        int recordsAnteriores = historicoDAO.obtenerVictoriasAnteriores(campeon.getNombre());
        
        // Guardamos el registro completo del equipo campeón en el archivo binario
        // Estructura: [Clave] [Nombre Equipo] [Jugador 1] [Jugador 2] [Jugador 3] [Puntaje]
        historicoDAO.guardarResultado(campeon);

        // 3. Fase de Interfaz: Construcción del mensaje de gloria
        String mensajeHistorial;
        if (recordsAnteriores > 0) {
            mensajeHistorial = String.format("Este equipo ya ha ganado el torneo %d %s anteriormente.",
                    recordsAnteriores, (recordsAnteriores == 1 ? "vez" : "veces"));
        } else {
            mensajeHistorial = "Primera vez que este equipo gana.";
        }

        String resumenFinal = String.format(
            "¡FELICITACIONES AL CAMPEÓN! \n\n" +
            "EQUIPO: %s\n" +
            "PUNTAJE TOTAL: %d pts\n" +
            "------------------------------------------\n" +
            "%s",
            campeon.getNombre().trim().toUpperCase(),
            campeon.getPuntajeTotal(),
            mensajeHistorial
        );

        // Mostramos el Hall de la Fama y regresamos al menú principal
        JOptionPane.showMessageDialog(vista, resumenFinal, "Resultados", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiamos el rastro del juego actual y volvemos al inicio
        vista.mostrarPanel("MENU");
    }

    /**
     * Controla la transparencia de los paneles de equipo (Principio de Enfoque).
     */
    private void aplicarEfectoEnfoque(int indiceBrillante) {
        for (int i = 0; i < 3; i++) {
            // 1.0f = Opaco (Visible), 0.3f = Transparente (Difuminado)
            vista.getPanelJuego().getEquipo(i).setTransparencia(i == indiceBrillante ? 1.0f : 0.3f);
        }
    }

    /**
     * Cumple con el Literal i: Carga integrantes desde un .txt sin errores fatales.
     */
    private void saludarYPresentarIntegrantes() {
        File archivoInfo = new File("src/Docs/Integrantes/Integrantes.txt");
        if(!archivoInfo.exists()) return;
        
        try (BufferedReader lector = new BufferedReader(new FileReader(archivoInfo))) {
            StringBuilder lista = new StringBuilder("DESARROLLADO POR:\n");
            String linea;
            while ((linea = lector.readLine()) != null) {
                lista.append("• ").append(linea).append("\n");
            }
            JOptionPane.showMessageDialog(vista, lista.toString(), "Créditos del Taller", 1);
        } catch (IOException e) { /* Silencio solicitado */ }
    }

    private void avisarUsuario(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Aviso del Sistema", JOptionPane.WARNING_MESSAGE);
    }
}