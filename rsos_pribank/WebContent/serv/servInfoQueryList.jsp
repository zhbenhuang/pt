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
				{field:'ck' ,width:20 ,checkbox: true}
			]],
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'serPic' ,title:'图片',width:50,align:'center',formatter:function(value,record,index){return '<img src="downloadServInfoFile!getFile.action?serId='+record.serId+'&fileType=0" height="20" width="30" />';}},
				{field:'serId' ,title:'活动ID' ,width:50 ,align:'center'},
				{field:'serName' ,title:'活动名称' ,width:50 ,align:'center'},
				{field:'serDes' ,title:'活动描述' ,width:100 ,align:'center'},
				{field:'serValue' ,title:'价格' ,width:30 ,align:'center'},
				{field:'serAmount' ,title:'剩余数量' ,width:30 ,align:'center'},
				{field:'begTime' ,title:'生效时间' ,width:120 ,align:'center'},
				{field:'endTime' ,title:'失效时间' ,width:120 ,align:'center'},
				{field:'opt' ,title:'操作',width:50,align:'center',formatter:
					function(value,record,index){
						if(record.serAmount >= 1 ){
							return '<button onclick="javascript:apply('+index+');" class="easyui-linkbutton"><span style=color:green;>申请兑换</span></button>';
						}else{
							return '<span style=color:black;>数量不足</span>';
						}
						return '';
					}
				}
			]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[10,20,50] ,
			toolbar:[	
			]
		});

	});
	
	function apply(rowNum){
		if(rowNum){
			$('#t_servInfo').datagrid('selectRow',rowNum);
		}
		actionMethod = 'newServApply';
		var grid =$('#t_servInfo').datagrid('getSelections');
		if(grid.length != 1){
			LG.showWarn("提示信息","请选择一条记录作兑换!");
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
			$("#imgSerPic").attr("src","downloadServInfoFile!getFile.action?serId="+grid[0].serId+"&fileType=0&fileName="+grid[0].serPic);
			$("#showFile1").empty();
			$("#showFile1").append( "<a href=downloadServInfoFile!getFile.action?serId="+grid[0].serId+"&fileType=1&fileName="+grid[0].fileUrl1+">"+grid[0].fileUrl1+"</a><br/>");
			$("#showFile2").empty();
			$("#showFile2").append( "<a href=downloadServInfoFile!getFile.action?serId="+grid[0].serId+"&fileType=2&fileName="+grid[0].fileUrl2+">"+grid[0].fileUrl2+"</a><br/>");
			
			var idKey_board = "board";
			var idKey_board_ydj = "board_ydj";
			var idKey_board_lbc = "board_lbc";
			var idKey_tj = "tj";
			var idKey_gh = "gh";
			var currIdKey="";
			if(grid[0].bigType == 1 && grid[0].smlType == 1){
				currIdKey=idKey_board_ydj;
				show([idKey_board, idKey_board_ydj]);
				hide([idKey_board_lbc, idKey_tj, idKey_gh]);
			}else if(grid[0].bigType == 1 && grid[0].smlType == 2){
				currIdKey=idKey_board_lbc;
				show([idKey_board, idKey_board_lbc]);
				hide([idKey_board_ydj, idKey_tj, idKey_gh]);
			}else if(grid[0].bigType == 1 && grid[0].smlType == 3){
				currIdKey=idKey_tj;
				show([idKey_tj]);
				hide([idKey_board, idKey_board_ydj, idKey_board_lbc, idKey_gh]);
			}else if(grid[0].bigType == 1 && grid[0].smlType == 4){
				currIdKey=idKey_gh;
				show([idKey_gh]);
				hide([idKey_board, idKey_board_ydj, idKey_board_lbc, idKey_tj]);
			}else{
				hide([idKey_board, idKey_board_ydj, idKey_board_lbc, idKey_tj, idKey_gh]);
			}
		}
		
	}
	function show(arrayobj){
		if(!arrayobj || arrayobj.length <=0) return ;
		for (var i=0; i<arrayobj.length; i++){
			$("#newServApplyTable tr[id='"+arrayobj[i]+"']").show();
		}
	}
	function hide(arrayobj){
		if(!arrayobj || arrayobj.length <=0) return ;
		for (var i=0; i<arrayobj.length; i++){
			$("#newServApplyTable tr[id='"+arrayobj[i]+"']").hide();
		}
	}

	function doConfirmAdd1(){
		actionMethod = "apply";
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
							$('#t_servInfo').datagrid('unselectAll');	
						});
					}else{
						LG.showError("提示信息",result.message);
					}
				}
			});
		} else {
			LG.showError("提示信息","数据验证不通过,不能保存!");
		}
	}
	
	function doCancelAdd1(){
		$('#newServApplyDialog').dialog('close');
		$('#t_servInfo').datagrid('reload');
		$('#t_servInfo').datagrid('unselectAll');
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
	<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
		<div region="center" style="width: 100%;height:100%">
			<table id="t_servInfo" style="width: 100%;height:100%"></table>
		</div>
	</div>
	<div id="newServApplyDialog" title="申请兑换--非增值活动" class="easyui-dialog" style="width:600px;height:500px;background-color:#E4F5EF;"  
        data-options="iconCls:'icon-dialog',modal:true,draggable:true,closed:true">
		<form id="newServApplyForm" action="" method="post"  align="center">
		<br/>
		<table id="newServApplyTable" align="center" width="90%" border="1" style="background-color:#E4F5EF;">
			<tr>
				<td width="15%" align="center">标的图片&nbsp;</td>
				<td width="25%">&nbsp;<img id="imgSerPic" src="downloadServInfoFile!getSerPic.action?serId=&fileType=0" height="20" width="30" />
				</td>
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
				<td>&nbsp;<input id="bigType" name="bigType" type="text" class="easyui-combobox" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  data-options="valueField:'id',textField:'text',data:selectBigType"/></td>
				<td align="center">标的小类&nbsp;</td>
				<td>&nbsp;<input id="smlType" name="smlType" type="text" class="easyui-combobox" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value=""  data-options="valueField:'id',textField:'text',data:selectSmlType"/></td>
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
			
			<!-- 易登机/礼宾车 BOARDING-->
			<tr id="board">
				<td align="center">使用者姓名&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="clientName" type="text" name="clientName" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">服务日期&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="servDate" type="text" name="servDate" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr id="board">
				<td align="center">航班号&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="fltNo" type="text" name="fltNo" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">航班起飞时间&nbsp;<font color="red">*</font>(HH:MM)</td>
				<td>&nbsp;<input id="takeoffTime" type="text" name="takeoffTime" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr id="board">
				<td align="center">航班到达时间&nbsp;<font color="red">*</font>(HH:MM)</td>
				<td>&nbsp;<input id="arrivalTime" type="text" name="arrivalTime" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">始发城市&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="provenace" type="text" name="provenace" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr id="board">
				<td align="center">人数&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="peopleNum" type="text" name="peopleNum" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">目的城市&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="destination" type="text" name="destination" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<!-- +易登机-->
			<tr id="board_ydj">
				<td align="center">车牌号&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="licenseNumber" type="text" name="licenseNumber" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">是否托运行李&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="consignLuggage" type="text" name="consignLuggage" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectConsignLuggage"/></td>
			</tr>
			
			<!-- +礼宾车 -->
			<tr id="board_lbc">
				<td align="center">接送时间&nbsp;<font color="red">*</font>(HH:MM)</td>
				<td>&nbsp;<input id="pickTime" type="text" name="pickTime" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">接送地址&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="pickAddr" type="text" name="pickAddr" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" /></td>
			</tr>
			<tr id="board_lbc">
				<td align="center">车型&nbsp;<font color="red">*</font></td>
				<td colspan="3">&nbsp;<input id="carType" type="text" name="carType" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectCarType"/></td>
			</tr>
			
			<!-- 体检 PHYSICAL_EXAM-->
			<tr id="tj">
				<td align="center">体检人姓名&nbsp;<font color="red">*</font></td>
				<td colspan="3">&nbsp;<input id="clientName_tj" type="text" name="clientName_tj" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr id="tj">
				<td align="center">身份证号&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="idcarNo_tj" type="text" name="idcarNo_tj" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">性别&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="gender_tj" type="text" name="gender_tj" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectGender"/></td>
			</tr>
			<tr id="tj">
				<td align="center">体检日期&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="servDate_tj" type="text" name="servDate_tj" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">体检套餐&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="examType" type="text" name="examType" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectExamType"/></td>
			</tr>
			
			<!-- 挂号 HOSPITAL_REG-->
			<tr id="gh">
				<td align="center">挂号姓名&nbsp;<font color="red">*</font></td>
				<td colspan="3">&nbsp;<input id="clientName_gh" type="text" name="clientName_gh" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
			</tr>
			<tr id="gh">
				<td align="center">身份证号&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="idcarNo_gh" type="text" name="idcarNo_gh" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">性别&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="gender_gh" type="text" name="gender_gh" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectGender"/></td>
			</tr>
			<tr id="gh">
				<td align="center">就诊日期&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="servDate_gh" type="text" name="servDate_gh" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">预约医院&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="hospital" type="text" name="hospital" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectHospital"/></td>
			</tr>
			<tr id="gh">
				<td align="center">预约医科&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="medicalLabor" type="text" name="medicalLabor" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;"/></td>
				<td align="center">预约专科&nbsp;<font color="red">*</font></td>
				<td>&nbsp;<input id="docter" type="text" name="docter" value="" style="background-color: #E1E6E9;width:80%;height:22px;border:1px solid #A4BED4;" data-options="valueField:'id',textField:'text',data:selectMedicalLabor"/></td>
			</tr>
			<tr id="gh">
				<td align="center">病情描述&nbsp;</td>
				<td align="left" colspan="3">&nbsp;<input id="illnessDes" name="illnessDes" value="" style="background-color: #E1E6E9;width:80%;height:44px;border:1px solid #A4BED4;"/></td>
			</tr>
			
			
			<tr>
				<td align="center">备注&nbsp;</td>
				<td align="left" colspan="3">&nbsp;<input id="remark" name="remark" value="" style="background-color: #E1E6E9;width:80%;height:44px;border:1px solid #A4BED4;"/></td>
			</tr>
			
			
			
			
			<tr>
				<td align="center" >标的附件一&nbsp;</td>
				<td colspan="3">&nbsp;<div id="showFile1"></div></td>
			</tr>
			<tr>
				<td align="center">标的附件二&nbsp;</td>
				<td colspan="3">&nbsp;<div id="showFile2"></div></td>
			</tr>
			<tr>
				<td align="center">申请人&nbsp;</td>
				<td>&nbsp;<input id="userId" name="userId" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value="<%=userId%>"  /></td>
				<td align="center">申请机构&nbsp;</td>
				<td>&nbsp;<input id="userDepartmentName" name="userDepartmentName" type="text" readonly="readonly" required=true style="height:22px;border:1px solid #A4BED4;" value="<%=userDepartmentName%>"  /></td>
			</tr> 			
			
			<tr align="center">
				<td colspan="2">
					<a href="javascript:void(0)" id="confirmAdd1" onclick="doConfirmAdd1();" class="easyui-linkbutton">申请</a>
					<a href="javascript:void(0)" id="cancelAdd1" onclick="doCancelAdd1();" class="easyui-linkbutton">关闭</a>
				</td>
			</tr>
		</table>
		</form> 			
	</div>
	
  </body>
</html>
