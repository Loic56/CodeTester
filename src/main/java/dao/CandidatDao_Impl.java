/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jpa.Candidat;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tools.Utils;

/**
 *
 * @author Loïc
 */
public class CandidatDao_Impl extends Abstract_GenericDao implements ICandidatDao, Serializable {
	
	public CandidatDao_Impl(){
		super();
	}
	
	
    @Override
    public Candidat create(Candidat candidat) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            session.saveOrUpdate(candidat);
            session.flush();
            // id est généré par l'insertion en base !
            int id = candidat.getCandidatid();
            transaction.commit();
            List<Candidat> list = session.createQuery("from Candidat where candidatid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Candidat create => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Candidat edit(Candidat candidat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(Candidat candidat) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            Criteria criteria = session.createCriteria(Candidat.class).add(Restrictions.eq("id", candidat.getCandidatid()));
            List results = criteria.list();
            Candidat cand = (Candidat) results.get(0);
            session.delete(cand);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // new PamException("Candidat destroy => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public Candidat find(Long id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Candidat> list = session.createQuery("from Candidat where candidatid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Candidat find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Candidat find(String login, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Candidat> findAll() {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            final Criteria crit = session.createCriteria(Candidat.class);
            transaction.commit();
            return crit.list();
        } catch (Exception e) {
            new PamException("Candidat findAll => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Candidat find(String nom, String prenom, Date date) {
        // Date theDate = utils.stringToMySQLDate(date);
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Candidat> list = session.createQuery("select c from Candidat c where c.candidatNom=:nom and c.candidatPrenom=:prenom and c.candidatDateNaissance=:date").setParameter("nom", nom).setParameter("prenom", prenom).setParameter("date", date).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
           // new PamException("Candidat find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

}
