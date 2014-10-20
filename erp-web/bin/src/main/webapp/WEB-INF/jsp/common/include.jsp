<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="/WEB-INF/functionTag.tld" prefix="functionTag" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<script src="<%=basePath%>resource/dwz/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.core.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.util.date.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.validate.method.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.barDrag.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.drag.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.tree.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.accordion.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.ui.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.theme.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.switchEnv.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.alertMsg.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.contextmenu.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.tab.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.resize.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.dialog.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.sortDrag.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.cssTable.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.stable.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.taskBar.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.ajax.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.pagination.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.database.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.datepicker.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.effects.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.panel.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.checkbox.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.history.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.combox.js" type="text/javascript"></script>
<script src="<%=basePath%>resource/dwz/dwz.print.js" type="text/javascript"></script>

<link href="<%=basePath%>resource/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>resource/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link href="<%=basePath%>resource/bootstrap/css/style.css" rel="stylesheet" type="text/css" />

<link href="<%=basePath%>resource/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath%>resource/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath%>resource/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=basePath%>resource/app/custom.css" rel="stylesheet" type="text/css" />



<meta name="viewport" content="width=device-width, initial-scale=1.0">