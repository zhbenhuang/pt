<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>合同查询</title>
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
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript">
			$(function(){
				$('#redempStatus').combobox({
					editable:false,
					panelHeight:'auto'
				});
				$('#handStatus').combobox({
					editable:false,
					panelHeight:'auto'
				});
				$('#t_branchContract').datagrid({
						title:'合同信息列表' ,
						fit:true ,
						height:450 ,
						url:'contractAction!getBranchContractList.action',
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
							{field:'customeId' ,title:'客户号' ,width:80,formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewContract(\'' + row.contractId+ '\')">'+row.customeId+'</a>'}},
							{field:'customeName' ,title:'客户姓名' ,width:80}
						]],
						columns:[[
							{field:'productId' ,title:'产品Id' ,hidden:true},
							{field:'productCode' ,title:'产品编码' ,width:100,formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewProduct(\'' + row.productId+ '\')">'+row.productCode+'</a>'}},
							{field:'productName' ,title:'产品名称' ,width:120},
							{field:'isRoll' ,title:'是否滚动',width:100},
							{field:'benefitDate' ,title:'起息日' ,width:80},
							{field:'dueDate' ,title:'到期日' ,width:80},
							{field:'plannedBenefit' ,title:'预期收益(%)' ,width:100},
							{field:'signAccount' ,title:'签约帐号' ,width:150},
							{field:'handStatus' ,title:'合同移交状态' ,width:80},
							{field:'handDate' ,title:'移交日期' ,width:80},
							{field:'getContractDate' ,title:'领取合同日' ,width:80},
							{field:'redempStatus' ,title:'产品赎回状态' ,width:80},
							{field:'redempStartDate' ,title:'赎回申请日' ,width:80},
							{field:'redempConformDate' ,title:'赎回确认日' ,width:80},
							{field:'openDay' ,title:'开放日' ,width:80},
							{field:'redeemBegin' ,title:'赎回起始日' ,width:80},
							{field:'redeemEnd' ,title:'赎回结束日' ,width:80},
							{field:'rollBenefit' ,title:'滚动期收益(%)' ,width:100}
						]] ,
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
				                   $('#t_branchContract').datagrid('fixDetailRowHeight',index);  
				                },  
				                onLoadSuccess:function(){  
				                   setTimeout(function(){  
				                        $('#t_branchContract').datagrid('fixDetailRowHeight',index);  
				                    },0);  
				                } ,
								onLoadError: function (xhr,data){
									showErrorMessage(xhr);
								}
				            });  
				            $('#t_branchContract').datagrid('fixDetailRowHeight',index);  
				        }
				});
				$('#searchbtn').click(function(){
					$('#t_branchContract').datagrid('load' ,serializeForm($('#mysearch')));
				});
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_branchContract').datagrid('load' ,{});
				});
			});
			function viewContract(contractId){
				$('#mydialogContract').dialog('open').dialog("refresh","funcMng/contractView.jsp?contractId='"+contractId+"'");
			}
			function viewProduct(productId){
				$('#mydialogProduct').dialog('open').dialog("refresh","funcMng/productView.jsp?productId='"+productId+"'");
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
	<div id="lay" class="easyui-layout" style="width:100%;height:100%">
		<div region="north" title="合同查询" collapsed=false style="height:100%;overflow:hidden">
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;客户号&nbsp;<input name="customeId"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;产品编码:&nbsp;<input name="productCode"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="30%">&nbsp;移交状态:&nbsp;
		    				<select id="handStatus" class="" name="handStatus" style="width:100%;">
		    					<option value=""></option>
		    					<option value="录入未移交">录入未移交</option>
		    					<option value="合同已移交">合同已移交</option>
		    					<option value="合同已返回">合同已返回</option>
		    					<option value="合同已领取">合同已领取</option>
		    				</select>
	    				</td>	
					</tr>						
					<tr>
						<td width="25%">&nbsp;客户名&nbsp;<input name="customeName"  class="easyui-validatebox" value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" class="easyui-validatebox"  value="" style="width:55%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="30%">&nbsp;赎回状态:&nbsp;
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
			<table id="t_branchContract"></table>
		</div>
	</div>
	<div id="mydialogContract" title="查看合同信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
	<div id="mydialogProduct" title="查看产品信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
	</div>
  </body>
</html>
