package com.tamargo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

class Consulta1 {

    static String BDPer = "DBPersonas.yap";

    public static void main(String[] args) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        //devuelve todos los objetos persona de la bd
        Persona per = new Persona(null, null);
        ObjectSet<Persona> result = db.queryByExample(per);

        // Búsqueda de objetos Persona con nombre Juan
        Persona per1 = new Persona("Juan", null);
        ObjectSet<Persona> result1 = db.queryByExample(per1);

        // Búsqueda de objetos Persona con ciudad Vitoria
        Persona per2 = new Persona(null, "Vitoria");
        ObjectSet<Persona> result2 = db.queryByExample(per2);


        System.out.println("- Todas las personas guardadas: ");
        if (result.size() == 0) {
            System.out.println("No hay personas en la BD");
        }
        while (result.hasNext()) {
            Persona p = result.next();
            System.out.println(p.getNombre() + ", " + p.getCiudad());
        }

        System.out.println("\n- Todas las personas llamadas Juan: ");
        if (result1.size() == 0) {
            System.out.println("No hay personas en la BD");
        }
        while (result1.hasNext()) {
            Persona p = result1.next();
            System.out.println(p.getNombre() + ", " + p.getCiudad());
        }

        System.out.println("\n- Todas las personas de Vitoria: ");
        if (result2.size() == 0) {
            System.out.println("No hay personas en la BD");
        }
        while (result2.hasNext()) {
            Persona p = result2.next();
            System.out.println(p.getNombre() + ", " + p.getCiudad());
        }

        db.close();

    }
}
