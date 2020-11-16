package com.tamargo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gestion", schema = "practica2dt", catalog = "practica2dt")
public class GestionEntity {
    private int codigo;
    private Double cantidad;
    private ProveedoresEntity proveedoresByCodProveedor;
    private PiezasEntity piezasByCodPieza;
    private ProyectosEntity proyectosByCodProyecto;

    @Id
    @Column(name = "codigo", nullable = false)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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
        return codigo == that.codigo &&
                Objects.equals(cantidad, that.cantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, cantidad);
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
