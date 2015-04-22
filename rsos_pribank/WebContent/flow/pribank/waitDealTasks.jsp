<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.cmbc.flow.bean.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Integer total = (Integer)request.getAttribute("total");
String business = (String)request.getAttribute("business");
List<InstanceInformation> instanceInformationList = (ArrayList<InstanceInformation>)request.getAttribute("instanceInformationList");
String title = "待办任务列表(共"+total.toString()+"条)";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待办任务列表</title>

<script type="text/javascript">
	var business = <%=business %>;
 	function moreTask(){
 		if(business=='0'){
 			$.messager.alert('提示消息',"您没有待办任务需要办理!",'info');
 		}else{
 			parent.moreTask();
 		}
 	}

</script>

</head>

<div class="position">
<div id="daiban" class="easyui-panel" title=<%=title %> style="height:250%;">
     <div>	
     	<table border="0" style="border-collapse:collapse" width="100%">
     		<thead>
     			<tr>
     				<td class="fontStyle" style="width:40px">
     					<span style="font-size:12px">序号</span>
     				</td>
     				<td class="fontStyle" style="width:100px">
     					<span style="font-size:12px">流程类型</span>
     				</td>
     				<td class="fontStyle" style="width:300px">
     					<span style="font-size:12px">流程标题</span>
     				</td>
     				<td class="fontStyle" style="width:100px"></td>
     				<td class="fontStyle" style="width:100px">
     					<span style="font-size:12px">流程状态</span>
     				</td>
     				<td class="fontStyle" style="width:200px">
     					<span style="font-size:12px">到达时间</span>
     				</td>
     				<td class="fontStyle" style="width:100px" align="right">
     					<a href="javascript:void(0)" onclick="moreTask()"><span style="font-size:15px">更多>>></span></a>
     				</td>
     			</tr>
     		</thead>
     		<tbody>
     			<%int i=0;%>
     			<s:iterator value="instanceInformationList">
     				<%i++; %>
     				<tr>
     					<%if(i%2==0){ %>
     					<td class="fontStyle1"><%=i %></td>
     					<td class="fontStyle1"><s:property value="processTypeName"/></td>
     					<td class="fontStyle1"><a href="processBaseAction!dealWaitTask.action?businessId=<s:property value="businessId"/>&taskId=<s:property value="taskId"/>"><s:property value="title"/></a></td>
     					<td class="fontStyle1"></td>
     					<td class="fontStyle1"><s:property value="status"/></td>
     					<td class="fontStyle1"><s:property value="arriveTime"/></td>
     					<td></td>
     					<%}else{ %>
     						<td class="fontStyle1"><%=i %></td>
	     					<td class="fontStyle1"><s:property value="processTypeName"/></td>
	     					<td class="fontStyle1"><a href="processBaseAction!dealWaitTask.action?businessId=<s:property value="businessId"/>&taskId=<s:property value="taskId"/>"><s:property value="title"/></a></td>
	     					<td class="fontStyle1"></td>
	     					<td class="fontStyle1"><s:property value="status"/></td>
	     					<td class="fontStyle1"><s:property value="arriveTime"/></td>
	     					<td></td>
     					<%} %>
     				</tr>
     			</s:iterator>
     		</tbody>
     	</table>
     </div>
</div>
</div>
