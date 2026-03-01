package Controlador;

import Modelo.Equipo;
import Modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de la gestión de los equipos.
 *
 * Responsabilidades:
 * - Crear equipos
 * - Agregar jugadores a equipos
 * - Administrar la lista de equipos
 * - Calcular puntajes totales
 *
 * Este controlador será utilizado por el ControlPrincipal.
 *
 * No contiene lógica de interfaz gráfica.
 *
 * @author Juan
 * @version 1.0
 */
public class ControlEquipo {

    private List<Equipo> equipos;

    /**
     * Constructor.
     * Inicializa la lista de equipos.
     */
    public ControlEquipo() {
        equipos = new ArrayList<>();
    }

    // ================= CREACIÓN =================

    /**
     * Crea un nuevo equipo y lo agrega a la lista.
     *
     * @param nombre nombre del equipo
     * @param proyecto proyecto curricular
     * @return equipo creado
     */
    public Equipo crearEquipo(String nombre, String proyecto) {
        Equipo equipo = new Equipo(nombre, proyecto);
        equipos.add(equipo);
        return equipo;
    }

    /**
     * Agrega un jugador a un equipo.
     *
     * @param equipo equipo destino
     * @param nombreJugador nombre del jugador
     */
    public void agregarJugador(Equipo equipo, String nombreJugador) {
        Jugador jugador = new Jugador(nombreJugador);
        equipo.agregarJugador(jugador);
    }

    // ================= CONSULTAS =================

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public Equipo getEquipo(int index) {
        return equipos.get(index);
    }

    public int cantidadEquipos() {
        return equipos.size();
    }

    // ================= LÓGICA =================

    /**
     * Calcula el puntaje total de todos los equipos.
     */
    public void calcularPuntajesTotales() {
        for (Equipo equipo : equipos) {
            equipo.calcularPuntajeTotal();
        }
    }

    /**
     * Reinicia las estadísticas de todos los equipos.
     */
    public void reiniciarEquipos() {
        for (Equipo equipo : equipos) {
            equipo.reiniciarPuntajes();
        }
    }
}