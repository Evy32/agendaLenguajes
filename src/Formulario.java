import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.stream.IntStream;

public class Formulario extends JFrame {
    private static final Color FONDO = new Color(17, 20, 27);
    private static final Color SUPERFICIE = new Color(28, 33, 43);
    private static final Color CAMPO = new Color(21, 26, 35);
    private static final Color TEXTO = new Color(235, 239, 246);
    private static final Color SECUNDARIO = new Color(151, 162, 180);
    private static final Color BORDE = new Color(56, 65, 80);
    private static final Color ACENTO = new Color(116, 157, 255);

    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    private final JTextField textField3 = new JTextField();
    private final JTextField textField4 = new JTextField();
    private final JButton insertarButton = new JButton("Insertar");
    private final JButton limpiarButton = new JButton("Limpiar");
    private final JButton buscarButton = new JButton("Buscar");
    private final JButton eliminarButton = new JButton("Eliminar");
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"CÉDULA", "NOMBRE", "TELÉFONO", "CARRERA"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);
    private final JLabel cantidadRegistros = new JLabel("0 registros");

    public Formulario() {
        super("Agenda de estudiantes");
        construirVentana();
        conectarBotones();
    }

    private void construirVentana() {
        var contenido = new JPanel(new BorderLayout(0, 22)) {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                var g = (Graphics2D) graphics.create();
                g.setPaint(new GradientPaint(0, 0, new Color(24, 29, 40),
                        getWidth(), getHeight(), FONDO));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setPaint(new RadialGradientPaint(
                        new Point2D.Float(getWidth() * 0.85f, getHeight() * 0.08f),
                        Math.max(getWidth(), getHeight()) * 0.55f,
                        new float[]{0f, 1f},
                        new Color[]{new Color(76, 112, 190, 48), new Color(76, 112, 190, 0)}));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.dispose();
            }
        };
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(30, 34, 30, 34));

        var encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);
        var titulo = new JLabel("Agenda de estudiantes");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(TEXTO);
        var subtitulo = new JLabel("Administra la información académica en un solo lugar");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(SECUNDARIO);
        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(6));
        encabezado.add(subtitulo);

        var formulario = new JPanel(new BorderLayout(0, 18)) {
            @Override
            protected void paintComponent(Graphics graphics) {
                var g = (Graphics2D) graphics.create();
                g.setColor(new Color(30, 36, 47, 238));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g.dispose();
            }
        };
        formulario.setOpaque(false);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE), new EmptyBorder(20, 22, 18, 22)));
        var campos = new JPanel(new GridLayout(2, 2, 18, 16));
        campos.setOpaque(false);
        campos.add(crearCampo("Nombre", textField2));
        campos.add(crearCampo("Cédula", textField1));
        campos.add(crearCampo("Teléfono", textField3));
        campos.add(crearCampo("Carrera", textField4));

        var acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        acciones.setOpaque(false);
        estiloBoton(insertarButton, ACENTO, FONDO, true);
        estiloBoton(buscarButton, SUPERFICIE, TEXTO, false);
        estiloBoton(eliminarButton, SUPERFICIE, new Color(255, 143, 143), false);
        estiloBoton(limpiarButton, SUPERFICIE, TEXTO, false);
        acciones.add(insertarButton);
        acciones.add(buscarButton);
        acciones.add(eliminarButton);
        acciones.add(limpiarButton);
        formulario.add(campos, BorderLayout.CENTER);
        formulario.add(acciones, BorderLayout.SOUTH);

        var parteSuperior = new JPanel(new BorderLayout(0, 19));
        parteSuperior.setOpaque(false);
        parteSuperior.add(encabezado, BorderLayout.NORTH);
        parteSuperior.add(formulario, BorderLayout.CENTER);

        var lista = new JPanel(new BorderLayout(0, 12));
        lista.setOpaque(false);
        var tituloLista = new JPanel(new BorderLayout());
        tituloLista.setOpaque(false);
        var registros = new JLabel("Registros");
        registros.setFont(new Font("SansSerif", Font.BOLD, 17));
        registros.setForeground(TEXTO);
        cantidadRegistros.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cantidadRegistros.setForeground(SECUNDARIO);
        tituloLista.add(registros, BorderLayout.WEST);
        tituloLista.add(cantidadRegistros, BorderLayout.EAST);

        estilizarTabla();
        var scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(BORDE));
        scroll.getViewport().setBackground(SUPERFICIE);
        scroll.getViewport().setOpaque(true);
        lista.add(tituloLista, BorderLayout.NORTH);
        lista.add(scroll, BorderLayout.CENTER);

        contenido.add(parteSuperior, BorderLayout.NORTH);
        contenido.add(lista, BorderLayout.CENTER);
        setContentPane(contenido);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 650));
        setSize(850, 760);
        setLocationRelativeTo(null);
    }

    private JPanel crearCampo(String etiqueta, JTextField campo) {
        var panel = new JPanel(new BorderLayout(0, 7));
        panel.setOpaque(false);
        var label = new JLabel(etiqueta);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(SECUNDARIO);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campo.setForeground(TEXTO);
        campo.setCaretColor(TEXTO);
        campo.setBackground(CAMPO);
        campo.setPreferredSize(new Dimension(230, 40));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE), new EmptyBorder(8, 11, 8, 11)));
        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);
        return panel;
    }

    private void estilizarTabla() {
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setForeground(TEXTO);
        tabla.setBackground(SUPERFICIE);
        tabla.setSelectionBackground(new Color(54, 74, 112));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(SUPERFICIE);
        tabla.setShowGrid(false);
        tabla.setRowHeight(36);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFillsViewportHeight(true);
        var encabezado = tabla.getTableHeader();
        encabezado.setFont(new Font("SansSerif", Font.BOLD, 11));
        encabezado.setForeground(SECUNDARIO);
        encabezado.setBackground(SUPERFICIE);
        encabezado.setOpaque(true);
        encabezado.setReorderingAllowed(false);
        encabezado.setPreferredSize(new Dimension(0, 38));
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE));
        encabezado.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean seleccionado, boolean tieneFoco, int fila, int columna) {
                var celda = (JLabel) super.getTableCellRendererComponent(
                        table, value, seleccionado, tieneFoco, fila, columna);
                celda.setFont(new Font("SansSerif", Font.BOLD, 11));
                celda.setForeground(SECUNDARIO);
                celda.setBackground(SUPERFICIE);
                celda.setHorizontalAlignment(SwingConstants.LEFT);
                celda.setBorder(new EmptyBorder(0, 12, 0, 12));
                return celda;
            }
        });
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean seleccionado, boolean tieneFoco, int fila, int columna) {
                var celda = (JLabel) super.getTableCellRendererComponent(
                        table, value, seleccionado, tieneFoco, fila, columna);
                celda.setOpaque(true);
                celda.setBorder(new EmptyBorder(0, 12, 0, 12));
                celda.setBackground(seleccionado ? table.getSelectionBackground()
                        : fila % 2 == 0 ? SUPERFICIE : CAMPO);
                celda.setForeground(seleccionado ? Color.WHITE : TEXTO);
                return celda;
            }
        });
    }

    private void estiloBoton(JButton boton, Color fondo, Color texto, boolean primario) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setForeground(texto);
        boton.setBackground(fondo);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primario ? ACENTO : BORDE),
                new EmptyBorder(9, 15, 9, 15)));
    }

    private void conectarBotones() {
        insertarButton.addActionListener(evento -> insertarEstudiante());
        buscarButton.addActionListener(evento -> buscarEstudiante());
        eliminarButton.addActionListener(evento -> eliminarEstudiante());
        limpiarButton.addActionListener(evento -> limpiarFormulario());
    }

    private void insertarEstudiante() {
        var cedula = textField1.getText().trim();
        var nombre = textField2.getText().trim();
        var telefono = textField3.getText().trim();
        var carrera = textField4.getText().trim();
        if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || carrera.isEmpty()) {
            mostrarMensaje("Completa todos los campos para insertar el registro.");
            return;
        }
        if (buscarFila(cedula) >= 0) {
            mostrarMensaje("Ya existe un registro con esa cédula.");
            return;
        }
        modelo.addRow(new Object[]{cedula, nombre, telefono, carrera});
        actualizarCantidad();
        limpiarFormulario();
    }

    private void buscarEstudiante() {
        var cedula = textField1.getText().trim();
        if (cedula.isEmpty()) {
            mostrarMensaje("Escribe una cédula para buscar.");
            return;
        }
        var fila = buscarFila(cedula);
        if (fila < 0) {
            mostrarMensaje("No se encontró un registro con esa cédula.");
            return;
        }
        tabla.setRowSelectionInterval(fila, fila);
        textField2.setText((String) modelo.getValueAt(fila, 1));
        textField3.setText((String) modelo.getValueAt(fila, 2));
        textField4.setText((String) modelo.getValueAt(fila, 3));
    }

    private void eliminarEstudiante() {
        var cedula = textField1.getText().trim();
        if (cedula.isEmpty()) {
            mostrarMensaje("Escribe una cédula para eliminar.");
            return;
        }
        var fila = buscarFila(cedula);
        if (fila < 0) {
            mostrarMensaje("No se encontró un registro con esa cédula.");
            return;
        }
        modelo.removeRow(fila);
        actualizarCantidad();
        limpiarFormulario();
    }

    private int buscarFila(String cedula) {
        return IntStream.range(0, modelo.getRowCount())
                .filter(fila -> cedula.equals(modelo.getValueAt(fila, 0)))
                .findFirst()
                .orElse(-1);
    }

    private void limpiarFormulario() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        tabla.clearSelection();
    }

    private void actualizarCantidad() {
        cantidadRegistros.setText(modelo.getRowCount() + (modelo.getRowCount() == 1
                ? " registro" : " registros"));
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Agenda de estudiantes",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
