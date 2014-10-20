<script type="text/javascript" src="${base}resource/ckeditor/ckeditor.js"></script>
<#include "textarea.ftl"/>
<script type="text/javascript"><#rt/>
 $(document).ready(function () {
 CKEDITOR.replace( '${tag.id?default("")?html}',{width: ${parameters.width?default("10")?html}, height:${parameters.height?default("10")?html},pages:true,textareaid:'${tag.id?default("")?html}'
                       ,module:'${tag.id?default("")?html}',catid:'6',
			flashupload:true,alowuploadexts:'',allowbrowser:'1',allowuploadnum:'10',
			toolbar :[
				 ['Source','-','Templates'],
				 ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print'],
				 ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],['ShowBlocks'],['Maximize'],
				 '/',
				 ['Bold','Italic','Underline','Strike','-'],
				 ['Subscript','Superscript','-'],
				 ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
				 ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
				 ['Link','Unlink','Anchor'],
				 ['Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
				 '/',
				['Styles','Format','Font','FontSize'],
				['TextColor','BGColor'],
				['attachment']
			]
	    });
});
</script>