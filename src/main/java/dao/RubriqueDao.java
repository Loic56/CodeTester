/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.util.List;
import jpa.Rubrique;
import jpa.Test;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Loïc
 */
public class RubriqueDao implements IRubriqueDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Rubrique create(Rubrique rubrique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rubrique edit(Rubrique rubrique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(Rubrique rubrique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rubrique find(Long id) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Rubrique> list = session.createQuery("from Rubrique where rubriqueid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Rubrique find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Rubrique> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rubrique> find(Test test) {
        try {
            System.out.println("1");
            org.hibernate.Session session = sessionFactory.openSession();
            System.out.println("2");
            Transaction transaction = session.beginTransaction();
            System.out.println("3");
            transaction.begin();
            System.out.println("4");
            List<Rubrique> list = session.createQuery("select r from Rubrique r where r.testid =:test ").setParameter("test", test).list();
            System.out.println("5");
            transaction.commit();
            System.out.println("6");
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            //e.printStackTrace();
            new PamException("Rubrique find by testid => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

}
