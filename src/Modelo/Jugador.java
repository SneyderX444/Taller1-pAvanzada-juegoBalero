package Modelo;

import java.io.Serializable;

public class Jugador implements Serializable {
    private int intentosEfectivos = 0;
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String codigo;
    private int puntaje;
    private int intentos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.intentos = 0;
    }

    // Métodos que faltaban y causaban errores
    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    public void incrementarIntentos() {
        this.intentos++;
    }

    public void reiniciarEstadisticas() {
        this.puntaje = 0;
        this.intentos = 0;
    }
    
    
    public void registrarEmbocadaEfectiva() {
    this.intentosEfectivos++;
    }
    
    public int getIntentosEfectivos() {
    return intentosEfectivos;
   }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    public int getIntentos() { return intentos; }
}