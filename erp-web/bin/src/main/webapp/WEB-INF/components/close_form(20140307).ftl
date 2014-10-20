<#if tag.haveSubmitBtn>
<tbody>
<tfoot>
    <tr>
        <td colspan="${tag.column*2}"><a href="JavaScript:void(0);" class="btn" id="submitBtn"><span>提交</span></a></td>
     </tr>
 </tfoot>
 </#if>
</table>
<div class="blank"></div><p id="ajaxStatus"></p><div class="blank"></div>
</form>
 <script type="text/javascript">
  <!--
 var options = {
	     target: '#${tag.id}',
	     dataType: "json",
	     beforeSubmit: function(data) {
		  $('#${tag.id}').mask();
	     },
	     success: function(data) {
			 if(data.status=='error'){
				 $('#${tag.id}').mask('hide');
                 parent.popMeages(data.message);
			 }else{
				  $('#${tag.id}').mask('hide');
				  parent.popMeages('操作成功。');
				  if(parent){
					 var d = parent.GETWIN($('#windowFrames',parent.document));
					 d.dialog("options").onAjaxComplete.call(this,d);
					 d.dialog("close");
				  }else{
					 var d = window.GETWIN($('#${tag.id}'));
					 d.dialog("options").onAjaxComplete.call(this,d);
					 d.dialog("close");
				  }
			 } 
	     }
	}
$(function(){ 
	$('#${tag.id}').validate({
		 errorElement: "div",
		 wrapper: "div",  // a wrapper around the error message	
                errorPlacement: function(error, element){
		        offset = element.offset();
		        error.appendTo( element.parent() );
			error.addClass('message');  // add a class to the wrapper
			error.css('left', offset.left + element.outerWidth());
		        error.css('top', offset.top+5);

		},	
		onfocusout : false,
		onkeyup    : false,
		rules : {
		  <#list tag.validators as bean>
		    <#assign roleKey = bean.validatorValue/>
                    <#if bean_index==0>
		      ${bean.id}:{<#rt/><#list roleKey?keys as v> <#if v_index==0> ${bean.validatorValue[v]} <#else>,${bean.validatorValue[v]}</#if></#list>}
		    <#else>
                     ,${bean.id}:{<#rt/><#list roleKey?keys as v> <#if v_index==0> ${bean.validatorValue[v]} <#else>,${bean.validatorValue[v]}</#if></#list>}
		    </#if>
                 </#list>
		},
		messages : {
		<#list tag.validators as bean>
		    <#assign roleKey = bean.validatorValue/>
		    <#if bean_index==0>
		      ${bean.id}:{<#rt/><#list roleKey?keys as v> <#if v_index==0> ${v}:'${bean.message[v_index]}' <#else> ,${v}:'${bean.message[v_index]}'</#if></#list>}
		    <#else>
		     ,${bean.id}:{<#rt/><#list roleKey?keys as v> <#if v_index==0> ${v}:'${bean.message[v_index]}' <#else> ,${v}:'${bean.message[v_index]}'</#if></#list>}
		    </#if>
                 </#list>
		}
	    });
	

      
	$('form').focus_first();
        $('#${tag.id}').bind('submit', function() {
		$(this).ajaxSubmit(options);
		return false; // <-- important!
	 });

 });
  //-->
 </script>