<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>流程管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link href="css/uploadify.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>	
	<script type="text/javascript" src="js/swfobject.js"></script>
	<script type="text/javascript" src="js/jquery.uploadify.v2.0.1.js"></script>
	<script type="text/javascript">
	$(function(){		
		/**
		 *	初始化数据表格  
		 */
		$('#t_flow').datagrid({
			title:'流程列表' ,
			fit:true ,
			height:450 ,
			url:'flowManage!queryFlowList.action' ,
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
				{field:'processDefinitionId' ,title:'流程号' ,width:100 ,align:'center'},
				{field:'name' ,title:'流程名称' ,width:120 ,align:'center'},
				{field:'version' ,title:'版本号' ,width:100,align:'center'},
				{field:'deploymentId' ,title:'发布编号' ,width:100 ,align:'center'},
				{field:'processPngName' ,title:'流程图' ,hidden:true}
			]] ,
			pagination: true , 
			pageSize: 10 ,
			pageList:[10,20,50] ,
			toolbar:[
				{
					text:'发布新流程' ,
					iconCls:'icon-my-add' , 
					handler:function(){
						$('#newFlowDialog').dialog({title:'发布新流程'});
						$('#newFlowDialog').dialog('open');
					}					
				},{
					text:'删除流程定义' ,
					iconCls:'icon-my-delete' , 
					handler:deleteFlow
				},{
					text:'查看流程图',
					iconCls:'icon-my-photo',
					handler:viewProcessPng
				}
			]
		});
							
		$('#searchbtn').click(function(){
			$('#t_flow').datagrid('load' ,serializeForm($('#mysearch')));
		});
			
			
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_flow').datagrid('load' ,{});
		});
		
		$("#uploadify").uploadify({
			'uploader'       : 'js/uploadify.swf',
			'script'         : 'flowManage!deployFlow.action',
			'cancelImg'      : 'img/cancel.png',
			'folder'         : 'uploads',
			'queueID'        : 'fileQueue',
			'auto'           : false,
			'multi'          : false,
			'simUploadLimit' : 1,
			'buttonText'	 : 'BROWSE',
			onComplete		 : function(){$.messager.alert('提示消息','发布流程成功!','info');}
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
	function closeFlowDialog(){
		$('#newFlowDialog').dialog('close');
		$('#t_flow').datagrid('reload');
	}
	function deleteFlow(){
		var grid =$('#t_flow').datagrid('getSelections');
		if(grid.length <=0){
			LG.showWarn("提示信息","请选择记录进行删除!");
		} else {			
			$.messager.confirm('提示信息' , '确认删除?' , function(r){
				if(r){
					var ids = "";
					for(var i =0 ;i<grid.length;i++){
						ids += grid[i].deploymentId+",";
					}
					if(i>0){
						ids = ids.substring(0 , ids.length-1);
					}
					$.post('flowManage!deleteFlow.action' , 
						{ids:ids},
						function(result){											
							$.messager.confirm('提示信息' , result.message, function(r){
								if(r){													
									//1 刷新数据表格 
									$('#t_flow').datagrid('reload');
									//2 清空idField   
									$('#t_flow').datagrid('unselectAll');	
								}else{
									return;
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
	function viewProcessPng(){
		var grid =$('#t_flow').datagrid('getSelections');
		if(grid.length !=1){
			LG.showWarn("提示信息","请选择一个流程!");
		} else {
			var deploymentId = grid[0].deploymentId;
			var processName = grid[0].name;
			var processPngName = grid[0].processPngName;
			$('#viewProcessPng').dialog('open').dialog("refresh","flow/viewProcessPng.jsp?deploymentId="+deploymentId+"&processName="+processName+"&processPngName="+processPngName);
		}
	}
</script>
</head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >

		<div region="north" style="width: 100%;height:65%" >
			<br>
			<form id="mysearch" method="post">  
					&nbsp;流程名称:&nbsp;<input name="flowName" type="text" size="20" class="input-style" />
					&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
					&nbsp;<a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a>
			</form>
		</div>				
				
		<div region="center" style="width: 100%;height:100%">
			<table id="t_flow" style="width: 100%;height:100%"></table>
		</div>
	</div>
	
	<div id="newFlowDialog" title="发布流程定义" modal=true draggable=true class="easyui-dialog" closed=true style="width:400px;">
   		<font color="red">注：发布zip文件</font>
   		<div id="fileQueue"></div>
		<input type="file" name="uploadify" id="uploadify" />
		<p>
		<a id="confirmAdd" class="easyui-linkbutton" href="javascript:jQuery('#uploadify').uploadifyUpload()">开始上传</a>
		<a id="cancelAdd" class="easyui-linkbutton" href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消上传</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeFlowDialog()">关闭</a>
		</p>
	</div>
	<div id="viewProcessPng" title="流程图" class="easyui-dialog" style="width:720px;height:520px" data-options="iconCls:'icon-my-view',modal:true,draggable:true,closed:true,cache:false"></div>
  </body>
</html>
