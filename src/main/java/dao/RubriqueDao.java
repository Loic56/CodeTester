/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.io.Serializable;
import java.util.List;
import jpa.Rubrique;
import jpa.Test;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Loïc
 */
public class RubriqueDao implements IRubriqueDao, Serializable {

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
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            session.saveOrUpdate(rubrique);
            session.flush();
            int id = rubrique.getRubriqueid();
            transaction.commit();
            List<Rubrique> list = session.createQuery("from Rubrique where rubriqueid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
 //           new PamException("Rubrique create => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Rubrique edit(Rubrique rubrique) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.merge(rubrique);
            transaction.commit();
            // id est généré par l'insertion en base !
            int id = rubrique.getRubriqueid();
            session.flush();
            List<Rubrique> list = session.createQuery("from Rubrique where rubriqueid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            // transaction.rollback();
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
        return null;
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
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Rubrique> list = session.createQuery("select r from Rubrique r where r.testid =:test ").setParameter("test", test).list();
            transaction.commit();
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
