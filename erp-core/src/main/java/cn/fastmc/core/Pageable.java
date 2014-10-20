package cn.fastmc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fastmc.core.pagination.PropertyFilter;

public class Pageable implements Serializable {
	private static final long serialVersionUID = -3930180379790344299L;
	private static final int MIN_PAGE_SIZE = 1;
	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int MAX_PAGE_SIZE = 1000;
	private int pageNumber = 1;
	private int pageSize = 20;
	private Map<String,String> searchParam;
	private Order.Direction  orderDirection;
	
	private List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
	

	private List<Order> orders = new ArrayList<Order>();
	private String orderProperty;

	public Pageable() {
	}

	public Pageable(Integer pageNumber, Integer pageSize) {
		if ((pageNumber != null) && (pageNumber.intValue() >= 1))
			this.pageNumber = pageNumber.intValue();
		if ((pageSize != null) && (pageSize.intValue() >= MIN_PAGE_SIZE) && (pageSize.intValue() <= MAX_PAGE_SIZE))
			this.pageSize = pageSize.intValue();
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1)
			pageNumber = 1;
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return this.pageSize;
	}
	
	public int getOffset() {
		return (getPageNumber() - 1) * getPageSize();
	}

	public void setPageSize(int pageSize) {
		if ((pageSize < MIN_PAGE_SIZE) || (pageSize > MAX_PAGE_SIZE))
			pageSize = DEFAULT_PAGE_SIZE;
		this.pageSize = pageSize;
	}

	public Order.Direction getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(Order.Direction orderDirection) {
		this.orderDirection = orderDirection;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getOrderProperty() {
		return this.orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public Map<String,String> getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(Map<String,String> searchParam) {
		this.searchParam = searchParam;
	}
	
	public List<PropertyFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<PropertyFilter> filters) {
		this.filters = filters;
	}

}
