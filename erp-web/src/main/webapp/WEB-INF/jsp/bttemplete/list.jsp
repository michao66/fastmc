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
					<form  id="btinfo_btTempleteForm" action="<%=basePath%>btinfo/bttemplete/expExcelTemp.action" method="post" class="form-inline">
					     <lable>选择区域</lable>
						    <input ype="hidden" placeholder="按编号,名称或拼音查询"  name="xzqy" id="btinfo_btTempleteForm_xzqy" data-toggle="autocomplete"  data-url='<%=basePath%>btinfo/xzqy/autocomplete.action'  style="width:300px"/>
                               		      <lable>模板行数</lable>	
                                                    <input ype="text"  name="rowNum" value="20">   					      
						    <input type="hidden" name="projectId" id="btinfo_btTempleteForm_xzqy_projectId"/>  
							<span class="input-append">
								<button class="btn green" type="submmit">
									<i class="icon-search"></i>&nbsp; 下&nbsp;载
								</button>
								
							</span>
					

					</form>
				</div>
			</div>
</div>
<div data-options="region:'center'" border="false"> 
<table class="easyui-datagrid" pagination="true" id="bttempleteTable"
url="<%=basePath%>btinfo/bttemplete/findByNativePage.action?pagingSql=bttempletePage&pagingCountSql=bttempleteCount"
data-options="fitColumns:true,fit:true,nowrap:false,idField:'project',singleSelect:true,border:false"
title="补贴字段">
<thead>
<tr>
<th data-options="field:'PROJECT'" width="100" align="center">项目编码</th>
<th data-options="field:'NAME'" width="100" align="center">项目名称</th>
</tr>
</thead>
</table>
</div>

</div>

<script type="text/javascript">
<!--
	$(function(){
           $('#btinfo_btTempleteForm').bind('submit', function() {
					var selected = $('#bttempleteTable').datagrid("getSelected");
					if (!selected){
						Global.notify("请先选择相关信息")
						return false;
					}
				$("#btinfo_btTempleteForm_xzqy_projectId").val(selected.project);
				return true; // <-- important!
		 });  
	});
//-->
</script>