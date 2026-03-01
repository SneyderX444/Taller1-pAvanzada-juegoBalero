package Controlador;

import Modelo.Equipo;
import Modelo.Resultados;
import java.util.List;

/**
 * Controlador encargado de la gestión de resultados del juego.
 *
 * Responsabilidades:
 * - Determinar el equipo ganador
 * - Guardar el resultado en el archivo persistente
 * - Consultar cuántas veces ha ganado un equipo
 * - Obtener el historial de resultados
 *
 * Este controlador NO maneja archivos directamente.
 * Toda la persistencia se delega al modelo Resultados.
 *
 * @author Juan
 * @version 1.0
 */
public class ControlResultados {

    private Resultados modeloResultados;

    /**
     * Constructor
     */
    public ControlResultados() {
        modeloResultados = new Resultados();
    }

    /**
     * Determina el equipo ganador (mayor puntaje).
     */
    public Equipo determinarGanador(List<Equipo> equipos) {
        Equipo ganador = null;
        int mayorPuntaje = -1;

        for (Equipo equipo : equipos) {
            if (equipo.getPuntajeTotal() > mayorPuntaje) {
                mayorPuntaje = equipo.getPuntajeTotal();
                ganador = equipo;
            }
        }

        return ganador;
    }

    /**
     * Guarda el resultado del equipo ganador.
     */
    public void guardarResultado(Equipo equipoGanador) {
        modeloResultados.guardarResultado(
                equipoGanador.getNombre(),
                equipoGanador.getPuntajeTotal()
        );
    }

    /**
     * Obtiene cuántas veces ha ganado un equipo.
     */
    public int obtenerVictorias(String nombreEquipo) {
        return modeloResultados.contarVictorias(nombreEquipo);
    }

    /**
     * Obtiene el historial completo.
     */
    public List<String> obtenerHistorial() {
        return modeloResultados.leerResultados();
    }
}
