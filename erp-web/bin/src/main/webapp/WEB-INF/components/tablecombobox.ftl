<#assign parameters = tag.parameters/>
<input name="${tag.id?default("")?html}"<#rt/> 
<#if parameters.id??>
id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.width??>
  style="width:${parameters.width?html}px;"<#rt/>
</#if>
<#if inputValue??>
 value="${inputValue?html}"<#rt/>
<#else>
 value="${parameters.value?default("")}" <#rt/>
</#if>
 data-options="url:'${base+url?trim}',
   panelWidth:${parameters.panelWidth?html},   
   idField:'${parameters.idField?html}',   
   textField:'${parameters.textField?html}', 
   columns:[[${parameters.columns?html}]]"<#rt/>
/><#rt/>
<script type="text/javascript"><#rt/>
$(function(){<#rt/>
    $('#${parameters.id?html}').combogrid(); <#rt/>
});</script>

