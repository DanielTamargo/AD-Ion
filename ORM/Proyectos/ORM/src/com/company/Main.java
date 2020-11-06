package com.company;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class Main {

    static SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();

    public static void main(String[] args) {

        //SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            DepartamentosEntity departamento = session.load(DepartamentosEntity.class, (byte) 20);
            System.out.format("%3s, %-10s, %-10s", String.valueOf(departamento.getDeptNo()), departamento.getLoc(), departamento.getDnombre());
        } catch (Exception ignored) {
            System.out.println("Error");
        }

        session.close();


    }
}
