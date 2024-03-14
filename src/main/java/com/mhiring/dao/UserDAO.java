package com.mhiring.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;

@Component("UserDAO")
public class UserDAO extends DAO<User>{
	@Override
	public User saveUser(User obj) {
		// TODO Auto-generated method stub
		try {
	        	beginTxn();
	        	getSession().persist(obj);
	        	commitTxn();
	        	closeSession();
	        	return obj;
	        } catch(HibernateException e) {
	        	rollback();
	            System.out.println("Exception: " + e.getMessage());
	        }
		
		return null;
	}

	@Override
	public User getUser(String email) {
		
		try {
			beginTxn();
			Query query = getSession().createQuery("from User where email = :email");
			query.setParameter("email", email);
			User user = new User();
			user = (User) query.uniqueResult();
			commitTxn();
			closeSession();
			return user;
		} catch(HibernateException e) {
        	rollback();
            System.out.println("Exception: " + e.getMessage());
        }
		
		return null;
	}
	
	
}
