package com.tamargo.version3;

import com.tamargo.version1.Empleado;

import java.util.ArrayList;

public class Departamento3 {

    private Integer numDep;
    private String nombre;
    private String ciudad;
    private ArrayList<Empleado3> empleados = new ArrayList<>();

    public Departamento3(Integer numDep, String nombre, String ciudad, ArrayList<Empleado3> empleados) {
        this.numDep = numDep;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.empleados = empleados;
    }

    public Departamento3(Integer numDep, String nombre, String ciudad) {
        this.numDep = numDep;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public Departamento3() {
        this.numDep = null;
        this.nombre = null;
        this.ciudad = null;
        //this.empleados = new ArrayList<Empleado3>();
    }

    public void addEmpleado(Empleado3 empleado) { empleados.add(empleado); }

    public void setEmpleados(ArrayList<Empleado3> empleados) { this.empleados = empleados; }

    public ArrayList<Empleado3> getEmpleados() { return empleados; }

    public int getNumDep() {
        return numDep;
    }

    public void setNumDep(Integer numDep) {
        this.numDep = numDep;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
