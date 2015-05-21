<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.cmbc.sa.bean.Users"%>
<%@ page language="java" import="rsos.framework.constant.GlobalConstants"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Integer business = (Integer)session.getAttribute("BUSINESS");
	System.out.println("business="+business);
	Users user = (Users)session.getAttribute(GlobalConstants.USER_INFORMATION_KEY);
	String userId = user.getUserId();
	String userDepartmentName = user.getDepartmentName();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">    
    <title>积分兑换</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>		
	<script type="text/javascript">
	var actionMethod="";		//判断新增和修改方法 
	$(function(){
		var business = <%=business%>;
		$('#newServApplyDialog').dialog({
			onClose:function()
			{
				$('#posbusiness').html("");
			} 
		});
		
		
		$('#t_servInfo').datagrid({
			title:'列表' ,
			fit:true ,
			height:450 ,
			url:'queryServInfoList!queryServInfoList.action' ,
			fitColumns:true ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			frozenColumns:[[
				{field:'ck' ,width:50 ,checkbox: true}
			]],
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'serPic' ,title:'图片' ,width:100 ,align:'center'},
				{field:'serId' ,title:'活动ID' ,width:100 ,align:'center'},
				{field:'serName' ,title:'活动名称' ,width:100 ,align:'center'},
				{field:'serDes' ,title:'活动描述' ,width:100 ,align:'center'},
				{field:'serValue' ,title:'价格' ,width:100 ,align:'center'},
				{field:'serAmount' ,title:'剩余数量' ,width:100 ,align:'center'},
				{field:'begTime' ,title:'生效时间' ,width:110 ,align:'center'},
				{field:'endTime' ,title:'失效时间' ,width:110 ,align:'center'},
				{field:'opt' ,title:'执行操作' ,width:100 ,align:'center'}

			]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[10,20,50] ,
			toolbar:[
		         {
		       		text:'申请兑换' ,
		       		iconCls:'icon-my-add' , 
					handler:'newServApply'
		       	 }	   
			]
		});

	});
	
	function newServApply(){
		actionMethod = 'newServApply';
		var grid =$('#t_servInfo').datagrid('getSelections');
		if(grid.length != 1){
			LG.showWarn("提示信息","请选择一条记录修改标的!");
		} else {
			$('#newServApplyDialog').dialog({
				title:'申请兑换'
			});
			$('#newServApplyDialog').dialog('open'); 
			$('#newServApplyForm').get(0).reset();  
			$('#newServApplyForm').form('load',{
				serPic:grid[0].serPic ,
				serId:grid[0].serId ,
				serName:grid[0].serName,
				bigType:grid[0].bigType,
				bigTypeHidden:grid[0].bigType,
				smlType:grid[0].smlType,
				smlTypeHidden:grid[0].smlType,
				serValue:grid[0].serValue
				
			});
		}
	}
	

	$('#confirmAdd').click(function(){
		if($('#newServApplyForm').form('validate')){				
			$.ajax({
				type: 'post' ,
				url: actionMethod+'!'+actionMethod+'.action',
				cache:false ,
				data:$('#newServApplyForm').serialize() ,
				dataType:'json' ,
				success:function(result){
					if(result.retCode=='A000000'){
						$.messager.alert('提示消息',result.message,'info',function(){
							$('#newServApplyDialog').dialog('close');
							$('#t_servInfo').datagrid('reload');
						});
					}else{
						LG.showError("提示信息",result.message);
					}
				}
			});
		} else {
			LG.showError("提示信息","数据验证不通过,不能保存!");
		}
	});

	$('#cancelAdd').click(function(){
		$('#newServApplyDialog').dialog('close');
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
		<div region="center" style="width: 100%;height:100%">
			<table id="t_servInfo" style="width: 100%;height:100%"></table>
		</div>
	</div>

	<div id="newServApplyDialog" title="申请兑换" class="easyui-dialog" style="width:600px;height:450px;background-color:#E4F5EF;"  
        data-options="iconCls:'icon-dialog',modal:true,draggable:true,closed:true">
		<form id="newServApplyForm" action="" method="post"  align="center">
		<br/>
		<table align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td width="15%" align="center">标的图片&nbsp;</td>
				<td width="25%">&nbsp;<input id="serId" name="serId" type="file" value=""/><br /><a href="#" >"xxxx.jpg"</a></td>
				<td width="15%" align="center">标的编号&nbsp;</td>
				<td width="25%">&nbsp;<input id="serId" name="serId" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  /></td>
			</tr>
			<tr>
				<td align="center">标的名称&nbsp;</td>
				<td>&nbsp;<input id="serName" name="serName" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  /></td>
				<td align="center">标的价格&nbsp;</td>
				<td>&nbsp;<input id="serValue" name="serValue" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  /></td>
			</tr>
			<tr>
				<td align="center">标的大类&nbsp;</td>
				<td>&nbsp;<input id="bigType" name="bigType" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  /></td>
				<td align="center">标的小类&nbsp;</td>
				<td>&nbsp;<input id="smlType" name="smlType" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  /></td>
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
				<td align="center" >标的附件一&nbsp;</td>
				<td colspan="3">&nbsp;<input id="fileUrl1" type="file" name="fileUrl1" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr>
				<td align="center">标的附件二&nbsp;</td>
				<td colspan="3">&nbsp;<input id="fileUrl2" type="file" name="fileUrl2" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr>
				<td align="center">申请人&nbsp;</td>
				<td>&nbsp;<input id="userId" name="userId" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value="<%=userId%>"  /></td>
				<td align="center">申请机构&nbsp;</td>
				<td>&nbsp;<input id="userDepartmentName" name="userDepartmentName" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value="<%=userDepartmentName%>"  /></td>
			</tr> 			
			
			<tr align="center">
				<td colspan="2">
					<a href="javascript:void(0)" id="confirmAdd" class="easyui-linkbutton">申请</a>
					<a href="javascript:void(0)" id="cancelAdd" class="easyui-linkbutton">关闭</a>
				</td>
			</tr>
		</table>
		</form> 			
	</div>
  </body>
</html>
