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
					<form action="#" method="get" class="form-inline" data-grid-search="btxxTable" id="brxxForm">
					        <functionTag:comboxTag id="projectId"  attribute="class=input-xlarge" itemValue="method:loader=configBtXMload"/>
							<select name="pager.property" class="input-medium">
								<option value="name">姓名</option>
								<option value="cardId">身份证</option>
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

<a id="btxx_imp_excel" href="#"  class="easyui-linkbutton"   data-options="plain:true,iconCls:'easyUIicon-add'">导入</a>
<a id="btxx_add" href="#"  class="easyui-linkbutton"   data-options="plain:true,iconCls:'easyUIicon-add'">新增</a>
<a id="btxx_edit" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-edit'">修改</a>
<a id="edit" href="#" data-toggle="data-del" dataUrl="<%=basePath%>btinfo/btxx/doDelete.action" dataGridId="btxxTable" class="easyui-linkbutton" data-options="plain:true,iconCls:'easyUIicon-remove'">删除</a>	

</div>

</div>
<div data-options="region:'center'" border="false"> 
<table id="btxxTable"></table>
</div>

<script type="text/javascript">
<!--
	$(function(){
		     function reloadGrid(){
				  $("#btxxTable").datagrid("reload");
			 }
             $('#btxx_imp_excel').bind('click',function(){
				   var projectId = $('#projectId').find("option:selected").val();
				   if(projectId == "" || projectId == null){
					   Global.notify("请先选择相关补贴项目信息")
				   }
					 
                   Global.popWin("导入补贴信息","<%=basePath%>btinfo/btxx/imp.action?projectId="+projectId,450,300,reloadGrid); 
			 }); 

			  $('#btxx_add').bind('click',function(){
				   var projectId = $('#projectId').find("option:selected").val();
				   if(projectId == "" || projectId == null){
					   Global.notify("请先选择相关补贴项目信息")
				   }
                   Global.popWin("补贴信息管理","<%=basePath%>btinfo/btxx/createBtxx.action?projectId="+projectId,650,400,reloadGrid); 
			 }); 
			 $('#btxx_edit').bind('click',function(){ 
                   var projectId = $('#projectId').find("option:selected").val();
				   if(projectId == "" || projectId == null){
					   Global.notify("请先选择相关补贴项目信息")
				   }
                   	var selected = $("#btxxTable").datagrid("getSelected");
						if (!selected){
							Global.notify("请先选择相关信息")
							return null;
						}  
                   Global.popWin("补贴信息管理","<%=basePath%>btinfo/btxx/eidtBtxx.action?projectId="+projectId+"&ID="+selected.id,650,400,reloadGrid); 
			  });
             $('#brxxForm').bind('submit', function() {
                    var projectId = $('#projectId').find("option:selected").val();
					 if(projectId == "" || projectId == null){
					   Global.notify("请先选择相关补贴项目信息")
				     }
					$.ajax({
						type : "GET",
						cache : false,
						url : '<%=basePath%>btinfo/btxx/getTableHeader.action?projectId='+projectId,
						dataType : "json",
						success : function(g) {
                            $('#btxxTable').datagrid({
								url:'<%=basePath%>btinfo/btxx/findByNativePage.action?pagingSql=btxxPage&pagingCountSql=btxxCount&projectId='+projectId,
                                fitColumns:true,
								pagination:true,
								fit:true,
								nowrap:false,
								idField:'id',
								singleSelect:true,
								border:false,
								columns:[g.columns]
							});
						
                           var param = $form.serializeObject();
                           var property = param['pager.property'];
						   param[property]=param['searchKey'];
						   $grid.datagrid('load',param);
						}
					});
					return false;
             });  

			  $('#brxxForm').submit();
	});
//-->
</script>