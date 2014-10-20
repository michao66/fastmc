/**
 * Copyright (c) 2012
 */
package cn.fastmc.core.pagination;

import java.util.List;

import javax.servlet.http.HttpServletRequest;




import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.fastmc.core.ServiceException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;


public class GroupPropertyFilter {

	  private static ObjectMapper objectMapper = new ObjectMapper();

	    public static final String GROUP_OPERATION_AND = "and";
	    public static final String GROUP_OPERATION_OR = "or";

	    /** 组合类型:AND/OR */
	    private String groupType = GROUP_OPERATION_AND;

	    /** 组合条件列表 */
	    private List<PropertyFilter> filters = Lists.newArrayList();

	    /** 强制追加AND条件列表 */
	    private List<PropertyFilter> forceAndFilters = Lists.newArrayList();

	    /** 组合条件组 */
	    private List<GroupPropertyFilter> groups = Lists.newArrayList();

	    public List<GroupPropertyFilter> getGroups() {
	        return groups;
	    }

	    public void setGroups(List<GroupPropertyFilter> groups) {
	        this.groups = groups;
	    }

	    public String getGroupType() {
	        return groupType;
	    }

	    public void setGroupType(String groupType) {
	        this.groupType = groupType;
	    }

	    public List<PropertyFilter> getFilters() {
	        return filters;
	    }

	    public void setFilters(List<PropertyFilter> filters) {
	        this.filters = filters;
	    }

	    public List<PropertyFilter> getForceAndFilters() {
	        return forceAndFilters;
	    }

	    private GroupPropertyFilter() {
	    }

	    private GroupPropertyFilter(String groupType) {
	        this.groupType = groupType;
	    }

	    public GroupPropertyFilter append(GroupPropertyFilter... groups) {
	        this.groups.addAll(Lists.newArrayList(groups));
	        return this;
	    }

	    public GroupPropertyFilter append(PropertyFilter... filters) {
	        this.filters.addAll(Lists.newArrayList(filters));
	        return this;
	    }

	    public GroupPropertyFilter forceAnd(PropertyFilter... filters) {
	        this.forceAndFilters.addAll(Lists.newArrayList(filters));
	        return this;
	    }


   

    public static GroupPropertyFilter buildDefaultAndGroupFilter(PropertyFilter... filters) {
        GroupPropertyFilter dpf = new GroupPropertyFilter(GROUP_OPERATION_AND);
        if (filters != null && filters.length > 0) {
            dpf.setFilters(Lists.newArrayList(filters));
        }
        return dpf;
    }

    public static GroupPropertyFilter buildDefaultOrGroupFilter(PropertyFilter... filters) {
        GroupPropertyFilter dpf = new GroupPropertyFilter(GROUP_OPERATION_OR);
        if (filters != null && filters.length > 0) {
            dpf.setFilters(Lists.newArrayList(filters));
        }
        return dpf;
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(groups) && CollectionUtils.isEmpty(filters);
    }
    
    public static GroupPropertyFilter buildFromHttpRequest(Class<?> entityClass, HttpServletRequest request) {

        try {
            String filtersJson = request.getParameter("filters");

            GroupPropertyFilter groupPropertyFilter = new GroupPropertyFilter();
            //列表自定查询，暂不开发
            if (StringUtils.isNotBlank(filtersJson)) {
            
            }

            List<PropertyFilter> filters = PropertyFilter.buildFiltersFromHttpRequest(entityClass, request);
            if (CollectionUtils.isNotEmpty(filters)) {

                GroupPropertyFilter comboGroupPropertyFilter = new GroupPropertyFilter();
                comboGroupPropertyFilter.setGroupType(GROUP_OPERATION_AND);

                GroupPropertyFilter normalGroupPropertyFilter = new GroupPropertyFilter();
                normalGroupPropertyFilter.setGroupType(GROUP_OPERATION_AND);
                normalGroupPropertyFilter.setFilters(filters);
                comboGroupPropertyFilter.getGroups().add(normalGroupPropertyFilter);

                comboGroupPropertyFilter.getGroups().add(groupPropertyFilter);
                return comboGroupPropertyFilter;
            }

            return groupPropertyFilter;

        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


}
