package com.mhiring.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhiring.dao.ApplicantDAO;
import com.mhiring.dao.ApplicationDAO;
import com.mhiring.dao.DAO;
import com.mhiring.dao.JobDAO;
import com.mhiring.dao.RecruiterDAO;
import com.mhiring.dao.UserDAO;
import com.mhiring.pojo.Application;
import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;

@Service
public class RecruiterService {
		
	private RecruiterDAO recruiterDao;
	
	private JobDAO jobDAO;
	
	private ApplicationDAO appDao;
	
	public RecruiterService() {}
	
	@Autowired
	public RecruiterService(RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		this.recruiterDao = recruiterDao;
		this.jobDAO = jobDAO;
		this.appDao = appDao;
	}

	public Job saveJobPosting(Job jobObj) throws Exception{
		 jobObj = jobDAO.saveUser(jobObj);
		 return jobObj;
	}
	
	public List<Job> getAllJobs() throws Exception{
		List<Job> jobsList = new ArrayList<Job>();
		jobsList= jobDAO.getAllJobs();
		return jobsList;
	}
	
	public List<Job> getCreatedJobs(Recruiter recObj) throws Exception{
		recObj = recruiterDao.getUser(Long.toString(recObj.getRecruiterID()));
		List<Job> listOfJobs = new ArrayList<Job>();
		listOfJobs = recObj.getJobsAdded();
		return listOfJobs;
	}
	
	public Boolean deleteJob(long jobId) throws Exception{
		int affectedEnteries = jobDAO.deleteJob(jobId);
		if(affectedEnteries != 0) {
			return true;
		}	
		return false;
	}
	
	public Job getJobDetails(long jobId) throws Exception{
		Job jobObj = new Job();
		jobObj = jobDAO.getUser(Long.toString(jobId));
		
		if(jobObj != null) {
			return jobObj;
		}
		
		return null;
	}
	
	public Job updateJobDetails(Job jobObj) throws Exception{
		jobObj = jobDAO.updateJob(jobObj);
		
		if(jobObj != null) {
			return jobObj;
		}
		
		return null;			
	}
	
	public Recruiter getRecruiterObj(Recruiter obj) {
		obj = recruiterDao.getUser(Long.toString(obj.getRecruiterID()));
		return obj;	
	}
	
	public Recruiter updateRecProfile(Recruiter Obj) throws Exception{
		RecruiterDAO recDAO = new RecruiterDAO();
		Obj = recDAO.updateRecruiter(Obj);
		return Obj;
	}
	
	public void updateAppStatusOnExpire(Job job) throws Exception{
		
		List<Application> applications = job.getApplications();
		
		for(Application each:applications) {
			if(each.getAppStatus().equals("In Review")) {
				each.setAppStatus("Reject");
			}
		}
		appDao.updateAppStatus(applications);		
	}
	
	public void updateAppStatusOnAvailable(Job job) throws Exception{
		List<Application> applications = job.getApplications();
		for(Application each:applications) {
			if(each.getAppStatus().equals("Reject")) {
				each.setAppStatus("Reject - Job Expired");
			}
		}	
		appDao.updateAppStatus(applications);
	}
	
	
	
	public Application getApplicationDetails(long appId) throws Exception{
		Application appObj = appDao.getUser(Long.toString(appId));
		return appObj;
	}
	
	public Application updateApplicationStatus(Application appObj) throws Exception{
		appObj = appDao.updateApplicationStatus(appObj);
		return appObj;
	}
}
