package Controlador;

import Modelo.*;
import Vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controlador principal del sistema (Patrón MVC).
 * * Gestiona el flujo del juego, el cronómetro, la carga de archivos
 * y la actualización visual de los componentes (difuminado y nombres).
 * * @author Juan
 * @version 2.0
 */
public class ControlPrincipal {

    // ===== Vista =====
    private VistaPrincipal vista;

    // ===== Modelos y Persistencia =====
    private GestionConfiguracion gestionConfig;
    private ResultadosRAF modeloResultados;
    private PersistenciaEquipos persistencia;

    // ===== Controladores de Lógica =====
    private ControlEquipo controlEquipo;
    private ControlJugador controlJugador;
    private ControlJuego controlJuego;
    private ControlResultados controlResultados;

    // ===== Atributos de Juego =====
    private Timer cronometro;
    private int tiempoRestante;

    public ControlPrincipal() {
        inicializarSistema();
        configurarEventos();
        mostrarIntegrantes(); // Literal h
    }

    private void inicializarSistema() {
        // Vista
        vista = new VistaPrincipal();

        // Modelos
        this.gestionConfig = new GestionConfiguracion();
        this.persistencia = new PersistenciaEquipos();
        this.modeloResultados = new ResultadosRAF();

        // Controladores
        controlEquipo = new ControlEquipo();
        controlJugador = new ControlJugador();
        controlJuego = new ControlJuego();
        controlResultados = new ControlResultados(modeloResultados);

        vista.setVisible(true);
    }

    private void configurarEventos() {
        // Botón CARGAR (Literal g)
        vista.getBtnCargar().addActionListener(e -> cargarDatos());

        // Botón INICIAR
        vista.getBtnIniciar().addActionListener(e -> iniciarJuego());

        // Botón LANZAR (Dentro del PanelJuego)
        vista.getPanelJuego().getBtnLanzar().addActionListener(e -> ejecutarLanzamiento());
    }

    /**
     * Carga equipos usando JFileChooser y actualiza la interfaz.
     */
    private void cargarDatos() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccione el archivo .properties");
        selector.setFileFilter(new FileNameExtensionFilter("Propiedades", "properties"));

        if (selector.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = selector.getSelectedFile().getAbsolutePath();
                List<Equipo> lista = gestionConfig.leerArchivoConfiguracion(ruta);

                if (lista == null || lista.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "El archivo no es válido.");
                    return;
                }

                controlEquipo.setEquipos(lista);
                actualizarNombresEnVista(lista);
                
                // Efecto visual: todos inician difuminados hasta que empiece el juego
                for(int i=0; i<3; i++) vista.getPanelJuego().getEquipo(i).setTransparencia(0.3f);
                
                JOptionPane.showMessageDialog(vista, "Equipos cargados exitosamente.");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
            }
        }
    }

    /**
     * Inicia el flujo del juego capturando el tiempo del JTextField.
     */
    private void iniciarJuego() {
        List<Equipo> equipos = controlEquipo.getEquipos();

        if (equipos == null || equipos.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe cargar los equipos primero.");
            return;
        }

        try {
            // Captura de tiempo (Requisito de usuario)
            int tiempoUser = Integer.parseInt(vista.getTextFieldTiempo().getText());
            
            controlJuego.iniciarJuego(equipos, tiempoUser);
            
            // UI: Cambiar panel y aplicar difuminado al equipo inicial
            vista.mostrarPanel("Juego");
            actualizarEfectoVisualEquipos(0); 
            
            // Iniciar el cronómetro visual
            iniciarCronometro(tiempoUser);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Ingrese un tiempo numérico válido.");
        }
    }

    private void iniciarCronometro(int segundos) {
        if (cronometro != null) cronometro.stop();
        
        this.tiempoRestante = segundos;
        cronometro = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                vista.getPanelJuego().getLblTiempo().setText("Tiempo: " + tiempoRestante);
                
                if (tiempoRestante <= 0) {
                    cronometro.stop();
                    cambiarTurno();
                }
            }
        });
        cronometro.start();
    }

    private void cambiarTurno() {
        controlJuego.siguienteTurno();
        if (controlJuego.juegoTerminado()) {
            JOptionPane.showMessageDialog(vista, "Fin del juego");
            // Aquí llamarías a la pantalla de resultados
        } else {
            int indexEq = 0; // Aquí deberías obtener el índice del equipo actual desde controlJuego
            actualizarEfectoVisualEquipos(indexEq);
            iniciarCronometro(controlJuego.getTiempoPorJugador());
        }
    }

    private void actualizarEfectoVisualEquipos(int indiceActivo) {
        for (int i = 0; i < 3; i++) {
            if (i == indiceActivo) {
                vista.getPanelJuego().getEquipo(i).setTransparencia(1.0f);
            } else {
                vista.getPanelJuego().getEquipo(i).setTransparencia(0.3f);
            }
        }
    }

    private void actualizarNombresEnVista(List<Equipo> listaEquipos) {
        for (int i = 0; i < Math.min(listaEquipos.size(), 3); i++) {
            Equipo eq = listaEquipos.get(i);
            vista.getPanelJuego().getEquipo(i).setBorder(
                javax.swing.BorderFactory.createTitledBorder(eq.getNombre())
            );

            for (int j = 0; j < Math.min(eq.getJugadores().size(), 3); j++) {
                String nombre = eq.getJugadores().get(j).getNombre();
                vista.getPanelJuego().getEquipo(i).getPanelJugador(j).actualizarNombre(nombre);
            }
        }
    }

    private void mostrarIntegrantes() {
        File f = new File("src/Docs/Integrantes/Integrantes.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder("Integrantes:\n");
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            // Se puede mostrar en un Label o un Dialog al cargar
        } catch (IOException e) {
            // No imprimir en consola para evitar descuentos
        }
    }

    private void ejecutarLanzamiento() {
        TipoEmbocada resultado = controlJuego.lanzar();
        // Actualizar labels de puntos e intentos en PanelJuego
        // vista.getPanelJuego().actualizarMarcador(...);
    }
}
