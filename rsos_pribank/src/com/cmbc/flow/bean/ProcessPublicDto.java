package com.cmbc.flow.bean;

import java.io.Serializable;


import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import rsos.framework.constant.StaticVariable;
import rsos.framework.utils.CalendarUtil;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;

public class ProcessPublicDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 流程启动时,设置初始任务,且初始任务被执行完毕
	 * @param task
	 * @param processInstance
	 * @param user
	 * @param department
	 * @return
	 */
	public TaskIns initStartTaskIns(Task task,ProcessInstance processInstance,Users user,Department department,Users nextUser,Department nextDepartment){
		TaskIns taskIns = new TaskIns();
		taskIns.setTaskId(task.getId());							//任务Id
		taskIns.setTaskName(task.getName());						//任务名
		taskIns.setProcessInsId(processInstance.getId());				//流程Id
		taskIns.setAssignUserId(user.getUserId());                   //当前办理人Id
		taskIns.setAssignUserName(user.getUsername());
		taskIns.setAssignDepartmentId(department.getDepartmentId()); //当前办理机构
		taskIns.setAssignDepartment(department.getDepartmentName());
	
		taskIns.setPreUserId("无");     //上一办理人
		taskIns.setPreDepartmentId("无");
		taskIns.setPreUserName("无");
		taskIns.setPreDepartment("无");
		taskIns.setSignTime(CalendarUtil.getDateTimeStr());	//由于任务没有上一办理人，因此，任务的签收时间也就是到达时间
		
		taskIns.setNextUserId(nextUser.getUserId());
		taskIns.setNextUserName(nextUser.getUsername());
		taskIns.setNextDepartmentId(nextDepartment.getDepartmentId());
		taskIns.setNextDepartment(nextDepartment.getDepartmentName());
		
		taskIns.setArriveTime(CalendarUtil.getDateTimeStr());		
		taskIns.setCompleteTime(CalendarUtil.getDateTimeStr());									//任务完成时间
		taskIns.setStatus(StaticVariable.COMPLETE);							//设置任务完成状态
		taskIns.setIsActivity(StaticVariable.COMPLETED);					//标记任务已经完成
		return taskIns;
	}
	
	/**
	 * 产生一个新任务,设置这个任务的办理人以及该任务前一任务的办理人
	 * @param task
	 * @param processInstance
	 * @param user
	 * @param department
	 * @param preUser
	 * @param preDepartment
	 * @return
	 */
	public TaskIns initTaskIns(Task task,String processInsId,Users user,Department department,Users preUser,Department preDepartment){
		TaskIns taskIns = new TaskIns();
		taskIns.setTaskId(task.getId());							//任务Id
		taskIns.setTaskName(task.getName());						//任务名
		taskIns.setProcessInsId(processInsId);			//流程Id
		taskIns.setAssignUserId(user.getUserId());                   //当前办理人Id
		taskIns.setAssignUserName(user.getUsername());
		taskIns.setAssignDepartmentId(department.getDepartmentId()); //当前办理机构
		taskIns.setAssignDepartment(department.getDepartmentName());
		if(preUser!=null){
			taskIns.setPreUserId(preUser.getUserId()); //上一办理人
			taskIns.setPreUserName(preUser.getUsername());
			taskIns.setPreDepartmentId(preDepartment.getDepartmentId());
			taskIns.setPreDepartment(preDepartment.getDepartmentName());
		}else{
			taskIns.setPreUserId("无"); //无上一办理人
			taskIns.setPreUserName("无");
			taskIns.setPreDepartmentId("无");
			taskIns.setPreDepartment("无");
		}
		
		taskIns.setNextUserId("无");
		taskIns.setNextUserName("无");
		taskIns.setNextDepartmentId("无");
		taskIns.setNextDepartment("无");				

		taskIns.setArriveTime(CalendarUtil.getDateTimeStr());		
		
		taskIns.setStatus(StaticVariable.WAITSIGN);
		taskIns.setIsActivity(StaticVariable.UNCOMPLETED);
		return taskIns;
	}
	
	/**
	 * 当前任务完成,更改任务状态
	 * @param taskIns
	 * @param nextUser
	 * @param nextDepartment
	 * @return
	 */
	public TaskIns updateTaskIns(TaskIns taskIns,Users nextUser,Department nextDepartment){
		if(nextUser!=null){
			taskIns.setNextUserId(nextUser.getUserId());
			taskIns.setNextUserName(nextUser.getUsername());
			taskIns.setNextDepartmentId(nextDepartment.getDepartmentId());
			taskIns.setNextDepartment(nextDepartment.getDepartmentName());	
		}else{
			taskIns.setNextUserId("无");
			taskIns.setNextUserName("无");
			taskIns.setNextDepartmentId("无");
			taskIns.setNextDepartment("无");														//设置taskId对应任务的下一办理机构
		}
		taskIns.setStatus(StaticVariable.COMPLETE);												//设置任务完成状态
		taskIns.setIsActivity(StaticVariable.COMPLETED);
		taskIns.setCompleteTime(CalendarUtil.getDateTimeStr());									//任务完成时间
		return taskIns;
	}
	
	public ProcessIns setProcessInsCurrentDealer(ProcessIns processIns,Users currentUser,Department currentDepartment){
		processIns.setCurrentUserId(currentUser.getUserId());
		processIns.setCurrentUserName(currentUser.getUsername());
		processIns.setCurrentDepartmentId(currentDepartment.getDepartmentId());
		processIns.setCurrentDepartmentName(currentDepartment.getDepartmentName());
		return processIns;
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		for(int i=0;i<10000;i++){
			System.out.println(CalendarUtil.getDateTimeStr());
		}
	}

}


