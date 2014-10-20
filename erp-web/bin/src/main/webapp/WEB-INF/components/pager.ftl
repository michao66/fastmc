<#assign pager = tag.pager />  
<#assign targetType = Request['targetType']!"navTab" />

<form id="pagerForm" method="post" action="#rel#">
<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
<input type="hidden" id="orderProperty" name="orderProperty" value="${page.orderProperty}" />
<input type="hidden" id="orderDirection" name="orderDirection" value="${page.orderDirection}" />
</form>


<div class="row page_wrapper">
  <div class="col-md-6"  >
  <div style="padding-top: 2px;">
     <span>显示</span>
	<select name="pageSize" onchange="pageBreak({data:{pageSize:this.value}})" class="input-sm">
	  <#list [10,20,30,50] as value>
	     <option value="${value}" <#if value == page.pageSize>selected</#if>>${value}</option>
          </#list>
	</select>
      <span>项记录,共: ${pager.total} 项</span>
   </div>
  </div>
 <div class="col-md-6" >
       <div class = "tables_paginate pagination">

         <#if isFirst>
	   <li class="firstPage disabled" ><span>首&nbsp;页</span></li>
	 <#else>
	   <li class="firstPage" ><a href="javascript:pageBreak({data:{pageNumber:1}});">首&nbsp;页</a></li>
	 </#if>
         <#if hasPrevious> 
	     <li class="previousPage"><a href="javascript:pageBreak({data:{pageNumber:${previousPageNumber}}});">上一页</a></li>
	 <#else>
	    <li class="previousPage disabled" ><span>上一页</span></li>
	 </#if>
	 
	 <#list segment as segmentPageNumber>
	
	  <#if segmentPageNumber != pageNumber>
	      <li><a href="javascript: pageBreak({data:{pageNumber:${segmentPageNumber}}});">${segmentPageNumber}</a></li>
	  <#else>
	     <li class="active"><span>${segmentPageNumber}</span></li>
	  </#if>
	  
	</#list>
        <#if hasNext>
	   <li class="nextPage"><a  href="javascript:pageBreak({data:{pageNumber:${nextPageNumber}}});">下一页</a></li>
	  <#else>
	     <li class="nextPage disabled"><span>下一页</span></li>
        </#if>
	<#if isLast>
	     <li class="lastPage disabled"><span>末&nbsp;页</span></li>
	 <#else>
	    <li class="lastPage "><a  href="javascript:pageBreak({data:{pageNumber:${lastPageNumber}}});">末&nbsp;页</a></li>
	</#if>
	 
	
       <div>
</div>
</div>

