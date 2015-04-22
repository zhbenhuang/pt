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
    <title>用户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	<script type="text/javascript">
	$(function(){
		var actionMethod="saveUser";		//判断新增和修改方法 
		var business = <%=business%>;
		$('#roleDlg').dialog({
			onClose:function()
			{
				$('#roletable').html("");
			}
		});
		
		/**
		 *	初始化数据表格  
		 */
		$('#t_user').datagrid({				
			title:'用户列表' ,
			fit:true ,
			height:450 ,
			url:'queryUserList!queryUserList.action' ,
			fitColumns:true ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			frozenColumns:[[
				{field:'ck' ,width:50 ,checkbox: true}
			]],
			onLoadSuccess: function (result){
				LG.showMsg("查询结果提示",result.retCode,result.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'userId' ,title:'用户编号' ,width:80 ,align:'center'},
				{field:'username' ,title:'用户名' ,width:80 ,align:'center'},
				{field:'contact' ,title:'联系方式' ,width:100 ,align:'center'},
				{field:'sex' ,title:'性别' ,width:50 ,align:'center'},
				{
					field:'rank' ,
					title:'职称' ,
					width:80 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 0){
							return '<span style=color:black; >正式</span>' ;
						}else if(value == 1){
							return '<span style=color:green; >外包</span>' ;
						}else if(value == 2){
							return '<span style=color:blue; >派遣</span>' ;
						}
					}
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
				{field:'departmentId' ,title:'机构' ,hidden:true	},
				{field:'departmentName' ,title:'机构' ,width:140 ,align:'center'}
				]] ,
				pagination: true , 
				pageSize: 10 ,
				pageList:[10,20,50] ,
				toolbar:[
					{
						text:'新增用户' ,
						iconCls:'icon-my-add' , 
						handler:function(){
							actionMethod = 'saveUser';
							$('#userIdSearch').removeAttr("readonly");
							$('#newUserDialog').dialog({
								title:'新增用户' 
							});
							$('#newUserForm').get(0).reset();
							$('#newUserDialog').dialog('open');
							$('#business').combobox({hasDownArrow : true});
							if(business!=0){
								$('#newUserForm').form('load',{
									business:business
								});
								$('#business').combobox({hasDownArrow : false});
							}
						}							
					},
					{
						text:'修改用户' ,
						iconCls:'icon-my-edit' , 
						handler:function(){
							actionMethod = 'modifyUser';
							$('#userIdSearch').attr("readonly","readonly");
							var grid =$('#t_user').datagrid('getSelections');
							if(grid.length != 1){
								LG.showWarn("提示信息","请选择一条记录查看角色!");
							} else {
								$.ajax({
									url:'findUser!findUser.action',
									type:'post',
									dataType:'json',
									data:{
										"userId":grid[0].userId,
										"business":grid[0].business
									},
									cache:false ,
									async: false ,
									success:function(result){
										$('#newUserDialog').dialog({
											title:'修改用户'
										});										
										$('#newUserDialog').dialog('open'); //打开窗口
										$('#newUserForm').get(0).reset();   //清空表单数据 
										$('#business').combobox({hasDownArrow : false});//修改业务时,保存会出错,所以这里不允许修改已有用户的业务
										$('#newUserForm').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
											userId:grid[0].userId ,
											username:grid[0].username ,
											address:grid[0].address ,
											contact:grid[0].contact ,
											sex:grid[0].sex ,
											password:result.obj.password ,
											mail:grid[0].mail ,
											business:grid[0].business,
											rank:grid[0].rank,
											enabled:grid[0].enabled,
											departmentId:grid[0].departmentId,
											departmentName:grid[0].departmentName
										});
									}
								});
							}
						}
					},
					{
						text:'删除用户' ,
						iconCls:'icon-my-delete' , 
						handler:function(){
							var grid =$('#t_user').datagrid('getSelections');
							if(grid.length <=0){
								LG.showWarn("提示信息","请选择记录进行删除!");
							} else {			
								$.messager.confirm('提示信息' , '确认删除?' , function(r){
									if(r){
										var ids = "";
										for(var i =0 ;i<grid.length;i++){
											ids += "'"+grid[i].userId+grid[i].business + "',";
										}
										if(i>0){
											ids = ids.substring(0 , ids.length-1);
										}
										$.post('deleteUser!deleteUser.action' , {ids:ids},
											function(result){
												$.messager.alert('提示信息',result.message,'info',function(){
													if(result.retCode=="A000000"){													
														//1 刷新数据表格 
														$('#t_user').datagrid('reload');
														//2 清空idField   
														$('#t_user').datagrid('unselectAll');	
													}else{
														LG.showError("提示信息","删除失败!");
													}
												});
											},'json'
										);
									} else {
										return ;
									}
								});
							}
						}								
					},{
						text:'角色分配' , 
						iconCls:'icon-document_out' , 
						handler:getRole
					},{
						text:'初始化产品' , 
						iconCls:'icon-my-document_into' , 
						handler:importProducts
					}
				]
		});
		
		function importProducts(){
			$.ajax({
				type: 'post' ,
				url: 'productAction!importProductsAction.action',
				cache:false ,
				dataType:'json' ,
				success:function(result){
					if(result.retCode=='A000000'){
						$.messager.alert('提示消息',result.message,'info');
					}else{
						LG.showError("提示信息",result.message);
					}
				}
			});
		}
			
		function getRole(){
			var grid =$('#t_user').datagrid('getSelections');
			if(grid.length <=0){
				LG.showWarn("提示信息","请选择一条记录查看角色!");
			}else if(grid.length > 1){
				LG.showWarn("提示信息","请选择一条记录查看角色!");
			}else{
				var params = {
					"business":grid[0].business,
					"userId":grid[0].userId
				};
				$("#userId").html(grid[0].userId);
				$("#businessId").val(grid[0].business);
				
				$.ajax({
					type:'post',
					url:'queryRoles!queryRoles.action',
					cache:false,
					data:params,
					dataType:'json',
					success:function(result){
						var str="";
						var count = 0;
						$.each(result.roleList,function(commentIndex,comment){
							if(count %3 == 0){
								str += "<tr><td><input type='radio' name='radio-choice' class='role' value='"+comment.roleId+"'/></td><td><label>"+comment.roleName+"</label></td>";
							}else{
								if((count+1)%3 == 0){
									str += "<td><input type='radio' name='radio-choice' class='role' value='"+comment.roleId+"'/></td><td><label>"+comment.roleName+"</label></td></tr>";
								}else{
									str += "<td><input type='radio' name='radio-choice' class='role' value='"+comment.roleId+"'/></td><td><label>"+comment.roleName+"</label></td>";
								}
							}
							count++;
						});
						$("#roletable").append(str);
								
						$.each(result.ownRoleList,function(commentIndex,comment){								
							$("table label").each(function(){									
    	                    	if($(this).html()==comment.roleName){
    	                    		$(this).parent().prev().children().attr("checked",true);
    	                    	}
    	                    });
						});
								
						$('#roleDlg').dialog('open');
					}								
				});
			}
		}

		/**
		 *  提交表单方法
		 */
		$('#confirmAdd').click(function(){						
			if($('#newUserForm').form('validate')){
				$.ajax({
					type: 'post' ,
					url: actionMethod+'!'+actionMethod+'.action',
					cache:false ,
					data:$('#newUserForm').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='A000000'){
							$.messager.alert('提示消息',result.message,'info',function(){
								$('#newUserDialog').dialog('close');
								$('#t_user').datagrid('reload');
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
		$('#saveRolesBtn').click(function(){
		  	var userId = $('#roleDlg').find('#userId').html();
		  	var roleString = "";		  	
		  	$('.role').each(function(){				  		
		  		if($(this).attr('checked')== "checked"){	 	  						
	 	  			//roleString += $(this).parent().next().children().html();
	 	  			roleString += $(this).val();
	 	  			roleString +=",";
  				}
		  	});
		  	
		  	var params = {
	 	  		"userId" : userId,
	 	  		"business" : $("#businessId").val(),
	 	  		"roleString" : roleString
	 	  	};
		  	
			$.ajax({
		 		type:'post',
		 		url:'saveUserRoles!saveUserRoles.action',
		 		cache:false,
		 		data:params,
		 		dataType:'json',
		 		success:function(result){
 					//1 关闭窗口
					$('#roleDlg').dialog('close');
					//2刷新datagrid 
					$('#t_user').datagrid('reload');
					//3 提示信息
					LG.showMsg("提示信息",result.ret.retCode,result.ret.message,true);
		 		}
		 	});
		});
		
		$('#cancelRolesBtn').click(function(){
			$('#roleDlg').dialog('close');
		});
		
		/**
		 * 关闭窗口方法
		 */
		$('#cancelAdd').click(function(){
			$('#newUserDialog').dialog('close');
		});
		
			
		$('#searchbtn').click(function(){
			$('#t_user').datagrid('load' ,serializeForm($('#mysearch')));
		});
		
		
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_user').datagrid('load' ,{});
		});
		
		/*机构弹窗按钮*/			
		$('#searchbtn1').click(function(){
			$('#brch_list').datagrid('load' ,serializeForm($('#search_panel')));
		});			
			
		$('#clearbtn1').click(function(){
			$('#search_panel').form('clear');
			$('#brch_list').datagrid('load',{});
		});
		
		$.extend($.fn.validatebox.defaults.rules, {
			//密码验证(只能包括 _ 数字 字母) 
		    password: {//param的值为[]中值
		        validator: function (value, param) {
		            if (value.length < param[0] || value.length > param[1]) {
		                $.fn.validatebox.defaults.rules.password.message = '密码长度必须在' + param[0] + '至' + param[1] + '范围';
		                return false;
		            } else {
		                if (!/^[A-Za-z0-9]+$/.test(value)) {
		                    $.fn.validatebox.defaults.rules.password.message = '密码只能包含数字和字母.';
		                    return false;
		                } else {
		                    return true;
		                }
		            }
		        }, message: ''
		    }
		});
	});

	//js方法：序列化表单 			
	function serializeForm(form){
		var obj = {};
		$.each(form.serializeArray(),function(index){
			if(obj[this['name']]){
				obj[this['name']] = obj[this['name']] + ','+this['value'];
				alert(obj[this['name']]+":"+this['value']);
			} else {
				obj[this['name']] =this['value'];
			}
		});
		return obj;
	}
	
	function f_select_branch(){
		
		/*弹窗列表查询*/
		$('#brch_list').datagrid({
			title:'机构列表' ,
			fit:true ,
			height:450 ,
			url:'queryDepartmentList!queryDepartmentList.action' ,
			fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			frozenColumns:[[
				{
					field:'ck' ,
					width:50 ,
					checkbox: true
				}
			]], 
			onLoadSuccess: function (data){				
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},		
			columns:[[
				{
					field:'departmentId' ,
					title:'机构编号' ,
					width:100 ,
					align:'center' 
				},{
					field:'departmentName' ,
					title:'机构名称' ,
					width:150 ,
					align:'center'
				},{
					field:'departmentType' ,
					title:'业务' ,
					width:100,
					sortable : true,
					align:'center' ,
					formatter:function(value , record , index){
						if(value == 0){
							return '<span style=color:black; >超级管理员</span>' ;
						}else if(value == 1){
							return '<span style=color:green; >深度开发</span>' ;
						}else if(value == 2){
							return '<span style=color:blue; >私银销售</span>' ;
						}
					}
				},{
					field:'type' ,
					title:'机构类型' ,
					width:100 ,
					align:'center' ,
					formatter:function(value , record , index){
						if(value == 0){
							return '<span style=color:blue; >一级分行</span>';
						} else if( value == 1){
							return '<span style=color:blue;>同城支行</span>'; 
						} else if( value == 2){
							return '<span style=color:blue;>部门</span>'; 
						} else if( value == 3){
							return '<span style=color:green;>异地分行</span>'; 
						} else if( value == 4){
							return '<span style=color:green;>异地支行</span>'; 
						} else if( value == 5){
							return '<span style=color:green;>异地部门</span>'; 
						}
						
					}
				}
			]] ,
			
			pagination: true , 
			pageSize: 10 ,
			pageList:[10,20,50]			
		});
		
		$('#departmentDlg1').dialog({   
		    title: '机构选择',   
		    width: 680,   
		    height: 480,   
		    closed: false, 
		    modal: true,
		    buttons:[{
				text:'确认',
				handler:function () {
					var grid =$('#brch_list').datagrid('getSelections');
					if(grid.length != 1){
						LG.showWarn("提示信息","只能选择一行记录!");
					} else {						
						$('#departmentName').val(grid[0].departmentName);
						$('#departmentId').val(grid[0].departmentId);
						$('#departmentDlg1').dialog('close');
					}
		    		
				}
			},{
				text:'关闭',
				handler:function () {$('#departmentDlg1').dialog('close');}
			}]
		});
    } 

	</script>
 </head>
  
 <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="north" style="width: 100%;height:65%" > 
		<br/>
		<form id="mysearch" method="post">  
			&nbsp;用户编号:&nbsp;<input name="userId" type="text" size="10" class="input-style"/>
			&nbsp;用户名:&nbsp;<input name="username" type="text" size="15" class="input-style"/>
			&nbsp;机构编号:&nbsp;<input name="departmentId" type="text" size="10" class="input-style"/>
			&nbsp;机构名:&nbsp;<input name="departmentName" type="text" size="15" class="input-style"/>
			&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
			&nbsp;<a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a>
		</form>
		</div>
		<div region="center" style="width: 100%;height:100%">
			<table id="t_user" style="width: 100%;height:100%"></table>
		</div>
	</div>
  			
	<div id="roleDlg" title="角色分配" modal=true draggable=true class="easyui-dialog" closed=true style="width:450px;">
		<form id="rolefrom">
			<font size="2px">&nbsp;用户<label id="userId"></label>角色如下:</font>
			<input name="businessId" type="hidden" id="businessId"/>
			<br/>			
			<table id="roletable">				
			</table>
			<div id="button" align="center">
				<table>
					<tr align="center">
						<td colspan="2">
							<a href="javascript:void(0)" id="saveRolesBtn" class="easyui-linkbutton">保存</a>
							<a href="javascript:void(0)" id="cancelRolesBtn" class="easyui-linkbutton">关闭</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
  			
	<div id="newUserDialog" title="新增用户" modal=true  draggable=true class="easyui-dialog" closed=true style="width:380px;background-color:#E4F5EF;">
		<form id="newUserForm" action="" method="post">
		<br/>		
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td width="30%" align="right">用户编号&nbsp;<font color="red">*</font></td><%--validType="midLength[2,5]" invalidMessage="用户编号必须在2到5个字符之间!"--%>
				<td>
					<input type="text" id="userIdSearch" name="userId" class="easyui-validatebox" required=true  missingMessage="用户编号必填!" style="height:22px;border:1px solid #A4BED4;" value="" />
				</td>
			</tr>
			<tr>
				<td align="right">用户名&nbsp;<font color="red">*</font></td>
				<td><input name="username" class="easyui-validatebox" required=true missingMessage="用户名必填!" invalidMessage="用户编号必须在2到5个字符之间!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="right">地址&nbsp;</td>
				<td><input type="text" name="address" class="easyui-validatebox" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="right">联系方式&nbsp;<font color="red">*</font></td>
				<td><input type="text" name="contact" class="easyui-validatebox" required=true missingMessage="联系方式必填!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>	    						
				<td align="right">性别&nbsp;<font color="red">*</font></td>
				<td>
					<SELECT id="sex" name="sex" class="easyui-combobox" required=true missingMessage="性别必选!" panelHeight="auto" editable="false">
						<option value="男">男</option>
						<option value="女">女</option>
					</SELECT>
				</td>
			</tr>
			<tr>
				<td align="right">密码&nbsp;<font color="red">*</font></td>
				<td><input type="password" name="password" class="easyui-validatebox" required=true validType="password[6,8]" missingMessage="请输入6-8位密码,只可包含数字、字母!" invalidMessage="密码为6-8位,只能包含数字和字母!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="right">邮箱&nbsp;</td>
				<td><input id="mail" type="text" name="mail" class="easyui-validatebox" invalidMessage="邮箱格式不正确" validtype="email" style="height:22px;border:1px solid #A4BED4;" value=""/></td>
			</tr>
			<tr>
				<td align="right">岗位&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="rank" id="rank" style="background-color: #E1E6E9;border:1px solid #A4BED4;width:142px" class="easyui-combobox" required=true missingMessage="必选" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectRank,editable:false" />
	       		</td>
	       		<td></td>
			</tr>
			<tr>
				<td align="right">业务&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="business" id="business" style="background-color: #E1E6E9;border:1px solid #A4BED4;width:142px" class="easyui-combobox" required=true missingMessage="必选" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectMinBusiness,editable:false" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">管辖下属机构&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="enabled" id="enabled" style="background-color: #E1E6E9;border:1px solid #A4BED4;width:142px" class="easyui-combobox" required=true missingMessage="必选" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectEnabled,editable:false" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">机构&nbsp;<font color="red">*</font></td>
	         	<td align="left">
	         		<input type="text" id="departmentName" name="departmentName" readonly="readonly" class="input-style" style="background-color: #E1E6E9;border:1px solid #A4BED4"/>
	         		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="f_select_branch();" ><font size="1">选择</font></a>
					<input type="hidden" id="departmentId" name="departmentId" />
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
 			
	<div id="departmentDlg1" class="easyui-dialog" title="My Window" style="width:600px;height:400px"  
	        data-options="iconCls:'icon-save',modal:true,closed:true">
		<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
	
			<div region="north" style="width: 100%;height:65%" >
				<br>
				<form id="search_panel" method="post">  
						&nbsp;机构编号:&nbsp;<input name="departmentId" size="10" class="input-style" />
						&nbsp;机构名称:&nbsp;<input name="departmentName" size="15" class="input-style" />
						&nbsp;<a id="searchbtn1" class="easyui-linkbutton">查询</a>
						&nbsp;<a id="clearbtn1" class="easyui-linkbutton">清空</a>
				</form>
			</div>				
					
			<div region="center" style="width: 100%;height:100%">
				<table id="brch_list" style="width: 100%;height:100%"></table>
			</div>
		</div>
	</div>
  </body>
</html>
