package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a team in the Balero game.
 *
 * Responsibilities:
 * - Store team information
 * - Manage its players
 * - Accumulate team score
 * - Store historical wins
 *
 * Part of the Model layer (MVC).
 *
 * @author Juan
 * @version 1.0
 */
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String proyectoCurricular;
    private List<Jugador> jugadores;
    private int puntajeTotal;
    private int partidasGanadas;

    /**
     * Constructor vacío.
     */
    public Equipo() {
        this.jugadores = new ArrayList<>();
    }

    /**
     * Constructor principal.
     */
    public Equipo(String nombre, String proyectoCurricular) {
        this.nombre = nombre;
        this.proyectoCurricular = proyectoCurricular;
        this.jugadores = new ArrayList<>();
        this.puntajeTotal = 0;
        this.partidasGanadas = 0;
    }

    // ================= MÉTODOS DE NEGOCIO =================

    /**
     * Agrega un jugador al equipo.
     */
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    /**
     * Calcula el puntaje total del equipo sumando
     * el puntaje de todos sus jugadores.
     */
    public void calcularPuntajeTotal() {
        puntajeTotal = 0;
        for (Jugador j : jugadores) {
            puntajeTotal += j.getPuntaje();
        }
    }

    /**
     * Incrementa el número de partidas ganadas.
     */
    public void incrementarVictorias() {
        partidasGanadas++;
    }

    /**
     * Reinicia el puntaje de todos los jugadores.
     */
    public void reiniciarPuntajes() {
        puntajeTotal = 0;
        for (Jugador j : jugadores) {
            j.setPuntaje(0);
        }
    }

    // ================= GETTERS Y SETTERS =================

    public String getNombre() {
        return nombre;
    }
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProyectoCurricular() {
        return proyectoCurricular;
    }

    public void setProyectoCurricular(String proyectoCurricular) {
        this.proyectoCurricular = proyectoCurricular;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }
}