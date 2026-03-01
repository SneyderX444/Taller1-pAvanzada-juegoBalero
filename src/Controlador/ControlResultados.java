package Controlador;

import Modelo.Equipo;
import Modelo.ResultadosRAF;
import java.util.List;

/**
 * Controlador de resultados con lógica de desempate.
 * @author Juan
 */
public class ControlResultados {
    private ResultadosRAF modeloResultados;

    public ControlResultados(ResultadosRAF modelo) {
    this.modeloResultados = modelo;
  }

    public Equipo determinarGanador(List<Equipo> equipos) {
        Equipo ganador = equipos.get(0);

        for (int i = 1; i < equipos.size(); i++) {
            Equipo actual = equipos.get(i);
            
            if (actual.getPuntajeTotal() > ganador.getPuntajeTotal()) {
                ganador = actual;
            } 
            else if (actual.getPuntajeTotal() == ganador.getPuntajeTotal()) {
                // CRITERIO DESEMPATE: Suma de intentos efectivos de sus jugadores
                if (obtenerEmbocadasTotales(actual) > obtenerEmbocadasTotales(ganador)) {
                    ganador = actual;
                }
            }
        }
        return ganador;
    }

    private int obtenerEmbocadasTotales(Equipo e) {
        return e.getJugadores().stream().mapToInt(j -> j.getIntentosEfectivos()).sum();
    }

    public void registrarEnHistorial(Equipo e) {
        persistenciaRAF.guardarGanador(e.getNombre(), e.getPuntajeTotal());
    }
}
