<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<div class="tips">
	<table align="right">		
		<tr>
			<td align="left" style="color:#01ad9d">		
			</td>
			<td>
			&nbsp;&nbsp;
			</td>
	        <td align="right" style="color:#01ad9d">
			<s:property value="userName"/>&nbsp;您好！
			</td>
			<td align="right" width="30px"><img src="img/edit.png" width="16px" height="16px"></td>
	        <td align="left" width="50px"><a href="editPasswd.jsp" style="color:#01ad9d">修改密码</a></td>
			<td align="right" width="20px"><img src="img/exit.png" width="16px" height="16px"/></td>
			<td align="left" width="50px"><a href="logout" style="color:#01ad9d">安全退出</a></td>
	    </tr>
	</table>
</div>



