package Controlador;

import Modelo.Equipo;
import Modelo.Jugador;
import Modelo.Juego;
import Modelo.TipoEmbocada;

import java.util.List;

/**
 * Controlador encargado del flujo del juego.
 *
 * Responsabilidades:
 * - Inicializar el juego
 * - Ejecutar lanzamientos
 * - Controlar turnos
 * - Verificar finalización
 *
 * Este controlador coordina el ModeloJuego.
 *
 * No contiene lógica de interfaz gráfica.
 *
 * @author Juan
 * @version 1.0
 */
public class ControlJuego {

    private Juego modeloJuego;

    /**
     * Inicializa el juego con los equipos y el tiempo por jugador.
     */
    public void iniciarJuego(List<Equipo> equipos, int tiempoPorJugador) {
        modeloJuego = new Juego(equipos, tiempoPorJugador);
    }

    // ================= ACCIONES DEL JUEGO =================

    /**
     * Realiza un lanzamiento del balero.
     *
     * @return TipoEmbocada obtenida
     */
    public TipoEmbocada lanzar() {
        TipoEmbocada resultado = modeloJuego.lanzarBalero();
        modeloJuego.registrarResultado(resultado);
        return resultado;
    }

    /**
     * Pasa al siguiente jugador.
     */
    public void siguienteTurno() {
        modeloJuego.siguienteJugador();
    }

    /**
     * Indica si el juego ha terminado.
     */
    public boolean juegoTerminado() {
        return modeloJuego.juegoTerminado();
    }

    // ================= CONSULTAS =================

    public Equipo getEquipoActual() {
        return modeloJuego.getEquipoActual();
    }

    public Jugador getJugadorActual() {
        return modeloJuego.getJugadorActual();
    }

    public int getIntentosActual() {
        return modeloJuego.getIntentosJugador();
    }

    public int getTiempoPorJugador() {
        return modeloJuego.getTiempoPorJugador();
    }

    public List<Equipo> getEquipos() {
        return modeloJuego.getEquipos();
    }

    /**
     * Calcula los puntajes finales de los equipos.
     */
    public void calcularResultadosFinales() {
        modeloJuego.calcularPuntajesEquipos();
    }
}