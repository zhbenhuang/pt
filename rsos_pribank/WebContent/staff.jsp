<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String business = (String)request.getAttribute("business");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link type="text/css" rel="stylesheet" href="css/top.css" />
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">	
			var flag ; //判断执行保存还是修改的方法
			var business=<%=business%>;
			$(function(){
				$('#t1').tree({
					//发送异步ajax请求, 还会携带一个id的参数 
					url:'showPermission!showPermission.action' ,
					dnd:true ,
					lines:true,
					animate:true,
					onDrop:function(target , source , point){
						var tar = $('#t1').tree('getNode' , target);
						$.ajax({
							type:'post',
							url:'changeLevel' , 
							data:{
								targetId:tar.id , 
								sourceId:source.id , 
								point:point
							} , 
							dataType:'json' , 
							cache:false , 
							success:function(result){
								$.messager.alert('提示消息','加载成功!','info');
							}
						});
					} , 
					onClick : function(node){
						var src = node.attributes.url;
						var lentgh = src.toString().length;
						if(src!=null&&lentgh>0){
							var title = node.text;
							if($('#tt').tabs('exists' ,title)){
								$('#tt').tabs('select',title);
							} else {
								$('#tt').tabs('add',{   
								    title:title,   
								    content:'<iframe frameborder=0 style=width:100%;height:100% src='+ src +' ></iframe>',   
								    closable:true  
								});  
							}
						}
					},
					onContextMenu: function(e,node){
						//禁止浏览器的菜单打开
						e.preventDefault();
						$(this).tree('select',node.target);
						$('#mm').menu('show', {
							left: e.pageX,
							top: e.pageY
						});
					}
				});
				$('#tt').tabs('add',{  
					title:"默认",
					content:'<iframe frameborder=0 style=width:100%;height:100% src="main.jsp?business='+business+'"></iframe>',   
					closable:false  
				});	
			});
			
		function moreTask(){
		$('#tt').tabs('add',{   
				title:"待办任务",   
				content:'<iframe frameborder=0 style=width:100%;height:100% src="login!waitTaskAction.action" ></iframe>',  
				closable:true  
			});
		};
		function moreNotice(){
		$('#tt').tabs('add',{
				title:"通知信息",   
				content:'<iframe frameborder=0 style=width:100%;height:100% src="login!noticeAction.action" ></iframe>',  
				closable:true  
			});
		};
		

	</script>
  </head>
  
  <body>
	<div id="cc" class="easyui-layout" fit=true style="width:100%;height:100%;">  
	    <div region="north" style="width:100%;height:60%;">
			<div class="logo" align="center" style="width:100%;"><img src="img/mslogo.jpg" width="100%" align="center" /></div>
			<s:action name="validate" executeResult="true" ignoreContextParams="true"></s:action>
	    </div>
	    <div region="west"  iconCls="icon-tip" split="true" title="菜单"  style="width:160%;height:100%" class="bgcolor">
			<ul id="t1" class="easyui-tree">
 				</ul>
	    </div>  
	    <div region="center" style="padding:5px;overflow:hidden">
			<div id="tt" region="center" class="easyui-tabs" fit=true style="width:100%;height:100%;overflow:hidden">  
			</div>  
	    </div>  
	</div>
  </body>
</html>
