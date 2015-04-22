<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>startSontractProcess</title>
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
		var contractParams;
		var nextUserParams;
		$(function(){
			/**
			 * 罗列客户的所有合同,以供选择
			 */
			 $('#searchCustomeCon').click(function(){
				 var customeId = $('#customeId').attr('value');
				 if(customeId!=''){
					 var table = 'contractView';
					 $('#customeId0').val(customeId);
					 $('#table').val(table);
					 contractParams = {'customeId':customeId,'table':table};
					 $('#selectedCustomeCon').html("");
					 $('#customeConDialog').dialog('open');		//弹出选择窗口
			    	 $('#customeConList').datagrid({
			    		 fit:true ,
						 height:450 ,
			             url: 'contractAction!getCustomeConList.action',
			             queryParams:contractParams,
			             method: 'post',
			             loadMsg: '数据正在加载,请耐心的等待...' ,
			             fitColumns:false ,  
						 striped: true ,					
			             rownumbers:true ,					
			             pagination: true ,
			             onLoadSuccess: function (result){
							LG.showMsg("查询结果提示",result.retCode,result.message,false);
						 },
						 onLoadError: function (xhr,data){
							showErrorMessage(xhr);
						 },
			             frozenColumns:[[				
							{
								field:'ck',width:50,checkbox: true
							},{
								field:'customeId',title:'客户号',width:100,align:'center' 
							},{
								field:'customeName',title:'客户姓名' ,width:80 
							}
						 ]],
						 columns:[[
							{
								field:'contractId',title:'合同号',hidden: true
							},{
					           field:'handStatus',title:'合同移交状态',width:100 ,
					           formatter:function(value , record , index){
									if(value == "录入未移交"){
										return '<span style=color:red; >录入未移交</span>' ;
									}else if(value == "合同已移交"){
										return '<span style=color:green; >合同已移交</span>' ;
									}else if(value == "合同已返回"){
										return '<span style=color:blue; >合同已返回</span>' ;
									}else if(value == "合同已领取"){
										return '<span style=color:blue; >合同已领取</span>' ;
									}
								}
			                },{
								field:'saleDate',title:'销售日期',width:100 
							},{
								field:'productType',title:'产品类型',width:100 
							},{
								field:'productCode',title:'产品编码',width:100 
							},{
								field:'productName', title:'产品名称',  width:150 
							},{
								field:'money',title:'金额  (万)',width:70 
							},{
								field:'signAccount',title:'签约帐号',width:100 
							},{
								field:'saleManager',title:'销售经理',width:100 
							},{
								field:'businessManager',title:'理财经理',width:100 
			             	},{
				            	field:'belongDepartment',title:'归属机构',width:100 
			              	},{
					           field:'signDepartment',title:'签约机构',width:100 
			                }
						 ]] ,
						 pageSize: 10 ,
						 pageList:[10,20,50],
						 toolbar:[
							 {
								 text:'选择',
								 iconCls:'icon-add',
								 handler:function(){
								 	var trNum = $('#selectedCustomeCon tr').size();	//判断已经选择了几个选项,selectedCustomeCon关联的table下面有多少个tr
								 	if(trNum===1){
								 		LG.showWarn("提示信息","已存在一个选项!");
								 	}else if(trNum===0){									//如果没有选项，则可选择一个
								 		var str= "";
									 	var arr = $('#customeConList').datagrid('getSelections');
									 	if(arr.length <=0){
									 		LG.showWarn("提示信息","请至少选择一项!");
									 	}else if(arr.length != 1){
									 		LG.showWarn("提示信息","只能选择一项!");
									 	}else{
									 		if(arr[0].handStatus!="录入未移交"){
									 			LG.showWarn("提示信息","所选合同已移交,不可重复启动流程!");
									 		}else{
										 		str += "<tr><td><input type='checkbox' checked='checked'/>"+arr[0].customeId+"</td>";
										 		str += "<td style='display:none'>"+arr[0].customeName+"</td>";
										 		str += "<td style='display:none'>"+arr[0].saleDate+"</td>";
										 		str += "<td style='display:none'>"+arr[0].productType+"</td>";
										 		str += "<td style='display:none'>"+arr[0].productCode+"</td>";
										 		str += "<td style='display:none'>"+arr[0].productName+"</td>";
										 		str += "<td style='display:none'>"+arr[0].money+"</td>";
										 		str += "<td style='display:none'>"+arr[0].signAccount+"</td>";
										 		str += "<td style='display:none'>"+arr[0].saleManager+"</td>";
										 		str += "<td style='display:none'>"+arr[0].businessManager+"</td>";
										 		str += "<td style='display:none'>"+arr[0].belongDepartment+"</td>"
										 		str += "<td style='display:none'>"+arr[0].signDepartment+"</td>"
										 		str += "<td style='display:none'>"+arr[0].contractId+"</td>"
										 		str += "</tr>";
										 		$('#selectedCustomeCon').append(str);
									 		}
									 	}
								 	}
								 }
							 }
						 ]
					});
				 }else{
					 LG.showWarn("提示信息","请输入客户号!");
				 } 
			 });
		     /**
		      * 下面是保存按钮
		      * @memberOf {TypeName} 
		      */
		    $("#saveCustomeCon").click(function(){
		    	var i=0;
		    	if($('#selectedCustomeCon tr').size()===0){			//判断table里面是否包含tr
		    		LG.showWarn("提示信息","已选项为空,请添加选项!");
		    	}else{
		    		$('#selectedCustomeCon tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
		    			if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
		    				 i=1;
		    				 $('#customeId').val($(this).children("td").eq(0).text());
		    				 $('#customeName').val($(this).children("td").eq(1).text());
		    				 $('#saleDate').val($(this).children("td").eq(2).text());
		    				 $('#productType').val($(this).children("td").eq(3).text());
		    				 $('#productCode').val($(this).children("td").eq(4).text());
		    				 $('#productName').val($(this).children("td").eq(5).text());
		    				 $('#money').val($(this).children("td").eq(6).text());
		    				 $('#signAccount').val($(this).children("td").eq(7).text());
		    				 $('#saleManager').val($(this).children("td").eq(8).text());
		    				 $('#businessManager').val($(this).children("td").eq(9).text());
		    				 $('#belongDepartment').val($(this).children("td").eq(10).text());
		    				 $('#signDepartment').val($(this).children("td").eq(11).text());
		    				 $('#contractId').val($(this).children("td").eq(12).text());
		    				 $('#customeConDialog').dialog('close');		//关闭选择窗口
		    			}
		    		});
		    		if(i===0){
		    			LG.showWarn("提示信息","未勾选需保存的选项!");
		    		}
		    	}
		    });
		      /**
		       * 移除已选项中选中的项
		       * @memberOf {TypeName} 
		       */
		    $("#removeCustomeCon").click(function(){
		    	var i = 0;
			    if($('#selectedCustomeCon tr').size()===0){			//判断table里面是否包含tr
			    	LG.showWarn("提示信息","已选项为空!");
			    }else{
			    	$('#selectedCustomeCon tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
			    		if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
			    			$(this).remove();
			    			i++;
			    		}
			    	});
			    	if(i===0){
			    		LG.showWarn("提示信息","未勾选需移除的选项!");
			    	}
				}
		    });
		    
		   /**
		    * 检测条码是否可用
		    */
	       $('#myView').click(function(){
	    	   var codeId = $('#codeId').attr('value');
				if(codeId!=""){
					$.ajax({
						url:'contractProcess!checkCodeId.action',
						type:'post',
						dataType:'json',
						data:{
							"codeId":codeId
						},
						cache:false,
						success:function(result){
							if(result.retCode=="A000000"){
								LG.showSuccess("提示消息",result.message);
							}else if(result.retCode=="E888888"){
								LG.showWarn("提示信息",result.message);
							}else{
								LG.showError("提示信息","系统访问异常,请联系管理员!");
							}
						}
					});
				}else{
					LG.showWarn("提示消息","请正确填写所要检测的条码号!");
				}
	       });
		       
		    /**
			 * 罗列所有下一候选办理人,以供选择
			 */
			 $('#selectUser').click(function(){
				 $('#selectedUser').html("");
				 var assignUser = $('#assignUser').attr('value');
				 if(assignUser!=''){
					 str = "";
					 str += "<tr><td><input type='checkbox' checked='checked'/>"+assignUser+"</td></tr>";
				 	 $('#selectedUser').append(str);
				 }
				 nextUserParams = {'roleId':'7'};
				 $('#roleIdDlg').val(nextUserParams.roleId);
				 $('#userDialog').dialog('open');		//弹出选择窗口
		    	 $('#userList').datagrid({
		    		 fit:true ,
					 height:450 ,
					 url:'queryUsersByRole!queryUsersByRole.action' ,
					 queryParams:nextUserParams,
		             method: 'post',
		             loadMsg: '数据正在加载,请耐心的等待...' ,
		             fitColumns:true ,  
					 striped: true ,					//隔行变色特性
		             rownumbers:true ,					//显示行号
		             pagination: true , 				//显示分页
		             onLoadSuccess: function (result){
						LG.showMsg("查询结果提示",result.retCode,result.message,false);
					 },
					 onLoadError: function (xhr,data){
						showErrorMessage(xhr);
					 },
		             frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
						{
							field:'ck',width:50,checkbox: true
						}
					 ]],
					 columns:[[
						{
							field:'userId',title:'用户编号' ,width:80 ,align:'center' 
						},{
							field:'username',title:'用户名' ,width:80 ,align:'center'
						},{
							field:'contact',title:'联系方式' ,width:100 ,align:'center'
						},{
							field:'sex' ,title:'性别' ,width:50 ,align:'center'
						},{field:'departmentName' ,title:'所属机构' ,width:80 ,align:'center'}
					 ]] ,
					 pageSize: 10 ,
					 pageList:[10,20,50],
					 toolbar:[
						 {
							 text:'选择',
							 iconCls:'icon-add',
							 handler:function(){
							 	var trNum = $('#selectedUser tr').size();	//判断已经选择了几个选项,selectedCustomeCon关联的table下面有多少个tr
							 	if(trNum===1){
							 		LG.showWarn("提示消息","已存在一个选项!");
							 	}else if(trNum===0){									//如果没有选项，则可选择一个
							 		var str= "";
								 	var arr = $('#userList').datagrid('getSelections');
								 	if(arr.length <=0){
								 		LG.showWarn("提示消息","请至少选择一项!");
								 	}else if(arr.length != 1){
								 		LG.showWarn("提示消息","只能选择一项!");
								 	}else{
								 		str += "<tr><td><input type='checkbox' checked='checked'/>"+arr[0].username+"("+arr[0].userId+")"+"</td>";
								 		str += "</tr>";
								 		$('#selectedUser').append(str);
								 	}
							 	}
							 }
						 }
					 ]
				});
			 });
			
			 /**
		     * 将客户合同列表选中的保存或清除
		     */
		     /**
		      * 下面是保存按钮
		      * @memberOf {TypeName} 
		      */
		    $("#saveUser").click(function(){
		    	var i=0;
		    	if($('#selectedUser tr').size()===0){			//判断table里面是否包含tr
		    		LG.showWarn("提示消息","已选项为空,请添加选项!");
		    	}else{
		    		$('#selectedUser tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
		    			if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
		    				 i=1;
		    				 $('#assignUser').val($(this).children("td").eq(0).text());
		    				 $('#userDialog').dialog('close');		//关闭选择窗口
		    			}
		    		});
		    		if(i===0){
		    			LG.showWarn("提示消息","未勾选需保存的选项!");
		    		}
		    	}
		    });
		      /**
		       * 移除已选项中选中的项
		       * @memberOf {TypeName} 
		       */
		    $("#removeUser").click(function(){
		    	var i = 0;
			    if($('#selectedUser tr').size()===0){			//判断table里面是否包含tr
			    	LG.showWarn("提示消息","已选项为空!");
			    }else{
			    	$('#selectedUser tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
			    		if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
			    			$(this).remove();
			    			i++;
			    		}
			    	});
			    	if(i===0){
			    		LG.showWarn("提示消息","未勾选需移除的选项!");
			    	}
				}
		    });
		    $('#searchConbtn').click(function(){
				$('#customeConList').datagrid('load' ,serializeForm($('#mysearchCon')));
			});
			
			$('#clearConbtn').click(function(){
				$('#mysearchCon').form('clear');
				$('#customeId0').val(contractParams.customeId);
				$('#table').val(contractParams.table);
				$('#customeConList').datagrid('load' ,contractParams);
			});
			
			$('#searchUserbtn').click(function(){
				$('#userList').datagrid('load' ,serializeForm($('#mysearchUser')));
			});
			
			$('#clearUserbtn').click(function(){
				$('#mysearchUser').form('clear');
				$('#roleIdDlg').val(nextUserParams.roleId);
				$('#userList').datagrid('load' ,nextUserParams);
			});
		});
	</script>
	

  </head>
  
  <body>
    <div id="customeConDialog" title="选择客户合同" modal=false draggable=true class="easyui-dialog" closed=true style="width:800px;height:450px">
		<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
			<div region="north" title="信息查询" align="center" style="background-color:#E4F5EF;width:100%;height:60%;overflow:hidden">
				<form id="mysearchCon" method="post">
					<table width="100%">
						<tr>
							<td width="15%">&nbsp;产品编码:&nbsp;<input name="productCode" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
							<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/>
								<input type="hidden" id="table" name="table"/>
								<input type="hidden" id="customeId0" name="customeId"/>
							</td>
							<td width="15%" align="left"><a id="searchConbtn" href="javascript:void(0)" class="easyui-linkbutton">查询</a> <a id="clearConbtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a></td>
						</tr>						
					</table>
				</form>
			</div>
			<div region="east" title="已选项" split="true" style="background-color:#E4F5EF;width:160px;height:35%">
				<div style="height:70%">
					<table id="selectedCustomeCon">
					</table>
				</div>
				<hr>
				<div>
					<table>
						<tr>
							<td colspan="2">
								<a href="javascript:void(0)" id="saveCustomeCon" class="easyui-linkbutton">保存</a>
								<a href="javascript:void(0)" id="removeCustomeCon" class="easyui-linkbutton">移除</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
				<table id="customeConList" title="客户合同信息" class="easyui-datagrid" align="center" border="1" style="height:auto;background-color:#E4F5EF">
				</table>
			</div>
		</div>
	</div>
	<div id="userDialog" title="选择办理人" modal=false draggable=true class="easyui-dialog" closed=true style="width:700px;height:450px">
		<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
			<div region="north" title="信息查询" align="center" style="background-color:#E4F5EF;width:100%;height:60%;overflow:hidden">
				<form id="mysearchUser" method="post">				
					<table width="100%">						
						<tr>
							<td width="15%">&nbsp;用户编号:&nbsp;<input name="userId" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
							<td width="20%">&nbsp;用户名:&nbsp;<input name="username" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/>
								<input type="hidden" id="roleIdDlg" name="roleIdInDlg"/>
							</td>
							
							<td width="15%" align="left"><a id="searchUserbtn" href="javascript:void(0)" class="easyui-linkbutton">查询</a> <a id="clearUserbtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a></td>
						</tr>						
					</table>
				</form>
			</div>
			<div region="east" title="已选项" split="true" style="background-color:#E4F5EF;width:160px;height:35%">
				<div style="height:70%">
					<table id="selectedUser">
					</table>
				</div>
				<hr>
				<div>
					<table>
						<tr>
							<td colspan="2">
								<a href="javascript:void(0)" id="saveUser" class="easyui-linkbutton">保存</a>
								<a href="javascript:void(0)" id="removeUser" class="easyui-linkbutton">移除</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
				<table id="userList" title="候选办理人列表" class="easyui-datagrid" align="center" border="1" style="height:auto;background-color:#E4F5EF">
				</table>
			</div>
		</div>
	</div>
  </body>
</html>
