<#assign parameters = tag.parameters/>
<input  name="${tag.id?default("")?html}" id="${tag.id?default("")?html}"<#rt/>
<#if parameters.width??>
style="width:${parameters.width?html}px;"<#rt/>
</#if>
<#if inputValue??>
 value="${inputValue?html}"<#rt/>
</#if>
>
<script type="text/javascript"><#rt/>
$('#${tag.id}').timespinner({ 
 <#if parameters.min??>
    min: '${parameters.min}', 
 </#if> 
  <#if parameters.max??>
     max: '${parameters.max}', 
  </#if>
  <#if parameters.increment??>
     increment: '${parameters.increment}', 
  </#if>
  showSeconds: false  
});  
</script>
