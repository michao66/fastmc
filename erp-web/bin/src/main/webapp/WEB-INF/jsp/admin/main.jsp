<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>控制台</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/easyui/themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>resource/plugins/easyui/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/plugins/easyui/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="<%=basePath%>resource/plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/js/global.js"></script>
	    <script type="text/javascript" src="<%=basePath%>resource/js/windowControl.js"></script>
	    <script type="text/javascript" src="<%=basePath%>resource/js/theme.js"></script>
        <script type="text/javascript" src="<%=basePath%>resource/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>resource/plugins/poshytip-1.2/jquery.poshytip.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/plugins/jquery-validation/localization/messages_zh.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/js/form-validation.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/js/jquery.form.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/jqgrid/css/ui.jqgrid.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/bootstrap/css/bootstrap.min.css">
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/fontAwesome/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/fontAwesome/css/font-awesome.min.css">
		<!--[if IE 7]>
		<link rel="stylesheet" href="a<%=basePath%>resource/plugins/fontAwesome/css/font-awesome-ie7.min.css">
		<![endif]-->
	   <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/poshytip-1.2/tip-yellow/tip-yellow.css">
      
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/style-metro.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/index.css">
		<script type="text/javascript">
		  var WEB_ROOT = "<%=path%>";
			$(function(){  
               		Global.init();

			});
			function open1(title,url){
				if ($('#tt').tabs('exists',title)){
					$('#tt').tabs('select', title);
				} else {
					$('#tt').tabs('add',{
						title:title,
						href:url,
						closable:true,
						extractor:function(data){
							data = $.fn.panel.defaults.extractor(data);
							
							return data;
						}
					});
				}
			}
		</script>


	</head>
	<body class="easyui-layout" style="text-align:left;"  >
		<div region="north"  style="width:100%;height:54px;" >
		   <div id="header" >
			<div  class="headerNav">
			  <ul class="nav" style="margin:0px">
			        <li><a href="#" target="_blank">首页</a></li>
					<li><a href="#" target="_blank">个人信息</a></li>
					<li><a href="#" target="_blank">修改密码</a></li>
					<li><a href="#">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="red"><div>黄色</div></li>
					<li theme="orange"><div>红色</div></li>
					<li theme="gray"><div>银色</div></li>
				
				</ul>
            </div>
			</div>
		</div>

		<div data-options="region:'west',split:true" title="主菜单" style="width:250px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="系统设置" data-options="iconCls:'easyUIicon-sum'"  style="padding:3px;" >
					<ul class="easyui-tree" data-options="lines:true">
				
						<li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('菜单管理','nav/privilege/list.action')">菜单管理</a>
						</li>
					
					</ul>

				</div>
				<div title="Title2" data-options="iconCls:'easyUIicon-sum'" style="padding:10px;">
					content2
				</div>
				<div title="Title3" data-options="iconCls:'easyUIicon-sum'" style="padding:10px">
					content3
				</div>
			</div>
		</div>


		<div region="center">
			<div id="tt" class="easyui-tabs" fit="true" border="false" plain="true">
				<div title="welcome" href="welcome.php"></div>
			</div>
		</div>
	</body>
</html>
 
 


