<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html>
 <head>
 	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
 </head>
 <body>
 	<script type="text/javascript">
	 	$.messager.alert('提示消息',"登录超时,请重新登录!",'error',function(){
			window.top.location.href = "<%=path %>/login.jsp";
		});
  	</script>
 </body>
</html>
