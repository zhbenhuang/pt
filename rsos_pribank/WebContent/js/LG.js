(function ($)
{
    /*全局系统对象*/
    window['LG'] = {};
    
    /*显示成功提示窗口*/
    LG.showMsg = function (title, retCode, msg, tipSuccess) {
		
		if(typeof(retCode)!="undefined"){			
	    	if(retCode=='A000000'){
	    		if(tipSuccess){
	    			$.messager.alert(title,msg,'info');
	    		}
	        }else if(retCode=='E888888'){
	        	$.messager.confirm(title, msg, function (r) {
	        		if (r) {
	        			if (window != top){
			        		top.location.href = "/rsos_pribank/login.jsp";
			        	}else{
			        		location.href = "/rsos_pribank/login.jsp";
			        	}
	        		}
	        	});
	        }else{
	        	$.messager.alert(title,msg,'error');
	        }
    	}
	};
    
    /*显示失败提示窗口*/
    LG.showError = function ( title, msg )
    {
    	$.messager.alert(title,msg,'error');


    };
    
    LG.showWarn = function ( title, msg )
    {
    	$.messager.alert(title,msg,'warning');
    };
    
    LG.showSuccess = function ( title, msg )
    {
    	$.messager.alert(title,msg,'info');
    };

})(jQuery);
