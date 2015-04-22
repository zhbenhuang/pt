<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.cmbc.attach.action.UploadAttachAction" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String businessId = (String)request.getAttribute("businessId");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="pragma" content="no-cache"/>    
    <title>附件上传成功</title>
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
<script language="javascript" type="text/javascript">
	function custom_close(){
		window.close();
		window.location.href="attachAction!listNoticeAttach.action?businessId=<%=businessId%>";
	}
</script>     
	<form id="myForm" enctype="multipart/form-data">	
	  	<table align="center">
		  	<tr>
		  	<td>		  	
		  	<s:iterator value="tag" status="stat" id="sd">
				<tr >							
					<s:iterator value="total[#stat.index]" > 
		                 <s:property/>     
		            </s:iterator> 
		            <td>
		              	  附件上传成功！<s:property/><br/>
		            </td>
				</tr>
			</s:iterator>
			</td>
			</tr>
	    </table>	    
	    <table align="center">
			<tr>
			<td align="center"><font size="1.9">您上传的附件如下：</font><br/>
			<br/></td>
			</tr>			
		     <s:iterator value="uploadFileName" status="stat" id="sd">
				<tr align="center">							
					<s:iterator value="total[#stat.index]" > 
		                 <s:property/>     
		            </s:iterator> 
		            <td>
		                 <font size="2"><s:property/></font><br/>
		            </td>
				</tr>
			</s:iterator> 			    
	     	<tr>
	     		<td align="center" colspan="2"><br/>					
					<input type="button" value="关闭" id="reset" name="reset"  class="easyui-linkbutton" onclick="custom_close()"/>
				</td>
	     	</tr>	     
	    </table>
    </form>
  </body>
</html>
