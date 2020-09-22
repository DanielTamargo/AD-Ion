package com.tamargo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

class Eliminar1 {

    static String BDPer = "DBPersonas.yap";

    public static void main(String[] args) {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BDPer);

        // Preparamos y realizamos la búsqueda
        Persona per = new Persona("Juan", null);
        ObjectSet<Persona> result = db.queryByExample(per);

        // Nadamos en los resultados
        if (result.size() == 0) {
            System.out.println("No existe 'Juan'");
        } else {
            while (result.hasNext()) {

                // Recogemos el objeto
                Persona p = result.next();

                // Lo eliminamos
                db.delete(p);

                // Notificamos
                System.out.println("Eliminado...");

                // Nota: estamos eliminando todos los 'Juan' de la BBDD
            }
        }

        db.close();

    }
}
