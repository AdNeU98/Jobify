package com.mhiring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Application;
import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;

@Component("ApplicationDAO")
public class ApplicationDAO extends DAO<Application>{

	@Override
	public Application saveUser(Application obj) {
		try {
        	beginTxn();
        	getSession().persist(obj);
        	commitTxn();
        	closeSession();
        	return obj;
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }	
	
	return null;
	}

	@Override
	public Application getUser(String param) {
		try {
			beginTxn();
			long paramId = Long.parseLong(param);
			Query query = getSession().createQuery("from Application where applicationID = :param");
			query.setParameter("param", paramId);
			Application appObj = new Application();
			appObj = (Application) query.uniqueResult();
			commitTxn();
			closeSession();
			return appObj;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public List<Application> getUserApplications (Applicant app){
		try {
				beginTxn();
				Query query = getSession().createQuery("from Application where appDetails = :param");
				query.setParameter("param", app);
				List<Application> applications = new ArrayList<Application>();
				applications = (List<Application>) query.list();
				commitTxn();
				closeSession();
				return applications;
		} catch(HibernateException e) {
			rollback();
			System.out.println("Exception: " + e.getMessage());
		}
		return null;
	}
	
	public void deleteApplication(Application app) {
		try {
				beginTxn();
				getSession().remove(app);
				commitTxn();
				closeSession();
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
	}
	
	public void updateAppStatus(List<Application> application) {
		
		try {
			beginTxn();
			for(Application each: application) {	
				getSession().merge(each);
			}			
			commitTxn();	
			closeSession();
			
		}catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }		
	}
	
	public Application updateApplicationStatus(Application application) {
		
		try {
			beginTxn();
			application = getSession().merge(application);		
			commitTxn();	
			closeSession();
			return application;
		}catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
}

