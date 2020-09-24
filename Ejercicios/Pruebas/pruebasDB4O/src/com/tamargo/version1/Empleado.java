package com.tamargo.version1;

public class Empleado {

    private Integer numEmp;
    private String nombre;
    private String cargo;
    private Integer departamento;

    public Empleado(Integer numEmp, String nombre, String cargo, Integer departamento) {
        this.numEmp = numEmp;
        this.nombre = nombre;
        this.cargo = cargo;
        this.departamento = departamento;
    }

    public Empleado() {
        this.numEmp = null;
        this.nombre = null;
        this.cargo = null;
        this.departamento = null;
    }

    public int getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(Integer numEmp) {
        this.numEmp = numEmp;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Integer departamento) {
        this.departamento = departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nom) {
        nombre = nom;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String car) {
        cargo = car;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "numEmp=" + numEmp +
                ", nombre='" + nombre + '\'' +
                ", cargo='" + cargo + '\'' +
                ", departamento=" + departamento +
                '}';
    }
}
