package com.slsoft.core.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.slsoft.auth.service.PrivilegeService;

public class DAOTest {
	 private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");   
	       
	 private static PrivilegeService userService = (PrivilegeService) context.getBean("privilegeService"); 
	 
	 public static void main(String[] arg){
		 List<String> ids = new ArrayList<String>();
		 ids.add("10");
		 ids.add("11");
		 userService.findAll(ids);
	 }

}
