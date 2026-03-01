package Controlador;

import Modelo.Jugador;

/**
 * Controlador encargado de la gestión de los jugadores.
 *
 * Responsabilidades:
 * - Crear jugadores
 * - Reiniciar estadísticas
 * - Gestionar información básica del jugador
 *
 * Este controlador será utilizado por ControlEquipo
 * o por el ControlPrincipal.
 *
 * No contiene lógica de interfaz gráfica.
 *
 * @author Juan
 * @version 1.0
 */
public class ControlJugador {

    /**
     * Crea un nuevo jugador.
     *
     * @param nombre nombre del jugador
     * @return jugador creado
     */
    public Jugador crearJugador(String nombre) {
        return new Jugador(nombre);
    }

    /**
     * Reinicia las estadísticas de un jugador.
     *
     * @param jugador jugador a reiniciar
     */
    public void reiniciarJugador(Jugador jugador) {
        jugador.reiniciarEstadisticas();
    }

    /**
     * Suma puntos a un jugador.
     *
     * @param jugador jugador
     * @param puntos puntos a agregar
     */
    public void sumarPuntos(Jugador jugador, int puntos) {
        jugador.sumarPuntos(puntos);
    }

    /**
     * Incrementa los intentos del jugador.
     *
     * @param jugador jugador
     */
    public void incrementarIntentos(Jugador jugador) {
        jugador.incrementarIntentos();
    }

    /**
     * Obtiene el puntaje del jugador.
     */
    public int getPuntaje(Jugador jugador) {
        return jugador.getPuntaje();
    }

    /**
     * Obtiene los intentos del jugador.
     */
    public int getIntentos(Jugador jugador) {
        return jugador.getIntentos();
    }
}
