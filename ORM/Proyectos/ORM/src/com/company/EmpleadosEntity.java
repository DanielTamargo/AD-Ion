package com.company;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "empleados", schema = "tema3", catalog = "tema3")
public class EmpleadosEntity {
    private int empNo;
    private String apellido;
    private String oficio;
    private Integer dir;
    private Date fechaAlt;
    private Double salario;
    private Double comision;
    private DepartamentosEntity departamentosByDeptNo;

    @Id
    @Column(name = "emp_no", nullable = false)
    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    @Basic
    @Column(name = "apellido", nullable = true, length = 45)
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Basic
    @Column(name = "oficio", nullable = true, length = 45)
    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    @Basic
    @Column(name = "dir", nullable = true)
    public Integer getDir() {
        return dir;
    }

    public void setDir(Integer dir) {
        this.dir = dir;
    }

    @Basic
    @Column(name = "fecha_alt", nullable = true)
    public Date getFechaAlt() {
        return fechaAlt;
    }

    public void setFechaAlt(Date fechaAlt) {
        this.fechaAlt = fechaAlt;
    }

    @Basic
    @Column(name = "salario", nullable = true, precision = 0)
    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    @Basic
    @Column(name = "comision", nullable = true, precision = 0)
    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadosEntity that = (EmpleadosEntity) o;
        return empNo == that.empNo &&
                Objects.equals(apellido, that.apellido) &&
                Objects.equals(oficio, that.oficio) &&
                Objects.equals(dir, that.dir) &&
                Objects.equals(fechaAlt, that.fechaAlt) &&
                Objects.equals(salario, that.salario) &&
                Objects.equals(comision, that.comision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, apellido, oficio, dir, fechaAlt, salario, comision);
    }

    @ManyToOne
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no")
    public DepartamentosEntity getDepartamentosByDeptNo() {
        return departamentosByDeptNo;
    }

    public void setDepartamentosByDeptNo(DepartamentosEntity departamentosByDeptNo) {
        this.departamentosByDeptNo = departamentosByDeptNo;
    }
}
