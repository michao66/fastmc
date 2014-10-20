<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	$(function(){
		$('#add').click(function(){
			Global.popWin("权限管理",'<%=basePath%>auth/privilege/create.action',600,400,null);
		}); 
		$('#edit').click(function(){
			var selected = $('#privilegeTable').datagrid('getSelected');
			if (!selected){
				Global.notify("请先选择相关信息")
				return null;
			}
			Global.popWin("权限管理",'<%=basePath%>auth/privilege/edit.action?ID='+selected.id,600,400,null);
		});
	});
</script>	
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
<a id="add" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-add'">新建</a>
<a id="edit" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>	
</div>

</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="privilegeTable"
url="<%=basePath%>auth/privilege/findByPage.action"
data-options="fitColumns:true,fit:true,nowrap:false,idField:'id',singleSelect:true,border:false"
title="权限列表">
<thead>
<tr>
<th data-options="field:'id'" width="100" align="center">代码</th>
<th data-options="field:'type',formatter:function(value,row){
 if(value=='0'){return '菜单';}
 if(value=='1'){return '按钮';}
 if(value=='2'){return 'URL';}
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