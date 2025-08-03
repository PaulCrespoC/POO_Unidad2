import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller.ContenidoController;
import interfaces.IContenidoRepository;
import interfaces.IContenidoService;
import interfaces.IFileHandler;
import model.ContenidoAudiovisual;
import service.ContenidoRepository;
import service.ContenidoService;
import service.FileHandlerService;
import view.MainView;

/**
 * Clase principal de la aplicación
 * Configura e inicializa todo el sistema siguiendo el patrón MVC
 * Aplica Dependency Injection para configurar las dependencias
 */
public class MainApplication {
    private static final String RUTA_ARCHIVO_DATOS = "data/contenidos.csv";
    private static final String DIRECTORIO_DATOS = "data";

    public static void main(String[] args) {
        // Configurar Look and Feel del sistema (opcional)
        // Comentado por compatibilidad con diferentes versiones de Java

        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                crearYMostrarGUI();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al inicializar la aplicación: " + e.getMessage(),
                    "Error Fatal", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    private static void crearYMostrarGUI() {
        // Crear directorio de datos si no existe
        crearDirectorioDatos();

        // Configurar dependencias usando Dependency Injection
        IFileHandler<ContenidoAudiovisual> fileHandler = new FileHandlerService();
        IContenidoRepository repository = new ContenidoRepository(fileHandler);
        IContenidoService contenidoService = new ContenidoService(repository, RUTA_ARCHIVO_DATOS);

        // Crear controlador
        ContenidoController controller = new ContenidoController(contenidoService);

        // Crear vista principal
        MainView mainView = new MainView();
        
        // Conectar controlador y vista
        controller.setMainView(mainView);

        // Configurar el cierre de la aplicación
        mainView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarAplicacion(controller, mainView);
            }
        });

        // Crear archivo de ejemplo si no existen datos
        crearDatosEjemplo(fileHandler);

        // Inicializar el controlador (carga datos)
        controller.inicializar();

        // Mostrar la ventana principal
        mainView.setVisible(true);

        System.out.println("Aplicación iniciada correctamente");
        System.out.println("Archivo de datos: " + RUTA_ARCHIVO_DATOS);
    }

    private static void crearDirectorioDatos() {
        File directorio = new File(DIRECTORIO_DATOS);
        if (!directorio.exists()) {
            boolean creado = directorio.mkdirs();
            if (creado) {
                System.out.println("Directorio de datos creado: " + DIRECTORIO_DATOS);
            } else {
                System.err.println("No se pudo crear el directorio de datos: " + DIRECTORIO_DATOS);
            }
        }
    }

    private static void crearDatosEjemplo(IFileHandler<ContenidoAudiovisual> fileHandler) {
        File archivoDatos = new File(RUTA_ARCHIVO_DATOS);
        
        if (!archivoDatos.exists()) {
            try {
                if (fileHandler instanceof FileHandlerService) {
                    FileHandlerService fhs = (FileHandlerService) fileHandler;
                    fhs.crearArchivoEjemplo(RUTA_ARCHIVO_DATOS);
                    System.out.println("Archivo de ejemplo creado: " + RUTA_ARCHIVO_DATOS);
                }
            } catch (Exception e) {
                System.err.println("Error al crear archivo de ejemplo: " + e.getMessage());
            }
        }
    }

    private static void cerrarAplicacion(ContenidoController controller, MainView mainView) {
        int confirmacion = JOptionPane.showConfirmDialog(
            mainView,
            "¿Desea guardar los datos antes de salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        switch (confirmacion) {
            case JOptionPane.YES_OPTION:
                try {
                    controller.cerrarAplicacion();
                    JOptionPane.showMessageDialog(mainView, 
                        "Datos guardados exitosamente", 
                        "Guardado", 
                        JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mainView, 
                        "Error al guardar datos: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
                break;
                
            case JOptionPane.NO_OPTION:
                System.exit(0);
                break;
                
            case JOptionPane.CANCEL_OPTION:
            default:
                // No hacer nada, continuar con la aplicación
                break;
        }
    }

    /**
     * Método para obtener información de la aplicación
     */
    public static String getInfoAplicacion() {
        return """
               Sistema de Gestión de Contenido Audiovisual
               Versión: 2.0
               Patrón: MVC (Model-View-Controller)
               Principios: SOLID
               Interfaz: Java Swing
               Persistencia: Archivos CSV
               """;
    }

    /**
     * Método para obtener la ruta del archivo de datos
     */
    public static String getRutaArchivoDatos() {
        return RUTA_ARCHIVO_DATOS;
    }
}