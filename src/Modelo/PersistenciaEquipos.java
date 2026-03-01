package Modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de la persistencia de objetos mediante serialización binaria.
 * * Responsabilidades:
 * - Guardar el estado completo de la lista de equipos (incluyendo jugadores).
 * - Recuperar los datos de sesiones anteriores.
 * * Pertenece a la capa Modelo (MVC).
 * * @author Juan
 * @version 2.1
 */
public class PersistenciaEquipos {

    private static final String RUTA_ARCHIVO = "equipos.persistence";

    /**
     * Guarda la lista de equipos en un archivo binario.
     * Utiliza try-with-resources para asegurar el cierre del flujo.
     *
     * @param equipos Lista de objetos Equipo a serializar.
     * @throws IOException Si hay errores de escritura en el disco.
     */
    public void guardarEquipos(List<Equipo> equipos) throws IOException {
        try (ObjectOutputStream salida = 
                new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            salida.writeObject(equipos);
        }
    }

    /**
     * Carga los equipos desde el archivo binario.
     * Maneja el caso de archivo inexistente devolviendo una lista vacía.
     *
     * @return List de equipos recuperados.
     * @throws IOException Si hay error de lectura.
     * @throws ClassNotFoundException Si la estructura de las clases ha cambiado 
     * y no coincide con el archivo.
     */
    @SuppressWarnings("unchecked")
    public List<Equipo> cargarEquipos() throws IOException, ClassNotFoundException {
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream entrada = 
                new ObjectInputStream(new FileInputStream(archivo))) {
            // Se realiza el cast seguro tras la validación de existencia
            return (List<Equipo>) entrada.readObject();
        }
    }

    /**
     * Comprueba si existe un estado guardado previamente.
     * @return true si el archivo de persistencia se encuentra en la ruta.
     */
    public boolean existeArchivo() {
        return new File(RUTA_ARCHIVO).exists();
    }
    
    /**
     * Opcional: Borra el archivo de persistencia si se desea reiniciar el torneo.
     * @return true si el archivo fue eliminado exitosamente.
     */
    public boolean borrarPersistencia() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists() && archivo.delete();
    }
}