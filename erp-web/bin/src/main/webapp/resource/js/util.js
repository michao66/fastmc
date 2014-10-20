var Util = function() {
	var b = {};
	var a = null;
	return {
		traverseTreeToKeyValue : function(d, c) {
			if (c == undefined) {
				c = {}
			}
			$.each(d, function(e, f) {
				c[f.id] = f.name;
				if (typeof (f.children) === "object") {
					Util.traverseTreeToKeyValue(f.children, c)
				}
			});
			return c
		},
		remoteSelectOptions : function(c, e) {
			if (e == false) {
				var d;
				$.ajax({
					async : false,
					type : "GET",
					url : c,
					dataType : "json",
					success : function(f) {
						d = {
							"" : " "
						};
						$.each(f, function(g, h) {
							d[h.id] = h.display
						})
					}
				});
				return d
			} else {
				if (e == undefined) {
					e = $("body")
				}
				if (e.data("CacheUrlDatas") == undefined) {
					e.data("CacheUrlDatas", {})
				}
				var d = e.data("CacheUrlDatas")[c];
				if (d == undefined) {
					$.ajax({
						async : false,
						type : "GET",
						url : c,
						dataType : "json",
						success : function(f) {
							d = {
								"" : " "
							};
							$.each(f, function(g, h) {
								d[h.id] = h.display
							});
							e.data("CacheUrlDatas")[c] = d
						}
					})
				}
				return d
			}
		},
		getCacheDatas : function(c, e) {
			if (e == undefined) {
				e = $("body")
			}
			if (e.data("CacheUrlDatas") == undefined) {
				e.data("CacheUrlDatas", {})
			}
			var d = e.data("CacheUrlDatas")[c];
			if (d == undefined) {
				$.ajax({
					async : false,
					type : "GET",
					url : c,
					dataType : "json",
					success : function(f) {
						d = f;
						e.data("CacheUrlDatas")[c] = d
					}
				})
			}
			return d
		},
		getCacheSelectOptionDatas : function(c, e) {
			if (e == undefined) {
				e = $("body")
			}
			if (e.data("CacheSelectOptionDatas") == undefined) {
				e.data("CacheSelectOptionDatas", {})
			}
			var d = e.data("CacheSelectOptionDatas")[c];
			if (d == undefined) {
				$.ajax({
					async : false,
					type : "GET",
					url : c,
					dataType : "json",
					success : function(g) {
						var f = g;
						if (g.content) {
							f = g.content
						}
						d = {};
						d[""] = "";
						$.each(f, function(h, j) {
							d[j.id] = j.display
						});
						e.data("CacheSelectOptionDatas")[c] = d
					}
				})
			}
			return d
		},
		getCacheEnumsByType : function(c, e) {
			if (e == undefined) {
				e = $("body")
			}
			if (e.data("CacheEnumDatas") == undefined) {
				$.ajax({
					async : false,
					type : "GET",
					url : WEB_ROOT + "/pub/data!enums.json",
					dataType : "json",
					success : function(k) {
						for ( var j in k) {
							var g = k[j];
							var f = {
								"" : ""
							};
							for ( var h in g) {
								f[h] = g[h]
							}
							k[j] = f
						}
						e.data("CacheEnumDatas", k)
					}
				})
			}
			var d = e.data("CacheEnumDatas")[c];
			if (d == undefined) {
				alert("错误的枚举数据类型：" + c);
				d = {}
			}
			return d
		},
		getCacheDictDatasByType : function(d, g) {
			if (g == undefined) {
				g = $("body")
			}
			var h = g.data("CacheDictDatas");
			if (h == undefined) {
				$.ajax({
					async : false,
					type : "GET",
					url : WEB_ROOT + "/pub/data!dictDatas.json",
					dataType : "json",
					success : function(j) {
						h = j;
						g.data("CacheDictDatas", h)
					}
				})
			}
			var e = g.data("CacheDictDatas")[d];
			if (e == undefined) {
				var c = {};
				var f = true;
				$.each(h, function(j, k) {
					if (k.parentPrimaryKey == d) {
						f = false;
						c[k.primaryKey] = k.primaryValue
					}
				});
				e = c;
				g.data("CacheDictDatas")[d] = e;
				if (f) {
					alert("错误的数据字典类型：" + d)
				}
			}
			return e
		},
		assert : function(d, c) {
			if (!d) {
				alert(c)
			}
		},
		assertNotBlank : function(d, c) {
			if (d == undefined || $.trim(d) == "") {
				Util.assert(false, c);
				return
			}
		},
		debug : function(c) {
			if (window.console) {
				console.debug(c)
			} else {
				alert(c)
			}
		},
		hashCode : function(e) {
			var d = 0;
			if (e.length == 0) {
				return d
			}
			for (i = 0; i < e.length; i++) {
				var c = e.charCodeAt(i);
				d = ((d << 5) - d) + c;
				d = d & d
			}
			if (d < 0) {
				d = -d
			}
			return d
		},
		AddOrReplaceUrlParameter : function(h, c, g) {
			var f = h.indexOf("?");
			if (f == -1) {
				h = h + "?" + c + "=" + g
			} else {
				var j = h.split("?");
				var k = j[1].split("&");
				var e = "";
				var d = false;
				for (i = 0; i < k.length; i++) {
					e = k[i].split("=")[0];
					if (e == c) {
						k[i] = c + "=" + g;
						d = true;
						break
					}
				}
				if (!d) {
					h = h + "&" + c + "=" + g
				} else {
					h = j[0] + "?";
					for (i = 0; i < k.length; i++) {
						if (i > 0) {
							h = h + "&"
						}
						h = h + k[i]
					}
				}
			}
			return h
		},
		subStringBetween : function(f, h, d) {
			var g = new RegExp(h + ".*?" + d, "img");
			var e = new RegExp(h, "g");
			var c = new RegExp(d, "g");
			return f.match(g).join("=").replace(e, "").replace(c, "")
					.split("=")
		},
		split : function(c) {
			return c.split(",")
		},
		isArrayContainElement : function(e, d) {
			var c = e.length;
			while (c--) {
				if (e[c] === d) {
					return true
				}
			}
			return false
		},
		getTextWithoutChildren : function(c) {
			return $(c)[0].childNodes[0].nodeValue.trim()
		},
		findClosestFormInputByName : function(d, c) {
			return $(d).closest("form").find("[name='" + c + "']")
		},
		setInputValIfBlank : function(c, d) {
			if ($.trim($(c).val()) == "") {
				$(c).val(d)
			}
		},
		startWith : function(d, e) {
			var c = new RegExp("^" + e);
			return c.test(d)
		},
		endWith : function(e, c) {
			var d = new RegExp(c + "$");
			return d.test(e)
		},
		objectToString : function(c) {
			var d = "";
			$.each(c, function(f, e) {
				d += (f + ":" + e + ";\n")
			});
			return d
		},
		parseFloatValDefaultZero : function(d) {
			if ($.trim($(d).val()) == "") {
				return 0
			} else {
				var c = parseFloat($.trim($(d).val()));
				if (isNaN(c)) {
					return 0
				} else {
					return c
				}
			}
		},
		notSmallViewport : function() {
			var c = $(window).width();
			return c >= 768
		},
		init : function() {
			$.fn.plot = function(g) {
				var f = $(this);
				if (f.attr("chart-plot-done")) {
					return
				}
				f.css("min-height", "100px");
				var c = $.extend(true, g || {}, f.data("plotOptions") || {});
				var d = c.data;
				var e = c.options;
				$.each(d, function(j, k) {
					if (typeof k.data === "function") {
						k.data = k.data.call(f)
					}
				});
				e = $.extend(true, {
					pointhover : true,
					series : {
						lines : {
							show : true,
							lineWidth : 2,
							fill : true,
							fillColor : {
								colors : [ {
									opacity : 0.05
								}, {
									opacity : 0.01
								} ]
							}
						},
						points : {
							show : true
						},
						shadowSize : 2
					},
					grid : {
						hoverable : true,
						clickable : true,
						tickColor : "#eee",
						borderWidth : 0
					},
					colors : [ "#d12610", "#37b7f3", "#52e136" ],
					xaxis : {
						timezone : "browser",
						monthNames : [ "1月", "2月", "3月", "4月", "5月", "6月",
								"7月", "8月", "9月", "10月", "11月", "12月" ]
					}
				}, e);
				$.plot(f, d, e);
				if (c.pointhover) {
					var h = $("#plothoverTooltip");
					if (h.size() == 0) {
						h = $("<div id='plothoverTooltip'></div>").css({
							position : "absolute",
							display : "none",
							border : "1px solid #333",
							padding : "4px",
							color : "#fff",
							"border-radius" : "3px",
							"background-color" : "#333",
							opacity : 0.8,
							"min-width" : "50px",
							"text-align" : "center"
						}).appendTo("body")
					}
					f.bind("plothover", function(k, m, j) {
						if (j) {
							var l = j.datapoint[1];
							h.html(l).css({
								top : j.pageY,
								left : j.pageX + 15
							}).fadeIn(200)
						} else {
							h.hide()
						}
					})
				}
				f.attr("chart-plot-done", true)
			}, $.fn.barcodeScanSupport = function(d) {
				var c = $(this);
				var e = c.attr("id");
				if (e == undefined) {
					e = "barcode_" + new Date().getTime();
					c.attr("id", e)
				}
				if (c.attr("placeholder") == undefined) {
					c.attr("placeholder", "支持条码扫描输入")
				}
				c.focus(function(f) {
					c.select()
				}).click(function(f) {
					if (window.wst) {
						window.wst.startupBarcodeScan(e)
					}
				}).keydown(function(f) {
					if (d.onEnter) {
						if (f.keyCode === 13) {
							d.onEnter.call(c)
						}
					}
				}).bind("barcode", function(f, g) {
					c.val(g);
					var h = jQuery.Event("keydown");
					h.keyCode = 13;
					c.trigger(h);
					c.select()
				})
			}, $.fn.ajaxGetUrl = function(u,d) {
				Util.assertNotBlank(u, "URL参数不能为空");
				var c = $(this);
				c.addClass("ajax-get-container");
				c.attr("data-url", u);
				c.css("min-height", "100px");
				App.blockUI(c);
				$.ajax({
					type : "GET",
					cache : false,
					url : u,
					dataType : "html",
                    data:d,
					success : function(g) {
					    c.empty();
						var f = $("<div/>").appendTo(c);
						f.hide();
						f.html(g);
						Page.initAjaxBeforeShow(f);
						f.show();
						FormValidation.initAjax(f);
						Page.initAjaxAfterShow(f);
						//Grid.initAjax(f);
						App.unblockUI(c)
					},
					error : function(h, f, g) {
						c.html("<h4>页面内容加载失败</h4>");
						App.unblockUI(c)
					},
					statusCode : {
						403 : function() {
							Global.notify("error", "如果你确认需要此访问权限，请联系管理员",
									"未授权访问")
						},
						404 : function() {
							Global.notify("error", "请尝试刷新页面试试，如果问题依然请联系管理员",
									"请求资源未找到")
						}
					}
				});
				return c
			};
			$.fn.ajaxJsonUrl = function(d, f, e) {
				Util.assertNotBlank(d);
				var c = $(this);
				App.blockUI(c);
				$.ajax({
					traditional : true,
					type : "GET",
					cache : false,
					url : d,
					dataType : "json",
					data : e,
					success : function(g) {
						if (g.type == "error" || g.type == "warning"
								|| g.type == "failure") {
							Global.notify("error", g.message)
						} else {
							if (f) {
								f.call(c, g)
							}
							json = g
						}
						App.unblockUI(c)
					},
					error : function(j, g, h) {
						Global.notify("error", "数据请求异常，请联系管理员", "系统错误");
						App.unblockUI(c)
					},
					statusCode : {
						403 : function() {
							Global.notify("error", "如果你确认需要此访问权限，请联系管理员",
									"未授权访问")
						},
						404 : function() {
							Global.notify("error", "请尝试刷新页面试试，如果问题依然请联系管理员",
									"请求资源未找到")
						}
					}
				})
			};
			$.fn.ajaxJsonSync = function(d, f, g) {
				Util.assertNotBlank(d);
				var c = $(this);
				App.blockUI(c);
				var e = null;
				$.ajax({
					traditional : true,
					type : "GET",
					cache : false,
					async : false,
					url : d,
					data : f,
					contentType : "application/json",
					dataType : "json",
					success : function(h) {
						if (h.type == "error" || h.type == "warning"
								|| h.type == "failure") {
							Global.notify("error", h.message)
						} else {
							if (g) {
								g.call(c, h)
							}
							e = h
						}
						App.unblockUI(c)
					},
					error : function(k, h, j) {
						Global.notify("error", "数据请求异常，请联系管理员", "系统错误");
						App.unblockUI(c)
					},
					statusCode : {
						403 : function() {
							Global.notify("error", "如果你确认需要此访问权限，请联系管理员",
									"未授权访问")
						},
						404 : function() {
							Global.notify("error", "请尝试刷新页面试试，如果问题依然请联系管理员",
									"请求资源未找到")
						}
					}
				});
				return e
			};
			$.fn.ajaxPostURL = function(d, g, f, c) {
				Util.assertNotBlank(d);
				if (f == undefined) {
					f = "确认提交数据？"
				}
				if (f) {
					if (!confirm(f)) {
						return false
					}
				}
				var c = $.extend({
					data : {}
				}, c);
				var e = $(this);
				App.blockUI(e);
				$.post(encodeURI(d), c.data, function(h, l) {
					App.unblockUI(e);
					if (!h.type) {
						Global.notify("error", h, "系统处理异常");
						return
					}
					if (h.type == "success" || h.type == "warning") {
						Global.notify(h.type, h.message);
						if (g) {
							g.call(e, h)
						}
					} else {
						if (h.userdata) {
							var k = [];
							for ( var j in h.userdata) {
								k.push(h.userdata[j])
							}
							Global.notify("error", k.join("<br>"), h.message)
						} else {
							Global.notify("error", h.message)
						}
						if (c.failure) {
							c.failure.call(e, h)
						}
					}
				}, "json")
			};
			$.fn.ajaxPostForm = function(g, f, d, c) {
				if (f) {
					if (!confirm(f)) {
						return false
					}
				}
				var c = $.extend({
					data : {}
				}, c);
				var e = $(this);
				App.blockUI(e);
				e.ajaxSubmit({
					dataType : "json",
					method : "post",
					success : function(h) {
						App.unblockUI(e);
						if (h.type == "success") {
							if (g) {
								g.call(e, h)
							}
						} else {
							if (h.type == "failure") {
								bootbox.alert(h.message);
								if (d) {
									d.call(e, h)
								}
							} else {
								bootbox.alert("表单处理异常，请联系管理员");
								if (d) {
									d.call(e, h)
								}
							}
						}
					},
					error : function(l, k, h) {
						App.unblockUI(e);
						var j = jQuery.parseJSON(l.responseText);
						if (j.type == "error") {
							bootbox.alert(j.message)
						} else {
							bootbox.alert("表单处理异常，请联系管理员")
						}
						if (d) {
							d.call(e, j)
						}
					}
				})
			};
			$.fn.popupDialog = function(l) {
				var f = $(this);
				var c = f.attr("href");
				if (c == undefined) {
					c = f.attr("data-url")
				}
				var g = f.attr("title");
				if (g == undefined) {
					g = "对话框"
				}
				var k = f.attr("modal-size");
				if (k == undefined) {
					k = "modal-full"
				} else {
					if (k == "auto") {
						k = ""
					} else {
						k = "modal-" + k
					}
				}
				var l = $.extend({
					url : c,
					title : g,
					size : k
				}, l);
				Util.assertNotBlank(l.url);
				var j = "dialog_" + Util.hashCode(l.url);
				var d = $("#" + j);
				if (d.length == 0) {
					var e = [];
					e
							.push('<div id="'
									+ j
									+ '" class="modal fade" tabindex="-1" role="basic" aria-hidden="true" >');
					e.push('<div class="modal-dialog ' + l.size + '">');
					e.push('<div class="modal-content">');
					e.push('<div class="modal-header">');
					e
							.push('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>');
					e.push('<h4 class="modal-title">' + l.title + "</h4>");
					e.push("</div>");
					e.push('<div class="modal-body">');
					e.push("</div>");
					e.push('<div class="modal-footer">');
					e
							.push('<button type="button" class="btn default" data-dismiss="modal">关闭窗口</button>');
					e.push("</div>");
					e.push("</div>");
					e.push("</div>");
					e.push("</div>");
					var h = f.closest(".panel-content");
					if (h == undefined) {
						h = $(".page-container:first")
					}
					var d = $(e.join("")).appendTo($("body"));
					d.modal();
					d.find(" > .modal-dialog > .modal-content > .modal-body")
							.ajaxGetUrl(l.url)
				} else {
					d.modal("show")
				}
				if (l.callback) {
					d.data("callback", l.callback)
				}
			}
		}
	}
}();
var BooleanUtil = function() {
	return {
		toBoolean : function(b) {
			if (b) {
				var a = $.type(b);
				if (a === "string"
						&& (b == "false" || b == "0" || b == "n" || b == "no")) {
					return false
				} else {
					if (a === "number" && (b == 0)) {
						return false
					}
				}
				return true
			}
			return false
		}
	}
}();
var MathUtil = function() {
	return {
		mul : function(arg1, arg2) {
			if (arg1 == undefined) {
				arg1 = 0
			}
			var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
			try {
				m += s1.split(".")[1].length
			} catch (e) {
			}
			try {
				m += s2.split(".")[1].length
			} catch (e) {
			}
			return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
					/ Math.pow(10, m)
		},
		div : function(arg1, arg2, fix) {
			if (fix == undefined) {
				fix = 2
			}
			var t1 = 0, t2 = 0, r1, r2;
			try {
				t1 = arg1.toString().split(".")[1].length
			} catch (e) {
			}
			try {
				t2 = arg2.toString().split(".")[1].length
			} catch (e) {
			}
			with (Math) {
				r1 = Number(arg1.toString().replace(".", ""));
				r2 = Number(arg2.toString().replace(".", ""));
				return MathUtil.mul((r1 / r2), pow(10, t2 - t1)).toFixed(fix)
			}
		},
		add : function(arg1, arg2) {
			if (arg1 == undefined) {
				arg1 = 0
			}
			if (arg2 == undefined) {
				arg2 = 0
			}
			var r1, r2, m, c;
			try {
				r1 = arg1.toString().split(".")[1].length
			} catch (e) {
				r1 = 0
			}
			try {
				r2 = arg2.toString().split(".")[1].length
			} catch (e) {
				r2 = 0
			}
			c = Math.abs(r1 - r2);
			m = Math.pow(10, Math.max(r1, r2));
			if (c > 0) {
				var cm = Math.pow(10, c);
				if (r1 > r2) {
					arg1 = Number(arg1.toString().replace(".", ""));
					arg2 = Number(arg2.toString().replace(".", "")) * cm
				} else {
					arg1 = Number(arg1.toString().replace(".", "")) * cm;
					arg2 = Number(arg2.toString().replace(".", ""))
				}
			} else {
				arg1 = Number(arg1.toString().replace(".", ""));
				arg2 = Number(arg2.toString().replace(".", ""))
			}
			return MathUtil.div((arg1 + arg2), m)
		},
		sub : function(arg1, arg2) {
			return MathUtil.add(arg1, -Number(arg2))
		}
	}
}();
function scanBarcodeCallback(b, a) {
	$("#" + b).trigger("barcode", [ a ])
};

	/**
	 * 扩展String方法
	 */
	$.extend(String.prototype, {
		isPositiveInteger:function(){
			return (new RegExp(/^[1-9]\d*$/).test(this));
		},
		isInteger:function(){
			return (new RegExp(/^\d+$/).test(this));
		},
		isNumber: function(value, element) {
			return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
		},
		trim:function(){
			return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
		},
		startsWith:function (pattern){
			return this.indexOf(pattern) === 0;
		},
		endsWith:function(pattern) {
			var d = this.length - pattern.length;
			return d >= 0 && this.lastIndexOf(pattern) === d;
		},
		replaceSuffix:function(index){
			return this.replace(/\[[0-9]+\]/,'['+index+']').replace('#index#',index);
		},
		trans:function(){
			return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
		},
		encodeTXT: function(){
			return (this).replaceAll('&', '&amp;').replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
		},
		replaceAll:function(os, ns){
			return this.replace(new RegExp(os,"gm"),ns);
		},
		replaceTm:function($data){
			if (!$data) return this;
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				return $data[$1.replace(/[{}]+/g, "")];
			});
		},
		replaceTmById:function(_box){
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
				return $input.val() ? $input.val() : $1;
			});
		},
		isFinishedTm:function(){
			return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this)); 
		},
		skipChar:function(ch) {
			if (!this || this.length===0) {return '';}
			if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
			return this;
		},
		isValidPwd:function() {
			return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this)); 
		},
		isValidMail:function(){
			return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(this.trim()));
		},
		isSpaces:function() {
			for(var i=0; i<this.length; i+=1) {
				var ch = this.charAt(i);
				if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
			}
			return true;
		},
		isPhone:function() {
			return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
		},
		isUrl:function(){
			return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
		},
		isExternalUrl:function(){
			return this.isUrl() && this.indexOf("://"+document.domain) == -1;
		}
	});