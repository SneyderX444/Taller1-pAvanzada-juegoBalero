package Modelo;

import java.io.*;
import java.util.List;

/**
 * Clase encargada de la persistencia del sistema.
 *
 * Responsabilidades:
 * - Guardar los equipos y sus jugadores en el archivo
 *   equipos_jugadores.persistence
 * - Cargar la información desde el archivo
 *
 * Pertenece a la capa Modelo (MVC).
 *
 * Importante:
 * Las clases Equipo y Jugador deben implementar Serializable.
 *
 * @author Juan
 * @version 1.0
 */
public class ConexionPropiedades {

    // Nombre del archivo de persistencia
    private final String RUTA_ARCHIVO = "equipos_jugadores.persistence";

    /**
     * Guarda la lista de equipos en el archivo.
     *
     * @param equipos Lista de equipos a guardar
     * @throws IOException Si ocurre un error al escribir
     */
    public void guardarEquipos(List<?> equipos) throws IOException {
        try (ObjectOutputStream salida =
                     new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {

            salida.writeObject(equipos);
        }
    }

    /**
     * Carga la lista de equipos desde el archivo.
     *
     * @return Lista de equipos almacenados
     * @throws IOException Si el archivo no puede leerse
     * @throws ClassNotFoundException Si hay cambios en las clases serializadas
     */
    public List<?> cargarEquipos() throws IOException, ClassNotFoundException {

        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo de persistencia no existe.");
        }

        try (ObjectInputStream entrada =
                     new ObjectInputStream(new FileInputStream(archivo))) {

            return (List<?>) entrada.readObject();
        }
    }

    /**
     * Verifica si el archivo de persistencia existe.
     *
     * @return true si el archivo existe, false en caso contrario
     */
    public boolean existeArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists();
    }
}
