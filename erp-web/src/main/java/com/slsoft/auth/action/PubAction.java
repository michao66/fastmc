package com.slsoft.auth.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pub")
public class PubAction {
	@RequestMapping("signin")
   public String signin(){
	   return "/login";
   }
	
	@RequestMapping("logout")
	   public String logout(){
		   return "/logout";
	   }
}
