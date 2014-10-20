var Page = function() {
	return {
		initAjaxBeforeShow : function($container) {
			if ($container == undefined) {
				$container = $("body")
			}
			
	
			
			
			$('input[data-toggle="autocomplete"]',$container).each(function() {
			      var $select2 = $(this);
				  var placeholder = $select2.attr("placeholder");
				  if (placeholder == undefined) {
						placeholder = "请选择..."
				  }
				  var dataUrl = $select2.attr("data-url");
				 
				  var options = {
						placeholder : placeholder,
						minimumInputLength: 2,
						allowClear : true,
						ajax : {
			               url: dataUrl,
			               dataType: 'json',
			               data: function (term, page) {
			                    return {
			                        q_term: term
			                    };
			               },
			               results: function (data, page) {
			                   return { results: data };
			              }
                      },
                       formatResult: function(medata){ return medata.text},
					   formatSelection: function(medata){ return medata.text;  }, 
					   dropdownCssClass: "bigdrop", 
					   escapeMarkup: function (m) { return m; }
				  }
				  
			     $select2.select2(options)
			     var $container = $select2.parent(".controls").children(".select2-container");
					if (!$container.hasClass("form-control")) {
						$container.addClass("form-control")
					}
				  
			});
			$("select[select2]", $container).each(function() {
					var $select2 = $(this);
					if ($select2.find(' > option[value=""]').size() == 0) {
						var $empty = $('<option value=""></option>');
						$select2.prepend($empty)
					}
					if ($select2.find(" > option[selected]").size() == 0) {
						$select2.find("> option[value='']").attr("selected",
								true)
					}
					var allowClear = true;
					if ($select2.attr("data-allowClear")) {
						if ($select2.attr("data-allowClear") == "false") {
							allowClear = false
						}
					}
					var placeholder = $select2.attr("placeholder");
					if (placeholder == undefined) {
						placeholder = "请选择..."
					}
					var options = {
						placeholder : placeholder,
						allowClear : allowClear,
						matcher : function(term, text) {
							var mod = Pinyin.getCamelChars(text) + "";
							var tf1 = mod.toUpperCase().indexOf(term.toUpperCase()) == 0;
							var tf2 = text.toUpperCase().indexOf(term.toUpperCase()) == 0;
							return (tf1 || tf2)
						}
					};
					if ($select2.attr("data-select2-type") == "combobox") {
						var $input = $('<input type="text" name="'+ $(this).attr("name") + '"/>').insertAfter($select2);
						if ($select2.attr("class") != undefined) {
							$input.attr("class", $select2.attr("class"))
						}
						var selected = $select2.find("option:selected").val();
						options = $.extend({}, options, {
									placeholder : "请选择或输入...",
									allowClear : true,
									query : function(query) {
										var data = {
											results : []
										};
										$select2.find("option").each(function() {
												data.results.push({
													id : $(this).val(),
													text : $(this).text()
												})
										});
										query.callback(data)
									},
									initSelection : function(element, callback) {
										if (selected != undefined) {
											var data = {
												id : selected,
												text : selected
											};
											callback(data)
										}
									},
									createSearchChoice : function(term, data) {
										if ($(data).filter(function() {
											return this.text
													.localeCompare(term) === 0
										}).length === 0) {
											return {
												id : term,
												text : term
											}
										}
									}
								});
						$input.select2(options);
						if (selected != undefined) {
							$input.select2("val", [selected])
						}
						$select2.remove()
					} else {
						var dataCache = $select2.attr("data-cache");
						if (dataCache) {
							var dataCache = eval(dataCache);
							for (var i in dataCache) {
								$select2.append("<option value='" + i + "'>"
										+ dataCache[i] + "</option>")
							}
						}
						var dataUrl = $select2.attr("data-url");
						if (dataUrl) {
							var val = $select2.val();
							var dataCache = Util.getCacheSelectOptionDatas(WEB_ROOT+ dataUrl);
							for (var i in dataCache) {
								if (val && val == i) {
									continue
								}
								$select2.append("<option value='" + i + "'>"+ dataCache[i] + "</option>")
							}
						}
						$select2.select2(options)
					}
					var $container = $select2.parent(".controls").children(".select2-container");
					if (!$container.hasClass("form-control")) {
						$container.addClass("form-control")
					}
				});
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