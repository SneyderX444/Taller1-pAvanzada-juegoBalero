package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase del modelo encargada de la persistencia de los equipos del sistema.
 *
 * Responsabilidades:
 * - Guardar los equipos y sus jugadores en un archivo binario
 * - Cargar los equipos desde el archivo
 * - Verificar si el archivo de persistencia existe
 *
 * Archivo utilizado: equipos.persistence
 *
 * Importante:
 * Las clases Equipo y Jugador deben implementar Serializable.
 *
 * Esta clase pertenece a la capa Modelo (MVC).
 *
 * @author Juan
 * @version 2.0
 */
public class PersistenciaEquipos {

    private static final String RUTA_ARCHIVO = "equipos.persistence";

    /**
     * Guarda la lista de equipos en el archivo de persistencia.
     *
     * @param equipos lista de equipos a guardar
     * @throws IOException si ocurre un error durante la escritura
     */
    public void guardarEquipos(List<Equipo> equipos) throws IOException {
        try (ObjectOutputStream salida =
                     new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {

            salida.writeObject(equipos);
        }
    }

    /**
     * Carga la lista de equipos desde el archivo de persistencia.
     *
     * @return lista de equipos almacenados.
     *         Si el archivo no existe, retorna una lista vacía.
     * @throws IOException si ocurre un error de lectura
     * @throws ClassNotFoundException si cambia la estructura de las clases serializadas
     */
    @SuppressWarnings("unchecked")
    public List<Equipo> cargarEquipos() throws IOException, ClassNotFoundException {

        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return new ArrayList<>(); // Evita errores si es la primera ejecución
        }

        try (ObjectInputStream entrada =
                     new ObjectInputStream(new FileInputStream(archivo))) {

            return (List<Equipo>) entrada.readObject();
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