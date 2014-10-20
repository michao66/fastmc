package cn.fastmc.core;

import javax.servlet.http.HttpServletRequest;




public interface TreeDataProvider {
		
	public Object[] getChildren(Object parentData,HttpServletRequest request )throws ServiceException ;

	
    
}
