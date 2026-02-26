package Modelo;

import java.io.Serializable;
/**
 * Clase que representa un jugador dentro del juego del balero.
 * Cada jugador tiene un código, nombre, puntaje acumulado e intentos realizados.
 * 
 * Responsabilidad:
 * - Acumular puntos.
 * - Contabilizar intentos.
 * 
 * @author 
 */
public class Jugador implements Serializable {

    private String codigo;
    private String nombre;
    private int puntaje;
    private int intentos;

    /**
     * Constructor de la clase Jugador.
     * 
     * @param codigo Código del estudiante.
     * @param nombre Nombre del jugador.
     */
    public Jugador(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puntaje = 0;
        this.intentos = 0;
    }

    /**
     * Suma puntos al jugador.
     * 
     * @param puntos Cantidad de puntos a agregar.
     */
    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    /**
     * Incrementa en uno el número de intentos del jugador.
     */
    public void incrementarIntento() {
        this.intentos++;
    }

    /**
     * Retorna el código del jugador.
     * 
     * @return código del jugador.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna el nombre del jugador.
     * 
     * @return nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el puntaje acumulado.
     * 
     * @return puntaje del jugador.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Retorna la cantidad de intentos realizados.
     * 
     * @return intentos del jugador.
     */
    public int getIntentos() {
        return intentos;
    }
}