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
    <title>标的管理</title>    
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
	var actionMethod="saveServInfo";		
	var currentSerId="";
	$(function(){
		var business = <%=business%>;
		$('#newServInfoDialog').dialog({
			onClose:function()
			{
				$('#posbusiness').html("");
			} 
		});
		
		/**
		 *	初始化数据表格  
		 */
		$('#t_servInfo').datagrid({
			title:'标的列表' ,
			fit:true ,
			height:450 ,
			url:'queryServInfoList!queryServInfoList.action' ,
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
				{field:'serPic' ,title:'图片',width:50,align:'center',formatter:function(value,record,index){return '<img src="downloadServInfoFile!getFile.action?serId='+record.serId+'&fileType=0" height="20" width="30" />';}},
				{field:'serId' ,title:'活动ID' ,width:50 ,align:'center'},
				{field:'serName' ,title:'活动名称' ,width:50 ,align:'center'},
				{field:'serDes' ,title:'活动描述' ,width:100 ,align:'center'},
				{
					field:'bigType' ,
					title:'活动大类' ,
					width:50 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 1){
							return '<span style=color:black; >尊享服务</span>' ;
						}else if(value == 2){
							return '<span style=color:black; >尊享活动</span>' ;
						}else if(value == 3){
							return '<span style=color:black; >尊享礼品</span>' ;
						}
					}
				},
				{
					field:'smlType' ,
					title:'活动小类' ,
					width:50 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 0){
							return '<span style=color:black; >请选择</span>' ;
						}else if(value == 1){
							return '<span style=color:black; >易登机</span>' ;
						}else if(value == 2){
							return '<span style=color:black; >礼宾车</span>' ;
						}else if(value == 3){
							return '<span style=color:black; >和谐体检</span>' ;
						}else if(value == 4){
							return '<span style=color:black; >和谐挂号</span>' ;
						}
					}
				},
				{field:'serValue' ,title:'价格' ,width:50 ,align:'center'},
				{field:'serAmount' ,title:'剩余数量' ,width:30 ,align:'center'},
				{field:'begTime' ,title:'生效时间' ,width:120 ,align:'center'},
				{field:'endTime' ,title:'失效时间' ,width:120 ,align:'center'}

			]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[10,20,50] ,
			toolbar:[
			       	 {
			       		text:'新增标的' ,
			       		iconCls:'icon-my-add' , 
						handler:'addServInfo'
			       	 },							
			         {
			       		 text:'修改标的' ,
			       		 iconCls:'icon-my-edit' ,
			       		 handler:'modifyServInfo'
			         },
			         {
			        	 text:'删除标的' ,
			       		 iconCls:'icon-my-delete' ,
			       		 handler:'deleteServInfo' 
			         }
			]
		});
		
		/**
		 *  提交表单方法
		 */
		$('#confirmAdd').click(function(){
			if($('#newServInfoForm').form('validate')){				
				$.ajax({
					type: 'post' ,
					url: actionMethod+'!'+actionMethod+'.action',
					cache:false ,
					data:$('#newServInfoForm').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='A000000'){
							$.messager.alert('提示消息',result.message,'info',function(){
								$('#newServInfoDialog').dialog('close');
								$('#t_servInfo').datagrid('reload');
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
			$('#newServInfoDialog').dialog('close');
		});
					
						
		$('#searchbtn').click(function(){
			$('#t_servInfo').datagrid('load' ,serializeForm($('#mysearch')));
		});
				
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_servInfo').datagrid('load' ,{});
		});
		
		$("#uploadifyPic").uploadify({
			'uploader'       : 'js/uploadify.swf',
			'script'         : 'uploadServInfoFile.action?',
			'scriptData'	 : {'serId':-1},
			'cancelImg'      : 'img/cancel.png',
			'folder'         : 'uploads',
			'queueID'        : 'fileQueuePic',
			'fileDataName'   : 'fileName',  
			'auto'           : false,
			'multi'          : false,
			'simUploadLimit' : 1,
			'buttonText'	 : '浏览图片',
			'fileExt'        : '*.jpg;*.gif;*.png;*.bmp;',
			'multi'          : true,
			'queueSizeLimit' : 1,
			'sizeLimit'      : 1024000,
			onSelect		 : function(e, queueId, fileObj) {
				$('#uploadifyPic').uploadifySettings('scriptData', {'serId':$("#serIdNew").val(), 'fileType':0});
			},
			onComplete		 : function(event, ID, fileObj, response, data){
				$("#serPic").val(fileObj.name);
				$("#imgSerPic").attr("src","downloadServInfoFile!getFile.action?serId="+$("#serIdNew").val()+"&fileType=0&fileName="+fileObj.name);
			}
		});
		$("#uploadify").uploadify({
			'uploader'       : 'js/uploadify.swf',
			'script'         : 'uploadServInfoFile.action?',
			'scriptData'	 : {'serId':-1},
			'cancelImg'      : 'img/cancel.png',
			'folder'         : 'uploads',
			'queueID'        : 'fileQueueFile1',
			'fileDataName'   : 'fileName',  
			'auto'           : false,
			'multi'          : false,
			'simUploadLimit' : 1,
			'buttonText'	 : '浏览文件',
			'fileExt'        : '*.jpg;*.gif;*.png;*.bmp;*.doc;*.pdf;*.rar;*.xls',
			'multi'          : true,
			'queueSizeLimit' : 1,
			'sizeLimit'      : 10240000,
			onSelect		 : function(e, queueId, fileObj) {
				$('#uploadify').uploadifySettings('scriptData', {'serId':$("#serIdNew").val(), 'fileType':1});
			},
			onComplete		 : function(event, ID, fileObj, response, data){
				$("#fileUrl1").val(fileObj.name);
				$("#showFile1").empty();
				$("#showFile1").append( "<a href=downloadServInfoFile!getFile.action?serId="+$("#serIdNew").val()+"&fileType=1&fileName="+fileObj.name+">"+fileObj.name+"</a><br/>");
				
			}
		});
		$("#uploadifyFile2").uploadify({
			'uploader'       : 'js/uploadify.swf',
			'script'         : 'uploadServInfoFile.action?',
			'scriptData'	 : {'serId':-1},
			'cancelImg'      : 'img/cancel.png',
			'folder'         : 'uploads',
			'queueID'        : 'fileQueueFile2',
			'fileDataName'   : 'fileName',  
			'auto'           : false,
			'multi'          : false,
			'simUploadLimit' : 1,
			'buttonText'	 : '浏览文件',
			'fileExt'        : '*.jpg;*.gif;*.png;*.bmp;*.doc;*.pdf;*.rar;*.xls',
			'multi'          : true,
			'queueSizeLimit' : 1,
			'sizeLimit'      : 10240000,
			onSelect		 : function(e, queueId, fileObj) {
				$('#uploadifyFile2').uploadifySettings('scriptData', {'serId':$("#serIdNew").val(), 'fileType':2});
			},
			onComplete		 : function(event, ID, fileObj, response, data){
				$("#fileUrl2").val(fileObj.name);
				$("#showFile2").empty();
				$("#showFile2").append( "<a href=downloadServInfoFile!getFile.action?serId="+$("#serIdNew").val()+"&fileType=2&fileName="+fileObj.name+">"+fileObj.name+"</a><br/>");
			}
		});
		
		$.extend($.fn.validatebox.defaults.rules, {
			idnum: { 
        		validator: function (value) {
        			var reg = /^[1-9]d*$/;
            		return reg.test(value);
        		},
        		message: '必须是整数.'
    		}
		});		
		
	});
	
	function addServInfo(){
		actionMethod = 'saveServInfo';
		$('#newServInfoDialog').dialog({
			title:'新增标的' 
		});
		$('#newServInfoForm').get(0).reset();
		$('#newServInfoDialog').dialog('open');
		
		$('#userIdSearch').removeAttr("readonly");
		$('#newServInfoForm').form('load',{
			bigType:"1",
			bigTypeHidden:"1",
			smlType:"1",
			smlTypeHidden:"1"
		});
		$("#showFile1").empty();
		$("#showFile2").empty();
	}
	
	function modifyServInfo(){
		actionMethod = 'modifyServInfo';
		var grid =$('#t_servInfo').datagrid('getSelections');
		if(grid.length != 1){
			LG.showWarn("提示信息","请选择一条记录修改标的!");
		} else {
			$('#newServInfoDialog').dialog({
				title:'修改标的'
			});
			$('#serIdNew').attr("readonly","readonly");
			//$('#bigType').combobox('disable');	//修改业务时,保存会出错,所以这里不允许修改已有用户的业务
			$('#newServInfoDialog').dialog('open'); //打开窗口
			$('#newServInfoForm').get(0).reset();   //清空表单数据 
			$('#newServInfoForm').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
				serPic:grid[0].serPic ,
				serId:grid[0].serId ,
				serName:grid[0].serName,
				serDes:grid[0].serDes,
				bigType:grid[0].bigType,
				bigTypeHidden:grid[0].bigType,
				smlType:grid[0].smlType,
				smlTypeHidden:grid[0].smlType,
				serValue:grid[0].serValue,
				serAmount:grid[0].serAmount,
				begTime:grid[0].begTime,
				endTime:grid[0].endTime,
				fileUrl1:grid[0].fileUrl1,
				fileUrl2:grid[0].fileUrl2
				
			});
			$("#imgSerPic").attr("src","downloadServInfoFile!getFile.action?serId="+grid[0].serId+"&fileType=0&fileName="+grid[0].serPic);
			$("#showFile1").empty();
			$("#showFile1").append( "<a href=downloadServInfoFile!getFile.action?serId="+grid[0].serId+"&fileType=1&fileName="+grid[0].fileUrl1+">"+grid[0].fileUrl1+"</a><br/>");
			$("#showFile2").empty();
			$("#showFile2").append( "<a href=downloadServInfoFile!getFile.action?serId="+grid[0].serId+"&fileType=2&fileName="+grid[0].fileUrl2+">"+grid[0].fileUrl2+"</a><br/>");
		}
	}
	
	function deleteServInfo(){
		var grid =$('#t_servInfo').datagrid('getSelections');
		if(grid.length <=0){
			LG.showWarn("提示信息","请选择记录进行删除!");
		} else {
			$.messager.confirm('提示信息' , '确认删除?' , function(r){
				if(r){
					var ids = '';
					for(var i =0 ;i<grid.length;i++){
						ids += grid[i].serId + ',' ;
						//ids += "'"+grid[i].serId+grid[i].bigType + "',";
					}
					if(i>0){
						ids = ids.substring(0 , ids.length-1);
					}
					$.post('deleteServInfo!deleteServInfo.action' , 
						{ids:ids} , 
						function(result){
							$.messager.alert('提示信息' , result.message,'info',function(){
								if(result.retCode=="A000000"){													
									//1 刷新数据表格 
									$('#t_servInfo').datagrid('reload');
									//2 清空idField   
									$('#t_servInfo').datagrid('unselectAll');	
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
		<div region="north" style="width: 100%;height:65%" >
		<br/>
		<form id="mysearch" method="post">  
			&nbsp;标的ID:&nbsp;<input name="serId" type="text" size="10" class="easyui-validatebox" validType="idnum"/>
			&nbsp;标的名称:&nbsp;<input name="serName" type="text" size="15" class="input-style"/>
			&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
			&nbsp;<a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a>
			</form>
		</div>
		<div region="center" style="width: 100%;height:100%">
			<table id="t_servInfo" style="width: 100%;height:100%"></table>
		</div>
	</div>
	
	
  	
  	<div id="newServInfoDialog" title="新增标的" class="easyui-dialog" style="width:600px;height:700px;background-color:#E4F5EF;"  
        data-options="iconCls:'icon-save',modal:true,draggable:true,closed:true">
		<form id="newServInfoForm" action="" method="post"  align="center">
		<input id="serIdNew" name="serId" type="hidden" value=""  required=true/>
		<br/>
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td align="right">标的名称:&nbsp;<font color="red">*</font></td>
				<td align="left" colspan="3"><input name="serName" type="text" class="easyui-validatebox" required=true missingMessage="标的名称不可为空!" style="width:90%;height:22px;border:1px solid #A4BED4;" value="" /></td>
				
			</tr>
			<tr>
				<td align="right">标的大类&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="bigTypeHidden" id="bigTypeHidden" type="hidden"/>
					<input name="bigType" id="bigType" style="width:138px" class="easyui-combobox" required=true  missingMessage="标的大类必选!" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectBigType" />
				</td>
				<td align="right">标的小类&nbsp;<font color="red">*</font></td>
				<td align="left">
					<input name="smlTypeHidden" id="smlTypeHidden" type="hidden"/>
					<input name="smlType" id="smlType" style="width:138px" class="easyui-combobox" required=true  missingMessage="标的大类必选!" panelHeight="auto"
						data-options="valueField:'id',textField:'text',data:selectSmlType" />
				</td>
			</tr>
			<tr>
				<td align="right">价格:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="serValue" type="text" class="easyui-validatebox" required=true missingMessage="价格不可为空!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
				<td align="right">数量:&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="serAmount" type="text" class="easyui-validatebox" required=true missingMessage="数量不可为空!" style="height:22px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="center">生效时间&nbsp;<font color="red">*</font></td>
				<td><input type="text" id="begTime" name="begTime" class="easyui-datebox" required=true missingMessage="生效时间不能为空" style="height:22px;border:1px solid #A4BED4;" value=""/></td>
				<td align="center">失效时间&nbsp;<font color="red">*</font></td>
				<td><input type="text" id="endTime" name="endTime" class="easyui-datebox" required=true missingMessage="失效时间不能为空" style="height:22px;border:1px solid #A4BED4;" value=""/></td>
			</tr>
			<tr>
				<td align="right">标的描述:&nbsp;<font color="red">*</font></td>
				<td align="left" colspan="3"><input name="serDes" type="text" class="easyui-validatebox" required=true missingMessage="标的描述不可为空!" style="height:42px;width:400px;border:1px solid #A4BED4;" value="" /></td>
			</tr>
			<tr>
				<td align="right">标的图片:&nbsp;<font color="red">*</font></td>
				<td align="left" colspan="3">
					<input id="serPic" name="serPic" type="hidden" value=""  required=true/>
					<img id="imgSerPic" src="downloadServInfoFile!getSerPic.action?serId=&fileType=0" height="20" width="30" />
					<div id="fileQueuePic"></div>
					<input type="file" name="uploadifyPic" id="uploadifyPic" />
					<p>
					<a id="confirmUploadPic" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyPic').uploadifyUpload()">开始上传</a>
					<a id="cancelUploadPic" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyPic').uploadifyClearQueue()">取消上传</a>
					</p>
				</td>
			</tr>
			<tr>
				<td align="right">标的附件一</td>
				<td align="left" colspan="3">
					<input id="fileUrl1" name="fileUrl1" type="hidden" value=""  required=true/>
		
					<div id="showFile1"></div>
					<div id="fileQueueFile1" ></div>
					<input type="file" name="uploadify" id="uploadify" />
					<p>
					<a id="confirmUpload" class="easyui-linkbutton" href="javascript:jQuery('#uploadify').uploadifyUpload()">开始上传</a>
					<a id="cancelUpload" class="easyui-linkbutton" href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消上传</a>
					</p>	
				</td>
			</tr>
			<tr>
				<td align="right">标的附件二</td>
				<td align="left" colspan="3">
					<input id="fileUrl2" name="fileUrl2" type="hidden" value=""  required=true/>
					<div id="showFile2"></div>
					<div id="fileQueueFile2" ></div>
					<input type="file" name="uploadifyFile2" id="uploadifyFile2" />
					<p>
					<a id="confirmUploadFile2" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyFile2').uploadifyUpload()">开始上传</a>
					<a id="cancelUploadFile2" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyFile2').uploadifyClearQueue()">取消上传</a>
					</p>	
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
