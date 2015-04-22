<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>深度开发月度报总表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/dev.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
			$(function(){
				$('#t_fdevSummary').datagrid({
						title:'深度开发月度报总表' ,
						fit:true ,
						height:450 ,
						url:'fdevReport!getFdevMonthlySummarylist.action',
						fitColumns:true , 
						striped: true ,
						loadMsg: '数据正在加载,请耐心的等待...' ,
						rownumbers:true ,
						onLoadSuccess: function (result){
							LG.showMsg("查询结果提示",result.retCode,result.message,false);
						},
						onLoadError: function (result){
							LG.showError("提示信息","系统访问异常,请联系管理员!");
						},
						frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
							{field:'ck' ,width:50 ,checkbox: true,rowspan:1}
						]],
						columns:[[
							{field:'departmentId' ,title:'机构号',width:50 ,align:'center',hidden: true,rowspan:2},
							{field:'yearMonth' ,title:'月份' ,width:40 ,align:'center',rowspan:2},
							{field:'departmentName' ,title:'申请机构' ,width:50 ,align:'center',rowspan:2},
							{field:'fdevTotal' ,title:'深度数量' ,width:50 ,align:'center',rowspan:2},
							{title:'处理结果',colspan:3}
							],[
								{field:'fdevPassTotal' ,title:'通过' ,width:40 ,align:'center'},
								{field:'fdevUnPassTotal' ,title:'不通过' ,width:50 ,align:'center'},
								{field:'fdevObjectTotal' ,title:'异议' ,width:50 ,align:'center'}
							]] ,
						pagination: true , 
						pageSize: 10 ,
						pageList:[10,20,50] ,
						toolbar:[
							{
								text:'导出选中汇总表' ,
								iconCls:'icon-print' , 
								handler:function(){
									var arr =$('#t_fdevSummary').datagrid('getSelections');
									if(arr.length <=0){
										LG.showWarn("提示消息","至少选择一行记录进行导出!");
									} else {
										var departmentIds = '';
										var yearMonths = '';
										for(var i =0 ;i<arr.length;i++){
											departmentIds += arr[i].departmentId + ',' ;
											yearMonths += arr[i].yearMonth + ',' ;
										}
										departmentIds = departmentIds.substring(0 , departmentIds.length-1);
										yearMonths = yearMonths.substring(0 , yearMonths.length-1);
										var url = 'fdevReport!exportFdevMonthlySelectSummaryExcel.action?departmentId='+departmentIds+'&yearMonth='+yearMonths;
										window.location.href=url;
									}
								}
							},
							{text:'导出月度报汇总表' ,iconCls:'icon-print' ,handler: printFdevSummarySelectMonth}
						]
				});
				
				$('#searchbtn').click(function(){
					$('#t_fdevSummary').datagrid('load' ,serializeForm($('#mysearch')));
				});
				
				
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_fdevSummary').datagrid('load' ,{});
				});
				
				$('#confirmbtn').click(function(){
					var yearMonth = $('#yearMonth').datebox("getValue");
					var url = 'fdevReport!exportFdevMonthlySummaryExcel.action?yearMonth='+yearMonth;
					window.location.href=url;
				});
			});
			function printFdevSummarySelectMonth(){
				$('#yearMonth').val("");
				$('#selectMonth').dialog('open'); //打开窗口
			}
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
			     $('#completeMonthBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString());
		            }
			     });
			     $('#completeMonthEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString());
		            }
			     });
			     $('#yearMonth').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString());
		            }
			  	});
			});	
	</script>
  </head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
		<div region="north" title="深度开发月度报总表查询" style="height:60%;overflow:hidden" > 					
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="30%">&nbsp;月份:&nbsp;<input name="completeMonthBegin" id="completeMonthBegin" class="easyui-datebox" value="" style="width:140%;"/>
						&nbsp;至&nbsp;<input name="completeMonthEnd" id="completeMonthEnd" class="easyui-datebox" value="" style="width:140%;"/></td>
						<td width="20%">申请机构:<input name="departmentName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>	
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a></td>
					</tr>
				</table> 
			</form>
		</div>				
		<div region="center" >
			<table id="t_fdevSummary"></table>
		</div>
	</div>
	<div id="selectMonth" title="导出月度报表" modal=false  draggable=true class="easyui-dialog" closed=true style="background-color:#E4F5EF;width:300;height:100">
		<table width="100%">
			<tr align="center">
			    <td align="center">&nbsp;选择年月
			    	<input type="text" class="easyui-datebox" id="yearMonth" name="yearMonth" style="width:120%;height:22px;border:1px solid #A4BED4;"/>
			    </td>
			</tr>
			<tr align="center">
			 	<td><a href="javascript:void(0)" id="confirmbtn" class="easyui-linkbutton">&nbsp;&nbsp;确定&nbsp;&nbsp;</a></td>
			</tr>
		</table>
	</div>
  </body>
</html>
