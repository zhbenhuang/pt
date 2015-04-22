<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>通知信息列表</title>
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
	<script type="text/javascript">
			$(function(){
				var currentPage = 'noticeViewStatus';
				var flag ;
				$.ajaxSettings.traditional=true;
				$('#noticeType').combobox({
					editable:false,
					panelHeight:'auto'
				});
				$('#noticeViewStatus').combobox({
					editable:false,
					panelHeight:'auto'
				});
				/**
				 *	初始化数据表格  
				 */
				$('#t_noticeList').datagrid({
					title:'通知列表' ,
					fit:true ,
					height:450 ,
					url:'contractAction!getNoticesViewList.action',
					fitColumns:true ,  
					striped: true ,					
					loadMsg: '数据正在加载,请耐心的等待...' ,
					rownumbers:true ,
					onLoadSuccess: function (result){
						LG.showMsg("查询结果提示",result.retCode,result.message,false);
					},
					onLoadError: function (xhr,data){
						showErrorMessage(xhr);
					},
					frozenColumns:[[
						{
							field:'ck' ,width:50 ,checkbox: true
						}
					]],
					columns:[[
						{field:'noticeId' ,title:'通知流水号' ,hidden:true},
						{field:'noticeType' ,title:'通知类型' ,width:100 ,align:'center'},
						{field:'noticeTitle' ,title:'通知标题' ,width:150},
						{field:'noticeArriveTime' ,title:'通知时间' ,width:80},
						{field:'noticeViewStatus',title:'查阅状态',width:80},
						{field:'noticeViewTime',title:'查阅时间',width:100},
						{field:'belongDepartment',title:'查阅机构',width:80}
					]] ,
					pagination: true , 
					pageSize: 10 ,
					pageList:[10,20,50]
				});
				/**
				 * 下面用于信息查询
				 */
				$('#searchbtn').click(function(){
					$('#t_noticeList').datagrid('load' ,serializeForm($('#mysearch')));
				});
					
				$('#clearbtn').click(function(){
					$('#mysearch').form('clear');
					$('#t_noticeList').datagrid('load' ,{});
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
			
			/**
			 * 转换dateBox的格式
			 * @return {TypeName} 
			 */
			$(function(){
			    $('#startTime1').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#completeTime1').datebox({
		           formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			        });
			    $('#arriveDateBegin').datebox({
		            formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			    $('#arriveDateEnd').datebox({
		           formatter: function (date) {
		                var y = date.getFullYear();
		                var m = date.getMonth() + 1;
		                var d = date.getDate();
		                return y.toString() + (m < 10 ? ("0" + m.toString()) : m.toString()) + (d < 10 ? ("0" + d.toString()) : d.toString());
		            }
			    });
			});

	</script>
  </head>
  <body style="overflow:hidden">
	<div id="lay" class="easyui-layout" style="width:100%;height:100%">
		<div region="north" title="通知查询" style="height:60%;overflow:hidden" >
			<form id="mysearch" method="post">
				<table width="100%">
					<tr>
						<td width="20%">&nbsp;通知类型:&nbsp;
							<select id="noticeType" class="easyui-combobox" name="noticeType" style="width:100%;height:22px;border:1px solid #A4BED4;">
								<option value=""></option>
								<option value="客户已赎回">客户已赎回</option>
								<option value="合同即将逾期">合同即将逾期</option>
								<option value="领取合同通知">领取合同通知</option>
								<option value="理财产品到期">理财产品到期</option>
								<option value="产品进入赎回期">产品进入赎回期</option>
							</select>
						</td>
						<td width="20%">&nbsp;查阅状态:&nbsp;
							<select id="noticeViewStatus" class="easyui-combobox" name="noticeViewStatus" style="width:100%;height:22px;border:1px solid #A4BED4;">
								<option value=""></option>
								<option value="未查阅">未查阅</option>
								<option value="已查阅">已查阅</option>
							</select>
						</td>
						<td width="40%">&nbsp;通知时间:&nbsp;<input name="arriveDateBegin" id="arriveDateBegin" class="easyui-datebox" value="" style="width:120%;"/>&nbsp;至&nbsp;<input name="arriveDateEnd" id="arriveDateEnd" class="easyui-datebox" value="" style="width:120%;"/></td>
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="center" >
			<table id="t_noticeList"></table>
		</div>
	</div>
  </body>
</html>
