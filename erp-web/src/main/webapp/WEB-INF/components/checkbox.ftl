<#assign parameters = tag.parameters/>
<input type="checkbox" name="${tag.id?default("")?html}" <#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.value??>
 value="${parameters.value?html}"<#rt/>
</#if>
<#if inputValue??>
   <#if inputValue==parameters.value>
       checked = "checked" <#rt/>
   </#if>
<#else>
   <#if parameters.check??>
      checked="checked" <#rt/>
   </#if>
</#if>
<#include "scripting-events.ftl"/>
/>

