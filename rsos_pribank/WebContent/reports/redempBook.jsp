<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>赎回台账</title>
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
				$('#redempStatus').combobox({
					editable:false,
					panelHeight:'auto'
				});
				$('#t_redempBook').datagrid({
						title:'赎回台账' ,
						fit:true ,
						height:450 ,
						url:'redempBookAction!getRedempBookList.action',
						fitColumns:false ,  
						striped: true ,
						loadMsg: '数据正在加载,请耐心的等待...' ,
						rownumbers:true ,
						onLoadSuccess: function (result){
							LG.showMsg("查询结果提示",result.retCode,result.message,false);
						},
						onLoadError: function (xhr,data){
							showErrorMessage(xhr);
						},
						frozenColumns:[[
							{field:'ck' ,width:50 ,checkbox: true},
							{field:'productId' ,title:'产品Id' ,hidden:true},
							{field:'productCode' ,title:'产品编码' ,width:100 ,align:'center',formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewProduct(\'' + row.productId+ '\')">'+row.productCode+'</a>'}},
							{field:'productName' ,title:'产品名称' ,width:120 ,align:'center'},
							{field:'benefitDate' ,title:'起息日' ,width:80 ,align:'center'},
							{field:'dueDate' ,title:'到期日' ,width:80 ,align:'center'},
							{field:'plannedBenefit' ,title:'预期收益(%)' ,width:100 ,align:'center'}
						]],
						columns:[[
							{field:'redempStatus' ,title:'赎回状态' ,width:80 ,align:'center'},
							{field:'openDay' ,title:'开放日' ,width:80 ,align:'center'},
							{field:'redeemBegin' ,title:'赎回起始日' ,width:80 ,align:'center'},
							{field:'redeemEnd' ,title:'赎回结束日' ,width:80 ,align:'center'},
							{field:'rollBenefit' ,title:'滚动期收益(%)' ,width:100 ,align:'center'},
							{field:'contractId' ,title:'合同号' ,hidden: true },
							{field:'customeId' ,title:'客户号' ,width:80 ,align:'center',formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewContract(\'' + row.contractId+ '\')">'+row.customeId+'</a>'}},
							{field:'customeName' ,title:'客户名' ,width:80 ,align:'center'},
							{field:'signAccount' ,title:'签约帐号' ,width:150 ,align:'center'},
							{field:'money' ,title:'赎回金额(万)' ,width:100 ,align:'center'},
							{field:'belongDepartment' ,title:'归属机构' ,width:80 ,align:'center'},
							{field:'signDepartment' ,title:'签约机构' ,width:80 ,align:'center'},
							{field:'redempStartDate' ,title:'赎回申请日' ,width:80 ,align:'center'},
							{field:'redempConformDate' ,title:'赎回确认日' ,width:80 ,align:'center'}
						]] ,
						pagination: true , 
						pageSize: 10 ,
						pageList:[10,20,50] ,
						toolbar:[
							{
								text:'导出产品赎回台账' ,
								iconCls:'icon-print',
								handler:redempProductBookExcel
							},{
								text:'导出全产品赎回台账' ,
								iconCls:'icon-print',
								handler:printRedempAllProductBookExcel
							}
						]
				});
				$('#searchbtn').click(function(){
					$('#t_redempBook').datagrid('load' ,serializeForm($('#mysearch')));
				});
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_redempBook').datagrid('load' ,{});
				});
				
				$('#confirmbtn').click(function(){
					var productCode = $('#productCode').attr('value');
					var params = {'productCode':productCode};
					$.ajax({
						type: 'post' ,
						url: 'redempBookAction!redempProductBookExcel.action',
						cache:false ,
						data:params,
						dataType:'json',
						success:function(result){
							if(result.retCode=="E000021"){
								LG.showWarn("提示信息","该产品可能是非滚动产品,无台账可导出,如有异议请联系管理员!");
							}else{
								var url = 'redempBookAction!printRedempProductBookExcel.action?productCode='+productCode;
								window.location.href=url;
							}
						}
					});
				});
			});
			$(function(){
			    $('#openDayBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#openDayEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#redeemBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#redeemEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#confirmBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#confirmEnd').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
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
			function viewContract(contractId){
				$('#mydialogContract').dialog('open').dialog("refresh","funcMng/contractView.jsp?contractId='"+contractId+"'");
			}
			function viewProduct(productId){
				$('#mydialogProduct').dialog('open').dialog("refresh","funcMng/productView.jsp?productId='"+productId+"'");
			}
			function redempProductBookExcel(){
				$('#productCode').val("");
				$('#selectProduct').dialog('open'); //打开窗口
			}
			function printRedempAllProductBookExcel(){
				var url = 'redempBookAction!printRedempAllProductBookExcel.action';
				window.location.href=url;
			}
	</script>
  </head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width:100%;height:100%">
		<div region="north" title="台账查询" collapsed=false style="height:120%;overflow:hidden">
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;产品编码:&nbsp;<input name="productCode"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;客户号&nbsp;<input name="customeId"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="45%">&nbsp;开&nbsp;放&nbsp;&nbsp;日:&nbsp;<input id="openDayBegin"  name="openDayBegin" class="easyui-datebox" value="" style="width:120%"/>
							&nbsp;到&nbsp;<input name="openDayEnd" id="openDayEnd" class="easyui-datebox" value="" style="width:120%"/></td>
					</tr>						
					<tr>
						<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" class="easyui-validatebox"  value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="25%">&nbsp;客户名&nbsp;<input name="customeName"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="45%">&nbsp;赎回区间:&nbsp;<input name="redeemBegin" id="redeemBegin" class="easyui-datebox" value="" style="width:120%"/> 							
							&nbsp;到&nbsp;<input name="redeemEnd" id="redeemEnd" class="easyui-datebox" value="" style="width:120%"/></td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td width="50%">&nbsp;赎回确认日:&nbsp;<input name="confirmBegin" id="confirmBegin" class="easyui-datebox" value="" style="width:120%"/> 							
							&nbsp;到&nbsp;<input name="confirmEnd" id="confirmEnd" class="easyui-datebox" value="" style="width:120%"/></td>
						<td width="30%">&nbsp;赎回状态:&nbsp;
		    				<select id="redempStatus" class="" name="redempStatus" style="width:100%;">
		    					<option value=""></option>
		    					<option value="产品未赎回">产品未赎回</option>
		    					<option value="产品赎回中">产品赎回中</option>
		    					<option value="产品已赎回">产品已赎回</option>
		    				</select>
	    				</td>	
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">&nbsp;&nbsp;查询&nbsp;&nbsp;</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">&nbsp;&nbsp;清空&nbsp;&nbsp;</a></td>
					</tr>
				</table>				   	
			</form>
		</div>				
		<div region="center" >
			<table id="t_redempBook"></table>
		</div>
	</div>
	<div id="selectProduct" title="导出产品赎回台账" modal=false  draggable=true class="easyui-dialog" closed=true style="background-color:#E4F5EF;width:300;height:100">
		<table width="100%">
			<tr align="center">
			    <td >&nbsp;产品编号:
			    	<input type="text" class="easyui-validatebox" id="productCode" style="width:120px;height:22px;border:1px solid #A4BED4;"/>
			    </td>
			</tr>
			<tr align="center">
				<td><a href="javascript:void(0)" id="confirmbtn" class="easyui-linkbutton">&nbsp;&nbsp;确定&nbsp;&nbsp;</a></td>
			</tr>
		</table>
	</div>
	<div id="mydialogContract" title="查看合同信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
	<div id="mydialogProduct" title="查看产品信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
  </body>
</html>
