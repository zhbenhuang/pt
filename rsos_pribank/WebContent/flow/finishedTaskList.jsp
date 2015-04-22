<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>已处理任务列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<link rel="stylesheet" type="text/css" href="css/dev.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
			$(function(){
				var taskId;
				var businessId;
				var flag ;		//undefined 判断新增和修改方法 
				$.ajaxSettings.traditional=true;  
				$('#t_taskList').datagrid({
					title:'已处理流程列表' ,
					fit:true ,
					height:450 ,
					url:'processBaseAction!finishedTasksAction.action',
					fitColumns:false ,    
					striped: true ,					
					loadMsg: '数据正在加载,请耐心的等待...' ,
					rownumbers:true ,
					onLoadSuccess: function (result){
						LG.showMsg("查询结果提示",result.retCode,result.message,false);
					},
					onLoadError: function (xhr,data){
						showErrorMessage(xhr);
					},
					frozenColumns:[[
						{field:'ck' ,width:50 ,checkbox: true}
					]],
					columns:[[
						{field:'businessId' ,title:'流水号' ,width:140},
						{field:'title' ,title:'标题' ,width:200,formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewProcessInfo(\'' + row.businessId+ '\',\'' + row.processTypeName+ '\')">'+row.title+'</a>'}},
						{field:'processTypeName' ,title:'流程类型',width:100},
						{field:'status' ,title:'流程状态' ,width:80 },
						{field:'userName' ,title:'发起人' ,width:80 },
						{field:'department' ,title:'发起机构' ,width:100},
						{field:'createTime' ,title:'发起日期' ,width:150},
						{field:'arriveTime' ,title:'到达日期' ,width:150},
						{field:'signTime' ,title:'签收日期' ,width:150},
						{field:'completeTime' ,title:'办理日期' ,width:150},
						{field:'finishedTime' ,title:'办结日期' ,width:150}
					]] ,
					pagination: true , 
					pageSize: 10 ,
					pageList:[10,20,50] ,
					toolbar:[
						{text:'流程跟踪' ,iconCls:'icon-search' ,handler:processTracing},
						{text:'流程实例图' ,iconCls:'icon-my-photo' , handler:processPngView}
					]
				});		
				$('#searchbtn').click(function(){
					$('#t_taskList').datagrid('load' ,serializeForm($('#mysearch')));
				});
				
				
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_taskList').datagrid('load' ,{});
				});
			});
			//js方法：序列化表单 			
			function serializeForm(form){
				var obj = {};
				$.each(form.serializeArray(),function(index){
					if(obj[this['name']]){
						obj[this['name']] = obj[this['name']] + ','+this['value'];
					} else {
						obj[this['name']] =this['value'];
					}
				});
				return obj;
			}
			function processTracing(){
				var grid =$('#t_taskList').datagrid('getSelections');
				if(grid.length !=1){
					LG.showWarn("提示信息","请选择一条待办任务跟踪!");
				} else {
					var businessId = grid[0].businessId;
					var topic = grid[0].title;
					var taskId = grid[0].taskId;
					$('#trackDlg').dialog('open').dialog("refresh","flow/selectTaskView.jsp?businessId="+businessId+"&topic="+topic+"&taskId="+taskId);
				}
			}
			function processPngView(){
				var grid =$('#t_taskList').datagrid('getSelections');
				if(grid.length !=1){
					LG.showWarn("提示信息","请选择一条流程查看流程图!");
				} else {
					var businessId = grid[0].businessId;
					var params = {'businessId':businessId};
					$.ajax({
						type: 'post' ,
						url: 'processBaseAction!findProcessInsId.action',
						cache:false ,
						data:params ,
						dataType:'json' ,
						success:function(result){
							if(result.retCode=="A000000"){
								var processInsId = result.processInsId;
								var deploymentId = result.deploymentId;
								var processPngName = result.processPngName;
								$('#processPngDlg').dialog('open').dialog("refresh","flow/processInsPngView.jsp?"
								                                               +"deploymentId="+deploymentId
								                                               +"&processPngName="+processPngName);
							}else{
								LG.showError("提示信息","系统访问异常,请联系管理员!");
							}
						}
					});
				}
			}
			
			function viewProcessInfo(businessId,processTypeName){
				if(processTypeName=='理财产品合同流程'){
					$('#mydialogContract').dialog('open').dialog("refresh","flow/contractView.jsp?businessId='"+businessId+"'");
				}else if(processTypeName=='理财产品赎回流程'){
					$('#mydialogRedemp').dialog('open').dialog("refresh","flow/redempView.jsp?businessId='"+businessId+"'");
				}else{
					LG.showError("提示信息","系统访问异常,请联系管理员!");
				}
			}
			
			$(function(){
			    $('#startTime1').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y + (m < 10 ? ("0" + m) : m) + (d < 10 ? ("0" + d) : d);
		            }
			        });
			    $('#finishedTime1').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y + (m < 10 ? ("0" + m) : m) + (d < 10 ? ("0" + d) : d);
		            }
			   });
			    $('#startTime2').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y + (m < 10 ? ("0" + m) : m) + (d < 10 ? ("0" + d) : d);
		            }
			        });
			    $('#finishedTime2').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y + (m < 10 ? ("0" + m) : m) + (d < 10 ? ("0" + d) : d);
		            }
			   });
			});

	</script>
  </head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
			<div region="north" title="办结流程查询" style="height:90%;overflow:hidden" >
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;发起人工号:&nbsp;<input name="createUserId" value="" style="width:50%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="25%">&nbsp;上一办理人工号:&nbsp;<input name="preUserId" value="" style="width:50%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="35%">&nbsp;发&nbsp;起&nbsp;日&nbsp;期:&nbsp;<input name="startTime1" id="startTime1" class="easyui-datebox" value="" style="width:90%;"/>&nbsp;
						至&nbsp;&nbsp;<input name="startTime2" id="startTime2" class="easyui-datebox" value="" style="width:90%;"/></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%">&nbsp;流&nbsp;程&nbsp;标&nbsp;题:&nbsp;<input name="topic" value="" style="width:50%;height:22px;border:1px solid #A4BED4;"/></td>	
						<td width="25%">&nbsp;流&nbsp;&nbsp;&nbsp;程&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型:&nbsp;<input name="processTypeName" value="" style="width:50%;height:22px;border:1px solid #A4BED4;"/></td>	
						<td width="35%">&nbsp;办&nbsp;结&nbsp;日&nbsp;期:&nbsp;<input name="finishedTime1" id="finishedTime1" class="easyui-datebox" value="" style="width:90%;"/>&nbsp;								
						至&nbsp;&nbsp;<input name="finishedTime2" id="finishedTime2" class="easyui-datebox" value="" style="width:90%;"/></td>
						<td width="20%"><a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton">查询</a> <a id="clearbtn" href="javascript:void(0)" class="easyui-linkbutton">清空</a></td>
					</tr>
				</table> 
			</form>
		</div>
		<div region="center" >
			<table id="t_taskList"></table>
		</div>
	</div>
	<div id="trackDlg" title="流程跟踪" class="easyui-dialog" style="width:720px;height:520px" data-options="iconCls:'icon-my-view',modal:false,draggable:true,closed:true,cache:false">
	</div>
	<div id="processPngDlg" title="流程实例图" class="easyui-dialog" style="width:720px;height:520px" data-options="iconCls:'icon-my-photo',modal:false,draggable:true,closed:true,cache:false">
	</div>
	<div id="mydialogContract" title="合同详情" class="easyui-dialog" style="width:720px;height:520px" data-options="modal:false,draggable:true,closed:true,cache:false">
	</div>
	<div id="mydialogRedemp" title="赎回详情" class="easyui-dialog" style="width:720px;height:520px" data-options="modal:false,draggable:true,closed:true,cache:false">
	</div>
  </body>
</html>
