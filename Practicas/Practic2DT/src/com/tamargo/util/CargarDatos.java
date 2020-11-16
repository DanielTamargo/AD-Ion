package com.tamargo.util;

import com.tamargo.ProveedoresEntity;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CargarDatos {
    
    public static SessionFactory sessionFactory = cargarSessionFactory();

    public static SessionFactory cargarSessionFactory() {
        try {
            return HibernateUtil.getSessionFactory();
        } catch (HibernateException ignored) {
            // TODO LOGGER
            return null;
        }
    }

    public static String siguienteCodigo(String codigo) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWYXZ";

        char letra = codigo.charAt(0);
        int numero;
        try {
            numero = Integer.parseInt(codigo.substring(1));
        } catch (NumberFormatException ignored) {
            return codigo;
        }

        numero++;
        if (numero > 99999) {
            letra = abc.charAt(abc.indexOf(letra) + 1); // Pasamos a la siguiente letra
            numero = 1; // Volvemos a empezar desde 1
        }


        return String.valueOf(letra) + String.format("%05d", numero);
    }

    public static String codigoNuevoProveedor() {
        String nuevoCodigo = "000000";
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select p.codigo from ProveedoresEntity p order by p.codigo desc";
                Query q = session.createQuery(hql).setMaxResults(1);
                String ultimoCodigo = (String)q.uniqueResult();
                nuevoCodigo = siguienteCodigo(ultimoCodigo); // TODO TRANSFORMAR CODIGO
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de proveedores.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de proveedores.");
        }
        return nuevoCodigo;
    }

    public static ArrayList<ProveedoresEntity> proveedores() {
        ArrayList<ProveedoresEntity> proveedores = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from ProveedoresEntity";
                Query q = session.createQuery(hql);
                List<ProveedoresEntity> lista = q.list();

                proveedores.addAll(lista);

                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de proveedores.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de proveedores.");
        }
        return proveedores;
    }
    
    
}
