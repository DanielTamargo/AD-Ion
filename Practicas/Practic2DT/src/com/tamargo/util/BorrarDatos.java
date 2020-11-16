package com.tamargo.util;

import com.tamargo.ProveedoresEntity;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.swing.*;

public class BorrarDatos {

    public static SessionFactory sessionFactory = cargarSessionFactory();

    public static SessionFactory cargarSessionFactory() {
        try {
            return HibernateUtil.getSessionFactory();
        } catch (HibernateException ignored) {
            // TODO LOGGER
            return null;
        }
    }

    public static void mostrarJOptionPane(String titulo, String mensaje, int tipo) {
        JButton okButton = new JButton("Entendido");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane(mensaje, tipo, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public static boolean eliminarProveedor(String codigoProv) {
        boolean eliminado = true;

        String titulo = "Error";

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from ProveedoresEntity p where p.codigo='" + codigoProv + "'";
                try {
                    Transaction tx = session.beginTransaction();
                    Query q = session.createQuery(hql).setMaxResults(1);
                    ProveedoresEntity prov = (ProveedoresEntity) q.uniqueResult();
                    session.delete(prov);
                    tx.commit();

                    mostrarJOptionPane("Proveedor Eliminado",
                            "Proveedor '" + codigoProv + "' eliminado con éxito.",
                            1);
                } catch (IllegalArgumentException ex) {
                    String mensaje = "Error al eliminar. No existe el proveedor en cuestión";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe el proveedor en cuestión");
                    eliminado = false;
                } catch (ConstraintViolationException ex) {
                    String mensaje = "Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (TransientPropertyValueException ex) {
                    String mensaje = "Error al eliminar. No se puede eliminar un proveedor\nque no existe o está cargado correctamente";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No se puede eliminar un proveedor\nque no existe o está cargaddo correctamente");
                    eliminado = false;
                }
                session.close();
            } else {
                String mensaje = "No se pudo abrir una sesión. Imposible cargar el proveedor.";
                mostrarJOptionPane(titulo, mensaje, 0);
                System.out.println("No se pudo abrir una sesión. Imposible eliminar el proveedor.");
                eliminado = false;
            }
        } else {
            String mensaje = "SessionFactory no existente. Imposible cargar la lista el proveedor.";
            mostrarJOptionPane(titulo, mensaje, 0);
            System.out.println("SessionFactory no existente. Imposible cargar la lista el proveedor.");
            eliminado = false;
        }

        return eliminado;
    }

}
