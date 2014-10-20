
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="<%=request.getContextPath()%>/resource/swfupload/swfupload.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/jquery-easyui-1.3/jquery-1.7.2.min.js" ></script>
<style type="text/css">
	html{_overflow-y:scroll}
</style>
</head>
<body>	
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/resource/swfupload/swfupload.js"></script>
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/resource/swfupload/fileprogress.js"></script>
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/resource/swfupload/handlers.js"></script>
<script type="text/javascript">
        var swfu = '';
		$(document).ready(function(){
		swfu = new SWFUpload({
			flash_url:"<%=request.getContextPath()%>/resource/swfupload/swfupload.swf?"+Math.random(),
			upload_url:"<%=request.getContextPath()%>/admin/upload.action",
			file_post_name : "myUpload",
			post_params:{"SWFUPLOADSESSID":"1302078674","module":"content","catid":"6","userid":"1","siteid":"1","dosubmit":"1","thumb_width":"0","thumb_height":"0","watermark_enable":"1","filetype_post":"jpg|jpeg|gif|bmp|png|doc|docx|xls|xlsx|ppt|pptx|pdf|txt|rar|zip|swf","swf_auth_key":"492b14b7f553cfab917738f0409135d8","isadmin":"1","groupid":"8"},
			file_size_limit:"102400",
			file_types:"*.jpg;*.jpeg;*.gif;*.bmp;*.png",
			file_types_description:"All Files",
			file_upload_limit:"10",
			custom_settings : {progressTarget : "fsUploadProgress",cancelButtonId : "btnCancel"},
	 
			button_image_url: "",
			button_width: 75,
			button_height: 28,
			button_placeholder_id: "buttonPlaceHolder",
			button_text_style: "",
			button_text_top_padding: 3,
			button_text_left_padding: 12,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,

			file_dialog_start_handler:fileDialogStart,
			file_queued_handler:fileQueued,
			file_queue_error_handler:fileQueueError,
			file_dialog_complete_handler:fileDialogComplete,
			upload_progress_handler:uploadProgress,
			upload_error_handler:uploadError,
			upload_success_handler:uploadSuccess,
			upload_complete_handler:uploadComplete
			});
		})</script>
<div class="pad-10">
    <div class="col-tab">
        <ul class="tabBut cu-li">
            <li id="tab_swf_1"  class="on" onclick="SwapTab('swf','on','',5,1);">上传附件</li>
            <li id="tab_swf_2" onclick="SwapTab('swf','on','',5,2);">网络文件</li>
            <li id="tab_swf_4" onclick="SwapTab('swf','on','',5,4);set_iframe('album_dir','attachment_dir.tpl.jsp');">目录浏览</li>
         </ul>
         <div id="div_swf_1" class="content pad-10 ">
        	<div>
				<div class="addnew" id="addnew">
					<span id="buttonPlaceHolder"></span>
				</div>
				<input type="button" id="btupload" value="开始上传" onClick="swfu.startUpload();" />
                <div id="nameTip" class="onShow">最多上传<font color="red"> 10</font> 个附件,单文件最大 <font color="red">2 MB</font></div>
                <div class="bk3"></div>
				
                <div class="lh24">支持 <font style="font-family: Arial, Helvetica, sans-serif">jpg、jpeg、gif、bmp、png、doc、docx、xls、xlsx、ppt、pptx、pdf、txt、rar、zip、swf</font> 格式。</div><!-- <input type="checkbox" id="watermark_enable" value="1" checked onclick="change_params()"> 是否添加水印    -->     	</div> 	
    		<div class="bk10"></div>
        	<fieldset class="blue pad-10" id="swfupload">
        	<legend>列表</legend>
        	<ul class="attachment-list"  id="fsUploadProgress">    
        	</ul>
    		</fieldset>
    	</div>
        <div id="div_swf_2" class="contentList pad-10 hidden">
        <div class="bk10"></div>
        	请输入网络地址<div class="bk3"></div><input type="text" name="info[filename]" class="input-text" value=""  style="width:350px;"  onblur="addonlinefile(this)">
		<div class="bk10"></div>
        </div>    	
    	        <div id="div_swf_3" class="contentList pad-10 hidden">
            <ul class="attachment-list">
        	 <iframe name="album-list" src="#" frameborder="false" scrolling="no" style="overflow-x:hidden;border:none" width="100%" height="345" allowtransparency="true" id="album_list"></iframe>   
        	</ul>
        </div>
        <div id="div_swf_4" class="contentList pad-10 hidden">
            <ul class="attachment-list">
        	 <iframe name="album-dir" src="#" frameborder="false" scrolling="auto" style="overflow-x:hidden;border:none" width="100%" height="630" allowtransparency="true" id="album_dir"></iframe>   
        	</ul>
        </div>
                     
    <div id="att-status" class="hidden"></div>
     <div id="att-status-del" class="hidden"></div>
    <div id="att-name" class="hidden"></div>
<!-- swf -->
</div>
</body>
<script type="text/javascript">
function imgWrap(obj){
	$(obj).hasClass('on') ? $(obj).removeClass("on") : $(obj).addClass("on");
}

function SwapTab(name,cls_show,cls_hide,cnt,cur) {
    for(i=1;i<=cnt;i++){
		if(i==cur){
			 $('#div_'+name+'_'+i).show();
			 $('#tab_'+name+'_'+i).addClass(cls_show);
			 $('#tab_'+name+'_'+i).removeClass(cls_hide);
		}else{
			 $('#div_'+name+'_'+i).hide();
			 $('#tab_'+name+'_'+i).removeClass(cls_show);
			 $('#tab_'+name+'_'+i).addClass(cls_hide);
		}
	}
}

function addonlinefile(obj) {
	var strs = $(obj).val() ? '|'+ $(obj).val() :'';
	$('#att-status').html(strs);
}

function change_params(){
	if($('#watermark_enable').attr('checked')) {
		swfu.addPostParam('watermark_enable', '1');
	} else {
		swfu.removePostParam('watermark_enable');
	}
}
function set_iframe(id,src){
	$("#"+id).attr("src",src); 
}
function album_cancel(obj,id,source){
	var src = $(obj).children("img").attr("path");
	var filename = $(obj).attr('title');
	if($(obj).hasClass('on')){
		$(obj).removeClass("on");
		var imgstr = $("#att-status").html();
		var length = $("a[class='on']").children("img").length;
		var strs = filenames = '';
		$.get('index.php?m=attachment&c=attachments&a=swfupload_json_del&aid='+id+'&src='+source+'&filename='+filename);
		for(var i=0;i<length;i++){
			strs += '|'+$("a[class='on']").children("img").eq(i).attr('path');
			filenames += '|'+$("a[class='on']").children("img").eq(i).attr('title');
		}
		$('#att-status').html(strs);
		$('#att-status').html(filenames);
	} else {
		var num = $('#att-status').html().split('|').length;
		var file_upload_limit = '10';
		if(num > file_upload_limit) {alert('不能选择超过'+file_upload_limit+'个附件'); return false;}
		$(obj).addClass("on");
		$.get('index.php?m=attachment&c=attachments&a=swfupload_json&aid='+id+'&src='+source+'&filename='+filename);
		$('#att-status').append('|'+src);
		$('#att-name').append('|'+filename);
	}
}
</script>

</html>
