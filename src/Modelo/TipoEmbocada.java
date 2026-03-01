package Modelo;

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

    TipoEmbocada(String descripcion, int puntos) {
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public String getDescripcion() { return descripcion; }
    public int getPuntos() { return puntos; }
}