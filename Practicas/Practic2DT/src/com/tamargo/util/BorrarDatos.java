package com.tamargo.util;

import com.tamargo.GestionEntity;
import com.tamargo.PiezasEntity;
import com.tamargo.ProveedoresEntity;
import com.tamargo.ProyectosEntity;
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

    public static boolean eliminarGestion(String codProv, String codPieza, String codProyecto) {
        boolean eliminado = true;

        String titulo = "Error";

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from GestionEntity p " +
                        " where p.proveedoresByCodProveedor.codigo='" + codProv + "' " +
                        " and p.piezasByCodPieza.codigo='" + codPieza + "' " +
                        " and p.proyectosByCodProyecto.codigo='" + codProyecto + "'";
                try {
                    Transaction tx = session.beginTransaction();
                    Query q = session.createQuery(hql).setMaxResults(1);
                    GestionEntity prov = (GestionEntity) q.uniqueResult();
                    session.delete(prov);
                    tx.commit();

                    mostrarJOptionPane("Gestion Eliminada",
                            "Gestion eliminada con éxito.",
                            1);
                } catch (IllegalArgumentException ex) {
                    String mensaje = "Error al eliminar. No existe la gestion en cuestión";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe la gestion en cuestión");
                    eliminado = false;
                } catch (ObjectNotFoundException ex) {
                    String mensaje = "Error al eliminar. No existe el objeto. Error:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe el objeto. Error\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (ConstraintViolationException ex) {
                    String mensaje = "Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (TransientPropertyValueException ex) {
                    String mensaje = "Error al eliminar. No se puede eliminar una gestion\nque no existe o está cargada correctamente";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No se puede eliminar una gestion\nque no existe o está cargada correctamente");
                    eliminado = false;
                }
                session.close();
            } else {
                String mensaje = "No se pudo abrir una sesión. Imposible cargar el gestion.";
                mostrarJOptionPane(titulo, mensaje, 0);
                System.out.println("No se pudo abrir una sesión. Imposible eliminar el gestion.");
                eliminado = false;
            }
        } else {
            String mensaje = "SessionFactory no existente. Imposible realizar la acción.";
            mostrarJOptionPane(titulo, mensaje, 0);
            System.out.println("SessionFactory no existente. Imposible realizar la acción.");
            eliminado = false;
        }

        return eliminado;
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
                } catch (ObjectNotFoundException ex) {
                    String mensaje = "Error al eliminar. No existe el objeto. Error:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe el objeto. Error\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (ConstraintViolationException ex) {
                    String mensaje = "Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (TransientPropertyValueException ex) {
                    String mensaje = "Error al eliminar. No se puede eliminar un proveedor\nque no existe o está cargado correctamente";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No se puede eliminar un proveedor\nque no existe o está cargado correctamente");
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
            String mensaje = "SessionFactory no existente. Imposible realizar la acción.";
            mostrarJOptionPane(titulo, mensaje, 0);
            System.out.println("SessionFactory no existente. Imposible realizar la acción.");
            eliminado = false;
        }

        return eliminado;
    }

    public static boolean eliminarPieza(String codigoPza) {
        boolean eliminado = true;

        String titulo = "Error";

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from PiezasEntity p where p.codigo='" + codigoPza + "'";
                try {
                    Transaction tx = session.beginTransaction();
                    Query q = session.createQuery(hql).setMaxResults(1);
                    PiezasEntity pza = (PiezasEntity) q.uniqueResult();
                    session.delete(pza);
                    tx.commit();

                    mostrarJOptionPane("Pieza Eliminada",
                            "Pieza '" + codigoPza + "' eliminada con éxito.",
                            1);
                } catch (IllegalArgumentException ex) {
                    String mensaje = "Error al eliminar. No existe la pieza en cuestión";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe la pieza en cuestión");
                    eliminado = false;
                } catch (ObjectNotFoundException ex) {
                    String mensaje = "Error al eliminar. No existe el objeto. Error:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe el objeto. Error\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (ConstraintViolationException ex) {
                    String mensaje = "Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (TransientPropertyValueException ex) {
                    String mensaje = "Error al eliminar. No se puede eliminar una pieza\nque no existe o está cargada correctamente";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No se puede eliminar una pieza\nque no existe o está cargada correctamente");
                    eliminado = false;
                }
                session.close();
            } else {
                String mensaje = "No se pudo abrir una sesión. Imposible realizar la acción.";
                mostrarJOptionPane(titulo, mensaje, 0);
                System.out.println("No se pudo abrir una sesión. Imposible realizar la acción.");
                eliminado = false;
            }
        } else {
            String mensaje = "SessionFactory no existente.Imposible realizar la acción.";
            mostrarJOptionPane(titulo, mensaje, 0);
            System.out.println("SessionFactory no existente. Imposible realizar la acción.");
            eliminado = false;
        }

        return eliminado;
    }

    public static boolean eliminarProyecto(String codigoProy) {
        boolean eliminado = true;

        String titulo = "Error";

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from ProyectosEntity p where p.codigo='" + codigoProy + "'";
                try {
                    Transaction tx = session.beginTransaction();
                    Query q = session.createQuery(hql).setMaxResults(1);
                    ProyectosEntity proy = (ProyectosEntity) q.uniqueResult();
                    session.delete(proy);
                    tx.commit();

                    mostrarJOptionPane("Proyecto Eliminado",
                            "Proyecto '" + codigoProy + "' eliminado con éxito.",
                            1);
                } catch (IllegalArgumentException ex) {
                    String mensaje = "Error al eliminar. No existe el proyecto en cuestión";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No existe el proyecto en cuestión");
                    eliminado = false;
                } catch (ConstraintViolationException ex) {
                    String mensaje = "Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage();
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. Se está incumpliendo la constraint:\n" + ex.getLocalizedMessage());
                    eliminado = false;
                } catch (TransientPropertyValueException ex) {
                    String mensaje = "Error al eliminar. No se puede eliminar un proyecto\nque no existe o está cargado correctamente";
                    mostrarJOptionPane(titulo, mensaje, 0);
                    System.out.println("Error al eliminar. No se puede eliminar un proyecto\nque no existe o está cargado correctamente");
                    eliminado = false;
                }
                session.close();
            } else {
                String mensaje = "No se pudo abrir una sesión. Imposible cargar el proyecto.";
                mostrarJOptionPane(titulo, mensaje, 0);
                System.out.println("No se pudo abrir una sesión. Imposible eliminar el proyecto.");
                eliminado = false;
            }
        } else {
            String mensaje = "SessionFactory no existente. Imposible realizar la acción.";
            mostrarJOptionPane(titulo, mensaje, 0);
            System.out.println("SessionFactory no existente. Imposible realizar la acción.");
            eliminado = false;
        }

        return eliminado;
    }

}
