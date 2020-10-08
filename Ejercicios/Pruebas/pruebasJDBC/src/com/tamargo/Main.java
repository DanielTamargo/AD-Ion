package com.tamargo;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        conexionMySQL();

        System.out.println();

        metadatosMySQL();

        //conexionSQLite();

    }

    public static void conexionSQLite() {
        try {
            Connection conexion = new Conexiones().conexionSQlite();

            /*//Cargar el driver
            Class.forName("org.sqlite.JDBC");
            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:sqlite.db");*/

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery("SELECT * FROM departamentos");
            // Recorremos el resultado para visualizar cada fila
            // Se hace un bucle mientras haya registros, se van visualizando
            while (resul.next()) {
                //System.out.println(resul.getInt(1) + " | " + resul.getString(2) + " | " + resul.getString(3));
                System.out.format("%2d | %15s | %12s\n", resul.getInt(1), resul.getString(2), resul.getString(3));
            }
            resul.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statement

            System.out.println();

            Statement sentencia2 = conexion.createStatement();
            ResultSet result2 = sentencia2.executeQuery("SELECT APELLIDO, OFICIO, SALARIO FROM EMPLEADOS WHERE DEPT_NO=1");
            while (result2.next()) {
                //System.out.println(result2.getString(1) + " | " + result2.getString(2) + " | " + result2.getFloat(3));
                System.out.format("%10s | %10s | %4.2f\n", result2.getString(1), result2.getString(2), result2.getFloat(3));
            }

            result2.close();
            sentencia2.close();

            conexion.close();//Cerrar conexion

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void metadatosMySQL() {

        try {
            //Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/tema2?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "root", "12345Abcde");

            DatabaseMetaData dbmd = conexion.getMetaData();//Creamos objeto DatabaseMetaData
            ResultSet resul = null;
            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();
            System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:\n" +
                    "Tipo   : " + nombre);
            System.out.println("Driver : " + driver);
            System.out.println("URL    : " + url);
            System.out.println("Usuario: " + usuario);

            System.out.println("\nTABLAS:");
            resul = dbmd.getTables("tema2", null, "%", null);
            while (resul.next()) {
                String catalogo = resul.getString(1);
                String esquema = resul.getString(2);
                String tabla = resul.getString(3);
                String tipo = resul.getString(4);
                System.out.println("- " + tipo + " | Catalogo: " + catalogo + ", Esquema : " + esquema + ", Nombre : " + tabla);
                ResultSet columnas = null;
                columnas = dbmd.getColumns(null, null, tabla, null);
                while (columnas.next()) {
                    String nombCol = columnas.getString("COLUMN_NAME");
                    String tipoCol = columnas.getString("TYPE_NAME");
                    String tamCol = columnas.getString("COLUMN_SIZE");
                    String nula = columnas.getString("IS_NULLABLE");
                    System.out.format("Columna: %12s, Tipo: %8s, Tamaño: %3s, Nullable: %3s %n", nombCol, tipoCol, tamCol, nula);
                }
                columnas.close();
                System.out.println();
            }
            resul.close();// Cerrar ResultSet
            conexion.close();//Cerrar conexion
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void conexionMySQL() {

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
                //System.out.println(resul.getInt(1) + " | " + resul.getString(2) + " | " + resul.getString(3));
                System.out.format("%2d | %15s | %12s\n", resul.getInt(1), resul.getString(2), resul.getString(3));
            }
            resul.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statement

            System.out.println();

            Statement sentencia2 = conexion.createStatement();
            ResultSet result2 = sentencia2.executeQuery("SELECT APELLIDO, OFICIO, SALARIO FROM EMPLEADOS WHERE DEPT_NO=1");
            while (result2.next()) {
                //System.out.println(result2.getString(1) + " | " + result2.getString(2) + " | " + result2.getFloat(3));
                System.out.format("%10s | %10s | %4.2f\n", result2.getString(1), result2.getString(2), result2.getFloat(3));
            }

            result2.close();
            sentencia2.close();

            conexion.close();//Cerrar conexion


        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
