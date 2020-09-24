package com.tamargo.version2;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Actividad5v2 {

    final static String BDPer = "EmpleDepV2.yap";

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
        Departamento2 d1 = new Departamento2(10, "Contabilidad", "Sevilla");
        Departamento2 d2 = new Departamento2(20, "Investigación", "Madrid");
        Departamento2 d3 = new Departamento2(30, "Ventas", "Barcelona");


        // Creamos Empleados
        Empleado2 e1 = new Empleado2(7369, "Sánchez", "Empleado", d2);
        Empleado2 e2 = new Empleado2(7499, "Arroyo", "Vendedor", d3);
        Empleado2 e3 = new Empleado2(7521, "Sala", "Vendedor", d3);
        Empleado2 e4 = new Empleado2(7566, "Jiménez", "Director", d2);
        Empleado2 e5 = new Empleado2(7782, "Cerezo", "Director", d1);
        Empleado2 e6 = new Empleado2(7839, "Rey", "Presidente", d1);

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

    public static void visualizarEmpleados() {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Empleado2 e = new Empleado2(null, null, null, null);
        ObjectSet<Empleado2> result = db.queryByExample(e);

        if (result.size() == 0) {
            System.out.println("No existen empleados");
        }
        while (result.hasNext()) {
            Empleado2 emp = result.next();
            System.out.println(emp);
        }

        db.close();
    }

    public static void visualizarDepartamento() {

        // Cambiar esta variable para buscar un departamento u otro
        int id = 10;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        Departamento2 d = new Departamento2(id, null, null);
        ObjectSet<Departamento2> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento2 dep = result.next();
            System.out.println("Departamento: " + dep.getNombre() + " (" + dep.getNumDep() + ")");
            System.out.println("Empleados:");
            Empleado2 e = new Empleado2 (null, null, null, dep);
            ObjectSet<Empleado2> result1 = db.queryByExample(e);
            if (result1.size() == 0)
                System.out.println("- No hay empleados en este departamento.");
            else {
                while(result1.hasNext()) {
                    Empleado2 emp = result1.next();
                    System.out.println("- " + emp);
                }
            }
        }


        db.close();

    }

    public static void borrarDepartamento() {

        int id = 10;

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);
        Departamento2 d = new Departamento2(id, null, null);
        ObjectSet<Departamento2> result = db.queryByExample(d);

        if (result.size() == 0) {
            System.out.println("No hay departamentos con el id: " + id);
        }
        while (result.hasNext()) {
            Departamento2 dep = result.next();
            db.delete(dep);
            System.out.println("Departamento eliminado...");
        }

        db.close();
    }

}
