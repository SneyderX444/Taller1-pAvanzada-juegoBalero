package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo en el torneo.
 * Responsabilidad: Gestionar el grupo de jugadores y el puntaje acumulado del equipo.
 */
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String proyectoCurricular;
    private List<Jugador> jugadores;
    private int puntajeTotal;

    public Equipo(String nombre, String proyectoCurricular) {
        this.nombre = nombre;
        this.proyectoCurricular = proyectoCurricular;
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        if (jugadores.size() < 3) {
            jugadores.add(jugador);
        }
    }

    /**
     * Calcula el puntaje total sumando el de cada jugador usando Streams.
     */
    public void calcularPuntajeTotal() {
        this.puntajeTotal = jugadores.stream()
                                     .mapToInt(Jugador::getPuntaje)
                                     .sum();
    }

    public void reiniciarPuntajes() {
        this.puntajeTotal = 0;
        jugadores.forEach(Jugador::reiniciarEstadisticas);
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getProyectoCurricular() { return proyectoCurricular; }
    public List<Jugador> getJugadores() { return jugadores; }
    public int getPuntajeTotal() { return puntajeTotal; }
}