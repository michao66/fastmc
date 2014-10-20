<#assign parameters = tag.parameters/>

<div style="float:left;width:450px; margin:5px auto">
     <div style="float:left; width:200px">
     <p>待选区</p>
     <select id="selectL" name="selectL" multiple="multiple"  style="width:180px; height:120px">
        <#if parameters.itemValue??>
	<#assign valus = parameters.itemValue/>
	</#if>
	<#if fromItemValue??>
	  <#assign valus = fromItemValue/>
	</#if>
	<#list valus?keys as v>
	<option value="${v?html}"
	<#if inputValue??>
	<#if v==inputValue>
	  selected="selected"<#rt/>
	</#if>
	</#if>
	><#t/>
	${valus[v]?html}<#t/>
	</option><#lt/>
        </#list>
     </select>
     </div>
     <div class="multipleSelect_opt">
        <p id="toright" title="添加">&gt;</p>
        <p id="toleft" title="移除">&lt;</p>
     </div>
     <div style="float:left; width:200px">
     <p>已选区</p>
     <select id="selectR" name="selectR" multiple="multiple" style="width:180px; height:120px" >
       <#if toItemValue??>
	  <#assign valus = toItemValue/>
	</#if>
	<#list valus?keys as v>
	<option value="${v?html}"
	<#if inputValue??>
	<#if v==inputValue>
	  selected="selected"<#rt/>
	</#if>
	</#if>
	><#t/>
	${valus[v]?html}<#t/>
	</option><#lt/>
        </#list>
     </select>
     <label for="${tag.id?default("")?html}"></label>
     </div>
 </div>
<#include "hidden.ftl"/>
<label for="${tag.id?default("")?html}"></label>
<script type="text/javascript">
$(function(){
        
        var leftSel = $("#selectL");
	var rightSel = $("#selectR");
	$("#toright").bind("click",function(){		
		leftSel.find("option:selected").each(function(){
		     if(inSelectValues(rightSel,this.value)){
		        return ;
		     }else{
		       $(this).remove().appendTo(rightSel);
			setMultipleValue();
		     }
		});
	});
	$("#toleft").bind("click",function(){		
		rightSel.find("option:selected").each(function(){
		     if(inSelectValues(leftSel,this.value)){
		         $(this).remove();
		     }else{
		        $(this).remove().appendTo(leftSel);
			
		     }
		     setMultipleValue();
		});
	});
	leftSel.dblclick(function(){
		$("#toright").click()
	});
	rightSel.dblclick(function(){
		$("#toleft").click()
	});
	function inSelectValues(sel,value){
	   var arry = sel.find("option");
	   for(var i = 0; i < arry.length; i++) {
	         if(arry[i].value == value){
		    return true;
		 }
	   }
	   return false;

	   
	}
	function setMultipleValue(){
		var selVal = [];
		rightSel.find("option").each(function(){
			selVal.push(this.value);
		});
		selVals = selVal.join(",");
	        $("#${tag.id}").val(selVals)
			
	
	}
        
	setMultipleValue();
});
 </script>