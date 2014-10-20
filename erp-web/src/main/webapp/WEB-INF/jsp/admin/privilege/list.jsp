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
					<form action="#" method="get" class="form-inline">
					       <select name="pager.property" class="input-medium">
								<option value="lzhtbm">类型</option>
								<option value="cbfmc">代码</option>
								<option value="cbfzjhm">名称</option>
								<option value="srfmc">URL</option>
								<option value="srfzjhm">排序号</option>
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

<a  href="#" data-toggle="win-edit" title="补贴项目" dataUrl="<%=basePath%>auth/privilege/create.action"  dataGridId="privilegeTable"   class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-add'">新建</a>
<a  href="#" data-toggle="win-edit" title="补贴项目" dataUrl="<%=basePath%>auth/privilege/edit.action" dataGridId="privilegeTable"  idField="id" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>

<a  href="#" data-toggle="data-del" dataUrl="<%=basePath%>auth/privilege/doDelete.action" dataGridId="privilegeTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">删除</a>	

</div>
</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="privilegeTable"
url="<%=basePath%>auth/privilege/findByPage.action"
data-options="queryParams:{sidx:'code'},
fitColumns:true,
fit:true,nowrap:false,
idField:'id',
singleSelect:true,
border:false"
title="权限列表">
<thead>
<tr>
<th data-options="field:'id'" hidden="false"  align="center">id</th>
<th data-options="field:'code'" width="100" align="center">代码</th>
<th data-options="field:'type',formatter:function(value,row){
 if(value=='MENU'){return '菜单';}
 if(value=='BTN'){return '按钮';}
 if(value=='URL'){return 'URL';}
}" width="180" align="center">类型</th>
<th data-options="field:'title'" width="180" align="center">名称</th>
<th data-options="field:'url'" width="150" align="center">URL</th>
<th data-options="field:'orderRank'" width="100" align="center">排序号</th>
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