<#assign parameters = tag.parameters/>
     <input type="text" <#rt/> 
         name="${tag.id?default("")?html}"<#rt/>
	<#if tag.id??>
	 id="${tag.id?html}"<#rt/>
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
	<#if parameters.readonly?default(false)>
	 readonly="readonly"<#rt/>
	</#if>
	<#include "scripting-events.ftl"/>
     />
<script type="text/javascript"><#rt/>
  $(function(){<#rt/>
     $('#${parameters.id?html}').ligerDateEditor({format: "yyyy-MM-dd",width:'${parameters.width?default("180")}'});
<#rt/>
   });
</script>  



