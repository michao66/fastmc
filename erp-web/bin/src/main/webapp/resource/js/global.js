var Global = function() {
	return {
		init : function() {
			
			$(window).resize(function() {
				//var c = $(".easyui-datagrid");
				//$.each(c, function(){
					
			
					//d.datagrid('resize',{width: 600,height: 400})
				//});
			});
			$("#themeList").theme({themeBase:"/resource/plugins/easyui/themes"});

			$("#a-logout").click(function() {
               $.messager.confirm('提示', "确认注销登录吗？", function(r){
					if (r){
						window.location.href = "j_spring_security_logout"
					}
				});
			});

		},
		notify : function(a, b) { //b 'error' 'info' 'question' 'warning'
			if(b == '' || b == null){
               b = 'info'
			}
			jQuery.messager.alert('提示',a,b);
		},
        popWin:function(wtitle,wurl,wwidth,wheight,wcloseFunction){
	         jQuery.createWin({title:wtitle,url:wurl,height:wheight,width:wwidth,onAjaxComplete:wcloseFunction});
        },
		scrollTo : function(M, L) {
			pos = (M && M.size() > 0) ? M.offset().top : 0;
			jQuery("html,body").animate({
				scrollTop : pos + (L ? L : 0)
			}, "slow")
		},
		scrollTop : function() {
			Global.scrollTo()
		},
		blockUI : function(L, M) {
			var L = jQuery(L);
			if (L.height() <= 400) {
				M = true
			}
			L.block({
				message : '<img src="' + WEB_ROOT
						+ '/assets/img/ajax-loading.gif" align="">',
				centerY : M != undefined ? M : true,
				css : {
					top : "10%",
					border : "none",
					padding : "2px",
					backgroundColor : "none"
				},
				overlayCSS : {
					backgroundColor : "#000",
					opacity : 0.05,
					cursor : "wait"
				}
			})
		},
		unblockUI : function(M, L) {
			jQuery(M).unblock({
				onUnblock : function() {
					jQuery(M).css("position", "");
					jQuery(M).css("zoom", "")
				}
			})
		}
	}
}();