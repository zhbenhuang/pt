<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>
<link rel="stylesheet" href="css/password.css">
<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/commons.js"></script>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
<script>
	function test(){
		if($('#setpasswordForm').form('validate')){
			var newpassword = $('#newpassword').val();
			var confirmpassword = $('#confirmpassword').val();
			if(newpassword!=confirmpassword){
				$.messager.alert("提示信息","确认密码与新密码不符,请重新输入!","warn");
			}else{
				$.ajax({
					type: 'post' ,
					url: 'alterpassword!alterPassword.action',
					cache:false ,
					data:$('#setpasswordForm').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='1111'){
							$.messager.alert('提示消息',result.message,'info',function(){
								window.location.href = "login.jsp";
							});
						}else if(result.retCode=='0000'){ 
							$.messager.alert("提示信息",result.message,"error");
						}else{
							$.messager.alert("提示信息","密码修改操作无法通过，请重新登录后重试或联系管理员!","error");
						}
					}
				});
			}
		} else {
			$.messager.alert("提示信息","密码格式不正确,请重新填写!","warn");
		}
	};
	function back() {
		window.location.href = "login.jsp";
	}
	//js方法：序列化表单 			
	function serializeForm(form){
		var obj = {};
		$.each(form.serializeArray(),function(index){
			if(obj[this['name']]){
				obj[this['name']] = obj[this['name']] + ','+this['value'];
				alert(obj[this['name']]+":"+this['value']);
			} else {
				obj[this['name']] =this['value'];
			}
		});
		return obj;
	}
	$(function() {
		$("#chk").bind({
			click : function() {
				if ($(this).attr("checked")) {
					$("#passwd2").val($("#originalpassword").val());
					$("#originalpassword").hide();
					$("#passwd2").show();
				} else {
					$("#originalpassword").val($("#passwd2").val());
					$("#passwd2").hide();
					$("#originalpassword").show();
				}
			}
		});
	});
</script>
</head>
<body class="bg" style="background-color:#E4F5EF;">
	<div id="box"><img src="img\bg.jpg" style="width:1000px"/>
        <div class="block" >
        <form id="setpasswordForm" action="" method="post">
            <div class="originalpassword">
            	<label>原始密码：</label>
            	<input type="text" name="originalpassword"  id="originalpassword" style="width:60%;"/>
            </div>
            <div class="newpassword">
            	<label>新&nbsp;&nbsp;密&nbsp;&nbsp;码：</label><input type="password" name="newpassword" id="newpassword" style="width:60%;"/>
          	</div>
          	<div class="confirmpassword">
            	<label>确认密码：</label><input type="password"  id="confirmpassword" name="confirmpassword" style="width:60%;"/>
          	</div>
          	<div class="set">
          		<input id="setPassword" type="button" value="设置"  class="button" onclick="test()"/>
          	</div>
          	<div class="return">
          		<input id="return" type="button" value="返回"  class="button" onclick="back()"/>
          	</div>
         </form>
        </div>
    </div>
</body>
</html>
