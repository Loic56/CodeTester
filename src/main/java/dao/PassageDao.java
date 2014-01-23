/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;
import jpa.Candidat;
import jpa.Passage;
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
@Transactional
public class PassageDao implements IPassageDao, Serializable {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Passage create(Passage passage) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            session.saveOrUpdate(passage);
            session.flush();
            int id = passage.getPassageid();
            transaction.commit();
            List<Passage> list = session.createQuery("from Passage where passageid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            //new PamException("Passage create => pamException", 0);
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Passage find(Long id) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Passage> list = session.createQuery("from Passage where passageid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Passage find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Passage update(Passage passage) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            session.update(passage);
            session.flush();
            // id est généré par l'insertion en base !
            int id = passage.getPassageid();
            List<Passage> list = session.createQuery("from Passage where passageid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Passage update -> Passage find by id => pamException", 0);
        } finally {
            System.out.println("session close ");
            getSessionFactory().close();
        }
        return null;
    }

    // supprimer la jointure foreign key
    @Override
    public void destroy(Passage passage) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            Criteria criteria = session.createCriteria(Passage.class).add(Restrictions.eq("id", passage.getPassageid()));
            List results = criteria.list();
            Passage pass = (Passage) results.get(0);
            session.delete(pass);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // new PamException("Passage destroy => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public List<Passage> find(String format) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Passage> find(String format, String theme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Passage> findAll() {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            final Criteria crit = session.createCriteria(Passage.class);
            transaction.commit();
            return crit.list();
        } catch (Exception e) {
            new PamException("Passage findAll => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Passage find(Candidat candidat) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Passage> list = session.createQuery("select p from Passage p where p.candidatid =:candidat and p.passageEtat = 0").setParameter("candidat", candidat).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            //e.printStackTrace();
            new PamException("Rubrique find by testid => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

}
