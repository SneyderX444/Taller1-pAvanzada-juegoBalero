package Modelo;

import java.io.Serializable;

/**
 * Representa a un participante individual del juego.
 * Responsabilidad: Almacenar estadísticas de desempeño personal.
 * * @author Juan
 * @version 2.0
 */
public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String codigo;
    private int puntaje;
    private int intentos;
    private int intentosEfectivos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        reiniciarEstadisticas();
    }

    /**
     * Incrementa el puntaje acumulado del jugador.
     * @param puntos Puntos a sumar.
     */
    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    public void incrementarIntentos() {
        this.intentos++;
    }

    public void registrarEmbocadaEfectiva() {
        this.intentosEfectivos++;
    }

    /**
     * Restablece todos los contadores a cero.
     */
    public final void reiniciarEstadisticas() {
        this.puntaje = 0;
        this.intentos = 0;
        this.intentosEfectivos = 0;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre; }
    
    public String getCodigo(){ 
        return codigo; 
    }
    public void setCodigo(String codigo){ this.codigo= codigo;
    }
    public int getPuntaje(){
        return puntaje; }
    
    public int getIntentos() { return intentos; }
    
    public int getIntentosEfectivos(){ 
        return intentosEfectivos;}
    public int getPuntos() { 
    return this.puntaje; 
}
}