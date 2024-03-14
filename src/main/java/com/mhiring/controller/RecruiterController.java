package com.mhiring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.mhiring.dao.ApplicationDAO;
import com.mhiring.dao.JobDAO;
import com.mhiring.dao.RecruiterDAO;
import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Application;
import com.mhiring.pojo.Job;
import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;
import com.mhiring.service.RecruiterService;
import com.mhiring.service.UserService;
import com.mhiring.view.PdfView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
	
	@GetMapping("/home")
	public ModelAndView homePage(HttpServletRequest request, 
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		
		HttpSession httpSession = request.getSession(false);
		Recruiter userRecObj = new Recruiter();	
		var mav = new ModelAndView();	
		if(httpSession!=null && httpSession.getAttribute("userObj")!= null) {
			
			System.out.println(httpSession.getAttribute("userObj"));
			userRecObj = (Recruiter) httpSession.getAttribute("userObj");
			if(userRecObj.getRecruiterID() != 0) {
				
				RecruiterService recServ = new RecruiterService(recruiterDao, jobDAO, appDao);
				userRecObj = recServ.getRecruiterObj(userRecObj);
				httpSession.setAttribute("userObj", userRecObj);
				mav.setViewName("RecruiterHome");
			}
			else {
				mav.setViewName("ErrorPage");
			}
			
			return mav;
		}

		mav.setViewName("AccessDenied");
		return mav;
	}

	@PostMapping("/jobs/save")
	public ModelAndView saveJobPosted(@Valid @ModelAttribute("job") Job jobObj,
			BindingResult bindingResult, HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
	var mav = new ModelAndView();
	if((Recruiter)request.getSession().getAttribute("userObj") != null) {
		 
		 if (bindingResult.hasErrors()) {
				mav.setViewName("PostJob");
				return mav;
		 }
		 RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);
		 Recruiter rtcr = new Recruiter();
		 rtcr = (Recruiter) request.getSession().getAttribute("userObj");
		 jobObj.setRecruiter(rtcr);
		 
		 try {
			jobObj = recruiterService.saveJobPosting(jobObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 if(jobObj.getJobID() != 0) {	
			 mav.addObject("successMesssage", "Job created successfully");
			 mav.setViewName("Jobadded-success");
		 }
		 else {
			 mav.addObject("errorMessage", "Job creation failed");
			 mav.setViewName("PostJob");
		 }
		  
		 request.getSession().setAttribute("userObj", rtcr);
		 return mav;
	}
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/jobs/create")
	public ModelAndView postJob(HttpServletRequest request) {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			mav.setViewName("PostJob");
			Job jobObj = new Job();
			Recruiter userObj = new Recruiter();
			userObj = (Recruiter) request.getSession().getAttribute("userObj");
			request.getSession().setAttribute("userObj", userObj);
			mav.addObject("job", jobObj);
			return mav;
		}
		mav.setViewName("AccessDenied");
		return mav;
	}
		
	@GetMapping("/jobs")
	public ModelAndView viewAllJobsPosted(HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);
			try {
					List<Job> listOfJobs = new ArrayList<Job>();
					listOfJobs = recruiterService.getAllJobs();
					mav.addObject("listOfJobs", listOfJobs);
					mav.setViewName("ViewAllJobs");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mav;
		}
		
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/jobs/created")
	public ModelAndView viewRecJobsPosted(HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);
			try {
					Recruiter userObj = new Recruiter();
					userObj = (Recruiter) request.getSession().getAttribute("userObj");
					List<Job> listOfJobs = new ArrayList<Job>();
					listOfJobs = recruiterService.getCreatedJobs(userObj);
					mav.addObject("listOfJobs", listOfJobs);
					mav.setViewName("ViewCreatedJobs");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mav;
		}
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	
	@SuppressWarnings("unused")
	@GetMapping("/jobs/status/{jobID}")
	public ModelAndView expireJob(@PathVariable("jobID") long jobID, 
			Recruiter recObj,HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);
			recObj = (Recruiter)request.getSession().getAttribute("userObj");		
			try {
				Job jobObj = recruiterService.getJobDetails(jobID);
				if(jobObj.getRecruiter().getRecruiterID() == recObj.getRecruiterID()){
					if(jobObj.getStatus().equals("Available")) {
						jobObj.setStatus("Expired");
					}
					else if(jobObj.getStatus().equals("Expired")) {
						jobObj.setStatus("Available");
					}
					
					jobObj = recruiterService.updateJobDetails(jobObj);
					if(jobObj.getStatus().equals("Expired")) {
						recruiterService.updateAppStatusOnExpire(jobObj);
					}
					else if(jobObj.getStatus().equals("Available")){
						recruiterService.updateAppStatusOnAvailable(jobObj);
					}
					
				if(jobObj!= null) {
					mav.addObject("successMessage", "Job status updated");									
				}
				else {
					 mav.addObject("errorMessage", "Job status updation failed");
				}
				
				List<Job> listOfJobs = new ArrayList<Job>();
				listOfJobs = recruiterService.getCreatedJobs(recObj);
				mav.addObject("listOfJobs", listOfJobs);
				mav.setViewName("ViewCreatedJobs");
				return mav;
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/jobs/update/{jobID}")
	public ModelAndView updateJobForm(@PathVariable("jobID") long jobID, 
			Recruiter recObj, HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) throws Exception {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);
			recObj = (Recruiter)request.getSession().getAttribute("userObj");
			Job jobObj = recruiterService.getJobDetails(jobID);
			if(jobObj!= null && jobObj.getRecruiter().getRecruiterID() == recObj.getRecruiterID()) {
					mav.addObject("jobObj", jobObj);	
					mav.setViewName("UpdateJob");	
					return mav;	
			}					
		}
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@PostMapping("/jobs/update")
	public ModelAndView updateJob(@RequestParam("roleName") String roleName,
			@RequestParam("descrp") String descrp,
			@RequestParam("expReq") String expReq,
			@RequestParam("location") String location,
			@RequestParam("jobId") long jobId, HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) throws Exception {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);
			Job jobObj = new Job();
			jobObj = recruiterService.getJobDetails(jobId);
			
			if(roleName == "" || descrp == "" || expReq == ""  || location == "") {
				mav.addObject("errorMessage", "Please fill information correctly");		
				mav.addObject("jobObj", jobObj);
				mav.setViewName("UpdateJob");
				return mav;
			}
			
			if(roleName.length() > 200 || descrp.length() > 200 
					|| expReq.length() > 200 || location.length()>200) {
				mav.addObject("errorMessage", "Please put characters less than 200");
				mav.addObject("jobObj", jobObj);
				mav.setViewName("UpdateJob");
				return mav;
			}
			
			jobObj.setDescrp(descrp);
			jobObj.setExpReq(expReq);
			jobObj.setLocation(location);
			jobObj.setRoleName(roleName);
			
			jobObj = recruiterService.updateJobDetails(jobObj);
			
			mav.setViewName("RecruiterHome");
			mav.addObject("successMessage", "Job updated successfully");
			return mav;
		}

		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@GetMapping("/profile")
	public ModelAndView showProfilePage(HttpServletRequest request) {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			Recruiter recObj = new Recruiter();
			recObj = (Recruiter) request.getSession().getAttribute("userObj");		
			User userObj = new User();
			userObj = recObj.getUser();
			mav.addObject("user", userObj);
			mav.setViewName("RecruiterProfilePage");
			return mav;
		}
		
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@PostMapping("/updateProfile")
	public ModelAndView updateProfile(@RequestParam("teamName") String teamName,
			@RequestParam("expertise") String expertise, HttpServletRequest request,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		
		var mav = new ModelAndView();
		Recruiter recObj = new Recruiter();
		recObj = (Recruiter) request.getSession().getAttribute("userObj");
		RecruiterService recruiterService = new RecruiterService(recruiterDao, jobDAO, appDao);	
		
		if(expertise.length() > 240 || teamName.length() > 240) {
			mav.addObject("errorMessage", "Please update entry within 240 characters");
			mav.addObject("user", recObj.getUser());
			mav.setViewName("RecruiterProfilePage");
			return mav;
		}
		recObj.setExpertise(expertise);
		recObj.setTeamName(teamName);
		
		try {
			recObj = recruiterService.updateRecProfile(recObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession httpSession = request.getSession(false);	
		httpSession.setAttribute("userObj", recObj);
		mav.addObject("user", recObj.getUser());
		mav.setViewName("RecruiterProfilePage");
		return mav;
		
	}
	
	@GetMapping("/jobs/{jobID}/applicants")
	public ModelAndView viewApplicantsOfJob(@PathVariable("jobID") long jobID,
			HttpServletRequest request, Recruiter recruiter,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		
		var mav = new ModelAndView();
		if((Recruiter)request.getSession().getAttribute("userObj") != null) {
			try {
				recruiter = (Recruiter)request.getSession().getAttribute("userObj");
				RecruiterService recSev = new RecruiterService(recruiterDao, jobDAO, appDao);
				Job jobObj = recSev.getJobDetails(jobID);
				if(jobObj.getRecruiter().getRecruiterID() == recruiter.getRecruiterID()) {
					List<Application> applications = jobObj.getApplications();
					mav.setViewName("AppliedApplicants");
					mav.addObject("lsOfApplications", applications);
					mav.addObject("jobId", jobID);
					return mav;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				mav.setViewName("ErrorPage");
				return mav;
			}
		}
	
		mav.setViewName("AccessDenied");
		return mav;
	}
	
	@PostMapping("/jobs/{jobId}/applicants")
	public ModelAndView updateStatusOfApplicant(@RequestParam("status") String status,
			@RequestParam("applicationID") long appId,
			HttpServletRequest request, @PathVariable("jobId") long jobID,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
				
		RecruiterService recServ = new RecruiterService(recruiterDao, jobDAO, appDao);
		var mav = new ModelAndView();
		try {
			Application application = recServ.getApplicationDetails(appId);		
			if(application != null) {
				application.setAppStatus(status);
				application = recServ.updateApplicationStatus(application);
				mav.addObject("successMessage", "Applicant status updated");
			}
			else {
				mav.addObject("errorMessage", "Error updating status of applicant");
			}
			
			Job jobObj = recServ.getJobDetails(jobID);
			List<Application> applications = jobObj.getApplications();					
			mav.addObject("lsOfApplications", applications);
			mav.addObject("jobId", Long.toString(jobID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.setViewName("AppliedApplicants");
		return mav;
	}
	
	@GetMapping("/logout")
	public ModelAndView userLogout(HttpServletRequest request) {
		var mav = new ModelAndView();
		if((Recruiter) request.getSession().getAttribute("userObj")!= null){		
			request.getSession().removeAttribute("userObj");
			request.getSession().invalidate();
			mav.setViewName("Logout");
			return mav;
		}
				
		mav.setViewName("ErrorPage");
		return mav;
	} 
	
	@GetMapping("/pdfView")
	public ModelAndView publishPdf(HttpServletRequest request, Model model,
			RecruiterDAO recruiterDao, JobDAO jobDAO, ApplicationDAO appDao) {
		var mav = new ModelAndView();
		if((Recruiter) request.getSession().getAttribute("userObj")!= null){	
			mav.setView(new PdfView());
			Recruiter rec = (Recruiter) request.getSession().getAttribute("userObj");
			RecruiterService recServ = new RecruiterService(recruiterDao, jobDAO, appDao);
			rec = recServ.getRecruiterObj(rec);
			model.addAttribute("createdJobs", rec.getJobsAdded());
			return mav;
		}
		
		mav.setViewName("AccessDenied");
		return mav;
	}
	
}