var Global = function() {
	return {
		init : function() {
			(function(d) {
				(jQuery.browser = jQuery.browser || {}).mobile = /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i
						.test(d)
						|| /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
								.test(d.substr(0, 4))
			})(navigator.userAgent || navigator.vendor || window.opera);
			$.fn.editable.defaults.inputclass = "form-control";
			$(window).resize(function() {
				//if (Grid) {
				//	Grid.refreshWidth()
				//}
			});
			toastr.options = {
				closeButton : true,
				positionClass : "toast-bottom-right",
				extendedTimeOut : 600000
			};
			$("#a-lock-screen").click(
					function() {
						$.backstretch([ "resource/img/bg/1.jpg",
								"resource/img/bg/2.jpg", "assets/img/bg/3.jpg",
								"resource/img/bg/4.jpg" ], {
							fade : 1000,
							duration : 8000
						});
						$(".page-container,.header,.footer").hide();
						$("#form-unlock").find(":text").focus().val("");
						$("#page-lock").show();
						$("#form-unlock").find("input").first().focus()
					});
			$("#form-unlock").submit(function(d) {
				d.preventDefault();
				$("body").data("backstretch").destroy();
				$("#page-lock").hide();
				$(".page-container,.header,.footer").fadeIn(2000);
				return false
			});
			$("#a-logout").click(function() {
				bootbox.setDefaults({
					locale : "zh_CN"
				});
				bootbox.confirm("确认注销登录吗？", function(d) {
					if (d) {
						window.location.href = "j_spring_security_logout"
					}
				})
			});
			$("#trigger_passwd").click(function(d) {
				$(this).popupDialog({
					size : "auto"
				});
				d.preventDefault();
				return false
			});
			var c = $(".page-sidebar .menu-root li > a.ajaxify");
			$.each(c, function() {
				var d = $(this);
				d.attr("data-py", makePy(d.text()))
			});
			$('.sidebar-search input[name="search"]').autocomplete({
				autoFocus : true,
				source : function(e, d) {
					return d(c.map(function() {
						var i = e.term.toLowerCase();
						var f = $(this);
						var h = f.text();
						var g = f.attr("data-py").toLowerCase();
						if (g.indexOf(i) > -1 || h.indexOf(i) > -1) {
							return {
								label : $.trim(h),
								link : f,
								href : f.attr("href")
							}
						}
					}))
				},
				minLength : 1,
				select : function(e, f) {
					var d = f.item;
					$(this).parent().find(".submit").data("link", d.link);
					d.link.click();
					return true
				}
			}).focus(function() {
				$(this).select()
			}).val("").focus();
			$('.sidebar-search input[name="search"]').parent().find(".submit")
					.click(function() {
						var d = $(this).data("link");
						if (d) {
							d.click()
						}
						return false
					});
			var a = $('.page-sidebar .menu-root a[data-code="MMOBILE"]')
					.parent().find("ul li > a.ajaxify");
			var b = $("#dropdown-menu-mobile");
			$.each(a, function() {
				var d = $(this);
				b.find("> .divider-menus").after(d.closest("li").clone(true))
			});
			$.address.change(function(d) {
						App.scrollTop();
						var e = $('.page-sidebar li > a.ajaxify[rel="address:'+ d.value + '"]');
						if (e.size() > 0) {
							Global.addOrActivePanel(e);
							var h = $(".page-sidebar-menu").find("li");
							h.removeClass("active");
							var k = e.parent("li");
							k.addClass("active");
							var f = k.closest("ul.sub-menu");
							while (f.size() > 0) {
								f.show();
								var l = f.parent("li");
								l.addClass("open");
								l.find(" > a > span.arrow").addClass("open");
								f = l.closest("ul.sub-menu")
							}
						} else {
							var j = $("#layout-nav");
							var i = j.next(".tab-content");
							var g = $("#tab_content_dashboard");
							if (g.is(":empty")) {
								$("#tab_content_dashboard").ajaxGetUrl("/admin/dashboard.action")
							}
							g.show();
							i.find("> div").not(g).hide();
							j.find("> li:not(.btn-group)").remove();
							j.append('<li><i class="fa fa-home"></i> <a href="javascript:;">Dashboard</a></li>')
						}
					});
			$("div#portlet-layout > .portlet-title-layout > .tools > .reload")
					.click(
							function(i) {
								Util.debug(i.target + ":" + i.type);
								var d = $("div#portlet-layout").find(
										" > .portlet-body > .portlet-tabs");
								var h = d.find("> .nav > li.active > a");
								var g = d.find(h.attr("href"));
								var f = h.attr("data-url");
								g.ajaxGetUrl(f)
							});
			jQuery("body").on("dblclick", ".portlet-title", function(d) {
				$(this).find(".tools .collapse,.tools .expand").click()
			});
			jQuery("body").on(
					"click",
					".btn-cancel",
					function(h) {
						var f = $(this).closest(".tab-closable");
						if (f.length > 0) {
							f.parent(".tab-content").parent().find(
									" > .nav li.active .close").click()
						} else {
							var g = $("#layout-nav");
							var d = g.find(" > .btn-group > ul.dropdown-menu");
							d.find("> li.active > a > .badge").click()
						}
					});
			jQuery("body").on(
					"click",
					"#layout-nav >  li > .btn-close-active",
					function(h) {
						var g = $("#layout-nav");
						var f = g.next(".tab-content").find(
								".panel-content:visible").attr("data-url");
						var d = g.find(" > .btn-group > ul.dropdown-menu");
						d.find("a[href='" + f + "']").find(".badge").click()
					});
			jQuery("body").on("click", "#layout-nav >  li > .btn-dashboard",
					function(d) {
						$.address.value("/dashboard")
					});
			jQuery("body").on("click",
							"#portlet-layout > .portlet-body > .portlet-tabs > .nav > li > a",
							function(i) {
								Util.debug(i.target + ":" + i.type);
								i.preventDefault();
								var g = $(this);
								var d = g.attr("data-url");
								var f = $(".page-sidebar-menu").find(
										"a[href='" + d + "']");
								$(".page-sidebar-menu").find("li").removeClass(
										"active");
								f.parent("li").addClass("active");
								var h = g.text();
								if (f.length > 0) {
									var j = f.parent("li").parent(".sub-menu");
									while (j.length > 0) {
										h = j.prev("a").children("span.title").text()+ " > " + h;
										j = j.parent("li").parent(".sub-menu")
									}
								}
								$("div#portlet-layout > .portlet-title > .caption").html(h)
							});
			jQuery("body").on("click","ul.nav > li.tools > .reload",
							function(i) {
								Util.debug(i.target + ":" + i.type);
								i.preventDefault();
								var d = $(this).closest(".nav");
								var h = d.find("li.active > a");
								var g = d.closest(".tabbable").find(
										h.attr("href"));
								if (h.attr("data-url")) {
									var f = h.attr("data-url");
									g.ajaxGetUrl(f,	function() {
											g.find(".tabbable:first > .nav > li.active > a").click()
									})
								} else {
									/*
									if (jQuery().jqGrid) {
										g.find("table.ui-jqgrid-btable").each(
											function() {
												var e = $(this);
												e.trigger("clearToolbar");
												var j = e.attr("data-url");
												e.jqGrid("setGridParam", {
													datatype : "json",
													url : j
												}).trigger("reloadGrid")
										})
									}
									*/
								}
							});
			jQuery("body").on("click", ".portlet-title > .tools > .reload",
					function(g) {
						Util.debug(g.target + ":" + g.type);
						g.preventDefault();
						var f = $(this).closest(".ajaxify");
						if (f.attr("data-url")) {
							var d = f.attr("data-url");
							f.ajaxGetUrl(d)
						}
					});
			$(document).off("click.tab.data-api");
			$(document).on("click.tab.data-api",'[data-toggle="tab"], [data-toggle="pill"]',
						function(j) {
							Util.debug(j.target + ":" + j.type);
							j.preventDefault();
							var i = $(this);
							if (i.hasClass("disabled")|| i.attr("data-tab-disabled") == "true") {
									return false
							}
							var d = i.attr("href");
							var h = new RegExp("^#");
							if (i.attr("data-url") == undefined&& !h.test(d)) {
									i.attr("data-url", d);
									var k = "tab_content_" + Util.hashCode(d);
									i.attr("href", "#" + k);
									var g = i.closest("div.tabbable,div.portlet-tabs")	.find(" > div.tab-content");
									if (g.length == 0) {
										g = $('<div class="tab-content">').appendTo(i.closest("div.tabbable"))
									}
									var f = g.find("div#" + k);
									if (f.length == 0) {
										f = $('<div id="'+ k+ '" class="tab-pane active">').appendTo(g)
									}
									if (f.is(":empty")) {
										f.ajaxGetUrl(d,function() {
											f.append('<div style="clear:both"></div>');
											f.find(".tabbable:first > .nav > li.active > a").click()
										})
									}
								}
								$(this).tab("show");
								$(this).attr("click-idx", new Date().getTime());
								//Grid.refreshWidth()
							});
			$(".page-sidebar, .header").on("click", ".sidebar-toggler",
					function(d) {
						//Grid.refreshWidth()
					});
			jQuery("body").on("click", 'a[data-toggle="modal-ajaxify"]',
					function(d) {
						Util.debug(d.target + ":" + d.type);
						d.preventDefault();
						$(this).popupDialog()
					});
			jQuery("body").on("click", 'a[data-toggle="panel"]', function(f) {
				Util.debug(f.target + ":" + f.type);
				f.preventDefault();
				var d = $(this);
				Global.addOrActivePanel(d)
			});
			jQuery("body").on("click", 'a[data-toggle="dynamic-tab"]',
					function(g) {
						Util.debug(g.target + ":" + g.type);
						g.preventDefault();
						var f = $(this);
						var d = f.closest(".tabbable").find(" > .nav");
						var h = f.attr("data-title");
						if (h == undefined) {
							h = f.text()
						}
						Global.addOrActiveTab(d, {
							title : h,
							url : f.attr("data-url")
						})
					});
			jQuery("body").on("click", ".btn-post-url", function(g) {
				Util.debug(g.target + ":" + g.type);
				g.preventDefault();
				var f = $(this);
				var d = null;
				if (f.is("button")) {
					d = f.attr("data-url")
				} else {
					if (f.is("a")) {
						d = f.attr("href")
					}
				}
				var h = f.data("success");
				if (h == undefined) {
					h = function() {
						var i = f.attr("data-ajaxify-reload");
						if (i != "false") {
							$(i).each(function() {
								$(this).ajaxGetUrl($(this).attr("data-url"))
							})
						}
						var e = f.attr("data-close-container");
						if (e != "false") {
							Global.autoCloseContainer(f)
						}
					}
				}
				f.ajaxPostURL(d, h, f.attr("data-confirm"))
			});
			jQuery("body").on("click", ".select-table-checkbox", function(f) {
				var d = $(this).find(".table-checkbox :checkbox");
				if (!(d.is(f.target) || d.find(f.target).length)) {
					d.attr("checked", !d.is(":checked"))
				}
			})
		},
		autoCloseContainer : function(g) {
			var c = $(g);
			if (c.attr("data-prevent-close") == undefined
					|| c.attr("data-prevent-close") == "false") {
				var d = c.closest(".tabbable-secondary");
				if (d.length == 0) {
					var h = c.closest(".modal");
					if (h.size() > 0) {
						h.modal("hide")
					} else {
						var i = c.closest(".tab-closable");
						if (i.length > 0) {
							i.parent(".tab-content").parent().find(
									" > .nav li.active .close").click()
						} else {
							$("#layout-nav .btn-close-active").click()
						}
					}
				} else {
					if (d.find(" > ul.nav > li").not(".tools").size() < 2) {
						var i = c.closest(".tab-closable");
						if (i.length > 0) {
							i.parent(".tab-content").parent().find(
									" > .nav li.active .close").click()
						} else {
							$("#layout-nav .btn-close-active").click()
						}
					} else {
						var b = c.closest("form").find("input[name='id']")
								.val();
						if (b && b != "") {
							d.find(" > ul.nav > li.tools > .reload").click()
						} else {
							var e = f.find(" > ul.nav > li.active > a");
							var a = Util.AddOrReplaceUrlParameter(e
									.attr("data-url"), "id",
									response.userdata.id);
							e.attr("data-url", a);
							var f = c.closest(".tabbable-primary");
							f.find(" > ul.nav > li.tools > .reload").click()
						}
					}
				}
			}
		},
		doSomeStuff : function() {
			myFunc()
		},
		notify : function(a, b, c) {
			if (a == "error") {
				toastr.options.timeOut = 5000;
				toastr.options.positionClass = "toast-bottom-center"
			} else {
				toastr.options.positionClass = "toast-bottom-right"
			}
			if (c == undefined) {
				c = ""
			}
			toastr[a](b, c)
		},
		addOrActivePanel : function(d) {
			var b = d.attr("href");
			var g = $("#layout-nav");
			var f = g.next(".tab-content");
			var k = f.find("> div[data-url='" + b + "']");
			if (k.length == 0) {
				k = $('<div data-url="' + b + '" class="panel-content"></div>').appendTo(f);
				k.ajaxGetUrl(b)
			} else {
				k.show()
			}
			f.find("> div").not(k).hide();
			var c = g.find(" > .btn-group > ul.dropdown-menu");
			var h = c.find("> li > a[href='" + b + "']");
			if (h.length == 0) {
				h = $('<a href="'+ b+ '">'+ d.text()+ '<span class="badge badge-default">X</span></a>').appendTo(c).wrap("<li/>");
				h.find(".badge").click(
					function(o) {
							o.preventDefault();
							var n = false;
							k.find("form[method='post']:not(.form-track-disabled)[form-data-modified='true']").each(
									 function() {
									    var q = $(this);
										if (!confirm("当前表单有修改数据未保存，确认离开当前表单吗？")) {
											n = true;
											return false
										}});
									if (!n) {
										h.parent("li").remove();
										k.remove();
										var m = 1;
										c.find("> li").each(function() {
											var q = $(this).attr("count");
											if (q) {
												if (Number(q) > m) {
													m = Number(q)
												}
											}
										});
										var p = c.find("> li[count='" + m + "'] > a");
										if (p.length > 0) {
											p.click()
										} else {
											$("#layout-nav >  li > .btn-dashboard").click()
										}
									}
								});
				        h.click(function(m) {m.preventDefault();d.click()
				});
				var a = $(".page-sidebar-menu").find("a[href='" + b + "']");
				var l = '<li><a href="' + b + '" title="刷新当前页面">' + d.text()+ "</a></li>";
				if (a.length > 0) {
					var j = a.parent("li").parent(".sub-menu");
					while (j.length > 0) {
						var e = j.prev("a").children("span.title").text();
						l = '<li class="hidden-inline-xs"><a href="#" title="TODO">'
								+ e
								+ '</a> <i class="fa fa-angle-right"></i></li>'
								+ l;
						j = j.parent("li").parent(".sub-menu")
					}
				}
				l = '<li><a href="#dashboard" class="btn-dashboard"><i class="fa fa-home"></i></a></li> '+ l;
				h.data("path", l)
			}
			var i = 1;
			c.find("> li").each(function() {
				$(this).removeClass("active");
				var m = $(this).attr("count");
				if (m) {
					if (Number(m) > i) {
						i = Number(m)
					}
				}
			});
			h.parent("li").addClass("active");
			h.parent("li").attr("count", i + 1);
			g.find("> li:not(.btn-group)").remove();
			g.append(h.data("path"));
			g.find("> li:not(.btn-group) > a[href='" + b + "']").click(
					function(m) {
						m.preventDefault();
						k.ajaxGetUrl(b)
					})
		},
		addOrActiveTab : function(b, e) {
			var a = b.parent("div");
			var d = "tab_" + Util.hashCode(e.url);
			var f = $("#" + d);
			if ($("#" + d).length == 0) {
				var g = $('<li><a id="'
						+ d
						+ '" data-toggle="tab" href="'
						+ e.url
						+ '">'
						+ e.title
						+ ' <button class="close" type="button" style="margin-left:8px"></button></a></li>');
				b.append(g);
				$("#" + d).click();
				var c = b.parent().find(g.find("a").attr("href"));
				c.addClass("tab-closable");
				g
						.find(".close")
						.click(
								function() {
									var i = false;
									c
											.find(
													"form[method='post']:not(.form-track-disabled)[form-data-modified='true']")
											.each(
													function() {
														var k = $(this);
														if (!confirm("当前表单有修改数据未保存，确认离开当前表单吗？")) {
															i = true;
															return false
														}
													});
									if (!i) {
										c.remove();
										g.remove();
										var h = 0;
										var j = b.find("li:not(.tools) > a");
										j.each(function() {
											var k = $(this).attr("click-idx");
											if (k && Number(k) > h) {
												h = Number(k)
											}
										});
										if (h == 0) {
											j.first().click()
										} else {
											j.filter("[click-idx='" + h + "']")
													.click()
										}
									}
								})
			} else {
				$("#" + d).tab("show")
			}
		},
		fixCloneElements : function(a) {
			a.find(".date-picker").each(function() {
				$(this).attr("id", "").removeData().off();
				$(this).find("button").removeData().off();
				$(this).find("input").removeData().off();
				$(this).datepicker({
					language : "zh-CN",
					autoclose : true,
					format : $(this).attr("data-date-format")
				})
			})
		}
	}
}();