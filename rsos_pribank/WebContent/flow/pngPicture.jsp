<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.jbpm.api.*,java.io.*"%>
<%
	String deploymentId = request.getParameter("deploymentId");
	String processPngName = request.getParameter("processPngName");
	ProcessEngine processEngine = Configuration.getProcessEngine();
	RepositoryService repositoryService = processEngine.getRepositoryService();	
	InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, processPngName);	
	byte[] b = new byte[1024];
	int len = -1;
	OutputStream ops = response.getOutputStream();
	while ((len = inputStream.read(b, 0,1024)) != -1) {
		ops.write(b, 0, len);
	}
	out.clear();
	out = pageContext.pushBody();
%>