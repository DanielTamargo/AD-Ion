package com.tamargo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

class Modificar1 {

    static String BDPer = "DBPersonas.yap";

    public static void main(String[] args) {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        // Preparamos y realizamos la búsqueda
        Persona per = new Persona("Juan", null);
        ObjectSet<Persona> result = db.queryByExample(per);

        // Nadamos en los resultados
        if (result.size() == 0) {
            System.out.println("No existe ningún 'Juan' en la BBDD");
        } else {
            while (result.hasNext()) {

                // Cogemos el objeto
                Persona existe = result.next();

                // Le modificamos la ciudad
                existe.setCiudad("Vitoria");

                // Lo guardamos
                db.store(existe);

                // Mostramos el objeto final
                System.out.println(existe.getNombre() + ", " + existe.getCiudad());
            }
            // Notificamos
            System.out.println("¡Todos los 'Juan' han sido modificados!");
        }

        db.close();

    }
}
