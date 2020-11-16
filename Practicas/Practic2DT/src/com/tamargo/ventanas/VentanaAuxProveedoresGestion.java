package com.tamargo.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAuxProveedoresGestion {
    private JPanel panelDatos;
    private JButton button1;
    private JPanel panel1;
    private JPanel datos;

    public VentanaAuxProveedoresGestion() {
        actualizarVentana();
    }

    public void actualizarVentana() {
        Dimension dim = new Dimension();
        dim.setSize(50, 20);

        JLabel label = new JLabel("Hola");
        label.setMinimumSize(dim);

        datos.setLayout(null);
        datos.add(label);
        label.setBounds(20, 20, dim.width, dim.height);

    }

    public JPanel getPanelDatos() {
        return panelDatos;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaAuxProveedoresGestion");
        frame.setContentPane(new VentanaAuxProveedoresGestion().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
