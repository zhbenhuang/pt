<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.cmbc.attach.action.AttachAction" %>
<%@ page import="com.cmbc.attach.bean.AttachInfo" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String businessId = (String)request.getAttribute("businessId");
List<AttachInfo> QueryNoticeAttachResultList = (ArrayList<AttachInfo>)request.getAttribute("QueryNoticeAttachResultList"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>附件</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="pragma" content="no-cache"/>
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
	<style type = "text/css">
  		a:link       { text-decoration: none; color: #383838 }
		a:active     { text-decoration: none; color: #383838 }
		a:visited    { text-decoration: none; color: #383838 }
		a:hover      { text-decoration: none; color: #CC0000 }
  	</style>
<script type="text/javascript">

	function selectOne(obj){  	  
		if(!document.getElementsByClassName){
			document.getElementsByClassName = function(className, element){
				var children = (element || document).getElementsByTagName('*');
				var elements = new Array();
				for (var i=0; i<children.length; i++){
					var child = children[i];
					var classNames = child.className.split(' ');
					for (var j=0; j<classNames.length; j++){
						if (classNames[j] == className){ 
							elements.push(child);
							break;
						}
					}
				} 
				return elements;			
			};
		}
	    var objCheckBox = document.getElementsByClassName("attachIds");    
	    for(var i=0;i<objCheckBox.length;i++){          
	        if (objCheckBox[i]!=obj) { 
	            objCheckBox[i].checked = false; 
	        } else{              
	            objCheckBox[i].checked = true; 
	        } 
	    } 
	}
	function getElementsByClassName(n) {
	    var classElements = [], allElements = document.getElementsByTagName('*');
	    for (var i=0; i< allElements.length; i++ ) {
	       if (allElements[i].className == n ) {
	           classElements[classElements.length] = allElements[i];
	        }
	    }
	    return classElements;
	}

	function addAttach()
	{
		var h=200;
		var w=800;
		var l = (screen.width - w) / 2; 
		var t = (screen.height - h) / 2;
		var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l; s += ', toolbar=no, scrollbars=no, menubar=no, location=no, resizable=no'; 
		window.location.href = "attachAction!addNoticeAttach.action?businessId=<%=businessId%>";	
		  
	}
	
	$(function(){
		$("#delAttach").click(function(){		
			var a = document.getElementsByClassName("attachIds");
			var flag = false;
			var attachId;	
			for(var i=0;i<a.length;i++){
				if(a[i].checked){
					attachId = a[i].value;
					flag = true;
				}
			}
			if(!flag){		 
				LG.showWarn("提示消息","请先选择需要删除的附件！");	
			}else{
				if(confirm('确定删除该附件？')){
					var h=100;
					var w=300;
					var l = (screen.width - w) / 2; 
					var t = (screen.height - h) / 2;
					var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l; s += ', toolbar=no, scrollbars=no, menubar=no, location=no, resizable=no'; 
					window.location.href = "attachAction!delAttach.action?attachId="+attachId;
				}
			}
		});	
	});
	
	function downloadAttach()
	{
		var a = document.getElementsByClassName("attachIds");
		var flag = false;
		var attachId;
		for(var i=0;i<a.length;i++){
			if(a[i].checked){
				attachId = a[i].value;
				flag = true;
			}
		}
		if(!flag){			 
			 LG.showWarn("提示消息","请先选择需要下载的附件！");
		}else{
			var h=200;
			var w=800;
			var l = (screen.width - w) / 2; 
			var t = (screen.height - h) / 2;
			var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l; s += ', toolbar=no, scrollbars=no, menubar=no, location=no, resizable=no'; 
			window.location.href = "downloadAttach.action?attachId="+attachId;
		}
	}

</script>
</head>
  <body style="background-color: #E4F5EF;"> 
    <s:form action="#" id="myForm" enctype="multipart/form-data" method="post" name="myForm">    	
    	<input type="hidden" name="businessId" value="<%=businessId %>" />    	
    	<table align="center">
    		<tr>    			
    			<td>    		
					<a id="" href="#" style="" onclick="addAttach()">
						<font size="2"><b>添加附件</b></font>
					</a>			
				</td>
    		</tr>
    	</table>
    	<table>
    	<s:iterator value="QueryNoticeAttachResultList">
    		<tr>					
				<td align="center" width="80%">
					<font size="2"><s:property value="attachNewName"/></font>
				</td>
				<td style="text-align: center"  width="5px" >
					<input type="checkbox" class="attachIds" name="attachIds" id="attachIds" value="<s:property value="attachId"/>" onclick="selectOne(this)"/>
				</td>
		</s:iterator>
		<%if(QueryNoticeAttachResultList.size() != 0){ %>
    			<td align="right" width="15%">    				
    				<a href="javascript:void(0)" id="delAttach"><font size="1.7"><b>删除</b></font></a>
    				&nbsp;				   		
					<a id="" href="#" onclick="javascript:downloadAttach()"><font size="1.7"><b>下载</b></font></a>			
				</td>
		<%}else{%>
				<td width="15%"></td>
		<%} %>
    		</tr>    	
    	</table>    
    </s:form>
  </body>
</html>
