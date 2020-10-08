package com.tamargo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManejoDatos {

    private int num;

    public ArrayList<Integer> cargarEmpleados() {

        Connection conexion = null;
        ArrayList<Integer> empleados = new ArrayList<>();

        if (num == 1)
            conexion = new Conexiones().conexionSQlite();
        else if (num == 2)
            conexion = new Conexiones().conexionSQlite();
        else if (num == 3)
            conexion = new Conexiones().conexionSQlite();

        try {
        // SELECT * ..............
        //


            conexion.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return empleados;

    }


}
