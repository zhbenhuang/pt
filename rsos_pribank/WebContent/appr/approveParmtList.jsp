<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Integer business = (Integer)session.getAttribute("BUSINESS");
	System.out.println("business="+business);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">    
    <title>审批流程设置</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>		
	<script type="text/javascript">
	var actionMethod="saveApproveParmt";		//判断新增和修改方法 
	$(function(){
		var business = <%=business%>;
		$('#newApproveParmtDialog').dialog({
			onClose:function()
			{
				$('#posbusiness').html("");
			} 
		});
		
		/**
		 *	初始化数据表格  
		 */
		$('#t_approveParmt').datagrid({
			title:'审批流程列表' ,
			fit:true ,
			height:450 ,
			url:'queryApproveParmtList!queryApproveParmtList.action' ,
			fitColumns:true ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			frozenColumns:[[
				{field:'ck' ,width:50 ,checkbox: true}
			]], 
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'apprType' ,title:'审批流程类型' ,width:100 ,align:'center'},
				{field:'apprName' ,title:'审批流程名称' ,width:100 ,align:'center'},
				{
					field:'apprSwitch' ,
					title:'开关' ,
					width:100 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 1){
							return '<span style=color:black; >开</span>' ;
						}else if(value == 2){
							return '<span style=color:black; >关</span>' ;
						}
					}
				},
				{field:'stepNum' ,title:'审批步数' ,width:100 ,align:'center'},
				{field:'stepRole1' ,title:'审批角色1' ,width:100 ,align:'center'},
				{field:'stepRole2' ,title:'审批角色2' ,width:100 ,align:'center'},
				{field:'stepRole3' ,title:'审批角色3' ,width:100 ,align:'center'},
				{field:'stepRole4' ,title:'审批角色4' ,width:100 ,align:'center'},
				{field:'stepRole5' ,title:'审批角色5' ,width:100 ,align:'center'},
				{field:'userId' ,title:'维护人' ,width:100 ,align:'center'},
				{field:'alterTime' ,title:'维护时间' ,width:110 ,align:'center'}
			]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[10,20,50] ,
			toolbar:[
			       	 {
			       		text:'新增审批流程' ,
			       		iconCls:'icon-my-add' , 
						handler:'addApproveParmt'
			       	 },							
			         {
			       		 text:'修改审批流程' ,
			       		 iconCls:'icon-my-edit' ,
			       		 handler:'modifyApproveParmt'
			         },
			         {
			        	 text:'删除审批流程' ,
			       		 iconCls:'icon-my-delete' ,
			       		 handler:'deleteApproveParmt' 
			         }
			]
		});
		
		/**
		 *  提交表单方法
		 */
		$('#confirmAdd').click(function(){
			if($('#newApproveParmtForm').form('validate')){				
				$.ajax({
					type: 'post' ,
					url: actionMethod+'!'+actionMethod+'.action',
					cache:false ,
					data:$('#newApproveParmtForm').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='A000000'){
							$.messager.alert('提示消息',result.message,'info',function(){
								$('#newApproveParmtDialog').dialog('close');
								$('#t_approveParmt').datagrid('reload');
							});
						}else{
							LG.showError("提示信息",result.message);
						}
					}
				});
			} else {
				LG.showError("提示信息","数据验证不通过,不能保存!");
			}
		});
		

		/**
		 * 关闭窗口方法
		 */
		$('#cancelAdd').click(function(){
			$('#newApproveParmtDialog').dialog('close');
		});
					
						
		$('#searchbtn').click(function(){
			$('#t_approveParmt').datagrid('load' ,serializeForm($('#mysearch')));
		});
							
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_approveParmt').datagrid('load' ,{});
		});
		
		$('#stepNum').change(function(e) { 
			var value = $('#stepNum').val();
			if(value == 1){
				$('#sr1').show();
				$('#sr2').hide();
				$('#sr3').hide();
				$('#sr4').hide();
				$('#sr5').hide();
			}else if(value == 2){
				$('#sr1').show();
				$('#sr2').show();
				$('#sr3').hide();
				$('#sr4').hide();
				$('#sr5').hide();
			}else if(value == 3){
				$('#sr1').show();
				$('#sr2').show();
				$('#sr3').show();
				$('#sr4').hide();
				$('#sr5').hide();
			}else if(value == 4){
				$('#sr1').show();
				$('#sr2').show();
				$('#sr3').show();
				$('#sr4').show();
				$('#sr5').hide();
			}else if(value == 5){
				$('#sr1').show();
				$('#sr2').show();
				$('#sr3').show();
				$('#sr4').show();
				$('#sr5').show();
			}
		});
		
		$('#stepRole1').combobox({ 
			url:'loadRoles!loadRoles.action', 
			valueField:'id', 
			textField:'text' 
		}); 
		$('#stepRole2').combobox({ 
			url:'loadRoles!loadRoles.action', 
			valueField:'id', 
			textField:'text' 
		}); 
		$('#stepRole3').combobox({ 
			url:'loadRoles!loadRoles.action', 
			valueField:'id', 
			textField:'text' 
		}); 
		$('#stepRole4').combobox({ 
			url:'loadRoles!loadRoles.action', 
			valueField:'id', 
			textField:'text' 
		}); 
		$('#stepRole5').combobox({ 
			url:'loadRoles!loadRoles.action', 
			valueField:'id', 
			textField:'text' 
		}); 

	});
	
	function addApproveParmt(){
		actionMethod = 'saveApproveParmt';
		$('#newApproveParmtDialog').dialog({
			title:'新增审批流程' 
		});
		$('#newApproveParmtForm').get(0).reset();
		$('#newApproveParmtDialog').dialog('open');
		
		$('#apprTypeNew').removeAttr("readonly");
		$('#newApproveParmtForm').form('load',{
			stepNum:"2",
			stepNumHidden:"2",
			apprSwitch:"1",
			apprSwitchHidden:"1"
		});
		$('#sr1').show();
		$('#sr2').show();
		$('#sr3').hide();
		$('#sr4').hide();
		$('#sr5').hide();
	}
	
	function modifyApproveParmt(){
		actionMethod = 'modifyApproveParmt';
		var grid =$('#t_approveParmt').datagrid('getSelections');
		if(grid.length != 1){
			LG.showWarn("提示信息","请选择一条记录修改标的!");
		} else {
			$('#newApproveParmtDialog').dialog({
				title:'修改审批流程'
			});
			$('#apprTypeNew').attr("readonly","readonly");
			$('#newApproveParmtDialog').dialog('open'); //打开窗口
			$('#newApproveParmtForm').get(0).reset();   //清空表单数据 
			$('#newApproveParmtForm').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
				apprType:grid[0].apprType ,
				apprName:grid[0].apprName,
				apprSwitch:grid[0].apprSwitch,
				apprSwitchHidden:grid[0].apprSwitch,
				stepNum:grid[0].stepNum,
				stepNumHidden:grid[0].stepNum,
				stepRole1:grid[0].stepRole1,
				stepRole1Hidden:grid[0].stepRole1,
				stepRole2:grid[0].stepRole2,
				stepRole2Hidden:grid[0].stepRole2,
				stepRole3:grid[0].stepRole3,
				stepRole3Hidden:grid[0].stepRole3,
				stepRole4:grid[0].stepRole4,
				stepRole4Hidden:grid[0].stepRole4,
				stepRole5:grid[0].stepRole5,
				stepRole5Hidden:grid[0].stepRole5,
				userId:grid[0].userId,
				alterTime:grid[0].alterTime
			});
		}
	}
	
	function deleteApproveParmt(){
		var grid =$('#t_approveParmt').datagrid('getSelections');
		if(grid.length <=0){
			LG.showWarn("提示信息","请选择记录进行删除!");
		} else {
			$.messager.confirm('提示信息' , '确认删除?' , function(r){
				if(r){
					var ids = '';
					for(var i =0 ;i<grid.length;i++){
						ids += grid[i].apprType + ',' ;
					}
					if(i>0){
						ids = ids.substring(0 , ids.length-1);
					}
					$.post('deleteApproveParmt!deleteApproveParmt.action' , 
						{ids:ids} , 
						function(result){
							$.messager.alert('提示信息' , result.message,'info',function(){
								if(result.retCode=="A000000"){													
									//1 刷新数据表格 
									$('#t_approveParmt').datagrid('reload');
									//2 清空idField   
									$('#t_approveParmt').datagrid('unselectAll');	
								}else{
									LG.showError("提示信息","删除失败!");
								}
							});
						},'json');
				} else {
					return ;
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
	</script>

  </head>
  
    <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="center" style="width: 100%;height:100%">
			<table id="t_approveParmt" style="width: 100%;height:100%"></table>
		</div>
	</div>
	
	
  	
  	<div id="newApproveParmtDialog" title="新增审批流程" class="easyui-dialog" style="width:400px;height:500px;background-color:#E4F5EF;"  
        data-options="iconCls:'icon-save',modal:true,draggable:true,closed:true">
		<form id="newApproveParmtForm" action="" method="post"  align="center">
		<input id="apprTypeNew" name="apprType" type="hidden" value="" />
		<br/>
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td align="right">审批流程名称:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="apprName" type="text" class="easyui-validatebox" required=true missingMessage="审批流程名称不可为空!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="right">审批开关&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="apprSwitchHidden" id="apprSwitchHidden" type="hidden"/>
					<input name="apprSwitch" id="apprSwitch" style="width:142px" class="easyui-combobox" required=true  missingMessage="审批开关必选!" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectApprSwitch" />
				</td>
			</tr>
			<tr>
				<td align="right">审批步骤&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="stepNumHidden" id="stepNumHidden" type="hidden"/>
					<!--  <input name="stepNum1" id="stepNum1" type="text" style="width:142px" class="easyui-combobox" required=true  missingMessage="审批步数必选!" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectStepNum" />
					-->
					<select id="stepNum" class="" name="stepNum" style="width:142px">
    					<option value="1">1</option>
    					<option value="2">2</option>
    					<option value="3">3</option>
    					<option value="4">4</option>
    					<option value="5">5</option>
    				</select>
				</td>
			</tr>
			<tr id="sr1">
				<td align="right">步骤一审批角色:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="stepRole1" id="stepRole1" type="text" value="" /></td>
			</tr>
			<tr id="sr2">
				<td align="right">步骤二审批角色:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="stepRole2" id="stepRole2" type="text" value="" /></td>
			</tr>
			<tr id="sr3">
				<td align="right">步骤三审批角色:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="stepRole3" id="stepRole3" type="text" value="" /></td>
			</tr>
			<tr id="sr4">
				<td align="right">步骤四审批角色:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="stepRole4" id="stepRole4" type="text" value="" /></td>
			</tr>
			<tr id="sr5">
				<td align="right">步骤五审批角色:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="stepRole5" id="stepRole5" type="text" value="" /></td>
			</tr>
			
			
			<tr align="center">
				<td colspan="2">
					<a href="javascript:void(0)" id="confirmAdd" class="easyui-linkbutton">保存</a>
					<a href="javascript:void(0)" id="cancelAdd" class="easyui-linkbutton">关闭</a>
				</td>
			</tr>
		</table>
		</form> 			
	</div>
  </body>
</html>
