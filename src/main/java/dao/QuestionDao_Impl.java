/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;

import java.io.Serializable;
import java.util.List;

import jpa.Question;
import jpa.Rubrique;
import jpa.Test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Loïc
 */
public class QuestionDao_Impl  extends Abstract_GenericDao implements IQuestionDao, Serializable {
	
	public QuestionDao_Impl(){
		super();
	}
	
    @Override
    public Question create(Question question) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            session.saveOrUpdate(question);
            session.flush();
            int id = question.getQuestionid();
            transaction.commit();
            List<Question> list = session.createQuery("from Question where questionid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            new PamException("Question create => pamException", 0);
        } finally {
            getSessionFactory().close();
            
        }
        return null;
    }

    @Override
    public Question edit(Question question) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.merge(question);
            transaction.commit();
            int id = question.getQuestionid();
            session.flush();
            List<Question> list = session.createQuery("from Question where questionid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
//new PamException("Reponse create => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public void destroy(Question question) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Question find(Long id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Question> list = session.createQuery("from Question where questionid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Question find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Question> find(Rubrique rubrique) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            // List<Rubrique> list = session.createQuery("select r from Rubrique  r where r.testid.testid = " + test.getTestid()).list();
            List<Question> list = session.createQuery("select q from Question q where q.rubriqueid =:rubrique ").setParameter("rubrique", rubrique).list();
            transaction.commit();;
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            //e.printStackTrace();
            new PamException("Question find by rubriqueid => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Question> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> findByTest(Test test) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Question> list = session.createQuery("select q from Question q where q.rubriqueid.testid =:test ").setParameter("test", test).list();
            transaction.commit();;
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            new PamException("Question find by Test rubriqueid => pamException", 0);
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
        return null;

    }

}
