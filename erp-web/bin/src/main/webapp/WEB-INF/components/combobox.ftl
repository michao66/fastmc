<#assign parameters = tag.parameters/>
<select name="${tag.id?default("")?html}" <#rt/> 
<#if tag.id??> 
id="${tag.id?html}"<#rt/>
</#if>
 class="${parameters.class?default("span12")}" <#rt/>
<#if parameters.width??>
style="width:${parameters.width?html}px;"<#rt/>
</#if>
>
<#if parameters.itemValue??>
<#assign valus = parameters.itemValue/>
</#if>
<#if itemValue??>
<#assign valus = itemValue/>
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


