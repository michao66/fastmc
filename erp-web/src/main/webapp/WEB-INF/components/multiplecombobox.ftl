<#assign parameters = tag.parameters/>
<#if parameters.itemValue??>
<#assign valus = parameters.itemValue/>
</#if>
<#if itemValue??>
<#assign valus = itemValue/>
</#if>
<select name="${tag.id?default("")?html}" id="${tag.id?default("")?html}" data-options="width:${parameters.width?default("150")}"></select>
<div id="optionDiv">
<#list valus?keys as v>
<input type="checkbox" name="checkedOption" value="${v}"><span>${valus[v]}</span><br/>
</#list>
</div>
<script type="text/javascript">
$(function(){

$('#${tag.id?default("")?html}').combo({
   required:true,
   editable:true,
   multiple:true
});
$('#optionDiv').appendTo($('#${tag.id?default("")?html}').combo('panel'));
$("#optionDiv input").click(function(){
    selectMultipleValue();
});
function selectMultipleValue(){
  var _value="";
  var _text="";
  $("[name=checkedOption]:input:checked").each(function(){
	_value+=$(this).val()+",";
	_text+=$(this).next("span").text()+",";
  });
  if(_value.length>0){_value = _value.substring(0,_value.length-1);}
  if(_text.length>0){_text = _text.substring(0,_text.length-1);}
  $('#${tag.id?default("")?html}').combo('setValue', _value).combo('setText', _text);
}
 <#if inputValue??>
    var inputValue = "${inputValue}".split(",");
    for(var i=0;i<inputValue.length;i++){
        var data = inputValue[i];
       $("input[type='checkbox'][name='checkedOption']").each(function(){
         if($(this).val() == data){
	   this.checked = "checked"; 
	 }
      });
      selectMultipleValue()
    }
 </#if>
});
</script>