package com.cmbc.flow.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.flow.bean.InstanceInformation;
import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.bean.TaskView;

public interface ProcessService {
//	public EasyGridList<ProcessView> queryProcessList(QueryFlowDto queryDto) throws AppException;
	public EasyGridList<TaskView> tracingProcess(QueryFlowDto queryDto) throws AppException;
	public ProcessInstance startProcess(String processKey,Map<String, Object> variables) throws AppException;
	public Task findActivityTaskByProcessInsId(String processInsId) throws AppException;
	public void completeTask(String taskId, String userId, String attitude) throws AppException;
	public void taskAssign(String taskId, String userId) throws AppException;
	
	public List<InstanceInformation> findWaitByConditions(String table,int currentPage, int pageSize, String userId,
			Map<String, Object> condition)throws AppException;
	public int getWaitTotal(String table, String userId,
			Map<String, Object> condition)throws AppException;
	public List<InstanceInformation> findCompleteByConditions(String table,
			int currentPage, int pageSize, String userId,
			Map<String, Object> condition)throws AppException;
	public int getCompleteTotal(String table, String userId,
			Map<String, Object> condition)throws AppException;
	public List<InstanceInformation> findFinishedByConditions(String table,
			int currentPage, int pageSize, String userId,
			Map<String, Object> condition)throws AppException;
	public int getFinishedTotal(String table, String userId,
			Map<String, Object> condition)throws AppException;
	public ProcessIns findProcessIns(String businessId)throws AppException;
	public Task findTaskByTaskId(String taskId)throws AppException;
	public HistoryProcessInstance findHistoryProcessInstanceById(
			String processInsId)throws AppException;
	public ProcessDefinition findProcessDefinition(String processInsId)throws AppException;
	public ProcessDefinition findProcessDefinitionByHistoryIns(
			String processInsId)throws AppException;

}
