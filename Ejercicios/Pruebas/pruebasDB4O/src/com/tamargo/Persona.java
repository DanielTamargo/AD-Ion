package com.tamargo;

public class Persona {
    private String nombre;
    private String ciudad;

    public Persona(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public Persona() {
        this.nombre = null;
        this.ciudad = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nom) {
        nombre = nom;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String dir) {
        ciudad = dir;
    }

}