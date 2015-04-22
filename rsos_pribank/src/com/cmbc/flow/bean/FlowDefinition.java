package com.cmbc.flow.bean;

public class FlowDefinition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String processDefinitionId;
	private String name;
	private String description;
	private String version;
	private String deploymentId;
	private String processPngName;
	
	public FlowDefinition() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessPngName(String processPngName) {
		this.processPngName = processPngName;
	}
	public String getProcessPngName() {
		return processPngName;
	}
	
}
