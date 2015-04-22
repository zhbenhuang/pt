<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<link rel="stylesheet" href="css/login.css">
	<link rel="stylesheet" type="text/css" href="css/common.css" />	
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />	
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>		
	<script type="text/javascript" src="js/LG.js"></script>		
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE-EmulateIE7" />
<title>登录页面</title>

<script type="text/javascript">
function login() {
	var form = $("#loginform");
	if(checkInput(form.get()[0])) {
		document.getElementById("loginform").submit();
	}
}
function checkInput(form) {
	if(form.userId.value == "") {		
		$.messager.alert("提示","请填写登录名！",'warning');
		form.userId.focus();
		return false;
	}
	if(form.password.value == "") {
		$.messager.alert("提示","请填写密码！",'warning');
		form.password.focus();
		return false;
	}
	return true;
}
$(function() {
	$("#chk").bind({
		click : function() {
			if ($(this).attr("checked")) {
				$("#passwd2").val($("#passwd").val());
				$("#passwd").hide();
				$("#passwd2").show();
			} else {
				$("#passwd").val($("#passwd2").val());
				$("#passwd2").hide();
				$("#passwd").show();
			}
		}
	});
});
</script>
</head>
<body class="bg" style="background-color:#E4F5EF;">	
	<div id="box"><img src="img\bg.jpg" style="width:1000px"/>		
        <div class="block" >
        <form method="post" action="login!login.action" id="loginform" name="form">        	
        	<div class="fielderror">
            	<s:fielderror/>
            </div>
            <div class="sys">
            	<label>子系统：</label><input name="business" id="business" style="width:136px" class="easyui-combobox" panelHeight="auto"
					data-options="valueField:'id',textField:'text',data:selectBusiness" />
            </div>
            <div class="username">
            	<label>登录名：</label><input type="text" name="userId" class="input-style" />
            </div>
            <div class="password">
            	<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label><input id="passwd" name="password" type="password" style="ime-mode: disabled; display: inline;" class="input-style" /><input id="passwd2" type="text" style="ime-mode: disabled; display: none;" class="input-style"/> 
				<input id="chk"	type="checkbox" /><font size="2">显示密码</font>
          	</div>
          	<div class="login">
          		<input type="button" value="登录" class="button" onclick="login()"/>
          	</div>          	
         </form>
        </div>        
    </div>
<p>&nbsp;</p>
</body>
</html>
