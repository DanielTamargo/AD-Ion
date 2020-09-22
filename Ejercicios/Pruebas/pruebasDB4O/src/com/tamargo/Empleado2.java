package com.tamargo;

public class Empleado2 {

    private Integer numEmp;
    private String nombre;
    private String cargo;
    private Departamento2 departamento;

    public Empleado2(Integer numEmp, String nombre, String cargo, Departamento2 departamento) {
        this.numEmp = numEmp;
        this.nombre = nombre;
        this.cargo = cargo;
        this.departamento = departamento;
    }

    public Empleado2() {
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

    public Departamento2 getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento2 departamento) {
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
        /*
        try {
            Integer numDep = this.departamento.getNumDep();
            return "Empleado{" +
                    "numEmp=" + numEmp +
                    ", nombre='" + nombre + '\'' +
                    ", cargo='" + cargo + '\'' +
                    ", departamento=" + departamento.getNumDep() +
                    '}';
        } catch (NullPointerException e) {
            return "Empleado{" +
                    "numEmp=" + numEmp +
                    ", nombre='" + nombre + '\'' +
                    ", cargo='" + cargo + '\'' +
                    ", departamento= sin departamento}";
        }*/
        if (departamento == null) {
            return "Empleado{" +
                    "numEmp=" + numEmp +
                    ", nombre='" + nombre + '\'' +
                    ", cargo='" + cargo + '\'' +
                    ", departamento= sin departamento}";
        } else {
            return "Empleado{" +
                    "numEmp=" + numEmp +
                    ", nombre='" + nombre + '\'' +
                    ", cargo='" + cargo + '\'' +
                    ", departamento=" + departamento.getNumDep() +
                    '}';
        }


    }
}
