<#assign parameters = tag.parameters/>
<span class="type-file-box">
<input type="file" class="type-file-file"  hidefocus="true"<#rt/>
name="${tag.id?default("")?html}"<#rt/>
<#if parameters.id??>
id="${parameters.id?html}"<#rt/> 
</#if> 
<#if parameters.size??>
size="${parameters.size?html}"<#rt/>
</#if> 
 <#if parameters.width??>
 width = "${parameters.width?html}"<#rt/>
</#if>
>
</span>
</td>
<label for="${tag.id?default("")?html}"></label>

