package com.mhiring.controller;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mhiring.dao.ApplicantDAO;
import com.mhiring.dao.DAO;
import com.mhiring.dao.RecruiterDAO;
import com.mhiring.dao.UserDAO;
import com.mhiring.pojo.Applicant;
import com.mhiring.pojo.Recruiter;
import com.mhiring.pojo.User;
import com.mhiring.service.RecruiterService;
import com.mhiring.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class UserController {

	@GetMapping("/login.htm")
	public ModelAndView showLogin() {
		var mav = new ModelAndView();
		mav.setViewName("Login");
		return mav;
	}
	
	@GetMapping("/index.htm")
	public ModelAndView showHome(ModelAndView mav) {
		User user = new User();
		mav.addObject("user", user);
		mav.setViewName("RegisterUser");
		return mav;
	}
	
	@PostMapping("/index.htm")
	public ModelAndView deleteUser(ModelAndView mav, HttpServletRequest request,  ApplicantDAO applicantDao) {
		
		if((Applicant) request.getSession().getAttribute("appObj")!= null) {
			UserService userService = new UserService(null, applicantDao, null);
			Applicant appOjb = (Applicant) request.getSession().getAttribute("appObj");
			Boolean isDeleted = userService.deleteApplicant(appOjb);			
			if(isDeleted) {
				User user = new User();
				mav.addObject("user", user);
				mav.setViewName("RegisterUser");
				mav.addObject("successMesssage", "Applicant deleted");
				return mav;
			}
		}
		
		mav.addObject("errorMessage", "Error deleting the user");
		mav.setViewName("ApplicantHome");
		return mav;
	}
	
	@PostMapping(path = "/register-success.htm")
	public ModelAndView userRegistration(@Valid @ModelAttribute("user") User user,
			BindingResult bindingResult, UserDAO userDao, RecruiterDAO recruiterDao, ApplicantDAO applicantDao) {
		
		var mav = new ModelAndView();
		UserService userService = new UserService(userDao, applicantDao, recruiterDao);
		if (bindingResult.hasErrors()) {
			mav.setViewName("RegisterUser");
			return mav;
		}
		
		Boolean isAllowed = userService.registrationCriteria(user);
		
		if(!isAllowed) {
			mav.addObject("errorMessage", "Email already exists");
			mav.addObject("user", user);
			mav.setViewName("RegisterUser");
			return mav;
		}
		
		userService.userRegistration(user);
		
		mav.addObject("successMesssage", "Registration Successful. Please Login to continue");
		mav.setViewName("Login");
			
		return mav;
	}

	
	@PostMapping("/home.htm")
	public ModelAndView userLogin(
			@RequestParam(name = "email", defaultValue = "null") String email,
			@RequestParam(name = "password", defaultValue = "null") String password,
			@RequestParam(name = "roleType", defaultValue = "null") String roleType, HttpServletRequest request
			, UserDAO userDao, RecruiterDAO recruiterDao, ApplicantDAO applicantDao) {
		
		var mav = new ModelAndView();
		if(!email.equals("null") && !password.equals("null") && !roleType.equals("null")) {
			UserService userService = new UserService(userDao, applicantDao, recruiterDao);			
		try {
			
			if(email.length() > 200 || password.length() > 200) {
				mav.addObject("errorMessage", "Entries less than 200 characters allowed only");
				mav.setViewName("Login");
				return mav;
			}
			Boolean isUser = userService.loginCriteria(email, password, roleType);
			
			if(!isUser) {
				mav.addObject("errorMessage", "Incorrect Information Provided");
				mav.setViewName("Login");
				return mav;
			}		
			
			HttpSession httpSession = request.getSession(true);		
			
			if(roleType.equals("Recruiter")) {
				Recruiter rectrObj = userService.getRecruiterUserDetails(email);
				httpSession.setAttribute("userObj", rectrObj);
				mav.setViewName("RecruiterHome");
			}
			else if(roleType.equals("Applicant")){
				Applicant appObj = userService.getApplicantUserDetails(email);
				httpSession.setAttribute("appObj", appObj);
				mav.setViewName("ApplicantHome");

			}						
		} catch(Exception e) {
			mav.setViewName("ErrorPage");
		}		
			return mav;
		}

		mav.setViewName("AccessDenied");
		return mav;
	}
	   
    
}
