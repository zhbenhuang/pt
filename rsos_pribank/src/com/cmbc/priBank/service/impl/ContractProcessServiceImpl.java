package com.cmbc.priBank.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.exception.AppException;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;

import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.flow.bean.TaskIns;
import com.cmbc.flow.dao.ProcessInsDao;
import com.cmbc.flow.dao.TaskInsDao;
import com.cmbc.flow.service.ProcessService;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.Notice;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.dao.ContractDao;
import com.cmbc.funcmanage.dao.NoticeDao;
import com.cmbc.priBank.bean.ContractDto;
import com.cmbc.priBank.service.ContractProcessService;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.service.DepartmentService;
import com.cmbc.sa.service.UserService;

public class ContractProcessServiceImpl implements ContractProcessService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ProcessService processService;
	private ProcessInsDao processInsDao;
	private TaskInsDao taskInsDao;
	private UserService userService;
	private DepartmentService departmentService;
	private ContractDao contractDao;
	private NoticeDao noticeDao;
	
	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	public void setTaskInsDao(TaskInsDao taskInsDao) {
		this.taskInsDao = taskInsDao;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public void setProcessInsDao(ProcessInsDao processInsDao) {
		this.processInsDao = processInsDao;
	}
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	@Override
	public void startProcess(ContractDto contractDto) throws AppException {
		try{
			log.info("----startProcess...");
			String businessId = UUIDGenerator.generateShortUuid();
			ProcessIns processIns = contractDto.initProcessIns(businessId);							   
			processIns.setCodeId(contractDto.getCodeId());
			Contract contract = contractDao.selectBy(contractDto.getContractId());
			contract.setCodeId(contractDto.getCodeId());
			contract.setBusinessId(businessId);
			contract.setHandDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			contract.setHandStatus(StaticVariable.CONTRACTHAND);
			
			Users user = userService.findUser(contractDto.getAssignUserId(), Constants.BUSINESS_PBS);
			Department department = departmentService.findDepartment(user.getDepartmentId());
			
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("createUserId", contractDto.getUser().getUserId());
			ProcessInstance processInstance = processService.startProcess(ContractDto.processKey, variables);//启动流程
			
			Task task0 = processService.findActivityTaskByProcessInsId(processInstance.getId());								//查找活动任务
			TaskIns taskIns0 = contractDto.initStartTaskIns(task0, processInstance,contractDto.getUser(),contractDto.getDepartment(),user,department);//初始化初始任务
			taskInsDao.insert(taskIns0);
			
			processService.completeTask(task0.getId(),contractDto.getUser().getUserId(),"提交");											//执行初始化任务
			
			Task task1 = processService.findActivityTaskByProcessInsId(processInstance.getId());	//查询下一活动节点任务
			processService.taskAssign(task1.getId(), contractDto.getAssignUserId());						//将任务分配给指定办理人
			TaskIns taskIns1 = contractDto.initTaskIns(task1, processInstance.getId(), user,department,contractDto.getUser(),contractDto.getDepartment());
			taskInsDao.insert(taskIns1);
			
			processIns.setProcessInsId(processInstance.getId());
			processIns.setCurrentTaskId(task1.getId());		//当前活动任务节点id
			
			//设置当前办理人,办理机构
			processIns = contractDto.setProcessInsCurrentDealer(processIns,user,department);
			
			processIns.setStatus(StaticVariable.WAITSIGN);
			processIns.setStartTime(CalendarUtil.getDateTimeStr());
			processIns.setSignTime(StaticVariable.SIGNTIME);
			processIns.setIsActivity(StaticVariable.UNEND);
			processInsDao.insert(processIns);
			
			contractDao.update(contract);
			
		}catch (AppException ex) {
			throw ex;
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	@Override
	public ProcessIns findProcessInsByCode(String codeId) throws AppException {
		try{
			return processInsDao.findProcessInsByCode(codeId);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{codeId});
		}
	}
	@Override
	public ProcessIns findProcessInsByCode(String codeId,String processTypeName) throws AppException {
		try{
			return processInsDao.findProcessInsByCode(codeId,processTypeName);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{codeId,processTypeName});
		}
	}
	@Override
	public Contract findContractByCode(String codeId) throws AppException {
		try{
			return contractDao.findContractByCode(codeId);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{codeId});
		}
	}
	@Override
	public void signTask(String taskId, String businessId,Users user) throws AppException {
		try{
			ProcessIns processIns = processInsDao.selectBy(businessId);
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			processIns.setStatus(StaticVariable.WAITCHECK);
			processIns.setSignTime(CalendarUtil.getDateTimeStr());
			taskIns.setStatus(StaticVariable.WAITCHECK);
			taskIns.setSignTime(CalendarUtil.getDateTimeStr());
			processInsDao.update(processIns);
			taskInsDao.update(taskIns);
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000006");
		}
		
	}
	@Override
	public String passTask(String taskId, String businessId, ContractDto contractDto)throws AppException {
		try{
			ProcessIns processIns = processInsDao.selectBy(businessId);
			processService.completeTask(taskId,contractDto.getUser().getUserId(),StaticVariable.PASS);	//审核通过当前任务
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			
			Contract contract = contractDao.findContractByBusId(businessId);
			contract.setRemark(contractDto.getRemark());
			
			
			Task task1 = processService.findActivityTaskByProcessInsId(processIns.getProcessInsId());	//查询下一活动节点任务
			Users user = userService.findUser(task1.getAssignee(), Constants.BUSINESS_PBS);
			Department department = departmentService.findDepartment(user.getDepartmentId());

			taskIns = contractDto.updateTaskIns(taskIns,user,department);
			taskInsDao.update(taskIns);
			
			TaskIns taskIns1 = contractDto.initTaskIns(task1, processIns.getProcessInsId(),user,department,contractDto.getUser(),contractDto.getDepartment());
			taskInsDao.insert(taskIns1);
			processIns.setStatus(StaticVariable.WAITSUBMIT);
			processIns.setCurrentTaskId(task1.getId());
			
			//设置当前办理人,办理机构
			processIns = contractDto.setProcessInsCurrentDealer(processIns,user,department);
			
			processInsDao.update(processIns);
			contractDao.update(contract);
			return task1.getId();
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
	}
	@Override
	public void rejectTask(String taskId, String businessId,ContractDto contractDto) throws AppException {
		try{
			String attitude = "";
			Contract contract = contractDao.findContractByBusId(businessId);
			ProcessIns processIns = processInsDao.selectBy(businessId);
			if(contractDto.getUser().getUserId().equals(processIns.getCreateUserId())){
				attitude = StaticVariable.ENDCANCEL;
			}else{
				attitude = StaticVariable.REJECT;
			}
			processService.completeTask(taskId,contractDto.getUser().getUserId(),attitude);	
			if(attitude.equals(StaticVariable.ENDCANCEL)){
				contract.setHandStatus(StaticVariable.UNCONTRACTHAND);
				contract.setHandDate("");
				processIns.setStatus(StaticVariable.CANCEL);	
				processIns.setSignTime(StaticVariable.CANCEL);	
				processIns.setCompleteTime(CalendarUtil.getDateTimeStr());
				
				processIns.setCurrentTaskId("");
				processIns.setCurrentUserId("");
				processIns.setCurrentUserName("");
				processIns.setCurrentDepartmentId("");
				processIns.setCurrentDepartmentName("");
				
				processIns.setIsActivity(StaticVariable.END);	
				processInsDao.update(processIns);
				TaskIns taskIns = taskInsDao.selectBy(taskId);
				taskIns = contractDto.updateTaskIns(taskIns, contractDto.getUser(), contractDto.getDepartment());
				taskInsDao.update(taskIns);
			}else{
				TaskIns taskIns = taskInsDao.selectBy(taskId);
				Task task1 = processService.findActivityTaskByProcessInsId(processIns.getProcessInsId());	//查询下一活动节点任务
				Users user = userService.findUser(task1.getAssignee(), Constants.BUSINESS_PBS);
				Department department = departmentService.findDepartment(user.getDepartmentId());
				taskIns = contractDto.updateTaskIns(taskIns, user, department);
				taskInsDao.update(taskIns);
				TaskIns taskIns1 = contractDto.initTaskIns(task1, processIns.getProcessInsId(),user,department,contractDto.getUser(),contractDto.getDepartment());
				taskInsDao.insert(taskIns1);
				processIns.setStatus(StaticVariable.BACKSIGN);
				processIns.setSignTime(StaticVariable.SIGNTIME);
				processIns.setCurrentTaskId(task1.getId());
				
				//设置当前办理人,办理机构
				processIns = contractDto.setProcessInsCurrentDealer(processIns,user,department);
				
				processInsDao.update(processIns);
				contract.setRemark(contractDto.getRemark());
				contractDao.update(contract);
			}
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
		
	}
	@Override
	public void submitXinTuo(String taskId, String businessId,
			ContractDto contractDto) throws AppException {
		try{
			autoSignTask(taskId,businessId);
			ProcessIns processIns = processInsDao.selectBy(businessId);
			Contract contract = contractDao.findContractByBusId(businessId);
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			
			processService.completeTask(taskId,contractDto.getUser().getUserId(),StaticVariable.SUBMITXT);	
			Task task = processService.findActivityTaskByProcessInsId(processIns.getProcessInsId());	//查看当前流程实例是否具有活动任务
			Users user = userService.findUser(task.getAssignee(), Constants.BUSINESS_PBS);
			Department department = departmentService.findDepartment(user.getDepartmentId());
			taskIns = contractDto.updateTaskIns(taskIns, user, department);
			TaskIns newTaskIns = contractDto.initTaskIns(task,processIns.getProcessInsId(), user, department, contractDto.getUser(), contractDto.getDepartment());
			taskInsDao.update(taskIns);
			taskInsDao.insert(newTaskIns);
			
			processIns.setStatus(StaticVariable.WAITBACK);	
			processIns.setCurrentTaskId(task.getId());
			processInsDao.update(processIns);
			contract.setHandXinTuoDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));	
			contract.setHandStatus(StaticVariable.CONTRACTHAND);
			contractDao.update(contract);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
	}
	
	private void autoSignTask(String taskId, String businessId) throws AppException {
		try{
			ProcessIns processIns = processInsDao.selectBy(businessId);
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			processIns.setStatus(StaticVariable.WAITCHECK);
			taskIns.setStatus(StaticVariable.WAITCHECK);				//将任务实例状态更改为"待审核"
			taskIns.setSignTime(CalendarUtil.getDateTimeStr());
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000006");
		}
	}
	@Override
	public void xinTuoBack(String taskId, String businessId,
			ContractDto contractDto) throws AppException {
		try{
			autoSignTask(taskId,businessId);
			ProcessIns processIns = processInsDao.selectBy(businessId);
			Contract contract = contractDao.findContractByBusId(businessId);
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			
			processService.completeTask(taskId,contractDto.getUser().getUserId(),StaticVariable.XTBACK);	
			Task task = processService.findActivityTaskByProcessInsId(processIns.getProcessInsId());	//查看当前流程实例是否具有活动任务
			Users user = userService.findUser(task.getAssignee(), Constants.BUSINESS_PBS);
			Department department = departmentService.findDepartment(user.getDepartmentId());
			taskIns = contractDto.updateTaskIns(taskIns, user, department);
			TaskIns newTaskIns = contractDto.initTaskIns(task,processIns.getProcessInsId(), user, department, contractDto.getUser(), contractDto.getDepartment());
			taskInsDao.update(taskIns);
			taskInsDao.insert(newTaskIns);
			
			processIns.setStatus(StaticVariable.WAITGET);	
			processIns.setCurrentTaskId(task.getId());
			processInsDao.update(processIns);
			
			contract.setHandBackDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));	
			contract.setHandStatus(StaticVariable.CONTRACTBACK);
			contractDao.update(contract);
			
			Notice notice1 = new Notice();
			notice1.setNoticeId(UUIDGenerator.generateShortUuid());						//noticeId
			notice1.setNoticeType(StaticVariable.GETCONTRACTNOTICE);			   		//通知类型
			notice1.setNoticeTitle(generatorTitle(contract));							//通知标题
			notice1.setNoticeViewStatus(StaticVariable.UNSCANED);						//通知查阅状态
			notice1.setNoticeDealStatus(StaticVariable.UNDEAL);							//通知处理状态
			notice1.setNoticeArriveTime(CalendarUtil.getDateTimeStr());					//通知生成时间
			notice1.setAssBusinessId(contract.getContractId());							//关联的业务Id
			notice1.setDepartmentId(contract.getBelongDepartmentId());
			notice1.setNoticeFlag(StaticVariable.NOTICEONE);
			noticeDao.insert(notice1);
			
			if(!contract.getSignDepartmentId().equals(contract.getBelongDepartmentId())){
				Notice notice2 = new Notice();
				notice2.setNoticeId(UUIDGenerator.generateShortUuid());						//noticeId
				notice2.setNoticeType(StaticVariable.GETCONTRACTNOTICE);			   		//通知类型
				notice2.setNoticeTitle(generatorTitle(contract));							//通知标题
				notice2.setNoticeViewStatus(StaticVariable.UNSCANED);						//通知查阅状态
				notice2.setNoticeDealStatus(StaticVariable.UNDEAL);							//通知处理状态
				notice2.setNoticeArriveTime(CalendarUtil.getDateTimeStr());					//通知生成时间
				notice2.setAssBusinessId(contract.getContractId());							//关联的业务Id
				notice2.setDepartmentId(contract.getSignDepartmentId());
				notice2.setNoticeFlag(StaticVariable.NOTICEONE);
				noticeDao.insert(notice2);
			}
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
		
	}
	private String generatorTitle(Contract contract) {
		return "客户"+contract.getCustomeName()+"("+contract.getCustomeId()+")办理的理财产品合同已由信托返回,请速至分行领取!";
	}
	@Override
	public void getContract(String taskId, String businessId,
			ContractDto contractDto) throws AppException {
		try{
			autoSignTask(taskId,businessId);
			ProcessIns processIns = processInsDao.selectBy(businessId);
			Contract contract = contractDao.findContractByBusId(businessId);
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			
			processService.completeTask(taskId,contractDto.getUser().getUserId(),StaticVariable.GETCONTRACT);	
			
			taskIns.setNextUserId("无");
			taskIns.setNextUserName("无");
			taskIns.setNextDepartmentId("无");
			taskIns.setNextDepartment("无");	
			taskIns.setStatus(StaticVariable.COMPLETE);												//设置任务完成状态
			taskIns.setIsActivity(StaticVariable.COMPLETED);
			taskIns.setCompleteTime(CalendarUtil.getDateTimeStr());
			taskInsDao.update(taskIns);
		
			
			processIns.setStatus(StaticVariable.WAITGETTED);	
			processIns.setSignTime(StaticVariable.COMPLETE);
			
			processIns.setCurrentTaskId("");
			processIns.setCurrentUserId("");
			processIns.setCurrentUserName("");
			processIns.setCurrentDepartmentId("");
			processIns.setCurrentDepartmentName("");
			
			processIns.setCompleteTime(CalendarUtil.getDateTimeStr());
			processIns.setIsActivity(StaticVariable.END);
			processInsDao.update(processIns);
			
			contract.setGetContractDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));	
			contract.setHandStatus(StaticVariable.CONTRACTGET);
			contractDao.update(contract);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
		
	}
	@Override
	public Contract findContractByContractId(String contractId)
			throws AppException {
		try{
			return contractDao.findContractByContractId(contractId);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{contractId});
		}
	}
	@Override
	public ProductRedemptionInterval findContractRedempTimeById(
			String contractId) throws AppException {
		try{
			return contractDao.findContractRedempTimeById(contractId);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{contractId});
		}
	}
}
