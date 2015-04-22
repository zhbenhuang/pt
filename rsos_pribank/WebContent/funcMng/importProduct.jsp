<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>afterServiceDealFdev</title>
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
  			$('#myformImport').get(0).reset();
  			$('#benefitDateImport').datebox({
		        formatter: function (date) {
		            var y = date.getFullYear();
		            var m = date.getMonth() + 1;
		            var d = date.getDate();
		            return y.toString() +"/"+ m.toString() +"/"+ d.toString();
		        }
			});
		    $('#dueDateImport').datebox({
	            formatter: function (date) {
	                var y = date.getFullYear();
	                var m = date.getMonth() + 1;
	                var d = date.getDate();
	                return y.toString() +"/"+ m.toString() +"/"+ d.toString();
	            }
		    });
		    $('#firstOpenDay').datebox({
	            formatter: function (date) {
	                var y = date.getFullYear();
	                var m = date.getMonth() + 1;
	                var d = date.getDate();
	                return y.toString() +"/"+ m.toString() +"/"+ d.toString();
	            }
		    });
  		});
  		
  	</script>
 	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
  		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:30%;overflow:hidden">
			<table>
				<tr>
					<td colspan="2">
						<a href="javascript:void(0)" onclick="saveImport()" class="easyui-linkbutton">保存</a>
						<a href="javascript:void(0)" onclick="closeImport()"class="easyui-linkbutton">关闭</a>
					</td>
				</tr> 
			</table>  					 					    					    					    					    					    					    					    					
  		</div>
  		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
   			<br/>
	   		<form id="myformImport" action="" method="post">
	   			<div class="title" align="center" ><table><tr><td><h4>理财产品信息表(<font style="color:red">*</font>必填)</h4></td></tr></table></div>
	   			<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
  					<tr>
  						<td width="20%" align="center">产品编码&nbsp;<font color="red">*</font></td>
  						<td width="25%"><input type="text" id="productCodeImport" name="productCodeImport" class="easyui-validatebox" required=true missingMessage="产品编码不能为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td width="20%" align="center">产品名称&nbsp;<font color="red">*</font></td>
  						<td width="25%" ><input type="text" id="productNameImport" name="productNameImport" class="easyui-validatebox" required=true missingMessage="产品名称不能为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">起息日&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="benefitDateImport" name="benefitDateImport" class="easyui-datebox" required=true missingMessage="起息日不能为空" data-options="editable:false" value="" readonly="readonly" style="background-color: #E1E6E9;width:200%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">到期日&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="dueDateImport" name="dueDateImport" value="" class="easyui-datebox" required=true missingMessage="到期日不能为空" data-options="editable:false" validType="TimeCheck['benefitDate']" invalidMessage="到期日必须大于起息日" readonly="readonly" style="background-color: #E1E6E9;width:200%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">预期收益(%)&nbsp;<font color="red">*</font></td>
  						<td><input type="text" id="plannedBenefitImport" name="plannedBenefitImport" class="easyui-numberbox" min="0" max="100" precision="2" required=true missingMessage="收益不可为空,且不能为负数" invalidMessage="收益不可为负数！" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">是否滚动&nbsp;<font color="red">*</font></td>
   					<td>
   						<span style="height:22px;border:1px solid #A4BED4;">
   						<SELECT id="isRollImport" name="isRollImport" style="width:100%">
							<option value=""></option>
							<option value="是">是</option>
							<option value="否">否</option>
						</SELECT>
						</span>
					</td>
  					</tr>
  					<tr>
  						<td align="center">首次开放日&nbsp;<font color="red">*</font></td>
  						<td ><input type="text" id="firstOpenDay" name="firstOpenDay" class="easyui-datebox" data-options="editable:false" value="" readonly="readonly" style="background-color: #E1E6E9;width:200%;height:22px;border:1px solid #A4BED4;"/></td>
  						<td align="center">滚动区间跨度(n个月)&nbsp;<font color="red">*</font></td>
  						<td ><input type="text" id="rollIntervalSpan" name="rollIntervalSpan" class="easyui-numberbox"  min="3" precision="0" invalidMessage="滚动区间跨度最少3个月!" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr>
  					<tr>
  						<td align="center">滚动期收益(%)&nbsp;<font color="red">*</font></td>
  						<td colspan="3"><input type="text" id="rollBenefit" name="rollBenefit" class="easyui-numberbox"  min="0" precision="2" invalidMessage="滚动期收益必须为正数!"  value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
  					</tr> 					 					    					    					    					    					    					    					    					
	   			</table>
  				<table width="90%" align="center" style="background-color:#E4F5EF">
  					<tr>
  						<td class="fontStyle"><font style="color:red;font-size:12px">注:&nbsp;1、如果产品是滚动期产品，必须填写:首次开放日,滚动区间跨度,滚动期收益</font></td>
  						<td class="fontStyle"><font style="color:red;font-size:12px">&nbsp;&nbsp;&nbsp;2、时间格式必须为：2014/8/1</font></td>
  					</tr>
  				</table>
	   		</form>
	   	</div>
   	</div>
  </body>
</html>
