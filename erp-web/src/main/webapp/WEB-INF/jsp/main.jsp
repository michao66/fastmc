<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>控制台</title>
		<script type="text/javascript" src="${base}/resource/plugins/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css" href="${base}/resource/plugins/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/resource/plugins/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${base}/resource/plugins/select2/select2.css">
		<script type="text/javascript" src="${base}/resource/plugins/easyui/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="${base}/resource/plugins/easyui/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="${base}/resource/plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
	    <script src="${base}/resource/plugins/jquery.blockui.min.js" type="text/javascript"></script>
       
		
		<script type="text/javascript" src="${base}/resource/plugins/poshytip-1.2/jquery.poshytip.js"></script>
		<script type="text/javascript" src="${base}/resource/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${base}/resource/plugins/jquery-validation/localization/messages_zh.js"></script>
		<script type="text/javascript" src="${base}/resource/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="${base}/resource/plugins/backstretch/jquery.backstretch.min.js"></script>
        <script type="text/javascript" src="${base}/resource/plugins/highcharts-4.0.3/js/highcharts.js"></script>
		<script src="${base}/resource/js/pinyin.js"></script>
	    <script src="${base}/resource/js/JSPinyin.js"></script>
	    <script type="text/javascript" src="${base}/resource/js/util.js"></script>
	    <script type="text/javascript" src="${base}/resource/js/app.js"></script>
        <script type="text/javascript" src="${base}/resource/js/global.js"></script>
        <script type="text/javascript" src="${base}/resource/js/windowControl.js"></script>
        <script type="text/javascript" src="${base}/resource/js/theme.js"></script>
		<script type="text/javascript" src="${base}/resource/js/form-validation.js"></script>
		<script type="text/javascript" src="${base}/resource/js/jquery.form.js"></script>
        
        <script type="text/javascript" src="${base}/resource/js/page.js"></script>

		<link rel="stylesheet" type="text/css" href="${base}/resource/plugins/jqgrid/css/ui.jqgrid.css">
		<link rel="stylesheet" type="text/css" href="${base}/resource/plugins/bootstrap/css/bootstrap.min.css">
		
		<link rel="stylesheet" type="text/css" href="${base}/resource/plugins/fontAwesome/css/font-awesome.min.css">
		<!--[if IE 7]>
		<link rel="stylesheet" href="a${base}/resource/plugins/fontAwesome/css/font-awesome-ie7.min.css">
		<![endif]-->
	   <link rel="stylesheet" type="text/css" href="${base}/resource/plugins/poshytip-1.2/tip-yellow/tip-yellow.css">
      
		<link rel="stylesheet" type="text/css" href="${base}/resource/css/style-metro.css">
		<link rel="stylesheet" type="text/css" href="${base}/resource/css/index.css">
        <link rel="stylesheet" type="text/css" href="${base}/resource/css/lock.css">
		<script type="text/javascript" src="${base}/resource/plugins/bootstrap/js/bootstrap.min.js"></script> 

		
		<script type="text/javascript">
		  var WEB_ROOT = "${base}";
			$(function(){	
				Global.init();
			  <c:if test="${sessionScope.SESSION_KEY_LOCKED}">
			      $('#a-lock-screen').click();
			  </c:if>
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
						},onLoad:function(){
						   Page.initAjaxBeforeShow();
						   FormValidation.initAjax();
						}
					});
				}
			}
		</script>
		

	</head>
	<body class="easyui-layout" style="text-align:left;"  >
		<div id="north" region="north"  style="width:100%;height:54px;" >
		   <div id="header" >
			<div  class="headerNav">
			  <ul class="nav" style="margin:0px">
			        <li><a  href="javascript:;" id="a_fullscreen"><i class="icon-move"></i>&nbsp;全屏显示</a></li>
					<li><a  href="javascript:;" id="a-lock-screen"><i class="icon-lock"></i>&nbsp;锁定系统</a></li>
					<li><a  href="javascript:;" id="a_passwd" ><i class="icon-key"></i>&nbsp;修改密码</a></li>
					<li><a  href="javascript:;" id="a-logout"><i class="icon-signout"></i>&nbsp;退出</a></li>
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
			   <div title="补贴基础信息" data-options="iconCls:'easyUIicon-sum'" style="padding:3px;">
				  <ul class="easyui-tree" data-options="lines:true">
					 <li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('补贴项目管理','${base}/btinfo/btxm/index.action')">补贴项目管理</a>
				     </li>
				     <li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('行政区划管理','${base}/btinfo/xzqy/index.action')">行政区划管理</a>
				     </li>
				   </ul>
				</div>
				<div title="补贴项目设置" data-options="iconCls:'easyUIicon-sum'" style="padding:10px">
					<ul class="easyui-tree" data-options="lines:true">
					 <li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('补贴字段管理','${base}/btinfo/btfield/index.action')">补贴字段管理</a>
				     </li>
				     <li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('补贴模板管理','${base}/btinfo/bttemplete/index.action')">补贴模板管理</a>
				     </li>
				   </ul>
				</div>
				<div title="补贴信息" data-options="iconCls:'easyUIicon-sum'"  style="padding:3px;" >
					<ul class="easyui-tree" data-options="lines:true">
				
						<li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('补贴信息','${base}/btinfo/btxx/index.action')">补贴信息管理</a>
						
						</li>
						<li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('在线批量操作','${base}/btinfo/onlineExcel/index.action')">在线批量操作</a>
						
						</li>
					
					</ul>

				</div>
              
			  	<div title="系统管理" data-options="iconCls:'easyUIicon-sum'"  style="padding:3px;" >
					<ul class="easyui-tree" data-options="lines:true">
				
						<li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('用户管理','${base}/auth/user/index.action')">用户管理</a>
						</li>
						<li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('角色管理','${base}/auth/role/index.action')">角色管理</a>
						</li>

						<li iconCls="easyUIicon-gears">
						<a class="e-link" href="#" onclick="open1('权限管理','${base}/auth/privilege/index.action')">权限管理</a>
						</li>
					
					</ul>

				</div>


				
				
			</div>
		</div>


		<div region="center">
			<div id="tt" class="easyui-tabs" fit="true" border="false" plain="true">
				<div title="首页" href="${base}/admin/welcome.action"></div>
			</div>
		</div>
	</body>
	
		<div class="page-lock" id="page-lock" style="text-align: left; color: #eeeeee; display: none">

		<div class="page-logo">
			<h3>
				三门峡补贴信息导入系统
			</h3>
		</div>
		<div class="page-body">
			<div class="tile bg-blue" style="margin-top: 10px; margin-left: 10px">
				<div class="tile-body">
					<i class="icon-lock"></i>
				</div>
			</div>
			<div class="page-lock-info">
				<h1>
				</h1>
				<form id="form-unlock" action="${base}/admin/unlock.action" class="form-inline" method="post">
					
						<input type="password" name="password" placeholder="输入解锁码..." class="form-control" autocomplete="off"
							required="true"> <span class="input-group-btn">
							<button class="btn blue " type="submit">
								<i class="icon-arrow-right"></i>
							</button>
						</span>
				
					<div class="relogin">请输入登录密码解锁</div>
				</form>
			</div>
		</div>
		<div class="page-footer">
			2014 &copy;
			<%=request.getServerName()%>
		</div>
	</div>
</html>
 
 


