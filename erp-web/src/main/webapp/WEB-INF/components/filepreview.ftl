<#assign parameters = tag.parameters/>
<#if parameters.id??>
<link rel="stylesheet" type="text/css" href="${base}resource/css/jquery-foxibox-0.2.css" />
<script language="JavaScript" src="${base}resource/js/jquery-foxibox-0.2.min.js"></script>
<style type="text/css">
/* sliderbox */
.sliderbox{background:url(${base}resource/images/index-bottom.jpg) no-repeat 0 -1px ;background-size:contain;width:500px;overflow:hidden;padding:10px 0 0 0;margin:10px auto;}
.arrow-btn{margin:-10px 0 0 0;display:inline;background:url(${base}resource/images/arrow-btn.png) no-repeat;width:19px;height:90px;overflow:hidden;cursor:pointer;}
#btn-left{float:left;margin-left:12px;background-position:0 0;}
#btn-left.dasabled{background-position:0 -90px;}
#btn-right{float:right;margin-right:6px;cursor:pointer;background-position:-19px 0;}
#btn-right.dasabled{background-position:-19px -90px;}
.slider{width:420px;overflow:hidden;position:relative;height:90px;float:left;}
.slider ul{position:absolute;left:0px;top:-10px;width:300px;height:90px;list-style:none;}
.slider li{float:left;width:150px;height:60px;}
.slider img{width:60px;height:60px;display:inline-block!important;}
</style>
<div class="sliderbox">
  <div id="btn-left" class="arrow-btn dasabled"></div>
		<div class="slider">
		  <ul id="${parameters.type?html}">
          </ul>
        </div>
       	<div id="btn-right" class="arrow-btn">
     </div>
</div>
<script type="text/javascript">

var $slider = $('.slider ul');
var $slider_child_l = $('.slider ul li').length;
var $slider_width = $('.slider ul li').width();
$(".sliderbox").css("width",((${parameters.count?default(3)} * 150 + 50) + "px"));
$(".slider").css("width",((${parameters.count?default(3)} * 150 - 30) + "px"));

function slider_load(){
	$slider_child_l = $('.slider ul li').length;
	$slider_width = $('.slider ul li').width();
	$slider.width($slider_child_l * $slider_width);
}

var slider_count = 0;

if ($slider_child_l > ${parameters.count?default(3)}) {
	$('#btn-right').css({cursor: 'auto'});
	$('#btn-right').removeClass("dasabled");
}

$('#btn-right').click(function() {
	if ($slider_child_l <= ${parameters.count?default(3)} || slider_count >= $slider_child_l - ${parameters.count?default(3)}) {
		return false;
	}
	
	slider_count++;
	$slider.animate({left: '-=' + $slider_width + 'px'}, 'slow');
	slider_pic();
});

$('#btn-left').click(function() {
	if (slider_count <= 0) {
		return false;
	}
	
	slider_count--;
	$slider.animate({left: '+=' + $slider_width + 'px'}, 'slow');
	slider_pic();
});

function slider_pic() {
	if (slider_count > 0 && slider_count >= $slider_child_l - ${parameters.count?default(3)}) {
		$('#btn-right').css({cursor: 'auto'});
		$('#btn-right').addClass("dasabled");
		$('#btn-left').css({cursor: 'pointer'});
		$('#btn-left').removeClass("dasabled");
	}
	else if (slider_count > 0 && slider_count <= $slider_child_l - ${parameters.count?default(3)}) {
		$('#btn-left').css({cursor: 'pointer'});
		$('#btn-left').removeClass("dasabled");
		$('#btn-right').css({cursor: 'pointer'});
		$('#btn-right').removeClass("dasabled");
	}
	else if (slider_count <= 0) {
		$('#btn-left').css({cursor: 'auto'});
		$('#btn-left').addClass("dasabled");
		$('#btn-right').css({cursor: 'pointer'});
		$('#btn-right').removeClass("dasabled");
	}
}

<#if parameters.type??>
function delImage(fileid,path,isExists){
	if(isExists){
	$.ajax({
		type:"post",
		dataType: "json",
		data:"id="+fileid+"&path="+path,
		url:${base}+"admin/invokeMethod/otherFileServiceImpl/removeOtherFiles.action",
		success:function(data){
			if(data.status!='error'){
				$("#${parameters.type?html} li[id="+fileid+"]").remove();
				slider_load();
			}
		},
		error:function(){
			
		}
	});
	}else{
		$.ajax({
			type:"post",
			data:"src="+path,
			url:${base}+"admin/deleteUploadFile.action",
			success:function(data){
				$("#${parameters.type?html} li[id="+fileid+"]").remove();
				slider_load();
			},
			error:function(){
				
			}
		});
	}
}
</#if>

 <#if inputValue??>
window.onload = function(){
	$.ajax({
		type:"post",
		dataType: "json",
		data:"otherid=${inputValue?html}&type=${parameters.type?html}",
		url:${base}+"admin/invokeMethod/otherFileServiceImpl/queryOtherFiles.action",
		success:function(data){
			if(data.status!='error'){
				var result = (new Function("return " + data.message))();
				for(var i = 0; i < result.length; i++){
					//$("#"+divCoun).append("<a href=\"javascript:void(0)\" dbId="+result[i].id+"  onclick=\"loadAreaCunInfo(this,"+result[i].id+",'divAreaCoun','hiAreaCoun','divAreaCun','spanXiang','"+ result[i].name+"','spanCun');\">" + result[i].name +"</a>&nbsp;");
					if(result[i].path != null && result[i].path != ''){
						var suffix = result[i].suffix.toLowerCase();
						if(suffix == 'jpg' || suffix == 'jpeg' || suffix == 'png' || suffix == 'gif' || suffix == 'bmp'){
							//$("#${parameters.type?html}").append("<li id=\""+result[i].id+"\"><img src=\"${base}"+result[i].path+"\" /><a  href=\"javascript:void(0)\" onclick=\"delImage('"+result[i].id+"','"+result[i].path+"',true)\"> 删除 </a><br/><a target=\"_blank\" href=\"${base}admin/filedown.action?path="+encodeURI(result[i].path)+"&name="+encodeURI(result[i].name + "." + suffix) +"\">"+result[i].name+"</a><input name=\"${parameters.fileText}\" id=\"${parameters.fileText}\" type=\"hidden\" value=\"'+result[i].name+'\"><input name=\"${parameters.fileId}\" id=\"${parameters.fileId}\" type=\"hidden\" value=\"'+result[i].path+'\"></li>");
							$("#${parameters.type?html}").append("<li id=\""+result[i].id+"\"><a href=\"${base}"+result[i].path+"\"  rel=\"[gall2]\"><img src=\"${base}"+result[i].path+"\" /></a><a  href=\"javascript:void(0)\" onclick=\"delImage('"+result[i].id+"','"+result[i].path+"',true)\"> 删除 </a><br/><a target=\"_blank\" href=\"${base}admin/filedown.action?id="+result[i].id+"\">"+result[i].name+"</a><input name=\"${parameters.fileText}\" id=\"${parameters.fileText}\" type=\"hidden\" value=\""+result[i].name+"\"><input name=\"${parameters.fileId}\" id=\"${parameters.fileId}\" type=\"hidden\" value=\""+result[i].path+"\"></li>");
						}else{
							//$("#${parameters.type?html}").append("<li id=\""+result[i].id+"\"><img src=\"${base}resource/images/ext/"+suffix+".png\" /><a  href=\"javascript:void(0)\" onclick=\"delImage('"+result[i].id+"','"+result[i].path+"',true)\"> 删除 </a><br/><a target=\"_blank\" href=\"${base}admin/filedown.action?path="+encodeURI(result[i].path)+"&name="+encodeURI(result[i].name + "." + suffix)+"\">"+result[i].name+"</a><input name=\"${parameters.fileText}\" id=\"${parameters.fileText}\" type=\"hidden\" value=\"'+result[i].name+'\"><input name=\"${parameters.fileId}\" id=\"${parameters.fileId}\" type=\"hidden\" value=\"'+result[i].path+'\"></li>");
							$("#${parameters.type?html}").append("<li id=\""+result[i].id+"\"><img src=\"${base}resource/images/ext/"+suffix+".png\" /><a  href=\"javascript:void(0)\" onclick=\"delImage('"+result[i].id+"','"+result[i].path+"',true)\"> 删除 </a><br/><a target=\"_blank\" href=\"${base}admin/filedown.action?id="+result[i].id+"\">"+result[i].name+"</a><input name=\"${parameters.fileText}\" id=\"${parameters.fileText}\" type=\"hidden\" value=\""+result[i].name+"\"><input name=\"${parameters.fileId}\" id=\"${parameters.fileId}\" type=\"hidden\" value=\""+result[i].path+"\"></li>");
						}
					}
				}
				slider_load();
				$('#${parameters.type?html} li a[rel]').foxibox();
			}
		},
		error:function(){
			
		}
	});
}
</#if>
</script>
</#if>