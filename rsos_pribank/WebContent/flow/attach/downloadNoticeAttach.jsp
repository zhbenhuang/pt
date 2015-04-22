<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cmbc.attach.bean.AttachInfo"%>
<%request.setCharacterEncoding("utf-8");%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String tmpName = (String)request.getAttribute("attachName");
URLEncoder.encode(tmpName,"UTF-8");
String tmpNameNew = tmpName.substring(tmpName.indexOf('_') + 1);
String flag = (String)request.getAttribute("flag");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="pragma" content="no-cache"/>    
    <title>下载附件</title>
</head>
<body style="background-color: #E3E9FF;">
	<form method="post"  action="" name="dictionaryForm" id="dictionaryForm" enctype="multipart/form-data">		
		<input type="hidden" id="tmpName" name="tmpName" value="tmpName" />		
		<table align="center">
			<tr>
				<td align="center"><font size="1.9">您要下载的附件如下：</font><br /><br />					
				</td>
			</tr>			
				<tr align="center">
					<td><%=tmpNameNew %><br/><br /></td>
				</tr>			
		</table>		
		<div align="center">
			<a href="downloadAttach.action?tag=1&tmpName=<%=tmpName %>" >
			<b>点击下载</b>
			</a>
		</div>		
		<br/>
	</form>
</body>
</html>
