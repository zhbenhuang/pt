package rsos.framework.flow;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;

public interface ProService {
	
	public String deployProcess(ZipInputStream zis);
	
	public ProcessInstance startProcess(String processKey,Map<String,Object> variables); //按照流程Key启动流程
	
	public List<Task> findTasksByUserId(int pageSize,int currentPage,String userId); //分页查找分配人的任务列表
	
	public Long findTotalNum(String userId); //查找某个分配人的所有待办任务的个数
	
	public boolean signTaskByTaskId(String taskId,String userId,String sign); //签收当前的taskId对应的任务,userId用于判断taskId对应的任务的所属人是否是userId
	
	public Task findActivityTaskByProcessInsId(String processInsId);//通过某个processInsId找到当前处于activity状态的任务
	
	public boolean taskAssign(String taskId,String userId);//为处于activity状态的Task指定办理人
	
	public boolean checkTask(String taskId,String userId,String attitude);//审核当前任务,通过或驳回由传入的参数决定
	
	public List<HistoryTask> findHistoryTasksByProInsId(String processInsId);//流程跟踪
	
	public Set<Object> findProcessInsIdsNoEndByUserId(String userId);//查找特定用户已办但未完结的任务实例(内部实现分页)
	
	public List<Object> findProcessInsIdsByUserId(String userId);//查找特定用户已办结的任务实例(内部实现分页)
	
	public List<Object> page(int pageSize, int currentPage,List<Object> executionIdList);//自定义一个针对List的分页API
	
	public String findTaskNameByTaskId(String taskId);		//通过taskId获得taskName
	
	public HistoryProcessInstance findProcessInstanceByTaskId(String taskId);	//通过taskId获得流程实例

	public String findHistoryTaskNameById(String historyTaskId);			//通过historyTaskId查找任务的
	
	public Task findTaskByTaskId(String taskId);									//通过taskid找到对应的task
	
	public List<HistoryTask> findTasksbyExeIdAndUserId(String executionId,String userId);	//通过用户Id和ExecutionId找到用户最近办理的任务
	
	public boolean isLoginUser(String executionId,String userId);						//判断当前登录用户是否是指定流程executionId活动任务节点的办理人

	public List<Object> findFinishedProIds();

	public HistoryProcessInstance findHistoryProcessInstanceById(String processInsId);				//由流程实例Id查询流程实例

	public boolean isActivity(String processInsId);										//判断当前实例是否处于activity状态

	public HistoryTask findHistoryTaskById(String taskId);								//通过taskId找到历史Task
	
	//public String findVariblesByProcessInsId();
	
}
