package com.tamargo;

import javax.persistence.*;

@Entity
@Table(name = "gestion", schema = "practica2dt", catalog = "")
public class GestionEntity {
    private Double cantidad;
    private ProveedoresEntity proveedoresByCodProveedor;
    private PiezasEntity piezasByCodPieza;
    private ProyectosEntity proyectosByCodProyecto;

    @Basic
    @Column(name = "cantidad", nullable = true, precision = 0)
    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GestionEntity that = (GestionEntity) o;

        if (cantidad != null ? !cantidad.equals(that.cantidad) : that.cantidad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cantidad != null ? cantidad.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "codProveedor", referencedColumnName = "codigo", nullable = false)
    public ProveedoresEntity getProveedoresByCodProveedor() {
        return proveedoresByCodProveedor;
    }

    public void setProveedoresByCodProveedor(ProveedoresEntity proveedoresByCodProveedor) {
        this.proveedoresByCodProveedor = proveedoresByCodProveedor;
    }

    @ManyToOne
    @JoinColumn(name = "codPieza", referencedColumnName = "codigo", nullable = false)
    public PiezasEntity getPiezasByCodPieza() {
        return piezasByCodPieza;
    }

    public void setPiezasByCodPieza(PiezasEntity piezasByCodPieza) {
        this.piezasByCodPieza = piezasByCodPieza;
    }

    @ManyToOne
    @JoinColumn(name = "codProyecto", referencedColumnName = "codigo", nullable = false)
    public ProyectosEntity getProyectosByCodProyecto() {
        return proyectosByCodProyecto;
    }

    public void setProyectosByCodProyecto(ProyectosEntity proyectosByCodProyecto) {
        this.proyectosByCodProyecto = proyectosByCodProyecto;
    }
}
