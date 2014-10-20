package com.slsoft.auth.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.utils.ResponseUtils;

import com.google.common.collect.Lists;
import com.slsoft.auth.entify.Role;
import com.slsoft.auth.entify.User;
import com.slsoft.auth.entify.UserR2Role;
import com.slsoft.auth.service.RoleService;
import com.slsoft.auth.service.UserService;
import com.slsoft.core.action.BaseController;

@Controller
@RequestMapping("auth/user")
public class UserAction extends BaseController<User, String> {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public UserAction() {
		super();
		this.pageId = "system";
		this.formId = "userForm";
	}

	@Override
	protected BaseService<User, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.userService;
	}

	@RequestMapping("index")
	public String index() {
		return "/admin/user/list";
	}

	@Override
	public void doCreate(HttpServletRequest request,
			HttpServletResponse response) {
		User user = newBindingEntity(request);

		if (user.isNew()) {
			userService.save(user, request.getParameter("newpassword"));
			ResponseUtils.renderAjaxJsonSuccessMessage(response, "操作成功");
		} else {
			String newpassword = request.getParameter("newpassword");
			if (StringUtils.isNotBlank(newpassword)) {
				userService.save(user, newpassword);
			} else {
				userService.save(user);
			}
			ResponseUtils.renderAjaxJsonSuccessMessage(response, "操作成功");
		}
	}
    //角色关联
	@RequestMapping("roles")
	public ModelAndView roles(String ID) {
		List<Role> roles = Lists.newArrayList();
		List<Role> allRoles = roleService.findAllCached();
		List<UserR2Role> r2s = userService.findOne(ID).getUserR2Roles();
		for (Role role : allRoles) {
			if (Role.ROLE_ANONYMOUSLY_CODE.equals(role.getCode())) {
				continue;
			}
			role.addExtraAttribute("related", false);
			for (UserR2Role r2 : r2s) {
				if (r2.getRole().equals(role)) {
					role.addExtraAttribute("r2CreatedDate", r2.getCreateDate());
					role.addExtraAttribute("related", true);
					break;
				}
			}
			roles.add(role);
		}
		ModelAndView view = new ModelAndView("/admin/user/user-roles");
		view.addObject("roles", roles);
		return view;
	}
	
	 //更新角色关联
	    @RequestMapping("doUpdateRelatedRoleR2s")
	    public void doUpdateRelatedRoleR2s(String id,String[] r2ids,HttpServletRequest request,
				HttpServletResponse response) {
	      
	        userService.updateRelatedRoleR2s(id, r2ids);
	        ResponseUtils.renderAjaxJsonSuccessMessage(response, "操作成功");
	    }

}
