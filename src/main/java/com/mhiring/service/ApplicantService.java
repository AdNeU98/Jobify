package com.mhiring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhiring.dao.ApplicantDAO;
import com.mhiring.dao.ApplicationDAO;
import com.mhiring.dao.JobDAO;
import com.mhiring.dao.RecruiterDAO;
import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Application;
import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;

import jakarta.transaction.Transactional;

@Service
public class ApplicantService {
	
	public ApplicantService() {}
	
	private ApplicantDAO appDao;
	
	private JobDAO jobDAO;
	
	private ApplicationDAO applicationDao;
	
	@Autowired
	public ApplicantService(ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao) {
		this.appDao = appDao;
		this.jobDAO = jobDAO;
		this.applicationDao = applicationDao;
	}

	public Applicant updateAppProfile(Applicant Obj) throws Exception{
		
		Obj = appDao.updateApplicant(Obj);
		return Obj;
	}
	
	public List<Job> getNonAppliedJobs(Applicant applicant, String roleName, String location, String skills) throws Exception {	
		
		List<Job> jobsList = new ArrayList<Job>();
		if(location == (null) && roleName == (null) && skills == (null)) {
			jobsList= jobDAO.getAllJobs();
		}
		else {
			jobsList = jobDAO.getJobsByQuery(skills, roleName, location);
		}
		

		List<Application> applicantApps = applicant.getApplications();
		HashMap<Long, String> mpAppliedJobs = new HashMap<Long, String>();
		List<Job> nonAppliedJobs = new ArrayList<Job>();
		
		
		for (Application each : applicantApps) {
			mpAppliedJobs.put(each.getJobDetails().getJobID(), each.getAppStatus());
		}
				
		for(Job each : jobsList) {
			if(!mpAppliedJobs.containsKey(each.getJobID())) {
				nonAppliedJobs.add(each);
			}else {				
				String app_status = mpAppliedJobs.get(each.getJobID());
				if(app_status.equals("Reject - Job Expired")) {
					nonAppliedJobs.add(each);
				}
			}
			
		}
		
		return nonAppliedJobs;
	}
	
	public Job getJobDetails(long jobId) throws Exception{
		Job jobObj = new Job();
		jobObj = jobDAO.getUser(Long.toString(jobId));
		
		if(jobObj != null) {
			return jobObj;
		}
		
		return null;
	}
	
	public Boolean saveApplication(Job job, Applicant app) throws Exception{
		
		List<Application> appliedJobs = getListOfApplications(app);
		
		for(Application each : appliedJobs) {
			if(each.getJobDetails().getJobID() == job.getJobID() &&
					!(each.getAppStatus().equals("Reject - Job Expired"))) {
				return false;
			}
		}
		
		Application appObj = new Application();
		appObj.setJobDetails(job);
		appObj.setAppDetails(app);
		appObj.setAppStatus("In Review");
		
		appObj = applicationDao.saveUser(appObj);
		
		if(appObj!= null && appObj.getApplicationID() != 0) {
			return true;
		}
		
		return false;
	}
	
	public List<Application> getListOfApplications(Applicant app) throws Exception{
		
		if(app!= null) {
			List<Application> lsApps = applicationDao.getUserApplications(app);
			return lsApps;
		}
		return null;
		
	}
	
	public Applicant refreshSessionObj(Applicant obj) {
		obj = appDao.getUser(Long.toString(obj.getApplicantID()));
		return obj;
	}
	
	public void deleteApplication(long appID) throws Exception{		
		Application appObj = applicationDao.getUser(Long.toString(appID));
		applicationDao.deleteApplication(appObj);
	}
}
