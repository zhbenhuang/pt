package rsos.framework.flow;

import java.io.Serializable;

import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("processService")
@Scope("prototype")
public class ProcessService implements Serializable {
	/**
	 *配置好JBPM需要的服务
	 */
	private static final long serialVersionUID = 7943286151544402392L;
	private static ProcessEngine processEngine = Configuration.getProcessEngine();
	
	
	public static ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}
	
	public ExecutionService getExecutionService() {
		return processEngine.getExecutionService();
	}
	
	public TaskService getTaskService() {
		return processEngine.getTaskService();
	}
	
	public IdentityService getIdentityService() {
		return processEngine.getIdentityService();
	}
	
	public ManagementService getManagementService() {
		return processEngine.getManagementService();
	}
	
	public HistoryService getHistoryService() {
		return processEngine.getHistoryService();
	}
}
