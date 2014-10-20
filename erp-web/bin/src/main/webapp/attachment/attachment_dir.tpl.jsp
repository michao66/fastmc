<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.mich.erp.core.utils.FunctionUtils"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.lang.StringUtils;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<link href="<%=request.getContextPath()%>/resource/swfupload/swfupload.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/jquery-easyui-1.3/jquery-1.7.2.min.js" ></script>
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/resource/swfupload/swfupload.js"></script>
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/resource/swfupload/fileprogress.js"></script>
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/resource/swfupload/handlers.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/resource/js/jquery.imgpreview.js"></script>
<style type="text/css">
html{_overflow-y:scroll}
.table-list td,.table-list th{padding-left:12px}
.table-list thead th{ height:30px; background:#eef3f7; border-bottom:1px solid #d5dfe8; font-weight:normal}
.table-list tbody td,.table-list .btn{border-bottom: #eee 1px solid; padding-top:5px; padding-bottom:5px}
.table-list tr:hover,.table-list table tbody tr:hover{ background:#fbffe4}
.table-list .input-text-c{ padding:0; height:18px}
.td-line{border:1px solid #eee}
.td-line td,.td-line th{border:1px solid #eee}
.table-list tr.on,.table-list tr.on td,.table-list tr.on th,.table-list td.on,.table-list th.on{background:#fdf9e5;}
.pad-lr-10{padding:0 10px}.pad-lr-6{padding:0 6px}
.img-wrap{border:1px solid #eee;text-align:center;overflow:hidden;background:#fff}
.img-wrap a{display:table-cell;vertical-align:middle;text-align:center;*display:block;}

#imgPreview td.on{background:#f4f9fe}
#imgPreview td.on a{background:url("<%=request.getContextPath()%>/resource/images/msg_bg.png") no-repeat right -250px;vertical-align:middle; padding-right:70px;display:inline-block;display:-moz-inline-stack;zoom:1;*display:inline; height:16px; line-height:16px}
</style>
</head>
<body>	

<script type="text/javascript">
	$(document).ready(function(){
		var obj=$("#imgPreview a[rel]");
		if(obj.length>0) {
			$('#imgPreview a[rel]').imgPreview({
				srcAttr: 'rel',
			    imgCSS: { width: 200 }
			});
		}
	});	
</script>
<div class="pad-lr-10">
<div class="table-list">
<% 
    String scheme = request.getScheme(); 
    String serverName = request.getServerName(); 
    int serverPort = request.getServerPort();        
    String contextPath = request.getContextPath(); 
	//scheme+"://"+serverName+":"+
    String url = serverPort+contextPath;
   
   String currentDir = StringUtils.isEmpty(request.getParameter("dir")) ?"uploaderDir":request.getParameter("dir") ;
   if(currentDir.indexOf("uploaderDir")<0){
      currentDir = "uploaderDir"+File.separator+currentDir;
   }
   String rootDir  =  request.getRealPath("/")+File.separator+currentDir; 
   File[] files = FunctionUtils.getDirFileList(rootDir);
   String parentDir = null;
   int index = currentDir.lastIndexOf(File.separator);
   if(index>=0){
     parentDir = currentDir.substring(0,index);

   }
%>
<table width="100%" cellspacing="0" id="imgPreview" >
<tr>
<td align="left">当前目录：<%=currentDir %></td>
</tr>
<% if (StringUtils.isNotEmpty(parentDir)){%>
<tr>
<td align="left"><a href="?dir=<%=parentDir %>"><img src="<%=request.getContextPath()%>/resource/images/folder-closed.gif" />上一级目录</a></td>
</tr>
<%}%>
<% for(File file : files ) {
     if(file.isDirectory()){
 %>
<tr>
<td align="left"><img src="<%=request.getContextPath()%>/resource/images/folder-closed.gif" /> <a href="?dir=<%=currentDir+File.separator+file.getName()%>"><b><%=file.getName()%></b></a></td>
</tr>
<%}else{
%>
<tr>
 <td align="left" onclick="javascript:album_cancel(this)"><img src=<%=request.getContextPath()+FunctionUtils.getFileIcon(file.getName())%> /> <a href="javascript:;" rel="<%=contextPath+"/"+currentDir.replaceAll("\\\\","/")+"/"+file.getName()%>" title="<%=file.getName()%>"><%=file.getName()%></a></td>
 </tr> 
<%
}}%>
</table>
</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	set_status_empty();
});	
function set_status_empty(){
	parent.window.$('#att-status').html('');
	parent.window.$('#att-name').html('');
}
function album_cancel(obj){
	var src = $(obj).children("a").attr("rel");
	var filename = $(obj).children("a").attr("title");
	if($(obj).hasClass('on')){
		$(obj).removeClass("on");
		var imgstr = parent.window.$("#att-status").html();
		var length = $("a[class='on']").children("a").length;
		var strs = filenames = '';
		for(var i=0;i<length;i++){
			strs += '|'+$("a[class='on']").children("a").eq(i).attr('rel');
			filenames += '|'+$("a[class='on']").children("a").eq(i).attr('title');
		}
		parent.window.$('#att-status').html(strs);
		parent.window.$('#att-name').html(filenames);
	} else {
		var num = parent.window.$('#att-status').html().split('|').length;
		var file_upload_limit = '10';
		$(obj).addClass("on");
		parent.window.$('#att-status').append('|'+src);
		parent.window.$('#att-name').append('|'+filename);
	}
}
</script>
</html>