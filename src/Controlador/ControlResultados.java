package Controlador;

import Modelo.Equipo;
import Modelo.ResultadosRAF;
import java.util.List;

public class ControlResultados {

    private ResultadosRAF modeloResultados;

    // CONSTRUCTOR CORREGIDO
    public ControlResultados(ResultadosRAF modeloResultados) {
        this.modeloResultados = modeloResultados;
    }

    public Equipo determinarGanador(List<Equipo> equipos) {
        if (equipos == null || equipos.isEmpty()) return null;
        
        Equipo ganador = equipos.get(0);
        for (int i = 1; i < equipos.size(); i++) {
            Equipo actual = equipos.get(i);
            if (actual.getPuntajeTotal() > ganador.getPuntajeTotal()) {
                ganador = actual;
            } else if (actual.getPuntajeTotal() == ganador.getPuntajeTotal()) {
                // Lógica de desempate usando el método que causaba error
                if (obtenerIntentosTotales(actual) > obtenerIntentosTotales(ganador)) {
                    ganador = actual;
                }
            }
        }
        return ganador;
    }

    private int obtenerIntentosTotales(Equipo e) {
        // Asegúrate que el método getIntentosEfectivos() exista en Jugador.java
        return e.getJugadores().stream().mapToInt(j -> j.getIntentosEfectivos()).sum();
    }
    
    public void guardarGanador(Equipo e) {
        modeloResultados.guardarResultado(e.getNombre(), e.getPuntajeTotal());
    }
}
