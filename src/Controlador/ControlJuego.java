package Controlador;

import Modelo.*;
import java.util.List;

/**
 * Controlador de flujo y reglas de negocio del juego.
 * Actúa como mediador entre el orquestador principal y el motor de reglas (Juego).
 * * @author Juan
 * @version 2.0
 */
public class ControlJuego {

    private Juego modeloJuego;

    /**
     * Inicializa el motor de juego. 
     * @param equipos Lista de equipos participantes.
     * @param tiempo Tiempo límite por cada turno de jugador.
     */
    public void iniciarJuego(List<Equipo> equipos, int tiempo) {
        this.modeloJuego = new Juego(equipos, tiempo);
    }

    /**
     * Ejecuta la acción de lanzamiento y registra el resultado en el modelo.
     * @return El tipo de embocada obtenido aleatoriamente.
     */
    public TipoEmbocada ejecutarLanzamiento() {
        TipoEmbocada resultado = modeloJuego.lanzarBalero();
        modeloJuego.registrarResultado(resultado);
        return resultado;
    }

    public void pasarAlSiguienteTurno() {
        modeloJuego.siguienteJugador();
    }

    public boolean esFinDelJuego() {
        return (modeloJuego != null) && modeloJuego.juegoTerminado();
    }

    // --- Getters de Estado Actual ---
    
    public Jugador getJugadorActivo() {
        return modeloJuego.getJugadorActual();
    }

    public int getIndiceEquipoActual() {
        return modeloJuego.getIndiceEquipoActual();
    }

    public int getIndiceJugadorActual() {
        return modeloJuego.getIndiceJugadorActual();
    }

    public int getTiempoPorJugador() {
        return modeloJuego.getTiempoPorJugador();
    }
}