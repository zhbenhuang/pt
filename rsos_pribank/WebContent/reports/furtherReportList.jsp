<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>深度开发查询</title>
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
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
			$(function(){
				$('#t_deepDev').datagrid({
						title:'深度开发列表' ,
						fit:true ,
						height:450 ,
						url:'fdevReport!getFdevReportList.action',
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
							{field:'ck' ,width:50 ,checkbox: true}
						]],
						columns:[[
							{field:'businessId' ,title:'业务号' ,width:50 ,align:'center',hidden: true },
							{field:'clientId' ,title:'客户号' ,width:40 ,align:'center'},
							{field:'clientName' ,title:'客户名' ,width:50 ,align:'center'},
							{field:'applyerName' ,title:'申请人' ,width:50 ,align:'center'},
							{field:'applyDepartmentName' ,title:'申请人归属机构' ,width:60 ,align:'center'},
							{field:'originalAgent' ,title:'原归属人' ,width:50 ,align:'center'},
							{field:'originalBranchBank' ,title:'原归属机构' ,width:50 ,align:'center'},
							{field:'newAgent' ,title:'新归属人' ,width:70 ,align:'center'},
							{field:'newBranchBank' ,title:'新归属机构' ,width:80 ,align:'center'},
							{field:'applyDate' ,title:'申请日期' ,width:70 ,align:'center'},
							{field:'endDate' ,title:'结束日期' ,width:50 ,align:'center'},
							{field:'result' ,title:'审批结果' ,width:40 ,align:'center'}
						]] ,
						pagination: true , 
						pageSize: 10 ,
						pageList:[10,20,50] ,
						toolbar:[
							{
								text:'查看深度开发' ,
								iconCls:'icon-search' , 
								handler:viewFdevInfo
							},{
								text:'导出选中明细' ,
								iconCls:'icon-print' , 
								handler:function(){
									var arr =$('#t_deepDev').datagrid('getSelections');
									if(arr.length <=0){
										LG.showWarn("提示消息","至少选择一行记录进行导出!");
									} else {
										var ids = '';
										for(var i =0 ;i<arr.length;i++){
											ids += arr[i].businessId + ',' ;
										}
										ids = ids.substring(0 , ids.length-1);
										var url = 'fdevReport!exportExcel.action?ids='+ids;
										window.location.href=url;
									}
								}
							},
							{
								text:'导出月度报明细表' ,
								iconCls:'icon-print' , 
								handler:function(){
									$('#yearMonth').val("");
									$('#exportExcelMonthlyDialog').dialog('open');
								}
							}
						]
				});
				
				$('#exportComfirm').click(function(){
					var yearMonth = $("input[name=yearMonth]").val();
					var url = 'fdevReport!exportExcelMonthly.action?yearMonth='+yearMonth;
					window.location.href=url;
					$('#exportExcelMonthlyDialog').dialog('close');
				});
				
				$('#searchbtn').click(function(){
					$('#t_deepDev').datagrid('load' ,serializeForm($('#mysearch')));
				});
				
				
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_deepDev').datagrid('load' ,{});
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
			
			function viewFdevInfo(){
				var arr =$('#t_deepDev').datagrid('getSelections');
				if(arr.length <=0){
					LG.showWarn("提示消息","请选择一条记录进行查看!");
				}else if(arr.length != 1){
					LG.showWarn("提示消息","只能选择一条记录进行查看!");
				}else {;
					businessId = arr[0].businessId;
					$('#viewFdevDialog').dialog('open').dialog("refresh","reports/viewFdevInfo.jsp?businessId='"+businessId+"'");
				}
			}
			
			$(function(){
			    $('#applyBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			     $('#applyEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#completeBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			     $('#completeEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
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
		<div region="north" title="深度报表查询" style="height:100%;overflow:hidden" > 					
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="17%">客户号:<input name="clientId" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;&nbsp;申请人：&nbsp;&nbsp;<input name="createUserName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>								
						<td width="43%">流程申请日期:<input name="applyBegin" id="applyBegin" class="easyui-datebox" value="" style="width:120%;"/>
						至<input name="applyEnd" id="applyEnd" class="easyui-datebox" value="" style="width:120%;"/></td>
						<td width="20%"></td>
					</tr>
					<tr>								
						<td width="17%">客户名:<input name="clientName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>	
						<td width="25%">申请人机构:<input name="applyDepartmentName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>	
						<td width="43%">流程结束日期:<input name="completeBegin" id="completeBegin" class="easyui-datebox" value="" style="width:120%;"/>
						至<input name="completeEnd" id="completeEnd" class="easyui-datebox" value="" style="width:120%;"/></td>
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a></td>
					</tr>
				</table> 
			</form>
		</div>				
		<div region="center" >
			<table id="t_deepDev"></table>
		</div>
		<div id="exportExcelMonthlyDialog" title="导出月度报明细表" modal=false draggable=true class="easyui-dialog" closed=true style="background-color:#E4F5EF;width:300px;height:100px;overflow:hidden">
			<table width="100%">
				<tr align="center">
					<td>&nbsp;选择年月：
	    				<input type="text" class="easyui-datebox" id="yearMonth" name="yearMonth" style="width:120%;height:22px;border:1px solid #A4BED4;"/>
	    			</td>
				</tr>
				<tr align="center">
					<td><a href="javascript:void(0)" id="exportComfirm" class="easyui-linkbutton">确定</a></td>
				</tr>
			</table>			
		</div>
	</div>
	<div id="viewFdevDialog" title="查看深度开发信息" modal=false  draggable=true class="easyui-dialog" closed=true style="background-color:#E4F5EF;width:800px;height:520px;overflow:hidden" >
	</div>
  </body>
</html>
