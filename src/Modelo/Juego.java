package Modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Modelo que representa el estado del juego del Balero.
 *
 * Responsabilidades:
 * - Controlar equipos y jugadores actuales
 * - Administrar intentos
 * - Generar embocadas aleatorias
 * - Registrar puntajes
 *
 * Pertenece a la capa Modelo (MVC).
 *
 * No contiene lógica de interfaz ni controladores.
 *
 * @author Juan
 * @version 1.1
 */
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Equipo> equipos;
    private int indiceEquipoActual;
    private int indiceJugadorActual;

    private int tiempoPorJugador;
    private int intentosJugador;

    private Random random;

    /**
     * Constructor del modelo del juego.
     * 
     * @param equipos lista de equipos participantes
     * @param tiempoPorJugador tiempo asignado a cada jugador
     */
    public Juego(List<Equipo> equipos, int tiempoPorJugador) {
        this.equipos = equipos;
        this.tiempoPorJugador = tiempoPorJugador;
        this.indiceEquipoActual = 0;
        this.indiceJugadorActual = 0;
        this.intentosJugador = 0;
        this.random = new Random();
    }

    // ================= ESTADO ACTUAL =================

    public Equipo getEquipoActual() {
        return equipos.get(indiceEquipoActual);
    }

    public Jugador getJugadorActual() {
        return getEquipoActual().getJugadores().get(indiceJugadorActual);
    }

    public int getIntentosJugador() {
        return intentosJugador;
    }

    public int getTiempoPorJugador() {
        return tiempoPorJugador;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    // ================= TURNOS =================

    /**
     * Avanza al siguiente jugador.
     * Si se terminan los jugadores del equipo,
     * pasa al siguiente equipo.
     */
    public void siguienteJugador() {
        indiceJugadorActual++;

        if (indiceJugadorActual >= getEquipoActual().getJugadores().size()) {
            indiceJugadorActual = 0;
            indiceEquipoActual++;
        }

        intentosJugador = 0;
    }

    /**
     * Indica si el juego ha finalizado.
     */
    public boolean juegoTerminado() {
        return indiceEquipoActual >= equipos.size();
    }

    // ================= LÓGICA DEL JUEGO =================

    /**
     * Genera una embocada aleatoria.
     * Selecciona un valor aleatorio del enum TipoEmbocada.
     *
     * @return TipoEmbocada obtenida
     */
    public TipoEmbocada lanzarBalero() {
        TipoEmbocada[] valores = TipoEmbocada.values();
        int indice = random.nextInt(valores.length);
        return valores[indice];
    }

    /**
     * Registra el resultado del lanzamiento
     * sumando los puntos al jugador actual.
     *
     * @param embocada tipo de embocada obtenida
     */
    public void registrarResultado(TipoEmbocada embocada) {
        Jugador jugador = getJugadorActual();
        jugador.sumarPuntos(embocada.getPuntos());
        intentosJugador++;
    }

    /**
     * Calcula el puntaje total de todos los equipos.
     */
    public void calcularPuntajesEquipos() {
        for (Equipo equipo : equipos) {
            equipo.calcularPuntajeTotal();
        }
    }
}