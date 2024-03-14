package com.mhiring.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Application;
import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;

@Component("ApplicantDAO")
public class ApplicantDAO extends DAO<Applicant>{
	
	@Override
    public Applicant saveUser(Applicant applicant) {
        try {
        		beginTxn();
        		getSession().persist(applicant);
        		commitTxn();
        		closeSession();
        		return applicant;
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
        return null;
    }

	@Override
	public Applicant getUser(String param) {
		try {
			beginTxn();
			long paramId = Long.parseLong(param);
			Query query = getSession().createQuery("from Applicant where applicantID = :param");
			query.setParameter("param", paramId);
			Applicant appObj = new Applicant();
			appObj = (Applicant) query.uniqueResult();
			commitTxn();
			closeSession();
			return appObj;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public Applicant getApplicantObj(User userObj) {		
		try {
				beginTxn();
				Query query = getSession().createQuery("from Applicant where user = :userObj");
				query.setParameter("userObj", userObj);
				Applicant appcnt = new Applicant();
				appcnt = (Applicant) query.uniqueResult();
				commitTxn();
				closeSession();
				return appcnt;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public Applicant updateApplicant(Applicant appObj) {
		
		try {
			beginTxn();
			appObj = getSession().merge(appObj);
			commitTxn();	
			closeSession();
			return appObj;
		}catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public Boolean deleteApplicant(Applicant appObj) {
		try {
				beginTxn();
				getSession().remove(appObj);
				commitTxn();
				closeSession();
				return true;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return false;
	}
}
