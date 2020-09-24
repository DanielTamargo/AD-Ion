package com.tamargo.version1;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Actividad5 {

    final static String BDPer = "EmpleDep.yap";

    public static void main(String[] args) {

        System.out.println("Insertar datos:\n");
        insertarDatos();
        System.out.println();

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

    }


    public static void insertarDatos() {
        //Abrir la BD
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        // Creamos Departamentos
        Departamento d1 = new Departamento(10, "Contabilidad", "Sevilla");
        Departamento d2 = new Departamento(20, "Investigación", "Madrid");
        Departamento d3 = new Departamento(30, "Ventas", "Barcelona");


        // Creamos Empleados
        Empleado e1 = new Empleado(7369, "Sánchez", "Empleado", 20);
        Empleado e2 = new Empleado(7499, "Arroyo", "Vendedor", 30);
        Empleado e3 = new Empleado(7521, "Sala", "Vendedor", 30);
        Empleado e4 = new Empleado(7566, "Jiménez", "Director", 20);
        Empleado e5 = new Empleado(7782, "Cerezo", "Director", 10);
        Empleado e6 = new Empleado(7839, "Rey", "Presidente", 10);

        // Almacenamos los departamentos
        db.store(d1);
        db.store(d2);
        db.store(d3);

        // Almacenamos los empleados (daría igual insertarlos antes que los departamentos porque no relaciona)
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

    public static void borrarDepartamento() {

        int id = 10;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);
        Departamento d = new Departamento(id, null, null);
        ObjectSet<Departamento> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento dep = result.next();
            db.delete(dep);
            System.out.println("Departamento eliminado.");
        }

        db.close();
    }

    public static void visualizarEmpleados() {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Empleado e = new Empleado(null, null, null, null);
        ObjectSet<Empleado> result = db.queryByExample(e);

        if (result.size() == 0) {
            System.out.println("No existen empleados");
        }
        while (result.hasNext()) {
            Empleado emp = result.next();
            System.out.println(emp);
        }

        db.close();
    }

    public static void visualizarDepartamento() {

        // Cambiar esta variable para buscar un departamento u otro
        int id = 10;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Departamento d = new Departamento(id, null, null);
        ObjectSet<Departamento> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento dep = result.next();
            System.out.println("Departamento: " + dep.getNombre());
            System.out.println("Empleados:");
            Empleado e = new Empleado (null, null, null, id);
            ObjectSet<Empleado> result1 = db.queryByExample(e);
            if (result1.size() == 0)
                System.out.println("- No hay empleados en este departamento.");
            else {
                while(result1.hasNext()) {
                    Empleado emp = result1.next();
                    System.out.println("- " + emp);
                }
            }
        }


        db.close();

    }

}
