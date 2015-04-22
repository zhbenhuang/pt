<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String businessId = (String) request.getAttribute("businessId");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>附件删除成功</title>
  	<link rel="stylesheet" type="text/css" href="<%=path %>/css/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/dev.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/js/commons.js"></script>
	<script type="text/javascript" src="<%=path %>/js/vobCombox.js"></script>
	<script type="text/javascript" src="<%=path %>/js/LG.js"></script>
</head>
<body style="background-color: #E4F5EF;">
	<script language="JavaScript" type="text/javascript">
	function back(){
		window.close();
		window.location.href="attachAction!listNoticeAttach.action?businessId=<%=businessId%>";
	}
	</script>
	<form method="post" action="" name="dictionaryForm" id="dictionaryForm"
		enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td><br /> 删除成功！ <br /></td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td><br/>
				<input type="button" value="关闭" id="reset" name="reset"  class="easyui-linkbutton" onclick="back()" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>