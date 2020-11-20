package com.tamargo.ventanas;

import com.tamargo.GestionEntity;
import com.tamargo.PiezasEntity;
import com.tamargo.ProveedoresEntity;
import com.tamargo.ProyectosEntity;
import com.tamargo.tablas.ModeloListadoEstPiezas;
import com.tamargo.tablas.ModeloListadoEstProveedores;
import com.tamargo.tablas.ModeloListadoGestiones;
import com.tamargo.util.BorrarDatos;
import com.tamargo.util.CargarDatos;
import com.tamargo.util.InsertarEditarDatos;
import org.hibernate.ObjectNotFoundException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaPrincipal {
    // Base de la Ventana
    private JFrame ventanaPrincipal;
    private JPanel panel;
    private JPanel panelDatos;
    private int numVentana = 1;

    // ArrayLists con los datos
    private ArrayList<ProveedoresEntity> proveedores = new ArrayList<>();
    private ArrayList<PiezasEntity> piezas = new ArrayList<>();
    private ArrayList<ProyectosEntity> proyectos = new ArrayList<>();
    private ArrayList<GestionEntity> gestiones = new ArrayList<>();

    private final int numObjetosPorPagLista = 16;

    // Proveedores
    private JList<ProveedoresEntity> listaProveedores;
    private int indexPagProv = 1;
    private final int numProvsPorPagLista = numObjetosPorPagLista;
    private int numPagsProv = 1;
    private JButton nuevoProveedor;
    private JButton borrarProveedor;
    private JButton editarProveedor;
    private JTextField t_provCod;
    private JTextField t_provNombre;
    private JTextField t_provApe;
    private JTextField t_provDir;
    private JLabel l_paginasProv;
    private ArrayList<ProveedoresEntity> proveedoresComboBox = new ArrayList<>();

    // Piezas
    private JList<PiezasEntity> listaPiezas;
    private int indexPagPza = 1;
    private final int numPzasPorPagLista = numObjetosPorPagLista;
    private int numPagsPza = 1;
    private JButton nuevaPieza;
    private JButton borrarPieza;
    private JButton editarPieza;
    private JTextField t_pzaCod;
    private JTextField t_pzaNombre;
    private JTextField t_pzaPrecio;
    private JTextArea t_pzaDesc;
    private JLabel l_paginasPza;
    private ArrayList<PiezasEntity> piezasComboBox = new ArrayList<>();

    // Proyectos
    private JList<ProyectosEntity> listaProyectos;
    private int indexPagProy = 1;
    private final int numProysPorPagLista = numObjetosPorPagLista;
    private int numPagsProy = 1;
    private JButton nuevoProyecto;
    private JButton borrarProyecto;
    private JButton editarProyecto;
    private JTextField t_proyCod;
    private JTextField t_proyNombre;
    private JTextField t_proyCiudad;
    private JLabel l_paginasProy;
    private ArrayList<ProyectosEntity> proyectosComboBox = new ArrayList<>();

    // Gestiones
    private JComboBox<String> cb_gestProveedor;
    private JComboBox<String> cb_gestPieza;
    private JComboBox<String> cb_gestProyecto;
    private ArrayList<ProveedoresEntity> gestProveedores = new ArrayList<>();
    private ArrayList<PiezasEntity> gestPiezas = new ArrayList<>();
    private ArrayList<ProyectosEntity> gestProyectos = new ArrayList<>();
    private JButton nuevaGestion;
    private JButton borrarGestion;
    private JButton editarGestion;
    private JButton listarGestiones;
    private JPanel gesPanel;
    private JTextField t_gesNomProveedor;
    private JTextField t_gesDirProveedor;
    private JTextField t_gesNomPieza;
    private JTextField t_gesPrecioPieza;
    private JTextField t_gesNomProyecto;
    private JTextField t_gesCiudadProyecto;
    private JTextField t_gesCantidad;
    private boolean insertando = false;
    private ArrayList<ProveedoresEntity> proveedoresNuevaGestion = new ArrayList<>();
    private ArrayList<PiezasEntity> piezasNuevaGestion = new ArrayList<>();
    private ArrayList<ProyectosEntity> proyectosNuevaGestion = new ArrayList<>();

    // Suministros Proveedor
    private JComboBox<String> cb_sumProv;
    private JTextField t_sumProvNom;
    private JTextField t_sumProvApe;
    private JTextField t_sumProvDir;
    private JTextField t_sumProvNumPiez;
    private JTextField t_sumProvNumProy;
    private JButton b_sumProvPiezasSuministradas;
    private JCheckBox check_sumProv;
    private boolean checkSumProvMarcado = false;

    // Suministros Piezas
    private JComboBox<String> cb_sumPza;
    private JTextField t_sumPzaNom;
    private JTextField t_sumPzaPrecio;
    private JTextArea t_sumPzaDesc;
    private JTextField t_sumPzaNumProy;
    private JTextField t_sumPzaNumProv;
    private JTextField t_sumPzaTotal;
    private JButton b_sumPzaPiezasSuministradas;
    private JCheckBox check_sumPza;
    private boolean checkSumPzaMarcado = false;

    // Estadísticas
    private JButton b_estNumPiezasProyecto;
    private JTextField t_estCodPiezaMasCantidad;
    private JTextField t_estNombrePiezaMasCantidad;
    private JTextField t_estCodPiezaMasProyectos;
    private JTextField t_estNombrePiezaMasProyectos;

    private JButton b_estNumPiezasProveedor;
    private JTextField t_estCodProvMasCantPiezas;
    private JTextField t_estNombreProvMasCantPiezas;
    private JTextField t_estCodProvMasProy;
    private JTextField t_estNombreProvMasProy;
    private JTextField t_estCodProvMasPiezasDistintas;
    private JTextField t_estNombreProvMasPiezasDistintas;

    private boolean filtrarEstadisticas = true;

    // Dimensiones
    private final Dimension dimPanelDatos = new Dimension(700, 500);
    private final Dimension dimLabel = new Dimension(200, 20);
    private final Dimension dimTextField = new Dimension(200, 25);
    private final Dimension dimTextFieldCodigo = new Dimension(75, 25);
    private final Dimension dimBoton = new Dimension(150, 40);
    private final Dimension dimBotonPeque = new Dimension(50, 50);
    private final Dimension dimLista = new Dimension(250, 300);
    private final Dimension dimScrollPane = new Dimension(255, 305);
    private final Dimension dimTextArea = new Dimension(200, 75);

    public VentanaPrincipal() {
        cargarVentanaInicio(1);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Métodos generales
    public void confLabel(JLabel label) {
        label.setMinimumSize(dimLabel);
        label.setMaximumSize(dimLabel);
        label.setPreferredSize(dimLabel);
    }
    public void confTextField(JTextField textField) {
        textField.setMinimumSize(dimTextField);
        textField.setMaximumSize(dimTextField);
        textField.setPreferredSize(dimTextField);
    }
    public void confTextFieldCodigo(JTextField textField) {
        textField.setMinimumSize(dimTextField);
        textField.setMaximumSize(dimTextField);
        textField.setPreferredSize(dimTextField);
    }
    public void confBoton(JButton boton, Dimension dim) {
        boton.setMinimumSize(dim);
        boton.setMaximumSize(dim);
        boton.setPreferredSize(dim);

        boton.setFocusPainted(false);
        boton.setFont(new Font("MicrosoftYaHeiUI", Font.BOLD, 15));
    }
    public void confLista(JList lista) {
        lista.setMinimumSize(dimLista);
        lista.setMaximumSize(dimLista);
        lista.setPreferredSize(dimLista);
    }
    public void confScrollPane(JScrollPane scrollPane) {
        scrollPane.setMinimumSize(dimScrollPane);
        scrollPane.setMaximumSize(dimScrollPane);
        scrollPane.setPreferredSize(dimScrollPane);

        scrollPane.getVerticalScrollBar().setVisible(true);
    }
    public void confPanel(JPanel panel) {
        panel.setMinimumSize(dimPanelDatos);
        panel.setMaximumSize(dimPanelDatos);
        panel.setPreferredSize(dimPanelDatos);
    }
    public void confTextArea(JTextArea textArea) {
        textArea.setMinimumSize(dimTextArea);
        textArea.setMaximumSize(dimTextArea);
        textArea.setPreferredSize(dimTextArea);
    }
    public void mostrarJOptionPane(String titulo, String mensaje, int tipo) {
        JButton okButton = new JButton("Entendido");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane(mensaje, tipo, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    public void mostrarJOptionPaneEliminar(int tipo, String codigo, String codigo2, String codigo3) {
        JButton noButton = new JButton("Mejor no");
        JButton eliminarButton = new JButton("Eliminar");
        noButton.setFocusPainted(false);
        eliminarButton.setFocusPainted(false);
        Object[] options = {noButton, eliminarButton};

        String titulo = "Eliminando datos";
        String mensaje = "Una vez eliminados los datos no podrán recuperarse.";

        if (tipo == 1)
            titulo += " Proveedor " + codigo;
        else if (tipo == 2)
            titulo += " Pieza " + codigo;
        else if (tipo == 3)
            titulo += " Proyecto " + codigo;
        else if (tipo == 4)
            titulo += " Gestión";

        final JOptionPane pane = new JOptionPane("¿Estás seguro?\n" +
                mensaje, JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ELIMINAR PROVEEDOR / PIEZA / PROYECTO / GESTIÓN
                if (tipo == 1)
                    eliminarProveedor(codigo);
                else if (tipo == 2)
                    eliminarPieza(codigo);
                else if (tipo == 3)
                    eliminarProyecto(codigo);
                else if (tipo == 4)
                    eliminarGestion(codigo, codigo2, codigo3);

                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }
    public void resizeColumnWidth(JTable table, int[] widths) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = widths[column];
            if (column == table.getColumnCount() - 1)
                for (int i = 0; i < widths.length - 1; i++) {
                    width -= widths[i];
                }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Administración de Proveedores, Piezas y Proyectos: Cargar Listas
    public void cargarListaProveedores() {
        DefaultListModel<ProveedoresEntity> modelo = new DefaultListModel<>();

        int i = 0;
        int inicio = (indexPagProv - 1) * numProvsPorPagLista;
        int fin = inicio + numProvsPorPagLista;

        for (ProveedoresEntity prov: proveedores) {
            if (i >= inicio && i < fin)
                modelo.addElement(prov);
            i++;
        }

        double numPagsDec = (double)proveedores.size() / (double)numProvsPorPagLista;
        int numPagsEnt = proveedores.size() / numProvsPorPagLista;

        if (numPagsEnt < numPagsDec)
            numPagsEnt++;

        numPagsProv = numPagsEnt;

        l_paginasProv.setText("Pag. " + indexPagProv + "/" + numPagsEnt);
        vaciarDatosProveedor();
        listaProveedores.setModel(modelo);
        listaProveedores.setSelectedIndex(0);

        if (proveedores.size() <= 0) {
            mostrarJOptionPane("No existen proveedores",
                    "No existe ningún proveedor en la BBDD. ¡Registra alguno!",
                    1);
            prepararNuevoProveedor();
            borrarProveedor.setEnabled(false);
        }
    }
    public void cargarListaProyectos() {
        DefaultListModel<ProyectosEntity> modelo = new DefaultListModel<>();

        int i = 0;
        int inicio = (indexPagProy - 1) * numProysPorPagLista;
        int fin = inicio + numProysPorPagLista;

        for (ProyectosEntity proy: proyectos) {
            if (i >= inicio && i < fin)
                modelo.addElement(proy);
            i++;
        }

        double numPagsDec = (double)proyectos.size() / (double)numProysPorPagLista;
        int numPagsEnt = proyectos.size() / numProysPorPagLista;

        if (numPagsEnt < numPagsDec)
            numPagsEnt++;

        numPagsProy = numPagsEnt;

        l_paginasProy.setText("Pag. " + indexPagProy + "/" + numPagsEnt);
        vaciarDatosProyecto();
        listaProyectos.setModel(modelo);
        listaProyectos.setSelectedIndex(0);

        if (proyectos.size() <= 0) {
            mostrarJOptionPane("No existen proyectos",
                    "No existe ningún proyecto en la BBDD. ¡Registra alguno!",
                    1);
            prepararNuevoProyecto();
            borrarProyecto.setEnabled(false);
        }
    }
    public void cargarListaPiezas() {
        DefaultListModel<PiezasEntity> modelo = new DefaultListModel<>();

        int i = 0;
        int inicio = (indexPagPza - 1) * numPzasPorPagLista;
        int fin = inicio + numPzasPorPagLista;

        for (PiezasEntity pza: piezas) {
            if (i >= inicio && i < fin)
                modelo.addElement(pza);
            i++;
        }

        double numPagsDec = (double)piezas.size() / (double)numPzasPorPagLista;
        int numPagsEnt = piezas.size() / numPzasPorPagLista;

        if (numPagsEnt < numPagsDec)
            numPagsEnt++;

        numPagsPza = numPagsEnt;

        l_paginasPza.setText("Pag. " + indexPagPza + "/" + numPagsEnt);
        vaciarDatosPieza();
        listaPiezas.setModel(modelo);
        listaPiezas.setSelectedIndex(0);

        if (piezas.size() <= 0) {
            mostrarJOptionPane("No existen piezas",
                    "No existe ninguna pieza en la BBDD. ¡Registra alguna!",
                    1);
            prepararNuevaPieza();
            borrarPieza.setEnabled(false);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Utilidades Piezas
    // Ventana gestión
    public PiezasEntity construirPiezaConLosDatos() {
        String codigo = t_pzaCod.getText();
        String nombre = t_pzaNombre.getText();
        String precio = t_pzaPrecio.getText().replace(',','.');
        String descripcion = t_pzaDesc.getText();

        PiezasEntity pza = new PiezasEntity();
        pza.setCodigo(codigo);
        pza.setNombre(nombre);
        pza.setPrecio(Double.parseDouble(precio));
        pza.setDescripcion(descripcion);

        return pza;
    }
    public void editarPiezaLista() {
        PiezasEntity prov = (PiezasEntity) listaPiezas.getSelectedValue();
        prov.actualizarDatos(construirPiezaConLosDatos());
    }
    public void addPiezaALista() {
        piezas.add(construirPiezaConLosDatos());
    }
    public void eliminarPieza(String codigo) {
        boolean eliminado = BorrarDatos.eliminarPieza(codigo);
        if (eliminado) {
            piezas = CargarDatos.piezas();
            if ((indexPagProv - 1) * numProvsPorPagLista > piezas.size())
                indexPagProv--;
            cargarListaPiezas();
        }
    }
    public boolean comprobarDatosPieza() {
        boolean datosValidos = true;
        StringBuilder datosFaltantes = new StringBuilder();
        if (t_pzaNombre.getText().equalsIgnoreCase("")) {
            datosFaltantes.append("Nombre");
            datosValidos = false;
        }
        if (t_pzaPrecio.getText().equalsIgnoreCase("")) {
            if (String.valueOf(datosFaltantes).length() > 2)
                datosFaltantes.append(", ");
            datosFaltantes.append("Precio");
            datosValidos = false;
        }
        if (t_pzaDesc.getText().equalsIgnoreCase("")) {
            if (String.valueOf(datosFaltantes).length() > 2)
                datosFaltantes.append(", ");
            datosFaltantes.append("Descripción");
            datosValidos = false;
        }

        if (!datosValidos) {
            mostrarJOptionPane("Faltan Datos",
                    "Debes rellenar todos los datos obligatorios\nFaltan los siguientes datos:\n" +
                            String.valueOf(datosFaltantes),
                    0);
            return false;
        }

        try {
            double prueba = Double.parseDouble(t_pzaPrecio.getText().replace(',','.'));
            if (prueba < 0) {
                mostrarJOptionPane("Faltan Datos",
                        "El precio no puede ser negativo",
                        0);
                return false;
            }
        } catch (NumberFormatException ignored) {
            mostrarJOptionPane("Faltan Datos",
                    "Debes introducir un precio con el siguiente formato:" +
                            "entero.decimal (Ejemplo: 7.5)",
                    0);
            return false;
        }

        return datosValidos;
    }
    public void vaciarDatosPieza() {
        t_pzaCod.setText(CargarDatos.codigoNuevo(2));
        t_pzaNombre.setText("");
        t_pzaPrecio.setText("");
        t_pzaDesc.setText("");
    }
    public void cargarDatosPieza() {
        PiezasEntity pza = null;
        try {
            pza = (PiezasEntity) listaPiezas.getSelectedValue();
        } catch (NullPointerException | ObjectNotFoundException ignored) { }

        if (pza != null) {
            t_pzaCod.setText(pza.getCodigo());
            t_pzaNombre.setText(pza.getNombre());
            t_pzaPrecio.setText(String.format("%.2f", pza.getPrecio()));
            t_pzaDesc.setText(pza.getDescripcion());
        }
    }
    public void reanudarVentanaPiezas() {
        borrarPieza.setText("Eliminar");
        editarPieza.setText("Editar");
        nuevaPieza.setEnabled(true);

        listaPiezas.setEnabled(true);
    }
    public void prepararNuevaPieza() {
        nuevaPieza.setEnabled(false);

        borrarPieza.setEnabled(true);
        editarPieza.setEnabled(true);

        borrarPieza.setText("Cancelar");
        editarPieza.setText("Insertar");

        listaPiezas.clearSelection();
        listaPiezas.setEnabled(false);
        vaciarDatosPieza();
    }

    // Ventana búsqueda
    public void actualizarOpcionesPza(JComboBox<String> comboBox, JTextField busqueda, int tipo, JLabel cod, JLabel nombre, JLabel precio, JTextPane desc) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        piezasComboBox = new ArrayList<>();

        String texto = busqueda.getText().toLowerCase();
        for (PiezasEntity pza: piezas) {
            String valor = "";
            if (tipo == 1) {
                valor = pza.getCodigo();
            } else if (tipo == 2) {
                valor = pza.getNombre();
            }

            if ((!valor.equalsIgnoreCase("") && valor.toLowerCase().contains(texto)) || (!valor.equalsIgnoreCase("") && texto.equalsIgnoreCase(""))) {
                piezasComboBox.add(pza);
                modelo.addElement(valor);
            }
        }
        comboBox.setModel(modelo);
        if (modelo.getSize() > 0)
            comboBox.setSelectedIndex(0);
        else
            vaciarDatosOpcionesPza(cod, nombre, precio, desc);

    }
    public void vaciarDatosOpcionesPza(JLabel cod, JLabel nombre, JLabel precio, JTextPane desc) {
        cod.setText("");
        nombre.setText("");
        precio.setText("");
        desc.setText("");
    }
    public void actualizarDatosOpcionesPza(JComboBox<String> comboBox, JLabel cod, JLabel nombre, JLabel precio, JTextPane desc) {
        PiezasEntity pza = piezasComboBox.get(comboBox.getSelectedIndex());
        cod.setText(String.format("%s", pza.getCodigo()));
        nombre.setText(String.format("%s", pza.getNombre()));
        precio.setText(String.format("%.2f", pza.getPrecio()));
        desc.setText(String.format("%s", pza.getDescripcion()));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Utilidades Proyectos
    // Ventana gestión
    public ProyectosEntity construirProyectoConLosDatos() {
        String codigo = t_proyCod.getText();
        String nombre = t_proyNombre.getText();
        String ciudad = t_proyCiudad.getText();

        ProyectosEntity proy = new ProyectosEntity();
        proy.setCodigo(codigo);
        proy.setNombre(nombre);
        proy.setCiudad(ciudad);

        return proy;
    }
    public void editarProyectoLista() {
        ProyectosEntity proy = (ProyectosEntity) listaProyectos.getSelectedValue();
        proy.actualizarDatos(construirProyectoConLosDatos());
    }
    public void addProyectoALista() {
        proyectos.add(construirProyectoConLosDatos());
    }
    public void eliminarProyecto(String codigo) {
        boolean eliminado = BorrarDatos.eliminarProyecto(codigo);
        if (eliminado) {
            proyectos = CargarDatos.proyectos();
            if ((indexPagProy - 1) * numProysPorPagLista > proyectos.size())
                indexPagProy--;
            cargarListaProyectos();
        }
    }
    public boolean comprobarDatosProyecto() {
        boolean datosValidos = true;
        StringBuilder datosFaltantes = new StringBuilder();
        if (t_proyNombre.getText().equalsIgnoreCase("")) {
            datosFaltantes.append("Nombre");
            datosValidos = false;
        }
        if (t_proyCiudad.getText().equalsIgnoreCase("")) {
            if (String.valueOf(datosFaltantes).length() > 2)
                datosFaltantes.append(", ");
            datosFaltantes.append("Ciudad");
            datosValidos = false;
        }

        if (!datosValidos) {
            mostrarJOptionPane("Faltan Datos",
                    "Debes rellenar todos los datos obligatorios\nFaltan los siguientes datos:\n" +
                            String.valueOf(datosFaltantes),
                    0);
        }

        return datosValidos;
    }
    public void vaciarDatosProyecto() {
        t_proyCod.setText(CargarDatos.codigoNuevo(3));
        t_proyNombre.setText("");
        t_proyCiudad.setText("");
    }
    public void cargarDatosProyecto() {
        ProyectosEntity proy = null;
        try {
            proy = (ProyectosEntity) listaProyectos.getSelectedValue();
        } catch (NullPointerException | ObjectNotFoundException ignored) { }

        if (proy != null) {
            t_proyCod.setText(proy.getCodigo());
            t_proyNombre.setText(proy.getNombre());
            t_proyCiudad.setText(proy.getCiudad());
        }
    }
    public void reanudarVentanaProyectos() {
        borrarProyecto.setText("Eliminar");
        editarProyecto.setText("Editar");
        nuevoProyecto.setEnabled(true);

        listaProyectos.setEnabled(true);
    }
    public void prepararNuevoProyecto() {
        nuevoProyecto.setEnabled(false);

        borrarProyecto.setEnabled(true);
        editarProyecto.setEnabled(true);

        borrarProyecto.setText("Cancelar");
        editarProyecto.setText("Insertar");

        listaProyectos.clearSelection();
        listaProyectos.setEnabled(false);
        vaciarDatosProyecto();
    }

    // Ventana búsqueda
    public void actualizarOpcionesProy(JComboBox<String> comboBox, JTextField busqueda, int tipo, JLabel cod, JLabel nombre, JLabel ciudad) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        proyectosComboBox = new ArrayList<>();

        String texto = busqueda.getText().toLowerCase();
        for (ProyectosEntity proy: proyectos) {
            String valor = "";
            if (tipo == 1) {
                valor = proy.getCodigo();
            } else if (tipo == 2) {
                valor = proy.getNombre();
            } else if (tipo == 3) {
                valor = proy.getCiudad();
            }

            if ((!valor.equalsIgnoreCase("") && valor.toLowerCase().contains(texto)) || (!valor.equalsIgnoreCase("") && texto.equalsIgnoreCase(""))) {
                proyectosComboBox.add(proy);
                modelo.addElement(valor);
            }
        }
        comboBox.setModel(modelo);
        if (modelo.getSize() > 0)
            comboBox.setSelectedIndex(0);
        else
            vaciarDatosOpcionesProy(cod, nombre, ciudad);
    }
    public void vaciarDatosOpcionesProy(JLabel cod, JLabel nombre, JLabel ciudad) {
        cod.setText("");
        nombre.setText("");
        ciudad.setText("");
    }
    public void actualizarDatosOpcionesProy(JComboBox<String> comboBox, JLabel cod, JLabel nombre, JLabel ciudad) {
        ProyectosEntity proy = proyectosComboBox.get(comboBox.getSelectedIndex());
        cod.setText(proy.getCodigo());
        nombre.setText(proy.getNombre());
        ciudad.setText(proy.getCiudad());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Utilidades Proveedores
    // Ventana gestión
    public ProveedoresEntity construirProveedorConLosDatos() {
        String codigo = t_provCod.getText();
        String nombre = t_provNombre.getText();
        String apellidos = t_provApe.getText();
        String direccion = t_provDir.getText();

        ProveedoresEntity prov = new ProveedoresEntity();
        prov.setCodigo(codigo);
        prov.setNombre(nombre);
        prov.setApellidos(apellidos);
        prov.setDireccion(direccion);

        return prov;
    }
    public void editarProveedorLista() {
        ProveedoresEntity prov = (ProveedoresEntity) listaProveedores.getSelectedValue();
        prov.actualizarDatos(construirProveedorConLosDatos());
    }
    public void addProveedorALista() {
        proveedores.add(construirProveedorConLosDatos());
    }
    public void eliminarProveedor(String codigo) {
        boolean eliminado = BorrarDatos.eliminarProveedor(codigo);
        if (eliminado) {
            proveedores = CargarDatos.proveedores();
            if ((indexPagProv - 1) * numProvsPorPagLista >= proveedores.size())
                indexPagProv--;
            cargarListaProveedores();
        }
    }
    public boolean comprobarDatosProveedor() {
        boolean datosValidos = true;
        StringBuilder datosFaltantes = new StringBuilder();
        if (t_provNombre.getText().equalsIgnoreCase("")) {
            datosFaltantes.append("Nombre");
            datosValidos = false;
        }
        if (t_provApe.getText().equalsIgnoreCase("")) {
            if (String.valueOf(datosFaltantes).length() > 2)
                datosFaltantes.append(", ");
            datosFaltantes.append("Apellidos");
            datosValidos = false;
        }
        if (t_provDir.getText().equalsIgnoreCase("")) {
            if (String.valueOf(datosFaltantes).length() > 2)
                datosFaltantes.append(", ");
            datosFaltantes.append("Dirección");
            datosValidos = false;
        }

        if (!datosValidos) {
            mostrarJOptionPane("Faltan Datos",
                    "Debes rellenar todos los datos obligatorios\nFaltan los siguientes datos:\n" +
                            String.valueOf(datosFaltantes),
                    0);

        }

        return datosValidos;
    }
    public void vaciarDatosProveedor() {
        t_provCod.setText(CargarDatos.codigoNuevo(1));
        t_provNombre.setText("");
        t_provApe.setText("");
        t_provDir.setText("");
    }
    public void cargarDatosProveedor() {
        ProveedoresEntity prov = null;
        try {
            prov = (ProveedoresEntity) listaProveedores.getSelectedValue();
        } catch (NullPointerException | ObjectNotFoundException ignored) { }

        if (prov != null) {
            t_provCod.setText(prov.getCodigo());
            t_provNombre.setText(prov.getNombre());
            t_provApe.setText(prov.getApellidos());
            t_provDir.setText(prov.getDireccion());
        }
    }
    public void reanudarVentanaProveedores() {
        borrarProveedor.setText("Eliminar");
        editarProveedor.setText("Editar");
        nuevoProveedor.setEnabled(true);

        listaProveedores.setEnabled(true);
    }
    public void prepararNuevoProveedor() {
        nuevoProveedor.setEnabled(false);

        borrarProveedor.setEnabled(true);
        editarProveedor.setEnabled(true);

        borrarProveedor.setText("Cancelar");
        editarProveedor.setText("Insertar");

        listaProveedores.clearSelection();
        listaProveedores.setEnabled(false);
        vaciarDatosProveedor();
    }

    // Ventana búsqueda
    public void actualizarOpcionesProv(JComboBox<String> comboBox, JTextField busqueda, int tipo, JLabel cod, JLabel nombre, JLabel apellidos, JLabel dir) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        proveedoresComboBox = new ArrayList<>();

        String texto = busqueda.getText().toLowerCase();
        for (ProveedoresEntity prov: proveedores) {
            String valor = "";
            if (tipo == 1) {
                valor = prov.getCodigo();
            } else if (tipo == 2) {
                valor = prov.getNombre();
            } else if (tipo == 3) {
                valor = prov.getDireccion();
            }

            if ((!valor.equalsIgnoreCase("") && valor.toLowerCase().contains(texto)) || (!valor.equalsIgnoreCase("") && texto.equalsIgnoreCase(""))) {
                proveedoresComboBox.add(prov);
                modelo.addElement(valor);
            }
        }
        comboBox.setModel(modelo);
        if (modelo.getSize() > 0)
            comboBox.setSelectedIndex(0);
        else
            vaciarDatosOpcionesProv(cod, nombre, apellidos, dir);

    }
    public void vaciarDatosOpcionesProv(JLabel cod, JLabel nombre, JLabel apellidos, JLabel dir) {
        cod.setText("");
        nombre.setText("");
        apellidos.setText("");
        dir.setText("");
    }
    public void actualizarDatosOpcionesProv(JComboBox<String> comboBox, JLabel cod, JLabel nombre, JLabel apellidos, JLabel dir) {
        ProveedoresEntity prov = proveedoresComboBox.get(comboBox.getSelectedIndex());
        cod.setText(prov.getCodigo());
        nombre.setText(prov.getNombre());
        apellidos.setText(prov.getApellidos());
        dir.setText(prov.getDireccion());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Utilidades Gestión
    // Ventana administrar gestión
    public boolean gestionInsertarNuevaGestion() {
        boolean insertado = false;
        if (gestionComprobarCantidadCorrecta()) {
            ProveedoresEntity prov = null;
            PiezasEntity pieza = null;
            ProyectosEntity proyecto = null;
            double cantidad = -1;

            try {
                prov = proveedoresNuevaGestion.get(cb_gestProveedor.getSelectedIndex());
                pieza = piezasNuevaGestion.get(cb_gestPieza.getSelectedIndex());
                proyecto = proyectosNuevaGestion.get(cb_gestProyecto.getSelectedIndex());
                cantidad = Double.parseDouble(t_gesCantidad.getText().replace(',','.'));
            } catch (NullPointerException | IllegalArgumentException ignored) {}

            if (prov != null && pieza != null && proyecto != null) {
                if (cantidad >= 0) {
                    insertado = InsertarEditarDatos.saveUpdateGestion(prov, pieza, proyecto, cantidad, 1);
                }
            } else {
                mostrarJOptionPane("Error al insertar", "Ha ocurrido un error al elegir los componentes de la nueva gestión", 0);
            }
        }
        return insertado;
    }
    public boolean gestionEditarGestion() {
        boolean editado = false;
        if (gestionComprobarCantidadCorrecta()) {
            ProveedoresEntity prov = null;
            PiezasEntity pieza = null;
            ProyectosEntity proyecto = null;
            double cantidad = -1;

            try {
                prov = gestProveedores.get(cb_gestProveedor.getSelectedIndex());
                pieza = gestPiezas.get(cb_gestPieza.getSelectedIndex());
                proyecto = gestProyectos.get(cb_gestProyecto.getSelectedIndex());
                cantidad = Double.parseDouble(t_gesCantidad.getText().replace(',','.'));
            } catch (NullPointerException | IllegalArgumentException ignored) {}

            if (prov != null && pieza != null && proyecto != null) {
                if (cantidad >= 0) {
                    editado = InsertarEditarDatos.saveUpdateGestion(prov, pieza, proyecto, cantidad, 2);
                }
            } else {
                mostrarJOptionPane("Error al insertar", "Ha ocurrido un error al elegir los componentes de la gestión", 0);
            }
        }
        return editado;
    }
    public void eliminarGestion(String codProv, String codPieza, String codProyecto) {
        boolean eliminado = BorrarDatos.eliminarGestion(codProv, codPieza, codProyecto);
        if (eliminado) {
            recargarDatosVentanaAdminGestion();
        }
    }
    public boolean gestionComprobarCantidadCorrecta() {
        if (t_gesCantidad.getText().equalsIgnoreCase("")) {
            mostrarJOptionPane("Faltan datos",
                    "Debes introducir la cantidad", 0);
            return false;
        }

        try {
            double cantidad = Double.parseDouble(t_gesCantidad.getText().replace(',','.'));
            if (cantidad < 0) {
                mostrarJOptionPane("Faltan datos",
                        "La cantidad no puede ser negativa", 0);
                return false;
            }
            return true;
        } catch (IllegalArgumentException | NullPointerException ignored) {
            mostrarJOptionPane("Faltan datos",
                    "La cantidad debe introducirse en el siguiente formato:\nentero.decimal (ejemplo: 2.5)", 0);
            return false;
        }
    }
    public void gestionPrepararNuevaGestion() {
        ArrayList<ProveedoresEntity> proveedores = CargarDatos.proveedores();
        long numPiezas = CargarDatos.cargarNumPiezas();
        long numProyectos = CargarDatos.cargarNumProyectos();
        if (proveedores.size() > 0 && numPiezas > 0 && numProyectos > 0) {
            boolean correcto = false;
            //ArrayList<ProveedoresEntity> proveedoresGestion = CargarDatos.proveedoresGestion();
            for (ProveedoresEntity prov: proveedores) {
                ArrayList<PiezasEntity> piezasProveedor = CargarDatos.piezasGestionProv(prov.getCodigo());
                if (piezasProveedor.size() <= 0 || piezasProveedor.size() < numPiezas) {
                    correcto = true;
                    break;
                }
                for (PiezasEntity pieza: piezasProveedor) {
                    long numProyectosUsados = CargarDatos.proyectosGestionProvPieza(prov.getCodigo(), pieza.getCodigo()).size();
                    if (numProyectosUsados < numProyectos) {
                        correcto = true;
                        break;
                    }
                }
            }
            if (correcto) {
                gestionCargarDatosNuevaGestion(proveedores, numPiezas, numProyectos);
            } else {
                JButton nuevProv = new JButton("Nuevo Proveedor");
                JButton nuevPieza = new JButton("Nueva Pieza");
                JButton nuevProy = new JButton("Nuevo Proyecto");
                JButton cancelar = new JButton("Cancelar");
                nuevProv.setFocusPainted(false);
                nuevPieza.setFocusPainted(false);
                nuevProy.setFocusPainted(false);
                cancelar.setFocusPainted(false);

                Object[] options = {cancelar, nuevProv, nuevPieza, nuevProy};

                String titulo = "Sin posibilidades";
                String mensaje = "Parece que ya has cubierto todas las posibilidades.\n" +
                        "Tendrás que crear nuevos proveedores, piezas o proyectos para poder crear nuevas gestiones.";


                final JOptionPane pane = new JOptionPane(mensaje,
                        JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
                JDialog dialog = pane.createDialog(titulo);
                cancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        reanudarVentanaAdminGestion();
                    }
                });
                nuevProv.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        cargarVentanaProveedorGestion();
                        numVentana = 3;
                    }
                });
                nuevPieza.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        cargarVentanaPiezaGestion();
                        numVentana = 7;
                    }
                });
                nuevProy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        cargarVentanaProyectoGestion();
                        numVentana = 10;
                    }
                });
                dialog.setVisible(true);
            }
        } else {
            if (numPiezas <= 0) {
                mostrarJOptionPane("Sin piezas",
                        "¡No existen piezas! Tendrás que crearlas primero",
                        2);
                cargarVentanaPiezaGestion();
                numVentana = 7;
            } else if (numProyectos <= 0) {
                mostrarJOptionPane("Sin proyectos",
                        "¡No existen proyectos! Tendrás que crearlos primero",
                        2);
                cargarVentanaProyectoGestion();
                numVentana = 10;
            } else {
                mostrarJOptionPane("Sin proveedores",
                        "¡No existen proveedores! Tendrás que crearlos primero",
                        2);
                cargarVentanaProveedorGestion();
                numVentana = 3;
            }
        }
    }
    public void gestionCargarDatosNuevaGestion(ArrayList<ProveedoresEntity> proveedores, long numPiezas, long numProyectos) {
        insertando = true;
        proveedoresNuevaGestion = new ArrayList<>();
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

        // HACER COUNT DE LAS SUMAR(FOR -> NUMPIEZAS * NUMPROYECTOS)
        // IF TOTAL >= PIEZAS * PROYECTOS
        for (ProveedoresEntity prov: proveedores) {
            if (CargarDatos.cargarNumProvGestion(prov.getCodigo()) < (numPiezas * numProyectos)) {
                proveedoresNuevaGestion.add(prov);
                modelo.addElement(prov.getCodigo());
            }
        }

        cb_gestProveedor.setModel(modelo);
        cb_gestProveedor.setSelectedIndex(0);

    }
    public void gestionVaciarDatos() {
        cb_gestProveedor.setModel(new DefaultComboBoxModel<>());
        cb_gestPieza.setModel(new DefaultComboBoxModel<>());
        cb_gestProyecto.setModel(new DefaultComboBoxModel<>());
        t_gesNomProveedor.setText("");
        t_gesDirProveedor.setText("");
        t_gesNomPieza.setText("");
        t_gesPrecioPieza.setText("");
        t_gesNomProyecto.setText("");
        t_gesCiudadProyecto.setText("");
        t_gesCantidad.setText("");
    }
    public void gestionCargarDatosProveedor() {
        ProveedoresEntity prov = null;
        if (!insertando)
            prov = gestProveedores.get(cb_gestProveedor.getSelectedIndex());
        else
            prov = proveedoresNuevaGestion.get(cb_gestProveedor.getSelectedIndex());
        t_gesNomProveedor.setText(prov.getNombre() + " " + prov.getApellidos());
        t_gesDirProveedor.setText(prov.getDireccion());
        try {
            gestionCargarCantidad();
        } catch (NullPointerException ignored) {}

        gestionCargarProveedorPiezas();
        gestionCargarDatosPieza();
    }
    public void gestionCargarDatosPieza() {
        PiezasEntity pieza = null;
        if (!insertando)
            pieza = gestPiezas.get(cb_gestPieza.getSelectedIndex());
        else
            pieza = piezasNuevaGestion.get(cb_gestPieza.getSelectedIndex());
        t_gesNomPieza.setText(pieza.getNombre());
        t_gesPrecioPieza.setText(String.format("%.2f", pieza.getPrecio()));
        try {
            gestionCargarCantidad();
        } catch (NullPointerException ignored) {}

        gestionCargarProveedorPiezasProyecto();
        gestionCargarDatosProyecto();
    }
    public void gestionCargarDatosProyecto() {
        ProyectosEntity proyecto = null;
        if (!insertando)
            proyecto = gestProyectos.get(cb_gestProyecto.getSelectedIndex());
        else
            proyecto = proyectosNuevaGestion.get(cb_gestProyecto.getSelectedIndex());
        t_gesNomProyecto.setText(proyecto.getNombre());
        t_gesCiudadProyecto.setText(proyecto.getCiudad());
        try {
            gestionCargarCantidad();
        } catch (NullPointerException ignored) {}
    }
    public void gestionCargarCantidad() {
        if (!insertando) {
            ProveedoresEntity prov = gestProveedores.get(cb_gestProveedor.getSelectedIndex());
            PiezasEntity pieza = gestPiezas.get(cb_gestPieza.getSelectedIndex());
            ProyectosEntity proyecto = gestProyectos.get(cb_gestProyecto.getSelectedIndex());
            double cantidad = CargarDatos.cantidadGestionProvPiezProyecto(prov.getCodigo(), pieza.getCodigo(), proyecto.getCodigo());
            t_gesCantidad.setText(String.format("%.2f", cantidad));
        }
        else
            t_gesCantidad.setText("1");
    }
    public void reanudarVentanaAdminGestion() {
        insertando = false;
        editarGestion.setText("Editar Gestión");
        borrarGestion.setText("Borrar Gestión");
        nuevaGestion.setEnabled(true);
        listarGestiones.setEnabled(true);
        recargarDatosVentanaAdminGestion();
    }
    public void recargarDatosVentanaAdminGestion() {
        gestProveedores = CargarDatos.proveedoresGestion();
        if (gestProveedores.size() > 0) {
            prepararComboBoxGestProveedores(gestProveedores);
            gestionCargarProveedorPiezas();
            gestionCargarProveedorPiezasProyecto();

            cb_gestProveedor.setSelectedIndex(0);
            cb_gestPieza.setSelectedIndex(0);
            cb_gestProyecto.setSelectedIndex(0);
        } else {
            mostrarJOptionPane("No existen gestiones",
                    "No existen gestiones en la BBDD, ¡inserta alguna!", 1);
            editarGestion.setEnabled(false);
            borrarGestion.setEnabled(false);
            listarGestiones.setEnabled(false);
            gestionVaciarDatos();
        }
    }
    public void gestionCargarProveedorPiezas() {
        if (!insertando) {
            gestPiezas = CargarDatos.piezasGestionProv(gestProveedores.get(cb_gestProveedor.getSelectedIndex()).getCodigo());
            prepararComboBoxGestPiezas(gestPiezas);
        }
    }
    public void gestionCargarProveedorPiezasProyecto() {
        if (!insertando) {
            gestProyectos = CargarDatos.proyectosGestionProvPieza(
                    gestProveedores.get(cb_gestProveedor.getSelectedIndex()).getCodigo(),
                    gestPiezas.get(cb_gestPieza.getSelectedIndex()).getCodigo()
            );
            prepararComboBoxGestProyectos(gestProyectos);
        }
    }
    public void prepararComboBoxGestProveedores(ArrayList<ProveedoresEntity> proveedores) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (ProveedoresEntity prov : proveedores) {
            modelo.addElement(prov.getCodigo());
        }
        cb_gestProveedor.setModel(modelo);
    }
    public void prepararComboBoxGestPiezas(ArrayList<PiezasEntity> piezas) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (PiezasEntity pza : piezas) {
            modelo.addElement(pza.getCodigo());
        }
        cb_gestPieza.setModel(modelo);
    }
    public void prepararComboBoxGestProyectos(ArrayList<ProyectosEntity> proyectos) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (ProyectosEntity proy : proyectos) {
            modelo.addElement(proy.getCodigo());
        }
        cb_gestProyecto.setModel(modelo);
    }

    // Ventana suministros
    public void cargarDatosSuministrosProveedor() {
        ProveedoresEntity proveedor = null;
        try {
            proveedor = proveedores.get(cb_sumProv.getSelectedIndex());
        } catch (NullPointerException | IllegalArgumentException | ClassCastException | ObjectNotFoundException ignored) {}
        if (proveedor != null) {
            float[] datosProveedor = CargarDatos.proveedorDatosListadoEstadisticas(proveedor.getCodigo());
            t_sumProvNom.setText(proveedor.getNombre());
            t_sumProvApe.setText(proveedor.getApellidos());
            t_sumProvDir.setText(proveedor.getDireccion());

            t_sumProvNumPiez.setText(String.format("%.0f", datosProveedor[0]));
            t_sumProvNumProy.setText(String.format("%.0f", datosProveedor[2]));
        }
    }
    public void vaciarDatosSuministrosProveedor() {
        t_sumProvNom.setText("");
        t_sumProvApe.setText("");
        t_sumProvDir.setText("");

        t_sumProvNumPiez.setText("");
        t_sumProvNumProy.setText("");
    }
    public void cargarDatosSuministrosPieza() {
        PiezasEntity pieza = null;
        try {
            pieza = piezas.get(cb_sumPza.getSelectedIndex());
        } catch (NullPointerException | IllegalArgumentException | ClassCastException | ObjectNotFoundException ignored) {}
        if (pieza != null) {
            float[] datosPieza = CargarDatos.piezaDatosListadoEstadisticas(pieza.getCodigo());
            t_sumPzaNom.setText(pieza.getNombre());
            t_sumPzaDesc.setText(pieza.getDescripcion());
            t_sumPzaPrecio.setText(String.format("%.2f", pieza.getPrecio()));

            t_sumPzaNumProv.setText(String.format("%.0f", datosPieza[0]));
            t_sumPzaTotal.setText(String.format("%.0f", datosPieza[1]));
            t_sumPzaNumProy.setText(String.format("%.0f", datosPieza[2]));
        }
    }
    public void vaciarDatosSuministrosPieza() {
        t_sumPzaNom.setText("");
        t_sumPzaDesc.setText("");
        t_sumPzaPrecio.setText("");

        t_sumPzaNumProv.setText("");
        t_sumPzaTotal.setText("");
        t_sumPzaNumProy.setText("");
    }
    public void cargarModeloCheckBoxSuministrosProveedor(ArrayList<ProveedoresEntity> proveedores) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (ProveedoresEntity prov : proveedores) {
            modelo.addElement(prov.getCodigo());
        }
        cb_sumProv.setModel(modelo);
        if (proveedores.size() > 0)
            cb_sumProv.setSelectedIndex(0);
        else
            vaciarDatosSuministrosProveedor();
    }
    public void cargarModeloCheckBoxSuministrosPieza(ArrayList<PiezasEntity> piezas) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (PiezasEntity pieza : piezas) {
            modelo.addElement(pieza.getCodigo());
        }
        cb_sumPza.setModel(modelo);
        if (piezas.size() > 0)
            cb_sumPza.setSelectedIndex(0);
        else
            vaciarDatosSuministrosPieza();
    }

    // Ventana estadísticas


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Cargar Ventanas
    // INICIO
    public void cargarVentanaInicio(int numGif) {
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        JLabel foto = new JLabel();
        panelDatos.add(foto);
        foto.setBounds(0, 0, dimPanelDatos.width, dimPanelDatos.height);

        String gif;
        if (numGif == 2) {
            System.out.println("Inicio: Tostada\n");
            gif = "./assets/2-tostada.gif";
        } else {
            System.out.println("Inicio: Cohete\n");
            gif = "./assets/1-cohete.gif";
        }

        foto.setIcon(new ImageIcon(gif));
    }

    // ADMINISTRACIÓN GESTIÓN
    public void cargarVentanaAdministracionGestion() {
        System.out.println("Gestión: Administración");
        System.out.println("  Cargando ventana");
        insertando = false;
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        // BOTONES EN EL TOP
        nuevaGestion = new JButton("Nueva Gestión");
        confBoton(nuevaGestion, dimBoton);
        panelDatos.add(nuevaGestion);
        nuevaGestion.setBounds(15, 20, dimBoton.width + 10, dimBoton.height);

        editarGestion = new JButton("Editar Gestión");
        confBoton(editarGestion, dimBoton);
        panelDatos.add(editarGestion);
        editarGestion.setBounds(185, 20, dimBoton.width + 10, dimBoton.height);

        borrarGestion = new JButton("Borrar Gestión");
        confBoton(borrarGestion, dimBoton);
        panelDatos.add(borrarGestion);
        borrarGestion.setBounds(355, 20, dimBoton.width + 10, dimBoton.height);

        listarGestiones = new JButton("Listar Gestiones");
        confBoton(listarGestiones, dimBoton);
        panelDatos.add(listarGestiones);
        listarGestiones.setBounds(525, 20, dimBoton.width + 10, dimBoton.height);

        // LINEA
        JPanel linea = new JPanel();
        linea.setLayout(null);
        linea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDatos.add(linea);
        linea.setBounds(0, 70, dimPanelDatos.width, 2);


        // Utilizaremos otro panel donde cargamos la ventana normal que sustituiremos por el listado cada vez que cliquemos en el botón
        cargarVentanaAdministracionGestionNormal();

        // LISTENERS
        nuevaGestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarGestion.setText("Insertar");
                editarGestion.setEnabled(true);
                borrarGestion.setText("Cancelar");
                borrarGestion.setEnabled(true);
                nuevaGestion.setEnabled(false);
                listarGestiones.setEnabled(false);

                gestionPrepararNuevaGestion();

            }
        });
        editarGestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editarGestion.getText().equalsIgnoreCase("Insertar")) {
                    if (gestionInsertarNuevaGestion())
                        reanudarVentanaAdminGestion();
                } else {
                    if (gestionEditarGestion())
                        reanudarVentanaAdminGestion();
                }
            }
        });
        borrarGestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (borrarGestion.getText().equalsIgnoreCase("Cancelar")) {
                    reanudarVentanaAdminGestion();
                } else {
                    try {
                        ProveedoresEntity prov = gestProveedores.get(cb_gestProveedor.getSelectedIndex());
                        PiezasEntity pieza = gestPiezas.get(cb_gestPieza.getSelectedIndex());
                        ProyectosEntity proyecto = gestProyectos.get(cb_gestProyecto.getSelectedIndex());
                        mostrarJOptionPaneEliminar(4, prov.getCodigo(), pieza.getCodigo(), proyecto.getCodigo());
                    } catch (NullPointerException | ObjectNotFoundException | IllegalArgumentException ignored) {}
                }
            }
        });
        listarGestiones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listarGestiones.getText().equalsIgnoreCase("Volver")) {
                    cargarVentanaAdministracionGestionNormal();
                    listarGestiones.setText("Listar Gestiones");
                    nuevaGestion.setEnabled(true);
                    editarGestion.setEnabled(true);
                    borrarGestion.setEnabled(true);
                    System.out.println("  Cargando Administración de Gestiones");
                    cargarVentanaAdministracionGestion();
                    System.out.println("  Ventana cargada\n");
                } else {
                    System.out.println("  Cargando Listado de Gestiones");
                    cargarVentanaAdministracionGestionListado();
                    System.out.println("  Listado Cargado\n");
                    listarGestiones.setText("Volver");
                    nuevaGestion.setEnabled(false);
                    editarGestion.setEnabled(false);
                    borrarGestion.setEnabled(false);
                }
            }
        });
        cb_gestProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!insertando)
                    gestionCargarDatosProveedor();
                else {
                    // CON LAS PIEZAS HACER UN METODO QUE RECOGA EL NUMERO DE COD PROV + COD PIEZA Y MIRE
                    // IF TOTALPIEZAPROV >= NUMPROYECTOS
                    ProveedoresEntity prov = null;
                    try {
                        prov = proveedoresNuevaGestion.get(cb_gestProveedor.getSelectedIndex());
                    } catch (NullPointerException | IllegalArgumentException ignored) {}
                    if (prov != null) {
                        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
                        piezasNuevaGestion = new ArrayList<>();
                        piezas = CargarDatos.piezas();
                        for (PiezasEntity pieza : piezas) {
                            if (CargarDatos.cargarNumProvPiezaGestion(prov.getCodigo(), pieza.getCodigo()) < CargarDatos.cargarNumProyectos()) {
                                piezasNuevaGestion.add(pieza);
                                modelo.addElement(pieza.getCodigo());
                            }
                        }
                        cb_gestPieza.setModel(modelo);
                        if (modelo.getSize() > 0)
                            cb_gestPieza.setSelectedIndex(0);
                        else {
                            t_gesNomPieza.setText("");
                            t_gesPrecioPieza.setText("");
                            t_gesNomProyecto.setText("");
                            t_gesCiudadProyecto.setText("");
                        }
                        gestionCargarDatosProveedor();
                    }
                }
            }
        });
        cb_gestPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!insertando)
                    gestionCargarDatosPieza();
                else {
                    PiezasEntity pieza = null;
                    ProveedoresEntity prov = null;
                    try {
                        prov = proveedoresNuevaGestion.get(cb_gestProveedor.getSelectedIndex());
                        pieza = piezasNuevaGestion.get(cb_gestPieza.getSelectedIndex());
                    } catch (NullPointerException | IllegalArgumentException ignored) {}
                    if (pieza != null && prov != null) {
                        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
                        proyectosNuevaGestion = new ArrayList<>();
                        proyectos = CargarDatos.proyectos();
                        for (ProyectosEntity proyecto: proyectos) {
                            if (CargarDatos.cargarNumProvPiezaProyGestion(prov.getCodigo(), pieza.getCodigo(), proyecto.getCodigo()) < proyectos.size()) {
                                if (!CargarDatos.gestionProvPiezProyExiste(prov.getCodigo(), pieza.getCodigo(), proyecto.getCodigo())) {
                                    proyectosNuevaGestion.add(proyecto);
                                    modelo.addElement(proyecto.getCodigo());
                                }
                            }
                        }
                        cb_gestProyecto.setModel(modelo);
                        if (modelo.getSize() > 0) {
                            cb_gestProyecto.setSelectedIndex(0);
                            t_gesCantidad.setText("1");
                        } else {
                            t_gesNomProyecto.setText("");
                            t_gesCiudadProyecto.setText("");
                        }
                        gestionCargarDatosPieza();
                    }
                }
            }
        });
        cb_gestProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!insertando)
                    gestionCargarDatosProyecto();
                else {
                    ProyectosEntity proyecto = null;
                    try {
                        proyecto = proyectosNuevaGestion.get(cb_gestProyecto.getSelectedIndex());
                        gestionCargarDatosProyecto();
                    } catch (NullPointerException | IllegalArgumentException ignored) {}
                }
            }
        });

        recargarDatosVentanaAdminGestion();
        System.out.println("  Ventana cargada\n");
    }
    public void cargarVentanaAdministracionGestionNormal() {
        int anchuraLabel = 80;
        int anchuraComboBox = 120;
        int posYPanel = 100;

        try {
            gesPanel.removeAll();
            gesPanel.repaint();
            panelDatos.remove(gesPanel);
        } catch (Exception ignored) { }
        gesPanel = new JPanel();
        gesPanel.setLayout(null);
        confPanel(gesPanel);
        panelDatos.add(gesPanel);
        gesPanel.setBounds(0, posYPanel, dimPanelDatos.width, dimPanelDatos.height - posYPanel);

        // PROVEEDOR
        JLabel l_proveedor = new JLabel("Proveedor", SwingConstants.RIGHT);
        confLabel(l_proveedor);
        gesPanel.add(l_proveedor);
        l_proveedor.setBounds(15, (110 - posYPanel), anchuraLabel, dimLabel.height);

        cb_gestProveedor = new JComboBox<String>();
        gesPanel.add(cb_gestProveedor);
        cb_gestProveedor.setBounds((15 + anchuraLabel + 10), (108 - posYPanel), anchuraComboBox, dimTextField.height);
        cb_gestProveedor.addItem("Proveedores");

        t_gesNomProveedor = new JTextField();
        gesPanel.add(t_gesNomProveedor);
        t_gesNomProveedor.setBounds((15 + anchuraLabel + 10 + anchuraComboBox + 10),
                (108 - posYPanel), (650 - (15 + anchuraLabel + 10 + anchuraComboBox + 10)), dimTextField.height);
        t_gesNomProveedor.setEditable(false);

        t_gesDirProveedor = new JTextField();
        gesPanel.add(t_gesDirProveedor);
        t_gesDirProveedor.setBounds((15 + anchuraLabel + 10 + anchuraComboBox + 10),
                (140 - posYPanel), (650 - (15 + anchuraLabel + 10 + anchuraComboBox + 10)), dimTextField.height);
        t_gesDirProveedor.setEditable(false);

        // PIEZA
        JLabel l_pieza = new JLabel("Pieza", SwingConstants.RIGHT);
        confLabel(l_pieza);
        gesPanel.add(l_pieza);
        l_pieza.setBounds(15, (210 - posYPanel), anchuraLabel, dimLabel.height);

        cb_gestPieza = new JComboBox<String>();
        gesPanel.add(cb_gestPieza);
        cb_gestPieza.setBounds((15 + anchuraLabel + 10), (208 - posYPanel), anchuraComboBox, dimTextField.height);
        cb_gestPieza.addItem("Piezas");

        t_gesNomPieza = new JTextField();
        gesPanel.add(t_gesNomPieza);
        t_gesNomPieza.setBounds((15 + anchuraLabel + 10 + anchuraComboBox + 10),
                (208 - posYPanel), (650 - (15 + anchuraLabel + 10 + anchuraComboBox + 10)), dimTextField.height);
        t_gesNomPieza.setEditable(false);

        t_gesPrecioPieza = new JTextField();
        gesPanel.add(t_gesPrecioPieza);
        t_gesPrecioPieza.setBounds((15 + anchuraLabel + 10 + anchuraComboBox + 10),
                (240 - posYPanel), 100, dimTextField.height);
        t_gesPrecioPieza.setEditable(false);

        // PROYECTO
        JLabel l_proyecto = new JLabel("Proyecto", SwingConstants.RIGHT);
        confLabel(l_proyecto);
        gesPanel.add(l_proyecto);
        l_proyecto.setBounds(15, (310 - posYPanel), anchuraLabel, dimLabel.height);

        cb_gestProyecto = new JComboBox<String>();
        gesPanel.add(cb_gestProyecto);
        cb_gestProyecto.setBounds((15 + anchuraLabel + 10), (308 - posYPanel), anchuraComboBox, dimTextField.height);
        cb_gestProyecto.addItem("Proyectos");

        t_gesNomProyecto = new JTextField();
        gesPanel.add(t_gesNomProyecto);
        t_gesNomProyecto.setBounds((15 + anchuraLabel + 10 + anchuraComboBox + 10),
                (308 - posYPanel), (650 - (15 + anchuraLabel + 10 + anchuraComboBox + 10)), dimTextField.height);
        t_gesNomProyecto.setEditable(false);

        t_gesCiudadProyecto = new JTextField();
        gesPanel.add(t_gesCiudadProyecto);
        t_gesCiudadProyecto.setBounds((15 + anchuraLabel + 10 + anchuraComboBox + 10),
                (340 - posYPanel), (650 - (15 + anchuraLabel + 10 + anchuraComboBox + 10)), dimTextField.height);
        t_gesCiudadProyecto.setEditable(false);

        // CANTIDAD
        JLabel l_cantidad = new JLabel("Cantidad", SwingConstants.RIGHT);
        confLabel(l_cantidad);
        gesPanel.add(l_cantidad);
        l_cantidad.setBounds(15, (410 - posYPanel), anchuraLabel, dimLabel.height);

        t_gesCantidad = new JTextField();
        gesPanel.add(t_gesCantidad);
        t_gesCantidad.setBounds((15 + anchuraLabel + 10), (408 - posYPanel), anchuraComboBox, dimTextField.height);
        //t_gesCantidad.setEditable(false);
    }
    public void cargarVentanaAdministracionGestionListado() {
        int posYPanel = 72;

        try {
            gesPanel.removeAll();
            gesPanel.repaint();
            panelDatos.remove(gesPanel);
        } catch (Exception ignored) { }
        gesPanel = new JPanel();
        gesPanel.setLayout(null);
        confPanel(gesPanel);
        panelDatos.add(gesPanel);

        gesPanel.setBounds(0, posYPanel, dimPanelDatos.width, dimPanelDatos.height - posYPanel);

        gestiones = CargarDatos.gestiones();

        JTable tablaListadoGestiones = new JTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(0xd4d4d4);
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())) {
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp.setBackground(bg);
                    bg = null;
                }
                return returnComp;
            }
        };

        ModeloListadoGestiones mlg = new ModeloListadoGestiones(gestiones);
        tablaListadoGestiones.setModel(mlg);
        //tablaListadoGestiones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //gesPanel.add(tablaListadoGestiones);
        //tablaListadoGestiones.setBounds(0, 0, dimPanelDatos.width, (dimPanelDatos.height - posYPanel));

        JScrollPane scrollPane = new JScrollPane(tablaListadoGestiones);
        gesPanel.add(scrollPane);
        scrollPane.setBounds(1, 0, dimPanelDatos.width - 1, dimPanelDatos.height - posYPanel);
        int[] widthsListadoGestiones = { 200, 200, 200, dimPanelDatos.width - 1 };
        resizeColumnWidth(tablaListadoGestiones, widthsListadoGestiones);
    }

    // SUMINISTROS
    public void cargarVentanaSuministros() {
        System.out.println("Estadísticas: Suministros");
        System.out.println("  Cargando datos");
        if (checkSumProvMarcado)
            proveedores = CargarDatos.proveedoresGestion();
        else
            proveedores = CargarDatos.proveedores();
        if (checkSumPzaMarcado)
            piezas = CargarDatos.piezasGestion();
        else
            piezas = CargarDatos.piezas();
        System.out.println("  Datos cargados:\n" +
                "\tNum.Proveedores: " + proveedores.size() +
                "\n\tNum.Piezas: " + piezas.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        // PARTE PROVEEDORES
        int posY = 60;
        int anchuraLabel = 100;

        JLabel l_provTitulo = new JLabel("SUMINISTROS POR PROVEEDOR", SwingConstants.LEFT);
        l_provTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        l_provTitulo.setForeground(Color.DARK_GRAY);
        confLabel(l_provTitulo);
        panelDatos.add(l_provTitulo);
        l_provTitulo.setBounds(10, 10, 300, dimLabel.height);

        JLabel l_proveedor = new JLabel("Proveedor", SwingConstants.RIGHT);
        confLabel(l_proveedor);
        panelDatos.add(l_proveedor);
        l_proveedor.setBounds(0, posY, anchuraLabel, dimLabel.height);

        cb_sumProv = new JComboBox<>();
        panelDatos.add(cb_sumProv);
        cb_sumProv.setBounds(anchuraLabel + 10, posY - 2, 120, dimTextField.height);

        anchuraLabel = 140;

        JLabel l_numPiezas = new JLabel("Num. Piezas distintas", SwingConstants.RIGHT);
        confLabel(l_numPiezas);
        panelDatos.add(l_numPiezas);
        l_numPiezas.setBounds(0, posY + 40, anchuraLabel, dimLabel.height);

        t_sumProvNumPiez = new JTextField();
        panelDatos.add(t_sumProvNumPiez);
        t_sumProvNumPiez.setBounds(anchuraLabel + 10, posY + 40 - 2, 80, dimTextField.height);
        t_sumProvNumPiez.setEditable(false);

        JLabel l_numProy = new JLabel("Num. Proyectos", SwingConstants.RIGHT);
        confLabel(l_numProy);
        panelDatos.add(l_numProy);
        l_numProy.setBounds(0, posY + 70, anchuraLabel, dimLabel.height);

        t_sumProvNumProy = new JTextField();
        panelDatos.add(t_sumProvNumProy);
        t_sumProvNumProy.setBounds(anchuraLabel + 10, posY + 70 - 2, 80, dimTextField.height);
        t_sumProvNumProy.setEditable(false);

        b_sumProvPiezasSuministradas = new JButton("Ver listado proveedores");
        confBoton(b_sumProvPiezasSuministradas, dimBoton);
        panelDatos.add(b_sumProvPiezasSuministradas);
        b_sumProvPiezasSuministradas.setBounds(10, posY + 100, 222, dimBoton.height);

        JLabel l_provFiltrar1 = new JLabel("Filtrar proveedores por aquellos", SwingConstants.RIGHT);
        confLabel(l_provFiltrar1);
        panelDatos.add(l_provFiltrar1);
        l_provFiltrar1.setBounds(dimPanelDatos.width - 40 - 195, posY - 47, 190, dimLabel.height);

        JLabel l_provFiltrar2 = new JLabel("que participen en gestiones", SwingConstants.RIGHT);
        confLabel(l_provFiltrar2);
        panelDatos.add(l_provFiltrar2);
        l_provFiltrar2.setBounds(dimPanelDatos.width - 40 - 195, posY - 33, 190, dimLabel.height);

        check_sumProv = new JCheckBox();
        panelDatos.add(check_sumProv);
        check_sumProv.setBounds(dimPanelDatos.width - 40, posY - 40, 20, 20);
        if (checkSumProvMarcado)
            check_sumProv.setSelected(true);

        JPanel datosProveedor = new JPanel();
        datosProveedor.setLayout(null);
        Border blackline = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Datos Proveedor");
        datosProveedor.setBorder(blackline);
        panelDatos.add(datosProveedor);
        datosProveedor.setBounds(250, posY - 10, (700 - 250 - 20), 153);

        t_sumProvNom = new JTextField();
        datosProveedor.add(t_sumProvNom);
        t_sumProvNom.setBounds(10, 27, (700 - 250 - 20 - 20), dimTextField.height);
        t_sumProvNom.setEditable(false);

        t_sumProvApe = new JTextField();
        datosProveedor.add(t_sumProvApe);
        t_sumProvApe.setBounds(10, 67, (700 - 250 - 20 - 20), dimTextField.height);
        t_sumProvApe.setEditable(false);

        t_sumProvDir = new JTextField();
        datosProveedor.add(t_sumProvDir);
        t_sumProvDir.setBounds(10, 107, (700 - 250 - 20 - 20), dimTextField.height);
        t_sumProvDir.setEditable(false);

        //t_sumProvNom.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // SEPARADOR
        JPanel lineaSeparadora = new JPanel();
        lineaSeparadora.setLayout(null);
        lineaSeparadora.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDatos.add(lineaSeparadora);
        lineaSeparadora.setBounds(0, 226, dimPanelDatos.width, 4);
        lineaSeparadora.setBackground(Color.DARK_GRAY);

        // PARTE PIEZAS
        JLabel l_pzaTitulo = new JLabel("SUMINISTROS POR PIEZA", SwingConstants.LEFT);
        l_pzaTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        l_pzaTitulo.setForeground(Color.DARK_GRAY);
        confLabel(l_pzaTitulo);
        panelDatos.add(l_pzaTitulo);
        l_pzaTitulo.setBounds(10, 240, 300, dimLabel.height);

        posY = 290;

        anchuraLabel = 100;
        JLabel l_pieza = new JLabel("Pieza", SwingConstants.RIGHT);
        confLabel(l_pieza);
        panelDatos.add(l_pieza);
        l_pieza.setBounds(0, posY, anchuraLabel, dimLabel.height);

        cb_sumPza = new JComboBox<>();
        panelDatos.add(cb_sumPza);
        cb_sumPza.setBounds(anchuraLabel + 10, posY - 2, 120, dimTextField.height);

        anchuraLabel = 140;

        JLabel l_pzaNumProy = new JLabel("Num. Proyectos", SwingConstants.RIGHT);
        confLabel(l_pzaNumProy);
        panelDatos.add(l_pzaNumProy);
        l_pzaNumProy.setBounds(0, posY + 35, anchuraLabel, dimLabel.height);

        t_sumPzaNumProy = new JTextField();
        panelDatos.add(t_sumPzaNumProy);
        t_sumPzaNumProy.setBounds(anchuraLabel + 10, posY + 35 - 1, 80, dimTextField.height);
        t_sumPzaNumProy.setEditable(false);

        JLabel l_pzaNumProv1 = new JLabel("Num. Proveedores", SwingConstants.RIGHT);
        confLabel(l_pzaNumProv1);
        panelDatos.add(l_pzaNumProv1);
        l_pzaNumProv1.setBounds(0, posY + 72 - 7, anchuraLabel, dimLabel.height);
        JLabel l_pzaNumProv2 = new JLabel("que la suministran", SwingConstants.RIGHT);
        confLabel(l_pzaNumProv2);
        panelDatos.add(l_pzaNumProv2);
        l_pzaNumProv2.setBounds(0, posY + 72 + 7, anchuraLabel, dimLabel.height);

        t_sumPzaNumProv = new JTextField();
        panelDatos.add(t_sumPzaNumProv);
        t_sumPzaNumProv.setBounds(anchuraLabel + 10, posY + 72 - 1, 80, dimTextField.height);
        t_sumPzaNumProv.setEditable(false);

        JLabel l_pzaCantTotal1 = new JLabel("Cantidad total de", SwingConstants.RIGHT);
        confLabel(l_pzaCantTotal1);
        panelDatos.add(l_pzaCantTotal1);
        l_pzaCantTotal1.setBounds(0, posY + 110 - 7, anchuraLabel, dimLabel.height);
        JLabel l_pzaCantTotal2 = new JLabel("veces suministrada", SwingConstants.RIGHT);
        confLabel(l_pzaCantTotal2);
        panelDatos.add(l_pzaCantTotal2);
        l_pzaCantTotal2.setBounds(0, posY + 110 + 7, anchuraLabel, dimLabel.height);

        t_sumPzaTotal = new JTextField();
        panelDatos.add(t_sumPzaTotal);
        t_sumPzaTotal.setBounds(anchuraLabel + 10, posY + 110 - 1, 80, dimTextField.height);
        t_sumPzaTotal.setEditable(false);

        b_sumPzaPiezasSuministradas = new JButton("Ver listado piezas");
        confBoton(b_sumPzaPiezasSuministradas, dimBoton);
        panelDatos.add(b_sumPzaPiezasSuministradas);
        b_sumPzaPiezasSuministradas.setBounds(10, posY + 150, 222, dimBoton.height);

        JLabel l_pzaFiltrar1 = new JLabel("Filtrar piezas por aquellas", SwingConstants.RIGHT);
        confLabel(l_pzaFiltrar1);
        panelDatos.add(l_pzaFiltrar1);
        l_pzaFiltrar1.setBounds(dimPanelDatos.width - 40 - 195, posY - 47, 190, dimLabel.height);

        JLabel l_pzaFiltrar2 = new JLabel("que participen en gestiones", SwingConstants.RIGHT);
        confLabel(l_pzaFiltrar2);
        panelDatos.add(l_pzaFiltrar2);
        l_pzaFiltrar2.setBounds(dimPanelDatos.width - 40 - 195, posY - 33, 190, dimLabel.height);

        check_sumPza = new JCheckBox();
        panelDatos.add(check_sumPza);
        check_sumPza.setBounds(dimPanelDatos.width - 40, posY - 40, 20, 20);
        if (checkSumPzaMarcado)
            check_sumPza.setSelected(true);

        JPanel datosPieza = new JPanel();
        datosPieza.setLayout(null);
        Border blackline2 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Datos Pieza");
        datosPieza.setBorder(blackline2);
        panelDatos.add(datosPieza);
        datosPieza.setBounds(250, posY - 10, (700 - 250 - 20), 202);

        t_sumPzaNom = new JTextField();
        datosPieza.add(t_sumPzaNom);
        t_sumPzaNom.setBounds(10, 27, (700 - 250 - 20 - 20), dimTextField.height);
        t_sumPzaNom.setEditable(false);

        t_sumPzaPrecio = new JTextField();
        datosPieza.add(t_sumPzaPrecio);
        t_sumPzaPrecio.setBounds(10, 67, 80, dimTextField.height);
        t_sumPzaPrecio.setEditable(false);

        t_sumPzaDesc = new JTextArea();
        datosPieza.add(t_sumPzaDesc);
        t_sumPzaDesc.setBounds(10, 107, (700 - 250 - 20 - 20), dimTextArea.height);
        t_sumPzaDesc.setEditable(false);
        t_sumPzaDesc.setOpaque(false);
        t_sumPzaDesc.setBorder(BorderFactory.createLineBorder(new Color(0xadcfdb)));

        cb_sumProv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosSuministrosProveedor();
            }
        });
        cb_sumPza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosSuministrosPieza();
            }
        });
        b_sumProvPiezasSuministradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanaListadoEstadisticas(2, 1);
            }
        });
        b_sumPzaPiezasSuministradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanaListadoEstadisticas(1, 1);
            }
        });
        check_sumProv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkSumProvMarcado) {
                    checkSumProvMarcado = true;
                    System.out.println("  Filtrando proveedores");
                    proveedores = CargarDatos.proveedoresGestion();
                } else {
                    checkSumProvMarcado = false;
                    System.out.println("  Quitando el filtro de los proveedores");
                    proveedores = CargarDatos.proveedores();
                }
                cargarModeloCheckBoxSuministrosProveedor(proveedores);
                System.out.println("  Datos cargados: " + proveedores.size() + "\n");
            }
        });
        check_sumPza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkSumPzaMarcado) {
                    checkSumPzaMarcado = true;
                    System.out.println("  Filtrando piezas");
                    piezas = CargarDatos.piezasGestion();
                } else {
                    checkSumPzaMarcado = false;
                    System.out.println("  Quitando el filtro de las piezas");
                    piezas = CargarDatos.piezas();
                }
                cargarModeloCheckBoxSuministrosPieza(piezas);
                System.out.println("  Datos cargados: " + piezas.size() + "\n");
            }
        });

        cargarModeloCheckBoxSuministrosProveedor(proveedores);
        cargarModeloCheckBoxSuministrosPieza(piezas);

        System.out.println("   Ventana cargada\n");
    }

    // ESTADISTICAS
    public void cargarVentanaEstadisticas() {
        System.out.println("Estadísticas: Generales");
        System.out.println("  Cargando datos");

        String[] proveedorConMasPiezasDistintas = CargarDatos.cargarDatosProvConMasPiezasDistintas();
        String[] proveedorConMasProyectosDistintos = CargarDatos.cargarDatosProvConMasProyectosDistintos();
        String[] proveedorConMasCantidadPiezas = CargarDatos.cargarDatosProvConMasCantidadPiezas();

        String[] piezaMasCantidadVecesSuministrada = CargarDatos.cargarDatosPiezaMasCantidadVecesSuministrada();
        String[] piezaMasVecesDistintasSuministrada = CargarDatos.cargarDatosPiezaMasVecesDistintasSuministrada();

        System.out.println("  Todos los datos cargados");

        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        JLabel l_titulo = new JLabel("RESÚMENES ESTADÍSTICOS", SwingConstants.CENTER);
        l_titulo.setFont(new Font("Arial", Font.BOLD, 25));
        l_titulo.setForeground(Color.DARK_GRAY);
        panelDatos.add(l_titulo);
        l_titulo.setBounds(0, 15, dimPanelDatos.width, dimLabel.height + 10);

        JLabel l_subtitulo = new JLabel("PIEZAS, PROYECTOS Y PROVEEDORES", SwingConstants.CENTER);
        l_subtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        l_subtitulo.setForeground(Color.DARK_GRAY);
        panelDatos.add(l_subtitulo);
        l_subtitulo.setBounds(0, 42, dimPanelDatos.width, dimLabel.height);

        JPanel lineaSeparadora = new JPanel();
        lineaSeparadora.setLayout(null);
        lineaSeparadora.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDatos.add(lineaSeparadora);
        lineaSeparadora.setBounds(0, 75, dimPanelDatos.width, 8);
        lineaSeparadora.setBackground(Color.DARK_GRAY);

        int margen = 20;
        int anchuraLabel = 300;

        int posY = 90;

        b_estNumPiezasProyecto = new JButton("Número de piezas y cantidad de piezas suministradas en proyectos");
        confBoton(b_estNumPiezasProyecto, dimBoton);
        panelDatos.add(b_estNumPiezasProyecto);
        b_estNumPiezasProyecto.setBounds(margen, posY, dimPanelDatos.width - (margen * 2), dimBoton.height);

        posY += 50;

        JLabel l_piezaMasCant = new JLabel("Pieza suministrada más cantidad de veces en total:", SwingConstants.LEFT);
        confLabel(l_piezaMasCant);
        panelDatos.add(l_piezaMasCant);
        l_piezaMasCant.setBounds(margen, posY, anchuraLabel, dimLabel.height);

        t_estCodPiezaMasCantidad = new JTextField();
        panelDatos.add(t_estCodPiezaMasCantidad);
        t_estCodPiezaMasCantidad.setBounds(margen + anchuraLabel, posY - 2, 55, dimTextField.height);
        t_estCodPiezaMasCantidad.setEditable(false);
        t_estCodPiezaMasCantidad.setText(piezaMasCantidadVecesSuministrada[0]);
        t_estCodPiezaMasCantidad.setHorizontalAlignment(SwingConstants.CENTER);

        t_estNombrePiezaMasCantidad = new JTextField();
        panelDatos.add(t_estNombrePiezaMasCantidad);
        t_estNombrePiezaMasCantidad.setBounds(margen + anchuraLabel + 55 + 10, posY - 2, dimPanelDatos.width - (margen + anchuraLabel + 10 + 55 + 20), dimTextField.height);
        t_estNombrePiezaMasCantidad.setEditable(false);
        t_estNombrePiezaMasCantidad.setText(piezaMasCantidadVecesSuministrada[1]);

        posY += 30;

        JLabel l_piezaMasProy = new JLabel("Pieza suministrada a más proyectos distintos:", SwingConstants.LEFT);
        confLabel(l_piezaMasProy);
        panelDatos.add(l_piezaMasProy);
        l_piezaMasProy.setBounds(margen, posY, anchuraLabel, dimLabel.height);

        t_estCodPiezaMasProyectos = new JTextField();
        panelDatos.add(t_estCodPiezaMasProyectos);
        t_estCodPiezaMasProyectos.setBounds(margen + anchuraLabel, posY - 2, 55, dimTextField.height);
        t_estCodPiezaMasProyectos.setEditable(false);
        t_estCodPiezaMasProyectos.setText(piezaMasVecesDistintasSuministrada[0]);
        t_estCodPiezaMasProyectos.setHorizontalAlignment(SwingConstants.CENTER);

        t_estNombrePiezaMasProyectos = new JTextField();
        panelDatos.add(t_estNombrePiezaMasProyectos);
        t_estNombrePiezaMasProyectos.setBounds(margen + anchuraLabel + 55 + 10, posY - 2, dimPanelDatos.width - (margen + anchuraLabel + 10 + 55 + 20), dimTextField.height);
        t_estNombrePiezaMasProyectos.setEditable(false);
        t_estNombrePiezaMasProyectos.setText(piezaMasVecesDistintasSuministrada[1]);

        posY += 40;

        JPanel lineaSeparadora2 = new JPanel();
        lineaSeparadora2.setLayout(null);
        lineaSeparadora2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDatos.add(lineaSeparadora2);
        lineaSeparadora2.setBounds(0, posY, dimPanelDatos.width, 8);
        lineaSeparadora2.setBackground(Color.DARK_GRAY);

        posY += 15;

        b_estNumPiezasProveedor = new JButton("Número de piezas y cantidad de piezas suministradas por proveedor");
        confBoton(b_estNumPiezasProveedor, dimBoton);
        panelDatos.add(b_estNumPiezasProveedor);
        b_estNumPiezasProveedor.setBounds(margen, posY, dimPanelDatos.width - (margen * 2), dimBoton.height);

        posY += 55;
        anchuraLabel = 330;

        JLabel l_provMasCant = new JLabel("Proveedor que ha suministrado más cantidad de piezas:", SwingConstants.LEFT);
        confLabel(l_provMasCant);
        panelDatos.add(l_provMasCant);
        l_provMasCant.setBounds(margen, posY, anchuraLabel, dimLabel.height);

        t_estCodProvMasCantPiezas = new JTextField();
        panelDatos.add(t_estCodProvMasCantPiezas);
        t_estCodProvMasCantPiezas.setBounds(margen + anchuraLabel, posY - 2, 55, dimTextField.height);
        t_estCodProvMasCantPiezas.setEditable(false);
        t_estCodProvMasCantPiezas.setText(proveedorConMasCantidadPiezas[0]);
        t_estCodProvMasCantPiezas.setHorizontalAlignment(SwingConstants.CENTER);

        t_estNombreProvMasCantPiezas = new JTextField();
        panelDatos.add(t_estNombreProvMasCantPiezas);
        t_estNombreProvMasCantPiezas.setBounds(margen + anchuraLabel + 55 + 10, posY - 2, dimPanelDatos.width - (margen + anchuraLabel + 10 + 55 + 20), dimTextField.height);
        t_estNombreProvMasCantPiezas.setEditable(false);
        t_estNombreProvMasCantPiezas.setText(proveedorConMasCantidadPiezas[1]);

        posY += 30;

        JLabel l_provMasProy = new JLabel("Proveedor que ha participado en distintos proyectos:", SwingConstants.LEFT);
        confLabel(l_provMasProy);
        panelDatos.add(l_provMasProy);
        l_provMasProy.setBounds(margen, posY, anchuraLabel, dimLabel.height);

        t_estCodProvMasProy = new JTextField();
        panelDatos.add(t_estCodProvMasProy);
        t_estCodProvMasProy.setBounds(margen + anchuraLabel, posY - 2, 55, dimTextField.height);
        t_estCodProvMasProy.setEditable(false);
        t_estCodProvMasProy.setText(proveedorConMasProyectosDistintos[0]);
        t_estCodProvMasProy.setHorizontalAlignment(SwingConstants.CENTER);

        t_estNombreProvMasProy = new JTextField();
        panelDatos.add(t_estNombreProvMasProy);
        t_estNombreProvMasProy.setBounds(margen + anchuraLabel + 55 + 10, posY - 2, dimPanelDatos.width - (margen + anchuraLabel + 10 + 55 + 20), dimTextField.height);
        t_estNombreProvMasProy.setEditable(false);
        t_estNombreProvMasProy.setText(proveedorConMasProyectosDistintos[1]);

        posY += 30;

        JLabel l_provMasPiezasDistintas = new JLabel("Proveedor que ha suministrado más piezas distintas:", SwingConstants.LEFT);
        confLabel(l_provMasPiezasDistintas);
        panelDatos.add(l_provMasPiezasDistintas);
        l_provMasPiezasDistintas.setBounds(margen, posY, anchuraLabel, dimLabel.height);

        t_estCodProvMasPiezasDistintas = new JTextField();
        panelDatos.add(t_estCodProvMasPiezasDistintas);
        t_estCodProvMasPiezasDistintas.setBounds(margen + anchuraLabel, posY - 2, 55, dimTextField.height);
        t_estCodProvMasPiezasDistintas.setEditable(false);
        t_estCodProvMasPiezasDistintas.setText(proveedorConMasPiezasDistintas[0]);
        t_estCodProvMasPiezasDistintas.setHorizontalAlignment(SwingConstants.CENTER);

        t_estNombreProvMasPiezasDistintas = new JTextField();
        panelDatos.add(t_estNombreProvMasPiezasDistintas);
        t_estNombreProvMasPiezasDistintas.setBounds(margen + anchuraLabel + 55 + 10, posY - 2, dimPanelDatos.width - (margen + anchuraLabel + 10 + 55 + 20), dimTextField.height);
        t_estNombreProvMasPiezasDistintas.setEditable(false);
        t_estNombreProvMasPiezasDistintas.setText(proveedorConMasPiezasDistintas[1]);

        posY += 40;

        JLabel piePagina = new JLabel("Fin del proyecto", SwingConstants.CENTER);
        panelDatos.add(piePagina);
        piePagina.setBounds(0, posY + 45, dimPanelDatos.width, 60);
        piePagina.setFont(new Font("Calibri", Font.BOLD, 35));
        piePagina.setForeground(Color.WHITE);

        JPanel fondoPiePagina = new JPanel();
        fondoPiePagina.setLayout(null);
        fondoPiePagina.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDatos.add(fondoPiePagina);
        fondoPiePagina.setBounds(0, posY, dimPanelDatos.width, dimPanelDatos.height - posY);
        fondoPiePagina.setBackground(Color.DARK_GRAY);

        b_estNumPiezasProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanaListadoEstadisticas(1, 2);
            }
        });
        b_estNumPiezasProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanaListadoEstadisticas(2, 2);
            }
        });

        System.out.println("  Ventana cargada\n");

        if (CargarDatos.cargarNumGestiones() <= 0) {
            JButton ok = new JButton("Ok");
            JButton volverAInicio = new JButton("Volver a inicio");
            JButton nuevaGestion = new JButton("Ir al panel");
            ok.setFocusPainted(false);
            volverAInicio.setFocusPainted(false);
            nuevaGestion.setFocusPainted(false);

            Object[] options = { ok, volverAInicio, nuevaGestion };

            String titulo = "Sin datos";
            String mensaje = "No existen gestiones, por lo que no se van a generar los datos de las\n" +
                    "estadísticas. Para generar nuevas gestiones ve al panel de administración de gestiones.";

            final JOptionPane pane = new JOptionPane(mensaje,
                    JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
            JDialog dialog = pane.createDialog(titulo);
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            volverAInicio.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    cargarVentanaInicio(1);
                    numVentana = 1;
                }
            });
            nuevaGestion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    cargarVentanaAdministracionGestion();
                    numVentana = 14;
                }
            });
            dialog.setVisible(true);
        }
    }
    public void cargarVentanaListadoEstadisticas(int tabla, int volverA) {
        String subtitulo;
        if (tabla == 1) {
            System.out.println("Estadísticas: Listado Piezas de las Gestiones + Resumen Histórico");
            subtitulo = "PIEZAS: DATOS Y RESUMEN HISTÓRICO";
        } else {
            System.out.println("Estadísticas: Listado Proveedores de las Gestiones + Resumen Histórico");
            subtitulo = "PROVEEDORES: DATOS Y RESUMEN HISTÓRICO";
        }
        System.out.println("  Cargando datos");

        ArrayList<ProveedoresEntity> proveedoresGestion = new ArrayList<>();
        ArrayList<PiezasEntity> piezasGestion = new ArrayList<>();

        if (tabla == 1) {
            if (filtrarEstadisticas)
                piezasGestion = CargarDatos.piezasGestion();
            else
                piezasGestion = CargarDatos.piezas();
            System.out.println("  Datos cargados: " + piezasGestion.size());
        }
        else {
            if (filtrarEstadisticas)
                proveedoresGestion = CargarDatos.proveedoresGestion();
            else
                proveedoresGestion = CargarDatos.proveedores();
            System.out.println("  Datos cargados: " + proveedoresGestion.size());
        }
        System.out.println("  Cargando ventana");

        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);


        JLabel l_titulo = new JLabel("RESÚMENES ESTADÍSTICOS", SwingConstants.CENTER);
        l_titulo.setFont(new Font("Arial", Font.BOLD, 25));
        l_titulo.setForeground(Color.DARK_GRAY);
        panelDatos.add(l_titulo);
        l_titulo.setBounds(0, 15, dimPanelDatos.width, dimLabel.height + 10);

        JLabel l_subtitulo = new JLabel(subtitulo, SwingConstants.CENTER);
        l_subtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        l_subtitulo.setForeground(Color.DARK_GRAY);
        panelDatos.add(l_subtitulo);
        l_subtitulo.setBounds(0, 42, dimPanelDatos.width, dimLabel.height);

        JPanel lineaSeparadora = new JPanel();
        lineaSeparadora.setLayout(null);
        lineaSeparadora.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDatos.add(lineaSeparadora);
        lineaSeparadora.setBounds(0, 75, dimPanelDatos.width, 8);
        lineaSeparadora.setBackground(Color.DARK_GRAY);

        int posY = 80;
        int margen = 10;

        JTable table = new JTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(0xd4d4d4);
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())) {
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp.setBackground(bg);
                    bg = null;
                }
                return returnComp;
            }
        };
        if (tabla == 2) {
            ModeloListadoEstProveedores mlp = new ModeloListadoEstProveedores(proveedoresGestion);
            table.setModel(mlp);

            JScrollPane scrollPane = new JScrollPane(table);
            panelDatos.add(scrollPane);
            scrollPane.setBounds(1, posY, dimPanelDatos.width - 1, dimPanelDatos.height - posY - 55 - 10);
            int[] widthsListadoProveedores = {70, 129, 200, 100, 100, dimPanelDatos.width - 1};
            resizeColumnWidth(table, widthsListadoProveedores);
        } else {
            ModeloListadoEstPiezas mlp = new ModeloListadoEstPiezas(piezasGestion);
            table.setModel(mlp);

            JScrollPane scrollPane = new JScrollPane(table);
            panelDatos.add(scrollPane);
            scrollPane.setBounds(1, posY, dimPanelDatos.width - 1, dimPanelDatos.height - posY - 55 - 10);
            int[] widthsListadoPiezas = {70, 219, 50, 120, 120, dimPanelDatos.width - 1};
            resizeColumnWidth(table, widthsListadoPiezas);
        }
        posY = dimPanelDatos.height - 55;

        JButton b_volver = new JButton("Volver");
        confBoton(b_volver, dimBoton);
        panelDatos.add(b_volver);
        b_volver.setBounds(dimPanelDatos.width - 140, posY, 80, dimBoton.height);

        JLabel l_provFiltrar1 = new JLabel("Filtrar estadísticas por aquellos", SwingConstants.RIGHT);
        confLabel(l_provFiltrar1);
        panelDatos.add(l_provFiltrar1);
        l_provFiltrar1.setBounds(60, posY + 3, 210, dimLabel.height);

        JLabel l_provFiltrar2 = new JLabel("datos que participen en gestiones", SwingConstants.RIGHT);
        confLabel(l_provFiltrar2);
        panelDatos.add(l_provFiltrar2);
        l_provFiltrar2.setBounds(60, posY + 17, 210, dimLabel.height);

        JCheckBox check_filtrarEstadisticas = new JCheckBox();
        panelDatos.add(check_filtrarEstadisticas);
        check_filtrarEstadisticas.setBounds(60 + 215, posY + 10, 20, 20);
        if (filtrarEstadisticas)
            check_filtrarEstadisticas.setSelected(true);

        b_volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (volverA == 1)
                    cargarVentanaSuministros();
                else
                    cargarVentanaEstadisticas();
            }
        });
        check_filtrarEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filtrarEstadisticas) {
                    filtrarEstadisticas = false;
                } else {
                    filtrarEstadisticas = true;
                }
                cargarVentanaListadoEstadisticas(tabla, volverA);
            }
        });

        System.out.println("  Ventana cargada\n");
    }

    // PIEZA
    public void cargarVentanaPiezaGestion() {
        System.out.println("Piezas: Gestión");
        System.out.println("  Cargando datos");
        piezas = CargarDatos.piezas();
        System.out.println("  Datos cargados: " + piezas.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        // LABELS
        JLabel l_nomListPza = new JLabel("Lista de piezas", SwingConstants.LEFT);
        confLabel(l_nomListPza);
        panelDatos.add(l_nomListPza);
        l_nomListPza.setBounds(25, 20, dimLabel.width, dimLabel.height);
        l_nomListPza.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JLabel l_codPza = new JLabel("Código", SwingConstants.RIGHT);
        confLabel(l_codPza);
        panelDatos.add(l_codPza);
        l_codPza.setBounds(180, 80, dimLabel.width, dimLabel.height);

        JLabel l_nombre = new JLabel("Nombre", SwingConstants.RIGHT);
        confLabel(l_nombre);
        panelDatos.add(l_nombre);
        l_nombre.setBounds(180, 140, dimLabel.width, dimLabel.height);

        JLabel l_apellidos = new JLabel("Precio", SwingConstants.RIGHT);
        confLabel(l_apellidos);
        panelDatos.add(l_apellidos);
        l_apellidos.setBounds(180, 200, dimLabel.width, dimLabel.height);

        JLabel l_direccion = new JLabel("Descripción", SwingConstants.RIGHT);
        confLabel(l_direccion);
        panelDatos.add(l_direccion);
        l_direccion.setBounds(180, 260, dimLabel.width, dimLabel.height);

        // TEXTFIELDS
        t_pzaCod = new JTextField();
        confTextFieldCodigo(t_pzaCod);
        panelDatos.add(t_pzaCod);
        t_pzaCod.setBounds(395, 78, dimTextFieldCodigo.width, dimTextFieldCodigo.height);
        t_pzaCod.setEditable(false);

        t_pzaNombre = new JTextField();
        confTextField(t_pzaNombre);
        panelDatos.add(t_pzaNombre);
        t_pzaNombre.setBounds(395, 138, dimTextField.width, dimTextField.height);

        t_pzaPrecio = new JTextField();
        confTextField(t_pzaPrecio);
        panelDatos.add(t_pzaPrecio);
        t_pzaPrecio.setBounds(395, 198, dimTextField.width, dimTextField.height);

        t_pzaDesc = new JTextArea();
        confTextArea(t_pzaDesc);
        panelDatos.add(t_pzaDesc);
        t_pzaDesc.setBounds(395, 262, dimTextArea.width, dimTextArea.height);
        t_pzaDesc.setLineWrap(true);
        t_pzaDesc.setWrapStyleWord(true);
        t_pzaDesc.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // SCROLLPANE + LIST
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLayout(null);

        panelDatos.add(scrollPane);
        scrollPane.setBounds(20, 40, dimScrollPane.width, dimScrollPane.height);
        confScrollPane(scrollPane);

        listaPiezas = new JList();

        scrollPane.add(listaPiezas);
        listaPiezas.setBounds(2, 2, dimLista.width, dimLista.height);
        //listaPiezas.setFont(new Font("Unispace", Font.BOLD, 12));
        confLista(listaPiezas);


        // BOTONES
        nuevaPieza = new JButton("Nuevo");
        confBoton(nuevaPieza, dimBoton);
        panelDatos.add(nuevaPieza);
        nuevaPieza.setBounds(20, 430, dimBoton.width, dimBoton.height);

        borrarPieza = new JButton("Eliminar");
        confBoton(borrarPieza, dimBoton);
        panelDatos.add(borrarPieza);
        borrarPieza.setBounds(360, 430, dimBoton.width, dimBoton.height);

        editarPieza = new JButton("Editar");
        confBoton(editarPieza, dimBoton);
        panelDatos.add(editarPieza);
        editarPieza.setBounds(520, 430, dimBoton.width, dimBoton.height);

        JButton nextPieza = new JButton(">");
        confBoton(nextPieza, dimBotonPeque);
        panelDatos.add(nextPieza);
        nextPieza.setBounds(225, 355, dimBotonPeque.width, dimBotonPeque.height);

        JButton prevPieza = new JButton("<");
        confBoton(prevPieza, dimBotonPeque);
        panelDatos.add(prevPieza);
        prevPieza.setBounds(20, 355, dimBotonPeque.width, dimBotonPeque.height);

        l_paginasPza = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_paginasPza);
        l_paginasPza.setBounds(75, 370, 135, dimLabel.height);
        l_paginasPza.setFont(new Font("SegoeUI", Font.BOLD, 12));

        cargarListaPiezas();

        // LISTENERS
        nuevaPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararNuevaPieza();
            }
        });
        borrarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (borrarPieza.getText().equalsIgnoreCase("Eliminar")) {
                    PiezasEntity pza = null;
                    try {
                        pza = (PiezasEntity) listaPiezas.getSelectedValue();
                    } catch (NullPointerException ignored) { }

                    if (pza != null) {
                        mostrarJOptionPaneEliminar(2, pza.getCodigo(), null, null);
                    }

                } else {
                    reanudarVentanaPiezas();
                    listaPiezas.setSelectedIndex(0);
                }
            }
        });
        editarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean datosCorrectos = comprobarDatosPieza();
                if (datosCorrectos) {
                    if (editarPieza.getText().equalsIgnoreCase("Editar")) {
                        int indexElegido = listaPiezas.getSelectedIndex();
                        if (InsertarEditarDatos.saveUpdatePieza(construirPiezaConLosDatos(), 2)) {
                            editarPiezaLista();
                        }
                            reanudarVentanaPiezas();
                            cargarListaPiezas();
                            listaPiezas.setSelectedIndex(indexElegido);
                    } else {
                        if (InsertarEditarDatos.saveUpdatePieza(construirPiezaConLosDatos(), 1)) {
                            addPiezaALista();
                            if (indexPagPza == numPagsPza && listaPiezas.getLastVisibleIndex() == numPzasPorPagLista - 1)
                                indexPagPza++;
                            else
                                indexPagPza = numPagsPza;
                            if (indexPagPza == 0)
                                indexPagPza = 1;
                            borrarPieza.setEnabled(true);
                        }
                            reanudarVentanaPiezas();
                            cargarListaPiezas();
                            listaPiezas.setSelectedIndex(listaPiezas.getLastVisibleIndex());
                    }
                }
            }
        });
        listaPiezas.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargarDatosPieza();
            }
        });
        nextPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexAntes = indexPagPza;
                indexPagPza++;
                if (indexPagPza > numPagsPza)
                    indexPagPza = 1;

                if (indexAntes != indexPagPza) {
                    cargarListaPiezas();
                    listaPiezas.setSelectedIndex(0);
                }
            }
        });
        prevPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexAntes = indexPagPza;
                indexPagPza--;
                if (indexPagPza < 1) {
                    indexPagPza = piezas.size() / numPzasPorPagLista;
                    if (indexPagPza * numPzasPorPagLista < piezas.size())
                        indexPagPza++;
                }

                if (indexPagPza == 0)
                    indexPagPza = 1;

                if (indexAntes != indexPagPza) {
                    cargarListaPiezas();
                    listaPiezas.setSelectedIndex(0);
                }
            }
        });

        listaPiezas.setSelectedIndex(0);
        cargarDatosPieza();
        System.out.println("  Ventana cargada\n");
    }
    public void cargarVentanaPiezaBuscar(int tipo) { // 1 -> codigo, 2 -> nombre, 3 -> direccion
        System.out.println("  Cargando datos");
        piezas = CargarDatos.piezas();
        System.out.println("  Datos cargados: " + piezas.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        String texto = "Búsqueda";
        if (tipo == 1)
            texto = "Introduce el código o parte del código para buscar";
        else if (tipo == 2)
            texto = "Introduce el nombre o parte del nombre para buscar";
        else if (tipo == 3)
            texto = "Introduce la dirección o parte de la dirección para buscar";

        JLabel l_textoBuscar = new JLabel(texto, SwingConstants.CENTER);
        panelDatos.add(l_textoBuscar);
        l_textoBuscar.setBounds(0, 50, dimPanelDatos.width, dimLabel.height);
        l_textoBuscar.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JTextField t_pzaBusqueda = new JTextField();
        confTextField(t_pzaBusqueda);
        panelDatos.add(t_pzaBusqueda);
        t_pzaBusqueda.setBounds(((dimPanelDatos.width / 2) - (dimTextField.width / 2)), 74, dimTextField.width, dimTextField.height);

        JLabel l_opcionesComboBox = new JLabel("Valores encontrados", SwingConstants.CENTER);
        panelDatos.add(l_opcionesComboBox);
        l_opcionesComboBox.setBounds(0, 130, dimPanelDatos.width, dimLabel.height);
        l_opcionesComboBox.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JComboBox<String> comboBox = new JComboBox<String>();
        panelDatos.add(comboBox);
        int anchuraComboBox;
        if (tipo == 1)
            anchuraComboBox = 90;
        else if (tipo == 2)
            anchuraComboBox = 150;
        else
            anchuraComboBox = 200;
        comboBox.setBounds(((dimPanelDatos.width / 2) - (anchuraComboBox / 2)), 150, anchuraComboBox, dimTextField.height);

        JLabel l_codPzaHeader = new JLabel("Código", SwingConstants.CENTER);
        panelDatos.add(l_codPzaHeader);
        l_codPzaHeader.setBounds(0, 222, dimPanelDatos.width, dimLabel.height);
        l_codPzaHeader.setForeground(Color.GRAY);

        JLabel l_codPza = new JLabel("Código", SwingConstants.CENTER);
        panelDatos.add(l_codPza);
        l_codPza.setBounds(0, 240, dimPanelDatos.width, dimLabel.height);

        JLabel l_nombrePzaHeader = new JLabel("Nombre", SwingConstants.CENTER);
        panelDatos.add(l_nombrePzaHeader);
        l_nombrePzaHeader.setBounds(0, 272, dimPanelDatos.width, dimLabel.height);
        l_nombrePzaHeader.setForeground(Color.GRAY);

        JLabel l_nombre = new JLabel("Nombre", SwingConstants.CENTER);
        panelDatos.add(l_nombre);
        l_nombre.setBounds(0, 290, dimPanelDatos.width, dimLabel.height);

        JLabel l_precioPzaHeader = new JLabel("Precio", SwingConstants.CENTER);
        panelDatos.add(l_precioPzaHeader);
        l_precioPzaHeader.setBounds(0, 322, dimPanelDatos.width, dimLabel.height);
        l_precioPzaHeader.setForeground(Color.GRAY);

        JLabel l_precio = new JLabel("Precio", SwingConstants.CENTER);
        panelDatos.add(l_precio);
        l_precio.setBounds(0, 340, dimPanelDatos.width, dimLabel.height);

        JLabel l_descPzaHeader = new JLabel("Descripción", SwingConstants.CENTER);
        panelDatos.add(l_descPzaHeader);
        l_descPzaHeader.setBounds(0, 372, dimPanelDatos.width, dimLabel.height);
        l_descPzaHeader.setForeground(Color.GRAY);

        JTextPane l_desc = new JTextPane();
        panelDatos.add(l_desc);
        l_desc.setBounds((dimPanelDatos.width / 2) - ((dimTextArea.width + 100) / 2), 390, (dimTextArea.width + 100), dimTextArea.height);
        l_desc.setOpaque(false);
        l_desc.setEditable(false);
        l_desc.setFont(l_precio.getFont());
        StyledDocument doc = l_desc.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        //l_desc.setLineWrap(true);
        //l_desc.setWrapStyleWord(true);


        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatosOpcionesPza(comboBox, l_codPza, l_nombre, l_precio, l_desc);
            }
        });
        t_pzaBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                actualizarOpcionesPza(comboBox, t_pzaBusqueda, tipo, l_codPza, l_nombre, l_precio, l_desc);
            }
            public void removeUpdate(DocumentEvent e) {
                actualizarOpcionesPza(comboBox, t_pzaBusqueda, tipo, l_codPza, l_nombre, l_precio, l_desc);
            }
            public void insertUpdate(DocumentEvent e) {
                actualizarOpcionesPza(comboBox, t_pzaBusqueda, tipo, l_codPza, l_nombre, l_precio, l_desc);
            }
        });
        actualizarOpcionesPza(comboBox, t_pzaBusqueda, tipo, l_codPza, l_nombre, l_precio, l_desc);
        System.out.println("  Ventana cargada\n");
    }

    // PROYECTO
    public void cargarVentanaProyectoGestion() {
        System.out.println("Proyectos: Gestión");
        System.out.println("  Cargando datos");
        proyectos = CargarDatos.proyectos();
        System.out.println("  Datos cargados: " + proyectos.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        // LABELS
        JLabel l_nomListProy = new JLabel("Lista de proyectos", SwingConstants.LEFT);
        confLabel(l_nomListProy);
        panelDatos.add(l_nomListProy);
        l_nomListProy.setBounds(25, 20, dimLabel.width, dimLabel.height);
        l_nomListProy.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JLabel l_codProy = new JLabel("Código", SwingConstants.RIGHT);
        confLabel(l_codProy);
        panelDatos.add(l_codProy);
        l_codProy.setBounds(180, 80, dimLabel.width, dimLabel.height);

        JLabel l_nombre = new JLabel("Nombre", SwingConstants.RIGHT);
        confLabel(l_nombre);
        panelDatos.add(l_nombre);
        l_nombre.setBounds(180, 140, dimLabel.width, dimLabel.height);

        JLabel l_apellidos = new JLabel("Ciudad", SwingConstants.RIGHT);
        confLabel(l_apellidos);
        panelDatos.add(l_apellidos);
        l_apellidos.setBounds(180, 200, dimLabel.width, dimLabel.height);

        // TEXTFIELDS
        t_proyCod = new JTextField();
        confTextFieldCodigo(t_proyCod);
        panelDatos.add(t_proyCod);
        t_proyCod.setBounds(395, 78, dimTextFieldCodigo.width, dimTextFieldCodigo.height);
        t_proyCod.setEditable(false);

        t_proyNombre = new JTextField();
        confTextField(t_proyNombre);
        panelDatos.add(t_proyNombre);
        t_proyNombre.setBounds(395, 138, dimTextField.width, dimTextField.height);

        t_proyCiudad = new JTextField();
        confTextField(t_proyCiudad);
        panelDatos.add(t_proyCiudad);
        t_proyCiudad.setBounds(395, 198, dimTextField.width, dimTextField.height);

        // SCROLLPANE + LIST
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLayout(null);

        panelDatos.add(scrollPane);
        scrollPane.setBounds(20, 40, dimScrollPane.width, dimScrollPane.height);
        confScrollPane(scrollPane);

        listaProyectos = new JList();

        scrollPane.add(listaProyectos);
        listaProyectos.setBounds(2, 2, dimLista.width, dimLista.height);
        //listaProyectos.setFont(new Font("Unispace", Font.BOLD, 12));
        confLista(listaProyectos);


        // BOTONES
        nuevoProyecto = new JButton("Nuevo");
        confBoton(nuevoProyecto, dimBoton);
        panelDatos.add(nuevoProyecto);
        nuevoProyecto.setBounds(20, 430, dimBoton.width, dimBoton.height);

        borrarProyecto = new JButton("Eliminar");
        confBoton(borrarProyecto, dimBoton);
        panelDatos.add(borrarProyecto);
        borrarProyecto.setBounds(360, 430, dimBoton.width, dimBoton.height);

        editarProyecto = new JButton("Editar");
        confBoton(editarProyecto, dimBoton);
        panelDatos.add(editarProyecto);
        editarProyecto.setBounds(520, 430, dimBoton.width, dimBoton.height);

        JButton nextProyecto = new JButton(">");
        confBoton(nextProyecto, dimBotonPeque);
        panelDatos.add(nextProyecto);
        nextProyecto.setBounds(225, 355, dimBotonPeque.width, dimBotonPeque.height);

        JButton prevProyecto = new JButton("<");
        confBoton(prevProyecto, dimBotonPeque);
        panelDatos.add(prevProyecto);
        prevProyecto.setBounds(20, 355, dimBotonPeque.width, dimBotonPeque.height);

        l_paginasProy = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_paginasProy);
        l_paginasProy.setBounds(75, 370, 135, dimLabel.height);
        l_paginasProy.setFont(new Font("SegoeUI", Font.BOLD, 12));

        cargarListaProyectos();

        // LISTENERS
        nuevoProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararNuevoProyecto();
            }
        });
        borrarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (borrarProyecto.getText().equalsIgnoreCase("Eliminar")) {
                    ProyectosEntity proy = null;
                    try {
                        proy = (ProyectosEntity) listaProyectos.getSelectedValue();
                    } catch (NullPointerException ignored) { }

                    if (proy != null) {
                        mostrarJOptionPaneEliminar(3, proy.getCodigo(), null, null);
                    }

                } else {
                    reanudarVentanaProyectos();
                    listaProyectos.setSelectedIndex(0);
                }
            }
        });
        editarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean datosCorrectos = comprobarDatosProyecto();
                if (datosCorrectos) {
                    if (editarProyecto.getText().equalsIgnoreCase("Editar")) {
                        int indexElegido = listaProyectos.getSelectedIndex();
                        if (InsertarEditarDatos.saveUpdateProyecto(construirProyectoConLosDatos(), 2)) {
                            editarProyectoLista();
                        }
                        reanudarVentanaProyectos();
                        cargarListaProyectos();
                        listaProyectos.setSelectedIndex(indexElegido);
                    } else {
                        if (InsertarEditarDatos.saveUpdateProyecto(construirProyectoConLosDatos(), 1)) {
                            addProyectoALista();
                            if (indexPagProy == numPagsProy && listaProyectos.getLastVisibleIndex() == numProysPorPagLista - 1)
                                indexPagProy++;
                            else
                                indexPagProy = numPagsProy;
                            if (indexPagProy == 0)
                                indexPagProy = 1;
                            borrarProyecto.setEnabled(true);
                        }
                        reanudarVentanaProyectos();
                        cargarListaProyectos();
                        listaProyectos.setSelectedIndex(listaProyectos.getLastVisibleIndex());

                    }
                }
            }
        });
        listaProyectos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargarDatosProyecto();
            }
        });
        nextProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexAntes = indexPagProy;
                indexPagProy++;
                if (indexPagProy > numPagsProy)
                    indexPagProy = 1;

                if (indexAntes != indexPagProy) {
                    cargarListaProyectos();
                    listaProyectos.setSelectedIndex(0);
                }
            }
        });
        prevProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexAntes = indexPagProy;
                indexPagProy--;
                if (indexPagProy < 1) {
                    indexPagProy = proyectos.size() / numProysPorPagLista;
                    if (indexPagProy * numProysPorPagLista < proyectos.size())
                        indexPagProy++;
                }

                if (indexPagProy == 0)
                    indexPagProy = 1;

                if (indexAntes != indexPagProy) {
                    cargarListaProyectos();
                    listaProyectos.setSelectedIndex(0);
                }
            }
        });

        listaProyectos.setSelectedIndex(0);
        cargarDatosProyecto();
        System.out.println("  Ventana cargada\n");
    }
    public void cargarVentanaProyectoBuscar(int tipo) { // 1 -> codigo, 2 -> nombre, 3 -> direccion
        System.out.println("  Cargando datos");
        proyectos = CargarDatos.proyectos();
        System.out.println("  Datos cargados: " + proyectos.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        String texto = "Búsqueda";
        if (tipo == 1)
            texto = "Introduce el código o parte del código para buscar";
        else if (tipo == 2)
            texto = "Introduce el nombre o parte del nombre para buscar";
        else if (tipo == 3)
            texto = "Introduce la dirección o parte de la dirección para buscar";

        JLabel l_textoBuscar = new JLabel(texto, SwingConstants.CENTER);
        panelDatos.add(l_textoBuscar);
        l_textoBuscar.setBounds(0, 50, dimPanelDatos.width, dimLabel.height);
        l_textoBuscar.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JTextField t_proyBusqueda = new JTextField();
        confTextField(t_proyBusqueda);
        panelDatos.add(t_proyBusqueda);
        t_proyBusqueda.setBounds(((dimPanelDatos.width / 2) - (dimTextField.width / 2)), 74, dimTextField.width, dimTextField.height);

        JLabel l_opcionesComboBox = new JLabel("Valores encontrados", SwingConstants.CENTER);
        panelDatos.add(l_opcionesComboBox);
        l_opcionesComboBox.setBounds(0, 130, dimPanelDatos.width, dimLabel.height);
        l_opcionesComboBox.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JComboBox<String> comboBox = new JComboBox<String>();
        panelDatos.add(comboBox);
        int anchuraComboBox;
        if (tipo == 1)
            anchuraComboBox = 90;
        else if (tipo == 2)
            anchuraComboBox = 150;
        else
            anchuraComboBox = 200;
        comboBox.setBounds(((dimPanelDatos.width / 2) - (anchuraComboBox / 2)), 150, anchuraComboBox, dimTextField.height);

        JLabel l_codProyHeader = new JLabel("Código", SwingConstants.CENTER);
        panelDatos.add(l_codProyHeader);
        l_codProyHeader.setBounds(0, 222, dimPanelDatos.width, dimLabel.height);
        l_codProyHeader.setForeground(Color.GRAY);
        
        JLabel l_codProy = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_codProy);
        l_codProy.setBounds(0, 240, dimPanelDatos.width, dimLabel.height);

        JLabel l_nombreProyHeader = new JLabel("Nombre", SwingConstants.CENTER);
        panelDatos.add(l_nombreProyHeader);
        l_nombreProyHeader.setBounds(0, 272, dimPanelDatos.width, dimLabel.height);
        l_nombreProyHeader.setForeground(Color.GRAY);
        
        JLabel l_nombre = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_nombre);
        l_nombre.setBounds(0, 290, dimPanelDatos.width, dimLabel.height);

        JLabel l_ciudadProyHeader = new JLabel("Ciudad", SwingConstants.CENTER);
        panelDatos.add(l_ciudadProyHeader);
        l_ciudadProyHeader.setBounds(0, 322, dimPanelDatos.width, dimLabel.height);
        l_ciudadProyHeader.setForeground(Color.GRAY);
        
        JLabel l_ciudad = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_ciudad);
        l_ciudad.setBounds(0, 340, dimPanelDatos.width, dimLabel.height);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatosOpcionesProy(comboBox, l_codProy, l_nombre, l_ciudad);
            }
        });
        t_proyBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                actualizarOpcionesProy(comboBox, t_proyBusqueda, tipo, l_codProy, l_nombre, l_ciudad);
            }
            public void removeUpdate(DocumentEvent e) {
                actualizarOpcionesProy(comboBox, t_proyBusqueda, tipo, l_codProy, l_nombre, l_ciudad);
            }
            public void insertUpdate(DocumentEvent e) {
                actualizarOpcionesProy(comboBox, t_proyBusqueda, tipo, l_codProy, l_nombre, l_ciudad);
            }
        });
        actualizarOpcionesProy(comboBox, t_proyBusqueda, tipo, l_codProy, l_nombre, l_ciudad);
        System.out.println("  Ventana cargada\n");
    }

    // PROVEEDOR
    public void cargarVentanaProveedorGestion() {
        System.out.println("Proveedores: Gestión");
        System.out.println("  Cargando datos");
        proveedores = CargarDatos.proveedores();
        System.out.println("  Datos cargados: " + proveedores.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        // LABELS
        JLabel l_nomListProv = new JLabel("Lista de proveedores", SwingConstants.LEFT);
        confLabel(l_nomListProv);
        panelDatos.add(l_nomListProv);
        l_nomListProv.setBounds(25, 20, dimLabel.width, dimLabel.height);
        l_nomListProv.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JLabel l_codProv = new JLabel("Código", SwingConstants.RIGHT);
        confLabel(l_codProv);
        panelDatos.add(l_codProv);
        l_codProv.setBounds(180, 80, dimLabel.width, dimLabel.height);

        JLabel l_nombre = new JLabel("Nombre", SwingConstants.RIGHT);
        confLabel(l_nombre);
        panelDatos.add(l_nombre);
        l_nombre.setBounds(180, 140, dimLabel.width, dimLabel.height);

        JLabel l_apellidos = new JLabel("Apellidos", SwingConstants.RIGHT);
        confLabel(l_apellidos);
        panelDatos.add(l_apellidos);
        l_apellidos.setBounds(180, 200, dimLabel.width, dimLabel.height);

        JLabel l_direccion = new JLabel("Dirección", SwingConstants.RIGHT);
        confLabel(l_direccion);
        panelDatos.add(l_direccion);
        l_direccion.setBounds(180, 260, dimLabel.width, dimLabel.height);

        // TEXTFIELDS
        t_provCod = new JTextField();
        confTextFieldCodigo(t_provCod);
        panelDatos.add(t_provCod);
        t_provCod.setBounds(395, 78, dimTextFieldCodigo.width, dimTextFieldCodigo.height);
        t_provCod.setEditable(false);

        t_provNombre = new JTextField();
        confTextField(t_provNombre);
        panelDatos.add(t_provNombre);
        t_provNombre.setBounds(395, 138, dimTextField.width, dimTextField.height);

        t_provApe = new JTextField();
        confTextField(t_provApe);
        panelDatos.add(t_provApe);
        t_provApe.setBounds(395, 198, dimTextField.width, dimTextField.height);

        t_provDir = new JTextField();
        confTextField(t_provDir);
        panelDatos.add(t_provDir);
        t_provDir.setBounds(395, 258, dimTextField.width, dimTextField.height);

        // SCROLLPANE + LIST
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLayout(null);

        panelDatos.add(scrollPane);
        scrollPane.setBounds(20, 40, dimScrollPane.width, dimScrollPane.height);
        confScrollPane(scrollPane);

        listaProveedores = new JList();

        scrollPane.add(listaProveedores);
        listaProveedores.setBounds(2, 2, dimLista.width, dimLista.height);
        //listaProveedores.setFont(new Font("Unispace", Font.BOLD, 12));
        confLista(listaProveedores);


        // BOTONES
        nuevoProveedor = new JButton("Nuevo");
        confBoton(nuevoProveedor, dimBoton);
        panelDatos.add(nuevoProveedor);
        nuevoProveedor.setBounds(20, 430, dimBoton.width, dimBoton.height);

        borrarProveedor = new JButton("Eliminar");
        confBoton(borrarProveedor, dimBoton);
        panelDatos.add(borrarProveedor);
        borrarProveedor.setBounds(360, 430, dimBoton.width, dimBoton.height);

        editarProveedor = new JButton("Editar");
        confBoton(editarProveedor, dimBoton);
        panelDatos.add(editarProveedor);
        editarProveedor.setBounds(520, 430, dimBoton.width, dimBoton.height);

        JButton nextProveedor = new JButton(">");
        confBoton(nextProveedor, dimBotonPeque);
        panelDatos.add(nextProveedor);
        nextProveedor.setBounds(225, 355, dimBotonPeque.width, dimBotonPeque.height);

        JButton prevProveedor = new JButton("<");
        confBoton(prevProveedor, dimBotonPeque);
        panelDatos.add(prevProveedor);
        prevProveedor.setBounds(20, 355, dimBotonPeque.width, dimBotonPeque.height);

        l_paginasProv = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_paginasProv);
        l_paginasProv.setBounds(75, 370, 135, dimLabel.height);
        l_paginasProv.setFont(new Font("SegoeUI", Font.BOLD, 12));

        cargarListaProveedores();

        // LISTENERS
        nuevoProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararNuevoProveedor();
            }
        });
        borrarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (borrarProveedor.getText().equalsIgnoreCase("Eliminar")) {
                    ProveedoresEntity prov = null;
                    try {
                        prov = (ProveedoresEntity) listaProveedores.getSelectedValue();
                    } catch (NullPointerException ignored) { }

                    if (prov != null) {
                        mostrarJOptionPaneEliminar(1, prov.getCodigo(), null, null);
                    }

                } else {
                    reanudarVentanaProveedores();
                       listaProveedores.setSelectedIndex(0);
                }
            }
        });
        editarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean datosCorrectos = comprobarDatosProveedor();
                if (datosCorrectos) {
                    if (editarProveedor.getText().equalsIgnoreCase("Editar")) {
                        int indexElegido = listaProveedores.getSelectedIndex();
                        if (InsertarEditarDatos.saveUpdateProveedor(construirProveedorConLosDatos(), 2)) {
                            editarProveedorLista();
                        }
                        reanudarVentanaProveedores();
                        cargarListaProveedores();
                        listaProveedores.setSelectedIndex(indexElegido);
                    } else {
                        if (InsertarEditarDatos.saveUpdateProveedor(construirProveedorConLosDatos(), 1)) {
                            addProveedorALista();
                            if (indexPagProv == numPagsProv && listaProveedores.getLastVisibleIndex() == numProvsPorPagLista - 1)
                                indexPagProv++;
                            else
                                indexPagProv = numPagsProv;
                            if (indexPagProv == 0)
                                indexPagProv = 1;
                            borrarProveedor.setEnabled(true);
                        }
                        reanudarVentanaProveedores();
                        cargarListaProveedores();
                        listaProveedores.setSelectedIndex(listaProveedores.getLastVisibleIndex());
                    }
                }
            }
        });
        listaProveedores.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargarDatosProveedor();
            }
        });
        nextProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexAntes = indexPagProv;
                indexPagProv++;
                if (indexPagProv > numPagsProv)
                    indexPagProv = 1;

                if (indexAntes != indexPagProv) {
                    cargarListaProveedores();
                    listaProveedores.setSelectedIndex(0);
                }
            }
        });
        prevProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexAntes = indexPagProv;
                indexPagProv--;
                if (indexPagProv < 1) {
                    indexPagProv = proveedores.size() / numProvsPorPagLista;
                    if (indexPagProv * numProvsPorPagLista < proveedores.size())
                        indexPagProv++;
                }

                if (indexPagProv == 0)
                    indexPagProv = 1;

                if (indexAntes != indexPagProv) {
                    cargarListaProveedores();
                    listaProveedores.setSelectedIndex(0);
                }
            }
        });

        listaProveedores.setSelectedIndex(0);
        cargarDatosProveedor();
        System.out.println("  Ventana cargada\n");
    }
    public void cargarVentanaProveedorBuscar(int tipo) { // 1 -> codigo, 2 -> nombre, 3 -> direccion
        System.out.println("  Cargando datos");
        proveedores = CargarDatos.proveedores();
        System.out.println("  Datos cargados: " + proveedores.size());
        System.out.println("  Cargando ventana");
        try {
            panelDatos.removeAll();
            panelDatos.repaint();
        } catch (Exception ignored) { }

        panelDatos.setLayout(null);
        confPanel(panelDatos);

        String texto = "Búsqueda";
        if (tipo == 1)
            texto = "Introduce el código o parte del código para buscar";
        else if (tipo == 2)
            texto = "Introduce el nombre o parte del nombre para buscar";
        else if (tipo == 3)
            texto = "Introduce la dirección o parte de la dirección para buscar";

        JLabel l_textoBuscar = new JLabel(texto, SwingConstants.CENTER);
        panelDatos.add(l_textoBuscar);
        l_textoBuscar.setBounds(0, 50, dimPanelDatos.width, dimLabel.height);
        l_textoBuscar.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JTextField t_provBusqueda = new JTextField();
        confTextField(t_provBusqueda);
        panelDatos.add(t_provBusqueda);
        t_provBusqueda.setBounds(((dimPanelDatos.width / 2) - (dimTextField.width / 2)), 74, dimTextField.width, dimTextField.height);

        JLabel l_opcionesComboBox = new JLabel("Valores encontrados", SwingConstants.CENTER);
        panelDatos.add(l_opcionesComboBox);
        l_opcionesComboBox.setBounds(0, 130, dimPanelDatos.width, dimLabel.height);
        l_opcionesComboBox.setFont(new Font("SegoeUI", Font.BOLD, 12));

        JComboBox<String> comboBox = new JComboBox<String>();
        panelDatos.add(comboBox);
        int anchuraComboBox;
        if (tipo == 1)
            anchuraComboBox = 90;
        else if (tipo == 2)
            anchuraComboBox = 150;
        else
            anchuraComboBox = 200;
        comboBox.setBounds(((dimPanelDatos.width / 2) - (anchuraComboBox / 2)), 150, anchuraComboBox, dimTextField.height);

        JLabel l_codProvHeader = new JLabel("Código", SwingConstants.CENTER);
        panelDatos.add(l_codProvHeader);
        l_codProvHeader.setBounds(0, 222, dimPanelDatos.width, dimLabel.height);
        l_codProvHeader.setForeground(Color.GRAY);
        
        JLabel l_codProv = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_codProv);
        l_codProv.setBounds(0, 240, dimPanelDatos.width, dimLabel.height);

        JLabel l_nombreProvHeader = new JLabel("Nombre", SwingConstants.CENTER);
        panelDatos.add(l_nombreProvHeader);
        l_nombreProvHeader.setBounds(0, 272, dimPanelDatos.width, dimLabel.height);
        l_nombreProvHeader.setForeground(Color.GRAY);
        
        JLabel l_nombre = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_nombre);
        l_nombre.setBounds(0, 290, dimPanelDatos.width, dimLabel.height);

        JLabel l_apeProvHeader = new JLabel("Apellidos", SwingConstants.CENTER);
        panelDatos.add(l_apeProvHeader);
        l_apeProvHeader.setBounds(0, 322, dimPanelDatos.width, dimLabel.height);
        l_apeProvHeader.setForeground(Color.GRAY);

        JLabel l_apellidos = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_apellidos);
        l_apellidos.setBounds(0, 340, dimPanelDatos.width, dimLabel.height);

        JLabel l_dirProvHeader = new JLabel("Dirección", SwingConstants.CENTER);
        panelDatos.add(l_dirProvHeader);
        l_dirProvHeader.setBounds(0, 372, dimPanelDatos.width, dimLabel.height);
        l_dirProvHeader.setForeground(Color.GRAY);
        
        JLabel l_direccion = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_direccion);
        l_direccion.setBounds(0, 390, dimPanelDatos.width, dimLabel.height);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatosOpcionesProv(comboBox, l_codProv, l_nombre, l_apellidos, l_direccion);
            }
        });
        t_provBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                actualizarOpcionesProv(comboBox, t_provBusqueda, tipo, l_codProv, l_nombre, l_apellidos, l_direccion);
            }
            public void removeUpdate(DocumentEvent e) {
                actualizarOpcionesProv(comboBox, t_provBusqueda, tipo, l_codProv, l_nombre, l_apellidos, l_direccion);
            }
            public void insertUpdate(DocumentEvent e) {
                actualizarOpcionesProv(comboBox, t_provBusqueda, tipo, l_codProv, l_nombre, l_apellidos, l_direccion);
            }
        });
        actualizarOpcionesProv(comboBox, t_provBusqueda, tipo, l_codProv, l_nombre, l_apellidos, l_direccion);
        System.out.println("  Ventana cargada\n");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Configurar Menú
    public void menuGestionGlobal(JMenuBar menuBar) {
        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("Gestión Global");
        menu.getAccessibleContext().setAccessibleDescription(
                "Administra las gestiones y muestra estadísticas");
        menuBar.add(menu);

        menuItem = new JMenuItem("Administrar gestiones");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 14) {
                    numVentana = 14;
                    cargarVentanaAdministracionGestion();
                }
            }
        });

        menuItem = new JMenuItem("Suministros");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 15) {
                    numVentana = 15;
                    cargarVentanaSuministros();
                }
            }
        });

        menuItem = new JMenuItem("Estadísticas");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 16) {
                    numVentana = 16;
                    cargarVentanaEstadisticas();
                }
            }
        });
    }
    public void menuProyectos(JMenuBar menuBar) {
        JMenu menu, subMenu;
        JMenuItem menuItem;

        menu = new JMenu("Proyectos");
        menu.getAccessibleContext().setAccessibleDescription(
                "Gestiona o consulta los proyectos de la BBDD");
        menuBar.add(menu);

        menuItem = new JMenuItem("Gestión de proyectos");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 10) {
                    numVentana = 10;
                    cargarVentanaProyectoGestion();
                }
            }
        });

        // SUBMENÚ CONSULTAS PROYECTOS
        subMenu = new JMenu("Consultar proyectos");

        menuItem = new JMenuItem("Por Código");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 11) {
                    System.out.println("Proyectos: Búsqueda por Código");
                    numVentana = 11;
                    cargarVentanaProyectoBuscar(1);
                }
            }
        });
        menuItem = new JMenuItem("Por Nombre");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 12) {
                    System.out.println("Proyectos: Búsqueda por Nombre");
                    numVentana = 12;
                    cargarVentanaProyectoBuscar(2);
                }
            }
        });
        menuItem = new JMenuItem("Por Ciudad");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 13) {
                    System.out.println("Proyectos: Búsqueda por Ciudad");
                    numVentana = 13;
                    cargarVentanaProyectoBuscar(3);
                }
            }
        });

        menu.add(subMenu); // AÑADIMOS EL SUBMENÚ YA CARGADO
    }
    public void menuPiezas(JMenuBar menuBar) {
        JMenu menu, subMenu;
        JMenuItem menuItem;

        menu = new JMenu("Piezas");
        menu.getAccessibleContext().setAccessibleDescription(
                "Gestiona o consulta los piezas de la BBDD");
        menuBar.add(menu);

        menuItem = new JMenuItem("Gestión de piezas");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 7) {
                    numVentana = 7;
                    cargarVentanaPiezaGestion();
                }
            }
        });

        // SUBMENÚ CONSULTAS PIEZAS
        subMenu = new JMenu("Consultar piezas");

        menuItem = new JMenuItem("Por Código");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 8) {
                    System.out.println("Piezas: Búsqueda por Código");
                    numVentana = 8;
                    cargarVentanaPiezaBuscar(1);
                }
            }
        });

        menuItem = new JMenuItem("Por Nombre");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 9) {
                    System.out.println("Piezas: Búsqueda por Nombre");
                    numVentana = 9;
                    cargarVentanaPiezaBuscar(2);
                }
            }
        });

        menu.add(subMenu); // AÑADIMOS EL SUBMENÚ YA CARGADO
    }
    public void menuProveedores(JMenuBar menuBar) {
        JMenu menu, subMenu;
        JMenuItem menuItem;

        menu = new JMenu("Proveedores");
        menu.getAccessibleContext().setAccessibleDescription(
                "Gestiona o consulta los proveedores de la BBDD");
        menuBar.add(menu);

        menuItem = new JMenuItem("Gestión de proveedores");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 3) {
                    numVentana = 3;
                    cargarVentanaProveedorGestion();
                }
            }
        });

        // SUBMENÚ CONSULTAS PROVEEDORES
        subMenu = new JMenu("Consultar proveedores");

        menuItem = new JMenuItem("Por Código");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 4) {
                    System.out.println("Proveedores: Búsqueda por Código");
                    numVentana = 4;
                    cargarVentanaProveedorBuscar(1);
                }
            }
        });

        menuItem = new JMenuItem("Por Nombre");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 5) {
                    System.out.println("Proveedores: Búsqueda por Nombre");
                    numVentana = 5;
                    cargarVentanaProveedorBuscar(2);
                }
            }
        });

        menuItem = new JMenuItem("Por Dirección");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 6) {
                    System.out.println("Proveedores: Búsqueda por Dirección");
                    numVentana = 6;
                    cargarVentanaProveedorBuscar(3);
                }
            }
        });

        menu.add(subMenu); // AÑADIMOS EL SUBMENÚ YA CARGADO
    }
    public void menuBBDD(JMenuBar menuBar) {
        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("Inicio");

        // Opciones del primer menú
        menuItem = new JMenuItem("Cohete", new ImageIcon(".assets/1-cohete.png"));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 1) {
                    numVentana = 1;
                    cargarVentanaInicio(1);
                }
            }
        });

        menuItem = new JMenuItem("Tostada",
                new ImageIcon(".assets/2-tostada.png"));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numVentana != 2) {
                    numVentana = 2;
                    cargarVentanaInicio(2);
                }
            }
        });

        menu.add(menuItem);
        menuBar.add(menu);
    }

    public void configurarMenu() {
        JMenuBar menuBar;

        // Crear la barra de menú
        menuBar = new JMenuBar();

        // Inicio
        menuBBDD(menuBar);

        // Proveedores
        menuProveedores(menuBar);

        // Piezas
        menuPiezas(menuBar);

        // Proyectos
        menuProyectos(menuBar);

        // Gestión Global
        menuGestionGlobal(menuBar);

        // Cargamos la barra de menú configurada en la ventana
        ventanaPrincipal.setJMenuBar(menuBar);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters y Setters
    public JPanel getPanel() {
        return panel;
    }
    public void setVentanaPrincipal(JFrame ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Lanzador ventana
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana Principal");
        VentanaPrincipal vp = new VentanaPrincipal();
        vp.setVentanaPrincipal(frame);
        vp.configurarMenu();
        frame.setContentPane(vp.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setBackground(new Color(0x91c8ff));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
