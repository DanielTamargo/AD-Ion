package com.tamargo.util;

import com.tamargo.GestionEntity;
import com.tamargo.PiezasEntity;
import com.tamargo.ProveedoresEntity;
import com.tamargo.ProyectosEntity;
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

    // 1 -> proveedor, 2 -> pieza, 3 -> proyecto
    public static String codigoNuevo(int tipo) {
        String nuevoCodigo = "000000";
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql;
                if (tipo == 1)
                    hql = "select p.codigo from ProveedoresEntity p order by p.codigo desc";
                else if (tipo == 2)
                    hql = "select p.codigo from PiezasEntity p order by p.codigo desc";
                else
                    hql = "select p.codigo from ProyectosEntity p order by p.codigo desc";

                try {
                    Query q = session.createQuery(hql).setMaxResults(1);
                    String ultimoCodigo = (String) q.uniqueResult();
                    nuevoCodigo = siguienteCodigo(ultimoCodigo);
                } catch (NullPointerException ignored) {
                    nuevoCodigo = "A00001";
                }
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de proveedores.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de proveedores.");
        }
        return nuevoCodigo;
    }

    public static long cargarNumProyectos() {
        long numProyectos = 0;

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select count(*) from ProyectosEntity";
                Query q = session.createQuery(hql).setFetchSize(1);
                numProyectos = (long) q.uniqueResult();
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }

        return numProyectos;
    }
    
    public static long cargarNumPiezas() {
        long numPiezas = 0;

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select count(*) from PiezasEntity";
                Query q = session.createQuery(hql).setFetchSize(1);
                numPiezas = (long) q.uniqueResult();
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }

        return numPiezas;
    }

    public static boolean gestionProvPiezProyExiste(String codProv, String codPieza, String codProyecto) {
        boolean existe = false;
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select count(*) from GestionEntity p " +
                        " where p.proveedoresByCodProveedor.codigo='" + codProv + "' " +
                        " and p.piezasByCodPieza.codigo='" + codPieza + "' " +
                        " and p.proyectosByCodProyecto.codigo='" + codProyecto + "'";
                Query q = session.createQuery(hql).setFetchSize(1);
                long num = -1;
                try {
                    num = (long) q.uniqueResult();
                } catch (IllegalArgumentException | NullPointerException ignored) {}
                if (num > 0)
                    existe = true;
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }

        return existe;
    }

    public static long cargarNumProvPiezaProyGestion(String codProv, String codPieza, String codProyecto) {
        long numProvPiezaProyecto = 0;

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select count(*) from GestionEntity p " +
                        " where p.proveedoresByCodProveedor.codigo='" + codProv + "' " +
                        " and p.piezasByCodPieza.codigo='" + codPieza + "' " +
                        " and p.proyectosByCodProyecto.codigo='" + codProyecto + "'";
                Query q = session.createQuery(hql).setFetchSize(1);
                numProvPiezaProyecto = (long) q.uniqueResult();
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }

        return numProvPiezaProyecto;
    }

    public static long cargarNumProvPiezaGestion(String codProv, String codPieza) {
        long numProvPieza = 0;

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select count(*) from GestionEntity p " +
                        " where p.proveedoresByCodProveedor.codigo='" + codProv + "' " +
                        " and p.piezasByCodPieza.codigo='" + codPieza + "'";
                Query q = session.createQuery(hql).setFetchSize(1);
                numProvPieza = (long) q.uniqueResult();
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }

        return numProvPieza;
    }

    public static long cargarNumProvGestion(String codProv) {
        long numProvGestion = 0;

        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select count(*) from GestionEntity p where p.proveedoresByCodProveedor.codigo='" + codProv + "'";
                Query q = session.createQuery(hql).setFetchSize(1);
                numProvGestion = (long) q.uniqueResult();
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }

        return numProvGestion;
    }

    public static double cantidadGestionProvPiezProyecto(String codProveedor, String codPieza, String codProyecto) {
        double cantidad = 0f;
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select e.cantidad " +
                        "from GestionEntity e " +
                        "where e.proveedoresByCodProveedor.codigo='" + codProveedor + "'" +
                        " and e.piezasByCodPieza.codigo='" + codPieza + "' " +
                        " and e.proyectosByCodProyecto.codigo='" + codProyecto + "'";
                Query q = session.createQuery(hql).setFetchSize(1);
                cantidad = (double) q.uniqueResult();
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la cantidad.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la cantidad.");
        }
        return cantidad;
    }

    public static ArrayList<ProyectosEntity> proyectosGestionProvPieza(String codProveedor, String codPieza) {
        ArrayList<ProyectosEntity> proyectos = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select e.proyectosByCodProyecto " +
                        "from GestionEntity e " +
                        "where e.proveedoresByCodProveedor.codigo='" + codProveedor + "'" +
                        " and e.piezasByCodPieza.codigo='" + codPieza + "' " +
                        " group by e.proyectosByCodProyecto " +
                        " order by e.proyectosByCodProyecto.codigo";
                Query q = session.createQuery(hql);

                List<ProyectosEntity> lista = q.list();
                proyectos.addAll(lista);
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de proyectos.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de proyectos.");
        }
        return proyectos;
    }

    public static ArrayList<PiezasEntity> piezasGestionProv(String codProveedor) {
        ArrayList<PiezasEntity> piezas = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select e.piezasByCodPieza from GestionEntity e " +
                        " where e.proveedoresByCodProveedor.codigo='" + codProveedor + "' " +
                        " group by e.piezasByCodPieza " +
                        " order by e.piezasByCodPieza.codigo";
                Query q = session.createQuery(hql);

                List<PiezasEntity> lista = q.list();
                piezas.addAll(lista);
                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de piezas.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de piezas.");
        }
        return piezas;
    }

    public static ArrayList<ProveedoresEntity> proveedoresGestion() {
        ArrayList<ProveedoresEntity> proveedores = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "select e.proveedoresByCodProveedor from GestionEntity e group by e.proveedoresByCodProveedor";
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

    public static ArrayList<GestionEntity> gestiones() {
        ArrayList<GestionEntity> gestiones = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from GestionEntity ";
                Query q = session.createQuery(hql);
                List<GestionEntity> lista = q.list();

                gestiones.addAll(lista);

                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de gestiones.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de gestiones.");
        }
        return gestiones;
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

    public static ArrayList<PiezasEntity> piezas() {
        ArrayList<PiezasEntity> piezas = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from PiezasEntity";
                Query q = session.createQuery(hql);
                List<PiezasEntity> lista = q.list();

                piezas.addAll(lista);

                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de piezas.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de piezas.");
        }
        return piezas;
    }

    public static ArrayList<ProyectosEntity> proyectos() {
        ArrayList<ProyectosEntity> proyectos = new ArrayList<>();
        if (sessionFactory != null) {
            Session session = null;
            try {
                session = sessionFactory.openSession();
            } catch (HibernateException ignored) { }

            if (session != null) {
                String hql = "from ProyectosEntity";
                Query q = session.createQuery(hql);
                List<ProyectosEntity> lista = q.list();

                proyectos.addAll(lista);

                session.close();
            } else {
                System.out.println("No se pudo abrir una sesión. Imposible cargar la lista de proyectos.");
            }
        } else {
            System.out.println("SessionFactory no existente. Imposible cargar la lista de proyectos.");
        }
        return proyectos;
    }
    
    
}
