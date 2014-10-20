<#assign parameters = tag.parameters/>
<div class="${parameters.rowClass?default("span6")}">
<div class="control-group">
<label class="control-label">${tag.label?default("&nbsp;")}:</label>
<div class="controls">
 <#include "${tag.type}.ftl"/><#rt/><#if parameters.caption??>${parameters.caption}</#if><#rt/>
</div>
</div>
</div>




