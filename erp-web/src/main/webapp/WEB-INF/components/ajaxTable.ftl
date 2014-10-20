<#assign parameters = tag.parameters/>
<table class="easyui-datagrid" pagination="true" id="${tag.id?html}" style="${tag.style?html}" url="${parameters.url?html}"
data-options="${tag.dataOptions?html}"
title="Editable DataGrid">
<thead>
<tr>
<#list tag.heads as bean>
<th data-options="field:'${bean.id}',width:80">${bean.title?html}</th>
</#list>
</tr>
</thead>
</table>