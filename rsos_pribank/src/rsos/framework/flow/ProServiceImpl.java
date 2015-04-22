package rsos.framework.flow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;


import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

@Service("proService")
public class ProServiceImpl implements ProService {
	

	private ProcessService processService;
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public ProcessInstance startProcess(String processKey,Map<String, Object> variables) {
		// TODO Auto-generated method stub
		try{
			ExecutionService executionService = processService.getExecutionService();
			ProcessInstance processIns = executionService.startProcessInstanceByKey(processKey, variables);
			return processIns;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Task> findTasksByUserId(int pageSize, int currentPage, String userId) {
		// TODO Auto-generated method stub
		try{
			TaskService taskService = processService.getTaskService();
			List<Task> taskList = taskService.createTaskQuery().assignee(userId).page((currentPage-1)*pageSize,pageSize).list(); //分页查找任务分配人的任务列表
			return taskList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Long findTotalNum(String userId) {
		// TODO Auto-generated method stub
		TaskService taskService = processService.getTaskService();
		Long count = taskService.createTaskQuery().assignee(userId).count();	//查找某个任务分配人的待办任务总数
		return count;
		
	}

	@Override
	public boolean signTaskByTaskId(String taskId,String userId,String sign) {
		// TODO Auto-generated method stub
		try{
			TaskService taskService = processService.getTaskService();
			Task task = taskService.getTask(taskId);
			if(task.getAssignee().equals(userId)){
				taskService.completeTask(taskId,sign);
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public Task findActivityTaskByProcessInsId(String processInsId) {
		// TODO Auto-generated method stub
		try{
			ExecutionService executionService = processService.getExecutionService();
			TaskService taskService = processService.getTaskService();
			Task task = taskService.newTask();
			ProcessInstance processInstance = executionService.findProcessInstanceById(processInsId);	//找到对应的流程实例Id对应的流程实例
			Set<String> activeActivityNames = processInstance.findActiveActivityNames();
			TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInsId);
			if(activeActivityNames!=null){
				for(String activityName:activeActivityNames){
					task = taskQuery.activityName(activityName).list().get(0);
				}
				return task;
			}else{
				return null;
			}
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean taskAssign(String taskId, String userId) {
		// TODO Auto-generated method stub
		try{
			TaskService taskService = processService.getTaskService();
			taskService.assignTask(taskId, userId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean checkTask(String taskId,String userId,String attitude) {
		// TODO Auto-generated method stub
		try{
			TaskService taskService = processService.getTaskService();
			if(taskService.getTask(taskId).getAssignee().equals(userId)){
				taskService.completeTask(taskId, attitude);
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<HistoryTask> findHistoryTasksByProInsId(String processInsId) {
		// TODO Auto-generated method stub
		try{
			ExecutionService executionService = processService.getExecutionService();
			HistoryService historyService = processService.getHistoryService();
			Execution execution = executionService.findExecutionById(processInsId);
			List<HistoryTask> historyTaskList =  historyService.createHistoryTaskQuery().executionId(execution.getId()).list();
			return historyTaskList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 遍历指定用户已处理的所有历史任务List列表
	 * 遍历List列表找到未完结的executionId,保存在Set中，使用Set达到去重的目的
	 * 然后抽出Set中元素存入List中用于后续的分页操作
	 */
	@Override
	public Set<Object> findProcessInsIdsNoEndByUserId(String userId) {
		// TODO Auto-generated method stub
		try{
			ExecutionService executionService = processService.getExecutionService();
			HistoryService historyService = processService.getHistoryService();
			Set<String> executionIdNoEndSet = new HashSet<String>();
			List<HistoryTask> historyTaskList = historyService.createHistoryTaskQuery().assignee(userId).state(HistoryTask.STATE_COMPLETED).list();
			if(historyTaskList!=null){
				for(HistoryTask historyTask:historyTaskList){	//遍历所以的历史任务，找到未完成的executionIds
					Execution execution = executionService.findExecutionById(historyTask.getExecutionId());
					if(execution!=null){
						if(!execution.isEnded()){
							executionIdNoEndSet.add(historyTask.getExecutionId());
						}
					}
				}
				Set<Object> executionIdList = new HashSet<Object>();	
				for(String executionId:executionIdNoEndSet){
					if(!isLoginUser(executionId,userId)){				//判断流程实例当前的办理人是不是当前登录用户,如果不是，说明登录用户已处理了这个实例对应的任务
						executionIdList.add(executionId);
					}
				}
				return executionIdList;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 遍历指定用户已处理的所有历史任务List列表
	 * 遍历List列表找到已办结的executionId,保存在Set中，使用Set达到去重的目的
	 * 然后抽出Set中元素存入List中用于后续的分页操作
	 */
	@Override
	public List<Object> findProcessInsIdsByUserId(String userId) {
		// TODO Auto-generated method stub
		try{
			ExecutionService executionService = processService.getExecutionService();
			HistoryService historyService = processService.getHistoryService();
			Set<String> executionIdSet = new HashSet<String>();
			List<HistoryTask> historyTaskList = historyService.createHistoryTaskQuery().assignee(userId).list();
			if(historyTaskList!=null){
				for(HistoryTask historyTask:historyTaskList){	//遍历所以的历史任务，找到已办结的executionIds
					executionIdSet.add(historyTask.getExecutionId());	
				}
			}
			List<Object> executionIdList = new ArrayList<Object>();	//将查找到的结果放入List中，用于后续分页
			for(String executionId:executionIdSet ){
				executionIdList.add(executionId);
			}
			return executionIdList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public List<Object> page(int pageSize, int currentPage,List<Object> executionIdList) {
		// TODO Auto-generated method stub
		int startIndex = (currentPage-1)*pageSize ;
		int endIndex = currentPage*pageSize-1;
		List<Object> pageResultList = new ArrayList<Object>();
		for(int i=startIndex;i<executionIdList.size()&&i<=endIndex;i++){
			pageResultList.add(executionIdList.get(i));
		}
		return pageResultList;
	}

	@Override
	public String findTaskNameByTaskId(String taskId) {
		// TODO Auto-generated method stub
		TaskService taskService = processService.getTaskService();
		String taskName = taskService.getTask(taskId).getName();
		return taskName;
	}

	@Override
	public HistoryProcessInstance findProcessInstanceByTaskId(String taskId) {
		// TODO Auto-generated method stub
		TaskService taskService = processService.getTaskService();
		ExecutionService excecutionService = processService.getExecutionService();
		HistoryService  historyService = processService.getHistoryService();
		Execution execution = excecutionService.findExecutionById(taskService.getTask(taskId).getExecutionId());
		ProcessInstance processInstance = (ProcessInstance)execution.getProcessInstance();
		List<HistoryProcessInstance> historyProcessInstanceList = historyService.createHistoryProcessInstanceQuery().processInstanceId(processInstance.getId()).list();
		if(historyProcessInstanceList.size()>0){
			return historyProcessInstanceList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String findHistoryTaskNameById(String historyTaskId) {
		// TODO Auto-generated method stub
		String querySQL = "select T.ACTIVITY_NAME_ as taskName from JBPM4_HIST_ACTINST as T where T.HTASK_ = " +"'"+historyTaskId+"'";
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(querySQL).list();
		return list.get(0).toString();
	}

	@Override
	public Task findTaskByTaskId(String taskId) {
		// TODO Auto-generated method stub
		TaskService taskService = processService.getTaskService();
		return taskService.getTask(taskId);
	}

	/**
	 * 遍历指定用户在指定流程实例流转过程中任务完成情况
	 */
	@Override
	public List<HistoryTask> findTasksbyExeIdAndUserId(String executionId,String userId) {//666
		// TODO Auto-generated method stub
		HistoryService historyService = processService.getHistoryService();
		List<HistoryTask> historyTaskList = historyService.createHistoryTaskQuery().executionId(executionId).assignee(userId).list();//找到流程实例中指定用户的历史任务
		
		return historyTaskList;
	}
	
	
	

	@Override
	public boolean isLoginUser(String executionId, String userId) {
		// TODO Auto-generated method stub
		Task task = this.findActivityTaskByProcessInsId(executionId);
		if(task.getAssignee().equals(userId)){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public List<Object> findFinishedProIds() {
		// TODO Auto-generated method stub
		HistoryService historyService = processService.getHistoryService();
		List<HistoryProcessInstance> historyProcessInstances = historyService.createHistoryProcessInstanceQuery().ended().list();
		List<Object> finishedProInds = new ArrayList<Object>();
		for(HistoryProcessInstance historyProcessInstance:historyProcessInstances){
			finishedProInds.add(historyProcessInstance.getProcessInstanceId());
		}
		return finishedProInds;
	}

	@Override
	public HistoryProcessInstance findHistoryProcessInstanceById(String processInsId) {
		// TODO Auto-generated method stub
		HistoryService historyService = processService.getHistoryService();
		List<HistoryProcessInstance> historyProcessInstanceList = historyService.createHistoryProcessInstanceQuery().processInstanceId(processInsId).list();
		if(historyProcessInstanceList.size()>0){
			return historyProcessInstanceList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean isActivity(String processInsId) {
		// TODO Auto-generated method stub
		HistoryService historyService = processService.getHistoryService();
		List<HistoryProcessInstance> historyProcessInstanceList = historyService.createHistoryProcessInstanceQuery().processInstanceId(processInsId).list();
		
		if(historyProcessInstanceList.size()>0){
			HistoryProcessInstance historyProcessInstance = historyProcessInstanceList.get(0);
			if(historyProcessInstance.getState().equals(HistoryProcessInstance.STATE_ACTIVE)){
				return true;
			}
		}
		return false;
	}

	@Override
	public HistoryTask findHistoryTaskById(String taskId) {
		// TODO Auto-generated method stub
		HistoryService historyService = processService.getHistoryService();
		List<HistoryTask> historyTaskList = historyService.createHistoryTaskQuery().taskId(taskId).list();
		if(historyTaskList.size()>0){
			return historyTaskList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String deployProcess(ZipInputStream zis) {
		// TODO Auto-generated method stub
		try{
			RepositoryService repositoryService = processService.getRepositoryService();
			String deploymentId = repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();	//发布流程,使用流程定义的*.jpdl.xml文件
			return deploymentId;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	
	
	
	
}
