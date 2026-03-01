package Modelo;

/**
 * Enumeración que define los tipos de embocadas y sus puntajes.
 * * Responsabilidades:
 * - Centralizar los valores de puntos por cada jugada.
 * - Proveer nombres descriptivos para la interfaz.
 * * @author Juan
 * @version 1.1
 */
public enum TipoEmbocada {
    NINGUNA("Siga intentando", 0),
    SIMPLE("Simple", 2),
    DOBLE("Doble", 10),
    VERTICAL("Vertical", 3),
    MARIQUITA("Mariquita", 4),
    PUNALADA("Puñalada", 5),
    PURTINA("Purtiña", 6),
    DOMINIO_REVES("Dominio de revés", 8);

    private final String descripcion;
    private final int puntos;

    /**
     * Constructor del enum.
     * @param descripcion nombre de la jugada.
     * @param puntos valor numérico de la jugada.
     */
    TipoEmbocada(String descripcion, int puntos) {
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public String getDescripcion() { return descripcion; }
    public int getPuntos() { return puntos; }
}