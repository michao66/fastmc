<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="<%=basePath%>resource/plugins/jquery-handsontable/dist/jquery.handsontable.full.js"></script>
<link rel="stylesheet" media="screen" href="<%=basePath%>resource/plugins/jquery-handsontable/dist/jquery.handsontable.full.css">
<div class="easyui-layout"  style="width:100%;height:100%" data-options="border:false,fit:true">

<div data-options="region:'north'" border="false" style=" padding:10px 1px 1px 1px;">
<div class="row-fluid">
				<div class="span12">
					<form id="onlineExcelForm" action="#" method="get" class="form-inline" data-grid-search="btxxTable" id="brxxForm">
					        <lable>选择项目</lable>
					        <functionTag:comboxTag id="onlineExcelForm_projectId"  attribute="name=projectId|class=input-xlarge" itemValue="method:loader=configBtXMload"/>
							<lable>选择区域</lable>
						    <input ype="hidden"  name="xzqy" id="onlineExcelForm_xzqy" placeholder="按编号,名称或拼音查询" data-toggle="autocomplete"  data-url='<%=basePath%>btinfo/xzqy/autocomplete.action'  style="width:300px"/>  
							
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

<a id="onlineExcel_save" href="#"  class="easyui-linkbutton"   data-options="plain:true,iconCls:'easyUIicon-save'">保存</a>
(ps:可从excel直接复制到表格)
</div>

</div>
<div data-options="region:'center'" border="false" > 
<div id="onlineExcel" style=" width:'100%';height:100%;overflow: scroll"></div>
</div>
<script type="text/javascript">
<!--
    var heads = [];
	var fields = [];
	$(function(){
		   $('#onlineExcel_save').bind('click',function(){
				   var projectId = $('#onlineExcelForm_projectId').find("option:selected").val();
				   if(projectId == "" || projectId == null){
					   Global.notify("请先选择相关补贴项目信息")
                       return false;
				   }
                   var xzqyId = $('#onlineExcelForm_xzqy').val();
				   if(xzqyId == "" || xzqyId == null){
					   Global.notify("请先选择相关区域信息")
					   return false;
				   }
				
                   var handsontable = $('#onlineExcel').data('handsontable');
				   var excelData = handsontable.getData();
	
				
				   $.each(excelData,function(i, item){
					    if(handsontable.isEmptyRow(i)){
							excelData.splice($.inArray(item,excelData),1);
							return true;
					    }
						 item['project']=projectId;
						 item['departid']=xzqyId;
					}); 

				
					  
				 
				    if(excelData.length==0){
					   Global.notify("表格为空或数据格试不正确，请检查！")
					   return;
				    }
					Global.blockUI('body');

				    $.ajax({
						url: "<%=basePath%>btinfo/btxx/onlineExceSave.action",
						data: {data:JSON.stringify(excelData)}, //returns all cells' data
						dataType: 'json', 
						type: 'POST',
						success: function (data) {
						      Global.unblockUI('body');
							  if(data.status=='error'){	
								Global.notify(data.message,"warning");
							  }else {
								Global.notify(data.message);
							  }
						},
						error: function () {
						   Global.notify("表单处理异常，请联系管理员","warning")
						}
				  });  
                  
			 }); 
          $('#onlineExcelForm').bind('submit', function() {
                var projectId = $('#onlineExcelForm_projectId').find("option:selected").val();
				 if(projectId == "" || projectId == null){
					   Global.notify("请先选择相关补贴项目信息");
					     return false;;
				  }
				   var xzqyId = $('#onlineExcelForm_xzqy').val();
				   if(xzqyId == "" || xzqyId == null){
					   Global.notify("请先选择相关区域信息")
					    return false;;
				   }
				$.ajax({
						type : "GET",
						cache : false,
						url : '<%=basePath%>btinfo/btxx/getTableHeader.action?projectId='+projectId,
						dataType : "json",
						success : function(g) {
                            var columns =[];
							$.each(g.columns,function(i, item){
								 if(item.field == 'departid'||item.field == 'project'){
									return true;
								 }
								 if(item.field == 'money'||item.field == 'population'||item.field == 'summoney'||item.field == 'field1'||item.field == 'field2'||item.field == 'field3'||item.field == 'field4'){
									 columns.push({data:item.field, type: 'numeric'});
								 }else{
									  columns.push({data:item.field});
								 }
								 
								 fields.push(item.field);
								 heads.push(item.title);
							 });
						
						  $('#onlineExcel').handsontable({
							      data:[{name:''}],
								  colHeaders:heads,
								  columns:columns,
								  rowHeaders: true,
								  stretchH: 'all',
								  minSpareRows: 1,
								  onBeforeChange: function (data) {
									  for (var i = 0, ilen = data.length; i < ilen; i++) {
											if (data[i][0] > 0) { //if it is not first row
												if(data[i][1]==0){ //if it is the first column
														//some validate logic here
											}else if(data[i][1]==1){//if it is the second column
														//some validate logic here
													}
											}
										}
									  }
								 
								});							
						}
					});
					return false;
		  });
       
	});
//-->
</script>