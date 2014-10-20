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
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
		<!-- basic styles -->
		<link rel="stylesheet" href="<%=basePath%>resource/plugins/font-awesome/css/font-awesome.min.css" />
		<link href="<%=basePath%>resource/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/style-metronic.css" />
		<link rel="stylesheet" href="<%=basePath%>resource/css/style.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/style-responsive.css" />
 
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css" />
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/jquery-ui/redmond/jquery-ui-1.10.3.custom.min.css">
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/bootstrap-toastr/toastr.min.css" /> 

		<link rel="stylesheet" href="<%=basePath%>resource/css/search.css" rel="stylesheet" />

		<link rel="stylesheet" href="<%=basePath%>resource/css/lock.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=basePath%>resource/css/themes/light.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/custom.css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/plugins.css" />

		<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/plugins/uniform/css/uniform.default.min.css" /> 
		<!–[if lt IE 9]>
		<script src=”http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js”></script>
		<script src=”http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js”></script>
		<![endif]–>
	    <script src="<%=basePath%>resource/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery.cookie.min.js" type="text/javascript"></script>

		<script src="<%=basePath%>resource/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
		
	    <script src="<%=basePath%>resource/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script  src="<%=basePath%>resource/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.min.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/plugins/bootstrap-editable/inputs-ext/address/address.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/pinyin.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/plugins/jquery.address/jquery.address-1.5.js"></script>
        <script src="<%=basePath%>resource/plugins/jquery.blockui.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery-dataTable/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/plugins/jquery-dataTable/js/dataTables.bootstrap.js" type="text/javascript"></script>
        <script src="<%=basePath%>resource/plugins/jquery.form.js"></script>
		<script src="<%=basePath%>resource/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/plugins/jquery-validation/dist/additional-methods.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/plugins/jquery-validation/localization/messages_zh.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resource/js/form-validation.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/util.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/app.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/global.js" type="text/javascript"></script>
		<script src="<%=basePath%>resource/js/page.js" type="text/javascript"></script>
		
	
		<script src="<%=basePath%>resource/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>

	</head>

	<body class="page-header-fixed page-body">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->
			<a class="navbar-brand" href="javascript:window.location.reload()"
				style="padding-top: 5px; padding-bottom: 5px; margin-left: 2px; width: 400px">
				<h3 style="margin-top: 2px; margin-bottom: 0px; color: #dddddd;">
					<%-- <img src="resources/images/logo.jpg"/> --%>
					<s:property value="%{systemTitle}" />
				</h3>
			</a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <img
				src="assets/img/menu-toggler.png" alt="" />
			</a>

			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav navbar-nav pull-right" style="margin-right: 50px">

				<!-- BEGIN NOTIFICATION DROPDOWN -->
				<li class="dropdown hide" id="header_notification_bar"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> <i class="fa fa-warning"></i> <span
						class="badge">6</span>
				</a>
					<ul class="dropdown-menu extended notification">
						<li>
							<p>You have 14 new notifications</p>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 250px;">
								<li><a href="#"> <span class="label label-icon label-success"><i class="fa fa-plus"></i></span> New
										user registered. <span class="time">Just now</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span> Server
										#12 overloaded. <span class="time">15 mins</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-warning"><i class="fa fa-bell-o"></i></span>
										Server #2 not responding. <span class="time">22 mins</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-info"><i class="fa fa-bullhorn"></i></span>
										Application error. <span class="time">40 mins</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span> Database
										overloaded 68%. <span class="time">2 hrs</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span> 2 user
										IP blocked. <span class="time">5 hrs</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-warning"><i class="fa fa-bell-o"></i></span>
										Storage Server #4 not responding. <span class="time">45 mins</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-info"><i class="fa fa-bullhorn"></i></span> System
										Error. <span class="time">55 mins</span>
								</a></li>
								<li><a href="#"> <span class="label label-icon label-danger"><i class="fa fa-bolt"></i></span> Database
										overloaded 68%. <span class="time">2 hrs</span>
								</a></li>
							</ul>
						</li>
						<li class="external"><a href="#">See all notifications <i class="m-icon-swapright"></i></a></li>
					</ul></li>
				<!-- END NOTIFICATION DROPDOWN -->
				<!-- BEGIN INBOX DROPDOWN -->
				<li class="dropdown hide" id="header_inbox_bar"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-close-others="true"> <i class="fa fa-envelope"></i> <span class="badge">5</span>
				</a>
					<ul class="dropdown-menu extended inbox">
						<li>
							<p>You have 12 new messages</p>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 250px;">
								<li><a href="inbox.html?a=view"> <span class="photo"><img src="./assets/img/avatar2.jpg" alt="" /></span>
										<span class="subject"> <span class="from">Lisa Wong</span> <span class="time">Just Now</span>
									</span> <span class="message"> Vivamus sed auctor nibh congue nibh. auctor nibh auctor nibh... </span>
								</a></li>
								<li><a href="inbox.html?a=view"> <span class="photo"><img src="./assets/img/avatar3.jpg" alt="" /></span>
										<span class="subject"> <span class="from">Richard Doe</span> <span class="time">16 mins</span>
									</span> <span class="message"> Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
								</a></li>
								<li><a href="inbox.html?a=view"> <span class="photo"><img src="./assets/img/avatar1.jpg" alt="" /></span>
										<span class="subject"> <span class="from">Bob Nilson</span> <span class="time">2 hrs</span>
									</span> <span class="message"> Vivamus sed nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
								</a></li>
								<li><a href="inbox.html?a=view"> <span class="photo"><img src="./assets/img/avatar2.jpg" alt="" /></span>
										<span class="subject"> <span class="from">Lisa Wong</span> <span class="time">40 mins</span>
									</span> <span class="message"> Vivamus sed auctor 40% nibh congue nibh... </span>
								</a></li>
								<li><a href="inbox.html?a=view"> <span class="photo"><img src="./assets/img/avatar3.jpg" alt="" /></span>
										<span class="subject"> <span class="from">Richard Doe</span> <span class="time">46 mins</span>
									</span> <span class="message"> Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
								</a></li>
							</ul>
						</li>
						<li class="external"><a href="inbox.html">See all messages <i class="m-icon-swapright"></i></a></li>
					</ul></li>
				<!-- END INBOX DROPDOWN -->
				<!-- BEGIN TODO DROPDOWN -->
				<li class="dropdown" id="header_task_bar"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-close-others="true"> <i class="fa fa-tasks"></i> <span class="badge badge-tasks-count"></span>
				</a>
					<ul class="dropdown-menu extended tasks" id="dropdown-task-list">
						<li>
							<p>
								您有 <span class="tasks-count"></span> 项待办任务
							</p>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 250px;">
								<li class="template display-hide"><a href="#">
										<div class="row">
											<div class="col-md-12">
												<span class="label label-icon label-primary"><i class="fa fa-user"></i></span><span class="task-desc">
												</span>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="task-biz-key">date</div>
											</div>
											<div class="col-md-6">
												<div style="text-align: right" class="task-date">date</div>
											</div>
										</div>
								</a></li>
							</ul>
						</li>
						<li class="external"><a href="#">See all tasks <i class="m-icon-swapright"></i></a></li>
					</ul></li>
				<!-- END TODO DROPDOWN -->

				<!-- BEGIN Mobile DROPDOWN -->
				<li class="dropdown user" style="padding-top: 5px"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-close-others="true"> <i class="fa fa-mobile"></i><i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu" id="dropdown-menu-mobile">
						<li><a href="${base}/pub/android_client.apk" target="_blank"><i class="fa fa-android"></i> Android客户端下载</a></li>
						<li class="divider divider-menus"></li>
					</ul></li>
				<!-- END Mobile DROPDOWN -->

				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown user" style="padding-top: 5px"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
					data-close-others="true"> <span class="username"><i class="fa fa-user"></i> <s:property
								value="%{authUserDetails.usernameDisplay}" /></span> <i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu">
						<li class="hide"><a href="extra_profile.html"><i class="fa fa-user"></i> My Profile</a></li>
						<li class="hide"><a href="page_calendar.html"><i class="fa fa-calendar"></i> My Calendar</a></li>
						<li class="hide"><a href="inbox.html"><i class="fa fa-envelope"></i> My Inbox <span
								class="badge badge-danger">3</span></a></li>
						<li class="hide"><a href="#"><i class="fa fa-tasks"></i> My Tasks <span class="badge badge-success">7</span></a></li>
						<li class="divider hide"></li>
						<li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-move"></i> 全屏显示</a></li>
						<li><a id="trigger_passwd" href="${base}/auth/profile!passwd" title="修改密码"><i class="fa fa-key"></i> 修改密码</a></li>
						<li><a href="#" id="a-lock-screen"><i class="fa fa-lock"></i> 锁定系统</a></li>
						<li><a href="javascript:;" id="a-logout"><i class="fa fa-sign-out"></i> 注销登录</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->

			</ul>
			<!-- END TOP NAVIGATION MENU -->

		</div>

		<!-- BEGIN STYLE CUSTOMIZER -->
		<div class="theme-panel hidden-xs hidden-sm pull-right" style="margin-top: -3px; position: absolute; right: 0px">
			<div class="toggler"></div>
			<div class="toggler-close"></div>
			<div class="theme-options">
				<div class="theme-option theme-colors clearfix">
					<span>颜色样式</span>
					<ul>
						<li class="color-black current color-default" data-style="default"></li>
						<li class="color-blue" data-style="blue"></li>
						<li class="color-brown" data-style="brown"></li>
						<li class="color-purple" data-style="purple"></li>
						<li class="color-grey" data-style="grey"></li>
						<li class="color-white color-light" data-style="light"></li>
					</ul>
				</div>
				<div class="theme-option">
					<span>页面布局</span> <select class="layout-option form-control input-small">
						<option value="fluid" selected="selected">扩展</option>
						<option value="boxed">收缩</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面头部</span> <select class="header-option form-control input-small">
						<option value="fixed" selected="selected">固定</option>
						<option value="default">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>侧边菜单</span> <select class="sidebar-option form-control input-small">
						<option value="fixed">浮动</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面底部</span> <select class="footer-option form-control input-small">
						<option value="fixed">固定</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>右键菜单</span> <select class="context-menu-option form-control input-small">
						<option value="enable" selected="selected">启用</option>
						<option value="disable">禁用</option>
					</select>
				</div>
			</div>
		</div>
		<!-- END BEGIN STYLE CUSTOMIZER -->

		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
		<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">Modal title</h4>
					</div>
					<div class="modal-body">Widget settings form goes here</div>
					<div class="modal-footer">
						<button type="button" class="btn blue">Save changes</button>
						<button type="button" class="btn default" data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
		<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
		<!-- BEGIN SIDEBAR1 -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU1 -->
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-xs"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
					<div class="sidebar-search">
						<div class="form-container">
							<div class="input-box">
								<a href="javascript:;" class="remove"></a> <input type="text" name="search" placeholder="菜单项快速查询过滤..." value=""
									title="试试输入菜单名称拼音首字母" /> <input type="button" class="submit" value=" " />
							</div>
						</div>
					</div> <!-- END RESPONSIVE QUICK SEARCH FORM -->
				</li>
			<li class="menu-root "><a href="javascript:;"
						data-code="M897633"> <i class="fa fa-cogs"></i> <span class="title">基础配置管理</span> <span class="arrow "></span>
					</a> 
							<ul class="sub-menu" style="display: none;">
								
									
										<li><a href="javascript:;" data-code="M243342"> <i
												class="fa fa-ellipsis-vertical"></i> <span class="title">权限配置</span> <span
												class="arrow "></span>
										</a>
											<ul class="sub-menu" style="display: none;">
												
													
													
														<li><a class="ajaxify" data-code="M483623"
															href="/prototype/auth/privilege"
															rel="address:/M483623" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																权限管理</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M444893"
															href="/prototype/auth/role"
															rel="address:/M444893" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																角色管理</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M435878"
															href="/demo/tables.jsp"
															rel="address:/M435878" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																用户管理</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M435833"
															href="/prototype/auth/department"
															rel="address:/M435833" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																部门管理</a></li>
													
												
											</ul></li>
									
									
								
									
										<li><a href="javascript:;" data-code="M792883"> <i
												class="fa fa-ellipsis-vertical"></i> <span class="title">系统管理</span> <span
												class="arrow "></span>
										</a>
											<ul class="sub-menu" style="display: none;">
												
													
													
														<li><a class="ajaxify" data-code="M900054"
															href="/prototype/sys/menu"
															rel="address:/M900054" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																菜单管理</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M900056"
															href="/prototype/sys/config-property"
															rel="address:/M900056" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																参数配置</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M900055"
															href="/prototype/sys/data-dict"
															rel="address:/M900055" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																数据字典</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M900057"
															href="/prototype/sys/pub-post"
															rel="address:/M900057" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																公告管理</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M993391"
															href="/prototype/sys/util!mgmt"
															rel="address:/M993391" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																辅助管理</a></li>
													
												
											</ul></li>
									
									
								
									
										<li><a href="javascript:;" data-code="M206909"> <i
												class="fa fa-ellipsis-vertical"></i> <span class="title">监控管理</span> <span
												class="arrow "></span>
										</a>
											<ul class="sub-menu" style="display: none;">
												
													
													
														<li><a class="ajaxify" data-code="M574536"
															href="/prototype/schedule/job-bean-cfg"
															rel="address:/M574536" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																计划任务</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M573336"
															href="/prototype/sys/logging-event"
															rel="address:/M573336" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																错误日志</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M573335"
															href="/prototype/auth/user-logon-log"
															rel="address:/M573335" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																登录日志</a></li>
													
												
													
													
														<li><a class="ajaxify" data-code="M573337"
															href="/prototype/sys/util!dev"
															rel="address:/M573337" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																开发调试</a></li>
													
												
											</ul></li>
									
									
								
							</ul>
						</li>
				
				
				
			</ul>

			<!-- END SIDEBAR MENU1 -->
		</div>
		<!-- END SIDEBAR1 -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<div class="row" style="margin-left: 0px; margin-right: 0px">
				<div class="col-md-12" style="padding-left: 0px; padding-right: 0px">
					<ul class="page-breadcrumb breadcrumb" id="layout-nav" style="margin-top: 0px; margin-bottom: 5px;">
						<li class="btn-group" style="right: 0px;">
							<button data-close-others="true" data-delay="1000" data-toggle="dropdown" class="btn blue dropdown-toggle"
								type="button">
								<span><i class="fa fa-reorder"></i> 访问列表</span> <i class="fa fa-angle-down"></i>
							</button>
							<ul role="menu" class="dropdown-menu">
							</ul>
							<button class="btn default btn-dashboard" type="button">
								<i class="fa fa-home"></i> Dashboard
							</button>
							<button class="btn default btn-close-active" type="button">
								<i class="fa fa-times"></i>
							</button>
						</li>
						<li><i class="fa fa-home"></i> <a href="javascript:;">Dashboard</a></li>
					</ul>
					<div class="tab-content">
						<div id="tab_content_dashboard"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- BEGIN PAGE -->

	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			2013 &copy;
		</div>
		<div class="footer-tools">
			<span class="go-top"> <i class="fa fa-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->

	<div class="page-lock" id="page-lock" style="text-align: left; color: #eeeeee; display: none">
		<div class="page-logo">
			<h3>
				<s:property value="%{systemTitle}" />
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
					<s:property value="%{authUserDetails.usernameDisplay}" />
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
		<div class="page-footer">2013 &copy;</div>
	</div>

        <script type="text/javascript">
        <!--
		var WEB_ROOT = "${base}";
		$(function() {
			App.init();
            Util.init();
            Global.init();
            //console.profileEnd();
        });
        //-->
        </script>
	
</body>
</html>

