<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>控制台</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link rel="stylesheet" href="<%=basePath%>resource/css/font-awesome.min.css" />
		<link href="<%=basePath%>resource/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/jquery-ui/redmond/jquery-ui-1.10.3.custom.min.css">
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/bootstrap-toastr/toastr.min.css" /> 
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/style-metronic.css" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/jquery-dataTable/css/dataTables.bootstrap.css"  />

		

		<link rel="stylesheet" href="<%=basePath%>resource/css/lock.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=basePath%>resource/css/custom.css" rel="stylesheet" />
		
	    <script src="<%=basePath%>resource/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery.cookie.min.js" type="text/javascript"></script>

		<script src="<%=basePath%>resource/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
		
	    <script src="<%=basePath%>resource/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/plugins/bootstrap-editable/inputs-ext/address/address.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/pinyin.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/plugins/jquery.address/jquery.address-1.5.js"></script>
        <script src="<%=basePath%>resource/plugins/jquery.blockui.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery-dataTable/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery-dataTable/js/dataTables.bootstrap.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/js/ace-extra.min.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/js/ace-elements.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/ace.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/util.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/app.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/global.js" type="text/javascript"></script>
		
	</head>

	<body>
		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							    后台管理系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="pull-right" style="margin-right: 24px">
					<ul class="nav ace-nav">
						<li class="light-blue"  >
						  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-close-others="true"> 
						  <i class="icon-user" ></i> <span class="username">Jason</span> <i class="icon-caret-down"></i>
					    </a>
					  <ul class="dropdown-menu">
						<li><a href="javascript:;" id="trigger_fullscreen"><i class="icon-move"></i> 全屏显示</a></li>
						<li><a id="trigger_passwd" href="#" title="修改密码"><i class="icon-key"></i> 修改密码</a></li>
						<li><a href="#" id="a-lock-screen"><i class="icon-lock"></i> 锁定系统</a></li>
						<li><a href="javascript:;" id="a-logout"><i class="icon-signout"></i> 注销登录</a></li>
					   </ul>
			     	 </li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
				<div class="pull-right" style="margin-top: 4px; position: absolute; right: 0px ;z-index:999"  >
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn" >
						<i class="icon-cog bigger-150"></i>
					</div>
					<div class="ace-settings-box" id="ace-settings-box"  >
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>&nbsp; 选择皮肤</span>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
							<label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
							<label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
							<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl">切换到左边</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
							<label class="lbl" for="ace-settings-add-container">
								切换窄屏
								<b></b>
							</label>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<div class="nav-search" id="nav-search"  >
								<span class="input-icon" style="z-index: 999">
									<i class="icon-search nav-search-icon"></i><input type="text" class="nav-search-input" name="search" placeholder="菜单项快速查询过滤..." value=""
									title="试试输入菜单名称拼音首字母" autocomplete="off" />
									
								</span>
						</div><!-- #nav-search -->

					<ul class="nav nav-list">
						<li class="active">
							<a href="/admin/dashboard.action" class="ajaxify" data-code="001" rel="address:/001">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> 控制台 </span>
							</a>
						</li>

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-desktop"></i>
								<span class="menu-text">表格</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a href="/demo/tables.jsp" class="ajaxify" rel="address:/002" data-code="002">
										<i class="icon-double-angle-right"></i>
										简单&动态
									</a>
								</li>

								<li>
									<a href="buttons.html" class="ajaxify">
										<i class="icon-double-angle-right"></i>
										jqGrid
									</a>
								</li>

								

							</ul>
						</li>

						
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>

				<div class="main-content">
					<!-- BEGIN PAGE -->
				<div class="page-content">
					<div class="row" style="margin-left: 0px; margin-right: 0px">
						<div class="col-md-12" style="padding-left: 0px; padding-right: 0px">
							<ul class="page-breadcrumb breadcrumb" id="layout-nav" style="margin-top: 0px; margin-bottom: 5px;">
								<li class="btn-group" style="right: 0px;">
									<button data-close-others="true" data-delay="1000" data-toggle="dropdown" class="btn blue dropdown-toggle"
										type="button">
										<span><i class="icon-reorder"></i> 访问列表</span> <i class="icon-angle-down"></i>
									</button>
									<ul role="menu" class="dropdown-menu">
									</ul>
									<button class="btn default btn-dashboard" type="button">
										<i class="icon-home"></i> Dashboard
									</button>
									<button class="btn default btn-close-active" type="button">
										<i class="icon-times"></i>
									</button>
								</li>
								<li><i class="icon-home"></i> <a href="javascript:;">Dashboard</a></li>
							</ul>
							<div class="tab-content">
								<div id="tab_content_dashboard"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- BEGIN PAGE -->
				</div><!-- /.main-content -->

		
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- 锁屏窗口 -->
		<div class="page-lock" id="page-lock" style="text-align: left; color: #eeeeee; display: none">
		<div class="page-logo">
			<h3>
				后台管理
			</h3>
		</div>
		<div class="page-body">
			<div class="tile bg-blue" style="margin-top: 10px; margin-left: 10px">
				<div class="tile-body">
					<i class="fa fa-lock"></i>
				</div>
			</div>
			<div class="page-lock-info">
				<h1>
					admin
				</h1>
				<form id="form-unlock" action="#" class="form-inline">
					<div class="input-group input-medium">
						<input type="password" placeholder="输入解锁码..." class="form-control" autocomplete="off"> <span
							class="input-group-btn">
							<button class="btn blue icn-only" type="submit">
								<i class="m-icon-swapright m-icon-white"></i>
							</button>
						</span>
					</div>
					<div class="relogin">请输入登录账号解锁</div>
				</form>
			</div>
		</div>
		<div class="page-footer">
			2013 &copy;
	   </div>
	
        <script type="text/javascript">
        <!--
		var WEB_ROOT = "${base}";
		$(function() {
			
            Util.init();
            Global.init();
            //console.profileEnd();
        });
        //-->
        </script>
	
</body>
</html>

