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
    <title>积分查询</title>    
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
	var actionMethod="saveStruValue";		//判断新增和修改方法 
	$(function(){
		var business = <%=business%>;
		$('#newStruValueDialog').dialog({
			onClose:function()
			{
				$('#posbusiness').html("");
			} 
		});
		
		/**
		 *	初始化数据表格  
		 */
		$('#t_struValue').datagrid({
			title:'积分记录列表' ,
			fit:true ,
			height:450 ,
			url:'queryStruValueList!queryStruValueList.action' ,
			fitColumns:true ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'struId' ,title:'机构ID' ,width:100 ,align:'center'},
				{field:'struName' ,title:'机构名称' ,width:100 ,align:'center'},
				{field:'term' ,title:'积分期数' ,width:100 ,align:'center'},
				{field:'begValue' ,title:'初始积分' ,width:100 ,align:'center'},
				{field:'surpValue' ,title:'剩余积分' ,width:100 ,align:'center'},
				{field:'alterTime' ,title:'维护时间' ,width:100 ,align:'center'}

			]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[10,20,50] ,
			toolbar:[
				{
					text:'导入积分' ,
					iconCls:'icon-my-add' , 
					handler:'addImportFile'
				 }   	 
			]
		});
		

		$("#uploadifyImportFile").uploadify({
			'uploader'       : 'js/uploadify.swf',
			'script'         : 'uploadStruValueImportTempFile.action?',
			'scriptData'	 : {'importTerm':-1},
			'cancelImg'      : 'img/cancel.png',
			'folder'         : 'uploads',
			'queueID'        : 'fileQueueImportFile',
			'fileDataName'   : 'fileName',  
			'auto'           : false,
			'multi'          : false,
			'simUploadLimit' : 1,
			'buttonText'	 : '浏览文件',
			'fileExt'        : '*.xls',
			'multi'          : true,
			'queueSizeLimit' : 1,
			'sizeLimit'      : 10240000,
			onSelect		 : function(e, queueId, fileObj) {
				$('#uploadifyImportFile').uploadifySettings('scriptData', serializeForm($('#importFileForm')));
			},
			onComplete		 : function(event, ID, fileObj, response, data){
			}
		});
		
		/**
		 * 关闭窗口方法
		 */
		$('#cancelAdd').click(function(){
			$('#newStruValueDialog').dialog('close');
		});
					
		
		$('#searchbtn').click(function(){
			$('#t_struValue').datagrid('load' ,serializeForm($('#mysearch')));
		});
							
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_struValue').datagrid('load' ,{});
		});
		
		$('#term').datebox({
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var dstr = y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) ;
                $("#term").attr("value", dstr);
                return dstr;
            }
	    });
		$('#importTerm').datebox({
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var dstr = y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) ;
                $("#importTerm").attr("value", dstr);
                return dstr;
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
	
	function addImportFile(){
		actionMethod = 'importFile';
		$('#importFileDialog').dialog({
			title:'积分导入' 
		});
		$('#importFileForm').get(0).reset();
		$('#importFileDialog').dialog('open');
		
		$('#importFileForm').form('load',{
		});
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
			&nbsp;机构ID:&nbsp;<input name="struId" type="text" size="15" class="easyui-validatebox" validType="idnum"/>
			&nbsp;积分期数:&nbsp;<input name="term" id="term" class="easyui-databox" value="" />
			&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
			</form>
		</div>
		<div region="center" style="width: 100%;height:100%">
			<table id="t_struValue" style="width: 100%;height:100%"></table>
		</div>
	</div>
	
	<div id="importFileDialog" title="导入积分" class="easyui-dialog" style="width:500px;height:400px;background-color:#E4F5EF;"  
        data-options="iconCls:'icon-save',modal:true,draggable:true,closed:true">
		<form id="importFileForm" action="" method="post"  align="center">
		<br/>
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td align="right">积分期数&nbsp;<font color="red">*</font></td>
				<td align="left"><input name="importTerm" id="importTerm" class="easyui-databox" value="" /></td>
			</tr>
			<tr>
				<td align="right">导入文件</td>
				<td align="left" colspan="3">
					<div id="fileQueueImportFile"></div>
					<input type="file" name="uploadifyImportFile" id="uploadifyImportFile" />
					<p>
					<a id="confirmImportFile" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyImportFile').uploadifyUpload()">开始导入</a>
					<a id="cancelImportFile" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyImportFile').uploadifyClearQueue()">取消导入</a>
					</p>	
				</td>
			</tr>
		</table>
		</form> 	
		
	</div>
  </body>
</html>
