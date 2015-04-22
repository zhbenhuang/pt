<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String businessId = request.getParameter("businessId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>redempView</title>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	<script type="text/javascript" src="<%=path %>/js/vobCombox.js"></script>
	<script type="text/javascript" src="<%=path %>/js/LG.js"></script>
 </head>
 <body style="overflow:hidden">
 	<script type="text/javascript">
		$(function(){
			var businessId = <%=businessId%>;
			var currentPage = 'redempView';
			var params = {'businessId':businessId,'currentPage':currentPage};
			$.ajax({
				type: 'post',
				url: 'redempProcess!viewRedempInfoAction.action',
				cache:false ,
				data:params,
				dataType:'json' ,
				success:function(result){
					if(result.redCode=="A000000"){
						$("#processStatus").val(result.status);
						$("#customeId").val(result.contract.customeId);
						$("#customeName").val(result.contract.customeName);
						$("#saleDate").val(result.contract.saleDate);
						$("#productType").val(result.contract.productType);
						$("#productCode").val(result.contract.productCode);
						$("#productName").val(result.contract.productName);
						$("#money").val(result.contract.money);
						$("#signAccount").val(result.contract.signAccount);
						$("#saleManager").val(result.contract.saleManager);
						$("#businessManager").val(result.contract.businessManager);
						$("#belongDepartment").val(result.contract.belongDepartment);
						$("#signDepartment").val(result.contract.signDepartment);
						$("#codeId").val(result.redemptionInfo.codeId);
						$("#remark").val(result.redemptionInfo.remark);
						if(result.productRedemptionInterval!=null){
							$("#redempBegin").val(result.productRedemptionInterval.redeemBegin);
							$("#redempEnd").val(result.productRedemptionInterval.redeemEnd);
						}
					}else{
						LG.showWarn("提示消息",result.message);
					}
				}
			});
		});
	</script>
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
   		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%;overflow:hidden">
   		<br/>
   		<form id="myformtwo" action="" method="post">
   		 	<div class="title" align="center"><h4>理财产品赎回流程（<font style="color:red">*</font>必填）</h4></div>	
				<div align="center">
					<table width="90%" border="1" align="center" style="background-color:#E4F5EF;table-layout:fixed;">
						<tr>
							<td width="15%" align="center"><font color="red">流程状态</font></td>
							<td width="30%"><input id="processStatus" name="processStatus" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;" readonly="readonly"></td>
							<td width="15%" align="center"><font color="red">赎回单条码&nbsp;*</font></td>
    						<td width="30%"><input id="codeId" type="text" name="codeId" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
						</tr>
						<tr>
    						<td align="center">客户号&nbsp;<font color="red">*</font></td>
    						<td ><input type="text" id="customeId" name="customeId" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
    						<td align="center">客户姓名&nbsp;<font color="red">*</font></td>
    						<td><input id="customeName" name="customeName" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
    					</tr>
    					<tr>
    						<td align="center">销售日期&nbsp;<font color="red">*</font></td>
    						<td><input type="text" id="saleDate" name="saleDate" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
    						<td align="center">产品类型&nbsp;<font color="red">*</font></td>
    						<td><input type="text" id="productType"name="productType" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;" readonly="readonly"/></td>
    					</tr>
    					<tr>
    						<td align="center">产品编码&nbsp;<font color="red">*</font></td>
    						<td><input type="text" id="productCode" name="productCode" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">产品名称&nbsp;<font color="red">*</font></td>
    						<td><input id="productName" type="text" name="productName" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">金额  (万)&nbsp;<font color="red">*</font></td>
    						<td><input id="money" type="text" name="money" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">签约帐号&nbsp;<font color="red">*</font></td>
    						<td><input id="signAccount" type="text" name="signAccount" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">销售经理&nbsp;</td>
    						<td><input id="saleManager" type="text" name="saleManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">理财经理&nbsp;</td>
    						<td><input id="businessManager" type="text" name="businessManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">归属机构&nbsp;<font color="red">*</font></td>
    						<td><input id="belongDepartment" type="text" name="belongDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">签约机构&nbsp;<font color="red">*</font></td>
    						<td><input id="signDepartment" type="text" name="signDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">赎回区间&nbsp;</td>
    						<td colspan="3">
    							<input id="redempBegin" type="text" name="redempBegin" value="" readonly="readonly" style="background-color: #E1E6E9;width:35%;height:22px;border:1px solid #A4BED4;"/>
    							&nbsp;~&nbsp;<input id="redempEnd" type="text" name="redempEnd" value="" readonly="readonly" style="background-color: #E1E6E9;width:35%;height:22px;border:1px solid #A4BED4;"/>
    						</td>
    					</tr>
    					<tr>
    						<td align="center"><font color="red">备注&nbsp;</font></td>
    						<td colspan="3"><input id="remark" type="text" name="remark" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
					</table>
				</div>
   			</form>
  		</div>
	</div>
  </body>
</html>
