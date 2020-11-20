package com.tamargo.run;

import com.tamargo.util.HibernateUtil;
import com.tamargo.ventanas.VentanaPrincipal;
import org.hibernate.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static boolean iniciar = true;

    public static void main(String[] args) {
        comprobarHibernate();

        if (iniciar)
            VentanaPrincipal.main(args);

    }

    public static void comprobarHibernate() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        if (sessionFactory == null) {
            JButton cerrarAplicacion = new JButton("Cerrar aplicación");
            JButton seguirIgualmente = new JButton("Seguir igualmente");

            cerrarAplicacion.setFocusPainted(false);
            cerrarAplicacion.setFocusPainted(false);

            Object[] options = {cerrarAplicacion, seguirIgualmente};

            String titulo = "Imposible Conectar";
            String mensaje = "Hibernate no ha podido arrancar correctamente.\n\n" +
                    "Causas habituales:\n" +
                    "- El archivo hibernate.cfg está mal configurado (credenciales incorrectas)\n" +
                    "- Los mapeos que utiliza hibernate presentan incoherencias que imposiblizan\n" +
                    "el uso de la base de datos\n" +
                    "- ¿La base de datos está creada y su servicio en ejecución?\n\n" +
                    "Si quieres puedes acceder a la aplicación, pero no tendrás datos ni podrás crearlos   ";

            final JOptionPane pane = new JOptionPane(mensaje,
                    JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
            JDialog dialog = pane.createDialog(titulo);
            cerrarAplicacion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    iniciar = false;
                }
            });
            seguirIgualmente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            dialog.setVisible(true);
        }
    }

}
