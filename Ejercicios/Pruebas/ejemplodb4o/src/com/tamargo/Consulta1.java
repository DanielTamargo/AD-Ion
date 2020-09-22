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
        if (result.size() == 0) {
            System.out.println("No existe hay personas en la BD");
        }
        while (result.hasNext()) {
            Persona p = result.next();
            System.out.println("\tNombre: " + p.getNombre());
            System.out.println("\tCiudad:" + p.getCiudad());
        }

        //búsqueda de objetos Persona con nombre Juan
        Persona per1 = new Persona("Juan", null);
        ObjectSet<Persona> result1 = db.queryByExample(per);

        //búsqueda de objetos Persona con ciudad Vitoria
        Persona per2 = new Persona(null, "Vitoria");
        ObjectSet<Persona> result2 = db.queryByExample(per2);
    }
}
