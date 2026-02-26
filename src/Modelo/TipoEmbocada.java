package Modelo;

/**
 * Enumeración que representa los diferentes tipos de embocada
 * posibles en el juego del balero.
 * 
 * Cada tipo de embocada tiene un puntaje asociado.
 * 
 * Responsabilidad:
 * - Definir los tipos de embocada.
 * - Asociar puntos a cada tipo.
 */
public enum TipoEmbocada {

    SIMPLE(2),
    DOBLE(10),
    VERTICAL(3),
    MARIQUITA(4),
    PUNALADA(5),
    PURTINA(6),
    DOMINIO_REVES(8),
    FALLA(0);

    private final int puntos; // el atributo es final porque el puntaje 
    //de cada tipo no debe cambiar nunca. Eso hace al Enum inmutable

    /**
     * Constructor del enum.
     * 
     * @param puntos Puntaje asociado al tipo de embocada.
     */
    TipoEmbocada(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Retorna el puntaje asociado al tipo de embocada.
     * 
     * @return puntos de la embocada.
     */
    public int getPuntos() {
        return puntos;
    }
}