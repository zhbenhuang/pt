<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String business = (String)request.getAttribute("business");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>理财产品赎回流程</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/dev.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/js/commons.js"></script>
	<script type="text/javascript" src="<%=path %>/js/LG.js"></script>
	<script type="text/javascript">
		var business = <%=business%>;
		var currentPage = 'redemption';
		var taskId;
		var status = "请输入条码";
		var businessId;
		var flag ;
		$(function()
		{
			$.ajaxSettings.traditional=true; 
			$('#setCodeId').focus(); 
			/**
			 * 输入条码后显示
			 * */
			$('#showInfo').click(function(){
				var codeId = $('#setCodeId').attr('value');
				if(codeId==''){
					LG.showWarn("提示消息","扫描失败,重试或手工输入!");
				}else{
					var params = {"codeId" : codeId,"currentPage" : currentPage} ;
					$.ajax({
						type: 'post',
						url: 'redempProcess!showRedempInfoAction.action',
						cache:false ,
						data:params,
						dataType:'json' ,
						success:function(result){
							if(result.redCode=="A000000"){
								taskId = result.taskId;
								businessId = result.redemptionInfo.businessId;
								status = result.status;
								$("#processStatus").val(status);
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
								$("#codeId").val(result.redemptionInfo.codeId);
								$("#remark").val(result.redemptionInfo.remark);
								if(result.productRedemptionInterval!=null){
									$("#redempBegin").val(result.productRedemptionInterval.redeemBegin);
									$("#redempEnd").val(result.productRedemptionInterval.redeemEnd);
									$("#redemptionIntervalId").val(result.productRedemptionInterval.redemptionIntervalId);
								}
							}else{
								LG.showError("提示信息","系统访问异常,请联系管理员!");
							}
						}
					});
				}
			});
			
			/**
		      * 执行签收操作
		      */
			$('#btn1').click(function(){
				var productCode = $('#productCode').attr('value');
				var codeId = $('#codeId').attr('value');
				if(codeId==""){
					LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
				}else if(productCode==""){
					LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
				}else{
					if(status.indexOf("待签收")>=0){
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
						LG.showWarn("提示消息","任务已签收,请审核!");
					}else if(status.indexOf("待提交信托")>=0){
						LG.showWarn("提示消息","任务已审核,请提交信托!");
					}else if(status.indexOf("请输入条码")>=0){
						LG.showWarn("提示消息","请输入条码!");
					}
				}
			});

			/**
			 *通过审核
			 */
			 $('#btn8').click(function(){
				var productCode = $('#productCode').attr('value');
				var codeId = $('#codeId').attr('value');
				if(codeId==""){
					LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
				}else if(productCode==""){
					LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
				}else{
					 if(status.indexOf("待审核")>=0){
						var redempBegin = $('#redempBegin').attr('value');
						if(redempBegin==""){
							LG.showWarn("提示消息","请选择赎回区间!");
						}else{
						 	$.messager.confirm('提示消息','确认审核通过?',function(r){
								 if(r){
									 $.ajax({
										type: 'post' ,
										url: 'redempProcess!passAction.action',
										cache:false,
										data:$('#myformtwo').serialize() ,
										dataType:'json',
										success:function(result){								
											if(result.retCode=="A000000"){
												$.messager.alert('提示信息',result.message,'info',function(){
													status = result.status;	
													taskId = result.taskId;
													$("#task").val(taskId);
													$("#processStatus").val(status);
												});
											}else{
												LG.showError("提示信息","系统访问异常,请联系管理员!");
											}
										}
									});
								 }
							 });
						 }
					 }else if(status.indexOf("待签收")>=0){
						LG.showWarn("提示消息","该任务尚未签收!");
					 }else if(status.indexOf("待提交信托")>=0){
						LG.showWarn("提示消息","已审核,请提交信托!");
					 }else if(status.indexOf("请输入条码")>=0){
						LG.showWarn("提示消息","请输入条码!");
					 }
				 }
			 });
			/**
			 *驳回
			 */
			$('#btn9').click(function(){
				var productCode = $('#productCode').attr('value');
				var codeId = $('#codeId').attr('value');
				if(codeId==""){
					LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
				}else if(productCode==""){
					LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
				}else{
					 if(status.indexOf("待审核")>=0){
						$.messager.confirm('提示消息','确认驳回?',function(r){
							if(r){
								$.ajax({
									type: 'post' ,
									url: 'redempProcess!rejectAction.action',
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
					 }else if(status.indexOf("待签收")>=0){
						LG.showWarn("提示消息","该任务尚未签收!");
					 }else if(status.indexOf("待提交信托")>=0){
						LG.showWarn("提示消息","已审核,请提交信托!");
					 }else if(status.indexOf("请输入条码")>=0){
						LG.showWarn("提示消息","请输入条码!");
					 }
				 }
			});
			/**
			 *提交信托
			 */
			$('#submitxintuo').click(function(){
				var productCode = $('#productCode').attr('value');
				var codeId = $('#codeId').attr('value');
				if(codeId==""){
					LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
				}else if(productCode==""){
					LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
				}else{
					 if(status.indexOf("待提交信托")>=0){
						$.messager.confirm('提示消息','确认提交信托?',function(r){
							if(r){
								$.ajax({
									type: 'post' ,
									url: 'redempProcess!submitXinTuoAction.action',
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
					 }else if(status.indexOf("待签收")>=0){
						LG.showWarn("提示消息","该任务尚未签收!");
					 }else if(status.indexOf("待审核")>=0){
						LG.showWarn("提示消息","任务尚未审核,请审核!");
					 }else if(status.indexOf("请输入条码")>=0){
						LG.showWarn("提示消息","请输入条码!");
					}
				 }
			});
			/**
			 * 关闭窗口方法
			 */
			$('#btn2').click(function(){
				window.location.href = "<%=path%>/main.jsp?business="+business;
			});
			$('#btn10').click(function(){
				window.location.href = "<%=path%>/main.jsp?business="+business;
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
		};
		//enter后触发
		function showInfo(event){
			if(event.keyCode==13){
				var codeId = $('#setCodeId').attr('value');
				if(codeId==''){
					LG.showWarn("提示消息","扫描失败,重试或手工输入!");
				}else{
					var params = {"codeId":codeId,"currentPage":currentPage} ;
					$.ajax({
						type: 'post',
						url: 'redempProcess!showRedempInfoAction.action',
						cache:false ,
						data:params,
						dataType:'json' ,
						success:function(result){
							if(result.redCode=="A000000"){
								taskId = result.taskId;
								businessId = result.redemptionInfo.businessId;
								status = result.status;
								$("#processStatus").val(status);
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
								$("#codeId").val(result.redemptionInfo.codeId);
								$("#remark").val(result.redemptionInfo.remark);
								if(result.productRedemptionInterval!=null){
									$("#redempBegin").val(result.productRedemptionInterval.redeemBegin);
									$("#redempEnd").val(result.productRedemptionInterval.redeemEnd);
									$("#redemptionIntervalId").val(result.productRedemptionInterval.redemptionIntervalId);
								}
							}else{
								LG.showError("提示信息","系统访问异常,请联系管理员!");
							}
						}
					});
				}
			}
		}
		function selectRedemps(){
			var productCode = $('#productCode').attr('value');
			var codeId = $('#codeId').attr('value');
			if(codeId==""){
				LG.showWarn("提示消息","尚未显示产品合同信息,请先扫描条码!");
			}else if(status=='待提交信托'){
				LG.showWarn("提示消息","已确定赎回区间,不可更改!");
			}else if(productCode==""){
				LG.showWarn("提示消息","产品编码未显示,请联系产品管理岗人员,确认系统中是否存在该产品!");
			}else{
				$('#redempDialog').dialog('open').dialog("refresh","flow/pribank/selectRedemp.jsp?productCode='"+productCode+"'");
			}
		}
	</script>
  </head>
  <body>
   	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
   		<div region="west" split="true" style="background-color:#E4F5EF;width:200px;height:35%">
   			<table>
   				<tr>
   					<td>&nbsp;扫描条码:</td>
   				</tr>
   				<tr>
   					<td colspan="2">
   						<input type="text" id="setCodeId" name="getCodeId" style="width:180px;height:22px;border:1px solid #A4BED4;" onKeyDown="showInfo(event)"></input>
   					</td>
   				</tr>
   				<tr>
   					<td>
   						<a href="javascript:void(0)" id="showInfo" class="easyui-linkbutton">确定</a>
   						<a href="javascript:void(0)" id="btn2" class="easyui-linkbutton">返回</a>
   					</td>
   				</tr>   					 					    					    					    					    					    					    					    					
   			</table>
   		</div>
   		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:35%">
			<table>
   				<tr>
   					<td colspan="2">
   						<a href="javascript:void(0)" id="btn1" class="easyui-linkbutton">签收</a>
   						<a href="javascript:void(0)" id="btn8" class="easyui-linkbutton">通过</a>
   						<a href="javascript:void(0)" id="btn9" class="easyui-linkbutton">驳回</a>
   						<a href="javascript:void(0)" id="submitxintuo" class="easyui-linkbutton">提交信托</a>
   						<a href="javascript:void(0)" id="btn10" class="easyui-linkbutton">返回</a>
   					</td>
   				</tr> 
   			</table>  					 					    					    					    					    					    					    					    					
   		</div>
   		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
    		<br/>
    		<form id="myformtwo" action="" method="post">
    			<input type="hidden" id="task" name="taskId"/>
    			<input type="hidden" id="business" name="businessId"/>
    		 	<div class="title" align="center"><table><tr><td><h4>理财产品赎回流程(<font style="color:red">*必填</font>)</h4></td></tr></table></div>
    				<table width="80%" border="1" align="center" style="background-color:#E4F5EF">
    					<tr>
							<td width="15%" align="center"><font color="red">流程状态</font></td>
							<td width="25%">&nbsp;<input id="processStatus" name="processStatus" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" readonly="readonly"></td>
							<td width="15%" align="center"><font color="red">赎回单条码&nbsp;*</font></td>
    						<td width="25%">&nbsp;<input id="codeId" type="text" name="codeId" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
    					<tr>
    						<td width="15%" align="center">客户号&nbsp;<font color="red">*</font></td>
    						<td width="25%">&nbsp;<input type="text" id="customeId" name="customeId" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/>
    						</td>
    						<td width="15%" align="center">客户姓名&nbsp;<font color="red">*</font></td>
    						<td width="25%">&nbsp;<input id="customeName" name="customeName" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">销售日期&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input type="text" id="saleDate" name="saleDate" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">产品类型&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input type="text" id="productType"name="productType" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">产品编码&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input type="text" id="productCode" name="productCode" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">产品名称&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="productName" type="text" name="productName" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">金额  (万)&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="money" type="text" name="money" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">签约帐号&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="signAccount" type="text" name="signAccount" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">销售经理&nbsp;</td>
    						<td>&nbsp;<input id="saleManager" type="text" name="saleManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">理财经理&nbsp;</td>
    						<td>&nbsp;<input id="businessManager" type="text" name="businessManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">归属机构&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="belongDepartment" type="text" name="belongDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">签约机构&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="signDepartment" type="text" name="signDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">赎回区间&nbsp;</td>
    						<td colspan="3">&nbsp;<input id="redempBegin" type="text" name="redempBegin" value="" readonly="readonly" style="background-color: #E1E6E9;width:30%;height:22px;border:1px solid #A4BED4;"/>
    							&nbsp;~&nbsp;<input id="redempEnd" type="text" name="redempEnd" value="" readonly="readonly" style="background-color: #E1E6E9;width:30%;height:22px;border:1px solid #A4BED4;"/>
    							<input id="redemptionIntervalId" type="hidden" name="redemptionIntervalId">
    							<a href="javascript:void(0)" onclick="selectRedemps()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" ><font size="1">选择</font></a>
    						</td>
    					</tr>
    					<tr>
		    				<td align="center">备注&nbsp;</td>
		    				<td colspan="3">&nbsp;<input id="remark" type="text" name="remark" value="" style="width:80%;height:22px;border:1px solid #A4BED4;"/></td>
		    			</tr>			    					    					    					    					    					    					
    				</table>
    			</form>
   			</div>
   	</div>
   	<div id="redempDialog" title="选择赎回区间" modal=false draggable=true class="easyui-dialog" closed=true style="width:700px;height:450px;overflow:hidden">
   	</div>
 </body>
</html>
