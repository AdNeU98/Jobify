package com.mhiring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mhiring.dao.ApplicantDAO;
import com.mhiring.dao.ApplicationDAO;
import com.mhiring.dao.JobDAO;
import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Application;
import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;
import com.mhiring.service.ApplicantService;
import com.mhiring.service.RecruiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
	
	@GetMapping("/home")
	public ModelAndView homePage(HttpServletRequest request, 
			ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao) {
		
		var mav = new ModelAndView();
		Applicant userAppObj = new Applicant();	
		HttpSession httpSession = request.getSession(false);		
		 
		if(httpSession != null && (Applicant) httpSession.getAttribute("appObj")!= null) {
			userAppObj =(Applicant) httpSession.getAttribute("appObj");
			ApplicantService appServ = new ApplicantService(appDao, jobDAO, applicationDao);
			userAppObj = appServ.refreshSessionObj(userAppObj);
			httpSession.setAttribute("appObj", userAppObj);
			mav.setViewName("ApplicantHome");
		}
		else {
			mav.setViewName("AccessDenied");
		}
		
		return mav;
	}
	
	@GetMapping("/profile")
	public ModelAndView viewProfile(HttpServletRequest request) {
		
		var mav = new ModelAndView();
		if((Applicant) request.getSession().getAttribute("appObj")!= null) {
			Applicant app = new Applicant(); 
			app = (Applicant) request.getSession().getAttribute("appObj");
			
			if(app != null) {
				mav.setViewName("ApplicantProfile");
				return mav;
			}
			
			mav.setViewName("ErrorPage");
			return mav;		
		}

		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@PostMapping("/updateProfile")
	public ModelAndView updateProfile(@RequestParam("workEx") String wrkExp,
			@RequestParam("resume") String resume, HttpServletRequest request,
			ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao) {
		
		var mav = new ModelAndView();
		Applicant applicant = new Applicant();
		applicant = (Applicant) request.getSession().getAttribute("appObj");
		
		if(resume.length() > 200 || wrkExp.length() > 200) {
			mav.addObject("errorMessage", "Entries within 200 characters allowed only.");
			mav.setViewName("ApplicantProfile");
			return mav;
		}
		ApplicantService applicantService = new ApplicantService(appDao, jobDAO, applicationDao);	
		applicant.setResumeLink(resume);
		applicant.setWrkExp(wrkExp);
		
		try {
			applicant = applicantService.updateAppProfile(applicant);
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("ErrorPage");
			return mav;
		}
		
		HttpSession httpSession = request.getSession(false);	
		httpSession.setAttribute("appObj", applicant);
		mav.setViewName("ApplicantProfile");
		return mav;		
	}
	
	@GetMapping("/jobs")
	public ModelAndView viewJobs(HttpServletRequest request, 
			@RequestParam(name = "role", defaultValue = "null") String roleName,
			@RequestParam(name = "location", defaultValue = "null") String location,
			@RequestParam(name = "skills", defaultValue = "null") String skills,
			ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao){
		
		var mav = new ModelAndView();
		if((Applicant) request.getSession().getAttribute("appObj")!= null) {
			
			if(roleName.length() > 200 || location.length() > 200 || skills.length() > 200) {
				mav.addObject("errorMessage", "Entries should have characters less than 200");
				mav.setViewName("ApplyJobs");
				return mav;
			}
			
			ApplicantService applicantService = new ApplicantService(appDao, jobDAO, applicationDao);
			List<Job> jobsPosted = new ArrayList<Job>();
			Applicant applicant = new Applicant();
			applicant = (Applicant) request.getSession().getAttribute("appObj");
			applicant  = applicantService.refreshSessionObj(applicant);
			request.getSession().setAttribute("appObj", applicant);
			
			try {
				if(applicant != null) {
					jobsPosted = applicantService.getNonAppliedJobs(applicant, roleName, location, skills);		
					mav.addObject("listOfJobs", jobsPosted);
					mav.setViewName("ApplyJobs");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mav;			
		}
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@PostMapping("/jobs/{jobID}")
	public ModelAndView applyJob(@PathVariable("jobID") long jobID,
			HttpServletRequest request, 
			ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao) {
		
		var mav = new ModelAndView();
		if((Applicant) request.getSession().getAttribute("appObj")!= null) {
			Applicant applicant = new Applicant();
			ApplicantService applicantService = new ApplicantService(appDao, jobDAO, applicationDao);
			applicant = (Applicant) request.getSession().getAttribute("appObj");
			List<Job> lsOfJobsPosted = new ArrayList<Job>();
			
			Job jobObj = new Job();
			try {
				
				if(applicant.getResumeLink() == null || applicant.getWrkExp() == null ||
						applicant.getResumeLink().length()==0 || applicant.getWrkExp().length() == 0
			) {
					mav.addObject("errorMessage", "Please fill your entries in the profile page");
					mav.setViewName("ApplyJobs");
					lsOfJobsPosted = applicantService.getNonAppliedJobs(applicant, null, null, null);
					mav.addObject("listOfJobs", lsOfJobsPosted);
					return mav;
				}
					jobObj = applicantService.getJobDetails(jobID);
					if(jobObj != null) {
						Boolean isSaved = applicantService.saveApplication(jobObj, applicant);
						if(isSaved) {
							mav.addObject("successMessage", "Job applied successfully");
							mav.setViewName("Apply-success");
							return mav;
						}
						else {
							mav.addObject("errorMessage", "Failed to apply for the job / You've already applied for the job");
							mav.setViewName("ApplyJobs");
						}
						
						applicant  = applicantService.refreshSessionObj(applicant);
						request.getSession().setAttribute("appObj", applicant);
						lsOfJobsPosted = applicantService.getNonAppliedJobs(applicant, null, null, null);
						
				}
				else {
						mav.setViewName("ErrorPage");
						return mav;
				}	
											
			}catch (Exception e) {
				e.printStackTrace();
				mav.setViewName("ErrorPage");
				return mav;
			}
			
			mav.addObject("listOfJobs", null);
			mav.addObject("listOfJobs", lsOfJobsPosted);	
			return mav;			
		}
		
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/jobs/applied")
	public ModelAndView jobsApplied(HttpServletRequest request, 
			ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao) {
		var mav = new ModelAndView();
		if((Applicant) request.getSession().getAttribute("appObj")!= null) {
			ApplicantService applicantService = new ApplicantService(appDao, jobDAO, applicationDao);
			Applicant applicant = (Applicant) request.getSession().getAttribute("appObj");
			try {
				List<Application> listOfApplications = applicantService.getListOfApplications(applicant);
				if(listOfApplications != null) {
					mav.setViewName("UserApplications");
					mav.addObject("applications", listOfApplications);
					return mav;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mav;			
		}
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/jobs/delete/{appId}")
	public ModelAndView withdrawApplication(@PathVariable("appId") long appId, HttpServletRequest request,
			ApplicantDAO appDao, JobDAO jobDAO, ApplicationDAO applicationDao) {
		var mav = new ModelAndView();
		if((Applicant) request.getSession().getAttribute("appObj")!= null) {
			ApplicantService applicantService = new ApplicantService(appDao, jobDAO, applicationDao);
			try {
				applicantService.deleteApplication(appId);
				mav.setViewName("Job-Withdrawn");
			} catch (Exception e) {
				e.printStackTrace();
				mav.setViewName("ErrorPage");
			}
			
			return mav;
		}
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/logout")
	public ModelAndView userLogout(HttpServletRequest request) {
		var mav = new ModelAndView();
		if((Applicant) request.getSession().getAttribute("appObj")!= null){		
			request.getSession().removeAttribute("appObj");
			request.getSession().invalidate();
			mav.setViewName("Logout");
			return mav;
		}
				
		mav.setViewName("ErrorPage");
		return mav;
	} 
	
}
