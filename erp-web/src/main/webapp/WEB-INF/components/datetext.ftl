<input type="text" class="txt date" <#rt/>
         name="${tag.id?default("")?html}"<#rt/>
	 <#if parameters.width??>
	   style="width:${parameters.width?html}px;"<#rt/>
	 </#if>
	<#if parameters.size??>
	  size="${parameters.size?html}"<#rt/>
	</#if>
	<#if parameters.maxlength??>
	  maxlength="${parameters.maxlength?html}"<#rt/>
	</#if>
	<#if parameters.id??>
	 id="${parameters.id?html}"<#rt/>
	</#if>
	<#if parameters.readonly?default(false)>
	 readonly="readonly"<#rt/>
	</#if>
	<#if inputValue??>
	    value="${inputValue}"<#rt/>
	<#else>
	   <#if parameters.value??>
	     <#if parameters.value == "now">
	       value=${.now?string('yyyy-MM-dd')} 
	     <#else>
	       value="${parameters.value?html}" <#rt/>
	     </#if>	     
	   </#if>
	</#if>
	<#include "scripting-events.ftl"/>
     />
     <script type="text/javascript"><#rt/>
	$(function(){
	    $('#${parameters.id?html}').datepicker({dateFormat: 'yy-mm-dd'});
	});
     </script><#rt/>
