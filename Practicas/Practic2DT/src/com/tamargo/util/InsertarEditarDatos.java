package com.tamargo.util;

import com.tamargo.ProveedoresEntity;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import javax.swing.*;

public class InsertarEditarDatos {

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

    // tipoAccion == 1 -> save, == 2 -> update
    public static boolean saveUpdateProveedor(ProveedoresEntity proveedor, int tipoAccion) {
        boolean accionCompletada = true;

        String titulo = "Error";

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                try {
                    Transaction tx = session.beginTransaction();

                    if (tipoAccion == 1) {
                        session.save(proveedor);
                        tx.commit();
                        mostrarJOptionPane("Proveedor Insertado",
                                "Proveedor '" + proveedor.getCodigo() + "' insertado con éxito.",
                                1);
                    } else if (tipoAccion == 2) {
                        String hql = "from ProveedoresEntity p where p.codigo='" + proveedor.getCodigo() + "'";
                        Query q = session.createQuery(hql).setMaxResults(1);
                        ProveedoresEntity prov = (ProveedoresEntity) q.uniqueResult();
                        prov.actualizarDatos(proveedor);
                        session.update(prov);
                        tx.commit();
                        mostrarJOptionPane("Proveedor Editado",
                                "Proveedor '" + proveedor.getCodigo() + "' editado con éxito.",
                                1);
                    }
                } catch (IllegalArgumentException ex) {
                    String mensaje = "Error al insertar/editar. No existe el proveedor en cuestión";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al insertar/editar. No existe el proveedor en cuestión");
                    accionCompletada = false;
                } catch (ConstraintViolationException ex) {
                    String mensaje = "Error al insertar/editar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al insertar/editar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage());
                    accionCompletada = false;
                } catch (TransientPropertyValueException ex) {
                    String mensaje = "Error al insertar/editar. No se puede insertar/editar un proveedor\nque no existe o está cargado correctamente";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al insertar/editar. No se puede insertar/editar un proveedor\nque no existe o está cargaddo correctamente");
                    accionCompletada = false;
                }
                session.close();
            } else {
                String mensaje = "No se pudo abrir una sesión. Imposible insertar/actualizar el proveedor.";
                mostrarJOptionPane(titulo, mensaje, 0);
                System.out.println("No se pudo abrir una sesión. Imposible insertar/actualizar el proveedor.");
                accionCompletada = false;
            }
        } else {
            String mensaje = "SessionFactory no existente. Imposible insertar/actualizar el proveedor.";
            mostrarJOptionPane(titulo, mensaje, 0);
            System.out.println("SessionFactory no existente. Imposible insertar/actualizar el proveedor.");
            accionCompletada = false;
        }

        return accionCompletada;
    }

}
