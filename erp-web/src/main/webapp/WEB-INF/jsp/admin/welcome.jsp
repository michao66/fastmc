<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>

<div  class="easyui-panel" title="图表分析" style="padding:10px;" data-options="collapsible:true">
<div class="row-fluid">
				<div class="span12">
					<form action="#" method="get" class="form-inline" data-grid-search="btxxTable" id="queryChartForm">
					        <functionTag:comboxTag id="projectId"  attribute="class=input-xlarge" itemValue="method:loader=configBtXMload"/>
							
							<span class="input-append">
								<button class="btn green" type="submmit">
									<i class="icon-search"></i>&nbsp; 查&nbsp;询
								</button>
								
							</span>
					

					</form>
				</div>
			</div>
	<div id="chartContainer"/>
</div>


<script type="text/javascript">
<!--
$(function () {
 $('#queryChartForm').bind('submit', function() {
    var projectId = $('#projectId').find("option:selected").val();
    var projecttext = $('#projectId').find("option:selected").text();
    $.ajax({  
         url:'${base}/admin/areaNumChart.action?projectId='+projectId,  
         type:'post',  
         dataType:'json',  
         success:function(data){
              $('#chartContainer').highcharts({
				        chart: {
				            type: 'column'
				        },
				        title: {
				            text: '三门峡市各县区补贴金额情况'
				        },
				        subtitle: {
				            text: projecttext
				        },
				        xAxis:{  
                            categories: data.categories  
                        }, 
				        yAxis: {
				            min: 0,
				            title: {
				                text: '金额 (百万元)'
				            }
				        },
				       
				        
				        series:[{
				            data: data.series
				        }]
				 });
         }
     })
		 return false;
   })  
   
   
$('#queryChartForm').submit(); 
});
//-->
</script>
</div>