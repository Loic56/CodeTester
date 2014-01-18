/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.io.Serializable;
import java.util.List;
import jpa.Jointure;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Loïc
 */
public class JointureDao implements IJointureDao, Serializable {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Jointure create(Jointure jointure) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();;
            session.saveOrUpdate(jointure);
            session.flush();
            int id = jointure.getJointureid();
            transaction.commit();
            List<Jointure> list = session.createQuery("from Jointure where jointureid = " + id).list(); // pas de commit -> fait planter !
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Jointure create => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Jointure> findAll() {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            final Criteria crit = session.createCriteria(Jointure.class);
            transaction.commit();
            return crit.list();
        } catch (Exception e) {
            new PamException("Jointure findAll => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    
    
    
    @Override
    public void destroy(Jointure jointure) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            Criteria criteria = session.createCriteria(Jointure.class).add(Restrictions.eq("id", jointure.getJointureid()));
            List results = criteria.list();
            Jointure join = (Jointure) results.get(0);
            session.delete(join);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // new PamException("Candidat destroy => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
    }

}
