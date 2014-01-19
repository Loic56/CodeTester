/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.util.List;
import jpa.Proposition;
import jpa.Question;
import jpa.Test;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Loïc
 */
public class PropositionDao implements IPropositionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Proposition create(Proposition proposition) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            session.saveOrUpdate(proposition);
            transaction.commit();
            // id est généré par l'insertion en base !
            int id = proposition.getPropositionid();
            session.flush();
            List<Proposition> list = session.createQuery("from Proposition where propositionid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            // new PamException("Proposition create => pamException", 0);
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Proposition edit(Proposition proposition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(Proposition proposition) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            Criteria criteria = session.createCriteria(Proposition.class).add(Restrictions.eq("id", proposition.getPropositionid()));
            List results = criteria.list();
            Proposition prop = (Proposition) results.get(0);
            session.delete(prop);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // new PamException("Candidat destroy => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public Proposition find(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Proposition> findAll() {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            final Criteria crit = session.createCriteria(Proposition.class);
            transaction.commit();
            return crit.list();
        } catch (Exception e) {
            new PamException("Proposition findAll => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Proposition> find(Test test) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Proposition> list = session.createQuery("select p from Proposition  p where p.questionid.rubriqueid.testid =:test ").setParameter("test", test).list();
            transaction.commit();;
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            new PamException("Proposition find by testid => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Proposition> find(Question question) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Proposition> list = session.createQuery("select p from Proposition p where p.questionid =:question ").setParameter("question", question).list();
            transaction.commit();
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            // e.printStackTrace();
            new PamException("Proposition find by question => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Proposition find_(Question question) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Proposition> list = session.createQuery("select p from Proposition p where p.questionid =:question and p.propositionvrai = 1").setParameter("question", question).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            // e.printStackTrace();
            new PamException("Proposition find_the_one by question => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

}
