package Modelo;

import java.io.*;
import java.util.*;

public class GestionConfiguracion {
    public List<Equipo> leerArchivoConfiguracion(String ruta) throws IOException {
        Properties p = new Properties();
        try (InputStream is = new FileInputStream(ruta)) {
            p.load(is);
        }

        List<Equipo> lista = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String nomEq = p.getProperty("equipo" + i);
            if (nomEq == null) continue;

            Equipo eq = new Equipo(nomEq, p.getProperty("equipo" + i + ".proyecto", "General"));
            for (int j = 1; j <= 3; j++) {
                String nomJug = p.getProperty("equipo" + i + ".jugador" + j + ".nombre" + j);
                if (nomJug != null) {
                    Jugador jug = new Jugador(nomJug);
                    jug.setCodigo(p.getProperty("equipo" + i + ".jugador" + j + ".codigo" + j, "000"));
                    eq.agregarJugador(jug);
                }
            }
            lista.add(eq);
        }
        return lista;
    }
}
