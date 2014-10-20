<#assign parameters = tag.parameters/>
<textarea name="${tag.id?default("")?html}"   class="span12"<#rt/>
cols="${parameters.cols?default("10")?html}"<#rt/>
rows="${parameters.rows?default("20")?html}"<#rt/>
<#if parameters.wrap??>
wrap="${parameters.wrap?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id??>
id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.title??>
title="${parameters.title?html}"<#rt/>
</#if>
<#include "/scripting-events.ftl" />
><#rt/>
<#if inputValue??>
${inputValue?html}<#rt/>
<#else>
<#if parameters.value??>
 ${parameters.value?html} <#rt/>
</#if>
</#if>
</textarea>