<#assign parameters = tag.parameters/>
     <input type="hidden"<#rt/>
         name="${tag.id?default("")?html}"<#rt/>
	<#if parameters.id??>
	 id="${parameters.id?html}"<#rt/>
	</#if>
	<#if inputValue??>
		  value="${inputValue?html}"<#rt/>
        <#else>
	  <#if parameters.value??>
	     value="${parameters.value?default("")}" <#rt/>
          </#if>
          <#if parameters.currentUser??>
	     value="${userInfoMethod(parameters.currentUser)}" <#rt/>
	  </#if>
	  </#if>
     />

