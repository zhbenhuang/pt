<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.cmbc.sa.bean.Users"%>
<%@ page language="java" import="rsos.framework.constant.GlobalConstants"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>积分导入</title>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
  			$('#myformImportFile').get(0).reset();
			
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
					if($("#importTerm").val()==''){
						alert('请选择积分分期');
						return ;
					}
					$('#uploadifyImportFile').uploadifySettings('scriptData', {'importTerm':$("#importTerm").val()});
				},
				onComplete		 : function(event, ID, fileObj, response, data){
				}
			});
			
			$('#btn10').click(function(){
				window.location.href = "history.go(-1);";
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
  		
  	</script>
 </head>
  <body style="overflow:hidden">
 	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
  		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:30%;overflow:hidden">
  		</div>
  		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
   			<br/>
	   		<form id="myformImportFile" action="" method="post">
	   			<div class="title" align="center" ><table><tr><td><h4>上传文件</h4></td></tr></table></div>
	   			<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
   					<tr>
   						<td align="right">积分期数&nbsp;<font color="red">*</font></td>
   						<td align="left"><input name="importTerm" id="importTerm" class="easyui-databox" readonly="readonly" value="" />(YYYYMM)</td>
   					</tr>
   					<tr>
						<td align="right">导入文件&nbsp;<font color="red">*</font></td>
						<td align="left" colspan="1">
							<div id="fileQueueImportFile"></div>
							<input type="file" name="uploadifyImportFile" id="uploadifyImportFile" />
							<p>
							<a id="confirmImportFile" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyImportFile').uploadifyUpload()">开始导入</a>
							<a id="cancelImportFile" class="easyui-linkbutton" href="javascript:jQuery('#uploadifyImportFile').uploadifyClearQueue()">取消导入</a>
							</p>
						</td>
					</tr>
   					<tr><td align="right"><img id="templateFile" src="img/excel.png" height="20" width="30" /></td>
						<td align="left">
						    <a href="excelModule/uploadValue.xls">下载导入模板</a>
						</td>
					</tr>
	   			</table>
	   		</form>
	   	</div>
   	</div>
  </body>
</html>