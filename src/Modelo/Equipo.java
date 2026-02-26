package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un equipo dentro del juego del balero.
 * 
 * Un equipo está compuesto por exactamente 3 jugadores.
 * Se encarga de calcular el puntaje total y los intentos totales
 * acumulados por sus integrantes.
 * 
 * Responsabilidad:
 * - Agrupar jugadores.
 * - Calcular totales del equipo.
 * 
 * @author
 */
public class Equipo implements Serializable {

    private String nombreProyecto;
    private String nombreEquipo;
    private List<Jugador> jugadores;

    /**
     * Constructor de la clase Equipo.
     * 
     * @param nombreProyecto Nombre del proyecto curricular.
     * @param nombreEquipo Nombre del equipo.
     */
    public Equipo(String nombreProyecto, String nombreEquipo) {
        this.nombreProyecto = nombreProyecto;
        this.nombreEquipo = nombreEquipo;
        this.jugadores = new ArrayList<>();
    }

    /**
     * Agrega un jugador al equipo.
     * 
     * @param jugador Jugador a agregar.
     */
    public void agregarJugador(Jugador jugador) {
        if (jugadores.size() < 3) {           // Se limita a 3 juagdores por el requerimiento
            jugadores.add(jugador);
        }
    }

    /**
     * Calcula el puntaje total del equipo
     * sumando el puntaje de todos sus jugadores.
     * 
     * @return puntaje total del equipo.
     */
    public int calcularPuntajeTotal() {
        int total = 0;
        for (Jugador jugador : jugadores) {
            total += jugador.getPuntaje();
        }
        return total;
    }

    /**
     * Calcula los intentos totales del equipo.
     * 
     * @return total de intentos.
     */
    public int calcularIntentosTotales() {
        int total = 0;
        for (Jugador jugador : jugadores) {
            total += jugador.getIntentos();
        }
        return total;
    }

    /**
     * Retorna la lista de jugadores del equipo.
     * 
     * @return lista de jugadores.
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Retorna el nombre del equipo.
     * 
     * @return nombre del equipo.
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * Retorna el nombre del proyecto curricular.
     * 
     * @return nombre del proyecto.
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }
}

    /**
     * Decisiones importantes que tomamos
     * No guardamos puntajeTotal como atributo
     * En vez de esto: Lo calculamos dinámicamente.
     * private int puntajeTotal;
     *¿Por qué?
     *Porque evita inconsistencias. Es mejor diseño. Cumple mejor con SOLID.
     */