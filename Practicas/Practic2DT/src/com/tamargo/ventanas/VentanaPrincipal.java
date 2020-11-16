package com.tamargo.ventanas;

import com.tamargo.GestionEntity;
import com.tamargo.PiezasEntity;
import com.tamargo.ProveedoresEntity;
import com.tamargo.ProyectosEntity;
import com.tamargo.util.BorrarDatos;
import com.tamargo.util.CargarDatos;
import com.tamargo.util.InsertarEditarDatos;
import org.hibernate.ObjectNotFoundException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaPrincipal {
    // Base de la Ventana
    private JFrame ventanaPrincipal;
    private JPanel panel;
    private JPanel panelDatos;

    // ArrayLists con los datos
    private ArrayList<ProveedoresEntity> proveedores = new ArrayList<>();
    private ArrayList<PiezasEntity> piezas = new ArrayList<>();
    private ArrayList<ProveedoresEntity> proyectos = new ArrayList<>();
    private ArrayList<GestionEntity> gestiones = new ArrayList<>();

    // Proveedores
    private JList<ProveedoresEntity> listaProveedores;
    private int indexPagProv = 1;
    private int numProvsPorPagLista = 5;
    private int numPagsProv = 1;
    private JButton nuevoProveedor;
    private JButton borrarProveedor;
    private JButton editarProveedor;
    private JTextField t_provCod;
    private JTextField t_provNombre;
    private JTextField t_provApe;
    private JTextField t_provDir;
    private JLabel l_paginas;

    // Piezas
    private JList<PiezasEntity> listaPiezas;

    // Proyectos
    private JList<ProyectosEntity> listaProyectos;

    // Dimensiones
    private final Dimension dimPanelDatos = new Dimension(700, 500);
    private final Dimension dimLabel = new Dimension(200, 20);
    private final Dimension dimTextField = new Dimension(200, 25);
    private final Dimension dimTextFieldCodigo = new Dimension(75, 25);
    private final Dimension dimBoton = new Dimension(150, 40);
    private final Dimension dimBotonPeque = new Dimension(50, 50);
    private final Dimension dimLista = new Dimension(250, 300);
    private final Dimension dimScrollPane = new Dimension(255, 305);

    public VentanaPrincipal() {
        //configurarDimensions();
    }

    // Métodos iniciales
    public void configurarDimensions() {
        dimPanelDatos.setSize(700, 500);
        dimLabel.setSize(200, 20);
        dimTextField.setSize(200, 25);
        dimTextFieldCodigo.setSize(75, 25);
        dimBoton.setSize(150, 40);
        dimBotonPeque.setSize(50, 50);
        dimLista.setSize(250, 350);
        dimScrollPane.setSize(255, 355);
    }

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

    // Cargar Datos
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

        l_paginas.setText("Pag. " + indexPagProv + "/" + numPagsEnt);
        vaciarDatosProveedor();
        listaProveedores.setModel(modelo);
        listaProveedores.setSelectedIndex(0);

        if (proveedores.size() <= 0) {
            mostrarJOptionPane("No existen proveedores",
                    "No existe ningún proveedor en la BBDD. ¡Registra alguno!",
                    1);
        }
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
    public void mostrarJOptionPaneEliminar(int tipo, String codigo) {
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
            titulo += " Gestión " + codigo;

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



                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    // Utilidades Proveedores
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
            if (indexPagProv * numProvsPorPagLista > proveedores.size())
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
            if (String.valueOf(datosFaltantes).length() < 2)
                datosFaltantes.append(", ");
            datosFaltantes.append("Apellidos");
            datosValidos = false;
        }
        if (t_provDir.getText().equalsIgnoreCase("")) {
            if (String.valueOf(datosFaltantes).length() < 2)
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

        return  datosValidos;
    }
    public void vaciarDatosProveedor() {
        t_provCod.setText(CargarDatos.codigoNuevoProveedor());
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

    // Cargar Ventanas
    public void cargarVentanaProveedorGestion() {
        System.out.println("  Cargando datos");
        proveedores = CargarDatos.proveedores();
        System.out.println("  Datos cargados (num datos: " + proveedores.size() + ")");
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

        l_paginas = new JLabel("", SwingConstants.CENTER);
        panelDatos.add(l_paginas);
        l_paginas.setBounds(75, 370, 135, dimLabel.height);
        l_paginas.setFont(new Font("SegoeUI", Font.BOLD, 12));

        cargarListaProveedores();


        // LISTENERS
        nuevoProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoProveedor.setEnabled(false);

                borrarProveedor.setEnabled(true);
                editarProveedor.setEnabled(true);

                borrarProveedor.setText("Cancelar");
                editarProveedor.setText("Insertar");

                listaProveedores.clearSelection();
                listaProveedores.setEnabled(false);
                vaciarDatosProveedor();

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
                        mostrarJOptionPaneEliminar(1, prov.getCodigo());
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
                        InsertarEditarDatos.saveUpdateProveedor(construirProveedorConLosDatos(), 2);
                        editarProveedorLista();
                        reanudarVentanaProveedores();
                        cargarListaProveedores();
                        listaProveedores.setSelectedIndex(indexElegido);
                    } else {
                        InsertarEditarDatos.saveUpdateProveedor(construirProveedorConLosDatos(), 1);
                        addProveedorALista();
                        if (indexPagProv == numPagsProv && listaProveedores.getLastVisibleIndex() == numProvsPorPagLista - 1)
                            indexPagProv++;
                        else
                            indexPagProv = numPagsProv;
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

    // Configurar Menú
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
                //TODO LISTENER GESTIÓN PROYECTOS
                System.out.println("Proyectos: Gestión");
            }
        });

        // SUBMENÚ CONSULTAS PROYECTOS
        subMenu = new JMenu("Consultar proyectos");

        menuItem = new JMenuItem("Por Código");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PROYECTOS BÚSQUEDA POR CÓDIGO
                System.out.println("Proyectos: Búsqueda por Código");
            }
        });

        menuItem = new JMenuItem("Por Nombre");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PROYECTOS BÚSQUEDA POR NOMBRE
                System.out.println("Proyectos: Búsqueda por Nombre");
            }
        });

        menuItem = new JMenuItem("Por Ciudad");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PROYECTOS BÚSQUEDA POR CIUDAD
                System.out.println("Proyectos: Búsqueda por Ciudad");
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
                //TODO LISTENER GESTIÓN PIEZAS
                System.out.println("Piezas: Gestión");
            }
        });

        // SUBMENÚ CONSULTAS PIEZAS
        subMenu = new JMenu("Consultar piezas");

        menuItem = new JMenuItem("Por Código");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PIEZAS BÚSQUEDA POR CÓDIGO
                System.out.println("Piezas: Búsqueda por Código");
            }
        });

        menuItem = new JMenuItem("Por Nombre");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PIEZAS BÚSQUEDA POR NOMBRE
                System.out.println("Piezas: Búsqueda por Nombre");
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
                //TODO LISTENER GESTIÓN PROVEEDORES
                System.out.println("Proveedores: Gestión");
                cargarVentanaProveedorGestion();
            }
        });

        // SUBMENÚ CONSULTAS PROVEEDORES
        subMenu = new JMenu("Consultar proveedores");

        menuItem = new JMenuItem("Por Código");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PROVEEDORES BÚSQUEDA POR CÓDIGO
                System.out.println("Proveedores: Búsqueda por Código");
            }
        });

        menuItem = new JMenuItem("Por Nombre");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PROVEEDORES BÚSQUEDA POR NOMBRE
                System.out.println("Proveedores: Búsqueda por Nombre");
            }
        });

        menuItem = new JMenuItem("Por Dirección");
        subMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LISTENER PROVEEDORES BÚSQUEDA POR DIRECCIÓN
                System.out.println("Proveedores: Búsqueda por Dirección");
            }
        });

        menu.add(subMenu); // AÑADIMOS EL SUBMENÚ YA CARGADO
    }

    public void menuBBDD(JMenuBar menuBar) {
        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("Menú 1");
        //menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        // Opciones del primer menú
        menuItem = new JMenuItem("Hola");
        //menuItem.setMnemonic(KeyEvent.VK_1);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola");
            }
        });

        menuItem = new JMenuItem("Adiós",
                new ImageIcon("images/middle.gif"));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Adiós");
            }
        });

        menu.add(menuItem);
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

        // Cargamos la barra de menú configurada en la ventana
        ventanaPrincipal.setJMenuBar(menuBar);
    }



    // Getters y Setters
    public JPanel getPanel() {
        return panel;
    }
    public void setVentanaPrincipal(JFrame ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

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
