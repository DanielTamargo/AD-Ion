package com.tamargo.run;

import com.tamargo.PiezasEntity;
import com.tamargo.ProveedoresEntity;
import com.tamargo.ProyectosEntity;
import com.tamargo.util.CargarDatos;

import java.util.ArrayList;

public class Pruebas {

    public static void main(String[] args) {
        long numPiezas = CargarDatos.cargarNumProyectos();
        System.out.println(numPiezas);
    }

}
