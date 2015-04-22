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
				var currentPage = 'privateBankDep';
				var flag ;		//undefined 判断新增和修改方法
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
					url:'contractAction!getNoticesList.action',
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
						{field:'ck' ,width:50 ,checkbox: true}
					]],
					columns:[[
						{field:'noticeId' ,title:'通知流水号' ,hidden:true},
						{field:'noticeType' ,title:'通知类型' ,width:60},
						{field:'noticeTitle' ,title:'通知标题' ,width:100,formatter:function(value,row,index){
       							return '<a href="javascript:void(0)" onclick="viewNoticeHref(\'' + row.noticeId+ '\')">'+row.noticeTitle+'</a>'}},
						{field:'noticeArriveTime' ,title:'通知时间' ,width:100},
						{field:'noticeViewStatus',title:'查阅状态',width:60},
						{field:'noticeViewTime',title:'查阅时间',width:100},
						{field:'belongDepartment',title:'查阅机构',width:80},
						{field:'noticeDealStatus',title:'处理状态',width:60}
					]] ,
					pagination: true , 
					pageSize: 10 ,
					pageList:[10,20,50],
					toolbar:[
						{
							text:'查阅通知' ,
							iconCls:'icon-search',
							handler:viewNotice
						},{
							text:'处理通知' ,
							iconCls:'icon-my-edit',
							handler:dealNotice
						}
					]
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
			function viewNotice(){
				var arr =$('#t_noticeList').datagrid('getSelections');
				if(arr.length <=0){
					LG.showWarn("提示消息","请选择一个通知进行查阅!");
				}else if(arr.length != 1){
					LG.showWarn("提示消息","一次只能查阅一个通知!");
				}else{
					var noticeId = arr[0].noticeId;
					var params = {"noticeId":noticeId};
					$.ajax({
						type: 'post',
						url: 'contractAction!viewNoticeAction.action',
						cache:false ,
						data:params,
						dataType:'json' ,
						success:function(result){
							if(result.noticeId!=null&&result.noticeId.toString().length>0){
								$("#object").val(result.belongDepartment);
								$("#content").val(result.noticeTitle);
								$("#time").val(result.noticeArriveTime);
								$('#viewNotice').dialog('open'); //打开窗口
							}else{
								LG.showError("提示信息","系统访问异常,请联系管理员!");
							}
						}
					});
				}
			}
			
			function viewNoticeHref(noticeId){
				var params = {"noticeId":noticeId};
				$.ajax({
					type: 'post',
					url: 'contractAction!viewNoticeAction.action',
					cache:false ,
					data:params,
					dataType:'json' ,
					success:function(result){
						if(result.noticeId!=null&&result.noticeId.toString().length>0){
							$("#object").val(result.belongDepartment);
							$("#content").val(result.noticeTitle);
							$("#time").val(result.noticeArriveTime);
							$('#viewNotice').dialog('open'); //打开窗口
						}else{
							LG.showError("提示信息","系统访问异常,请联系管理员!");
						}
					}
				});
			}
			
			/**
			 * 处理完通知中的任务后,将通知标记为已处理
			 * */
			function dealNotice(){
				var arr =$('#t_noticeList').datagrid('getSelections');
				if(arr.length <=0){
					LG.showWarn("提示消息","请选择一个通知!");
				}else if(arr.length != 1){
					LG.showWarn("提示消息","一次只能处理一个通知!");
				}else if(arr[0].noticeViewStatus=='未查阅'){
					LG.showWarn("提示消息","查阅通知详细内容后才能进行处理!");
				}else{
					var noticeId = arr[0].noticeId;
					var params = {"noticeId":noticeId};
					$.messager.confirm('提示消息','通知中指明的任务确认已处理?',function(r){
						if(r){
							$.ajax({
								type: 'post',
								url: 'contractAction!dealNoticeAction.action',
								cache:false ,
								data:params,
								dataType:'json' ,
								success:function(result){
									if(result.retCode=='A000000'){
										$.messager.alert('提示消息',result.message,'info',function(){
											$('#t_noticeList').datagrid('reload');
										});
									}else{
										LG.showError("提示信息",result.message);
									}
								}
							});
						}
					});
				}
			}
			
			function closeViewDialog(){
				$('#viewNotice').dialog('close');
				$('#t_noticeList').datagrid('reload');
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
			
			/**
			 * 转换dateBox的格式
			 * @return {TypeName} 
			 */
			$(function(){
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
		<div region="north" title="通知查询" style="height:60%;overflow:hidden">
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
						<td width="40%">&nbsp;通知日期:&nbsp;<input name="arriveDateBegin" id="arriveDateBegin" class="easyui-datebox" value="" style="width:120%;"/>&nbsp;至&nbsp;<input name="arriveDateEnd" id="arriveDateEnd" class="easyui-datebox" value="" style="width:120%;"/></td>
						<td width="20%"><a href="javascript:void(0)" id="searchbtn" class="easyui-linkbutton">查询</a> <a href="javascript:void(0)" id="clearbtn" class="easyui-linkbutton">清空</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="center" >
			<table id="t_noticeList"></table>
		</div>
	</div>
	<div id="viewNotice" title="查阅通知" modal=false  draggable=true class="easyui-dialog" closed=true style="width:800px;height:250px" >
		<div region="center" style="overflow:auto;width:100%;height:100%">
			<br/>
			  <div class="title" align="center"><h4>通&nbsp;&nbsp;知</h4></div>
			  <div>
				<table width="90%" border="0" align="center" style="table-layout:fixed;">
					<tr>
						<td align="left"><input type="text" id="object" value="" style="width:32%;height:22px;border:0px solid #A4BED4;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<textarea id="content" class="height" rows="4" cols="30" readonly="readonly" style="width:100%;border:0px solid #A4BED4;"></textarea></td>
					</tr>
					<tr>
						<td align="right"><input type="text" id="time" value="" style="width:32%;height:22px;border:0px solid #A4BED4;" readonly="readonly"/></td>
					</tr>			
		  		</table>
		  		<table align="center">
		  			<tr>
		  				<td>
		  					<a href="javascript:void(0)" onclick="closeViewDialog()" class="easyui-linkbutton">关闭</a>
		  				</td>
		  			</tr>
		  		</table>
			  </div> 		
		</div>	
	</div>
  </body>
</html>
