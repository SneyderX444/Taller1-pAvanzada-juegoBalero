package Controlador;

import Modelo.Equipo;
import Modelo.Jugador;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la gestión de entidades de Equipo.
 * Responsabilidad: Administrar la creación, almacenamiento y cálculos agregados de los equipos.
 * Cumple con SRP al no manejar lógica de juego ni de interfaz.
 * * @author Juan
 * @version 2.0
 */
public class ControlEquipo {

    private List<Equipo> equipos;

    public ControlEquipo() {
        this.equipos = new ArrayList<>();
    }

    /**
     * Registra una lista de equipos preexistente (útil para carga desde archivos).
     * @param equipos Lista de equipos cargada.
     */
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = (equipos != null) ? equipos : new ArrayList<>();
    }

    /**
     * Crea y almacena un equipo.
     * @param nombre Nombre identificativo.
     * @param proyecto Proyecto curricular asociado.
     * @return La instancia del equipo creado.
     */
    public Equipo crearEquipo(String nombre, String proyecto) {
        Equipo equipo = new Equipo(nombre, proyecto);
        equipos.add(equipo);
        return equipo;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    /**
     * Centraliza el cálculo de puntajes delegando en cada modelo.
     */
    public void actualizarEstadisticasGlobales() {
        equipos.forEach(Equipo::calcularPuntajeTotal);
    }

    /**
     * Limpia los datos de juego de todos los equipos registrados.
     */
    public void reiniciarTodosLosEquipos() {
        equipos.forEach(Equipo::reiniciarPuntajes);
    }
}