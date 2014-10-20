$.extend($.fn.dataTable.defaults, {
    "searching": false,

	"language": {
		 "url": "../resource/plugins/jquery-dataTable/js/Chinese.lang"
	},
	"bFilter": false, //过滤功能
	"bStateSave": true
} );
(function ($)
{
  
    inject = {
        prev: 'dynamic-',
        defaults: {
            dataTable: '$.fn.dataTable.defaults'
        },
		controls:['dataTable'],
        /*
        config里面配置了某插件参数或者复杂属性参数的类型(动态加载、数组、默认参数)
        */
        parse: function (code)
        {
            try
            {
                if (code == null) return null;
                return new Function("return " + code + ";")();
            } catch (e)
            {
                return null;
            }
        },

        parseDefault: function (value)
        {
            var g = this;
            if (!value) return value;
            var result = {};
            $(value.split(',')).each(function (index, name)
            {
                if (!name) return;
                $.extend(result, g.parse(name));
            });
            return result;
        },
		isEmptyObject: function( obj ) { 
		  for ( var name in obj ) { 
			return false; 
		  } 
			return true; 
		},
        wrapParse:{
           dataTable:function(jelement,defaults){
				$.extend(defaults,inject.parseOptions(jelement,null));
				$(jelement).children("thead").each(function(){
					$(this).find("tr").each(function(){
						var tr = [];
						$(this).find("th").each(function(){
						    var th=$(this);
							if(th.find('input:checkbox').length>0){
								tr.push({ "bSortable": false });
							}else{
								aoColumnDefs = $.extend(null,inject.parseOptions(th,null));
								alert(inject.isEmptyObject(aoColumnDefs)?null:aoColumnDefs);
								tr.push(inject.isEmptyObject(aoColumnDefs)?null:aoColumnDefs);
							}
							//aoColumnDefs = $.extend(null,inject.parseOptions(th,null));
							
						});
                       defaults.aoColumns=tr;
                   });
				});
		
		   }
		},
		parseOptions: function(target, properties){
			var t = $(target);
			var options = {};
			
			var s = $.trim(t.attr('data-options'));
			if (s){
				var first = s.substring(0,1);
				var last = s.substring(s.length-1,1);
				if (first != '{') s = '{' + s;
				if (last != '}') s = s + '}';
				options = (new Function('return ' + s))();
			}
				
			if (properties){
				var opts = {};
				for(var i=0; i<properties.length; i++){
					var pp = properties[i];
					if (typeof pp == 'string'){
						if (pp == 'width' || pp == 'height' || pp == 'left' || pp == 'top'){
							opts[pp] = parseInt(target.style[pp]) || undefined;
						} else {
							opts[pp] = t.attr(pp);
						}
					} else {
						for(var name in pp){
							var type = pp[name];
							if (type == 'boolean'){
								opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
							} else if (type == 'number'){
								opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
							}
						}
					}
				}
				$.extend(options, opts);
			}
			return options;
		},
	
        init: function ()
        {
			    
                var g = this;
				for (var name in g.defaults)
				{
					if (typeof (g.defaults[name]) == "string")
					{
						g.defaults[name] = g.parseDefault(g.defaults[name]);
					}
				}

			 for (var i=0;i< g.controls.length;i++){
				var controlName = g.controls[i];
                var className = g.prev + controlName;
                $("." + className).each(function (){
					
                    var jelement = $(this), value;
                    var defaults = g.defaults[controlName];
					
                    g.wrapParse[controlName](jelement,defaults);
                     alert(defaults.aoColumns);
                    jelement[controlName](defaults);
                });
            }
          
            
        }

    }

    $(function ()
    {
        inject.init();
    });

})(jQuery);