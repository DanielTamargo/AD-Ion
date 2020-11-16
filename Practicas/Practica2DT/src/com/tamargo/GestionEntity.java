package com.tamargo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gestion", schema = "practica2dt", catalog = "")
@IdClass(GestionEntityPK.class)
public class GestionEntity {
    private String codProveedor;
    private String codPieza;
    private String codProyecto;
    private Double cantidad;
    private ProveedoresEntity proveedoresByCodProveedor;
    private PiezasEntity piezasByCodPieza;
    private ProyectosEntity proyectosByCodProyecto;

    @Id
    @Column(name = "codProveedor", nullable = false, length = 6)
    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    @Id
    @Column(name = "codPieza", nullable = false, length = 6)
    public String getCodPieza() {
        return codPieza;
    }

    public void setCodPieza(String codPieza) {
        this.codPieza = codPieza;
    }

    @Id
    @Column(name = "codProyecto", nullable = false, length = 6)
    public String getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(String codProyecto) {
        this.codProyecto = codProyecto;
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
        return Objects.equals(codProveedor, that.codProveedor) &&
                Objects.equals(codPieza, that.codPieza) &&
                Objects.equals(codProyecto, that.codProyecto) &&
                Objects.equals(cantidad, that.cantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProveedor, codPieza, codProyecto, cantidad);
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
