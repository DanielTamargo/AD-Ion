package com.tamargo.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory()
    {
        try {
            return new Configuration().configure().buildSessionFactory(
                    new StandardServiceRegistryBuilder().configure().build ());
        }
        catch (Throwable ex)
        {
            System.err.println("Error al arrancar el sessionFactory." + ex);
            return null;
        }
    }
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

}

