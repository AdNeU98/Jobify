package com.mhiring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component("JobDAO")
public class JobDAO extends DAO<Job>{

	@Override
	public Job saveUser(Job obj) {
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
	
	public List<Job> getAllJobs(){
		try {
				beginTxn();
				Query query = getSession().createQuery("from Job");
				List<Job> allJobs = new ArrayList<Job>();
				allJobs = (List<Job>) query.list();
				commitTxn();
				closeSession();
				return allJobs;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public List<Job> getJobsByQuery(String skills, String role, String location){
		try {
				beginTxn();
				CriteriaBuilder builder = getSession().getCriteriaBuilder();
				CriteriaQuery<Job> query = builder.createQuery(Job.class);
				Root<Job> root = query.from(Job.class);
				Predicate rolePredicate = null;
				Predicate skillsPredicate = null;
				Predicate locationPredicate = null;
				
				if(!skills.equals("null")) {
					skillsPredicate = builder.like(root.get("descrp"), "%" + skills + "%");
				}
				else {
					skillsPredicate = builder.conjunction();
				}
				
				if(!role.equals("null")) {
					rolePredicate = builder.like(root.get("roleName"), "%" + role + "%");
				}
				else {
					rolePredicate = builder.conjunction();
				}
				
				if(!location.equals("null")) {
					 locationPredicate = builder.like(root.get("location"), "%" + location + "%");
				}
				else {
					locationPredicate = builder.conjunction();
				}
				
				query.where(builder.and(rolePredicate, skillsPredicate, locationPredicate));
				List<Job> result = getSession().createQuery(query).getResultList();
				commitTxn();
				closeSession();
				return result;
		}catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public int deleteJob(long jobId) {
		try {
				beginTxn();
				Query query = getSession().createQuery("delete from Job where jobID = :jobId");
				query.setParameter("jobId", jobId);
				int val = query.executeUpdate();
				commitTxn();
				closeSession();
				return val;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return 0;
	}

	@Override
	public Job getUser(String param) {
		try {
			
			beginTxn();
			long paramId = Long.parseLong(param);
			Query query = getSession().createQuery("from Job where jobID = :param");
			query.setParameter("param", param);
			Job jobObj = new Job();
			jobObj = (Job) query.uniqueResult();
//			getSession().evict(jobObj);
			commitTxn();
			closeSession();
			return jobObj;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	public Job updateJob(Job jobObj) {
		
		try {
			beginTxn();
			jobObj = getSession().merge(jobObj);
			commitTxn();	
			closeSession();
			return jobObj;
		}catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	

}
