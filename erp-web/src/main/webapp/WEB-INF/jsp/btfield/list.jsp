<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="<%=basePath%>resource/plugins/easyui/datagrid-groupview.js"></script>
<div class="easyui-layout"  style="width:100%;height:100%" data-options="border:false,fit:true">

<div data-options="region:'north'" border="false" style=" padding:10px 1px 1px 1px;">
<div class="row-fluid">
				<div class="span12">
					<form action="#" method="get" class="form-inline form-validation"  data-grid-search="btfieldTable">
					       <select name="pager.property" class="input-medium">
								<option value="search['CN_fildId']">字段编码</option>
								<option value="search['CN_projectText']">字段名称</option>
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
<a id="add" href="#" data-toggle="win-edit" title="补贴字段管理" dataUrl="<%=basePath%>btinfo/btfield/create.action"  dataGridId="btfieldTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-add'">新建</a>
<a id="edit" href="#" data-toggle="win-edit" title="补贴字段管理" dataUrl="<%=basePath%>btinfo/btfield/edit.action" dataGridId="btfieldTable" idField="id"  class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>	
<a id="edit" href="#" data-toggle="data-del" dataUrl="<%=basePath%>btinfo/btfield/doDelete.action" dataGridId="btfieldTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">删除</a>	
</div>	

</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="btfieldTable"
url="<%=basePath%>btinfo/btfield/findByPage.action"
data-options="queryParams:{sidx:'project,orderNum'},
fitColumns:true,
fit:true,nowrap:false,
idField:'id',
singleSelect:true,
border:false,
view:groupview,
groupField:'projectText',
groupFormatter:function(value,rows){
return value + ' - ' + rows.length + ' Item(s)';
}"
title="补贴字段">
<thead>
<tr>
<th data-options="field:'id',hidden:true" width="100" align="center">id</th>
<th data-options="field:'projectText'" width="100" align="center">项目名称</th>
<th data-options="field:'fildId'" width="100" align="center">字段编码</th>
<th data-options="field:'lable'" width="180" align="center">字段名称</th>
<th data-options="field:'type'" width="180" align="center">字段类型</th>
<th data-options="field:'attribute'" width="180" align="center">属性</th>
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