<style type="text/css">
#${tag.id?default("silder")}-divId {float: left;clear: left;width: 200px;margin: 2px;}
#${tag.id?default("silder")}-divId .ui-slider-range { background: #8ae234; }
#${tag.id?default("silder")}-divId .ui-slider-handle { border-color: #8ae234; }
.slider-tip {
  position: absolute;
  display: inline-block;
  line-height: 12px;
  font-size: 12px;
  white-space: nowrap;
  top: -22px;
}
</style>
 <#assign parameters = tag.parameters/>
 <div id="${tag.id?default("silder")}-divId"></div>
 <div id="result"></div> 
 <input  type="hidden"<#rt/>
         name="${tag.id?default("")?html}"<#rt/>
	<#if parameters.id??>
	 id="${parameters.id?html}"<#rt/>
	</#if>
	<#if inputValue??>
	 value="${inputValue?html}"<#rt/>
	<#else>
	<#if parameters.value??>
	 value="${parameters.value?html}" <#rt/>
	</#if>
	</#if>
 />
<script type="text/javascript"><#rt/>
$(function(){
   <#if inputValue??>
      var inputValue = ${inputValue?html};<#rt/>
   <#else>
      var inputValue = ${parameters.value?default("0")}; <#rt/>
  </#if>
   var slider = $( "#${tag.id?default("silder")}-divId" ).slider({
	 orientation:"${parameters.orientation?default("horizontal")}",
	 range: "min",
	 max: ${parameters.max?default("100")},
	 value:inputValue,
	 labels:2,
	 slide: refreshSwatch,
	 change: refreshSwatch
   });
   function refreshSwatch(event, ui){
        var value = $(this).slider('value');
	slider.find("a").text(value);
	$('#${tag.id?default("")?html}').val(value);
   }
   
   slider.find("a").text(inputValue);        

});
</script>
