
var Global = function() {
	return {
		init : function() {
			
			$(window).resize(function() {
				//var c = $(".easyui-datagrid");
				//$.each(c, function(){
					
			
					//d.datagrid('resize',{width: 600,height: 400})
				//});
			});

			$.extend($.fn.datagrid.defaults, {
				method:"GET"
			});
/*
            // 令牌
			$(document).ajaxSend(function(event, request, settings) {
				if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
					var token = Global.getCookie("spring.token");
					alert(token);
					if (token != null) {
						request.setRequestHeader("spring.token", token);
					}
				}
			});

            $(document).ajaxComplete(function(event, request, settings) {
					var loginStatus = request.getResponseHeader("loginStatus");
					var tokenStatus = request.getResponseHeader("tokenStatus");
					
					if (loginStatus == "accessDenied") {
						$.message("warn", "www");
						window.setTimeout(function() {
							window.location.reload(true);
						}, 2000);
					} else if (tokenStatus == "accessDenied") {
						var token = Global.getCookie("spring.token");
						if (token != null) {
							$.extend(settings, {
								global: false,
								headers: {token: token}
							});
							$.ajax(settings);
						}
					}

	        }); 

			// 令牌
			$("form").submit(function() {
		
				var $this = $(this);
				if ($this.attr("method") != null && $this.attr("method").toLowerCase() == "post" && $this.find("input[name='token']").size() == 0) {
					var token = getCookie("token");
					if (token != null) {
						$this.append('<input type="hidden" name="token" value="' + token + '" \/>');
					}
				}
			});

*/
           $("#a_fullscreen").click(function() {

				if (!document.fullscreenElement && !document.mozFullScreenElement && !document.webkitFullscreenElement) {
					if (document.documentElement.requestFullscreen) {
						document.documentElement.requestFullscreen()
					} else {
						if (document.documentElement.mozRequestFullScreen) {
							document.documentElement.mozRequestFullScreen()
						} else {
							if (document.documentElement.webkitRequestFullscreen) {
								document.documentElement
										.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT)
							}
						}
					}
				} else {
					if (document.cancelFullScreen) {
						document.cancelFullScreen()
					} else {
						if (document.mozCancelFullScreen) {
							document.mozCancelFullScreen()
						} else {
							if (document.webkitCancelFullScreen) {
								document.webkitCancelFullScreen()
							}
						}
					}
				}
			});
           $("#a_passwd").click(function() {
        	   Global.popWin("修改密码",WEB_ROOT+"/admin/profile/passwd.action",400,300,null);
           });
           
           $("#a-lock-screen").click(function() {				 
				  $.backstretch([WEB_ROOT+"/resource/img/bg/1.jpg",
				                 WEB_ROOT+"/resource/img/bg/2.jpg",
				                 WEB_ROOT+"/resource/img/bg/3.jpg",
				                 WEB_ROOT+"/resource/img/bg/4.jpg"], {
								fade : 1000,
								duration : 8000
							});
					$(".layout-panel").hide();		
					$("#form-unlock").find(":text").focus().val("");
					$("#page-lock").show();
					$("#form-unlock").find("input").first().focus();
					
					$("body").ajaxPostURL({
						url : WEB_ROOT + "/admin/lock.action",
						confirmMsg : false
					});
			});
             $("#form-unlock").submit(function(g) {
				g.preventDefault();
				$(this).ajaxPostForm({
							success : function() {
							
								$("body").data("backstretch").destroy();
								$("#page-lock").hide();
								$(".layout-panel").fadeIn(2000);
								$(this).find("input[name='password']").val("");
							},
							confirmMsg : false
						});
				return false
			});
			$("#themeList").theme({themeBase:WEB_ROOT+"/resource/plugins/easyui/themes"});

			$("#a-logout").click(function() {
               $.messager.confirm('提示', "确认注销登录吗？", function(r){
					if (r){
						window.location.href = WEB_ROOT+"/j_spring_security_logout"
					}
				});
			});
	        jQuery("body").on("click", 'button.btnLook',function(i) {
	              var h = $(this);
	              defaults={title:"选择窗口",width:600,height:400,dataGridId:undefined,idField:undefined,callfun:undefined};
	              var options = $.extend({},defaults,$.parser.parseOptions(this,["title","width","height","dataGridId","dataUrl","callfun","idField","target_to"]));
	              
	              if(!options.dataUrl){
	                   Global.notify("找到选择窗口的URL");
	                   return true;
	              }
	              Global.popWin(options.title,options.dataUrl,options.width,options.height,options.callfun);
	              	
				
			});	
			jQuery("body").on("click", 'a[data-toggle="win-edit"]',
				function(i) {
					i.preventDefault();
					var h = $(this);
	                defaults={title:"编辑窗口",width:600,height:400,dataGridId:undefined,idField:undefined,callfun:undefined,target_to:"win"};
	             
	                var options = $.extend({},defaults,$.parser.parseOptions(this,["title","width","height","dataGridId","dataUrl","callfun","idField","target_to"]));
	                if(options.dataGridId && options.idField){//为了区分新增或删除
	                	var selected = $('#'+options.dataGridId).datagrid("getSelected");
						if (!selected){
							Global.notify("请先选择相关信息");
							return null;
						}
						//var datagridOption = $('#'+options.dataGridId).datagrid("options");
						options.dataUrl = options.dataUrl+"?ID="+selected[options.idField]
						if(options.callfun == undefined) options.callfun = function(){$('#'+options.dataGridId).datagrid("reload");}
						
	                }
	               
	                if("win" == options.target_to){
	                   Global.popWin(options.title,options.dataUrl,options.width,options.height,options.callfun);		
	                }else if("tap" == options.target_to) {
	                   open1(options.title,options.dataUrl);
	                }else{
	                    window.location.href   = options.dataUrl;
	                }
						
				});
			jQuery("body").on("click", 'a[data-toggle="data-del"]',
				function(i) {	
					i.preventDefault();
					var h = $(this);
					dataGridId = (h.attr("dataGridId")?h.attr("dataGridId"):undefined);
					dataUrl = h.attr("dataUrl");
					if(dataGridId){
						var selected = $('#'+dataGridId).datagrid("getSelected");
						if (!selected){
							Global.notify("请先选择相关信息")
							return null;
						}
						var datagridOption = $('#'+dataGridId).datagrid("options");
						dataUrl = dataUrl+"?ID="+selected[datagridOption.idField];
						$.messager.confirm('提示', '您确认要删除该项吗?', function(r){
					            if(r){
					            	$.ajax({
							         url:dataUrl,
							         dataType: 'json',
									 success: function(data){
										if(data.status=='error'){
											Global.notify(data.message);
										}else{
											Global.notify('删除成功！');
										 	$('#'+dataGridId).datagrid("reload");
										}
									}
								});
					        }					       
					   });
					}
				}
			);
				
		},
		notify : function(a, b) { //b 'error' 'info' 'question' 'warning'
			if(b == '' || b == null){
               b = 'info'
			}
			jQuery.messager.alert('提示',a,b);
		},
		
		
        popWin:function(wtitle,url,wwidth,wheight,wcloseFunction){
			
			 
	         jQuery.createWin({title:wtitle,url:url,height:wheight,width:wwidth,onAjaxComplete:wcloseFunction});
        },

		// 获取Cookie
		 getCookie:function(name) {
			if (name != null) {
				var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
				return value ? decodeURIComponent(value[1]) : null;
			}
		},

		// 移除Cookie
		 removeCookie:function(name, options) {
			addCookie(name, null, options);
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
				message : '<img src="' + WEB_ROOT+ '/resource/img/ajax-loading.gif" align="">',
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

$.fn.ajaxPostForm = function(b) {
	var e = b.success;
	var a = b.failure;
	var d = b.confirmMsg;
	if (d) {
		if (!confirm(d)) {
			return false
		}
	}
	var b = $.extend({data : {}}, b);
	var c = $(this);
	Global.blockUI(c);
	c.ajaxSubmit({
		 dataType : "json",
		 method : "post",
		 success : function(f) {
				Global.unblockUI(c);
			if (f.status == "success") {
				if (e) {e.call(c, f)}
			} else {
				if (f.status == "failure"|| f.status == "error") {
					jQuery.messager.show({title:'提示',msg:f.message});
					if (a) {a.call(c, f)}
				} else {
					jQuery.messager.show({title:'提示',msg:"表单处理异常，请联系管理员"});
					if (a) {a.call(c, f)}
				}
			}
		 },
		 error : function(j, h, f) {
			 Global.unblockUI(c);
			 var g = jQuery.parseJSON(j.responseText);
			 if (g.status == "error") {
					jQuery.messager.show({title:'提示',msg:g.message});
			 } else {
					jQuery.messager.show({title:'提示',msg:"表单处理异常，请联系管理员"});
			}
			if (a) {
					a.call(c, g)
			}
		}
	})
};
		
		
$.fn.ajaxPostURL = function(b) {
	var a = b.url;
	var e = b.success;
	var d = b.confirmMsg;
	if (d == undefined) {d = "确认提交数据？"}
	if (d) {
		if (!confirm(d)) {return false}
	}
	var b = $.extend({data : {}}, b);
	var c = $(this);
	Global.blockUI(c);
	$.post(encodeURI(a), b.data, function(f, j) {
		Global.unblockUI(c);
		if (f.status == "success" || f.status == "warning") {
			jQuery.messager.show({title:'提示',msg:f.message});
				if (e) {
					e.call(c, f)
				}
		} else {
			jQuery.messager.show({title:'提示',msg:f.message});
			if (b.failure) {b.failure.call(c, f)}
		}
	}, "json")
};		