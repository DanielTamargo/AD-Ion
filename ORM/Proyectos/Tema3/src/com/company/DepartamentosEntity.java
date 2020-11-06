package com.company;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "departamentos", schema = "tema3", catalog = "tema3")
public class DepartamentosEntity {
    private int deptNo;
    private String dnombre;
    private String loc;

    @Id
    @Column(name = "dept_no", nullable = false)
    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    @Basic
    @Column(name = "dnombre", nullable = true, length = 45)
    public String getDnombre() {
        return dnombre;
    }

    public void setDnombre(String dnombre) {
        this.dnombre = dnombre;
    }

    @Basic
    @Column(name = "loc", nullable = true, length = 45)
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartamentosEntity that = (DepartamentosEntity) o;
        return deptNo == that.deptNo &&
                Objects.equals(dnombre, that.dnombre) &&
                Objects.equals(loc, that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptNo, dnombre, loc);
    }
}
