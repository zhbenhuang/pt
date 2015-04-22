<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Integer business = (Integer)session.getAttribute("BUSINESS");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>机构管理</title>    
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
	$(function(){
		var actionMethod="saveDepartment";		//判断新增和修改方法 
		var business = <%=business%>;
		/**
		 *	初始化数据表格  
		 */
		$('#t_branch').datagrid({
			title:'机构列表' ,
			fit:true ,
			height:450 ,
			url:'queryDepartmentList!queryDepartmentList.action' ,
			fitColumns:true ,  
			striped: true ,					//隔行变色特性 
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
				{field:'departmentId' ,title:'机构编号' ,width:100 ,align:'center'},
				{field:'departmentName' ,title:'机构名称' ,width:120 ,align:'center'},
				{field:'anoDepartmentId' ,title:'其他编号' ,width:100 ,align:'center'},
				{field:'anoDepartmentName' ,title:'其他名称' ,width:120 ,align:'center'},
				{field:'type' ,title:'机构类型' ,width:100 ,align:'center' ,
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
				},
				{field:'remark' ,title:'备注' ,width:150,align:'center'},
				{field:'parentId' ,title:'上级机构编号' ,width:100,align:'center'},
				{field:'parentDepartmentName' ,title:'上级机构' ,width:100,align:'center'}
			]] ,
			pagination: true , 
			pageSize: 10 ,
			pageList:[10,20,50] ,
			toolbar:[
				{
					text:'新增机构' ,
					iconCls:'icon-my-add' , 
					handler:function(){
						actionMethod = 'saveDepartment';
						$('#departmentId').removeAttr("readonly");
						$('#newBrchDialog').dialog({
							title:'新增机构' 
						});
						$('#newBrchForm').get(0).reset();
						$('#newBrchDialog').dialog('open');
					}					
				},{
					text:'修改机构' ,
					iconCls:'icon-my-edit' , 
					handler:function(){
						actionMethod = 'modifyDepartment';
						var grid =$('#t_branch').datagrid('getSelections');
						if(grid.length != 1){
							LG.showWarn("提示信息","只能选择一行记录进行修改!");
						} else {								
							$('#newBrchDialog').dialog({title:'修改机构'});
							$('#departmentId').attr("readonly","readonly");
							$('#newBrchDialog').dialog('open'); //打开窗口
							$('#newBrchForm').get(0).reset();   //清空表单数据 
							$('#newBrchForm').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
								departmentId:grid[0].departmentId ,
								departmentName:grid[0].departmentName ,
								anoDepartmentId:grid[0].anoDepartmentId,
								anoDepartmentName:grid[0].anoDepartmentName ,
								type:grid[0].type,
								remark:grid[0].remark,
								parentId:grid[0].parentId,
								parentName:grid[0].parentDepartmentName
							});
						}
							
					}
				},{
					text:'删除机构' ,
					iconCls:'icon-my-delete' , 
					handler:function(){
						var grid =$('#t_branch').datagrid('getSelections');
						if(grid.length <=0){
							LG.showWarn("提示信息","请选择记录进行删除!");
						} else {			
							$.messager.confirm('提示信息' , '确认删除?' , function(r){
								if(r){
									var ids = "";
									for(var i =0 ;i<grid.length;i++){
										ids += "'"+ grid[i].departmentId+ "',";
									}
									if(i>0){
										ids = ids.substring(0 , ids.length-1);
									}
									$.post('deleteDepartment!deleteDepartment.action' , 
										{ids:ids},
										function(result){											
											$.messager.alert('提示信息',result.message,'info',function(){
												if(result.retCode=="A000000"){													
													//1 刷新数据表格 
													$('#t_branch').datagrid('reload');
													//2 清空idField   
													$('#t_branch').datagrid('unselectAll');	
												}else{
													LG.showError("提示信息","删除失败!");
												}
											});
										},'json'
									);
								}
							});
						}
					}								
				}
			]
		});
			
		/**
		 *  提交表单方法
		 */
		$('#confirmAdd').click(function(){
			if($('#newBrchForm').form('validate')){				
				$.ajax({
					type: 'post' ,
					url: actionMethod+'!'+actionMethod+'.action',
					cache:false ,
					data:$('#newBrchForm').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='A000000'){
							$.messager.alert('提示消息',result.message,'info',function(){
								$('#newBrchDialog').dialog('close');
								$('#t_branch').datagrid('reload');
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
			$('#newBrchDialog').dialog('close');
		});
			
				
		$('#searchbtn').click(function(){
			$('#t_branch').datagrid('load' ,serializeForm($('#mysearch')));
		});
			
			
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_branch').datagrid('load' ,{});
			
		});
			
		$('#confirmbtn').click(function(){
			var num = 0;
			$('.department').each(function(){
				if($(this).attr('checked')== "checked"){
					num++;
				}
			});
			if(num>1){
				LG.showWarn("提示信息","只能选择一个机构!");
			}else{
				$('.department').each(function(){
					if($(this).attr('checked')== "checked"){
						var str = $(this).parent().next().children().html();
						$('#parentName').val(str);
						$('#departmentDlg').dialog('close');
					}
				});
			}
		});
		
		/*弹窗按钮*/			
		$('#searchbtn1').click(function(){
			$('#brch_list').datagrid('load' ,serializeForm($('#search_panel')));
		});			
			
		$('#clearbtn1').click(function(){
			$('#search_panel').form('clear');
			$('#brch_list').datagrid('load',{});
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
				{field:'ck' ,width:50 ,checkbox: true}
			]], 
			onLoadSuccess: function (data){				
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},	
			columns:[[
				{field:'departmentId' ,title:'机构编号' ,width:100 ,align:'center'},
				{field:'departmentName' ,title:'机构名称' ,width:150 ,align:'center'},
				{field:'type' ,title:'机构类型' ,width:100 ,align:'center' ,
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
		    height: 500,   
		    closed: false, 
		    modal: true,
		    buttons:[{
				text:'确认',
				handler:function () {
					var grid =$('#brch_list').datagrid('getSelections');
					if(grid.length != 1){
						LG.showWarn("提示信息","只能选择一行记录!");
					} else {						
						$('#parentName').val(grid[0].departmentName);
						$('#parentId').val(grid[0].departmentId);
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
			<br>
			<form id="mysearch" method="post">  
					&nbsp;机构编号:&nbsp;<input name="departmentId" type="text" size="10" class="input-style" />
					&nbsp;机构名称:&nbsp;<input name="departmentName" type="text" size="15" class="input-style" />		
					&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
					&nbsp;<a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a>
			</form>
		</div>				
				
		<div region="center" style="width: 100%;height:100%">
			<table id="t_branch" style="width: 100%;height:100%"></table>
		</div>
	</div>
	
	<div id="newBrchDialog" title="新增机构" modal=true  draggable=true class="easyui-dialog" closed=true style="width:300px;background-color:#E4F5EF;">
   		<form id="newBrchForm" action="" method="post">
   		<br/>
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td>机构编号&nbsp;<font color="red">*</font></td>
				<td><input type="text" name="departmentId" id="departmentId" class="easyui-validatebox" required=true  missingMessage="机构编号必填!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
				<td></td>
			</tr>
			<tr>
				<td>机构名称&nbsp;<font color="red">*</font></td>
				<td><input name="departmentName" id="departmentName" class="easyui-validatebox" required=true  missingMessage="机构名必填!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
				<td></td>
			</tr>
			<tr>
				<td>其他编号&nbsp;</td>
				<td><input type="text" name="anoDepartmentId" id="anoDepartmentId" class="input-style" /></td>
				<td></td>
			</tr>
			
			<tr>
				<td>其他名称&nbsp;</td>
				<td><input name="anoDepartmentName" id="anoDepartmentName" class="input-style" /></td>
				<td></td>
			</tr>
			<tr>
				<td>机构类型&nbsp;<font color="red">*</font></td>
				<td>
					<input name="type" id="type" style="width:142px" class="easyui-combobox" required=true missingMessage="机构类型必选!" panelHeight="auto"
					data-options="valueField:'id',textField:'text',data:selectOrgType" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td>备注&nbsp;</td>
				<td><input id="remark" type="text" name="remark" class="input-style" /></td>
				<td></td>
			</tr>
			<tr>
				<td>上级机构&nbsp;<font color="red">*</font></td>
				<td>
					<input type="text" id="parentName" name="parentName" disabled='true' class="easyui-validatebox" required=true  missingMessage="上级机构必填!" />
					<input type="hidden" id="parentId" name="parentId" />
				</td>
				<td align="left">
				<input type="button" value="选" id="selectBtn" onclick="f_select_branch();" class="select-button" />
				</td>
			</tr>

			<tr align="center">
				<td colspan="3">
					<a href="javascript:void(0)" id="confirmAdd" class="easyui-linkbutton">保存</a>
					<a href="javascript:void(0)" id="cancelAdd" class="easyui-linkbutton">关闭</a>
				</td>
			</tr>   					 					    					    					    					    					    					    					    					
		</table>
   		</form> 			
	</div>
	
 	<div id="departmentDlg1" class="easyui-dialog" title="My Window" style="width:600px;height:420px"  
	        data-options="iconCls:'icon-save',modal:true,closed:true">
		<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
	
			<div region="north" style="width: 100%;height:65%" >
				<br>
				<form id="search_panel" method="post">  
						&nbsp;机构编号:&nbsp;<input name="departmentId" size="10" class="input-style" />
						&nbsp;机构名称:&nbsp;<input name="departmentName" size="15" class="input-style" />
						&nbsp;<a href="javascript:void(0)" id="searchbtn1" class="easyui-linkbutton">查询</a>
						&nbsp;<a href="javascript:void(0)" id="clearbtn1" class="easyui-linkbutton">清空</a>
				</form>
			</div>				
					
			<div region="center" style="width: 100%;height:100%">
				<table id="brch_list" style="width: 100%;height:100%"></table>
			</div>
		</div>
	</div>
	
  </body>
</html>
