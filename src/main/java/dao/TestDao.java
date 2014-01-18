/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.util.List;
import javax.transaction.Transactional;
import jpa.Test;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;



/**
 *
 * @author Loïc
 */
@Transactional
public class TestDao implements ITestDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // CA MARCHE PAS CA !
    @Override
    public List<Test> find(String format) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Test> list = session.createQuery("from Test where testformat like " + format).list();
            transaction.commit();
            return (List<Test>) (list.isEmpty() ? null : list.get(0));

        } catch (Exception e) {
            new PamException("Test find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Test> findAll() {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            final Criteria crit = session.createCriteria(Test.class);
            transaction.commit();
            return crit.list();
        } catch (Exception e) {
            new PamException("Test findAll => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Test> find(String format, String theme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Test create(Test test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Test edit(Test test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(Test test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Test find(Long id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Test> list = session.createQuery("from Test where testid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException(" Test find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Test update(Test test) {
        try {
            System.out.println("1");
            Session session = sessionFactory.openSession();
            System.out.println("2");
            Transaction transaction = session.beginTransaction();
            System.out.println("3");
            transaction.begin();
            System.out.println("4");
            
            session.update(test);
            System.out.println("5");
            session.flush();
            // id est généré par l'insertion en base !
            int id = test.getTestid();
            System.out.println("6");
            session.flush();
            List<Test> list = session.createQuery("from Test where testid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
        return null;
    }
}
