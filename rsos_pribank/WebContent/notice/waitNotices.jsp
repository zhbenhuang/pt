<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.cmbc.funcmanage.bean.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Integer total = (Integer)request.getAttribute("total");
String business = (String)request.getAttribute("business");
String message = (String)request.getAttribute("message");
List<NoticeView> noticeViewList = (ArrayList<NoticeView>)request.getAttribute("noticeViewList");
String title = "通知列表(共"+total.toString()+"条)";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>通知列表</title>

<script type="text/javascript">
		var business = <%=business%>;
		var message = <%=message%>;
		if(message=='false'){
			LG.showError("提示信息","系统访问异常,请联系管理员!");
		}
		function moreNotice(){
			if(business=='0'){
 				$.messager.alert('提示消息',"您没有通知消息需要查阅!",'info');
 			}else{
				parent.moreNotice();
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
						$('#viewNotice').dialog('open');
					}else{
						LG.showError("提示信息","系统访问异常,请联系管理员!");
					}
				}
			});
		}
		function closeViewDialog(){
			$('#viewNotice').dialog('close');
			window.location.href = "main.jsp?business="+business;
		}
</script>

</head>

<div class="position1">
	<div id="daiban" class="easyui-panel" title=<%=title %> style="height:350%;">
	     <div>
	     	<table border="0" style="border-collapse:collapse" width="100%">
	     		<thead>
	     			<tr>
	     				<td class="fontStyle" style="width:40px">
	     					<span style="font-size:12px">序号</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px">
	     					<span style="font-size:12px">通知类型</span>
	     				</td>
	     				<td class="fontStyle" style="width:300px">
	     					<span style="font-size:12px">通知标题</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px">
	     					<span style="font-size:12px">通知时间</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px">
	     					<span style="font-size:12px">查阅状态</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px">
	     					<span style="font-size:12px">查阅时间</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px">
	     					<span style="font-size:12px">查阅机构</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px">
	     					<span style="font-size:12px">处理状态</span>
	     				</td>
	     				<td class="fontStyle" style="width:100px" align="right">
	     					<a href="javascript:void(0)" onclick="moreNotice()"><span style="font-size:15px">更多>>></span></a>
	     				</td>
	     			</tr>
	     		</thead>
	     		<tbody>
	     			<%int i=0;%>
	     			<s:iterator value="noticeViewList">
	     				<%i++; %>
	     				<tr>
	     					<%if(i%2==0){ %>
	     					<td class="fontStyle1"><%=i %></td>
	     					<td class="fontStyle1"><s:property value="noticeType"/></td>
	     					<td class="fontStyle1"><a href="javascript:void(0)" onclick="viewNoticeHref('<s:property value="noticeId"/>')"><s:property value="noticeTitle"/></a></td>
	     					<td class="fontStyle1"><s:property value="noticeArriveTime"/></td>
	     					<td class="fontStyle1"><s:property value="noticeViewStatus"/></td>
	     					<td class="fontStyle1"><s:property value="noticeViewTime"/></td>
	     					<td class="fontStyle1"><s:property value="belongDepartment"/></td>
	     					<td class="fontStyle1"><s:property value="noticeDealStatus"/></td>
	     					<td></td>
	     					<%}else{ %>
	     						<td><%=i %></td>
		     					<td class="fontStyle1"><s:property value="noticeType"/></td>
		     					<td class="fontStyle1"><a href="javascript:void(0)" onclick="viewNoticeHref('<s:property value="noticeId"/>')"><s:property value="noticeTitle"/></a></td>
		     					<td class="fontStyle1"><s:property value="noticeArriveTime"/></td>
		     					<td class="fontStyle1"><s:property value="noticeViewStatus"/></td>
		     					<td class="fontStyle1"><s:property value="noticeViewTime"/></td>
	     						<td class="fontStyle1"><s:property value="belongDepartment"/></td>
	     						<td class="fontStyle1"><s:property value="noticeDealStatus"/></td>
	     						<td></td>
	     					<%} %>
	     				</tr>
	     			</s:iterator>
	     		</tbody>
	     	</table>
	     </div>
	</div>
</div>
<div id="viewNotice" title="查阅通知" modal=false  draggable=true class="easyui-dialog" closed=true style="width:600px;height:250px;overflow:hidden" >
	<div region="center" style="overflow:auto;width:100%;height:100%">
		<br/>
		  <div class="title" align="center"><h4>通&nbsp;&nbsp;知</h4></div>
		  <div>
			<table width="90%" border="0" align="center" style="table-layout:fixed;">
				<tr>
					<td align="left"><input type="text" id="object" value="" style="width:32%;height:22px;border:0px solid #A4BED4;" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<textarea id="content" class="height" rows="4" cols="30" readonly="readonly" style="width:100%;border:0px solid #A4BED4;"></textarea>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
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
