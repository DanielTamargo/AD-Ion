package com.tamargo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "proveedores", schema = "practica2dt", catalog = "practica2dt")
public class ProveedoresEntity {
    private String codigo;
    private String nombre;
    private String apellidos;
    private String direccion;
    private Collection<GestionEntity> gestionsByCodigo;

    public void actualizarDatos(ProveedoresEntity prov) {
        this.nombre = prov.getNombre();
        this.apellidos = prov.getApellidos();
        this.direccion = prov.getDireccion();
    }

    @Override
    public String toString() {
        return String.format("%5s | %s, %s", codigo, apellidos, nombre);
    }

    @Id
    @Column(name = "codigo", nullable = false, length = 6)
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Basic
    @Column(name = "nombre", nullable = false, length = 20)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "apellidos", nullable = false, length = 30)
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Basic
    @Column(name = "direccion", nullable = false, length = 40)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedoresEntity that = (ProveedoresEntity) o;
        return Objects.equals(codigo, that.codigo) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellidos, that.apellidos) &&
                Objects.equals(direccion, that.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, apellidos, direccion);
    }

    @OneToMany(mappedBy = "proveedoresByCodProveedor")
    public Collection<GestionEntity> getGestionsByCodigo() {
        return gestionsByCodigo;
    }

    public void setGestionsByCodigo(Collection<GestionEntity> gestionsByCodigo) {
        this.gestionsByCodigo = gestionsByCodigo;
    }
}
