package com.tamargo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexiones {




    public Connection conexionSQlite() {

        // Establecemos la conexion con la BD
        Connection conexion = null;
        try {
        Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:sqlite.db");
        } catch (SQLException | ClassNotFoundException throwables) {
            // error al conectar = sacar JOptionPane
            throwables.printStackTrace();

        }

        return conexion;

    }


}
