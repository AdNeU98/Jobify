package com.mhiring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mhiring.dao.ApplicantDAO;
import com.mhiring.dao.DAO;
import com.mhiring.dao.RecruiterDAO;
import com.mhiring.dao.UserDAO;
import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;

@Service
public class UserService {
	
	private UserDAO userDao;
	
	private ApplicantDAO applicantDao;
	
	private RecruiterDAO recruiterDao;
	
	public UserService() {}
	
	@Autowired
	public UserService(UserDAO userDao, ApplicantDAO applicantDao, RecruiterDAO recruiterDao) {
		this.userDao = userDao;
		this.applicantDao = applicantDao;
		this.recruiterDao = recruiterDao;
	}
	
		
	public void userRegistration(User userObj) {
		
		if(userObj.getUserType().equals("Applicant")) {		
			Applicant appObj = new Applicant();
			appObj.setUser(userObj);
			applicantDao.saveUser(appObj);
		}
		else if(userObj.getUserType().equals("Recruiter")) {
			Recruiter recruitObj = new Recruiter();
			recruitObj.setUser(userObj);
			recruiterDao.saveUser(recruitObj);	
		}
	}
	
	public Boolean registrationCriteria(User userObj) {	
		userObj = userDao.getUser(userObj.getEmail());	
		if(userObj == null) {
			return true;
		}
		return false;
	}
	
	public Boolean loginCriteria(String email, String password, String roleType) {
		User userObj = userDao.getUser(email);
		if(userObj == null) {
			return false;
		}
		else if(userObj.getPassword().equals(password) && userObj.getUserType().equals(roleType)) {
			return true;
		}
		
		return false;
	}
	
	public Recruiter getRecruiterUserDetails(String email) {
		User userObj = userDao.getUser(email);
		Recruiter recObj = new Recruiter ();
		recObj = recruiterDao.getRecruiterObj(userObj);		
		return recObj;
	}
	
	public Applicant getApplicantUserDetails(String email) {
		User userObj = userDao.getUser(email);
		Applicant appObj = new Applicant();
		appObj = applicantDao.getApplicantObj(userObj);
		return appObj;
	}
	
	public Boolean deleteApplicant(Applicant appObj) {	
		Boolean isDeleted = applicantDao.deleteApplicant(appObj);
		return isDeleted;
	}
}
