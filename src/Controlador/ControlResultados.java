package Controlador;

import Modelo.Equipo;
import Modelo.ResultadosRAF;
import java.util.List;
import java.util.Comparator;

/**
 * Controlador para la gestión de resultados finales y persistencia en archivos.
 * * @author Juan
 * @version 2.0
 */
public class ControlResultados {

    private final ResultadosRAF modeloPersistencia;

    public ControlResultados(ResultadosRAF modelo) {
        this.modeloPersistencia = modelo;
    }

    /**
     * Determina el ganador basándose en puntaje total y desempate por intentos.
     * @param equipos Lista de equipos a comparar.
     * @return El equipo con mejor desempeño.
     */
    public Equipo determinarGanador(List<Equipo> equipos) {
        if (equipos == null || equipos.isEmpty()) return null;

        return equipos.stream()
            .max(Comparator.comparingInt(Equipo::getPuntajeTotal)
            .thenComparingInt(this::calcularIntentosTotales))
            .orElse(null);
    }

    /**
     * Sumatoria de intentos de todos los jugadores de un equipo.
     */
    private int calcularIntentosTotales(Equipo e) {
        return e.getJugadores().stream()
                .mapToInt(j -> j.getIntentos()) 
                .sum();
    }

    /**
     * Persiste el resultado del ganador en el archivo de acceso aleatorio.
     */
    public void registrarResultadoFinal(Equipo e) {
        if (e != null) {
            modeloPersistencia.guardarResultado(e.getNombre(), e.getPuntajeTotal());
        }
    }
}
