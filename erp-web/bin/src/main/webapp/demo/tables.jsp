<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="/demo/tables.jsp"  rel="pagerForm"  method="get" class="form-inline form-validation form-search form-search-init"
						data-grid-search=".grid-auth-department-index">
						<div class="input-group">
							<div class="input-cont">
								<input type="text" name='search["CN_code"]' class="form-control" placeholder="代码...">
							</div>
							<span class="input-group-btn">
								<button class="btn green" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp;查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp;重&nbsp;置
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>

				<div class="toolbar" >
				  <a href="#" class="btn btn-primary" data-toggle="dynamic-tab" data-url="/admin/findFormPage.action?pageId=system&formId=userForm">
				   <i class="fa fa-plus-circle"></i>&nbsp;新&nbsp;增 
				  </a>

				  <button type="button" class="btn btn-info"><i class="fa fa-pencil"></i>&nbsp;修&nbsp;改</button>
				  <button type="button" class="btn default"><i class="fa fa-trash-o"></i>&nbsp;删&nbsp;除</button>
				  <div class="btn-group">
				  <button type="button" class="btn green dropdown-toggle" data-toggle="dropdown">
					<i class="fa fa-external-link"></i>&nbsp; 导&nbsp;出 <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu">
					<li><a href="#"><i class="fa fa-qrcode"></i>&nbsp;当前页</a></li>
					<li><a href="#"><i class="fa fa-wrench"></i>&nbsp;自定义</a></li>
				  </ul>
				  </div>
				</div>
            
			<div class="row">
				<div class="col-md-12">
		
					<functionTag:tableTag path="system" 
					  id="userTable"
					  entityFullName="com.slsoft.code.admin.entify.User"
					  pagingSql="userInfo_pages"
					  pagingCountSql="userInfo_count">
					 </functionTag:tableTag>
	
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/resource/js/list.js" />

