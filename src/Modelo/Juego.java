package Modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Motor central de lógica del juego.
 * Controla el flujo de turnos, lanzamientos y estados de finalización.
 */
public class Juego implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Equipo> equipos;
    private int indiceEquipoActual;
    private int indiceJugadorActual;
    private final int tiempoPorJugador;
    private final Random random;

    public Juego(List<Equipo> equipos, int tiempoPorJugador) {
        this.equipos = equipos;
        this.tiempoPorJugador = tiempoPorJugador;
        this.indiceEquipoActual = 0;
        this.indiceJugadorActual = 0;
        this.random = new Random();
    }

    /**
     * Selecciona una embocada al azar del Enum TipoEmbocada.
     */
    public TipoEmbocada lanzarBalero() {
        TipoEmbocada[] opciones = TipoEmbocada.values();
        return opciones[random.nextInt(opciones.length)];
    }

    /**
     * Registra el resultado en el jugador que tiene el turno actual.
     */
    public void registrarResultado(TipoEmbocada embocada) {
        Jugador actual = getJugadorActual();
        actual.sumarPuntos(embocada.getPuntos());
        actual.incrementarIntentos();
        if (embocada != TipoEmbocada.NINGUNA) {
            actual.registrarEmbocadaEfectiva();
        }
    }

    /**
     * Gestiona el avance de los índices de turno.
     */
    public void siguienteJugador() {
        indiceJugadorActual++;
        if (indiceJugadorActual >= getEquipoActual().getJugadores().size()) {
            indiceJugadorActual = 0;
            indiceEquipoActual++;
        }
    }

    public boolean juegoTerminado() {
        return indiceEquipoActual >= equipos.size();
    }

    // Getters de estado actual
    public Equipo getEquipoActual() { return equipos.get(indiceEquipoActual); }
    public Jugador getJugadorActual() { return getEquipoActual().getJugadores().get(indiceJugadorActual); }
    public int getIndiceEquipoActual() { return indiceEquipoActual; }
    public int getIndiceJugadorActual() { return indiceJugadorActual; }
    public int getTiempoPorJugador() { return tiempoPorJugador; }
    public List<Equipo> getEquipos() { return equipos; }
}