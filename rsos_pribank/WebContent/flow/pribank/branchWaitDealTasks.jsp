<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>待办任务列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<link type="text/css" rel="stylesheet" href="css/dev.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
		var taskId;
		var businessId;
		var status = "请输入条码";
		var currentPage = 'branchWaitDealTasks';
		var flag ;	
		var remark="";
		var processTypeName="";
		$(function()
		{
			$('#status').combobox({
				editable:false,
				panelHeight:'auto'
			});
			$('#mydialogOne').dialog({
				onClose:function()
				{$('#mydialogOne').form('clear');}
			});
			$("a[name='btn']").click(function(){
				$(this).parent().find('select').combobox('clear');
			});
			$.ajaxSettings.traditional=true;  
			/**
			 *	初始化数据表格  
			 */
			$('#t_taskList').datagrid({
					title:'待处理流程列表' ,
					fit:true ,
					height:450 ,
					url:'processBaseAction!waitDealTasksAction.action' ,
					fitColumns:true ,  
					striped: true ,					//隔行变色特性 
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
						{field:'taskId' ,title:'任务Id' ,hidden: true},
						{field:'businessId' ,title:'流水号' ,hidden: true},
						{field:'codeId' ,title:'合同/赎回单条码' ,width:140},
						{field:'title' ,title:'标题' ,width:200},
						{field:'processTypeName' ,title:'流程类型',width:100 ,align:'center'},
						{field:'status' ,title:'流程状态' ,width:80},
						{field:'userName' ,title:'发起人' ,width:80},
						{field:'department' ,title:'发起机构' ,width:100},
						{field:'createTime' ,title:'发起日期' ,width:150},
						{field:'arriveTime' ,title:'到达日期' ,width:150},
						{field:'signTime' ,title:'签收日期' ,width:150},
						{field:'preUserName' ,title:'上一办理人' ,width:150},
						{field:'preDepartment' ,title:'上一办理机构' ,width:150}
					]] ,
					pagination: true , 
					pageSize: 10 ,
					pageList:[10,20,50] ,
					toolbar:[
						{
							text:'扫描条码' ,
							iconCls:'icon-search' , 
							handler:function(){
								flag = 'search';
								
								$('#mydialogOne').dialog({title:'扫描条码'});
								$('#mydialogOne').dialog('open');
								$('#setCodeId').focus();
							}
						},
						{text:'流程跟踪' ,iconCls:'icon-my-view' ,handler:processTracing},
						{text:'流程实例图' ,iconCls:'icon-my-photo' , handler:processPngView}
					]
				});
					
				/**
				 * 输入条码后显示
				 * */
				$('#showInfo').click(function(){
					var codeId = $('#setCodeId').attr('value');
					if(codeId==''){
						LG.showWarn("提示消息","扫描失败,重试或手工输入!");
					}else{
						var params = {  
							"codeId" : codeId,
							"currentPage" : currentPage
						} ;
						$.ajax({
							type: 'post',
							url: 'contractProcess!showProcessInfoAction.action',
							cache:false ,
							data:params,
							dataType:'json' ,
							success:function(result){
								if(result.redCode=="A000000"){
									processTypeName = result.processTypeName
									if(processTypeName=="理财产品合同流程"){
										businessId = result.contract.businessId;
										remark = result.contract.remark;
									}else{
										businessId = result.redemptionInfo.businessId;
										remark = result.redemptionInfo.remark;
									}
									taskId = result.taskId;
									status = result.status;
									$("#processTypeName").val(processTypeName);
									$("#processStatus").val(status);
									$("#codeId").val(codeId);
									$("#remark").val(remark);
									$("#task").val(taskId);
									$("#business").val(businessId);
									$("#customeId").val(result.contract.customeId);
									$("#customeName").val(result.contract.customeName);
									$("#saleDate").val(result.contract.saleDate);
									$("#productType").val(result.contract.productType);
									$("#productCode").val(result.contract.productCode);
									$("#productName").val(result.contract.productName);
									$("#money").val(result.contract.money);
									$("#signAccount").val(result.contract.signAccount);
									$("#saleManager").val(result.contract.saleManager);
									$("#businessManager").val(result.contract.businessManager);
									$("#belongDepartment").val(result.contract.belongDepartment);
									$("#signDepartment").val(result.contract.signDepartment);							
								}else{
									LG.showWarn("提示消息","系统错误,请联系管理员!");
								}
							}
						});
					}
				});
				/**
			      * 执行签收操作
			      */
				$('#sign').click(function(){
					var productCode = $('#productCode').attr('value');
					var codeId = $('#codeId').attr('value');
					if(codeId==""){
						LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
					}else if(productCode==""){
						LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
					}else{
						if(status.indexOf("退回待签收")>=0){	//如果状态是待签收,则执行签收动作
							$.messager.confirm('提示消息','确认签收?',function(r){
								if(r){
									var params = {  
				   	    	         "taskId" : taskId ,
				   	    	         "businessId" : businessId
				   	    	     	} ;
									$.ajax({
										type: 'post' ,
										url: 'contractProcess!signTaskAction.action',
										cache:false ,
										data:params,
										dataType:'json' ,
										success:function(result){
											if(result.retCode=="A000000"){
												$.messager.alert('提示信息',result.message,'info',function(){
													status = '待审核';
													$("#processStatus").val(status);
												});
											}else{
												LG.showError("提示信息","系统访问异常,请联系管理员!");
											}
										}
									});
								}
							})
						}else if(status.indexOf("待审核")>=0){
							LG.showWarn("提示消息","任务已签收,请取消流程!");
						}
					}
				});
				/**
				 *取消流程
				 */
				$('#btn9').click(function(){
					var productCode = $('#productCode').attr('value');
					var codeId = $('#codeId').attr('value');
					if(codeId==""){
						LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
					}else if(productCode==""){
						LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
					}else{
						 if(status.indexOf('待审核')>=0){
							 $.messager.confirm('提示消息','确认取消流程?',function(r){
								if(r){
									var url="";
									if(processTypeName=="理财产品合同流程"){
										url = 'contractProcess!rejectAction.action';
									}else{
										url = 'redempProcess!rejectAction.action';
									}
									$.ajax({
										type: 'post' ,
										url: url,
										cache:false,
										data:$('#myformtwo').serialize() ,
										dataType:'json',
										success:function(result){
											if(result.retCode=="A000000"){
												$.messager.alert('提示信息',result.message,'info',function(){
													status = "请输入条码";
													$('#myformtwo').form('clear');
													$('#setCodeId').val("");
												});
											}else{
												LG.showError("提示信息","系统访问异常,请联系管理员!");
											}
										}
									});	
								}
							});
						 }else{
							  LG.showWarn("提示消息","该任务尚未签收!");
						 }
					 }
				});
				/**
				 * 关闭窗口方法
				 */
				$('#btn2').click(function(){
					$('#mydialogOne').dialog('close');
				});

				$('#btn10').click(function(){
					$('#mydialogOne').dialog('close');
				});
				$('#btn13').click(function(){
					$('#trackDlg').dialog('close');
					//2刷新datagrid 
					$('#t_taskList').datagrid('reload');
				});
				
				$('#searchbtn').click(function(){
					$('#t_taskList').datagrid('load' ,serializeForm($('#mysearch')));
				});
					
					
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_taskList').datagrid('load' ,{});
				});
			});
			
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
					LG.showWarn("提示信息","请选择一条待办任务跟踪!");
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
								$('#processPngDlg').dialog('open').dialog("refresh","flow/viewProcessInsPng.jsp?"
								                                               +"processInsId="+processInsId
								                                               +"&deploymentId="+deploymentId
								                                               +"&processPngName="+processPngName);
							}else{
								LG.showError("提示信息","系统访问异常,请联系管理员!");
							}
						}
					});
				}
			}
			
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
			
			//enter后触发
			function showInfo(event){
				if(event.keyCode==13){
					var codeId = $('#setCodeId').attr('value');
					if(codeId==''){
						LG.showWarn("提示消息","扫描失败,重试或手工输入!");
					}else{
						var params = {  
							"codeId" : codeId,
							"currentPage" : currentPage
						} ;
						$.ajax({
							type: 'post',
							url: 'contractProcess!showProcessInfoAction.action',
							cache:false ,
							data:params,
							dataType:'json' ,
							success:function(result){
								if(result.redCode=="A000000"){
									processTypeName = result.processTypeName
									if(processTypeName=="理财产品合同流程"){
										businessId = result.contract.businessId;
										remark = result.contract.remark;
									}else{
										businessId = result.redemptionInfo.businessId;
										remark = result.redemptionInfo.remark;
									}
									taskId = result.taskId;
									status = result.status;
									$("#processTypeName").val(processTypeName);
									$("#processStatus").val(status);
									$("#codeId").val(codeId);
									$("#remark").val(remark);
									$("#task").val(taskId);
									$("#business").val(businessId);
									$("#customeId").val(result.contract.customeId);
									$("#customeName").val(result.contract.customeName);
									$("#saleDate").val(result.contract.saleDate);
									$("#productType").val(result.contract.productType);
									$("#productCode").val(result.contract.productCode);
									$("#productName").val(result.contract.productName);
									$("#money").val(result.contract.money);
									$("#signAccount").val(result.contract.signAccount);
									$("#saleManager").val(result.contract.saleManager);
									$("#businessManager").val(result.contract.businessManager);
									$("#belongDepartment").val(result.contract.belongDepartment);
									$("#signDepartment").val(result.contract.signDepartment);							
								}else{
									LG.showError("提示信息","系统访问异常,请联系管理员!");
								}
							}
						});
					}
				}
			}
			
			$(function(){
			    $('#startTime1').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#arriveTime1').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#signTime1').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#startTime2').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#arriveTime2').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#signTime2').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			});	
			
			$('#natural').combobox({
				editable:false,
				required:true,
				panelHeight:'auto'
			});
			
			$('#processTypeName').combobox({
				editable:false,
				required:true,
				panelHeight:'auto'
			});
	</script>
  </head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
			<div region="north" title="待办任务查询" style="height:120%;overflow:hidden" >
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;发起人工号:&nbsp;<input name="createUserId" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="25%">&nbsp;上一办理人工号:&nbsp;<input name="preUserId" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>		
						<td width="45%">&nbsp;发起日期:&nbsp;<input name="startTime1" id="startTime1" class="easyui-datebox" value="" style="width:140%;"/>
						&nbsp;至&nbsp;<input name="startTime2" id="startTime2" class="easyui-datebox" value="" style="width:140%;"/></td>	
					</tr>

					<tr>						
						<td width="20%">&nbsp;流&nbsp;程&nbsp;标&nbsp;题:&nbsp;<input name="topic" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="25%">&nbsp;流&nbsp;&nbsp;&nbsp;程&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型:&nbsp;<input name="processTypeName" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>					
						<td width="45%">&nbsp;到达日期:&nbsp;<input name="arriveTime1" id="arriveTime1" class="easyui-datebox" value="" style="width:140%;"/> 
						&nbsp;至&nbsp;<input name="arriveTime2" id="arriveTime2" class="easyui-datebox" value="" style="width:140%;"/></td>
					</tr>
					</table>
				<table width="100%">
					<tr>
						<td width="50%">&nbsp;签&nbsp;收&nbsp;日&nbsp;期:&nbsp;<input name="signTime1" id="signTime1" class="easyui-datebox" value="" style="width:134%;"/>
						&nbsp;至&nbsp;<input name="signTime2" id="signTime2" class="easyui-datebox" value="" style="width:134%;"/></td>
						<td width="30%">&nbsp;流程状态:&nbsp;
	    				<select id="status" class="" name="status" style="width:100%;">
	    					<option value=""></option>
	    					<option value="退回待签收">退回待签收</option>
	    					<option value="待审核">待办理</option>
	    				</select>
	    				</td>	
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">&nbsp;&nbsp;查询&nbsp;&nbsp;</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">&nbsp;&nbsp;清空&nbsp;&nbsp;</a></td>
					</tr>
				</table> 
			</form>
		</div>
		 <div region="center" >
			<table id="t_taskList"></table>
		</div>
	</div>	
	<div id="mydialogOne" title="扫描条码" modal=false  draggable=true class="easyui-dialog" closed=true style="background-color:#E4F5EF;width:800px;height:500px">
  		<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
  			<div region="west" split="true" style="background-color:#E4F5EF;width:200px;height:35%">
  				<table>
  					<tr>
  						<td>&nbsp;扫描条码:</td>
  					</tr>
  					<tr>
  						<td colspan="2">
  							<input type="text" id="setCodeId" name="getCodeId" style="height:22px;border:1px solid #A4BED4;" onKeyDown="showInfo(event)"></input>
  						</td>
  					</tr>
	  				<tr>
	  					<td>
	  						<a href="javascript:void(0)" id="showInfo" class="easyui-linkbutton">确定</a>
	  						<a href="javascript:void(0)" id="btn2" class="easyui-linkbutton">关闭</a>
	  					</td>
	  				</tr>   					 					    					    					    					    					    					    					    					
	  			</table>
  			</div>
  			<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:35%">
  				<table>
  					<tr>
  						<td colspan="2">
  							<a href="javascript:void(0)" id="sign" class="easyui-linkbutton">签收</a>
  							<a href="javascript:void(0)" id="btn9" class="easyui-linkbutton">取消流程</a>
  							<a href="javascript:void(0)" id="btn10" class="easyui-linkbutton">关闭</a>
  						</td>
  					</tr> 
  				</table>  	
  			</div>
  			<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
		   		<br/>
		   		<form id="myformtwo" action="" method="post">
		   			<input type="hidden" id="task" name="taskId"/>
		   			<input type="hidden" id="business" name="businessId"/>
						<div align="center">
							<table width="90%" border="1" align="center" style="background-color:#E4F5EF;table-layout:fixed;">
								<tr>
									<td width="15%" align="center"><font color="red">流程类型</font></td>
									<td width="30%"><input id="processTypeName" name="processTypeName" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;" readonly="readonly"></td>
									<td width="15%" align="center"><font color="red">流程状态</font></td>
									<td width="30%"><input id="processStatus" name="processStatus" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;" readonly="readonly"></td>
								</tr>
								<tr>
		    						<td align="center">客户号&nbsp;<font color="red">*</font></td>
		    						<td><input type="text" id="customeId" name="customeId" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
		    						<td align="center">客户姓名&nbsp;<font color="red">*</font></td>
		    						<td><input id="customeName" name="customeName" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center">销售日期&nbsp;<font color="red">*</font></td>
		    						<td><input type="text" id="saleDate" name="saleDate" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
		    						<td align="center">产品类型&nbsp;<font color="red">*</font></td>
		    						<td><input type="text" id="productType"name="productType" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center">产品编码&nbsp;<font color="red">*</font></td>
		    						<td><input type="text" id="productCode" name="productCode" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    						<td align="center">产品名称&nbsp;<font color="red">*</font></td>
		    						<td><input id="productName" type="text" name="productName" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center">金额  (万)&nbsp;<font color="red">*</font></td>
		    						<td><input id="money" type="text" name="money" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    						<td align="center">签约帐号&nbsp;<font color="red">*</font></td>
		    						<td><input id="signAccount" type="text" name="signAccount" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center">销售经理&nbsp;</td>
		    						<td><input id="saleManager" type="text" name="saleManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    						<td align="center">理财经理&nbsp;</td>
		    						<td><input id="businessManager" type="text" name="businessManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center">归属机构&nbsp;<font color="red">*</font></td>
		    						<td><input id="belongDepartment" type="text" name="belongDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    						<td align="center">签约机构&nbsp;<font color="red">*</font></td>
		    						<td><input id="signDepartment" type="text" name="signDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center"><font color="red">&nbsp;&nbsp;合同/赎回单条码号&nbsp;*</font></td>
		    						<td colspan="3"><input id="codeId" type="text" name="codeId" value="" readonly="readonly" style="background-color: #E1E6E9;width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    					</tr>
		    					<tr>
		    						<td align="center">备注&nbsp;</td>
		    						<td colspan="3"><input id="remark" type="text" name="remark" value="" style="width:86%;height:22px;border:1px solid #A4BED4;"/></td>
		    					</tr>
							</table>
						</div>
		   			</form>
  				</div>
  			</div>
		</div>
		<div id="trackDlg" title="流程跟踪" class="easyui-dialog" style="width:720px;height:520px"  
      		data-options="iconCls:'icon-my-view',modal:true,draggable:true,closed:true,cache:false">
		</div>
		<div id="processPngDlg" title="流程实例图" class="easyui-dialog" style="width:720px;height:520px" data-options="iconCls:'icon-my-photo',modal:true,draggable:true,closed:true,cache:false">
		</div>
  </body>
</html>
