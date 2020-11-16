package com.tamargo.run;

import com.tamargo.ProveedoresEntity;
import com.tamargo.util.HibernateUtil;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Iterator;

public class Main {

    static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {

        mostrarProveedores();

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
                System.out.format("Cod: %-7s | Proveedor: %-30s | Dirección: %s\n",
                        proveedor.getCodigo(), (proveedor.getApellidos() + "," + proveedor.getNombre()), proveedor.getDireccion());
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
