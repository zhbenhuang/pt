
// 自定义的校验器
$.extend($.fn.validatebox.defaults.rules, {   
		midLength: {   
	 			validator: function(value, param){   
								return value.length >= param[0] && value.length <= param[1];    
				},   
				message: ''  
		} ,
		equalLength : {
	 			validator: function(value, param){   
								return value.length == param[0];    
				},   
				message: '密码必须为4个字符!'  
		}
});

/**
 * Setup AJAX default error handle
 */
$.ajaxSetup({
	converters : {
		"* text" : window.String,
		"text html" : true,
		"text json" : function(json) {
			if (!json) {
				return null;
			}
			return jQuery.parseJSON(json);
		},
		"text xml" : jQuery.parseXML
	},
	error : function(xhr, status) {
		var resText=xhr.getResponseHeader('sessionstatus');
		if(resText=='timeout'){
			$.messager.alert('提示消息',"登录超时,请重新登录!",'error',function(){
				window.top.location.href = "/rsos_pribank/login.jsp";
			});
		}else{
			$.messager.alert('提示消息',"系统错误,请联系管理员!",'error');
		}
	}
});

function showErrorMessage(xhr){
	var resText=xhr.getResponseHeader('sessionstatus');
	if(resText=='timeout'){
		$.messager.alert('提示消息',"登录超时,请重新登录!",'error',function(){
			window.top.location.href = "/rsos_pribank/login.jsp";
		});
	}else{
		$.messager.alert('提示消息',"系统错误,请联系管理员!",'error');
	}
}

$.extend($.fn.datagrid.defaults.editors, {   
    datetimebox: {   
        init: function(container, options){   
            var box = $('<input />').appendTo(container);   
            box.datetimebox(options);
            return box;   
        },   
        getValue: function(target){   
            return $(target).datetimebox('getValue');   
        },   
        setValue: function(target, value){   
            $(target).datetimebox('setValue',value);
        },   
        resize: function(target, width){   
            var box = $(target);   
			box.datetimebox('resize' , width);
        } ,
        destroy:function(target){
        	$(target).datetimebox('destroy');
        }
    }   
});  







