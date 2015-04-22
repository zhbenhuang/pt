<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String prefix = (String)request.getAttribute("prefix");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>合同标签打印</title>
    
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
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript" src="js/jquery-barcode/jquery-barcode-1.3.3.js"></script>
	<script type="text/javascript" src="js/jquery-dayin/jquery.jqprint-0.3.js"></script>
	<script type="text/javascript">
	$(function(){
			$('#t_tagInfo').datagrid({
					title:'合同标签列表' ,
					fit:true ,
					height:450 ,
					url:'getTagList!getlist.action' ,
					fitColumns:true ,  
					striped: true ,					
					nowrap: true ,
					loadMsg: '数据正在加载,请耐心的等待...' ,
					rownumbers:true ,
					onLoadSuccess: function (data){					
						LG.showMsg("查询结果提示",data.retCode,data.message,false);
					},
					onLoadError: function (xhr,data){
						showErrorMessage(xhr);
					},
					frozenColumns:[[	
						{field:'ck' ,width:50 ,checkbox: true}
					]],
					columns:[[
						{field:'tagId' ,title:'合同标签号' ,width:150 ,align:'center' ,hidden:true},
						{field:'prefix' ,title:'条码前缀' ,width:150 ,align:'center' },
						{field:'startCode' ,title:'起始编码' ,width:150 ,align:'center'},
						{field:'endCode' ,title:'终止编码' ,width:150 ,align:'center'},						
						{field:'printer' ,title:'操作人' ,width:150 ,align:'center'},
						{field:'printDate' ,title:'操作日期' ,width:150 ,align:'center'}
					]] ,
					pagination: true , 
					pageSize: 10 ,
					pageList:[10,20,50] ,
					toolbar:[
						{
							text:'录入条码' ,
							iconCls:'icon-my-add' , 
							handler:function() {									
								$('#mydialog').dialog({title:'录入条码'});																		
								$('#mydialog').dialog('open');
							}															
						},
						{
							text:'打印条码' ,
							iconCls:'icon-print' , 
							handler:function() {									
								var arr =$('#t_tagInfo').datagrid('getSelections');
								if(arr.length <= 0){
									LG.showWarn("提示消息","至少选择一行记录进行打印!");
								}else if(arr.length >= 2){
									LG.showWarn("提示消息","只能选择一行记录进行打印!");
								}else {
									var path = arr[0].path;
									var url = 'downloadTag.action?path='+path;
									window.location.href=url;
								}
							}															
						}
					]
			});
			
			
			/**
			 *  提交表单方法
			 */
			$('#saveTag').click(function(){
				var arr = new Array();
				if($('#myform').form('validate')){
					var startCode = $('#startCode').attr('value');
					var endCode = $('#endCode').attr('value');
					if(endCode<startCode){
						LG.showWarn("提示信息","终止编码应大于起始编码!");
					}else{
						$.ajax({
							type: 'post' ,
							url: 'saveTag!save.action',
						    cache:false ,
							data:$('#myform').serialize() ,
							dataType:'json' ,
							success:function(result){							
								$.messager.alert("提示消息",result.message,"info",function(){
									$('#t_tagInfo').datagrid('reload');
									$('#myform').form('clear');
								});								
							}
						});
					}
				} else {
					LG.showWarn("提示信息","请输入4位数字作为条码前缀!");
				}
			});
			
			/**
			 * 关闭窗口方法
			 */
			$('#close').click(function(){
				$('#mydialog').dialog('close');
				$('#myform').form('clear');
				$('#t_tagInfo').datagrid('reload');
			});
			
				
			$('#searchbtn').click(function(){
				$('#t_tagInfo').datagrid('load' ,serializeForm($('#mysearch')));
			});
			
			//扩展easyui表单的验证
			$.extend($.fn.validatebox.defaults.rules, {
				account: { 
	        		validator: function (value) {
	            		var reg = /^[0-9]{4}$/;
	            		return reg.test(value);
	        		},
	        		message: '条码为10位数字组成.'
	    		}
			});
			
			$('#searchbtn2').click(function(){	
				$('#startCode').val('');
				$('#endCode').val('');
				$('#sum').val('');
				var prefix = $('#prefix').attr('value');				
				if(prefix != ""){
					$.ajax({
						type:'post',
						url:'showTask!showTaskByPrefix.action',
						dataType:'json',
						data:{
							"prefix":prefix,
							startCode:$("input[name=startCode]").val()							
						},
						cache:false,						
						success:function(result){							
							var result = eval("("+result+")");
							$('#startCode').val(""+result.startCode+"");														
						}
					});
				}else{
					LG.showWarn("提示信息","请输入4位数字作为条码前缀!");
				}
			});
			
			
			$('#clearbtn').click(function(){
				$('#mysearch').form('clear');
				$('#t_tagInfo').datagrid('load' ,{});
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
	$(function(){
	    $('#printDate').datebox({
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
            }
	    });
	});
	
</script>
  </head>
  
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="north" title="合同标签查询" collapsed=false style="height:60%;overflow:hidden" >
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="25%">&nbsp;打印日期:&nbsp;<input name="printDate" id="printDate" class="easyui-datebox" value="" /></td>
						<td width="25%">&nbsp;打印人:&nbsp;<input name="printer"  value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">重置</a></td>
						<td></td>
					</tr>
				</table>	
			</form>				
		</div>
		<div region="center" >
			<table id="t_tagInfo"></table>
		</div>
	</div>
			
	<div id="mydialog" title="录入条码" modal=true  draggable=true class="easyui-dialog" closed=true style="width:400px;background-color:#E4F5EF">
  		<br/>
  		<form id="myform" action="" method="post">
  				<input type="hidden" name="id" value="" />
  				<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
  					<tr>
  						<td align="left">条码前缀&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="prefix" name="prefix" class="easyui-validatebox" required=true validType="account" missingMessage="条码前缀必填!" invalidMessage="条码前缀必须是4位数字!" value="" style="height:22px;border:1px solid #A4BED4;"/>
  						<a href="javascript:void(0)" id="searchbtn2" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"><font size="1">查询</font></a></td>	    						
  					</tr>
  					<tr>
  						<td align="left">起始编码&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="startCode" name="startCode" class="easyui-validatebox" required=true validType="equalLength[6]" missingMessage="起始编码必填!" invalidMessage="起始编码必须是6位数字!" readonly="readonly" value="" style="background-color: #E1E6E9;width:90%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="left">终止编码&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="endCode" name="endCode" class="easyui-validatebox" required=true validType="equalLength[6]" missingMessage="终止编码必填!" invalidMessage="终止编码必须是6位数字且大于起始编码!" value="" style="width:90%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>  					
  					<tr align="center">
  						<td colspan="2">
  							<a href="javascript:void(0)" id="saveTag" class="easyui-linkbutton">确定</a>
  							<a href="javascript:void(0)" id="close" class="easyui-linkbutton">关闭</a>  														
  						</td>
  					</tr>
  				</table>
  		</form> 			
	</div>	
	<div id="bcTarget" ></div>
  </body>
</html>
