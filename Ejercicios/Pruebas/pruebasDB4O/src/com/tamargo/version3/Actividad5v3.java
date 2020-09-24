package com.tamargo.version3;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Actividad5v3 {

    final static String BDPer = "EmpleDepV3.yap";

    public static void main(String[] args) {

        System.out.println("Insertar datos:\n");
        insertarDatos();
        System.out.println();
/*
        System.out.println("Visualizar empleados:\n");
        visualizarEmpleados();
        System.out.println();

        System.out.println("Visualizar departamento:\n");
        visualizarDepartamento();
        System.out.println();

        System.out.println("Borrar departamento:\n");
        borrarDepartamento();
        System.out.println();

        System.out.println("Visualizar empleados tras eliminar un departamento:\n");
        visualizarEmpleados();
        System.out.println();
*/
        System.out.println("Visualizar departamentos y empleados antes de eliminar los empleados de un departamento.\n");
        visualizarDepartamentos();
        System.out.println();

        System.out.println("Eliminar los empleados de un departamento.\n");
        eliminarEmpleadosDeUnDepartamento();
        System.out.println();

        System.out.println("Visualizar departamentos y empleados tras eliminar los empleados de un departamento.\n");
        visualizarDepartamentos();
        System.out.println();

        System.out.println("Visualizar todos los empleados restantes en la BBDD");
        visualizarEmpleados();
        System.out.println();

        System.out.println("Visualizar todos los empleados a través del ArrayList de los departamentos");
        visualizarEmpleadosListaDepartamento();

    }

    public static void visualizarDepartamentos() {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Departamento3 d = new Departamento3(null, null, null, null);
        ObjectSet<Departamento3> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos.");
        }
        while (result.hasNext()) {
            Departamento3 dep = result.next();
            System.out.println("Empleados del departamento " + dep.getNumDep() + ":");


            // Version buscar los Empleados en la BBDD
            Empleado3 e = new Empleado3 (null, null, null, dep);
            ObjectSet<Empleado3> result1 = db.queryByExample(e);
            if (result1.size() == 0)
                System.out.println("- No hay empleados en este departamento.");
            else {
                while(result1.hasNext()) {
                    Empleado3 emp = result1.next();
                    System.out.println("- " + emp);
                }
            }

            /*
            // Version coger los Empleados de la lista del Departamento
            for (Empleado3 emp : dep.getEmpleados()) {
                System.out.println("- " + emp);
            }*/
        }

        db.close();
    }

    public static void eliminarEmpleadosDeUnDepartamento() {

        // Cambiar esta variable para eliminar los empleados de un departamento u otro
        int id = 30;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);
        Departamento3 d = new Departamento3(id, null, null, null);
        ObjectSet<Departamento3> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento3 dep = result.next();

            /*
            // Versión eliminar la lista de empleados guardada en el objeto departamento
            for (Empleado3 emp: dep.getEmpleados()) {
                db.delete(emp);
            }
            System.out.println("Empleados del departamento '" + dep.getNumDep() + "' eliminados...");
            */


            // Versión eliminar los empleados del departamento a través de buscarlos en la BBDD
            // Los deja huérfanos
            Empleado3 e = new Empleado3(null, null, null, dep);
            ObjectSet<Empleado3> result2 = db.queryByExample(e);
            if (result2.size() == 0)
                System.out.println("No hay empleados en el departamento: " + dep.getNumDep());
            else {
                while (result2.hasNext()) {
                    Empleado3 emp = result2.next();
                    db.delete(emp);
                }
                System.out.println("Empleados del departamento '" + dep.getNumDep() + "' eliminados...");
            }



        }

        db.close();
    }


    public static void insertarDatos() {
        //Abrir la BD
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        // Creamos Departamentos
        Departamento3 d1 = new Departamento3(10, "Contabilidad", "Sevilla");
        Departamento3 d2 = new Departamento3(20, "Investigación", "Madrid");
        Departamento3 d3 = new Departamento3(30, "Ventas", "Barcelona");

        // Creamos Empleados y les metemos en el departamento (la gallidepartamento se crea y se introducen huevoempleados)
        Empleado3 e1 = new Empleado3(7369, "Sánchez", "Empleado", d2);
        Empleado3 e2 = new Empleado3(7499, "Arroyo", "Vendedor", d3);
        Empleado3 e3 = new Empleado3(7521, "Sala", "Vendedor", d3);
        Empleado3 e4 = new Empleado3(7566, "Jiménez", "Director", d2);
        Empleado3 e5 = new Empleado3(7782, "Cerezo", "Director", d1);
        Empleado3 e6 = new Empleado3(7839, "Rey", "Presidente", d1);

        // Añadimos los empleados a los departamentos
        d1.addEmpleado(e5);
        d1.addEmpleado(e6);

        d2.addEmpleado(e4);
        d2.addEmpleado(e1);

        d3.addEmpleado(e2);
        d3.addEmpleado(e3);

        // Almacenamos los departamentos
        db.store(d1);
        db.store(d2);
        db.store(d3);

        // Almacenamos los empleados
        db.store(e1);
        db.store(e2);
        db.store(e3);
        db.store(e4);
        db.store(e5);
        db.store(e6);

        System.out.println("¡Departamentos y Empleados guardados con éxito!");

        // Cerrar base de datos
        db.close();
    }

    public static void visualizarEmpleadosListaDepartamento() {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Departamento3 d = new Departamento3(null, null, null, null);
        ObjectSet<Departamento3> result = db.queryByExample(d);
        while (result.hasNext()) {
            Departamento3 dep = result.next();
            for (Empleado3 emp: dep.getEmpleados()) {
                System.out.println(emp);
            }
        }

        db.close();
    }

    public static void visualizarEmpleados() {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Empleado3 e = new Empleado3(null, null, null, null);
        ObjectSet<Empleado3> result = db.queryByExample(e);

        if (result.size() == 0) {
            System.out.println("No existen empleados");
        }
        while (result.hasNext()) {
            Empleado3 emp = result.next();
            System.out.println(emp);
        }

        db.close();
    }

    public static void visualizarDepartamento() {

        // Cambiar esta variable para buscar un departamento u otro
        int id = 10;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Departamento3 d = new Departamento3(id, null, null, null);
        ObjectSet<Departamento3> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento3 dep = result.next();
            System.out.println("Departamento: " + dep.getNombre() + " (" + dep.getNumDep() + ")");
            System.out.println("Empleados:");
            Empleado3 e = new Empleado3(null, null, null, dep);
            ObjectSet<Empleado3> result1 = db.queryByExample(e);
            if (result1.size() == 0)
                System.out.println("- No hay empleados en este departamento.");
            else {
                while (result1.hasNext()) {
                    Empleado3 emp = result1.next();
                    System.out.println("- " + emp);
                }
            }
        }

        db.close();
    }

    public static void borrarDepartamento() {

        // Cambiar esta variable para eliminar un departamento u otro
        int id = 10;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);
        Departamento3 d = new Departamento3(id, null, null, null);
        ObjectSet<Departamento3> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento3 dep = result.next();
            db.delete(dep);
            System.out.println("Departamento eliminado...");
        }

        db.close();
    }

}
