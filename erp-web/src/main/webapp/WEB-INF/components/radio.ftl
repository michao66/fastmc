<#assign parameters = tag.parameters/>
 <label class="radio inline">
<input type="radio" name="${tag.id?default("")?html}"<#rt/>
<#if parameters.value??>
 value="${parameters.value?html}"<#rt/>
</#if>
<#if parameters.id??>
   id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.class??>
 class="${parameters.class?html}"<#rt/>
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
/><#rt/>
<#if tag.label??>
${tag.label}<#rt/>
</#if>
</label>

