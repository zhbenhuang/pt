<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.cmbc.sa.bean.Users"%>
<%@ page language="java" import="rsos.framework.constant.GlobalConstants"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String srcPic = request.getParameter("srcPic");
	String serId = request.getParameter("serId");
	String serValue = request.getParameter("serValue");
	String serName = request.getParameter("serName");
	serName = GlobalConstants.decodeParam("gb2312", serName);
	String bigType = request.getParameter("bigType");
	String smlType = request.getParameter("smlType");
	Users user = (Users)session.getAttribute(GlobalConstants.USER_INFORMATION_KEY);
	String userId = user.getUserId();
	String userDepartmentName = user.getDepartmentName();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>申请</title>
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
  			$('#myformApply').get(0).reset();
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
  		 
		    //
			/**
			  *提交
			  */
			$('#submit').click(function(){
				var clientId = $('#clientId').attr('value');
				var pbclientName = $('#pbclientName').attr('value');
				var mobilePhone = $('#mobilePhone').attr('value');
				var applyQuatt = $('#applyQuatt').attr('value');
				if(clientId==''){
					LG.showWarn("提示消息","未填客户信息号,无法提交!");
				}else if(pbclientName==''){
					LG.showWarn("提示消息","未填客户姓名,无法提交!");
				}else if(mobilePhone==''){
					LG.showWarn("提示消息","未填联系电话,无法提交!");
				}else if(applyQuatt==''){
					LG.showWarn("提示消息","未填申请标的数量,无法提交!");
				}else{
					$.messager.confirm('提示消息','确认提交申请?',function(r){
						if(r){
							$.ajax({
								type: 'post' ,
								url: 'saveServApply!servApplyAction.action',
								cache:false,
								data:$('#my_form').serialize() ,
								dataType:'json',
								success:function(result){
									if(result.retCode=="A000000"){
										$.messager.alert("提示消息",result.message,"info",function(){
											$('#my_form').form('clear');
										});
									}else if(result.retCode=="E000079"){
										LG.showWarn("提示消息","拟申请的标的数量过多,请另输入!");
									}else {
										LG.showError("提示信息","系统访问异常,请联系管理员!");
									}
								}
							});
						}
					});
				}
			});
			
			//扩展easyui表单的验证
			$.extend($.fn.validatebox.defaults.rules, {
				clientId: { 
	        		validator: function (value) {
	            		//var reg = /^[A-Z]{4}[0-9]\d{13}$/;
	            		var reg = /^[0-9]{10}$/;
	            		return reg.test(value);
	        		},
	        		message: 'clientId为4位大写字母前缀+14位数字组成.'
	    		}
			});

		});
		$('#reset').click(function(){
			$('#my_form').form('clear');
		});
		$('#btn10').click(function(){
			window.location.href = "history.go(-1);";
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
 	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
  		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:30%;overflow:hidden">
  		</div>
  		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
   			<br/>
	   		<form id="myformApply" action="" method="post">
	   			<div class="title" align="center" ><table><tr><td><h4>积分兑换申请</h4></td></tr></table></div>
	   			<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
  					<tr>
   						<td width="15%" align="center">标的图片&nbsp;</td>
   						<td width="25%">&nbsp;<%=srcPic %></td>
   						<td width="15%" align="center">标的编号&nbsp;</td>
   						<td width="25%">&nbsp;<%=serId %></td>
   					</tr>
  					<tr>
   						<td align="center">标的名称&nbsp;<%=serName %></td>
   						<td>&nbsp;</td>
   						<td align="center">标的价格&nbsp;<%=serValue %></td>
   						<td>&nbsp;</td>
   					</tr>
  					<tr>
   						<td align="center">标的大类&nbsp;<%=bigType %></td>
   						<td>&nbsp;</td>
   						<td align="center">标的小类&nbsp;<%=smlType %></td>
   						<td>&nbsp;</td>
   					</tr>
   					<tr>
   						<td align="center">客户信息号&nbsp;<font color="red">*</font></td>
   						<td>&nbsp;<input id="clientId" type="text" name="clientId" value="" class="easyui-validatebox" required=true style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
   						<td align="center">客户姓名&nbsp;<font color="red">*</font></td>
   						<td>&nbsp;<input id="pbclientName" type="text" name="pbclientName" value="" class="easyui-validatebox" required=true style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
   					</tr>
   					<tr>
   						<td align="center">联系电话&nbsp;<font color="red">*</font></td>
   						<td>&nbsp;<input id="mobilePhone" type="text" name="mobilePhone" value="" class="easyui-validatebox" required=true style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
   						<td align="center">标的数量&nbsp;<font color="red">*</font></td>
   						<td>&nbsp;<input id="applyQuatt" type="text" name="applyQuatt" value="" class="easyui-validatebox" required=true style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
   					</tr>
   					<tr>
   						<td align="center">备注&nbsp;</td>
   						<td align="left" colspan="3">&nbsp;<input id="remark" name="remark" value="" style="background-color: #E1E6E9;width:80%;height:44px;border:1px solid #A4BED4;"/></td>
   					</tr>
   					<tr>
   						<td align="center">标的附件一&nbsp;</td>
   						<td>&nbsp;<input id="fileUrl1" type="file" name="fileUrl1" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
   						<td align="center">标的附件二&nbsp;</td>
   						<td>&nbsp;<input id="fileUrl2" type="file" name="fileUrl2" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
   					</tr>
   					<tr>
   						<td align="center">申请人&nbsp;</td>
   						<td>&nbsp;<%=userId %></td>
   						<td align="center">申请机构&nbsp;</td>
   						<td>&nbsp;<%=userDepartmentName %></td>
   					</tr> 					 					    					    					    					    					    					    					    					
	   			</table>
  				<table width="90%" align="center" style="background-color:#E4F5EF">
  					<tr>
	    				<td align="center" colspan="4">
		    				<a href="javascript:void(0)" id="submit" class="easyui-linkbutton">提交</a>
		    				<a href="javascript:void(0)" id="btn10" onclick ="javascript:history.go(-1);"   class="easyui-linkbutton">返回</a>
	    				</td>
	    			</tr>   
  				</table>
	   		</form>
	   	</div>
   	</div>
  </body>
</html>