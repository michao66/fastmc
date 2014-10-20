function _getPagerForm(args) {
	var form = $("#pagerForm").get(0);
	if (form) {
		if (args["pageNumber"])
			form["pageNumber"].value = args["pageNumber"];
		if (args["pageSize"]) 
			form["pageSize"].value = args["pageSize"];
		if (args["orderProperty"])
			form["orderProperty"].value = args["orderProperty"];
		if (args["orderDirection"] && form["orderDirection"])
			form["orderDirection"].value = args["orderDirection"];
	}
	
	return form;
}

function pageBreak(options){
	    var op = $.extend({data:{pageNumber:"", pageSize:"", orderProperty:"", orderDirection:""}, callback:null}, options);
		var form = _getPagerForm(op.data);
		var url = $(form).attr("action");
		var params = $(form).serializeArray();
		var g = $("#layout-nav");
		var f = g.next(".tab-content");
		var k = f.find("> div[data-url='" + url + "']");
		k.ajaxGetUrl(url,params);
}

(function($){
   $.fn.extend({
	   pagerForm: function(options){
			var op = $.extend({pagerForm$:"#pagerForm", parentBox:document}, options);	     	
			var frag = '<input type="hidden" name="#name#" value="#value#" />';
			return this.each(function(){
				var $searchForm = $(this), $pagerForm = $(op.pagerForm$, op.parentBox);
				var actionUrl = $pagerForm.attr("action").replaceAll("#rel#", $searchForm.attr("action"));
				$pagerForm.attr("action", actionUrl);
				$searchForm.find(":input").each(function(){
					var $input = $(this), name = $input.attr("name");
					if (name && (!$input.is(":checkbox,:radio") || $input.is(":checked"))){
						if ($pagerForm.find(":input[name='"+name+"']").length == 0) {
							var inputFrag = frag.replaceAll("#name#", name).replaceAll("#value#", $input.val());
							$pagerForm.append(inputFrag);
						}
					}
				});
			});
		}
   });  
})(jQuery);

$().ready( function() {
    var $ids = $(".table input[name='ids']");
    var $sort = $(".table .sorting");
    var $orderProperty = $("#orderProperty");
	var $orderDirection = $("#orderDirection");
	if ($.fn.pagerForm) $("form[rel=pagerForm]", document).pagerForm({parentBox:document});

   	// 排序
	$sort.click( function() {
		var orderProperty = $(this).attr("name");
         
		if ($orderProperty.val() == orderProperty) {
			if ($orderDirection.val() == "asc") {
				$orderDirection.val("desc")
			} else {
				$orderDirection.val("asc");
			}
		} else {
			$orderProperty.val(orderProperty);
			$orderDirection.val("asc");
		}

        pageBreak('{data:{pageNumber:1,orderProperty:orderProperty,orderDirection:$orderDirection.val()}}');
		return false;
	});
    //全选
    $('table th input:checkbox').on('click' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			if ($(that).prop("checked")) {
			   $(this).closest('tr').addClass('selected');
			}else{
			   $(this).closest('tr').removeClass('selected');
			}
		});
						
	});

	// 选择
	$ids.click( function() {
		var $this = $(this);
		if ($this.prop("checked")) {
			$this.closest("tr").addClass("selected");
		} else {
			$this.closest("tr").removeClass("selected");
			
		}
	});

});