package Controlador;

import Modelo.*;
import Vista.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador principal del sistema.
 * 
 * Responsabilidades:
 * - Inicializar modelos y controladores
 * - Conectar la vista con la lógica del sistema
 * - Controlar el flujo general del programa
 * 
 * Este controlador actúa como orquestador central (Patrón MVC).
 * 
 * @author Juan
 * @version 1.0
 */
public class ControlPrincipal {

    // ===== Vista =====
    private Vista vista;

    // ===== Modelos =====
    private GestionConfiguracion gestionConfig; // En lugar de ArchivoPropiedades
    private ResultadosRAF modeloResultados;      // En lugar de Resultados
    private PersistenciaEquipos persistencia;

    // ===== Controladores =====
    private ControlEquipo controlEquipo;
    private ControlJugador controlJugador;
    private ControlJuego controlJuego;
    private ControlResultados controlResultados;

    /**
     * Constructor principal.
     * Inicializa todo el sistema.
     */
    public ControlPrincipal() {
        inicializarSistema();
        configurarEventos();
    }

    /**
     * Inicializa vista, modelos y controladores.
     */
    private void inicializarSistema() {

        // Vista
        vista = new Vista();

        // Modelos
        this.gestionConfig = new GestionConfiguracion();
        this.persistencia = new PersistenciaEquipos(); // Quita el String de adentro
        this.modeloResultados = new ResultadosRAF();

        // Controladores de modelos
        controlEquipo = new ControlEquipo();
        controlJugador = new ControlJugador();
        controlJuego = new ControlJuego();
        controlResultados = new ControlResultados(modeloResultados);

        vista.setVisible(true);
    }

    /**
     * Configura los eventos de la interfaz.
     */
    private void configurarEventos() {

        // Botón INICIAR
        vista.getBtnIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });

        // Botón CARGAR PROPERTIES
        vista.getBtnCargar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });
    }

    /**
     * Carga los equipos y jugadores desde el archivo persistence.
     */
    private void cargarDatos() {
        try {
            List<Equipo> equipos = persistencia.cargarEquipos();

            controlEquipo.setEquipos(equipos);

            System.out.println("Equipos cargados: " + equipos.size());

        } catch (Exception ex) {
            System.out.println("Error al cargar datos: " + ex.getMessage());
        }
    }

    /**
     * Inicia el flujo del juego.
     */
    private void iniciarJuego() {

        List<Equipo> equipos = controlEquipo.getEquipos();

        if (equipos == null || equipos.isEmpty()) {
            System.out.println("Debe cargar los equipos primero.");
            return;
        }

        controlJuego.iniciarJuego(equipos, 60);

        // Cambiar a pantalla de juego
        vista.mostrarPanel("Juego");
    }
}
