<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.jbpm.api.*,org.jbpm.api.model.*"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String deploymentId = request.getParameter("deploymentId");
	String processPngName = request.getParameter("processPngName");
	String processInsId = request.getParameter("processInsId"); // 获取流程实例ID
	ProcessEngine processEngine = Configuration.getProcessEngine();
	RepositoryService repositoryService = processEngine
			.getRepositoryService();
	ExecutionService executionService = processEngine
			.getExecutionService();
	ProcessInstance processInstance = executionService
			.findProcessInstanceById(processInsId); // 根据ID获取流程实例
	Set<String> activityNames = processInstance
			.findActiveActivityNames(); 			// 获取实例执行到的当前节点的名称
	ActivityCoordinates ac = repositoryService.getActivityCoordinates(processInstance.getProcessDefinitionId(), activityNames.iterator().next());
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
		<div region="center" style="width:300px;height:100%;overflow:auto">
			<img src="flow/pngPicture.jsp?deploymentId=<%=deploymentId%>&processPngName=<%=processPngName %>" style="position: absolute; left: 0px; top: 0px"/>
			<div style="position:absolute;border:2px solid red;left:<%=ac.getX()%>px;top:<%=ac.getY()%>px;width:<%=ac.getWidth()%>px;height:<%=ac.getHeight()%>px;"></div>	
		</div>
	</div>	
  </body>  
</html>
