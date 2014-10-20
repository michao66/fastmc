package com.slsoft.auth.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.utils.ResponseUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.slsoft.auth.entify.Privilege;
import com.slsoft.auth.entify.Role;
import com.slsoft.auth.entify.RoleR2Privilege;
import com.slsoft.auth.service.PrivilegeService;
import com.slsoft.auth.service.RoleService;
import com.slsoft.core.action.BaseController;
@Controller
@RequestMapping("auth/role")
public class RoleAction extends BaseController<Role, String> {
	@Autowired
	private RoleService roleService;
    
	@Autowired
	private PrivilegeService privilegeService;
	
	public RoleAction() {
		super();
		this.pageId = "system";
		this.formId = "roleForm";
	}

	@Override
	protected BaseService<Role, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.roleService;
	}
	
	@RequestMapping("index")
	public String index() {
		return "/admin/role/list";
	}
	@RequestMapping("privileges")
	public ModelAndView privileges(String ID) {
		 Map<String, List<Privilege>> groupDatas = Maps.newLinkedHashMap();
	     List<Privilege> privileges = privilegeService.findAllCached();
	     List<RoleR2Privilege> r2s = roleService.findOne(ID).getRoleR2Privileges();
	     for (Privilege privilege : privileges) {
	         List<Privilege> groupPrivileges = groupDatas.get(privilege.getCategory());
	         if (groupPrivileges == null) {
	             groupPrivileges = Lists.newArrayList();
	             groupDatas.put(privilege.getCategory(), groupPrivileges);
	         }
	         groupPrivileges.add(privilege);
	         privilege.addExtraAttribute("related", false);
	         for (RoleR2Privilege r2 : r2s) {
	             if (r2.getPrivilege().equals(privilege)) {
	                 privilege.addExtraAttribute("r2CreatedDate", r2.getCreateDate());
	                 privilege.addExtraAttribute("related", true);
	                 break;
	             }
	         }
	     }
	     ModelAndView view = new ModelAndView("/admin/role/role_privileges");
	     view.addObject("privileges",groupDatas);
	     return view;
	}
	
	@RequestMapping("doUpdateRelatedPrivilegeR2s")
    public void doUpdateRelatedPrivilegeR2s(String id,String[] r2ids,HttpServletResponse response) {
        roleService.updateRelatedPrivilegeR2s(id, r2ids);
        ResponseUtils.renderAjaxJsonSuccessMessage(response,"更新权限关联操作完成");
       
    }
	
}
