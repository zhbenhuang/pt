<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>selectProduct</title>
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
	    	$('#selectedProduct').html("");			//清空已选项
	    	var str= "";
	    	var productCode = $('#productCode').attr('value');
			var productName = $('#productName').attr('value');
			if(productCode!=''){					//产品编码内部有值，点击选择进行选择时需要显示,所以有if{}内部的代码
				str += "<tr><td><input type='checkbox' checked='checked'/>"+productCode+"</td>";
				str += "<td style='display:none'>"+productName+"</td></tr>";
				$('#selectedProduct').append(str);
			}
	    	$('#productList').datagrid({
	    		 fit:true ,
				 height:450 ,
	             url: 'productAction!getProductList.action',
	             method: 'post',
	             loadMsg: '数据正在加载,请耐心的等待...' ,
	             fitColumns:true ,  
				 striped: true ,					//隔行变色特性
	             rownumbers:true ,					//显示行号
	             pagination: true , 				//显示分页
	             onLoadSuccess: function (data){					
					LG.showMsg("查询结果提示",data.retCode,data.message,false);
				 },
				 onLoadError: function (xhr,data){
					showErrorMessage(xhr);
				 },
	             frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
					{field:'ck' ,width:50 ,checkbox: true}
				 ]],
				 pageSize: 10,
				 pageList:[10,15,20,50],
				 toolbar:[
					 {
						 text:'选择',
						 iconCls:'icon-add',
						 handler:function(){
						 	var trNum = $('#selectedProduct tr').size();	//判断已经选择了几个选项,selectedProduct关联的table下面有多少个tr
						 	if(trNum===1){
						 		LG.showWarn("提示消息","已存在一个选项!");
						 	}else if(trNum===0){									//如果没有选项，则可选择一个
						 		var str= "";
							 	var arr = $('#productList').datagrid('getSelections');
							 	var productCode = "";
							 	var productName = "";
							 	if(arr.length <=0){
							 		LG.showWarn("提示消息","请至少选择一项!");
							 	}else if(arr.length != 1){
							 		LG.showWarn("提示消息","只能选择一项!");
							 	}else{
							 		productCode = arr[0].productCode;
							 		productName = arr[0].productName;
							 		str += "<tr><td><input type='checkbox' checked='checked'/>"+productCode+"</td>";
							 		str += "<td style='display:none'>"+productName+"</td></tr>";
							 		$('#selectedProduct').append(str);
							 	}
						 	}
						 }
					 }
				 ],
				 columns:[[
					 {
						field:'productId',title:'产品Id',width:150,hidden:true
					 },{
						field:'productCode',title:'产品编号',width:150,align:'center' 
					 },{
						field:'productName',title:'产品名称',width:150,align:'center'
					 },{
						field:'benefitDate',title:'起息日',width:150,align:'center'
					 },{
					    field:'dueDate',title:'到期日',width:150,align:'center'
					 },{
					    field:'plannedBenefit',title:'预期收益',width:150,align:'center'
					 },{
					    field:'isRoll',title:'是否滚动',width:150,align:'center'
					 }
				 ]]
			});
		    
		    /**
		     * 将选择产品编码选中的保存或清除
		     */
		     /**
		      * 下面是保存按钮
		      * @memberOf {TypeName} 
		      */
		    $("#saveProCode").click(function(){
		    	var arrProCode = [];
		    	var arrProName = [];
		    	if($('#selectedProduct tr').size()===0){			//判断table里面是否包含tr
		    		LG.showWarn("提示消息","已选项为空，请添加选项!");
		    	}else{
		    		$('#selectedProduct tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
		    			if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
		    				 arrProCode[arrProCode.length] = $(this).children("td").eq(0).text();
		    				 arrProName[arrProName.length] = $(this).children("td").eq(1).text();
		    			}
		    		});
		    		var arrProCodeNum = arrProCode.length;
		    		var proCode = "";
		    		var proName = "";
		    		if(arrProCodeNum>0){
		    			for(var i=0;i<arrProCodeNum;i++){
		    				proCode += arrProCode[i]+',';
		    				proName += arrProName[i]+',';
		    			}
		    			proCode = proCode.substring(0,proCode.length-1);
		    			proName = proName.substring(0,proName.length-1);
		    			$('#productCode').val(proCode);
		    			$('#productName').val(proName);
		    			$('#proCodeDialog').dialog('close');		//关闭选择窗口
		    		}else{
		    			LG.showWarn("提示消息","未勾选需保存的选项!");
		    		}
		    	}
		    });
		      /**
		       * 移除已选项中选中的项
		       * @memberOf {TypeName} 
		       */
		    $("#removeProCode").click(function(){
		    	var i = 0;
			    if($('#selectedProduct tr').size()===0){			//判断table里面是否包含tr
			    	LG.showWarn("提示消息","已选项为空!");
			    }else{
			    	$('#selectedProduct tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
			    		if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
			    			$(this).remove();
			    			i++;
			    		}
			    	});
			    	if(i===0){
			    		LG.showWarn("提示消息","未勾选需移除的选项!");
			    	}
				}
		    });
		     
		    $('#searchProbtn').click(function(){
				$('#productList').datagrid('load' ,serializeForm($('#mysearchPro')));
			});
			
			$('#clearProbtn').click(function(){
				$('#mysearchPro').form('clear');
				$('#productList').datagrid('load' ,{});
			});
 		});
 	</script>
 	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
		<div region="north" align="center" style="background-color:#E4F5EF;width:100%;height:35%;overflow:hidden">
			<form id="mysearchPro" method="post">
				<table width="100%">
					<tr>
						<td width="15%">&nbsp;产品编码:&nbsp;<input name="productCode" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="20%">&nbsp;产品名称:&nbsp;<input name="productName" value="" style="width:60%;height:22px;border:1px solid #A4BED4;"/></td>
						<td width="15%" align="left"><a id="searchProbtn" href="javascript:void(0)" class="easyui-linkbutton">查询</a> <a id="clearProbtn" href="javascript:void(0)" class="easyui-linkbutton">重置</a></td>
					</tr>						
				</table>
			</form>
		</div>
		<div region="east" title="已选项" split="true" style="background-color:#E4F5EF;width:160px;height:35%">
			<div style="height:70%">
				<table id="selectedProduct">
				</table>
			</div>
			<hr>
			<div>
				<table>
					<tr>
						<td colspan="2">
							<a href="javascript:void(0)" id="saveProCode" class="easyui-linkbutton">保存</a>
							<a href="javascript:void(0)" id="removeProCode" class="easyui-linkbutton">移除</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
			<table id="productList" title="产品信息列表" class="easyui-datagrid" align="center" border="1" style="height:auto;background-color:#E4F5EF">
			</table>
		</div>
	</div>
 </body>
 </html>