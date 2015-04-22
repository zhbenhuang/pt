package com.cmbc.flow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.flow.bean.InstanceInformation;
import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.bean.TaskIns;
import com.cmbc.flow.bean.TaskView;
import com.cmbc.flow.dao.ProcessDao;
import com.cmbc.flow.dao.ProcessInsDao;
import com.cmbc.flow.dao.TaskInsDao;
import com.cmbc.flow.service.ProcessService;

public class ProcessServiceImpl implements ProcessService{
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ProcessEngine processEngine;
	private ProcessDao processDao;
	private ProcessInsDao processInsDao;
	private TaskInsDao taskInsDao;
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	public void setProcessInsDao(ProcessInsDao processInsDao) {
		this.processInsDao = processInsDao;
	}
	public void setProcessDao(ProcessDao processDao) {
		this.processDao = processDao;
	}
	public void setTaskInsDao(TaskInsDao taskInsDao) {
		this.taskInsDao = taskInsDao;
	}
	
	public ProcessInstance startProcess(String processKey,Map<String, Object> variables)
			throws AppException {
		try{
			ExecutionService executionService = processEngine.getExecutionService();
			ProcessInstance processIns = executionService.startProcessInstanceByKey(processKey, variables);
			return processIns;
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000016");
		}
	}
	
	public void completeTask(String taskId,String userId,String attitude) throws AppException {
		try{
			TaskService taskService = processEngine.getTaskService();
			if(taskService.getTask(taskId).getAssignee().equals(userId)){
				taskService.completeTask(taskId,attitude);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw  new AppException("E000016");
		}
	}
	
	public void taskAssign(String taskId, String userId) throws AppException {
		try{
			TaskService taskService = processEngine.getTaskService();
			taskService.assignTask(taskId, userId);
		}catch(Exception e){
			e.printStackTrace();
			throw  new AppException("E000016");
		}
		
	}
	
	public Task findActivityTaskByProcessInsId(String processInsId)
			throws AppException {
		try{
			ExecutionService executionService = processEngine.getExecutionService();
			TaskService taskService = processEngine.getTaskService();
			Task task = null;
			ProcessInstance processInstance = executionService.findProcessInstanceById(processInsId);
			Set<String> activeActivityNames = processInstance.findActiveActivityNames();
			
			TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInsId);
			if(activeActivityNames!=null){
				for(String activityName:activeActivityNames){
					task = taskQuery.activityName(activityName).list().get(0);
				}
			}
			if(task==null){
				throw new Exception();
			}else{
				return task;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000016");
		}
		
	}

	@Override
	public List<InstanceInformation> findWaitByConditions(String table,int currentPage, int pageSize, String userId,Map<String, Object> condition) throws AppException {
		try{
			return processDao.findWaitByConditions(table,currentPage,pageSize,userId,condition);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{condition.toString()});
		}
	}
	@Override
	public int getWaitTotal(String table, String userId,Map<String, Object> condition) throws AppException {
		try{
			return processDao.getWaitTotal(table,userId,condition);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{condition.toString()});
		}
	}
	@Override
	public List<InstanceInformation> findCompleteByConditions(String table,int currentPage, int pageSize, String userId,Map<String, Object> condition) throws AppException {
		try{
			return processDao.findCompleteByConditions(table,currentPage,pageSize,userId,condition);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{condition.toString()});
		}
	}
	@Override
	public int getCompleteTotal(String table, String userId,Map<String, Object> condition) throws AppException {
		try{
			return processDao.getCompleteTotal(table,userId,condition);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{condition.toString()});
		}
	}
	@Override
	public List<InstanceInformation> findFinishedByConditions(String table,
			int currentPage, int pageSize, String userId,
			Map<String, Object> condition) throws AppException {
		try{
			return processDao.findFinishedByConditions(table,currentPage,pageSize,userId,condition);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{condition.toString()});
		}
	}
	@Override
	public int getFinishedTotal(String table, String userId,
			Map<String, Object> condition) throws AppException {
		try{
			return processDao.getFinishedTotal(table,userId,condition);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{condition.toString()});
		}
	}
	@Override
	public EasyGridList<TaskView> tracingProcess(QueryFlowDto queryDto)
		throws AppException {
		try {
			log.info("----tracingProcess---");
			HistoryService historyService = processEngine.getHistoryService();
			EasyGridList<TaskIns> taskInsList= taskInsDao.selectTaskView(queryDto);
			List<TaskView> retList = new ArrayList<TaskView>();
			int i = 1;
            if (taskInsList != null) {
				for(TaskIns t : taskInsList.getRows()){
					TaskView v = new TaskView();
					List<HistoryTask> historyTaskList = historyService.createHistoryTaskQuery().taskId(t.getTaskId()).list();
					HistoryTask historyTask = (HistoryTask)historyTaskList.get(0);
					v.setTaskName(t.getTaskName());
					v.setAssignUserId(t.getAssignUserId());
					v.setAssignUserName(t.getAssignUserName());
					v.setAssignDepartment(t.getAssignDepartment());
					v.setAssignDepartmentId(t.getAssignDepartmentId());
					v.setArriveTime(t.getArriveTime());
					if(t.getStatus().equals(StaticVariable.WAITCHECK)){
						v.setActType("签收");
					}else{
						v.setActType(historyTask.getOutcome());
					}
					v.setSignTime(t.getSignTime());
					v.setCompleteTime(t.getCompleteTime());
					v.setTaskStatus(t.getStatus());
					v.setStepCode(Integer.toString(i++));
					retList.add(v);
				}
			}
            EasyGridList<TaskView> result= new EasyGridList<TaskView>();
            result.setRows(retList);
            result.setTotal(taskInsList.getTotal());
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	@Override
	public ProcessIns findProcessIns(String businessId) throws AppException {
		try{
			return processInsDao.selectBy(businessId);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{businessId});
		}
	}
	@Override
	public Task findTaskByTaskId(String taskId) throws AppException {
		try{
			TaskService taskService = processEngine.getTaskService();
			return taskService.getTask(taskId);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000016");
		}
	}
	@Override
	public HistoryProcessInstance findHistoryProcessInstanceById(
			String processInsId) throws AppException {
		try{
			HistoryService historyService = processEngine.getHistoryService();
			List<HistoryProcessInstance> historyProcessInstanceList = historyService.createHistoryProcessInstanceQuery().processInstanceId(processInsId).list();
			if(historyProcessInstanceList.size()>0){
				return historyProcessInstanceList.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000016");
		}
		
	}
	@Override
	public ProcessDefinition findProcessDefinition(String processInsId)throws AppException {
		try{
			RepositoryService repositoryService = processEngine.getRepositoryService();
			ExecutionService executionService = processEngine.getExecutionService();
			ProcessInstance processInstance = executionService.findProcessInstanceById(processInsId);
			String processDefinitionId = processInstance.getProcessDefinitionId();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
																   .processDefinitionId(processDefinitionId)
																   .uniqueResult();
			return processDefinition;
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000016");
		}
	}
	@Override
	public ProcessDefinition findProcessDefinitionByHistoryIns(
			String processInsId) throws AppException {
		try{
			RepositoryService repositoryService = processEngine.getRepositoryService();
			HistoryService historyService = processEngine.getHistoryService();
			HistoryProcessInstance historyprocessInstance = historyService.createHistoryProcessInstanceQuery().processInstanceId(processInsId).uniqueResult();
			String processDefinitionId = historyprocessInstance.getProcessDefinitionId();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
																   .processDefinitionId(processDefinitionId)
																   .uniqueResult();
			return processDefinition;
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000016");
		}
	}
}
