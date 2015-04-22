<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String productId = request.getParameter("productId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
    <base href="<%=basePath%>">
    <title>productView</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/dev.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/js/commons.js"></script>
	<script type="text/javascript" src="<%=path %>/js/LG.js"></script>
</head>
 	
  <body style="overflow:hidden">
  	<script type="text/javascript">
  			var productId = <%=productId%>;
			$(function(){
				$('#isRollNew').combobox({
					editable:false,
					required:true,
					panelHeight:'auto'
				});
				var params = {'productId':productId};
				$.ajax({
					type: 'post',
					url: 'productAction!getProductByProductId.action',
					cache:false ,
					data:params,
					dataType:'json' ,
					success:function(result){
						if(result.productCode!=null&&result.productCode.toString().length>0){
							$('#productCodeNew').val(result.productCode);
							$('#productNameNew').val(result.productName);
							$('#benefitDateNew').val(result.benefitDate);
							$('#dueDateNew').val(result.dueDate);
							$('#plannedBenefitNew').val(result.plannedBenefit);
							$('#isRollNew').combobox('setValue',result.isRoll);
						}else{
							LG.showError("提示信息","系统访问异常,请联系管理员!");
						}
					}
				});
				$('#dgNew').datagrid({
					 singleSelect: true,
		             url: 'productAction!getRedemptionList.action?productId='+productId,
		             method: 'post',
		             fitColumns:true ,  
					 striped: true ,					//隔行变色特性
		             rownumbers:true ,
		             onLoadSuccess: function (data){					
						LG.showMsg("查询结果提示",data.retCode,data.message,false);
					 },
					 onLoadError: function (xhr,data){
						showErrorMessage(xhr);
					 },
		             pagination: true , 
					 pageSize: 10 ,
					 pageList:[10,20,50],
					 columns:[[
						 {field:'redemptionIntervalId',title:'赎回区间ID',hidden: true},
						 {field:'openDay',title:'开放日',width:150,align:'center'},
						 {field:'redeemBegin',title:'赎回开始时间',width:150,align:'center'},
						 {field:'redeemEnd',title:'赎回结束时间',width:150,align:'center'},
						 {field:'rollBenefit',title:'滚动期收益(%)',width:150,align:'center'},
						 {field:'redemptionStatus',title:'赎回期状态',width:150,align:'center'}
					]]
				});
			});
	</script>
	    <div id="lay" class="easyui-layout" style="width:100%;height:100%" >
		    <div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
			    <br/>
	    		<div class="title" align="center" ><table><tr><td><h4>理财产品信息表(<font style="color:red">*</font>必填)</h4></td></tr></table></div>
   				<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
   					<tr>
   						<td width="20%" align="center">产品编码&nbsp;<font color="red">*</font></td>
   						<td width="25%"><input type="text" id="productCodeNew" name="productCode" readonly="readonly" value="" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
   						<td width="20%" align="center">产品名称&nbsp;<font color="red">*</font></td>
   						<td width="25%" ><input type="text" id="productNameNew" name="productName" readonly="readonly" value="" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
   					</tr>
   					<tr>
   						<td align="center">起息日&nbsp;<font color="red">*</font></td>
   						<td><input type="text" id="benefitDateNew" name="benefitDate" readonly="readonly" value="" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
   						<td align="center">到期日&nbsp;<font color="red">*</font></td>
   						<td><input type="text" id="dueDateNew" name="dueDate" readonly="readonly" value="" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
   					</tr>
   					<tr>
   						<td align="center">预期收益(%)&nbsp;<font color="red">*</font></td>
   						<td><input type="text" id="plannedBenefitNew" name="plannedBenefit" readonly="readonly" value="" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
   						<td align="center">是否滚动&nbsp;<font color="red">*</font></td>
    					<td>
    						<select id="isRollNew" name="isRoll" style="background-color: #E1E6E9;width:100%;" disabled="disabled">
						    	<option value="是">是</option>
						    	<option value="否">否</option>
						    </select>
    					</td>
   					</tr>
   					<tr>
   						<td colspan="4">
   							<table id="dgNew" class="easyui-datagrid" align="center" border="1" title="赎回区间" style="height:auto;background-color:#E4F5EF">
						    </table>
   						</td>
   					</tr>  					 					    					    					    					    					    					    					    					
   				</table>
			</div>
		</div>
  </body>
</html>
