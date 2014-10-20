var FormValidation = function() {
	return {
		init : function() {
			$.fn.setFormDatas = function(data, forceOverwrite) {
				var $container = $(this);
				for ( var key in data) {
					var el = "input[name='" + key + "'],select[name='" + key+ "'],textarea[name='" + key + "']";
					var $el = $container.find(el);
					if (forceOverwrite == false) {
						if ($.trim($el.val()) != "") {
							continue
						}
					}
					var val = data[key];
					$el.val(val);
					if ($el.is("select")) {
						$el.select2()
					}
				}
			}, jQuery.validator.addMethod("unique", function(value, element) {
				var form = $(element).closest("form");
				var url = form.attr("action").split("!")[0]+ "!checkUnique?element=" + $(element).attr("name");
				var id = form.find("input[name='id']");
				if (id.size() > 0) {
					url = url + "&id=" + id.val()
				}
				return $.validator.methods.remote.call(this, value, element,url)}, "数据已存在");
			
			   jQuery.validator.addMethod("timestamp",function(value, element) {
								if (value == "") {
									return this.optional(element)
								}
								var regex = /^(?:[0-9]{4})-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
								if (!regex.test(value)) {
									return false
								}
								return true
							}, "请输入合法的日期时间格式（如2011-08-15 13:40:00）");
			   
			  jQuery.validator.addMethod("yearMonth", function(value, element) {
					if (value == "") {
						return this.optional(element)
					}
					var regex = /^(?:[0-9]{4})(?:(?:0[1-9])|(?:1[0-2]))$/;
					if (!regex.test(value)) {
						return false
					}
					return true
				}, "请输入合法的年月格式（如201201）");
			  
			jQuery.validator.addMethod("startWith", function(value, element,param) {
				if (this.optional(element)) {
					return true
				}
				if (param.length > value.length) {
					return false
				}
				if (value.substr(0, param.length) == param) {
					return true
				} else {
					return false
				}
			}, "请输入以{0}开头字符串");
			
			jQuery.validator.addMethod("dateLT",function(value, element, param) {
						if (value == "") {
							return this.optional(element)
						}
						var endDate = $(param).val();
						if (endDate == "") {
							return true
						}
						var startDate = eval("new Date("
								+ value.replace(/[\-\s:]/g, ",") + ")");
						endDate = eval("new Date("
								+ endDate.replace(/[\-\s:]/g, ",") + ")");
						if (startDate > endDate) {
							return false
						} else {
							return true
						}
					}, "输入的日期数据必须小于结束日期");
			
			jQuery.validator.addMethod("dateGT",function(value, element, param) {
						if (value == "") {
							return this.optional(element)
						}
						var startDate = $(param).val();
						if (startDate == "") {
							return true
						}
						var endDate = eval("new Date("
								+ value.replace(/[\-\s:]/g, ",") + ")");
						startDate = eval("new Date("
								+ startDate.replace(/[\-\s:]/g, ",") + ")");
						if (startDate > endDate) {
							return false
						} else {
							return true
						}
					}, "输入的日期数据必须大于开始日期");
			
			var idCardNoUtil = {
				provinceAndCitys : {
					11 : "北京",
					12 : "天津",
					13 : "河北",
					14 : "山西",
					15 : "内蒙古",
					21 : "辽宁",
					22 : "吉林",
					23 : "黑龙江",
					31 : "上海",
					32 : "江苏",
					33 : "浙江",
					34 : "安徽",
					35 : "福建",
					36 : "江西",
					37 : "山东",
					41 : "河南",
					42 : "湖北",
					43 : "湖南",
					44 : "广东",
					45 : "广西",
					46 : "海南",
					50 : "重庆",
					51 : "四川",
					52 : "贵州",
					53 : "云南",
					54 : "西藏",
					61 : "陕西",
					62 : "甘肃",
					63 : "青海",
					64 : "宁夏",
					65 : "新疆",
					71 : "台湾",
					81 : "香港",
					82 : "澳门",
					99 : "其他"
				},
				powers : [ "7", "9", "10", "5", "8", "4", "2", "1", "6", "3","7", "9", "10", "5", "8", "4", "2" ],
				parityBit : [ "1", "0", "X", "9", "8", "7", "6", "5", "4", "3","2" ],
				genders : {
					male : "男",
					female : "女"
				},
				checkAddressCode : function(addressCode) {
					var check = /^[1-9]\d{5}$/.test(addressCode);
					if (!check) {
						return false
					}
					if (idCardNoUtil.provinceAndCitys[parseInt(addressCode
							.substring(0, 2))]) {
						return true
					} else {
						return false
					}
				},
				checkBirthDayCode : function(birDayCode) {
					var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/
							.test(birDayCode);
					if (!check) {
						return false
					}
					var yyyy = parseInt(birDayCode.substring(0, 4), 10);
					var mm = parseInt(birDayCode.substring(4, 6), 10);
					var dd = parseInt(birDayCode.substring(6), 10);
					var xdata = new Date(yyyy, mm - 1, dd);
					if (xdata > new Date()) {
						return false
					} else {
						if ((xdata.getFullYear() == yyyy)
								&& (xdata.getMonth() == mm - 1)
								&& (xdata.getDate() == dd)) {
							return true
						} else {
							return false
						}
					}
				},
				getParityBit : function(idCardNo) {
					var id17 = idCardNo.substring(0, 17);
					var power = 0;
					for ( var i = 0; i < 17; i++) {
						power += parseInt(id17.charAt(i), 10)
								* parseInt(idCardNoUtil.powers[i])
					}
					var mod = power % 11;
					return idCardNoUtil.parityBit[mod]
				},
				checkParityBit : function(idCardNo) {
					var parityBit = idCardNo.charAt(17).toUpperCase();
					if (idCardNoUtil.getParityBit(idCardNo) == parityBit) {
						return true
					} else {
						return false
					}
				},
				checkIdCardNo : function(idCardNo) {
					if (idCardNo.startWith("99")) {
						return true
					}
					var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
					if (!check) {
						return false
					}
					if (idCardNo.length == 15) {
						return idCardNoUtil.check15IdCardNo(idCardNo)
					} else {
						if (idCardNo.length == 18) {
							return idCardNoUtil.check18IdCardNo(idCardNo)
						} else {
							return false
						}
					}
				},
				check15IdCardNo : function(idCardNo) {
					var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/
							.test(idCardNo);
					if (!check) {
						return false
					}
					var addressCode = idCardNo.substring(0, 6);
					check = idCardNoUtil.checkAddressCode(addressCode);
					if (!check) {
						return false
					}
					var birDayCode = "19" + idCardNo.substring(6, 12);
					return idCardNoUtil.checkBirthDayCode(birDayCode)
				},
				check18IdCardNo : function(idCardNo) {
					var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/
							.test(idCardNo);
					if (!check) {
						return false
					}
					var addressCode = idCardNo.substring(0, 6);
					check = idCardNoUtil.checkAddressCode(addressCode);
					if (!check) {
						return false
					}
					var birDayCode = idCardNo.substring(6, 14);
					check = idCardNoUtil.checkBirthDayCode(birDayCode);
					if (!check) {
						return false
					}
					return check
				},
				formateDateCN : function(day) {
					if (idCardNoUtil.checkBirthDayCode(day)) {
						var yyyy = day.substring(0, 4);
						var mm = day.substring(4, 6);
						var dd = day.substring(6);
						return yyyy + "-" + mm + "-" + dd
					}
					return ""
				},
				getIdCardInfo : function(idCardNo) {
					var idCardInfo = {
						gender : "",
						birthday : ""
					};
					if (idCardNo.length == 15) {
						var aday = "19" + idCardNo.substring(6, 12);
						idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
						if (parseInt(idCardNo.charAt(14)) % 2 == 0) {
							idCardInfo.gender = idCardNoUtil.genders.female
						} else {
							idCardInfo.gender = idCardNoUtil.genders.male
						}
					} else {
						if (idCardNo.length == 18) {
							var aday = idCardNo.substring(6, 14);
							idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
							if (parseInt(idCardNo.charAt(16)) % 2 == 0) {
								idCardInfo.gender = idCardNoUtil.genders.female
							} else {
								idCardInfo.gender = idCardNoUtil.genders.male
							}
						}
					}
					return idCardInfo
				},
				getId15 : function(idCardNo) {
					if (idCardNo.length == 15) {
						return idCardNo
					} else {
						if (idCardNo.length == 18) {
							return idCardNo.substring(0, 6)
									+ idCardNo.substring(8, 17)
						} else {
							return null
						}
					}
				},
				getId18 : function(idCardNo) {
					if (idCardNo.length == 15) {
						var id17 = idCardNo.substring(0, 6) + "19"
								+ idCardNo.substring(6);
						var parityBit = idCardNoUtil.getParityBit(id17);
						return id17 + parityBit
					} else {
						if (idCardNo.length == 18) {
							return idCardNo
						} else {
							return null
						}
					}
				}
			};
			jQuery.validator.addMethod("idCardNo", function(value, element,param) {
				return this.optional(element)|| idCardNoUtil.checkIdCardNo(value)}, "请输入有效的身份证件号");
			
			jQuery.validator.addMethod("phone", function(value, element) {
				var phone = /^\d|-$/;
				return this.optional(element) || (phone.test(value))
			}, "请输入有效的电话号码：数字或'-'");
			
			jQuery.validator.addMethod("zipCode", function(value, element) {
				var tel = /^[0-9]{6}$/;
				return this.optional(element) || (tel.test(value))
			}, "请输入有效的6位数字邮政编码");
			
			jQuery.validator.addMethod("numberEndWithPointFive", function(value, element) {
				var reg = /^(0|[1-9]\d*)([.][05])?$/;
				return this.optional(element) || (reg.test(value))
			}, "必须以.0或.5作为小数结尾");
			
			jQuery.validator.addMethod("equalToByName", function(value,element, param) {
				var target = $(element).closest("form").find(
						"input[name='" + param + "']");
				if (this.settings.onfocusout) {
					target.unbind(".validate-equalTo").bind(
							"blur.validate-equalTo", function() {
								$(element).valid()
							})
				}
				return value === target.val()
			}, "请输入前后相等数据");
			
			$.validator.addMethod("regex", function(value, element, regexp) {
				var re = new RegExp(regexp);
				return this.optional(element) || re.test(value)
			}, "数据校验未通过")
		},
		initAjax : function($container) {
			if ($container == undefined) {
				$container = $("body")
			}
			$('.tip').poshytip({
				className: 'tip-yellow',
				showOn: 'focus',
				alignTo: 'target',
				alignX: 'center',
				alignY: 'top',
				offsetX: 0,
				offsetY: 5,
				allowTipHover: false

			});

			$("form.form-validation", $container).each(
							function() {
								var $form = $(this);
								var dataGridSearch = $form.attr("data-grid-search");
									$form.find('[type="reset"]').click(function() {
									if (dataGridSearch) {
										setTimeout(function() {$form.submit()}, 100);
										return
									}
								});
								var $errorAlert = $('<div class="alert alert-danger display-hide" style="display: none;"/>');
								$errorAlert.append('<button class="close" data-close="alert" type="button"></button>');
								$errorAlert.append("表单数据有误，请检查修正.");
								$errorAlert.prependTo($form);
								var options = $.extend(true, {
									editrulesurl : $form.attr("data-editrulesurl")
								}, $form.data("formOptions") || {});
								var $els = $form.find(":input[type='text'], :input[type='password'], :input[type='radio'], :input[type='checkbox'], :input[type='file'],:input[type='hidden'], select , textarea");
								$els.each(function() {
											var $el = $(this);
											if ($el.attr("data-rule-required")) {
												var $formGroup = $el.closest(".control-group");
												if ($formGroup.find("label.control-label > span.required").length == 0) {
													$formGroup.find("label.control-label").append('<span class="required">*</span>')
												}
											}
										});
								if ($form.attr("method") == "post"&& !$form.hasClass("form-track-disabled")) {
									$els.change(function() {
										$form.attr("form-data-modified","true")
									});
									$form.find("textarea").keyup(
										function() {
												$form.attr("form-data-modified","true")
									});
								}
								var $first = $form.find(".focus").first();
								if ($first.size() == 0) {
									$first = $form.find(":text:enabled").first()
								}
								$first.focus();
								
								$form.validate({
										errorElement : "span",
										errorClass : "error-block",
										focusInvalid : true,
										ignore : "",
										errorPlacement : function(error,element) {
										if (element.parent(".input-group").size() > 0) {
												error.insertAfter(element.parent(".input-group"))
											} else {
													if (element.attr("data-error-container")) {
														error.appendTo(element.attr("data-error-container"))
													} else {
														if (element.parents(".radio-list").size() > 0) {
															error.insertAfter(element.parents(".radio-list"))
														} else {
															if (element.parent(".radio-inline").size() > 0) {
																error.appendTo(element.parent(".radio-inline").parent())
															} else {
																if (element.parents(".checkbox-list")	.size() > 0) {
																	error.insertAfter(element.parents(".checkbox-list"))
																} else {
																	if (element.parent(".checkbox-inline").size() > 0) {
																		error.appendTo(element.parent(".checkbox-inline").parent())
																	} else {
																		error.insertAfter(element)
																	}
																}
															}
														}
													}
												}
											},
											invalidHandler : function(event,validator) {
												$errorAlert.show();
												Global.scrollTo($errorAlert, -200)
											},
											 highlight: function (element) { // hightlight error inputs
								                    $(element).closest('.help-inline').removeClass('ok'); // display OK icon
								                    $(element).closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
								             },
								             unhighlight: function (element) { // revert the change dony by hightlight
								                  $(element).closest('.control-group').removeClass('error'); // set error class to the control group
								             },
								             submitHandler : function(form) {
												var validator = this;
												$errorAlert.hide();
												if (dataGridSearch) {
													var $grid = $('#'+dataGridSearch);
												    var param = $form.serializeObject();
											        var property = param['pager.property'];
											        param[property]=param['searchKey'];
											        $grid.datagrid('load',param);
													return false;
												}
												
												$(form).find(":text").each(
													function() {
														$(this).val($.trim($(this).val()))
												});
		
											    Global.blockUI(form);    
												$(form).ajaxSubmit({
													dataType : "json",
													method : "post",
													success : function(data) {
														 Global.unblockUI(form);
														 if(data.status=='error'){
															
															Global.notify(data.message,"warning")
														 }else{
															Global.notify(data.message);
														    var d = window.GETWIN();
														    if(d.dialog("options").onAjaxComplete) d.dialog("options").onAjaxComplete.call(this,d);
															d.dialog("close");                               
														 }
															
													},
													error : function(xhr,e,status) {
														Global.notify("表单处理异常，请联系管理员","warning")
														Global.unblockUI(form);
													}
													
												});
								              return false;
											}
								  });
		
								  if ($form.attr("method") != undefined && $form.attr("method").toUpperCase() == "POST") {
									 $form.keypress(function(event) {
										var ta = event.target;
										if (ta.tagName === "TEXTAREA") {
											return true
										}
										return event.which != 13
									})
								}
								
							})
		},
		doSomeStuff : function() {
			myFunc()
		}
	}
}();