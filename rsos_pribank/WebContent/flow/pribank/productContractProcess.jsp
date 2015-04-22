<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>理财产品合同流程</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<jsp:include page="selectContract.jsp"/>
<script type="text/javascript">
	$(function()
	{
		var i = 0;
		$("a[name='btn']").click(function(){
			$(this).parent().find('select').combobox('clear');
		});
		/**
		  *启动合同流程
		  */
		$('#submit').click(function(){
			var assignUser = $('#assignUser').attr('value');
			var codeId = $('#codeId').attr('value');
			var contractId = $('#contractId').attr('value');
			if(contractId==''){
				LG.showWarn("提示消息","未显示合同信息,无法提交!");
			}else if(assignUser==''){
				LG.showWarn("提示消息","未指定下一办理人,无法提交!");
			}else if(codeId==''){
				LG.showWarn("提示消息","未输入合同条码号,无法提交!");
			}else{
				$.messager.confirm('提示消息','确认启动流程?',function(r){
					if(r){
						$.ajax({
							type: 'post' ,
							url: 'contractProcess!startContractAction.action',
							cache:false,
							data:$('#my_form').serialize() ,
							dataType:'json',
							success:function(result){
								if(result.retCode=="A000000"){
									$.messager.alert("提示消息",result.message,"info",function(){
										$('#my_form').form('clear');
									});
								}else if(result.retCode=="E000019"){
									LG.showWarn("提示消息","条码号以被使用,请另选条码号输入!");
								}else {
									LG.showError("提示信息","系统访问异常,请联系管理员!");
								}
							}
						});
					}
				});
			}
		});
		$('#reset').click(function(){
			$('#my_form').form('clear');
		});
		//扩展easyui表单的验证
		$.extend($.fn.validatebox.defaults.rules, {
			account: { 
        		validator: function (value) {
            		//var reg = /^[A-Z]{4}[0-9]\d{13}$/;
            		var reg = /^[0-9]{10}$/;
            		return reg.test(value);
        		},
        		message: '条码为4位大写字母前缀+14位数字组成.'
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
		
</script>
</head>

<body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="north" style="background-color:#E4F5EF;width:100%;height:30%;overflow:hidden" > 
			<table align="center">
				<tr>
	    			<td colspan="2">
	    				<a href="javascript:void(0)" id="submit" class="easyui-linkbutton">启动流程</a>
	    				<a href="javascript:void(0)" id="reset" class="easyui-linkbutton">重置</a>
	    			</td>
	    		</tr>   
			</table>
		</div>
		<div region="center" style="background-color:#E4F5EF;width:100%;height:100%">
		<br>
			<form id="my_form" action="" method="post">
	    			<input type="hidden" id="contractId" name="contractId" value="" />
    				<div class="title" align="center"><table><tr><td><h3>理财产品合同流程(<font style="color:red">*必填</font>)</h3></td></tr></table></div>
    				<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
    					<tr>
    						<td width="15%" align="center">客户号&nbsp;<font color="red">*</font></td>
    						<td width="25%">&nbsp;<input type="text" id="customeId" name="customeId" class="easyui-validatebox" style="width:65%;height:22px;border:1px solid #A4BED4;"/>
    							<a href="javascript:void(0)" id="searchCustomeCon" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" ><font size="1">查询</font></a>
    						</td>
    						<td width="15%" align="center">客户姓名&nbsp;<font color="red">*</font></td>
    						<td width="25%">&nbsp;<input id="customeName" name="customeName" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">销售日期&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input type="text" id="saleDate" name="saleDate" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">产品类型&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input type="text" id="productType"name="productType" class="easyui-validatebox" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">产品编码&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input type="text" id="productCode" name="productCode" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">产品名称&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="productName" type="text" name="productName" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">金额  (万)&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="money" type="text" name="money" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">签约帐号&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="signAccount" type="text" name="signAccount" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">销售经理&nbsp;</td>
    						<td>&nbsp;<input id="saleManager" type="text" name="saleManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">理财经理&nbsp;</td>
    						<td>&nbsp;<input id="businessManager" type="text" name="businessManager" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center">归属机构&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="belongDepartment" type="text" name="belongDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    						<td align="center">签约机构&nbsp;<font color="red">*</font></td>
    						<td>&nbsp;<input id="signDepartment" type="text" name="signDepartment" value="" readonly="readonly" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
    					</tr>
    					<tr>
    						<td align="center"><font color="red">输入合同条码&nbsp;*</font></td>
    						<td colspan="3">&nbsp;<input id="codeId" type="text" name="codeId" class="easyui-validatebox" validtype="account" missingMessage="合同条码必填" invalidMessage="条码为10位数字组成！" required=true style="width:30%;height:22px;border:1px solid #A4BED4;"/>
    							<a href="javascript:void(0)" id="myView" class="easyui-linkbutton" data-options="iconCls:'icon-my-view',plain:true" ><font size="1">是否可用</font></a>
    						</td>
    					</tr>
    					<tr id="assign">
				    		<td align="center"><font style="color:red">指定下一处理人&nbsp;&nbsp;</font></td>
				    		<td colspan="3">&nbsp;<input id="assignUser" name="assignUserId" readonly="readonly" style="background-color: #E1E6E9;width:38%;height:22px;border:1px solid #A4BED4;">
				    			<a href="javascript:void(0)" id="selectUser" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" ><font size="1">选择</font></a>
				    		</td>
				  		</tr>				 					    					    					    					    					    					    					    					
    				</table>
	    		</form> 			
			</div>
	</div>
</body>
</html>
