package com.mhiring.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;

@Component("RecruiterDAO")
public class RecruiterDAO extends DAO<Recruiter> {

	@Override
	public Recruiter saveUser(Recruiter obj) {
		// TODO Auto-generated method stub
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

	
	public Recruiter getRecruiterObj(User userObj) {		
		try {
				beginTxn();
				Query query = getSession().createQuery("from Recruiter where user = :userObj");
				query.setParameter("userObj", userObj);
				Recruiter rctr = new Recruiter();
				rctr = (Recruiter) query.uniqueResult();
				commitTxn();	
				closeSession();
				return rctr;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}


	@Override
	public Recruiter getUser(String param) {
		try {
			beginTxn();
			long paramId = Long.parseLong(param);
			Query query = getSession().createQuery("from Recruiter where recruiterID = :param");
			query.setParameter("param", paramId);
			Recruiter recObj = new Recruiter();
			recObj = (Recruiter) query.uniqueResult();
			commitTxn();
			closeSession();
			return recObj;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public Recruiter updateRecruiter(Recruiter recObj) {
		
		try {
			beginTxn();
			recObj = getSession().merge(recObj);
			commitTxn();	
			closeSession();
			return recObj;
		}catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	
}
