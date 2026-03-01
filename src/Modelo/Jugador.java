package Modelo;

import java.io.Serializable;

/**
 * Representa a un jugador dentro de un equipo en el juego del Balero.
 *
 * Responsabilidades:
 * - Almacenar información del jugador
 * - Gestionar su puntaje
 * - Registrar intentos realizados
 *
 * Pertenece a la capa Modelo (MVC).
 *
 * No contiene lógica de interfaz ni control del juego.
 *
 * @author Juan
 * @version 1.0
 */
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private int puntaje;
    private int intentos;

    /**
     * Constructor vacío.
     */
    public Jugador() {
        this.puntaje = 0;
        this.intentos = 0;
    }

    /**
     * Constructor con nombre.
     *
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.intentos = 0;
    }

    // ================= LÓGICA DEL MODELO =================

    /**
     * Suma puntos al jugador.
     *
     * @param puntos cantidad de puntos a agregar
     */
    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    /**
     * Incrementa el número de intentos.
     */
    public void incrementarIntentos() {
        this.intentos++;
    }

    /**
     * Reinicia las estadísticas del jugador.
     */
    public void reiniciarEstadisticas() {
        this.puntaje = 0;
        this.intentos = 0;
    }

    // ================= GETTERS Y SETTERS =================

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre +
               " | Puntaje: " + puntaje +
               " | Intentos: " + intentos;
    }
}