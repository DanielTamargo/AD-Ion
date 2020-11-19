package com.tamargo.run;

import com.tamargo.util.CargarDatos;

public class Pruebas {

    public static void main(String[] args) {

        String[] probando = CargarDatos.cargarDatosPiezaMasVecesDistintasSuministrada();

        System.out.println(probando[0] + " | " + probando[1]);
    }

}
