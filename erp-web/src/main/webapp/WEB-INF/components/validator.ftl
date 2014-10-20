<#if parameters.validator??>
<#assign bean = parameters.validator/>
<#assign roleKey = bean.validatorValue/>
<#list roleKey?keys as v> 
   data-rule-${v}="${bean.validatorValue[v]}"<#rt/>
   <#if bean.message?size gt v_index>
     data-msg-${v}="${bean.message[v_index]}"<#rt/>
   </#if>
</#list>
</#if>