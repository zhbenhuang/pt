<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>理财产品合同信息管理</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link href="css/uploadify.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link type="text/css" rel="stylesheet" href="css/dev.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/vobCombox.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
	<script type="text/javascript" src="js/swfobject.js"></script>
	<script type="text/javascript" src="js/jquery.uploadify.v2.0.1.js"></script>
	<script type="text/javascript">
	$(function(){
		var flag ;		
		$.ajaxSettings.traditional=true;  
		$('#status').combobox({
			editable:false,
			panelHeight:'auto'
		});
		$('#handStatus').combobox({
			editable:false,
			panelHeight:'auto'
		});
		$('#productType').combobox({
			editable:false,
			panelHeight:'auto'
		});
		$('#t_contract').datagrid({
			title:'合同信息列表' ,
			fit:true ,
			height:450 ,
			url:'contractAction!getContractList.action' ,
			fitColumns:false ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			nowrap: false,
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			frozenColumns:[[
				{field:'ck' ,width:50 ,checkbox: true},
				{field:'contractInfoStatus' ,title:'维护状态' ,width:80,
					formatter:function(value , record , index){
						if(value == "合同信息不全"){
							return '<span style=color:red; >合同信息不全</span>' ;
						}else if(value == "合同信息完整"){
							return '<span style=color:black; >合同信息完整</span>' ;
						}
					}}
			]],
			columns:[[
				{field:'contractId' ,title:'合同号' ,hidden: true},
				{field:'customeId' ,title:'客户号' ,width:100,formatter:function(value,row,index){
       				return '<a href="javascript:void(0)" onclick="viewContract(\'' + row.contractId+ '\')">'+row.customeId+'</a>'}},
				{field:'customeName' ,title:'客户姓名' ,width:80},
				{field:'saleDate' ,title:'销售日期' ,width:100},
				{field:'productCode' ,title:'产品编码' ,width:100},
				{field:'productName' ,title:'产品名称' ,width:150},
				{field:'money' ,title:'金额  (万)' ,width:70},
				{field:'signAccount' ,title:'签约帐号' ,width:100},
				{field:'saleManager' ,title:'销售经理' ,width:100},
				{field:'businessManager' ,title:'理财经理' ,width:100},
             	{field:'belongDepartment' ,title:'归属机构' ,width:120},
              	{field:'belongDepartmentId' ,title:'归属机构Id' ,hidden: true},
              	{field:'signDepartment' ,title:'签约机构' ,width:120},
                {field:'signDepartmentId' ,title:'签约机构' ,hidden: true},
              	{field:'addDate' ,title:'添加日期' ,width:100},
              	{field:'modifyDate' ,title:'修改日期' ,width:100},
              	{field:'operatorName' ,title:'维护人' ,width:100},
              	{field:'handStatus' ,title:'合同移交状态' ,width:100}
			]] ,
			pagination: true , 
			pageSize: 10,
			pageList:[10,20,50] ,
			toolbar:[
				{
					text:'手工录入合同' ,
					iconCls:'icon-my-add' , 
					handler:function(){
						flag = 'add';
						$('#mydialog').dialog({title:'录入合同'});
						$('#myform').get(0).reset();
						$('#mydialog').dialog('open');
					}
				},{
					text:'系统导入合同' ,
					iconCls:'icon-my-document_into' , 
					handler:function(){
						$('#IntoDialog').dialog({title:'导入合同'});
						$('#IntoDialog').dialog('open');
					}
				},{
					text:'修改合同' ,
					iconCls:'icon-my-edit' , 
					handler:function(){
						flag = 'edit';
						var arr =$('#t_contract').datagrid('getSelections');
						if(arr.length != 1){							
							LG.showWarn("提示信息","只能选择一行记录进行修改!");
						} else {
							$('#mydialog').dialog({
								title:'修改合同'
							});
							$('#mydialog').dialog('open'); //打开窗口
							$('#myform').get(0).reset();   //清空表单数据 
							$('#myform').form('load',{	//调用load方法把所选中的数据load到表单中,非常方便
								contractId:arr[0].contractId,
								customeId:arr[0].customeId ,
								customeName:arr[0].customeName ,
								saleDate:arr[0].saleDate ,
								productType:arr[0].productType,
								productCode:arr[0].productCode ,
								productName:arr[0].productName,
								money:arr[0].money,
								signAccount:arr[0].signAccount,
								saleManager:arr[0].saleManager,
								businessManager:arr[0].businessManager,
								belongDepartment:arr[0].belongDepartment,
								belongDepartmentId:arr[0].belongDepartmentId,
								signDepartment:arr[0].signDepartment,
								signDepartmentId:arr[0].signDepartmentId
							});
						}
					}
				},{
					text:'删除合同' ,
					iconCls:'icon-my-delete' , 
					handler:function(){
						var arr =$('#t_contract').datagrid('getSelections');
						var contractIds = new Array();
						if(arr.length <=0){							
							LG.showWarn("提示信息","至少选择一行记录进行删除!");
						} else {
							$.messager.confirm('提示信息' , '确认删除?' , function(r){
								if(r){
									for(var i =0 ;i<arr.length;i++){
										contractIds[i] = arr[i].contractId;
									}
									$.ajax({
										type: 'post',
										url: 'contractAction!deleteContract.action',
										cache:false ,
										data:{
											"contractIds":contractIds
										},
										dataType:'json' ,
										success:function(result){
											$.messager.alert('提示信息',result.message,'info',function(){
												//1 刷新数据表格 
												$('#t_contract').datagrid('reload');
												//2 清空idField   
												$('#t_contract').datagrid('unselectAll');
											});
										}
									});
								}
							});
						}								
					}
				}
				,{
					text:'下载合同导入模板示例' ,							
					iconCls:'icon-my-download' , 
					handler:function(){
						window.location.href="downloadInstance.action";
					}						
				}
			]
		});
			
		/**
		 *  提交表单方法
		 */
		$('#btn1').click(function(){
			if($('#myform').form('validate')){
				$.ajax({
					type: 'post' ,
					url: flag=='add'?'contractAction!saveContract.action':'contractAction!updateContract.action',
				    cache:false ,
					data:$('#myform').serialize() ,
					dataType:'json' ,
					success:function(result){
						if(result.retCode=='A000000'){
							$.messager.alert('提示信息',result.message,'info',function(){
								//1 关闭窗口
								$('#mydialog').dialog('close');
								//2刷新datagrid 
								$('#t_contract').datagrid('reload');
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
			
			
		$("#uploadify").uploadify({
			'uploader'       : 'js/uploadify.swf',
			'script'         : 'contractAction!inputContract.action',
			'cancelImg'      : 'img/cancel.png',
			'folder'         : 'uploads',
			'queueID'        : 'fileQueue',
			'fileExt'		 : '*.xls;*.xlsx',
			'fileDesc'		 : '文件格式必须为:.xls和.xlsx',
			'auto'           : false,
			'multi'          : false,
			'simUploadLimit' : 1,
			'buttonText'	 : 'BROWSE',
			onComplete		 : function(){$.messager.alert('提示消息','请排查各个合同资料是否完整!','info');}
		});
			
		/**
		 * 关闭窗口方法
		 */
		$('#btn2').click(function(){
			$('#mydialog').dialog('close');
		});
			
		$('#searchbtn').click(function(){
			$('#t_contract').datagrid('load' ,serializeForm($('#mysearch')));
		});
		$('#clearbtn').click(function(){
			$('#mysearch').form('clear');
			$('#t_contract').datagrid('load' ,{});
		});
			
		$('#selectedProduct').html("");
			
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
	    $('#addDateBegin').datebox({
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
            }
	    });
	    $('#addDateEnd').datebox({
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
	    $('#saleDate').datebox({
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
            }
	    });
	    //扩展easyui表单的验证
		$.extend($.fn.validatebox.defaults.rules, {
			account: { 
        		validator: function (value) {
            		var reg = /^[0-9]\d{15}$/;
            		return reg.test(value);
        		},
        		message: '签约账号必须是16位数字.'
    		}
		});
	});
	
	function viewContract(contractId){
		$('#mydialogContract').dialog('open').dialog("refresh","funcMng/contractView.jsp?contractId='"+contractId+"'");
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
	function closeDialog(){
		$('#IntoDialog').dialog('close');
		$('#t_contract').datagrid('reload');
	}
	function selectProCode(){
		$('#proCodeDialog').dialog('open').dialog("refresh","funcMng/selectProduct.jsp");
	}
	function selectBeDepart(){
		$('#belongDepartmentDialog').dialog('open').dialog("refresh","funcMng/selectBelongDepartment.jsp");
	}
	function selectSignDepart(){
		$('#signDepartmentDialog').dialog('open').dialog("refresh","funcMng/selectSignDepartment.jsp");
	}
</script>
</head>
 <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
		<div region="north" title="合同信息查询" style="height:150%;overflow:hidden" >
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;客&nbsp;户&nbsp;号&nbsp;:&nbsp;<input name="customeId" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;签约机构:&nbsp;<input name="signDepartment" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="35%">&nbsp;开&nbsp;放&nbsp;&nbsp;日:&nbsp;<input name="openDayBegin" id="openDayBegin" class="easyui-databox" value="" style="width:100%"/>&nbsp;到&nbsp;<input name="openDayEnd" id="openDayEnd" class="easyui-databox" value="" style="width:100%"/></td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;客&nbsp;户&nbsp;名&nbsp;:&nbsp;<input name="customeName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;归属机构:&nbsp;<input name="belongDepartment" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="35%">&nbsp;赎回区间:&nbsp;<input name="redeemBegin" id="redeemBegin" class="easyui-databox" value="" style="width:100%"/>&nbsp;到&nbsp;<input name="redeemEnd" id="redeemEnd" class="easyui-databox" value="" style="width:100%"/></td>
					</tr>							
				</table>
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;产品编码:&nbsp;<input name="productCode"  class="easyui-validatebox" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" class="easyui-validatebox"  value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="35%">&nbsp;添加日期:&nbsp;<input name="addDateBegin" id="addDateBegin" class="easyui-databox" value="" style="width:100%"/>&nbsp;到&nbsp;<input name="addDateEnd" id="addDateEnd" class="easyui-databox" value="" style="width:100%"/></td>
					</tr>							
				</table>
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;维护状态:
		    				<select id="status" name="contractInfoStatus" style="width:100%;">
		    					<option value=""></option>
		    					<option value="合同信息完整">合同信息完整</option>
		    					<option value="合同信息不全">合同信息不全</option>
		    				</select>
	    				</td>
	    				<td width="20%">&nbsp;移交状态:&nbsp;
		    				<select id="handStatus" class="" name="handStatus" style="width:100%;">
		    					<option value=""></option>
		    					<option value="录入未移交">录入未移交</option>
		    					<option value="合同已移交">合同已移交</option>
		    					<option value="合同已返回">合同已返回</option>
		    					<option value="合同已领取">合同已领取</option>
		    				</select>
	    				</td>	
						<td width="15%" align="left"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">重置</a></td>
					</tr>
				</table>
			</form>
		
		</div>
		<div region="center" >
			<table id="t_contract"></table>
		</div>
	</div>
		
	<div id="mydialog" title="录入合同信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:500px">
  		<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
	   		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:30%;overflow:hidden">
				<table>
					<tr>
						<td colspan="2">
							<a id="btn1" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
							<a id="btn2" href="javascript:void(0)" class="easyui-linkbutton">关闭</a>
						</td>
					</tr> 
	 			</table>  					 					    					    					    					    					    					    					    					
	   		</div>
   			<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
    		<br/>
  				<form id="myform" action="" method="post">
	  				<input type="hidden" id="contractId" name="contractId" value="" />
	  				<div class="title" align="center" ><table><tr><td><h4>理财合同信息表（<font style="color:red">*</font>必填）</h4></td></tr></table></div>
	  				<table width="90%" border="1" align="center" style="background-color:#E4F5EF">
	  					<tr>
	  						<td width="20%" align="center">客户号&nbsp;<font color="red">*</font></td>
	  						<td width="25%" ><input type="text" name="customeId" class="easyui-validatebox" required=true missingMessage="客户号不能为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  						<td width="20%" align="center">客户姓名&nbsp;<font color="red">*</font></td>
	  						<td width="25%"><input name="customeName" class="easyui-validatebox" required=true missingMessage="客户姓名不能为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  					</tr>
	  					<tr>
	  						<td align="center">销售日期&nbsp;<font color="red">*</font></td>
	  						<td><input type="text" id="saleDate" name="saleDate" class="easyui-databox" required=true missingMessage="销售日期不能为空" value="" style="width:200%;height:22px;border:1px solid #A4BED4;"/></td>
	  						<td align="center">产品类型&nbsp;<font color="red">*</font></td>
	  						<td><select id="productType" class="easyui-combobox" name="productType" required=true missingMessage="必选一项" style="width:200%;height:22px;border:1px solid #A4BED4;">
								<option value="私人银行产品">私人银行产品</option>
							</select>
	  						</td>
	  					</tr>
	  					<tr>
	  						<td align="center">产品编码&nbsp;<font color="red">*</font></td>
	  						<td><input type="text" id="productCode" name="productCode" class="easyui-validatebox" missingMessage="产品编码必选" readonly="readonly" style="background-color: #E1E6E9" required=true value="" style="width:70%;height:22px;border:1px solid #A4BED4;"/>
	  							<a href="javascript:void(0)" onclick="selectProCode()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" ><font size="1">选择</font></a>
	  						</td>
	  						<td align="center">产品名称&nbsp;<font color="red">*</font></td>
	  						<td><input id="productName" type="text" name="productName" class="easyui-validatebox" missingMessage="产品名称必选" required=true value="" readonly="readonly" style="background-color: #E1E6E9;width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  					</tr>
	  					<tr>
	  						<td align="center">金额  (万)&nbsp;<font color="red">*</font></td>
	  						<td><input id="money" type="text" name="money" class="easyui-numberbox" required=true min="0" precision="2" missingMessage="金额不可为空,且不能为负数" invalidMessage="金额不可为负数！" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  						<td align="center">签约帐号&nbsp;<font color="red">*</font></td>
	  						<td><input id="signAccount" type="text" name="signAccount" class="easyui-validatebox" required=true missingMessage="签约帐号不可为空" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  					</tr>
	  					<tr>
	  						<td align="center">销售经理&nbsp;</td>
	  						<td><input id="saleManager" type="text" name="saleManager" class="easyui-validatebox" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  						<td align="center">理财经理&nbsp;</td>
	  						<td><input id="businessManager" type="text" name="businessManager" class="easyui-validatebox" value="" style="width:100%;height:22px;border:1px solid #A4BED4;"/></td>
	  					</tr>
	  					<tr>
	  						<td align="center">归属机构&nbsp;<font color="red">*</font></td>
	  						<td><input id="belongDepartment" type="text" name="belongDepartment" class="easyui-validatebox" missingMessage="归属机构必选" required=true value="" readonly="readonly" style="background-color: #E1E6E9;width:70%;height:22px;border:1px solid #A4BED4;"/>
	  							<a href="javascript:void(0)" onclick="selectBeDepart()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" ><font size="1">选择</font></a>
	  							<input id="belongDepartmentId" type="hidden" name="belongDepartmentId"/>
	  						</td>
	  						<td align="center">签约机构&nbsp;<font color="red">*</font></td>
	  						<td><input id="signDepartment" type="text" name="signDepartment" class="easyui-validatebox" missingMessage="签约机构必选" required=true value="" readonly="readonly" style="background-color: #E1E6E9;width:70%;height:22px;border:1px solid #A4BED4;"/>
	  							<a href="javascript:void(0)" onclick="selectSignDepart()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" ><font size="1">选择</font></a>
	  							<input id="signDepartmentId" type="hidden" name="signDepartmentId"/>
	  						</td>
	  					</tr>			 					    					    					    					    					    					    					    					
	  				</table>
  				</form> 			
			</div>
		</div>
	</div>
	<div id="proCodeDialog" title="选择产品" modal=false draggable=true class="easyui-dialog" closed=true style="width:700px;height:450px;overflow:hidden">
	</div>
	<div id="belongDepartmentDialog" title="选择归属机构" modal=false draggable=true class="easyui-dialog" closed=true style="width:700px;height:450px;overflow:hidden">
	</div>
	<div id="signDepartmentDialog" title="选择签约机构" modal=false draggable=true class="easyui-dialog" closed=true style="width:700px;height:450px;overflow:hidden">
	</div>
	<div id="mydialogContract" title="查看合同信息" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:300px">
	</div>
	<div id="IntoDialog" title="导入合同" modal=true draggable=true class="easyui-dialog" closed=true style="width:400px;">
   		<table>
   			<tr><td><font color="red">注：1、Excel文件格式必须为:.xls和.xlsx</font></td></tr>
   			<tr><td><font color="red">&nbsp;&nbsp;&nbsp;2、上传前剔除Excel表头</font></td></tr>
   		</table>
   		<div id="fileQueue"></div>
		<input type="file" name="uploadify" id="uploadify" />
		<p>
		<a id="confirmAdd" class="easyui-linkbutton" href="javascript:jQuery('#uploadify').uploadifyUpload()">开始上传</a>
		<a id="cancelAdd" class="easyui-linkbutton" href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消上传</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">关闭</a>
		</p>
	</div>
</body>
</html>
