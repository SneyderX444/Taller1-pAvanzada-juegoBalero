package Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;

/**
 * Carga la configuración inicial de equipos y jugadores.
 * @author Juan
 */
public class GestionConfiguracion {
    
    public List<Equipo> leerArchivoConfiguracion(String ruta) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(ruta));
        List<Equipo> lista = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String nomEq = p.getProperty("equipo" + i);
            String proy = p.getProperty("equipo" + i + ".proyecto");
            
            if (nomEq != null) {
                Equipo eq = new Equipo(nomEq, proy);
                for (int j = 1; j <= 3; j++) {
                    String nomJug = p.getProperty("equipo" + i + ".jugador" + j + ".nombre" + j);
                    String codJug = p.getProperty("equipo" + i + ".jugador" + j + ".codigo" + j);
                    if (nomJug != null) {
                        Jugador jug = new Jugador(nomJug);
                        jug.setCodigo(codJug);
                        eq.agregarJugador(jug);
                    }
                }
                lista.add(eq);
            }
        }
        return lista;
    }
}
