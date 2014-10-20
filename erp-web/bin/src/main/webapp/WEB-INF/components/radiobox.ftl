<#assign parameters = tag.parameters/>
<th>${tag.label}:</th><#rt/>
<td >
<input type="radio"  name="${tag.name?default("")?html}"<#rt/>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
</#if>
<#include "scripting-events.ftl"/>
/>
</td><#rt/>
 <label for="${tag.id?default("")?html}"></label>

