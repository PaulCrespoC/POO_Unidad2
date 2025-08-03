package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ContenidoController;
import model.ContenidoAudiovisual;

/**
 * Vista principal de la aplicación usando Swing
 * Implementa el patrón MVC - Vista
 */
public class MainView extends JFrame {
    private ContenidoController controller;
    
    // Componentes de la interfaz
    private JTable tablaContenidos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<String> comboFiltro;
    private JComboBox<String> comboTipo;
    private JTextArea txtDetalles;
    private JLabel lblEstadisticas;
    
    // Botones
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnCargar;
    private JButton btnGuardar;
    private JButton btnActualizar;

    public MainView() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión de Contenido Audiovisual");
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    public void setController(ContenidoController controller) {
        this.controller = controller;
        actualizarTabla();
        actualizarEstadisticas();
    }

    private void initializeComponents() {
        // Inicializar tabla
        String[] columnas = {"ID", "Tipo", "Título", "Duración (min)", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla no editable
            }
        };
        tablaContenidos = new JTable(modeloTabla);
        tablaContenidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Componentes de búsqueda y filtros
        txtBuscar = new JTextField(20);
        comboFiltro = new JComboBox<>(new String[]{"Título", "Género", "Todos"});
        comboTipo = new JComboBox<>(new String[]{"Todos", "Película", "SerieDeTV", "Documental", "VideoYouTube", "Cortometraje"});
        
        // Área de detalles
        txtDetalles = new JTextArea(8, 30);
        txtDetalles.setEditable(false);
        txtDetalles.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        // Etiqueta de estadísticas
        lblEstadisticas = new JLabel("Estadísticas del sistema");
        
        // Botones
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnCargar = new JButton("Cargar Archivo");
        btnGuardar = new JButton("Guardar Archivo");
        btnActualizar = new JButton("Actualizar");
        
        // Configurar botones
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior - Búsqueda y filtros
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Buscar:"));
        panelSuperior.add(txtBuscar);
        panelSuperior.add(new JLabel("Por:"));
        panelSuperior.add(comboFiltro);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(new JSeparator(SwingConstants.VERTICAL));
        panelSuperior.add(new JLabel("Tipo:"));
        panelSuperior.add(comboTipo);
        panelSuperior.add(btnActualizar);
        
        // Panel central - Tabla y detalles
        JPanel panelCentral = new JPanel(new BorderLayout());
        
        // Tabla con scroll
        JScrollPane scrollTabla = new JScrollPane(tablaContenidos);
        scrollTabla.setPreferredSize(new Dimension(600, 300));
        
        // Panel de detalles
        JPanel panelDetalles = new JPanel(new BorderLayout());
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));
        JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        
        // Dividir tabla y detalles
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, panelDetalles);
        splitPane.setDividerLocation(350);
        panelCentral.add(splitPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(new JSeparator(SwingConstants.VERTICAL));
        panelBotones.add(btnCargar);
        panelBotones.add(btnGuardar);
        
        // Panel inferior - Estadísticas
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        panelInferior.add(lblEstadisticas, BorderLayout.CENTER);
        
        // Agregar paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelInferior, BorderLayout.EAST);
    }

    private void setupEventListeners() {
        // Selección en tabla
        tablaContenidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaContenidos.getSelectedRow();
                boolean haySeleccion = filaSeleccionada >= 0;
                
                btnEditar.setEnabled(haySeleccion);
                btnEliminar.setEnabled(haySeleccion);
                
                if (haySeleccion) {
                    mostrarDetallesContenido(filaSeleccionada);
                } else {
                    txtDetalles.setText("");
                }
            }
        });

        // Botones
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnEditar.addActionListener(e -> mostrarDialogoEditar());
        btnEliminar.addActionListener(e -> eliminarContenidoSeleccionado());
        btnBuscar.addActionListener(e -> buscarContenido());
        btnCargar.addActionListener(e -> cargarArchivo());
        btnGuardar.addActionListener(e -> guardarArchivo());
        btnActualizar.addActionListener(e -> actualizarTabla());
        
        // Filtro por tipo
        comboTipo.addActionListener(e -> filtrarPorTipo());
        
        // Enter en campo de búsqueda
        txtBuscar.addActionListener(e -> buscarContenido());
    }

    private void mostrarDetallesContenido(int fila) {
        if (controller == null) return;
        
        int id = (Integer) modeloTabla.getValueAt(fila, 0);
        ContenidoAudiovisual contenido = controller.obtenerContenidoPorId(id);
        
        if (contenido != null) {
            StringBuilder detalles = new StringBuilder();
            
            // Usar el método mostrarDetalles() existente redirigiendo la salida
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream original = System.out;
            
            System.setOut(ps);
            contenido.mostrarDetalles();
            System.setOut(original);
            
            txtDetalles.setText(baos.toString());
        }
    }

    private void mostrarDialogoAgregar() {
        ContenidoDialog dialog = new ContenidoDialog(this, "Agregar Contenido", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            try {
                ContenidoAudiovisual nuevoContenido = dialog.getContenido();
                controller.agregarContenido(nuevoContenido);
                actualizarTabla();
                actualizarEstadisticas();
                JOptionPane.showMessageDialog(this, "Contenido agregado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar contenido: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarDialogoEditar() {
        int filaSeleccionada = tablaContenidos.getSelectedRow();
        if (filaSeleccionada < 0) return;
        
        int id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        ContenidoAudiovisual contenido = controller.obtenerContenidoPorId(id);
        
        ContenidoDialog dialog = new ContenidoDialog(this, "Editar Contenido", contenido);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            try {
                ContenidoAudiovisual contenidoEditado = dialog.getContenido();
                controller.actualizarContenido(contenidoEditado);
                actualizarTabla();
                actualizarEstadisticas();
                JOptionPane.showMessageDialog(this, "Contenido actualizado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar contenido: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarContenidoSeleccionado() {
        int filaSeleccionada = tablaContenidos.getSelectedRow();
        if (filaSeleccionada < 0) return;
        
        int id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar '" + titulo + "'?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controller.eliminarContenido(id);
                actualizarTabla();
                actualizarEstadisticas();
                txtDetalles.setText("");
                JOptionPane.showMessageDialog(this, "Contenido eliminado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar contenido: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarContenido() {
        if (controller == null) return;
        
        String termino = txtBuscar.getText().trim();
        if (termino.isEmpty()) {
            actualizarTabla();
            return;
        }
        
        String filtroSeleccionado = (String) comboFiltro.getSelectedItem();
        List<ContenidoAudiovisual> resultados;
        
        try {
            switch (filtroSeleccionado) {
                case "Título":
                    resultados = controller.buscarPorTitulo(termino);
                    break;
                case "Género":
                    resultados = controller.filtrarPorGenero(termino);
                    break;
                default:
                    resultados = controller.obtenerTodosLosContenidos();
                    break;
            }
            
            actualizarTablaConResultados(resultados);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarPorTipo() {
        if (controller == null) return;
        
        String tipoSeleccionado = (String) comboTipo.getSelectedItem();
        List<ContenidoAudiovisual> contenidos = controller.obtenerTodosLosContenidos();
        
        if (!"Todos".equals(tipoSeleccionado)) {
            contenidos = contenidos.stream()
                    .filter(c -> c.getClass().getSimpleName().equals(tipoSeleccionado))
                    .collect(java.util.stream.Collectors.toList());
        }
        
        actualizarTablaConResultados(contenidos);
    }

    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                controller.cargarDatosDesdeArchivo(rutaArchivo);
                actualizarTabla();
                actualizarEstadisticas();
                JOptionPane.showMessageDialog(this, "Archivo cargado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar archivo: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                if (!rutaArchivo.endsWith(".csv")) {
                    rutaArchivo += ".csv";
                }
                controller.guardarDatosEnArchivo(rutaArchivo);
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void actualizarTabla() {
        if (controller == null) return;
        
        List<ContenidoAudiovisual> contenidos = controller.obtenerTodosLosContenidos();
        actualizarTablaConResultados(contenidos);
    }

    private void actualizarTablaConResultados(List<ContenidoAudiovisual> contenidos) {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        for (ContenidoAudiovisual contenido : contenidos) {
            Object[] fila = {
                contenido.getId(),
                contenido.getClass().getSimpleName(),
                contenido.getTitulo(),
                contenido.getDuracionEnMinutos(),
                contenido.getGenero()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void actualizarEstadisticas() {
        if (controller == null) return;
        
        String estadisticas = controller.obtenerEstadisticas();
        lblEstadisticas.setText("<html>" + estadisticas.replace("\n", "<br>") + "</html>");
    }
}