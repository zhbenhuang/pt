<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.cmbc.attach.action.UploadAttachAction" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String businessId = (String)request.getAttribute("businessId");
String attachId = (String)request.getAttribute("attachId");
String tag = (String)request.getAttribute("tag");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="pragma" content="no-cache"/>
	<title>附件上传</title>  
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
<script type="text/javascript">
function add()
{
	var td = document.getElementById("td");
	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");
	
	input.type="file";
	input.name="file";
	
	button.type="button";
	button.value="移除";
	
	button.onclick=function()
	{		
		td.removeChild(br);
		td.removeChild(input);
		td.removeChild(button);
	};
	
	td.appendChild(br);
	td.appendChild(input);
	td.appendChild(button);
}

function isTag(){
    if (tag == false) {
    	$.messager.alert("提示","文件下已有同名文件，请上传其他文件！",'warning');
    }
    else {
    	document.uploadform.submit();
    }
}

function check(){
	var a = document.getElementById("file").value;
	var tag = "<%=tag%>";	
	if(a == null || a == "") {		
		LG.showWarn("提示消息","请至少选择一个文件，再点“上传”按钮！");
	}	
	else {		
		document.uploadform.submit();
	}
}

function back(){
	window.location.href = "attachAction!listNoticeAttach.action?businessId=<%=businessId%>";
}
</script>
</head> 
<body style="background-color: #E4F5EF;">
    <s:form action="uploadAttach" enctype="multipart/form-data" theme="simple" method="post" name="uploadform">
    	<input type="hidden" name="businessId" value="<%=businessId%>" />    	
    <table>
    	<tr>
    	  <td> <s:fielderror></s:fielderror> </td>
    	</tr>
    </table>
    <table align="center">
    	<tr align="center">
    	  <td id="td"><br/>    	  
    	  <s:file name="upload" id="file"></s:file>
    	  </td>
    	</tr>
    	<tr align="center">
    	  <td><br/>
	    	  <input type="button" value="上传" onclick="javascript:check();" class="easyui-linkbutton"/>
	    	  <s:reset value="重置" class="easyui-linkbutton"></s:reset>	    	  
	    	  <input type="button" value="返回" onclick="javascript:back();" class="easyui-linkbutton"/>
    	  </td>
    	</tr>
    </table>
    </s:form>
  </body>
</html>
