package Controlador;

import Modelo.*;
import Vista.PanelEquipo;
import Vista.PanelJugador;
import Vista.VistaPrincipal;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ControlPrincipal {

    private final VistaPrincipal vista;
    private final GestionConfiguracion gestionConfig;
    private Juego juego; // Variable central del modelo
    private Timer cronometro;
    private int tiempoRestante;

    public ControlPrincipal() {
        this.vista = new VistaPrincipal();
        this.gestionConfig = new GestionConfiguracion();

        asignarEventos();
        cargarIntegrantesAlInicio();
        this.vista.setVisible(true);
    }

    private void asignarEventos() {
        // Ambos botones en la VistaPrincipal apuntan ahora a la misma lógica de inicio
        vista.getBtnCargar().addActionListener(e -> menuCargarEIniciar());
        vista.getPanelJuego().getBtnLanzar().addActionListener(e -> procesoLanzamiento());
    }

    private void menuCargarEIniciar() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Configuración (.properties)", "properties"));

        if (chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            try {
                // 1. Cargar archivo
                List<Equipo> cargados = gestionConfig.leerArchivoConfiguracion(chooser.getSelectedFile().getAbsolutePath());
                
                if (cargados.isEmpty()) {
                    mostrarError("El archivo no contiene equipos válidos.");
                    return;
                }

                // 2. Inicializar lógica de Juego
                int tiempo = Integer.parseInt(vista.getTextFieldTiempo().getText());
                this.juego = new Juego(cargados, tiempo);

                // 3. Sincronizar nombres y cambiar pantalla
                actualizarInterfazNombres();
                vista.mostrarPanel("JUEGO"); 
                
                // 4. Arrancar el primer turno
                prepararTurno();

                JOptionPane.showMessageDialog(vista, "¡Juego Iniciado!");
            } catch (Exception ex) {
                mostrarError("Error al iniciar: " + ex.getMessage());
            }
        }
    }

    private void actualizarInterfazNombres() {
        List<Equipo> eqs = juego.getEquipos();
        for (int i = 0; i < eqs.size(); i++) {
            Equipo eq = eqs.get(i);
            PanelEquipo pnlEq = vista.getPanelJuego().getEquipo(i);
            pnlEq.setNombreEquipo(eq.getNombre());
            
            for (int j = 0; j < eq.getJugadores().size(); j++) {
                String nombreJug = eq.getJugadores().get(j).getNombre();
                pnlEq.getPanelJugador(j).actualizarNombre(nombreJug);
            }
        }
    }

    private void procesoLanzamiento() {
        if (juego == null) return;

        TipoEmbocada result = juego.lanzarBalero();
        juego.registrarResultado(result);
        
        int eIdx = juego.getIndiceEquipoActual();
        int jIdx = juego.getIndiceJugadorActual();
        Jugador j = juego.getJugadorActual();

        // Actualización visual
        PanelJugador pnl = vista.getPanelJuego().getEquipo(eIdx).getPanelJugador(jIdx);
        pnl.actualizarDatos(j.getPuntos(), j.getIntentos());
        
        vista.getPanelJuego().setMensaje("Resultado: " + result.getDescripcion() + " (+" + result.getPuntos() + " pts)");
    }

    private void prepararTurno() {
        int eIdx = juego.getIndiceEquipoActual();
        Jugador j = juego.getJugadorActual();

        // Aplicar Literal g: resaltar equipo activo
        actualizarFocoVisual(eIdx);
        vista.getPanelJuego().setMensaje("Turno actual: " + j.getNombre());
        gestionarCronometro(juego.getTiempoPorJugador());
    }

    private void gestionarCronometro(int seg) {
        if (cronometro != null) cronometro.stop();
        this.tiempoRestante = seg;
        
        cronometro = new Timer(1000, e -> {
            tiempoRestante--;
            vista.getPanelJuego().getLblTiempo().setText(tiempoRestante + "s");
            if (tiempoRestante <= 0) {
                cronometro.stop();
                avanzarTurno();
            }
        });
        cronometro.start();
    }

    private void avanzarTurno() {
        juego.siguienteJugador();
        if (juego.juegoTerminado()) {
            cronometro.stop();
            JOptionPane.showMessageDialog(vista, "¡Juego Terminado!");
            vista.mostrarPanel("MENU");
        } else {
            prepararTurno();
        }
    }

    private void actualizarFocoVisual(int activo) {
        for (int i = 0; i < 3; i++) {
            // Llama al método que creamos en PanelEquipo
            vista.getPanelJuego().getEquipo(i).setTransparencia(i == activo ? 1.0f : 0.3f);
        }
    }

    private void cargarIntegrantesAlInicio() {
        // Ruta relativa estándar para NetBeans
        File f = new File("src/Docs/Integrantes/Integrantes.txt");
        if(!f.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder("EQUIPO DE DESARROLLO:\n");
            String s;
            while ((s = br.readLine()) != null) sb.append("- ").append(s).append("\n");
            JOptionPane.showMessageDialog(vista, sb.toString(), "Créditos", 1);
        } catch (IOException e) { }
    }

    private void mostrarError(String m) {
        JOptionPane.showMessageDialog(vista, m, "Error", 0);
    }
}
