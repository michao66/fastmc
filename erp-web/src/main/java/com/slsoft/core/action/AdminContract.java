package com.slsoft.core.action;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.fastmc.core.utils.RequestUtils;
import cn.fastmc.core.utils.ResponseUtils;
import cn.fastmc.sqlconfig.query.BaseQuery;

import com.slsoft.auth.entify.User;
import com.slsoft.auth.service.UserService;
import com.slsoft.core.security.AuthContextHolder;
import com.slsoft.core.security.AuthUserDetails;
import com.slsoft.core.security.AuthUserHolder;
import com.slsoft.core.security.ExtSecurityInterceptor;

@Controller
@RequestMapping("admin")
public class AdminContract{
	
	@Autowired
	private UserService userService;
	@Resource(name="jdbcQuery")
	protected BaseQuery nativeQuery;
	@RequestMapping(value = "/index")
	public String adminIndex(@RequestParam Map<String, String> queryparam,Writer writer) throws IOException {
		return "/admin/main";
	}
	@RequestMapping(value = "/lock")
	 public void lock(HttpServletRequest request,HttpServletResponse response) {
	        HttpSession session = request.getSession();
	        Assert.notNull(AuthContextHolder.getAuthUserPin());
	        session.setAttribute(ExtSecurityInterceptor.SESSION_KEY_LOCKED, true);
	        ResponseUtils.renderAjaxJsonSuccessMessage(response, "会话已锁定");
	    }
	@RequestMapping(value = "/unlock")
	    public void unlock(HttpServletRequest request,HttpServletResponse response) {
	        HttpSession session = request.getSession();
	        Assert.isTrue(session.getAttribute(ExtSecurityInterceptor.SESSION_KEY_LOCKED) != null, "Session unlocked");
	        String password = request.getParameter("password");
	        if(userService.validatePassword(AuthContextHolder.getAuthUserPin(), password)){
	        	session.removeAttribute(ExtSecurityInterceptor.SESSION_KEY_LOCKED);
		        ResponseUtils.renderAjaxJsonSuccessMessage(response, "会话已解锁");
	        }else{
	        	ResponseUtils.renderAjaxJsonSuccessMessage(response, "密码不正确");
	        }
	        
	    }
	@RequestMapping("/nav/{module}/{page}")
	public String nav(@PathVariable String module, @PathVariable
	String page, @RequestParam
	Map<String, String> param, HttpServletRequest request, Writer writer) throws IOException {
		return "/admin/"+module +"/"+ page;
	}
	
	 @RequestMapping(value = "findFormPage")
     public ModelAndView findFormPage(@RequestParam("pageId") String pageId, @RequestParam("formId") String formId,String templatePath){
    	 Assert.notNull(pageId, "pageId action  path  must not be null");
		 Assert.notNull(formId, "formId action  method  must not be null");
		 templatePath = templatePath==null?"/common/commForm":"/common/"+templatePath;
		 ModelAndView model = new ModelAndView(templatePath);
    	 model.addObject("pageId",pageId);
    	 model.addObject("formId",formId);
	     return model;
    
     }
	 
	    
	    //"密码修改显示"
	     @RequestMapping(value = "/profile/passwd")
	    public ModelAndView passwd() {
	    	return 	 ResponseUtils.buildDefaultInputView(null,"system","profilePasswdForm",null);
	    }

	    
	    //密码修改处理
	    @RequestMapping(value = "/profile/doPasswd")
	    public void doPasswd(HttpServletRequest request,HttpServletResponse response) {
	       
	        AuthUserDetails authUserDetails = AuthContextHolder.getAuthUserDetails();
	        Assert.notNull(authUserDetails);

	        String oldpasswd = request.getParameter("oldpasswd");
	        String newpasswd = request.getParameter("newpasswd");
	        Assert.isTrue(StringUtils.isNotBlank(oldpasswd));
	        Assert.isTrue(StringUtils.isNotBlank(newpasswd));

	        User user = AuthUserHolder.getLogonUser();
	        String encodedPasswd = userService.encodeUserPasswd(user, oldpasswd);
	        if (!encodedPasswd.equals(user.getPassword())) {
	        	ResponseUtils.renderAjaxJsonSuccessMessage(response, "原密码不正确,请重新输入");
	        } else {
	            userService.save(user, newpasswd);
	            ResponseUtils.renderAjaxJsonSuccessMessage(response, "密码修改成功,请在下次登录使用新密码");
	        }
	    }
	    
	    @SuppressWarnings("unchecked")
		@RequestMapping(value = "/areaNumChart")
	    public void areaNumChart(HttpServletRequest request,HttpServletResponse response) {
	    	Map<String,String> param = RequestUtils.getQueryParams(request);
	    	List datas = nativeQuery.findList("areaNumChart", param);
	    	  List xAxis = new ArrayList();  
	    	  List series = new ArrayList();
	    	for(Object data : datas){
	    		Map row = (Map)data;
	    		xAxis.add(row.get("name"));
	    		series.add(row.get("data"));
	    	}
	    	JSONObject json = new JSONObject(); 
	    	json.put("categories", xAxis);
	    	json.put("series", series);
	    	ResponseUtils.renderJson(response, json.toJSONString());
	    }
	    
	    @RequestMapping(value = "/welcome")
	    public String welcome(HttpServletRequest request,HttpServletResponse response) {
	    	return "/admin/welcome";
	    }

	    	

	    
}
