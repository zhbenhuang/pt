<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String businessId = request.getParameter("businessId");
	String topic = request.getParameter("topic");
	String topicStr = new String(topic.getBytes("iso8859-1"),"GBK");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>流程跟踪</title>
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
		<br/>
			&nbsp;流水号:&nbsp;<input id="businessId" name="businessId" type="text" value="<%=businessId%>" size="35" class="input-style" readonly/>
			&nbsp;标题:&nbsp;<input name="topic" type="text" value="<%=topicStr%>" size="50" class="input-style" readonly/>
		</div>	
		<div region="center" style="width:100%;height:100%">
			<table id="t_tracing_task" style="width: 100%;height:100%"></table>		
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		var taskId = <%=request.getParameter("taskId")%>;
		var businessId = $('#businessId').attr('value');
		var params = {
			"taskId":taskId,
			"businessId":businessId
		};
		
		$('#t_tracing_task').datagrid({
			title:'流程跟踪详情' ,
			fit:true ,
			height:200 ,
			url:'processBaseAction!tracingProcess.action' ,
			queryParams:params,
			fitColumns:false ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			frozenColumns:[[
				{field:'ck' ,width:50 ,checkbox: true},
				{field:'stepCode' ,title:'执行步骤',width:80 ,align:'center'}
			]],
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'taskName' ,title:'任务名称' ,width:150 ,align:'center'},
				{field:'arriveTime' ,title:'到达时间' ,width:150 ,align:'center'},
				{field:'signTime' ,title:'签收时间' ,width:150 ,align:'center'},
				{field:'completeTime' ,title:'完成时间' ,width:150 ,align:'center'},
				{field:'assignUserName' ,title:'办理人' ,width:100 ,align:'center'},
				{field:'assignDepartment' ,title:'办理机构' ,width:150 ,align:'center'},
				{field:'actType' ,title:'办理方式' ,width:100 ,align:'center'},
				{field:'taskStatus' ,title:'任务状态' ,width:100 ,align:'center'}					
			]],
			pagination: true , 
			pageSize: 10 ,
			pageList:[10,20,50]
		});
		
	});
	</script>	
  </body>  
</html>
