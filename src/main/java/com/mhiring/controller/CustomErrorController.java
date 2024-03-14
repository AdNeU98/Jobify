package com.mhiring.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

  @GetMapping("/error")
  public ModelAndView getErrorPath() {	  
	var mav = new ModelAndView();
	mav.setViewName("ErrorPage");
    return mav;
  }
}