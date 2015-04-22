<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String roleId = request.getParameter("roleId");
	String department = request.getParameter("department");
	if(department!=null){
		department = "true";
	}else{
		department = "false";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>选择下一办理人</title>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
 </head>
 <body style="overflow:hidden">
 	<script type="text/javascript">
 		var roleId=<%=roleId%>;
 		var department=<%=department %>
 		var nextUserParams = {'roleId':roleId,'department':department};
 		$('#roleIdDlg').val(nextUserParams.roleId);
 		$('#isSameDepartment').val(nextUserParams.department);
 		$(function(){
 			$('#selectedUser').html("");
			 var assignUser = $('#assignUserNew').attr('value');
			 if(assignUser!=''){
				 str = "";
				 str += "<tr><td><input type='checkbox' checked='checked'/>"+assignUser+"</td></tr>";
			 	 $('#selectedUser').append(str);
			 }
 			$('#userList').datagrid({
				title:'候选办理人列表' ,
				fit:true ,
				height:200 ,
				url:'queryUsersByRole!queryUsersByRole.action' ,
				queryParams:nextUserParams,
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
				frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
				{field:'ck' ,width:50 ,checkbox: true}
				]],
				columns:[[
				{field:'userId' ,title:'用户编号' ,width:80 ,align:'center'},
				{field:'username' ,title:'用户名' ,width:80 ,align:'center'},
				{field:'contact',title:'联系方式' ,width:80 ,align:'center'},
				{field:'sex' ,title:'性别' ,width:50 ,align:'center'},
				{field:'departmentName' ,title:'所属机构' ,width:80 ,align:'center'}
				]],
				pagination: true , 
				pageSize: 10 ,
				pageList:[10,20,50],
				toolbar:[
				{
					text:'选择',
					iconCls:'icon-my-add',
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
		    				 $('#assignUserNew').val($(this).children("td").eq(0).text());
		    				 $('#nextDealerDlg').dialog('close');		//关闭选择窗口
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
		       
		   $('#searchUserbtn').click(function(){
				$('#userList').datagrid('load' ,serializeForm($('#mysearchUser')));
			});
			
			$('#clearUserbtn').click(function(){
				$('#mysearchUser').form('clear');
				$('#roleIdDlg').val(nextUserParams.roleId);
				$('#isSameDepartment').val(nextUserParams.department);
				$('#userList').datagrid('load' ,nextUserParams);
			});
 		});
 	</script>
 	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
		<div region="north" title="信息查询" align="center" style="background-color:#E4F5EF;width:100%;height:60%;overflow:hidden">
			<form id="mysearchUser" method="post">				
				<table width="100%">						
					<tr>
						<td width="15%">&nbsp;用户编号:&nbsp;<input name="userId" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;用户名:&nbsp;<input name="username" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/>
							<input type="hidden" id="roleIdDlg" name="roleIdInDlg"/>
							<input type="hidden" id="isSameDepartment" name="department"/>
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
 </body>
 </html>