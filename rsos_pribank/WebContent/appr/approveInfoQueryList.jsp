<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Integer business = (Integer)session.getAttribute("BUSINESS");
	System.out.println("business="+business);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">    
    <title>审批记录查询</title>    
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
		
		/**
		 *	初始化数据表格  
		 */
		$('#t_approveInfo').datagrid({
			title:'审批记录列表' ,
			fit:true ,
			height:450 ,
			url:'queryApproveInfoList!queryApproveInfoList.action' ,
			fitColumns:true ,  
			striped: true ,
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			onLoadSuccess: function (data){					
				LG.showMsg("查询结果提示",data.retCode,data.message,false);
			},
			onLoadError: function (xhr,data){
				showErrorMessage(xhr);
			},
			columns:[[
				{field:'apprId' ,title:'审批流水号' ,width:20 ,align:'center'},
				{
					field:'apprType' ,
					title:'审批类型' ,
					width:50 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 1){
							return '<span style=color:black; >用户变更审批</span>' ;
						}else if(value == 2){
							return '<span style=color:green; >客户变更审批</span>' ;
						}else if(value == 3){
							return '<span style=color:green; >机构变更审批</span>' ;
						}else if(value == 4){
							return '<span style=color:green; >活动创建审批</span>' ;
						}else if(value == 5){
							return '<span style=color:green; >活动报名审批</span>' ;
						}
					}
				},
				{
					field:'apprStatus' ,
					title:'审批状态' ,
					width:30 ,
					align:'center',
					formatter:function(value , record , index){
						if(value == 1){
							return '<span style=color:black; >审批中</span>' ;
						}else if(value == 2){
							return '<span style=color:green; >审批通过</span>' ;
						}else if(value == 3){
							return '<span style=color:green; >审批不通过</span>' ;
						}else if(value == 4){
							return '<span style=color:green; >终止</span>' ;
						}
					}
				},
				{field:'applyNo' ,title:'申请单编号' ,width:30 ,align:'center'},
				{field:'applyUserId' ,title:'申请人' ,width:30 ,align:'center'},
				{field:'curUserId' ,title:'当前处理人' ,width:30 ,align:'center'},
				{field:'applyTime' ,title:'申请时间' ,width:50 ,align:'center'},

			]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[10,20,50] ,
			toolbar:[
			       	 
			]
		});
		
		$('#searchbtn').click(function(){
			$('#t_approveInfo').datagrid('load' ,serializeForm($('#mysearch')));
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
		<div region="north" style="width: 100%;height:65%" >
		<br/>
		<form id="mysearch" method="post">  
			&nbsp;审批流水号:&nbsp;<input name="apprId" type="text" size="10" class="input-style"/>
			&nbsp;审批类型:&nbsp;<input name="apprType" type="text" size="15" class="input-style"/>
			&nbsp;审批状态:&nbsp;<input name="apprStatus" type="text" size="15" class="input-style"/>
			&nbsp;申请人ID:&nbsp;<input name="applyUserId" type="text" size="15" class="input-style"/>
			&nbsp;开始日期:&nbsp;<input name="applyTimeBeg" type="text" size="15" class="input-style"/>
			&nbsp;结束日期:&nbsp;<input name="applyTimeEnd" type="text" size="15" class="input-style"/>
			&nbsp;<a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a>
			</form>
		</div>
		<div region="center" style="width: 100%;height:100%">
			<table id="t_approveInfo" style="width: 100%;height:100%"></table>
		</div>
	</div>
  </body>
</html>
