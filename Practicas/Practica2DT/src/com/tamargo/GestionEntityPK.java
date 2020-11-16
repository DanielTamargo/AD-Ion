package com.tamargo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class GestionEntityPK implements Serializable {
    private String codProveedor;
    private String codPieza;
    private String codProyecto;

    @Column(name = "codProveedor", nullable = false, length = 6)
    @Id
    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    @Column(name = "codPieza", nullable = false, length = 6)
    @Id
    public String getCodPieza() {
        return codPieza;
    }

    public void setCodPieza(String codPieza) {
        this.codPieza = codPieza;
    }

    @Column(name = "codProyecto", nullable = false, length = 6)
    @Id
    public String getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(String codProyecto) {
        this.codProyecto = codProyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestionEntityPK that = (GestionEntityPK) o;
        return Objects.equals(codProveedor, that.codProveedor) &&
                Objects.equals(codPieza, that.codPieza) &&
                Objects.equals(codProyecto, that.codProyecto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProveedor, codPieza, codProyecto);
    }
}
