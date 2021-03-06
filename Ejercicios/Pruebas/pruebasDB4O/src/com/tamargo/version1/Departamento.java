package com.tamargo.version1;

public class Departamento {

    private Integer numDep;
    private String nombre;
    private String ciudad;

    public Departamento(Integer numDep, String nombre, String ciudad) {
        this.numDep = numDep;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public Departamento() {
        this.numDep = null;
        this.nombre = null;
        this.ciudad = null;
    }

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
