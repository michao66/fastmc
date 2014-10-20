<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>

<div class="easyui-layout"  style="width:100%;height:100%" data-options="border:false,fit:true">

<div data-options="region:'north'" border="false" style=" padding:10px 1px 1px 1px;">
<div class="row-fluid">
				<div class="span12">
					<form action="#" method="get" class="form-inline form-validation " data-grid-search="roleTable">
					       <select name="pager.property" class="input-medium">
								<option value="search['CN_code']">代码</option>
								<option value="search['CN_title']">名称</option>
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

<a  href="#" data-toggle="win-edit" title="补贴项目" dataUrl="${base}/auth/role/create.action"  dataGridId="roleTable" idField="id"   class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-add'">新建</a>
<a  href="#" data-toggle="win-edit" title="补贴项目" dataUrl="${base}/auth/role/edit.action" dataGridId="roleTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>
<a  href="#" data-toggle="data-del" dataUrl="${base}/auth/role/doDelete.action" dataGridId="roleTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">删除</a>
<a  href="#" data-toggle="win-edit" title="角色关联权限" target_to="tap"  dataUrl="${base}/auth/role/privileges.action" dataGridId="roleTable" idField="id"  class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">关联权限</a>

</div>

</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="roleTable"
url="${base}/auth/role/findByPage.action"
data-options="fitColumns:true,fit:true,nowrap:false,idField:'id',singleSelect:true,border:false"
title="补贴项目">
<thead>
<tr>
<th data-options="field:'id'" hidden="false"  align="center">id</th>
<th data-options="field:'code'" width="100" align="center">代码</th>
<th data-options="field:'title'" width="180" align="center">名称</th>
<th data-options="field:'description'" width="180" align="center">描述</th>
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