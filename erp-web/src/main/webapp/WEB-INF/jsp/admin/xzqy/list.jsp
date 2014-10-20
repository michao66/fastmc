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
					<form action="#" method="get"  class="form-inline form-validation" data-grid-search="xzqyTable">
					       <select name="pager.property" class="input-medium">
								<option value="search['CN_id']">行政编码</option>
								<option value="search['CN_name']">行政名称</option>
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
<a id="add" href="#" data-toggle="win-edit" title="行政区域管理" dataUrl="<%=basePath%>btinfo/xzqy/create.action"   dataGridId="xzqyTable" idField="id"  class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-add'">新建</a>
<a id="edit" href="#" data-toggle="win-edit" title="行政区域管理" dataUrl="<%=basePath%>btinfo/xzqy/edit.action" dataGridId="xzqyTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>	
<a id="edit" href="#" data-toggle="data-del" dataUrl="<%=basePath%>btinfo/xzqy/doDelete.action" dataGridId="xzqyTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">删除</a>	
</div>	


</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="xzqyTable"
url="<%=basePath%>btinfo/xzqy/findByPage.action"
data-options="fitColumns:true,fit:true,nowrap:false,idField:'id',singleSelect:true,border:false"
title="补贴行政区划">
<thead>
<tr>
<th data-options="field:'id'" width="100" align="center">行政编码</th>
<th data-options="field:'name'" width="180" align="center">行政名称</th>
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