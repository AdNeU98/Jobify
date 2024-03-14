package com.mhiring.dao;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.cfg.Configuration;
import java.util.logging.Level;


public abstract class DAO<T> {

	private static final Logger logger = Logger.getAnonymousLogger();
	private static final ThreadLocal sessionThreadObj = new ThreadLocal();
	private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	public DAO() {
		
	}
	
    public static Session getSession() {
        Session sessionObj = (Session) DAO.sessionThreadObj.get();

        if (sessionObj == null) {
        	sessionObj = sessionFactory.openSession();
            DAO.sessionThreadObj.set(sessionObj);
        }
        
        return sessionObj;
    }
    
    public void beginTxn() {
        getSession().beginTransaction();
    }

    public void commitTxn() {
        getSession().getTransaction().commit();
    }
    
    protected void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            logger.log(Level.WARNING, "Cannot rollback", e);
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
        	logger.log(Level.WARNING, "Cannot close", e);
        }
        DAO.sessionThreadObj.set(null);
    }
    
    public static void closeSession() {
        getSession().close();
        DAO.sessionThreadObj.set(null);
    }
    
    public abstract T saveUser(T obj);
    public abstract T getUser(String param);
}
