<#assign parameters = tag.parameters/>
<div id="attachmentInfo" style="width:540px;"></div>
<input type="button" id="browserButton" class="button" value="选择文件" />
<input type="hidden" id="${parameters.id}" name="${parameters.id}"/>
<script type="text/javascript"><#rt/>
  var $browserButton = $("#browserButton");
  $browserButton.unbind('click'); 
  $browserButton.click(function() {
	var win = jQuery.createWin({
	     title: "文件上转",
	     width:${parameters.width?default("500")},
	     height:${parameters.height?default("400")},
	     buttons:[
	          {text:'确定',handler:function(){
		     d = document.frames['windowFrames'];
		     var in_content = d.$("#att-status").html();
		     var del_content = d.$("#att-status-del").html();
		     $("#${parameters.id}").val(in_content);
             var array = in_content.split('|');
		     <#if parameters.fileText?? && parameters.fileId?? >
             for(var i=1;i<array.length;i++){
                     var splitArr = array[i].split('@');
                     <#if parameters.fileprefix??>
                        var suffix = splitArr[0].substring(splitArr[0].lastIndexOf('.')+1).toLowerCase();
                        var curFileId = "liFile" + ($("#${parameters.fileprefix?html} li").size() + 1);
						if(suffix == 'jpg' || suffix == 'jpeg' || suffix == 'png' || suffix == 'gif' || suffix == 'bmp'){
							$("#${parameters.fileprefix?html}").append("<li id=\""+curFileId+"\"><img src=\"${base}"+splitArr[0]+"\" /><a  href=\"javascript:void(0)\" onclick=\"delImage('"+curFileId+"','"+splitArr[0]+"',false)\"> 删除 </a><br/><input name=\"${parameters.fileText}\" id=\"${parameters.fileText}\" type=\"text\"  value=\""+(splitArr.length > 1 ? splitArr[1] : "")+"\" class=\"txt\"><input name=\"${parameters.fileId}\" id=\"${parameters.fileId}\" type=\"hidden\" value=\""+splitArr[0]+"\"></li>");
						}else{
							$("#${parameters.fileprefix?html}").append("<li id=\""+curFileId+"\"><img src=\"${base}resource/images/ext/"+suffix+".png\" /><a  href=\"javascript:void(0)\" onclick=\"delImage('"+curFileId+"','"+splitArr[0]+"',false)\"> 删除 </a><br/><input name=\"${parameters.fileText}\" id=\"${parameters.fileText}\" type=\"text\"  value=\""+(splitArr.length > 1 ? splitArr[1] : "")+"\" class=\"txt\"><input name=\"${parameters.fileId}\" id=\"${parameters.fileId}\" type=\"hidden\" value=\""+splitArr[0]+"\"></li>");
						}
                     <#else>
				       input = $('<label>文件描述:</label><input name="${parameters.fileText}" id="${parameters.fileText}" type="text"  value="'+(splitArr.length > 1 ? splitArr[1] : "")+'" class="txt"  style="width:200px;">&nbsp;&nbsp;<label>文件路径:</label><input name="${parameters.fileId}" id="${parameters.fileId}" class="txt"  style="width:200px;"  value="'+splitArr[0]+'">');
				       $('#attachmentInfo').append(input);
		       		 </#if>
		     }
		     </#if>
		     <#if parameters.fileprefix??>
		     slider_load();
		     </#if>
		     win.dialog("close");
		  }}
	     ],   
	     url:'${base}/attachment/swfupload.tpl.jsp?fileType=${parameters.fileType?default("image")}'
	 }); 
  });
</script>
