<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="/WEB-INF/functionTag.tld" prefix="functionTag" %>
<%
    pageContext.setAttribute("base", request.getContextPath());
%>