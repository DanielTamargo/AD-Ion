package com.tamargo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "proyectos", schema = "practica2dt", catalog = "practica2dt")
public class ProyectosEntity {
    private String codigo;
    private String nombre;
    private String ciudad;
    private Collection<GestionEntity> gestionsByCodigo;

    public void actualizarDatos(ProyectosEntity proy) {
        this.nombre = proy.getNombre();
        this.ciudad = proy.getCiudad();
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
    @Column(name = "nombre", nullable = false, length = 40)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "ciudad", nullable = true, length = 40)
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectosEntity that = (ProyectosEntity) o;
        return Objects.equals(codigo, that.codigo) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(ciudad, that.ciudad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, ciudad);
    }

    @OneToMany(mappedBy = "proyectosByCodProyecto")
    public Collection<GestionEntity> getGestionsByCodigo() {
        return gestionsByCodigo;
    }

    public void setGestionsByCodigo(Collection<GestionEntity> gestionsByCodigo) {
        this.gestionsByCodigo = gestionsByCodigo;
    }
}
