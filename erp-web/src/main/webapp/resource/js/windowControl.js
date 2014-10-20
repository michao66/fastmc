var groupWid = [];
(function ($) {
	function S4() {
		return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	}
	function CreateIndentityWindowId() {
		return "UUID-" + (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
	}
	function destroy(target) {
		$(target).dialog("destroy");
	}
	function getWindow() {
		lastIndex = groupWid.length;
		lastIndex = lastIndex>=1?lastIndex-1:lastIndex
		return $("#" + groupWid[lastIndex]).dialog()
	}
	function getWindowById(index){
	   return $("#" + groupWid[index]).dialog();
	}
	$.createWin = function (options) {
		if (!options.url && !options.contents) {
			$.messager.alert("提示", "缺少必要参数!(url or contents)");
			return false;
		}
		var windowId = CreateIndentityWindowId();
		groupWid.push(windowId);
	
		if (options.winId) {
			windowId = options.winId;
		} else {
			options.winId = windowId;
		}
		var defaultBtn = [{
				text : '关闭',
				handler : function () {
					$("#" + windowId).dialog("close");
				}
			}
		];
		$.merge(options.buttons || [], defaultBtn);
		options = $.extend({}, $.createWin.defaults, options || {});
		if (options.isMax) {
			options.draggable = false;
			options.closed = true;
		}
		var dialog = $('<div/>');
		if (options.target != 'body') {
			options.inline = true;
		}
		dialog.appendTo($(options.target));
		$(document).bind( "keydown", function( event ) {
			if(event.keyCode && event.keyCode === 27 ) {
						$("#" + windowId).dialog("close");
						event.preventDefault();
			 }
		});
		dialog.dialog($.extend({}, options, {
			    onOpen:function(){
					//var escTitle = $("<div style='float: right; padding: 2px 93px 0px px;'>\u6309Esc\u9000\u51fa</div>"); 
					//escTitle.appendTo(dialog.panel("header").find("div.panel-title"))
                    
				},
			    
			    onKeydown: function(evt) {
					if (evt.keyCode === $.ui.keyCode.ESCAPE) {
						dialog.dialog('close');
					}                
					evt.stopPropagation();
				},
				onClose : function () {
				    groupWid.splice($.inArray(windowId,groupWid),1);
				    
					if (typeof options.onClose == "function") {
						options.onClose.call(dialog,$);
					}
					destroy(this);
				},
				onMove : function (left, top) {
					if (typeof options.onMove == "function") {
						options.onMove.call(dialog,$);
					}
					var o = $.data(this, 'panel').options;
					if (top < 0) {
						$(this).dialog("move", {
							"left" : left,
							"top" : 0
						});
					} else if (o.maximized) {
						$(this).dialog("restore");
						$(this).dialog("move", {
							"left" : left + 100,
							"top" : top
						});
					}
					if (top > ($(o.target).height() - 20)) {
						$(this).dialog("move", {
							"left" : left,
							"top" : ($(o.target).height() - 25)
						});
					}
				}
			}));
		if (options.align) {
			var w = dialog.closest(".window");
			switch (options.align) {
			case "right":
				dialog.dialog("move", {
					left : w.parent().width() - w.width() - 10
				});
				break;
			case "tright":
				dialog.dialog("move", {
					left : w.parent().width() - w.width() - 10,
					top : 0
				});
				break;
			case "bright":
				dialog.dialog("move", {
					left : w.parent().width() - w.width() - 10,
					top : w.parent().height() - w.height() - 10
				});
				break;
			case "left":
				dialog.dialog("move", {
					left : 0
				});
				break;
			case "tleft":
				dialog.dialog("move", {
					left : 0,
					top : 0
				});
				break;
			case "bleft":
				dialog.dialog("move", {
					left : 0,
					top : w.parent().height() - w.height() - 10
				});
				break;
			case "top":
				dialog.dialog("move", {
					top : 0
				});
				break;
			case "bottom":
				dialog.dialog("move", {
					top : w.parent().height() - w.height() - 10
				});
				break;
			}
		}
		if (options.isMax) {
			dialog.dialog("maximize");
			dialog.dialog("open");
		}
		
		if ($.fn.mask && 　options.mask)
			　dialog.mask();
		if (options.contents) {
			ajaxSuccess(options.contents);
		} else {
			if (!options.isIframe) {
				$.ajax({
					url : options.url,
					type : options.ajaxType || "POST",
					data : options.data == null ? "" : options.data,
					success : function (date) {
						ajaxSuccess(date);
					},
					error : function () {
						$.messager.alert("提示", "加载失败！");
						if ($.fn.mask && 　options.mask)
							dialog.mask("hide");
					}
				});
			} else {
				ajaxSuccess();
			}
		}
		dialog.attr("id",windowId);
		
		return dialog;
		function ajaxSuccess(data) {
			if (options.isIframe && !data) {
				dialog.find("div.dialog-content").html('<iframe id="windowFrames" name="windowFrames" width="100%" height="100%" frameborder="0" src="' + options.url + '" ></iframe>');
				$(self.frames['windowFrames'].document).bind( "keydown", function( event ) {
					if(event.keyCode && event.keyCode === 27 ) {
							$("#" + windowId).dialog("close");
							event.preventDefault();
					 }
				});
			} else {
				
				dialog.find("div.dialog-content").html(data);
			}
			$.parser.parse(dialog);
			var cancelBtn = $(dialog).find('button[type=button]');
			if(cancelBtn && cancelBtn.hasClass('cancelBtn')){
				cancelBtn.bind('click',function(event){
					dialog.dialog("close");
				})
				
			}
			options.onComplete.call(this, dialog,$);
			if ($.fn.mask && 　options.mask)
			dialog.mask("hide");
		}
	};
	$.fn.destroy = function () {
		destroy(this);
	};
	window.GETWIN = getWindow;
	window.GETWINBYID = getWindowById;
	$.createWin.defaults = $.extend({}, $.fn.dialog.defaults, {
			url : '',
			data : '',
			ajaxType:"GET",
			target : 'body',
			height : 600,
			width : 500,
			isIframe:false,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : true,
			modal : true,
			shadow : false,
			mask : true,
			onAjaxComplete:function (dialog){},
			onComplete : function (dialog,jq) { Page.initAjaxBeforeShow(dialog);FormValidation.initAjax(dialog)}
		});
})(jQuery);
