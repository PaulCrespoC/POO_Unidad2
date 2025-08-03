package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.ContenidoAudiovisual;
import model.Cortometraje;
import model.Documental;
import model.Pelicula;
import model.SerieDeTV;
import model.VideoYouTube;

/**
 * Diálogo para agregar o editar contenido audiovisual
 * Implementa el patrón MVC - Vista
 */
public class ContenidoDialog extends JDialog {
    private ContenidoAudiovisual contenido;
    private boolean confirmado = false;
    
    // Componentes comunes
    private JComboBox<String> comboTipo;
    private JTextField txtTitulo;
    private JTextField txtDuracion;
    private JTextField txtGenero;
    
    // Componentes específicos por tipo
    private JTextField txtEstudio; // Película
    private JTextField txtTemporadas; // Serie
    private JTextField txtTema; // Documental
    private JTextField txtCanal; // YouTube
    private JTextField txtVisualizaciones; // YouTube
    private JTextField txtLikes; // YouTube
    private JTextField txtFechaPublicacion; // YouTube
    private JComboBox<String> comboCalidad; // YouTube
    private JTextField txtDirector; // Cortometraje
    private JTextField txtFestival; // Cortometraje
    private JCheckBox chkEstudiantil; // Cortometraje
    private JComboBox<String> comboTecnica; // Cortometraje
    private JTextField txtPresupuesto; // Cortometraje
    
    private JPanel panelEspecifico;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public ContenidoDialog(Frame parent, String titulo, ContenidoAudiovisual contenidoExistente) {
        super(parent, titulo, true);
        this.contenido = contenidoExistente;
        
        initializeComponents();
        setupLayout();
        setupEventListeners();
        
        if (contenidoExistente != null) {
            cargarDatosExistentes();
        }
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initializeComponents() {
        // Componentes comunes
        comboTipo = new JComboBox<>(new String[]{"Pelicula", "SerieDeTV", "Documental", "VideoYouTube", "Cortometraje"});
        txtTitulo = new JTextField(20);
        txtDuracion = new JTextField(10);
        txtGenero = new JTextField(15);
        
        // Componentes específicos
        txtEstudio = new JTextField(20);
        txtTemporadas = new JTextField(10);
        txtTema = new JTextField(20);
        txtCanal = new JTextField(20);
        txtVisualizaciones = new JTextField(10);
        txtLikes = new JTextField(10);
        txtFechaPublicacion = new JTextField(15);
        comboCalidad = new JComboBox<>(new String[]{"480p", "720p", "1080p", "1440p", "4K"});
        txtDirector = new JTextField(20);
        txtFestival = new JTextField(20);
        chkEstudiantil = new JCheckBox("Es estudiantil");
        comboTecnica = new JComboBox<>(new String[]{"Digital", "Analógico", "Mixto"});
        txtPresupuesto = new JTextField(10);
        
        // Panel específico que cambiará según el tipo
        panelEspecifico = new JPanel(new GridBagLayout());
        
        // Botones
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        
        // Configurar valores por defecto
        comboCalidad.setSelectedItem("1080p");
        comboTecnica.setSelectedItem("Digital");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con datos comunes
        JPanel panelComun = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tipo
        gbc.gridx = 0; gbc.gridy = 0;
        panelComun.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        panelComun.add(comboTipo, gbc);
        
        // Título
        gbc.gridx = 0; gbc.gridy = 1;
        panelComun.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        panelComun.add(txtTitulo, gbc);
        
        // Duración
        gbc.gridx = 0; gbc.gridy = 2;
        panelComun.add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        panelComun.add(txtDuracion, gbc);
        
        // Género
        gbc.gridx = 0; gbc.gridy = 3;
        panelComun.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        panelComun.add(txtGenero, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        // Panel con scroll para campos específicos
        JScrollPane scrollEspecifico = new JScrollPane(panelEspecifico);
        scrollEspecifico.setBorder(BorderFactory.createTitledBorder("Campos Específicos"));
        scrollEspecifico.setPreferredSize(new Dimension(450, 200));
        
        // Agregar paneles
        add(panelComun, BorderLayout.NORTH);
        add(scrollEspecifico, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Mostrar campos específicos iniciales
        actualizarCamposEspecificos();
    }

    private void setupEventListeners() {
        comboTipo.addActionListener(e -> actualizarCamposEspecificos());
        
        btnAceptar.addActionListener(e -> {
            if (validarDatos()) {
                crearContenido();
                confirmado = true;
                dispose();
            }
        });
        
        btnCancelar.addActionListener(e -> {
            confirmado = false;
            dispose();
        });
    }

    private void actualizarCamposEspecificos() {
        panelEspecifico.removeAll();
        
        String tipoSeleccionado = (String) comboTipo.getSelectedItem();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        switch (tipoSeleccionado) {
            case "Pelicula":
                gbc.gridx = 0; gbc.gridy = 0;
                panelEspecifico.add(new JLabel("Estudio:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtEstudio, gbc);
                break;
                
            case "SerieDeTV":
                gbc.gridx = 0; gbc.gridy = 0;
                panelEspecifico.add(new JLabel("Número de temporadas:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtTemporadas, gbc);
                break;
                
            case "Documental":
                gbc.gridx = 0; gbc.gridy = 0;
                panelEspecifico.add(new JLabel("Tema:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtTema, gbc);
                break;
                
            case "VideoYouTube":
                gbc.gridx = 0; gbc.gridy = 0;
                panelEspecifico.add(new JLabel("Canal:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtCanal, gbc);
                
                gbc.gridx = 0; gbc.gridy = 1;
                panelEspecifico.add(new JLabel("Visualizaciones:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtVisualizaciones, gbc);
                
                gbc.gridx = 0; gbc.gridy = 2;
                panelEspecifico.add(new JLabel("Likes:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtLikes, gbc);
                
                gbc.gridx = 0; gbc.gridy = 3;
                panelEspecifico.add(new JLabel("Fecha publicación:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtFechaPublicacion, gbc);
                
                gbc.gridx = 0; gbc.gridy = 4;
                panelEspecifico.add(new JLabel("Calidad:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(comboCalidad, gbc);
                break;
                
            case "Cortometraje":
                gbc.gridx = 0; gbc.gridy = 0;
                panelEspecifico.add(new JLabel("Director:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtDirector, gbc);
                
                gbc.gridx = 0; gbc.gridy = 1;
                panelEspecifico.add(new JLabel("Festival:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtFestival, gbc);
                
                gbc.gridx = 0; gbc.gridy = 2;
                panelEspecifico.add(chkEstudiantil, gbc);
                
                gbc.gridx = 0; gbc.gridy = 3;
                panelEspecifico.add(new JLabel("Técnica:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(comboTecnica, gbc);
                
                gbc.gridx = 0; gbc.gridy = 4;
                panelEspecifico.add(new JLabel("Presupuesto:"), gbc);
                gbc.gridx = 1;
                panelEspecifico.add(txtPresupuesto, gbc);
                break;
        }
        
        panelEspecifico.revalidate();
        panelEspecifico.repaint();
    }

    private void cargarDatosExistentes() {
        if (contenido == null) return;
        
        // Deshabilitar cambio de tipo si estamos editando
        comboTipo.setEnabled(false);
        
        // Cargar datos comunes
        txtTitulo.setText(contenido.getTitulo());
        txtDuracion.setText(String.valueOf(contenido.getDuracionEnMinutos()));
        txtGenero.setText(contenido.getGenero());
        
        // Cargar datos específicos según el tipo
        if (contenido instanceof Pelicula) {
            comboTipo.setSelectedItem("Pelicula");
            Pelicula p = (Pelicula) contenido;
            txtEstudio.setText(p.getEstudio());
            
        } else if (contenido instanceof SerieDeTV) {
            comboTipo.setSelectedItem("SerieDeTV");
            SerieDeTV s = (SerieDeTV) contenido;
            txtTemporadas.setText(String.valueOf(s.getNumeroTemporadas()));
            
        } else if (contenido instanceof Documental) {
            comboTipo.setSelectedItem("Documental");
            Documental d = (Documental) contenido;
            txtTema.setText(d.getTema());
            
        } else if (contenido instanceof VideoYouTube) {
            comboTipo.setSelectedItem("VideoYouTube");
            VideoYouTube v = (VideoYouTube) contenido;
            txtCanal.setText(v.getCanal());
            txtVisualizaciones.setText(String.valueOf(v.getVisualizaciones()));
            txtLikes.setText(String.valueOf(v.getLikes()));
            txtFechaPublicacion.setText(v.getFechaPublicacion());
            comboCalidad.setSelectedItem(v.getCalidad());
            
        } else if (contenido instanceof Cortometraje) {
            comboTipo.setSelectedItem("Cortometraje");
            Cortometraje c = (Cortometraje) contenido;
            txtDirector.setText(c.getDirector());
            txtFestival.setText(c.getFestival());
            chkEstudiantil.setSelected(c.isEsEstudiantil());
            comboTecnica.setSelectedItem(c.getTecnicaFilmacion());
            txtPresupuesto.setText(String.valueOf(c.getPresupuesto()));
        }
        
        actualizarCamposEspecificos();
    }

    private boolean validarDatos() {
        // Validar campos comunes
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            if (duracion <= 0) {
                JOptionPane.showMessageDialog(this, "La duración debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (txtGenero.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El género es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar campos específicos según el tipo
        String tipo = (String) comboTipo.getSelectedItem();
        return validarCamposEspecificos(tipo);
    }

    private boolean validarCamposEspecificos(String tipo) {
        switch (tipo) {
            case "Pelicula":
                if (txtEstudio.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El estudio es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
                
            case "SerieDeTV":
                try {
                    int temporadas = Integer.parseInt(txtTemporadas.getText().trim());
                    if (temporadas <= 0) {
                        JOptionPane.showMessageDialog(this, "El número de temporadas debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El número de temporadas debe ser un entero", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
                
            case "Documental":
                if (txtTema.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El tema es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
                
            case "VideoYouTube":
                if (txtCanal.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El canal es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                try {
                    Integer.parseInt(txtVisualizaciones.getText().trim());
                    Integer.parseInt(txtLikes.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Visualizaciones y likes deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
                
            case "Cortometraje":
                if (txtDirector.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El director es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                try {
                    Integer.parseInt(txtPresupuesto.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El presupuesto debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
        }
        return true;
    }

    private void crearContenido() {
        String titulo = txtTitulo.getText().trim();
        int duracion = Integer.parseInt(txtDuracion.getText().trim());
        String genero = txtGenero.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        
        switch (tipo) {
            case "Pelicula":
                String estudio = txtEstudio.getText().trim();
                if (contenido instanceof Pelicula) {
                    Pelicula p = (Pelicula) contenido;
                    p.setTitulo(titulo);
                    p.setDuracionEnMinutos(duracion);
                    p.setGenero(genero);
                    p.setEstudio(estudio);
                } else {
                    contenido = new Pelicula(titulo, duracion, genero, estudio);
                }
                break;
                
            case "SerieDeTV":
                int temporadas = Integer.parseInt(txtTemporadas.getText().trim());
                if (contenido instanceof SerieDeTV) {
                    SerieDeTV s = (SerieDeTV) contenido;
                    s.setTitulo(titulo);
                    s.setDuracionEnMinutos(duracion);
                    s.setGenero(genero);
                    s.setNumeroTemporadas(temporadas);
                } else {
                    contenido = new SerieDeTV(titulo, duracion, genero, temporadas);
                }
                break;
                
            case "Documental":
                String tema = txtTema.getText().trim();
                if (contenido instanceof Documental) {
                    Documental d = (Documental) contenido;
                    d.setTitulo(titulo);
                    d.setDuracionEnMinutos(duracion);
                    d.setGenero(genero);
                    d.setTema(tema);
                } else {
                    contenido = new Documental(titulo, duracion, genero, tema);
                }
                break;
                
            case "VideoYouTube":
                String canal = txtCanal.getText().trim();
                int visualizaciones = Integer.parseInt(txtVisualizaciones.getText().trim());
                int likes = Integer.parseInt(txtLikes.getText().trim());
                String fechaPublicacion = txtFechaPublicacion.getText().trim();
                String calidad = (String) comboCalidad.getSelectedItem();
                
                if (contenido instanceof VideoYouTube) {
                    VideoYouTube v = (VideoYouTube) contenido;
                    v.setTitulo(titulo);
                    v.setDuracionEnMinutos(duracion);
                    v.setGenero(genero);
                    v.setCanal(canal);
                    v.setVisualizaciones(visualizaciones);
                    v.setLikes(likes);
                    v.setFechaPublicacion(fechaPublicacion);
                    v.setCalidad(calidad);
                } else {
                    contenido = new VideoYouTube(titulo, duracion, genero, canal, 
                                               visualizaciones, likes, fechaPublicacion, calidad);
                }
                break;
                
            case "Cortometraje":
                String director = txtDirector.getText().trim();
                String festival = txtFestival.getText().trim();
                boolean esEstudiantil = chkEstudiantil.isSelected();
                String tecnica = (String) comboTecnica.getSelectedItem();
                int presupuesto = Integer.parseInt(txtPresupuesto.getText().trim());
                
                if (contenido instanceof Cortometraje) {
                    Cortometraje c = (Cortometraje) contenido;
                    c.setTitulo(titulo);
                    c.setDuracionEnMinutos(duracion);
                    c.setGenero(genero);
                    c.setDirector(director);
                    c.setFestival(festival);
                    c.setEsEstudiantil(esEstudiantil);
                    c.setTecnicaFilmacion(tecnica);
                    c.setPresupuesto(presupuesto);
                } else {
                    contenido = new Cortometraje(titulo, duracion, genero, director, 
                                               festival, esEstudiantil, tecnica, presupuesto);
                }
                break;
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public ContenidoAudiovisual getContenido() {
        return contenido;
    }
}