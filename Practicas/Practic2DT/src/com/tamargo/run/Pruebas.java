package com.tamargo.run;

import com.tamargo.util.CargarDatos;

public class Pruebas {

    public static void main(String[] args) {

        float[] probando = CargarDatos.piezaDatosListadoEstadisticas("G00002");

        for (float v : probando) {
            System.out.println(v);
        }
    }

}
