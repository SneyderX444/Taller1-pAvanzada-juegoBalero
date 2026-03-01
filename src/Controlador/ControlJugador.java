package Controlador;

import Modelo.Jugador;

/**
 * Controlador de operaciones individuales para Jugadores.
 * Provee una capa de abstracción para modificar estadísticas de jugadores.
 * * @author Juan
 * @version 2.0
 */
public class ControlJugador {

    public Jugador crearJugador(String nombre) {
        return new Jugador(nombre);
    }

    public void procesarAcierto(Jugador jugador, int puntos) {
        jugador.sumarPuntos(puntos);
        jugador.incrementarIntentos(); // Se asume que todo acierto es un intento
    }

    public void registrarFallo(Jugador jugador) {
        jugador.incrementarIntentos();
    }
}
