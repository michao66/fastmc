<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-privilege">权限列表</a></li>
	</ul>
	<div class="tab-content">
		<div id="tab-privilege" class="tab-pane fade active in">
			<div class="row-fluid">
				<div class="span12">
					<form action="#" method="get" class="form-inline">
						<div class="input-group">
							<div class="input-cont">
								<input type="text" name="search['CN_code_OR_title_OR_url']" class="form-control" placeholder="权限代码、名称、URL...">
							</div>
							<span class="input-group-btn">
								<button class="btn green" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<div class="panelBar">
						<ul class="toolBar">
							<li><a class="add" target="navTab" rel="userNav" href="findFormPage.action?pageId=system&formId=userForm" title="添加用户"><span>添加</span></a></li>
							<li><a class="edit" target="navTab" rel="userNav" href="admin/findFormPage.action?pageId=system&formId=userForm'/>" title="编辑用户"><span>编辑</span></a></li>
							<li><a class="delete" target="selectedTodo" rel="ids" postType="string" title="你确定要删除吗?"><span>删除</span></a></li>
							<li class="line">line</li>
						</ul>
					</div>
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
<!-- 
<form method="post" rel="pagerForm" action="<c:url value='/admin/nav/user/list.action'/>" onsubmit="return navTabSearch(this)">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>关键词：</label>
				<input type="text" name="keywords" value="${param.keywords}"/>
			</li>
			<li>
				<label>状态：</label>
				<select name="status">
					<option value="">全部</option>
					<c:forEach var="item" items="${userStatusList}">
					<c:if test="${item ne 'DELETED'}">
					<option value="${item}" ${item eq param.status ? 'selected="selected"' : ''}>${item}</option>
					</c:if>
					</c:forEach>
				</select>
			</li>
		</ul>
		<div class="subBar">
			<ul>						
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="navTab" rel="userNav" href="findFormPage.action?pageId=system&formId=userForm" title="添加用户"><span>添加</span></a></li>
			<li><a class="edit" target="navTab" rel="userNav" href="admin/findFormPage.action?pageId=system&formId=userForm'/>" title="编辑用户"><span>编辑</span></a></li>
			<li><a class="delete" target="selectedTodo" rel="ids" postType="string" title="你确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<functionTag:tableTag path="system" 
	  id="userTable" 
	  entityFullName="com.slsoft.code.admin.entify.User"
	  pagingSql="userInfo_pages"
	  pagingCountSql="userInfo_count">
	 </functionTag:tableTag>
</div> -->
