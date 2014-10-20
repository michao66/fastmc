<#assign parameters = tag.parameters/>
<input type="password"  name="${tag.id?default("")?html}"<#rt/>
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
	<#if parameters.readonly?default(false)>
	 readonly="readonly"<#rt/>
	</#if>
	<#include "scripting-events.ftl"/>
     />



