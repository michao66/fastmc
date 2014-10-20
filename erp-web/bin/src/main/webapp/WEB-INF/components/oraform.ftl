<form method="post" <#rt/>
<#if tag.parameters.entity??>
   entity="${tag.parameters.entity}"<#rt/>
</#if>  id="${tag.id}" <#rt/>
action="${base}${formUrl?html}">
