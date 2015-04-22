<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String deploymentId = request.getParameter("deploymentId");
	String processPngName = request.getParameter("processPngName");
	String processName = request.getParameter("processName");
	String processNameStr = new String(processName.getBytes("iso8859-1"),"GBK");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>流程图</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="css/common.css" />	
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>	
 </head>
  
 <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="north" style="width:100%;height:50%" > 
			<br/>&nbsp;流程名:&nbsp;<input name="processName" type="text" value="<%=processNameStr%>" size="50" class="input-style" readonly/>
		</div>
		<div region="center" style="width: 100%;height:100%;overflow:auto">
			<img src="flow/pngPicture.jsp?deploymentId=<%=deploymentId%>&processPngName=<%=processPngName %>" />	
		</div>
	</div>	
  </body>  
</html>
