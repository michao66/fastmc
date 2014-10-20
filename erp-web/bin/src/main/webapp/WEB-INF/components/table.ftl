<#assign parameters = tag.parameters/>
<#assign page = tag.pager/>
<table id="${tag.id?default("tableId")}"  class="table  table-bordered table-hover" width="100%" layoutH="138">
<thead><#rt/>
<#list tag.heads as bean>
<#if tag.multiSelect &&  bean.type =='check'>
<th  width="22"><#rt/>
 <input type="checkbox" group="ids" ><#rt/>
</th>
<#else>
<th width="${bean.width}" name="${bean.field}" <#rt/>
class="sorting <#if page.orderProperty == bean.field>${page.orderDirection}</#if>" 
<#rt/>>
  ${bean.title?html}<#rt/> 
</th>
</#if>
</#list>
</tr>
</thead>
<tbody>
<#if (parameters.rowIterator?if_exists?size > 0)>
<#list parameters.rowIterator as currows>
<tr>
<#list currows as currow>
<td>${currow}</td>
</#list>
</tr>
</#list>
</#if>
</tbody>
</table>
<#if (parameters.rowIterator?size > 0)>
<@pagination pageNumber = page.pageNumber totalPages = page.totalPages>
 <#include "pager.ftl"/>
</@pagination>
<#else>
<div class="noRecord">
没有找到任何记录!
</div>
</#if>

