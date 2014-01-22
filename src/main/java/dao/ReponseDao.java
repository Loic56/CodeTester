/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exception.PamException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import jpa.Passage;
import jpa.Question;
import jpa.Reponse;
import jpa.Test;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.Type;
import org.hibernate.util.IdentitySet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Loïc
 */
public class ReponseDao implements IReponseDao, Serializable  {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Reponse create(Reponse reponse) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.saveOrUpdate(reponse);
            transaction.commit();
            // id est généré par l'insertion en base !
            int id = reponse.getReponseid();
            session.flush();
            List<Reponse> list = session.createQuery("from Reponse where reponseid = " + id).list();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
//new PamException("Reponse create => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Reponse edit(Reponse reponse) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.merge(reponse);
            transaction.commit();
            // id est généré par l'insertion en base !
            int id = reponse.getReponseid();
            session.flush();
            List<Reponse> list = session.createQuery("from Reponse where reponseid = " + id).list();
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
    public void destroy(Reponse reponse) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            Criteria criteria = session.createCriteria(Reponse.class).add(Restrictions.eq("id", reponse.getReponseid()));
            List results = criteria.list();
            Reponse rep = (Reponse) results.get(0);
            session.delete(rep);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // new PamException("Reponse destroy => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public Reponse find(Long id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Reponse> list = session.createQuery("from Reponse where reponseid = " + id).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            new PamException("Reponse find by id => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Reponse> findAll() {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            final Criteria crit = session.createCriteria(Reponse.class);
            transaction.commit();
            return crit.list();
        } catch (Exception e) {
            new PamException("Reponse findAll => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Reponse> find(Passage passage) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Reponse> list = session.createQuery("select r from Reponse r where r.passageid =:passage ").setParameter("passage", passage).list();
            transaction.commit();
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            //e.printStackTrace();
            new PamException("Reponse find by passage => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public Reponse find(Question question) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();
            List<Reponse> list = session.createQuery("select r from Reponse r where r.questionid =:question ").setParameter("question", question).list();
            transaction.commit();
            return (list.isEmpty() ? null : list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            //new PamException("Reponse find by proposition => pamException", 0);
        } finally {
            getSessionFactory().close();
        }
        return null;
    }

    @Override
    public List<Reponse> find(Passage passage, Test test) {
        try {
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.begin();//
            List<Reponse> list = session.createQuery("select r from Reponse r where r.questionid.rubriqueid.testid =:test and r.passageid =:passage ").setParameter("test", test).setParameter("passage", passage).list();
            transaction.commit();
            return (list.isEmpty() ? null : list);
        } catch (Exception e) {
            e.printStackTrace();
            //new PamException("Reponse find by passage and test => pamException", 0);
        } finally {
            getSessionFactory().close();
        }

        return null;
    }

    /**
     * Detach an entity from the session as it would be if the session was
     * closed.
     *
     * @param entity the hibernate entity.
     */
    public void detach(Object entity) {

        // Check for lazy-loading proxies
        if (entity instanceof HibernateProxy) {
            SessionImplementor sessionImplementor = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer().getSession();
            if (sessionImplementor instanceof Session) {
                ((Session) sessionImplementor).evict(entity);
            }

            return;
        }

        // processing queue
        Queue<Object> entities = new LinkedList<Object>();
        Set<Object> processedEntities = new IdentitySet();

        entities.add(entity);

        while ((entity = entities.poll()) != null) {

            // Skip already processed entities
            if (processedEntities.contains(entity)) {
                continue;
            }

            ClassMetadata classMetadata = getSessionFactory()
                    .getAllClassMetadata().get(entity.getClass().getName());
            String[] propertyNames = classMetadata.getPropertyNames();
            Session session = null;

            // Iterate through all persistent properties
            for (String propertyName : propertyNames) {
                Object propertyValue = classMetadata.getPropertyValue(entity,
                        propertyName, EntityMode.POJO);
                Type propertyType = classMetadata.getPropertyType(propertyName);
                // Handle entity types
                if (propertyType.isEntityType()) {
                    // Detach proxies
                    if (propertyValue instanceof HibernateProxy) {
                        SessionImplementor sessionImplementor = ((HibernateProxy) propertyValue)
                                .getHibernateLazyInitializer().getSession();
                        if (sessionImplementor instanceof Session) {
                            ((Session) sessionImplementor).evict(propertyValue);

                            // If we don't yet have a session for this entity save it for later use.
                            if (session == null) {
                                session = (Session) sessionImplementor;
                            }
                        }

                    } else {
                        // Add entities to the processing queue.
                        entities.add(propertyValue);
                    }

                } // Handle collection types
                else if (propertyType.isCollectionType()) {

                    if (propertyValue instanceof AbstractPersistentCollection) {
                        SessionImplementor sessionImplementor
                                = ((AbstractPersistentCollection) propertyValue).getSession();
                        // If we don't yet have a session for this entity save it for later use.
                        if (sessionImplementor instanceof Session && session == null) {
                            session = (Session) sessionImplementor;
                        }
                    }
                }
            }
            // Evict the entity and associated collections.
            if (session != null) {
                session.evict(entity);
            }
            processedEntities.add(entity);
        }
    }
}
