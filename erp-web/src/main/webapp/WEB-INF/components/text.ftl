 <#assign parameters = tag.parameters/>
 <input type="text" <#rt/>
         name="${tag.id?default("")?html}"<#rt/>
	 class="${parameters.class?default("span12")}" <#rt/>
	 <#if parameters.width??>
	   style="width:${parameters.width?html}px;"<#rt/>
	 </#if>
	<#if parameters.size??>
	  size="${parameters.size?html}"<#rt/>
	</#if>
	<#if parameters.title??>
	  title="${parameters.title?html}"<#rt/>
	</#if>
	<#if parameters.maxlength??>
	  maxlength="${parameters.maxlength?html}"<#rt/>
	</#if>
	<#if parameters.id??>
	 id="${parameters.id?html}"<#rt/>
	</#if>
	<#if parameters.readonly??>
	 readonly="readonly"<#rt/>
	</#if>

	<#if parameters.updateReadonly?? && form_updateState >
	 readonly="readonly"<#rt/>
	</#if>
	<#if inputValue??>
		  value="${inputValue?html}"<#rt/>
        <#else>
	  <#if parameters.value??>
	     value="${parameters.value?default("")}" <#rt/>
          </#if>
          <#if parameters.currentUser??>
	     value="${userInfoMethod(parameters.currentUser)}" <#rt/>
	  </#if>
	  </#if>
	 <#rt/><#include "validator.ftl"/> <#rt/>
	 <#rt/><#include "scripting-events.ftl"/><#rt/>
     />
  