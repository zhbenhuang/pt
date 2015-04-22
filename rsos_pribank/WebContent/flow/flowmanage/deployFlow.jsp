<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<title>流程发布</title>
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
</head>
<body>
<script type="text/javascript">
function deploy(){
	var fileName = document.getElementById("processDepFile").value;
	if(fileName == null || fileName == "") {		
		alert("请至少选择一个文件，再点“上传”按钮！");
	} else {		
		document.newFlowForm.submit();
	}
}
</script>
	<form id="newFlowForm" method="post" enctype="multipart/form-data" action="flowManage!deployFlow.action">
	<table>
		<tr><td colspan="2"><font color="red"><s:fielderror/></font></td></tr>
		<tr><td colspan="2"><font color="red">注：发布zip文件</font></td></tr>
		<tr>
			<td colspan="2">
			发布流程文件<input type="file" name="processDepFile" id="processDepFile"/>
			<input type="submit" value="提交" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>
