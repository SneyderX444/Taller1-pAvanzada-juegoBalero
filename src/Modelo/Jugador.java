package Modelo;

/**
 * Representa un jugador dentro del juego del balero.
 * Cada jugador mantiene su propio puntaje, número de intentos
 * y cantidad de embocadas exitosas.
 */
public class Jugador {

    private String codigo;
    private String nombre;
    private int puntaje;
    private int intentos;
    private int embocadasExitosas;

    /**
     * Constructor del jugador.
     * Inicializa las estadísticas en cero.
     * 
     * @param codigo identificador único del jugador
     * @param nombre nombre del jugador
     */
    public Jugador(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puntaje = 0;
        this.intentos = 0;
        this.embocadasExitosas = 0;
    }

    /**
     * Registra un intento del jugador con el tipo de embocada obtenido.
     * Actualiza puntaje, intentos y embocadas exitosas.
     * 
     * @param tipo tipo de embocada realizada
     */
    public void registrarIntento(TipoEmbocada tipo) {

        intentos++;

        int puntosObtenidos = tipo.getPuntos();
        puntaje += puntosObtenidos;

        if (puntosObtenidos > 0) {
            embocadasExitosas++;
        }
    }

    /**
     * Reinicia las estadísticas del jugador.
     */
    public void reiniciarEstadisticas() {
        puntaje = 0;
        intentos = 0;
        embocadasExitosas = 0;
    }

    // =====================
    // Getters
    // =====================

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getIntentos() {
        return intentos;
    }

    public int getEmbocadasExitosas() {
        return embocadasExitosas;
    }
}