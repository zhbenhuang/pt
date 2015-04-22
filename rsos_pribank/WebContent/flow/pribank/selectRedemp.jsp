<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String productCode = request.getParameter("productCode");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>选择赎回区间</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<link type="text/css" rel="stylesheet" href="css/dev.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/commons.js"></script>
	<script type="text/javascript" src="js/LG.js"></script>
</head>
<body style="overflow:hidden">
	<script type="text/javascript">
		var productCode = <%=productCode%>;
		var paramQuery = {'productCode':productCode};
		$(function(){
			$('#selectedRedemp').html("");			//清空已选项
    		var str= "";
    		var redempBegin = $('#redempBegin').attr('value');
			var redempEnd = $('#redempEnd').attr('value');
			if(redempBegin!=''){					//产品编码内部有值，点击选择进行选择时需要显示,所以有if{}内部的代码
				str += "<tr><td><input type='checkbox' checked='checked'/>"+redempBegin+"</td>";
				str += "<td>"+redempEnd+"</td></tr>";
				$('#selectedRedemp').append(str);
			}
	    	$('#redempList').datagrid({
	    		 fit:true ,
				 height:450 ,
	             url: 'productAction!getRedemptions.action',
	             queryParams:paramQuery,
	             loadMsg: '数据正在加载,请耐心的等待...' ,
	             fitColumns:true ,  
				 striped: true ,					
	             rownumbers:true ,					
	             pagination: true ,
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
				 pageList:[10,20,50],
				 toolbar:[
					 {
						 text:'选择',
						 iconCls:'icon-my-add',
						 handler:function(){
						 	var trNum = $('#selectedRedemp tr').size();	//判断已经选择了几个选项,selectedProduct关联的table下面有多少个tr
						 	if(trNum===1){
						 		LG.showWarn("提示消息","已存在一个选项!");
						 	}else if(trNum===0){									//如果没有选项，则可选择一个
						 		var str= "";
							 	var arr = $('#redempList').datagrid('getSelections');
							 	var redempBegin = "";
							 	var redempEnd = "";
							 	var redempIntervalId = "";
							 	if(arr.length <=0){
							 		LG.showWarn("提示消息","请至少选择一项!");
							 	}else if(arr.length != 1){
							 		LG.showWarn("提示消息","只能选择一项!");
							 	}else{
							 		redempBegin = arr[0].redeemBegin;
							 		redempEnd = arr[0].redeemEnd;
							 		redempIntervalId = arr[0].redemptionIntervalId;
							 		str += "<tr><td><input type='checkbox' checked='checked'/>"+redempBegin+"</td>";
							 		str += "<td>"+redempEnd+"</td>";
							 		str += "<td style='display:none'>"+redempIntervalId+"</td></tr>";
							 		$('#selectedRedemp').append(str);
							 	}
						 	}
						 }
					 }
				 ],
				 columns:[[
					 {field:'redemptionIntervalId',title:'赎回区间ID',hidden: true},
					 {field:'openDay',title:'开放日',width:150,align:'center'},
					 {field:'redeemBegin',title:'赎回开始时间',width:150,align:'center'},
					 {field:'redeemEnd',title:'赎回结束时间',width:150,align:'center'},
					 {field:'rollBenefit',title:'滚动期收益',width:150,align:'center'},
					 {field:'redemptionStatus',title:'赎回期状态',width:150,align:'center'}
				 ]]
			});
			
		/**
	     * 将选择产品编码选中的保存或清除
	     */
	     /**
	      * 下面是保存按钮
	      * @memberOf {TypeName} 
	      */
	    $("#saveRedemp").click(function(){
	    	var arrRedempBegin = [];
	    	var arrRedempEnd = [];
	    	var arrRedempId = [];
	    	if($('#selectedRedemp tr').size()===0){			//判断table里面是否包含tr
	    		LG.showWarn("提示消息","已选项为空,请添加选项!");
	    	}else{
	    		$('#selectedRedemp tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
	    			if($(this).children("td").eq(0).find("input:checkbox").prop("checked")){
	    				 arrRedempBegin[arrRedempBegin.length] = $(this).children("td").eq(0).text();
	    				 arrRedempEnd[arrRedempEnd.length] = $(this).children("td").eq(1).text();
	    				 arrRedempId[arrRedempId.length] = $(this).children("td").eq(2).text();
	    			}
	    		});
	    		var arrRedempBeginNum = arrRedempBegin.length;
	    		var redempBegin = "";
	    		var redempEnd = "";
	    		var redempId = "";
	    		if(arrRedempBeginNum>0){
	    			for(var i=0;i<arrRedempBeginNum;i++){
	    				redempBegin += arrRedempBegin[i]+',';
	    				redempEnd += arrRedempEnd[i]+',';
	    				redempId += arrRedempId[i]+',';
	    			}
	    			redempBegin = redempBegin.substring(0,redempBegin.length-1);
	    			redempEnd = redempEnd.substring(0,redempEnd.length-1);
	    			redempId = redempId.substring(0,redempId.length-1);
	    			$('#redempBegin').val(redempBegin);
	    			$('#redempEnd').val(redempEnd);
	    			$('#redemptionIntervalId').val(redempId);
	    			$('#redempDialog').dialog('close');		//关闭选择窗口
	    		}else{
	    			LG.showWarn("提示消息","未勾选需保存的选项!");
	    		}
	    	}
	    });
	      /**
	       * 移除已选项中选中的项
	       * @memberOf {TypeName} 
	       */
	    $("#removeRedemp").click(function(){
	    	var i = 0;
		    if($('#selectedRedemp tr').size()===0){			//判断table里面是否包含tr
		    	$.messager.alert('提示消息','已选项为空!','确定');
		    }else{
		    	$('#selectedRedemp tr').each(function(){	//如果包含tr,开始遍历每个tr内部每个td的内容
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
	});
	</script>
	<div id="lay" class="easyui-layout" style="width:100%;height:100%" >
		<div region="east" title="已选项" split="true" style="background-color:#E4F5EF;width:160px;height:35%">
			<div style="height:70%">
				<table id="selectedRedemp">
				</table>
			</div>
			<hr>
			<div>
				<table>
					<tr>
						<td colspan="2">
							<a href="javascript:void(0)" id="saveRedemp" class="easyui-linkbutton">保存</a>
							<a href="javascript:void(0)" id="removeRedemp" class="easyui-linkbutton">移除</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div region="center" style="overflow:auto;background-color:#E4F5EF;width:100%;height:100%">
			<table id="redempList" title="产品赎回区间列表" class="easyui-datagrid" align="center" border="1" style="height:auto;background-color:#E4F5EF">
			</table>
		</div>
	</div>
  </body>
</html>
