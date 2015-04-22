<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>产品信息查询</title>
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
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/plugins/datagrid-detailview.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
			$(function(){
				$('#redempStatus').combobox({
					editable:false,
					panelHeight:'auto'
				});
				$('#redemptionStatus').combobox({
					editable:false,
					panelHeight:'auto'
				});
				
				$('#t_branchContractProduct').datagrid({
						title:'合同产品信息' ,
						fit:true ,
						height:450 ,
						url:'contractAction!getBranchContractProductList.action',
						fitColumns:false ,  
						striped: true ,
						loadMsg: '数据正在加载,请耐心的等待...' ,
						rownumbers:true ,
						pagination: true , 
						pageSize: 10 ,
						pageList:[10,20,50] ,
						onLoadSuccess: function (result){
							LG.showMsg("查询结果提示",result.retCode,result.message,false);
						},
						onLoadError: function (xhr,data){
							showErrorMessage(xhr);
						},
						frozenColumns:[[
							{field:'ck' ,width:50 ,checkbox: true},
							{field:'contractId' ,title:'合同号' ,hidden: true },
							{field:'redempStatus' ,title:'产品赎回状态' ,width:80},
							{field:'customeId' ,title:'客户号' ,width:80,formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewContract(\'' + row.contractId+ '\')">'+row.customeId+'</a>'}},
							{field:'customeName' ,title:'客户姓名' ,width:80},
							{field:'productId' ,title:'产品Id' ,hidden:true}
						]],
						columns:[[
							{field:'productCode' ,title:'产品编码' ,width:100,formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewProduct(\'' + row.productId+ '\')">'+row.productCode+'</a>'}},
							{field:'productName' ,title:'产品名称' ,width:120},
							{field:'isRoll' ,title:'是否滚动',width:80},
							{field:'dueDate' ,title:'到期日' ,width:100},
							{field:'openDay' ,title:'开放日' ,width:100},
							{field:'redeemBegin' ,title:'赎回开始时间' ,width:100},
							{field:'redeemEnd' ,title:'赎回结束时间' ,width:100},
							{field:'rollBenefit' ,title:'滚动期收益(%)' ,width:100},
							{field:'belongDepartment' ,title:'归属机构' ,width:100},
							{field:'signDepartment' ,title:'签约机构' ,width:100}
						]],
						view: detailview,
						detailFormatter:function(index,row){
							return '<div><table id="ddv-' + index + '"></table></div>';
						},
						onExpandRow: function(index,row){ 
							var productId = row.productId;
					        $('#ddv-'+index).datagrid({  
					        	url: 'productAction!getRedemptionByProductId.action?productId='+productId,
					            fitColumns:true,  
					            rownumbers:true,  
					            loadMsg:'',  
					            height:'auto',  
					           	columns:[[  
				            		{field:'redemptionIntervalId',title:'赎回区间ID',hidden: true},
									{field:'openDay',title:'开放日',width:80},
									{field:'redeemBegin',title:'赎回开始时间',width:100},
									{field:'redeemEnd',title:'赎回结束时间',width:100},
									{field:'rollBenefit',title:'滚动期收益(%)',width:80},
									{field:'redemptionStatus',title:'赎回期状态',width:80}
				                ]],  
				                onResize:function(){  
				                   $('#t_branchContractProduct').datagrid('fixDetailRowHeight',index);  
				                },  
				                onLoadSuccess:function(){  
				                   setTimeout(function(){  
				                        $('#t_branchContractProduct').datagrid('fixDetailRowHeight',index);  
				                    },0);  
				                },
								onLoadError: function (xhr,data){
									showErrorMessage(xhr);
								}
				            });  
				            $('#t_branchContract').datagrid('fixDetailRowHeight',index);
				       }
				});
				$('#searchbtn').click(function(){
					$('#t_branchContractProduct').datagrid('load' ,serializeForm($('#mysearch')));
				});
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_branchContractProduct').datagrid('load' ,{});
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
			    $('#dueDateBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#dueDateEnd').datebox({
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
	</script>
  </head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width:100%;height:100%">
		<div region="north" title="合同产品信息查询" collapsed=false style="height:120%;overflow:hidden">
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;客户号&nbsp;<input name="customeId"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;产品编码:&nbsp;<input name="productCode"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="40%">&nbsp;开放日:&nbsp;<input id="openDayBegin"  name="openDayBegin" class="easyui-datebox" value="" style="width:120%"/>
									&nbsp;到&nbsp;<input name="openDayEnd" id="openDayEnd" class="easyui-datebox" value="" style="width:120%"/></td>
					</tr>
				</table>
				<table width="100%">						
					<tr>
						<td width="20%">&nbsp;客户名&nbsp;<input name="customeName"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" class="easyui-validatebox"  value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="40%">&nbsp;到期日:&nbsp;<input id="dueDateBegin"  name="dueDateBegin" class="easyui-datebox" value="" style="width:120%"/>
									&nbsp;到&nbsp;<input name="dueDateEnd" id="dueDateEnd" class="easyui-datebox" value="" style="width:120%"/></td>
					</tr>
				</table>
				<table width="100%">
					<tr>
	    				<td width="30%">&nbsp;赎回区间:&nbsp;<input name="redeemBegin" id="redeemBegin" class="easyui-datebox" value="" style="width:120%"/> 							
									&nbsp;到&nbsp;<input name="redeemEnd" id="redeemEnd" class="easyui-datebox" value="" style="width:120%"/></td>
						<td width="20%">&nbsp;赎回期类型:&nbsp;
		    				<select id="redemptionStatus" class="" name="redemptionStatus" style="width:100%;">
		    					<option value=""></option>
		    					<option value="已过赎回期">已过赎回期</option>
		    					<option value="正在赎回期">正在赎回期</option>
		    					<option value="即将开始赎回">即将开始赎回</option>
		    					<option value="已过开放日">已过开放日</option>
		    				</select>
	    				</td>
						<td width="20%">&nbsp;赎回状态:&nbsp;
		    				<select id="redempStatus" class="" name="redempStatus" style="width:100%;">
		    					<option value=""></option>
		    					<option value="产品未赎回">产品未赎回</option>
		    					<option value="产品赎回中">产品赎回中</option>
		    					<option value="赎回确认">赎回确认</option>
		    					<option value="产品已赎回">产品已赎回</option>
		    				</select>
	    				</td>
	    				<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">&nbsp;&nbsp;查询&nbsp;&nbsp;</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">&nbsp;&nbsp;清空&nbsp;&nbsp;</a></td>	
					</tr>
				</table>				   	
			</form>
		</div>				
		<div region="center" >
			<table id="t_branchContractProduct"></table>
		</div>
	</div>
	<div id="mydialogContract" title="查看合同信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
	<div id="mydialogProduct" title="查看产品信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
  </body>
</html>
