package com.company;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Main {

    static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        //sessionFactory = HibernateUtil.getSessionFactory();

        //mostrarDepartamentos();

        //inserts();
        //updates();
        deletes();

        //mostrarEmpleadosYSuDepartamento();
        //mostrarDepSalMedioYNumEmp();

        //modificarSalarioEmpleadoConcreto();
        //eliminarEmpleadoConQuery();
    }


    public static void eliminarEmpleadoConQuery() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            String hqlDel = "delete from EmpleadosEntity e where e.empNo=?1";

            Query q1 = session.createQuery(hqlDel);
            q1.setParameter(1, 16);

            int filasModif = q1.executeUpdate();
            tx.commit();

            System.out.printf("FILAS BORRADAS: %d %n", filasModif);
        } catch (ConstraintViolationException ignored) {
            System.out.println("Error al eliminar");
        }
        session.close();
    }

    public static void modificarSalarioEmpleadoConcreto() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            String hqlModif = "update EmpleadosEntity set salario = ?1 where apellido =?2";

            Query q1 = session.createQuery(hqlModif);
            q1.setParameter(1, (double) 3500.34);
            q1.setParameter(2, "Tamargo");

            int filasModif = q1.executeUpdate();
            tx.commit();

            System.out.printf("FILAS MODIFICADAS: %d %n", filasModif);
        } catch (ConstraintViolationException ignored) {
            System.out.println("Error al modificar");
        }
        session.close();
    }

    public static void mostrarDepSalMedioYNumEmp() {
        Session session = sessionFactory.openSession();

        String hql = "select e.departamentosByDeptNo.deptNo, AVG(e.salario), count(e.empNo) " +
                "from EmpleadosEntity e " +
                "group by e.departamentosByDeptNo.deptNo";
        Query cons = session.createQuery(hql);
        Iterator iter = cons.iterate();
        while (iter.hasNext()) {
            Object[] par = (Object[]) iter.next();
            Integer deptNo = (Integer) par[0];
            Double mediaSalario = (Double) par[1];
            Long numEmpleados = (Long) par[2];
            System.out.printf("Departamento: %2d, Media: %5.2f, NumEmpleados: %-2d%n", deptNo, mediaSalario, numEmpleados);
        }
        session.close();
    }

    public static void mostrarSalarioMedioEmpleados() {
        Session session = sessionFactory.openSession();

        String hql = "select AVG (em.salario) from EmpleadosEntity as em";
        Query cons = session.createQuery(hql);
        Double suma = (Double) cons.uniqueResult();
        System.out.printf("Salario medio: %.2f%n", suma);

        session.close();
    }

    public static void mostrarEmpleadosYSuDepartamento() {
        Session session = sessionFactory.openSession();

        String hql = "from EmpleadosEntity e, DepartamentosEntity d " +
                "where e.departamentosByDeptNo.deptNo=d.deptNo " +
                "order by e.departamentosByDeptNo.deptNo";

        Query q = session.createQuery(hql);

        Iterator it = q.iterate();

        while (it.hasNext()) {
            Object[] objetos = (Object[]) it.next();
            EmpleadosEntity emp = (EmpleadosEntity) objetos[0];
            DepartamentosEntity dep = (DepartamentosEntity) objetos[1];

            System.out.format("Empleado:           %-15s| %s\n", emp.getApellido(), emp.getOficio());
            System.out.format("Departamento: %-3d-> %s\n\n", dep.getDeptNo(), dep.getDnombre());

        }


        session.close();
    }

    public static void deletes() {
        System.out.println("- Deletes:");
        eliminarDepartamento(3);

        System.out.println();
    }

    public static void updates() {
        System.out.println("- Updates:");
        actualizarEmpleado(1);
        actualizarDepartamento(8);
        System.out.println();
    }

    public static void inserts() {
        System.out.println("- Inserts:");
        insertarNuevoDepartamento();
        insertarNuevoEmpleado();
        System.out.println();
    }



    public static void eliminarDepartamento(int numDep) {

        Session session = sessionFactory.openSession();
        DepartamentosEntity dep;

        try {
            dep = session.load(DepartamentosEntity.class, numDep);
            Transaction tx = session.beginTransaction();
            session.delete(dep);
            tx.commit();
            System.out.println("Departamento " + dep.getDeptNo() + " eliminado con éxito.");
        } catch (ObjectNotFoundException ignored) {
            System.out.println("Error. No existe el departamento.");
        } catch (ConstraintViolationException ex) {
            System.out.println("Error. Se ha violado alguna constraint: " + ex.getConstraintName());
            System.out.println("Es posible que el departamento tuviera empleados.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
        }

        session.close();

    }

    public static void actualizarDepartamento(int numDep) {

        Session session = sessionFactory.openSession();
        DepartamentosEntity dep;
        try {
            dep = session.load(DepartamentosEntity.class, numDep);
            dep.setLoc("Bilbao");
            session.update(dep);
            Transaction tx = session.beginTransaction();
            tx.commit();
            System.out.println("Localización del departamento " + dep.getDeptNo() +
                    " modificada (anterior: Vitoria, nueva: " + dep.getLoc() + ").");
        } catch (ObjectNotFoundException ignored) {
            System.out.println("Error. No existe el departamento.");
        } catch (ConstraintViolationException ex) {
            System.out.println("Error. Se ha violado alguna constraint: " + ex.getConstraintName());
        } catch (Exception ex) {
            System.out.println("Error desconocido: " + ex.getLocalizedMessage());
        }
        session.close();

    }

    public static void actualizarEmpleado(int numEmp) {

        Session session = sessionFactory.openSession();
        EmpleadosEntity emp;
        try {
            emp = session.load(EmpleadosEntity.class, numEmp);
            emp.setSalario(emp.getSalario() + 100);
            session.update(emp);
            Transaction tx = session.beginTransaction();
            tx.commit();
            System.out.println("Salario del empleado " + emp.getEmpNo() +
                    " modificado (anterior: " + (emp.getSalario() - 100) + ", nuevo: " + emp.getSalario() + ").");
        } catch (ObjectNotFoundException ignored) {
            System.out.println("Error. No existe el empleado.");
        } catch (ConstraintViolationException ex) {
            System.out.println("Error. Se ha violado alguna constraint: " + ex.getConstraintName());
        } catch (Exception ex) {
            System.out.println("Error desconocido: " + ex.getLocalizedMessage());
        }
        session.close();

    }

    public static void insertarNuevoEmpleado() {
        int numEmp = 0;

        Session session = sessionFactory.openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EmpleadosEntity> criteria = builder.createQuery(EmpleadosEntity.class);
            criteria.from(EmpleadosEntity.class);
            List<EmpleadosEntity> data = session.createQuery(criteria).getResultList();
            for (EmpleadosEntity datum : data) {
                if (datum.getEmpNo() >= numEmp)
                    numEmp = datum.getEmpNo();
            }
            numEmp += 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DepartamentosEntity dep;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DepartamentosEntity> criteria = builder.createQuery(DepartamentosEntity.class);
        criteria.from(DepartamentosEntity.class);
        List<DepartamentosEntity> data = session.createQuery(criteria).getResultList();
        dep = data.get(new Random().nextInt(data.size()));

        EmpleadosEntity emp = new EmpleadosEntity();
        emp.setEmpNo(numEmp);
        emp.setApellido("Apellido" + numEmp);
        emp.setOficio("Prueba");
        emp.setDir(01010);
        emp.setFechaAlt(new Date(System.currentTimeMillis()));
        emp.setSalario(1000.0);
        emp.setComision(20.0);
        emp.setDepartamentosByDeptNo(dep);

        Transaction tx = session.beginTransaction();
        try {
            session.save(emp);
            tx.commit();
            System.out.println("Empleado " + numEmp + " creado.");
        } catch (ConstraintViolationException | TransientPropertyValueException ignored) {
            System.out.println("Ya existe un departamento con la clave " + numEmp + " ó error al asignarle el departamento: " + dep.getDeptNo());
        } catch (HibernateError ignored) {
            System.out.println("Error al crear el empleado " + numEmp);
        }
        session.close();
    }

    public static void insertarNuevoDepartamento() {
        int numDep = 0;

        Session session = sessionFactory.openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DepartamentosEntity> criteria = builder.createQuery(DepartamentosEntity.class);
            criteria.from(DepartamentosEntity.class);
            List<DepartamentosEntity> data = session.createQuery(criteria).getResultList();
            for (DepartamentosEntity datum : data) {
                if (datum.getDeptNo() >= numDep)
                    numDep = datum.getDeptNo();
            }
            numDep += 1;
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
        }

        DepartamentosEntity dep = new DepartamentosEntity();
        dep.setDeptNo(numDep);
        dep.setDnombre("Departamento" + numDep);
        dep.setLoc("Vitoria");
        Transaction tx = session.beginTransaction();
        try {
            session.save(dep);
            tx.commit();
            System.out.println("Departamento " + numDep + " creado.");
        } catch (ConstraintViolationException ignored) {
            System.out.println("Ya existe un departamento con la clave " + numDep);
        } catch (HibernateError ignored) {
            System.out.println("Error al crear el departamento " + numDep);
        }
        session.close();
    }

    public static void mostrarEmpleados(DepartamentosEntity dep) {
        Session session = sessionFactory.openSession();
        Set<EmpleadosEntity> empleados;

        try {
            empleados = (Set<EmpleadosEntity>) dep.getEmpleadosByDeptNo();
            Iterator<EmpleadosEntity> it = empleados.iterator();
            if (it.hasNext())
                System.out.println("Lista de empleados:");
            while (it.hasNext()) {
                EmpleadosEntity emp = it.next();
                System.out.format("\t%3s | %15s | %.2f\n", emp.getEmpNo(), emp.getApellido(), emp.getComision());
            }
            System.out.println();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
        }

        session.close();
    }

    public static void mostrarDepartamentos() {
        Session session = sessionFactory.openSession();
        DepartamentosEntity departamento;

        try {
            for (int i = 0; i < 4; i++) {
                try {
                    departamento = session.load(DepartamentosEntity.class, (i + 1));
                    System.out.format("Departamento: %s, Localización: %s, Nombre: %s\n", String.valueOf(departamento.getDeptNo()), departamento.getLoc(), departamento.getDnombre());
                    mostrarEmpleados(departamento);
                } catch (ObjectNotFoundException | NullPointerException ex) {
                    System.out.println("Error: " + ex.getLocalizedMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
        }

        session.close();
    }
}
