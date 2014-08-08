package dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Abstract_GenericDao implements IDAO {

    @Autowired
	protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public String getConnexionCount(){
    	return new String ("Nombre de connexions ouvertes : " + getSessionFactory().getStatistics().getConnectCount());
    }
    
    
}
