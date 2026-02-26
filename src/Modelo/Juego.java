package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa la lógica principal del juego del balero.
 *
 * Esta clase es el núcleo del modelo. Se encarga de:
 * - Gestionar los equipos participantes.
 * - Controlar los turnos de los jugadores.
 * - Generar embocadas aleatorias.
 * - Sumar puntos e intentos.
 * - Determinar el equipo ganador.
 *
 * IMPORTANTE:
 * Esta clase NO debe contener:
 * - System.out
 * - JOptionPane
 * - Componentes Swing
 * - Entrada de datos
 *
 * Solo contiene lógica del negocio.
 *
 * @author
 */
public class Juego {

    /* ============================
       ATRIBUTOS
       ============================ */

    /**
     * Lista de equipos inscritos en la competencia.
     */
    private List<Equipo> equipos;

    /**
     * Tiempo total asignado a cada equipo.
     * Este tiempo será dividido entre los 3 jugadores.
     */
    private int tiempoPorEquipo;

    /**
     * Índice que indica cuál equipo está jugando actualmente.
     */
    private int indiceEquipoActual;

    /**
     * Índice que indica qué jugador del equipo actual está jugando.
     */
    private int indiceJugadorActual;

    /**
     * Objeto Random para generar embocadas aleatorias.
     */
    private Random random;


    /* ============================
       CONSTRUCTOR
       ============================ */

    /**
     * Constructor del juego.
     *
     * Inicializa la lista de equipos y configura los índices
     * en la posición inicial.
     *
     * @param tiempoPorEquipo Tiempo total asignado a cada equipo.
     */
    public Juego(int tiempoPorEquipo) {
        this.tiempoPorEquipo = tiempoPorEquipo;
        this.equipos = new ArrayList<>();
        this.indiceEquipoActual = 0;
        this.indiceJugadorActual = 0;
        this.random = new Random();
    }


    /* ============================
       MÉTODOS DE GESTIÓN DE EQUIPOS
       ============================ */

    /**
     * Agrega un equipo a la competencia.
     *
     * @param equipo Equipo a agregar.
     */
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    /**
     * Retorna la lista de equipos inscritos.
     *
     * @return lista de equipos.
     */
    public List<Equipo> getEquipos() {
        return equipos;
    }


    /* ============================
       MÉTODOS DE JUEGO
       ============================ */

    /**
     * Genera una embocada aleatoria usando el ENUM TipoEmbocada.
     *
     * Se obtiene el arreglo completo de valores del ENUM
     * y se selecciona una posición aleatoria.
     *
     * @return TipoEmbocada generado aleatoriamente.
     */
    public TipoEmbocada generarEmbocada() {

        // Obtener todos los valores del ENUM
        TipoEmbocada[] tipos = TipoEmbocada.values();

        // Generar una posición aleatoria válida
        int posicion = random.nextInt(tipos.length);

        // Retornar el tipo de embocada seleccionado
        return tipos[posicion];
    }


    /**
     * Ejecuta un turno del jugador actual.
     *
     * Lógica:
     * 1. Se obtiene el equipo actual.
     * 2. Se obtiene el jugador actual.
     * 3. Se genera una embocada aleatoria.
     * 4. Se incrementa el número de intentos.
     * 5. Se suman los puntos obtenidos.
     *
     * IMPORTANTE:
     * No se valida FALLA porque el ENUM FALLA tiene 0 puntos.
     *
     * @return TipoEmbocada obtenida en el turno.
     */
    public TipoEmbocada jugarTurno() {

        // Obtener el equipo actual
        Equipo equipoActual = equipos.get(indiceEquipoActual);

        // Obtener el jugador actual dentro del equipo
        Jugador jugadorActual = equipoActual
                .getJugadores()
                .get(indiceJugadorActual);

        // Generar una embocada aleatoria
        TipoEmbocada tipo = generarEmbocada();

        // Registrar intento
        jugadorActual.incrementarIntento();

        // Sumar puntos (si es FALLA suma 0)
        jugadorActual.sumarPuntos(tipo.getPuntos());

        return tipo;
    }


    /**
     * Cambia al siguiente jugador dentro del equipo.
     *
     * Cuando llega al jugador 3 (índice 2),
     * vuelve al jugador 0.
     */
    public void cambiarJugador() {

        indiceJugadorActual++;

        if (indiceJugadorActual >= 3) {
            indiceJugadorActual = 0;
        }
    }


    /**
     * Cambia al siguiente equipo.
     *
     * Si llega al último equipo,
     * vuelve al primero.
     */
    public void cambiarEquipo() {

        indiceEquipoActual++;

        if (indiceEquipoActual >= equipos.size()) {
            indiceEquipoActual = 0;
        }
    }


    /**
     * Determina el equipo ganador según las reglas:
     *
     * 1. Gana el equipo con mayor puntaje total.
     * 2. Si hay empate en puntaje,
     *    gana el equipo con mayor cantidad de intentos.
     *
     * @return Equipo ganador.
     */
    public Equipo determinarGanador() {

        // Se toma como referencia el primer equipo
        Equipo ganador = equipos.get(0);

        for (Equipo equipo : equipos) {

            int puntajeEquipo = equipo.calcularPuntajeTotal();
            int puntajeGanador = ganador.calcularPuntajeTotal();

            // Caso 1: Mayor puntaje
            if (puntajeEquipo > puntajeGanador) {
                ganador = equipo;
            }

            // Caso 2: Empate en puntaje → comparar intentos
            else if (puntajeEquipo == puntajeGanador) {

                int intentosEquipo = equipo.calcularIntentosTotales();
                int intentosGanador = ganador.calcularIntentosTotales();

                if (intentosEquipo > intentosGanador) {
                    ganador = equipo;
                }
            }
        }

        return ganador;
    }


    /* ============================
       GETTERS IMPORTANTES
       ============================ */

    public int getTiempoPorEquipo() {
        return tiempoPorEquipo;
    }

    public int getIndiceEquipoActual() {
        return indiceEquipoActual;
    }

    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }
}