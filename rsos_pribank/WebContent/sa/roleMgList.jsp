<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Integer business = (Integer)session.getAttribute("BUSINESS");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">    
    <title>角色管理</title>    
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
	var userParams;
	var actionMethod="saveRole";		//判断新增和修改方法 
	$(function(){
		var business = <%=business%>;
		$('#permissionDlg').dialog({
			onClose:function()
			{
				$('#permissiontable').html("");
			}
		});
		$('#newRoleDialog').dialog({
			onClose:function()
			{
				$('#posbusiness').html("");
			} 
		});
		
		/**
		 *	初始化数据表格  
		 */
		$('#t_role').datagrid({
			title:'角色列表' ,
			fit:true ,
			height:450 ,
			url:'queryRoleList!queryRoleList.action' ,
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
				{field:'roleId' ,title:'角色编号' ,width:100 ,align:'center'},
				{field:'roleName' ,title:'角色名' ,width:100 ,align:'center'},
				{
					field:'business' ,
					title:'业务' ,
					width:100 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 0){
							return '<span style=color:black; >超级管理员</span>' ;
						}else if(value == 1){
							return '<span style=color:green; >深度开发</span>' ;
						}else if(value == 2){
							return '<span style=color:blue; >私银销售</span>' ;
						}
					}
				}
			]] ,
			pagination: true , 
			pageSize: 10 ,
			pageList:[10,20,50] ,
			toolbar:[
			       	 {
			       		text:'新增角色' ,
			       		iconCls:'icon-my-add' , 
						handler:'addRole'
			       	 },							
			         {
			       		 text:'修改角色' ,
			       		 iconCls:'icon-my-edit' ,
			       		 handler:'modifyRole'
			         },
			         {
			        	 text:'删除角色' ,
			       		 iconCls:'icon-my-delete' ,
			       		 handler:'deleteRole' 
			         },
			         {
			        	 text:'权限分配' ,
			       		 iconCls:'icon-document_out' ,
			       		 handler:'getPermission'
			         },
			         {
			        	 text:'查看该角色用户' ,
			       		 iconCls:'icon-my-view' ,
			       		 handler:'getUsers'
			         }
			]
		});
		
		/**
		 *  提交表单方法
		 */
		$('#confirmAdd').click(function(){
			if($('#newRoleForm').form('validate')){				
				$.ajax({
					type: 'post' ,
					url: actionMethod+'!'+actionMethod+'.action',
					cache:false ,
					data:$('#newRoleForm').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='A000000'){
							$.messager.alert('提示消息',result.message,'info',function(){
								$('#newRoleDialog').dialog('close');
								$('#t_role').datagrid('reload');
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
		 * 保存角色方法
		 */
		 $('#savePermissionBtn').click(function(){		  	
		  	var permissionString = "";					  	
		  	$('.permission').each(function(){
		  		if($(this).attr('checked')== "checked"){
		  			permissionString += $(this).val();
					permissionString +=",";
				}
			});

		  	var params = {
	 	  		"roleId" : $('#roleIdInPermission').val(),
	 	  		"business" : $('#businessInPermission').val(),
	 	  		"permissionString" : permissionString
	 	  	};
		 	$.ajax({
		 		type:'post',
		 		url:'savePermissions!savePermissions.action',
		 		cache:false,
		 		data:params,
		 		dataType:'json',
		 		success:function(result){
 					//1 关闭窗口
					$('#permissionDlg').dialog('close');
					//2刷新datagrid 
					$('#t_role').datagrid('reload');
					//3 提示信息
					LG.showMsg("提示信息",result.ret.retCode,result.ret.message,true);
		 		}
		 	});
		});
		
		$('#cancelPermissionBtn').click(function(){
			$('#permissionDlg').dialog('close');
		});
		/**
		 * 关闭窗口方法
		 */
		$('#cancelAdd').click(function(){
			$('#newRoleDialog').dialog('close');
		});
					
						
		$('#searchbtn').click(function(){
			$('#t_role').datagrid('load' ,serializeForm($('#mysearch')));
		});
							
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_role').datagrid('load' ,{});
		});
		$('#searchUserBtn').click(function(){
			$('#t_user').datagrid('load' ,serializeForm($('#mysearchTwo')));
		});
		$('#clearUserBtn').click(function(){
			$('#mysearchTwo').form('clear');
			$('#roleIdInDlg').val(userParams.roleId);
			$('#businessInDlg').val(userParams.business);
			$('#t_user').datagrid('load' ,userParams);
		});

	});
	
	function addRole(){
		actionMethod = 'saveRole';
		$('#newRoleDialog').dialog({
			title:'新增角色' 
		});
		$('#newRoleForm').get(0).reset();
		$('#newRoleDialog').dialog('open');
		
		$('#newRoleForm').form('load',{
			businessHidden:business,
			business:business
		});
		if(business!=0){
			$('#business').combobox('disable');
		}
	}
	
	function modifyRole(){
		actionMethod = 'modifyRole';
		var grid =$('#t_role').datagrid('getSelections');
		if(grid.length != 1){
			LG.showWarn("提示信息","请选择一条记录修改角色!");
		} else {
			$('#newRoleDialog').dialog({
				title:'修改角色'
			});
			$('#roleIdNew').attr("readonly","readonly");
			$('#business').combobox('disable');	//修改业务时,保存会出错,所以这里不允许修改已有用户的业务
			$('#newRoleDialog').dialog('open'); //打开窗口
			$('#newRoleForm').get(0).reset();   //清空表单数据 
			$('#newRoleForm').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
				roleId:grid[0].roleId ,
				roleName:grid[0].roleName,
				business:grid[0].business,
				businessHidden:grid[0].business
			});
			if(business!=0){
				$('#business').combobox('disable');
			}
		}
	}
	
	function deleteRole(){
		var grid =$('#t_role').datagrid('getSelections');
		if(grid.length <=0){
			LG.showWarn("提示信息","请选择记录进行删除!");
		} else {
			$.messager.confirm('提示信息' , '确认删除?' , function(r){
				if(r){
					var ids = '';
					for(var i =0 ;i<grid.length;i++){
						ids += grid[i].roleId + ',' ;
						ids += "'"+grid[i].roleId+grid[i].business + "',";
					}
					if(i>0){
						ids = ids.substring(0 , ids.length-1);
					}
					$.post('deleteRole!deleteRole.action' , 
						{ids:ids} , 
						function(result){
							$.messager.alert('提示信息' , result.message,'info',function(){
								if(result.retCode=="A000000"){													
									//1 刷新数据表格 
									$('#t_role').datagrid('reload');
									//2 清空idField   
									$('#t_role').datagrid('unselectAll');	
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

	function getPermission(){
		var grid =$('#t_role').datagrid('getSelections');
		if(grid.length <=0){
			LG.showWarn("提示信息","只能选择一行记录进行权限分配!");
		}else if(grid.length > 1){
			LG.showWarn("提示信息","只能选择一行记录进行权限分配!");
		}else{
			var roleId = grid[0].roleId;
			var business = grid[0].business;
			$("#roleId").html(roleId);
			$('#roleIdInPermission').val(roleId);
			$('#businessInPermission').val(business);

			$.ajax({					
				url:'getPermission!getPermission.action',
				type:'post',
				dataType:'json',
				cache:false,
				data:{"roleId":roleId,"business":business},
				success:function(result){
					/*var count = 0;
					var str = "";					
					$.each(result.permissionList,function(commentIndex,comment){
						if(count %3 == 0){
							str += "<tr><td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label>"+comment.permissionName+"</label></td>";
						}else{
							if((count+1)%3 == 0){
								str += "<td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label>"+comment.permissionName+"</label></td></tr>";
							}else{
								str += "<td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label>"+comment.permissionName+"</label></td>";
							}
						}
						count++;
					});
					$("#permissiontable").append(str);*/
					
					$.each(result.permissionList,function(commentIndex,comment){
						if(comment.parentId=='9999'){
							var count = 0;
							var permissionId = comment.permissionId;
							var str = "<tr align='left' style='padding-left:10px'><td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label style='font-size:15px;color:red;font-weight:bold;'>"+comment.permissionName+"</label></td></tr>";
							$.each(result.permissionList,function(commentIndex,comment){
								if(comment.parentId==permissionId){
									if(count %3 == 0){
										str += "<tr align='left' style='padding-left:50px'><td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label>"+comment.permissionName+"</label></td>";
									}else{
										if((count+1)%3 == 0){
											str += "<td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label>"+comment.permissionName+"</label></td></tr>";
										}else{
											str += "<td><input type='checkbox' class='permission' value='"+comment.permissionId+"'/></td><td><label>"+comment.permissionName+"</label></td>";
										}
									}
									count++;
								}
							});
							$("#permissiontable").append(str);
						}
					});
			
					$.each(result.ownPermissionList,function(commentIndex,comment){
						$("table label").each(function(){
	                    	if($(this).html()==comment.permissionName){
	                    		$(this).parent().prev().children().attr("checked",true);
	                    	}
	                    });
					});
										
					$('#permissionDlg').dialog('open');
				}					
			});
		}
	};
	
	function getUsers(){
		var grid =$('#t_role').datagrid('getSelections');
		if(grid.length <=0){
			LG.showWarn("提示信息","请选择一条记录查看角色对应用户!");
		}else if(grid.length > 1){
			LG.showWarn("提示信息","请选择一条记录查看角色对应用户!");
		}else{
			var roleId = grid[0].roleId;
			var business = grid[0].business;
			userParams = {
				"roleId":roleId,
				"business":business
			};
			$('#roleIdInDlg').val(roleId);
			$('#businessInDlg').val(business);
			
			$('#t_user').datagrid({
				title:'用户列表' ,
				fit:true ,
				height:200 ,
				url:'queryUsersByRole!queryUsersByRole.action' ,
				queryParams:userParams,
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
					{
						field:'userId' ,
						title:'用户编号' ,
						width:100 ,
						align:'center'
					},{
						field:'username' ,
						title:'用户名' ,
						width:100 ,
						align:'center'
					},{
						field:'business' ,
						title:'业务' ,
						width:140 ,
						align:'center',
						formatter:function(value , record , index)
						{
							if(value == 0){
								return '<span style=color:black; >超级管理员</span>' ;
							}else if(value == 1){
								return '<span style=color:green; >深度开发</span>' ;
							}else if(value == 2){
								return '<span style=color:blue; >私银销售</span>' ;
							}
						}
					},{
						field:'enabled' ,
						title:'管辖下属机构' ,
						width:140 ,
						align:'center',
						formatter:function(value , record , index)
						{
							if(value == 0){
								return '<span style=color:red; >否</span>' ;
							}else if(value == 1){
								return '<span style=color:green; >是</span>' ;
							}
						}
					},
					{field:'departmentId' ,title:'机构编号' ,width:140 ,align:'center'},
					{field:'departmentName' ,title:'机构名' ,width:140 ,align:'center'}	
				]],
				pagination: true , 
				pageSize: 10 ,
				pageList:[10,20,50]
			});
								
			$('#usersDlg').dialog('open');
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
		<div region="north" style="width: 100%;height:65%" >
		<br/>
		<form id="mysearch" method="post">  
			&nbsp;角色编号:&nbsp;<input name="roleId" type="text" size="10" class="input-style"/>
			&nbsp;角色名:&nbsp;<input name="roleName" type="text" size="15" class="input-style"/>
			&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
			&nbsp;<a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a>
			</form>
		</div>
		<div region="center" style="width: 100%;height:100%">
			<table id="t_role" style="width: 100%;height:100%"></table>
		</div>
	</div>
	
	<div id="usersDlg" title="查看角色用户" class="easyui-dialog" style="width:600px;height:400px"  
        data-options="iconCls:'icon-my-view',modal:true,draggable:true,closed:true">
        <div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
			<div region="north"  style="width: 100%;height:65%" >
			<br/>
			<form id="mysearchTwo" method="post">  
				&nbsp;用户编号:&nbsp;<input name="userId" type="text" size="10" class="input-style"/>
				&nbsp;用户名:&nbsp;<input name="username" type="text" size="10" class="input-style"/>
				<input type="hidden" id="roleIdInDlg" name="roleIdInDlg"/>
				<input type="hidden" id="businessInDlg" name="businessInDlg"/>
				&nbsp;<a href="javascript:void(0)" id="searchUserBtn" class="easyui-linkbutton">查询</a>
				&nbsp;<a href="javascript:void(0)" id="clearUserBtn" class="easyui-linkbutton">清空</a>
			</form>
			</div>
			<div region="center" style="width: 100%;height:100%">
				<table id="t_user"></table>
			</div>
		</div>
	</div>
  	
  	<div id="permissionDlg" title="分配权限" class="easyui-dialog" style="width:450px;height:400px"  
        data-options="iconCls:'icon-document_out',modal:true,draggable:true,closed:true">
		<form id="roleform">
			<input name="roleIdInPermission" id="roleIdInPermission" type="hidden"/>
			<input name="businessInPermission" id="businessInPermission" type="hidden"/>
			<font size="3px">&nbsp;角色Id：<label id="roleId">&nbsp;&nbsp;&nbsp;</label>权限如下:</font>
			<br/>					
			<table id="permissiontable" align="center">
			</table>
			<div id="button" align="center">
				<table>
					<tr align="center">
						<td colspan="2">
							<a href="javascript:void(0)" id="savePermissionBtn" class="easyui-linkbutton">保存</a>
							<a href="javascript:void(0)" id="cancelPermissionBtn" class="easyui-linkbutton">关闭</a>
						</td>
					</tr>
				</table>
			</div>
  		</form>
  	</div>
  	
  	<div id="newRoleDialog" title="新增角色" class="easyui-dialog" style="width:300px;height:200px;background-color:#E4F5EF;"  
        data-options="iconCls:'icon-save',modal:true,draggable:true,closed:true">
		<form id="newRoleForm" action="" method="post"  align="center">
		<br/>
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td align="right">角色编号:&nbsp;<font color="red">*</font></td>
				<td align="left"><input id="roleIdNew" name="roleId" type="text" class="easyui-validatebox" required=true missingMessage="角色编号为数字,不可为空!" style="height:22px;border:1px solid #A4BED4;" value=""  /></td>
			</tr>
			<tr>
				<td align="right">角色名:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="roleName" type="text" class="easyui-validatebox" required=true missingMessage="角色名不可为空!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="right">业务&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="businessHidden" id="businessHidden" type="hidden"/>
					<input name="business" id="business" style="width:142px" class="easyui-combobox" required=true  missingMessage="角色业务必选!" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectBusiness" />
				</td>
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
