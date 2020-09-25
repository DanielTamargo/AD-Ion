package com.tamargo;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            //Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/tema2?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "root", "12345Abcde");

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery("SELECT * FROM departamentos");
            // Recorremos el resultado para visualizar cada fila
            // Se hace un bucle mientras haya registros, se van visualizando
            while (resul.next()) {
                System.out.println(resul.getInt(1) + " " + resul.getString(2) + " " + resul.getString(3));
            }
            resul.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statement
            conexion.close();//Cerrar conexion

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//fin de main
}
