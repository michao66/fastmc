var Page = function() {
	var IMAGE_URL_PREFIX = "http://img.iyoubox.com/GetFileByCode.aspx?code=";
	var initMultiimage = function() {
		$("[data-multiimage]").each(
						function() {
							var $el = $(this);
							if ($el.attr("multiimage-done")) {
								return
							}
							if ($el.attr("id") == undefined) {
								$el.attr("id", "multiimage_id_"
										+ new Date().getTime())
							}
							var name = $el.attr("name");
							var options = {
								width : "150",
								height : "150"
							};
							if ($el.attr("data-multiimage-width")) {
								options.width = $el
										.attr("data-multiimage-width")
							}
							if ($el.attr("data-multiimage-height")) {
								options.height = $el
										.attr("data-multiimage-height")
							}
							var control = $el.closest("div.controls");
							var thumbnail = $(
									'<div class="thumbnail thumbnail-'
											+ $el.attr("data-multiimage")
											+ '" style="float:left; margin-right: 5px; width: '
											+ options.width + 'px"/>')
									.appendTo(control);
							thumbnail.append($el);
							var pkValue = $el.attr("data-pk");
							if (pkValue) {
								var $pk = $('<input type="hidden" name="'
										+ name.substring(0, name.indexOf("]"))
										+ '.id" />');
								$pk.val(pkValue);
								thumbnail.append($pk);
								var $op = $('<input type="hidden" name="'
										+ name.substring(0, name.indexOf("]"))
										+ '.extraAttributes.operation" />');
								$op.val("update");
								thumbnail.append($op)
							}
							var addDiv = $('<div class="div-add-img" style="line-height: '
									+ options.height
									+ 'px; background-color: #EEEEEE; text-align: center;"/>');
							addDiv
									.append('<p style="margin:0px"><button class="btn btn-large" type="button">点击上传图片</button></p>');
							var caption = $('<div class="caption" style="height: 15px;padding-top:0px">');
							var addLi = $('<a class="btn-add pull-right" style="cursor: pointer;" title="点击上传图片"><i class="fa fa-plus"></i></a>');
							var shareLi = $('<a class="btn-view" style="cursor: pointer;"  title="查看原始图片"><i class="fa fa-picture-o"></i></a>');
							var minusLi = $('<a class="btn-remove pull-right"  style="cursor: pointer;" title="点击移除图片"><i class="fa fa-minus"></i></a>');
							var imageSrc = $el.val();
							if (imageSrc == undefined || imageSrc == "") {
								addDiv.appendTo(thumbnail);
								caption.appendTo(thumbnail);
								caption.append(addLi)
							} else {
								if (IMAGE_URL_PREFIX) {
									thumbnail
											.append('<img src="'
													+ IMAGE_URL_PREFIX
													+ imageSrc
													+ '" style="cursor: pointer; width: '
													+ options.width
													+ "px; height: "
													+ options.height
													+ 'px;" />')
								} else {
									thumbnail
											.append('<img src="'
													+ imageSrc
													+ '" style="cursor: pointer; width: '
													+ options.width
													+ "px; height: "
													+ options.height
													+ 'px;" />')
								}
								caption.appendTo(thumbnail);
								caption.append(shareLi);
								caption.append(minusLi)
							}
							control.css("min-height",
									(Number(options.height) + 50) + "px");
							control.append(control.find(".thumbnail-btn"));
							var picsEditor = KindEditor.editor({
								allowFileManager : false
							});
							var updateCallback = function() {
								var index = 1000;
								var pkCount = control.find(
										".thumbnail[data-pk]").size();
								alert(pkCount);
								form.thumbnails.find("li.img-item").each(
										function(idx, item) {
											$(this).find("input[name^='commodityPics']").each(
													function() {
														var oldName = $(this).attr("name");
														var newName = oldName.substring(0,oldName.indexOf("[") + 1);
														newName += idx;
														newName += oldName.substring(oldName.indexOf("]"),oldName.length);
														$(this).attr("name",newName)
											});
											$(this).find("input[name$='picIndex']").val(index);
												index -= 100
											})
							};
							control.sortable({
								items : ".thumbnail",
								stop : function(event, ui) {
									event.preventDefault();
									event.stopPropagation()
								}
							});
							thumbnail
									.delegate(
											"div.div-add-img, a.btn-add",
											"click",
											function() {
												picsEditor
														.loadPlugin(
																"multiimage",
																function() {
																	picsEditor.plugin
																			.multiImageDialog({
																				clickFn : function(
																						urlList) {
																					KindEditor
																							.each(
																									urlList,
																									function(
																											i,
																											data) {
																										var $new = $('<input type="hidden" name="'
																												+ name
																												+ '" data-multiimage="edit" />');
																										$new
																												.val(data.md5);
																										thumbnail
																												.before($new);
																										initMultiimage()
																									});
																					picsEditor
																							.hideDialog()
																				}
																			})
																})
											});
							thumbnail
									.delegate(
											"img",
											"click",
											function() {
												picsEditor
														.loadPlugin(
																"image",
																function() {
																	picsEditor.plugin
																			.imageDialog({
																				clickFn : function(
																						url,
																						title,
																						width,
																						height,
																						border,
																						align) {
																					var imageSrc = url;
																					if (IMAGE_URL_PREFIX) {
																						imageSrc = url
																								.split(IMAGE_URL_PREFIX)[1]
																					}
																					$el
																							.val(imageSrc);
																					if (thumbnail
																							.find("img").length == 0) {
																						addDiv
																								.hide();
																						thumbnail
																								.prepend('<img src="'
																										+ url
																										+ '" style="cursor: pointer; width: '
																										+ options.width
																										+ "; height: "
																										+ options.height
																										+ ';" />');
																						caption
																								.empty();
																						caption
																								.append(shareLi);
																						caption
																								.append(minusLi)
																					} else {
																						thumbnail
																								.find(
																										"img")
																								.attr(
																										{
																											src : url
																										})
																					}
																					picsEditor
																							.hideDialog()
																				}
																			})
																})
											});
							thumbnail
									.delegate(
											"a.btn-remove",
											"click",
											function() {
												var thumbnail = $(this)
														.closest(".thumbnail");
												if (thumbnail
														.find("input[name$='.id']")) {
													thumbnail
															.find(
																	"input[name$='.extraAttributes.operation']")
															.val("remove");
													thumbnail.hide()
												} else {
													thumbnail.remove()
												}
											});
							thumbnail.delegate("a.btn-view", "click",
									function() {
										window.open(thumbnail.find("img").attr(
												"src"), "_blank")
									});
							$el.attr("multiimage-done", true)
						})
	};
	return {
		initAjaxBeforeShow : function($container) {
			$.fn.treeselect = function(opts) {
				var $elem = $(this);
				if ($elem.attr("treeselect-done")) {
					return this
				}
				opts = $.extend({
					url : $elem.attr("data-url"),
					position : $elem.attr("data-position")
				}, $elem.data("treeOptions") || {}, opts);
				var id = new Date().getTime();
				$elem.attr("id", id);
				var $treeDisplayScope = $elem.closest(".panel-content");
				var $toggle = $('<i class="fa fa-angle-double-down btn-toggle"></i>').insertBefore($elem);
				var $elems = $elem.parent().children();
				$elems.wrapAll('<div class="input-icon right"></div>');
				var $dropdownZtreeContainer = $('<div style="z-index: 990; display: none; position: absolute; background-color: #FFFFFF; border: 1px solid #DDDDDD"></div>');
				$dropdownZtreeContainer.appendTo($treeDisplayScope);
				var toolbar = [];
				toolbar.push('<div role="navigation" class="navbar navbar-default" style="border: 0px;">');
				toolbar.push('<div class="collapse navbar-collapse navbar-ex1-collapse" style="padding: 0">');
				toolbar.push('<form role="search" class="navbar-form navbar-left">');
				toolbar.push('<div class="form-group" style="border-bottom: 0px">');
				toolbar.push('<input type="text" name="keyword" class="form-control input-small">');
				toolbar.push("</div>");
				toolbar.push('<button class="btn blue" type="submit">查询</button>');
				toolbar.push("</form>");
				toolbar.push('<ul class="nav navbar-nav navbar-right">');
				toolbar.push('<li><a href="#" class="btn-open-all" style="padding-left: 0">展开</li>');
				toolbar.push('<li><a href="#" class="btn-close-all" style="padding-left: 0">收拢</a></li>');
				toolbar.push('<li><a href="#" class="btn-clear" style="padding-left: 0;padding-right: 20px">清除</a></li>');
				toolbar.push("</ul>");
				toolbar.push("</div>");
				toolbar.push("</div>");
				var $toolbar = $(toolbar.join("")).appendTo($dropdownZtreeContainer);
				var $treeContainer = $('<div style="max-height: 300px;overflow: auto"></div>').appendTo($dropdownZtreeContainer);
				var $ztree = $('<ul class="ztree"></ul>').appendTo($treeContainer);
				$ztree.attr("id", "ztree_" + id);
				$ztree.attr("id-for", id);
				$ztree.attr("data-url", opts.url);
				$.fn.zTree.init($ztree, {
					callback : {
						onClick : function(event, treeId, treeNode) {
							if (opts.callback.onSingleClick) {
								var ret = opts.callback.onSingleClick.call(
										this, event, treeId, treeNode);
								if (ret == undefined || ret == true) {
									$dropdownZtreeContainer.hide();
									$toggle.removeClass("fa-angle-double-up");
									$toggle.addClass("fa-angle-double-down")
								}
							}
							event.stopPropagation();
							event.preventDefault();
							return false
						}
					}
				}, Util.getCacheDatas(opts.url));
				$elem.click(
						function() {
							var zTree = $.fn.zTree.getZTreeObj($ztree.attr("id"));
							zTree.cancelSelectedNode();
							if ($.trim($elem.val()) != "") {
									var nodeList = zTree.getNodesByParamFuzzy("name",$elem.val());
										for ( var i = 0, l = nodeList.length; i < l; i++) {
											var node = nodeList[i];
											zTree.selectNode(node)
										}
									}
									$dropdownZtreeContainer.children(".ztree").hide();
									$ztree.show();
									var width = $elem.outerWidth();
									if (width < 330) {
										width = 330
									}
									$dropdownZtreeContainer.css({width : width + "px"}).slideDown("fast");
									$dropdownZtreeContainer.position($.extend(true, {
												my : "right top",
												at : "right bottom",
												of : $elem.parent("div")
											}, opts.position));
									$toggle.removeClass("fa-angle-double-down");
									$toggle.addClass("fa-angle-double-up")
								}).keydown(function() {
							return false
						});
				$toolbar.find("form").submit(function(event) {
					var word = $toolbar.find("input[name='keyword']").val();
					var zTree = $.fn.zTree.getZTreeObj($ztree.attr("id"));
					zTree.cancelSelectedNode();
					var nodeList = zTree.getNodesByParamFuzzy("name", word);
					for ( var i = 0, l = nodeList.length; i < l; i++) {
						var node = nodeList[i];
						zTree.selectNode(node, true)
					}
					event.stopPropagation();
					event.preventDefault();
					return false
				});
				$toolbar.find(".btn-open-all").click(function(event) {
					var zTree = $.fn.zTree.getZTreeObj($ztree.attr("id"));
					zTree.expandAll(true);
					event.stopPropagation();
					event.preventDefault();
					return false
				});
				$toolbar.find(".btn-close-all").click(function(event) {
					var zTree = $.fn.zTree.getZTreeObj($ztree.attr("id"));
					zTree.expandAll(false);
					event.stopPropagation();
					event.preventDefault();
					return false
				});
				$toolbar.find(".btn-clear").click(function(event) {
					if (opts.callback.onClear) {
						opts.callback.onClear.call(this, event)
					}
					$dropdownZtreeContainer.hide();
					$toggle.removeClass("fa-angle-double-up");
					$toggle.addClass("fa-angle-double-down");
					event.stopPropagation();
					event.preventDefault();
					return false
				});
				$(document).on("mousedown",
					 function(e) {
							var $container = $dropdownZtreeContainer;
							var $el = $elem;
							var tagName = e.target.tagName;
							if (tagName == "HTML") {
								return
							}
							if (!($el.is(e.target) || $el.find(e.target).length|| $container.is(e.target) || $container
									.find(e.target).length)) {
								$container.hide()
							}
						});
				$elem.attr("treeselect-done", true)
			};
			if ($container == undefined) {
				$container = $("body")
			}
			//initMultiimage();
			//$(".make-switch:not(.has-switch)")["bootstrapSwitch"]();
			$(".date-picker", $container).each(function() {
				var $datapicker = $(this);
				var $el = $datapicker.find(" > .form-control");
				var timeInput = $el.attr("data-timepicker");
				if (BooleanUtil.toBoolean(timeInput)) {
					$el.datetimepicker({
						autoclose : true,
						format : "yyyy-mm-dd hh:ii:ss",
						todayBtn : true,
						language : "zh-CN",
						minuteStep : 10
					})
				} else {
					$datapicker.datepicker({
						language : "zh-CN",
						autoclose : true
					})
				}
				$("body").removeClass("modal-open")
			});
			$("table[data-dynamic-table]", $container).each(function() {
				var $dynamicTable = $(this);
				var options = $dynamicTable.data("dynamicTableOptions");
				$(this).dynamictable(options)
			});
			if (jQuery().select2) {
				$("select.select2me", $container)
						.each(
								function() {
									var $select2 = $(this);
									var allowClear = true;
									if ($select2.attr("data-allowClear")) {
										if ($select2.attr("data-allowClear") == "false") {
											allowClear = false
										}
									}
									var options = {
										placeholder : "请选择...",
										allowClear : allowClear
									};
									if ($select2.attr("data-select2-type") == "combobox") {
										var $input = $(
												'<input type="text" name="'
														+ $(this).attr("name")
														+ '"/>').insertAfter(
												$select2);
										if ($select2.attr("class") != undefined) {
											$input.attr("class", $select2
													.attr("class"))
										}
										var selected = $select2.find(
												"option:selected").val();
										options = {
											placeholder : "请选择或输入...",
											allowClear : true,
											query : function(query) {
												var data = {
													results : []
												};
												$select2.find("option").each(
														function() {
															data.results.push({
																id : $(this)
																		.val(),
																text : $(this)
																		.text()
															})
														});
												query.callback(data)
											},
											initSelection : function(element,
													callback) {
												if (selected != undefined) {
													var data = {
														id : selected,
														text : selected
													};
													callback(data)
												}
											},
											createSearchChoice : function(term,
													data) {
												if ($(data)
														.filter(
																function() {
																	return this.text
																			.localeCompare(term) === 0
																}).length === 0) {
													return {
														id : term,
														text : term
													}
												}
											}
										};
										$input.select2(options);
										if (selected != undefined) {
											$input.select2("val", [ selected ])
										}
										$select2.remove()
									} else {
										var dataCache = $select2
												.attr("data-cache");
										if (dataCache) {
											var dataCache = eval(dataCache);
											for ( var i in dataCache) {
												$select2
														.append("<option value='"
																+ i
																+ "'>"
																+ dataCache[i]
																+ "</option>")
											}
										}
										$select2.select2(options)
									}
								})
			}
			$("input.select2tags", $container).each(function() {
				var $select2tags = $(this);
				$select2tags.select2({
					tags : [ "审核通过", "采购价格太高", "优选供货商" ]
				})
			});
			$(".tooltipster", $container).each(function() {
				var $el = $(this);
				var position = "top";
				if ($el.find("[data-rule-date]").length > 0) {
					position = "right"
				}
				if ($el.attr("data-tooltipster-position")) {
					position = $el.attr("data-tooltipster-position")
				}
				$el.tooltipster({
					contentAsHTML : true,
					theme : "tooltipster-punk",
					trigger : "custom",
					position : position
				})
			});
			/*
			$("textarea[maxlength]", $container).maxlength({
				limitReachedClass : "label label-danger",
				alwaysShow : true
			});*/
			$('[data-toggle="dropdownselect"]', $container)
					.each(
							function() {
								var $dropdown = $(this);
								$dropdown.attr("autocomplete", "off");
								var $parent = $dropdown
										.closest(".panel-content");
								var $container = $(
										'<div class="container-dropdownselect container-callback" style="display : none;background-color: white;border: 1px solid #CCCCCC;"></div>')
										.appendTo($parent);
								$dropdown
										.add(
												$dropdown.parent().find(
														"i.btn-toggle"))
										.click(
												function() {
													var parentOffset = $parent
															.offset();
													var offset = $dropdown
															.offset();
													$container
															.css({
																width : $dropdown
																		.outerWidth(),
																position : "absolute",
																left : (offset.left - parentOffset.left)
																		+ "px",
																top : (offset.top - parentOffset.top)
																		+ $dropdown
																				.outerHeight()
																		+ "px"
															});
													$container
															.data(
																	"callback",
																	$dropdown
																			.data("data-dropdownselect-callback"));
													$container
															.slideToggle("fast");
													var $btnToggle = $dropdown
															.parent()
															.find(
																	"i.btn-toggle");
													if ($btnToggle
															.hasClass("fa-angle-double-down")) {
														$btnToggle
																.removeClass("fa-angle-double-down");
														$btnToggle
																.addClass("fa-angle-double-up")
													} else {
														$btnToggle
																.addClass("fa-angle-double-down");
														$btnToggle
																.removeClass("fa-angle-double-up")
													}
													if ($container.is(":empty")) {
														var url = $dropdown
																.attr("data-url");
														if ($dropdown
																.attr("data-selected")) {
															if (url
																	.indexOf("?") > -1) {
																url = url
																		+ "&selected="
																		+ $dropdown
																				.attr("data-selected")
															} else {
																url = url
																		+ "?selected="
																		+ $dropdown
																				.attr("data-selected")
															}
														}
														$container
																.ajaxGetUrl(url)
													}
												});
								$(document)
										.on(
												"mousedown",
												function(e) {
													var $el = $dropdown
															.parent("div");
													if (!($el.is(e.target)
															|| $el
																	.find(e.target).length
															|| $container
																	.is(e.target) || $container
															.find(e.target).length)) {
														$container.hide()
													}
												})
							});
			$("[data-htmleditor='kindeditor']").each(function() {
				var $kindeditor = $(this);
				var height = $kindeditor.attr("data-height");
				if (height == undefined) {
					height = "500px"
				}
				KindEditor.create($kindeditor, {
					allowFileManager : false,
					width : "100%",
					height : height,
					minWidth : "200px",
					minHeight : "60px",
					afterBlur : function() {
						this.sync()
					}
				})
			});
			$('.tabbable > .nav > li > a[href="#tab-auto"]').each(
					function() {
						var $link = $(this);
						var $tabbable = $link.closest(".tabbable");
						var idx = $tabbable.children(".nav").find(
								"li:not(.tools)").index($link.parent("li"));
						var $tabPane = $tabbable.children(".tab-content").find(
								"div.tab-pane").eq(idx);
						var tabid = "tab-" + new Date().getTime() + idx;
						$tabPane.attr("id", tabid);
						$link.attr("href", "#" + tabid)
					});
			$("a.x-editable").each(function() {
				var $edit = $(this);
				var url = $edit.attr("data-url");
				if (url == undefined) {
					url = $edit.closest("form").attr("action")
				}
				var pk = $edit.attr("data-pk");
				if (pk == undefined) {
					pk = $edit.closest("form").find("input[name='id']").val()
				}
				var title = $edit.attr("data-original-title");
				if (title == undefined) {
					title = "数据修改"
				}
				var placement = $edit.attr("data-placement");
				if (placement == undefined) {
					placement = "top"
				}
				Util.assertNotBlank(url);
				Util.assertNotBlank(pk);
				$edit.editable({
					url : url,
					pk : pk,
					title : title,
					placement : placement,
					params : function(params) {
						params.id = pk;
						params[params.name] = params.value;
						return params
					},
					validate : function(value) {
						var required = $edit.attr("data-required");
						if (required == "true") {
							if ($.trim(value) == "") {
								return "数据不能为空"
							}
						}
					}
				})
			});
			$("[data-sigleimage]")
					.each(
							function() {
								var $el = $(this);
								if ($el.attr("sigleimage-done")) {
									return
								}
								if ($el.attr("id") == undefined) {
									$el.attr("id", "sigleimage_id_"
											+ new Date().getTime())
								}
								var options = {
									width : "150px",
									height : "150px"
								};
								if ($el.attr("data-sigleimage-width")) {
									options.width = $el
											.attr("data-sigleimage-width")
								}
								if ($el.attr("data-sigleimage-height")) {
									options.height = $el
											.attr("data-sigleimage-height")
								}
								var control = $el.closest("div.form-group")
										.children("div");
								var thumbnail = $(
										'<div class="thumbnail" style=" width: '
												+ options.width + '"/>')
										.appendTo(control);
								var addDiv = $('<div class="div-add-img" style="line-height: '
										+ options.height
										+ '; background-color: #EEEEEE; text-align: center;"/>');
								addDiv
										.append('<p><button class="btn btn-large" type="button">点击上传图片</button></p>');
								var caption = $('<div class="caption" style="height: 15px;padding-top:0px">');
								var addLi = $('<a class="btn-add pull-right" style="cursor: pointer;" title="点击上传图片"><i class="fa fa-plus"></i></a>');
								var shareLi = $('<a class="btn-view" style="cursor: pointer;"  title="查看原始图片"><i class="fa fa-picture-o"></i></a>');
								var minusLi = $('<a class="btn-remove pull-right"  style="cursor: pointer;" title="点击移除图片"><i class="fa fa-minus"></i></a>');
								var imageSrc = $el.val();
								if (imageSrc == undefined || imageSrc == "") {
									addDiv.appendTo(thumbnail);
									caption.appendTo(thumbnail);
									caption.append(addLi)
								} else {
									if (IMAGE_URL_PREFIX) {
										thumbnail
												.append('<img src="'
														+ IMAGE_URL_PREFIX
														+ imageSrc
														+ '" style="cursor: pointer; width: '
														+ options.width
														+ "; height: "
														+ options.height
														+ ';" />')
									} else {
										thumbnail
												.append('<img src="'
														+ imageSrc
														+ '" style="cursor: pointer; width: '
														+ options.width
														+ "; height: "
														+ options.height
														+ ';" />')
									}
									caption.appendTo(thumbnail);
									caption.append(shareLi);
									caption.append(minusLi)
								}
								var picsEditor = KindEditor.editor({
									allowFileManager : false
								});
								thumbnail.delegate("div.div-add-img, a.btn-add , img","click",
										function() {
												picsEditor.loadPlugin("image",function() {
													picsEditor.plugin.imageDialog({
													clickFn : function(url,title,width,height,border,align) {
													var imageSrc = url;
													if (IMAGE_URL_PREFIX) {
														imageSrc = url.split(IMAGE_URL_PREFIX)[1]
													}
													$el.val(imageSrc);
													if (thumbnail.find("img").length == 0) {
														addDiv.hide();
														thumbnail.prepend('<img src="'+ url
															+ '" style="cursor: pointer; width: '+ options.width
															+ "; height: "
															+ options.height
															+ ';" />');
													caption.empty();
													caption.append(shareLi);
													caption.append(minusLi)
												} else {
													thumbnail.find("img").attr({src : url
													})
												}
												picsEditor.hideDialog()
												}
										     })
										  })
									  });
								thumbnail.delegate("a.btn-remove","click",
											function() {
												$el.val("");
												thumbnail.find("img").remove();
												if (thumbnail.find("div.div-add-img").length == 0) {
														thumbnail.prepend(addDiv)
													} else {
														addDiv.show()
													}
													caption.empty();
													caption.append(addLi)
												});
								thumbnail.delegate("a.btn-view", "click",function() {
										window.open(thumbnail.find("img").attr("src"), "_blank")
								});
								$el.attr("sigleimage-done", true)
							});
			  $(".scroller", $container).each(
						function() {
								var height;
								if ($(this).attr("data-height")) {
									height = $(this).attr("data-height")
								} else {
									height = $(this).css("height")
								}
								$(this).slimScroll({
										size : "7px",
										color :($(this).attr("data-handle-color") ? $(this).attr(
											"data-handle-color"): "#a1b2bd"),
											railColor : ($(this).attr(
												"data-rail-color") ? $(this).attr("data-rail-color"): "#333"),
												position : "right",
												height : height,
												alwaysVisible : ($(this).attr("data-always-visible") == "1" ? true: false),
													railVisible : ($(this).attr("data-rail-visible") == "1" ? true: false),
													disableFadeOut : true
												})
							});
			//$('[data-hover="dropdown"]', $container).dropdownHover();
			$("div.ajaxify", $container).each(
					function() {
						$(this).ajaxGetUrl($(this).attr("data-url"),$(this).data("success"))
					})
		},
		initAjaxAfterShow : function($container) {
			if ($container == undefined) {
				$container = $("body")
			}
			$(".form-body .row", $container).each(
					function() {
							var maxHeight = 0;
							var $rowcols = $(this).find(" > div > .form-group > div, > .form-group > div");
							$rowcols.each(function() {
									var height = $(this).innerHeight();
									if (height > maxHeight) {
										maxHeight = height
									}
								});
							$rowcols.css("min-height", maxHeight)
			});
			$(".chart-plot", $container).each(function() {
				$(this).plot()
			})
		},
		doSomeStuff : function() {
			myFunc()
		}
	}
}();