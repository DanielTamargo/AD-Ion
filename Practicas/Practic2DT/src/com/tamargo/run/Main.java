package com.tamargo.run;

import com.tamargo.GestionEntity;
import com.tamargo.PiezasEntity;
import com.tamargo.ProveedoresEntity;
import com.tamargo.ProyectosEntity;
import com.tamargo.util.HibernateUtil;
import com.tamargo.ventanas.VentanaPrincipal;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.Iterator;

public class Main {

    public static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        //System.out.println("\n"); //  <- Saltos de línea para separarlos de los mensajes en rojo que genera Hibernate

        // Métodos para mostrar
        //mostrarProveedores();
        //mostrarGestiones();

        //insertarGestionUltimosComponentes();

        VentanaPrincipal.main(args);

    }

    public static void insertarGestion(PiezasEntity pieza, ProyectosEntity proyecto, ProveedoresEntity proveedor) {
        Session session = sessionFactory.openSession();

        // Creo la gestión
        GestionEntity gestion = new GestionEntity();
        gestion.setPiezasByCodPieza(pieza);
        gestion.setProveedoresByCodProveedor(proveedor);
        gestion.setProyectosByCodProyecto(proyecto);
        gestion.setCantidad(2.0);

        // Inserto la gestión
        Transaction tx = session.beginTransaction();
        try {
            Query q4 = session.createQuery("from GestionEntity g " +
                    "where g.piezasByCodPieza='" + pieza.getCodigo() +
                    "' and g.proveedoresByCodProveedor='" + proveedor.getCodigo() +
                    "' and g.proyectosByCodProyecto='" + proyecto.getCodigo() + "'");
            if (q4.getResultList().size() > 0) {
                System.out.println("No se puede crear la gestión. Ya existe una gestión con estos componentes.");
            } else {
                session.save(gestion);
                tx.commit();
                System.out.println("Gestión creada");
            }
        } catch (ConstraintViolationException ex) {
            System.out.println("Error al crear la gestión. Se ha violado una constraint.\nError: " + ex.getLocalizedMessage());
        } catch (TransientPropertyValueException ignored) {
            System.out.println("Error al asignarle las claves foráneas");
        } catch (HibernateError ignored) {
            System.out.println("Error al crear la gestión");
        } catch (IllegalArgumentException ignored) {
            System.out.println("Error en la query");
        }

        session.close();
    }

    public static void insertarGestionUltimosComponentes() {
        Session session = sessionFactory.openSession();

        PiezasEntity pieza = null;
        ProyectosEntity proyecto = null;
        ProveedoresEntity proveedor = null;

        try {
            // Cojo la última pieza
            Query q1 = session.createQuery("from PiezasEntity order by codigo desc");
            q1.setFetchSize(1);
            pieza = (PiezasEntity) q1.getResultList().get(0);
            System.out.println("Pieza elegida: " + pieza.getCodigo());

            // Cojo el último proyecto
            Query q2 = session.createQuery("from ProyectosEntity order by codigo desc");
            q2.setFetchSize(1);
            proyecto = (ProyectosEntity) q2.getResultList().get(0);
            System.out.println("Proyecto elegido: " + proyecto.getCodigo());

            // Cojo el último proveedor
            Query q3 = session.createQuery("from ProveedoresEntity order by codigo desc");
            q3.setFetchSize(1);
            proveedor = (ProveedoresEntity) q3.getResultList().get(0);
            System.out.println("Proveedor elegido: " + proveedor.getCodigo());
        } catch (Exception ignored) { }

        session.close();

        if (pieza != null && proyecto != null && proveedor != null)
            insertarGestion(pieza, proyecto, proveedor);
        else
            System.out.println("Imposible insertar la gestión. Alguno de los componentes es nulo");

    }

    public static void mostrarGestiones() {
        Session session = sessionFactory.openSession();
        GestionEntity gestion;

        System.out.println("- Lista de gestiones:");
        try {
            Query q = session.createQuery("from GestionEntity");
            Iterator it = q.iterate();

            if (!it.hasNext())
                System.out.println("No existen gestiones en la base de datos.");

            while (it.hasNext()) {
                gestion = (GestionEntity) it.next();

                ProveedoresEntity prov = gestion.getProveedoresByCodProveedor();
                PiezasEntity pieza = gestion.getPiezasByCodPieza();
                ProyectosEntity proyecto = gestion.getProyectosByCodProyecto();

                System.out.format("Cod: %3d | Pieza: %-8s | Proyecto: %-10s | Proveedor: %s\n",
                        gestion.getCodigo(), pieza.getNombre(), proyecto.getNombre(), (prov.getApellidos() + ", " + prov.getNombre()));
            }

        } catch (ObjectNotFoundException | NullPointerException ex) {
            System.out.println("Error al leer los gestiones: " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Error desconocido: " + ex.getLocalizedMessage());
        }

        System.out.println(); //Salto de línea para que el feedback sea más claro
        session.close();
    }

    public static void mostrarProveedores() {
        Session session = sessionFactory.openSession();
        ProveedoresEntity proveedor;

        System.out.println("- Lista de proveedores:");
        try {
            Query q = session.createQuery("from ProveedoresEntity");
            Iterator it = q.iterate();

            if (!it.hasNext())
                System.out.println("No existen proveedores en la base de datos.");

            while (it.hasNext()) {
                proveedor = (ProveedoresEntity) it.next();
                System.out.format("Cod: %-6s | Proveedor: %-30s | Dirección: %s\n",
                        proveedor.getCodigo(), (proveedor.getApellidos() + ", " + proveedor.getNombre()), proveedor.getDireccion());
            }

        } catch (ObjectNotFoundException | NullPointerException ex) {
            System.out.println("Error al leer los proveedores: " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Error desconocido: " + ex.getLocalizedMessage());
        }

        System.out.println(); //Salto de línea para que el feedback sea más claro
        session.close();
    }
}
