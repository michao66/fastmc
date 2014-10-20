<#assign parameters = tag.parameters/>
<select name="${tag.id?default("")?html}"<#rt/>
<#if parameters.id??>
id="${parameters.id?html}"<#rt/>
</#if>
 <#if parameters.width??>
  style="width:${parameters.width?html}px;"<#rt/>
 </#if>
 <#rt/>
><#rt/>
</select>
<label for="${tag.id?default("")?html}"></label>

<script type="text/javascript"><#rt/>
 $(document).ready(function () {
 $('#${parameters.id?html}').combotree({
        url:'${base}${parameters.url}&id=0'
       ,onBeforeLoad:function(row,param){
		 if (row){
		   $(this).tree('options').url = '${base}${parameters.url}&id='+row.id
		 }else{
		   $(this).tree('options').url = '${base}${parameters.url}&id=${parameters.pid}'
		 }
       }
       <#if parameters.getCondition??>
       ,onSelect:function(record){
			if(record.attributes.${parameters.getCondition}){
				 $('#${parameters.id?html}').combotree('clear');
				 <#if parameters.selectFiled??>
					$("#${parameters.selectFiled?html}").val("");
				</#if>
			}
			<#if parameters.selectFiled??>
			else{
				$("#${parameters.selectFiled?html}").val(<#if parameters.valueFiled??>record.attributes.${parameters.valueFiled?html}<#else>record.id</#if>);
			}
			</#if>
		}
		</#if>

            <#if parameters.isLeaf??>
          ,onSelect:function(record){
	                var isleaf = $(this).tree('isLeaf', record.target);
			if(!isleaf){
				 $('#${parameters.id?html}').combotree('clear');
				 <#if parameters.selectFiled??>
					$("#${parameters.selectFiled?html}").val("");
				</#if>
			}
			<#if parameters.selectFiled??>
			else{
				$("#${parameters.selectFiled?html}").val(<#if parameters.valueFiled??>record.attributes.${parameters.valueFiled?html}<#else>record.id</#if>);
			}
			</#if>
		}
		</#if>   
       });
 
 <#if inputValue??>
 $('#${parameters.id?html}').combotree('setValue', '${inputValue?html}');
 </#if>
 });
</script>
