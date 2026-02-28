package Modelo;

/**
 * Enum que representa los tipos de embocadas posibles
 * en el juego del balero junto con su puntaje asociado.
 */
public enum TipoEmbocada {

    SIMPLE(2),
    DOBLE(10),
    VERTICAL(3),
    MARIQUITA(4),
    PUNALADA(5),
    PURTINA(6),
    DOMINIO_REVES(8),
    SIN_EMBOCADA(0);

    private final int puntos;

    /**
     * Constructor del enum.
     * @param puntos valor en puntos de la embocada
     */
    private TipoEmbocada(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Retorna el puntaje asociado al tipo de embocada.
     * @return puntos de la embocada
     */
    public int getPuntos() {
        return puntos;
    }
}