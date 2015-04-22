<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String contractId = request.getParameter("contractId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>contractView</title>
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
	<script type="text/javascript" src="<%=path %>/js/LG.js"></script>
 </head>
 <body style="overflow:hidden">
 	<script type="text/javascript">
		$(function(){
			var contractId = <%=contractId%>;
			var params = {'contractId':contractId};
			$.ajax({
				type: 'post',
				url: 'contractAction!getContractByContractId.action',
				cache:false ,
				data:params,
				dataType:'json' ,
				success:function(result){
					if(result.customeId!=null&&result.customeId.toString().length>0){
						$('#customeId0').val(result.customeId);
						$('#customeName0').val(result.customeName);
						$('#saleDate0').val(result.saleDate);
						$('#productType0').val(result.productType);
						$('#productCode0').val(result.productCode);
						$('#productName0').val(result.productName);
						$('#money0').val(result.money);
						$('#signAccount0').val(result.signAccount);
						$('#saleManager0').val(result.saleManager);
						$('#businessManager0').val(result.businessManager);
						$('#belongDepartment0').val(result.belongDepartment);
						$('#signDepartment0').val(result.signDepartment);
					}else{
						LG.showError("提示信息","系统访问异常,请联系管理员!");
					}
				}
			});
		});
	</script>
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
   		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
    		<br/>
	  			<div class="title" align="center" ><table><tr><td><h4>理财合同信息表</h4></td></tr></table></div>
  				<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
  					<tr>
  						<td width="20%" align="center">客户号&nbsp;<font color="red">*</font></td>
  						<td width="25%" ><input type="text" id="customeId0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td width="20%" align="center">客户姓名&nbsp;<font color="red">*</font></td>
  						<td width="25%"><input type="text" id="customeName0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">销售日期&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="saleDate0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">产品类型&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="productType0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">产品编码&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="productCode0" readonly="readonly" value="" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">产品名称&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="productName0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">金额  (万)&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="money0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">签约帐号&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="signAccount0" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">销售经理&nbsp;</td>
  						<td><input id="saleManager0" type="text" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">理财经理&nbsp;</td>
  						<td><input id="businessManager0" type="text" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">归属机构&nbsp;<font color="red">*</font></td>
  						<td><input id="belongDepartment0" type="text" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">签约机构&nbsp;<font color="red">*</font></td>
  						<td><input id="signDepartment0" type="text" value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>			 					    					    					    					    					    					    					    					
  				</table>			
			</div>
		</div>
  </body>
</html>
