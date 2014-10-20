<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="easyui-layout"  style="width:100%;height:100%" data-options="border:false,fit:true">

<div data-options="region:'north'" border="false" style=" padding:10px 1px 1px 1px;">
<div class="row-fluid">
				<div class="span12">
					<form action="#" method="get" class="form-inline form-validation " data-grid-search="userTable">
					       <select name="pager.property" class="input-medium">
								<option value="search['CN_signinid']">登录帐号</option>
								<option value="search['CN_nick']">姓名</option>
							</select>
						    <input type="text" name="searchKey" id="searchKey" value="" class="input-xlarge"/>  
							
							<span class="input-append">
								<button class="btn green" type="submmit">
									<i class="icon-search"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default" type="reset">
									<i class="icon-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
					

					</form>
				</div>
			</div>


<div style="background:#efefef;padding:3px 5px;">

<a  href="#" data-toggle="win-edit" title="补贴项目" dataUrl="<%=basePath%>auth/user/create.action" dataGridId="userTable"    class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-add'">新建</a>
<a  href="#" data-toggle="win-edit" title="补贴项目" dataUrl="<%=basePath%>auth/user/edit.action" dataGridId="userTable"  idField="id" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>
<a  href="#" data-toggle="data-del" dataUrl="<%=basePath%>auth/user/doDelete.action" dataGridId="userTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">删除</a>
<a  href="#" data-toggle="win-edit" title="用户关联角色" target_to="tap"  dataUrl="${base}/auth/user/roles.action" dataGridId="userTable" idField="id"  class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">关联角色</a>
	

</div>

</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="userTable"
url="<%=basePath%>auth/user/findByPage.action"
data-options="fitColumns:true,fit:true,nowrap:false,idField:'id',singleSelect:true,border:false"
title="补贴项目">
<thead>
<tr>
<th data-options="field:'id'" hidden="false"  align="center">id</th>
<th data-options="field:'signinid'" width="100" align="center">登录帐号</th>
<th data-options="field:'nick'" width="180" align="center">姓名</th>
<th data-options="field:'email'" width="180" align="center">电子邮件</th>
<th data-options="field:'mobilePhone'" width="180" align="center">联系电话</th>
<th data-options="field:'enabled'" width="180" align="center">启用状态</th>
<th data-options="field:'accountExpireTime'" width="180" align="center">账号失效日期</th>
</tr>
</thead>
</table>
</div>

</div>

<script type="text/javascript">
<!--
	$(function(){
       
	});
//-->
</script>