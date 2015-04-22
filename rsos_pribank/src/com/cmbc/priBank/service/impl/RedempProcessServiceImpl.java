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
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.bean.RedemptionInfo;
import com.cmbc.funcmanage.dao.ContractDao;
import com.cmbc.funcmanage.dao.ProductRedemptionIntervalDao;
import com.cmbc.funcmanage.dao.RedempDao;
import com.cmbc.priBank.bean.RedempDto;
import com.cmbc.priBank.service.RedempProcessService;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.service.DepartmentService;
import com.cmbc.sa.service.UserService;

public class RedempProcessServiceImpl implements RedempProcessService {

	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ProcessService processService;
	private ProcessInsDao processInsDao;
	private TaskInsDao taskInsDao;
	private UserService userService;
	private DepartmentService departmentService;
	private ContractDao contractDao;
	private RedempDao redempDao;
	private ProductRedemptionIntervalDao productRedemptionIntervalDao;
	
	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	public void setRedempDao(RedempDao redempDao) {
		this.redempDao = redempDao;
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
	public void setProductRedemptionIntervalDao(
			ProductRedemptionIntervalDao productRedemptionIntervalDao) {
		this.productRedemptionIntervalDao = productRedemptionIntervalDao;
	}
	
	@Override
	public void startProcess(RedempDto redempDto) throws AppException {
		try{
			log.info("----startProcess...");
			RedemptionInfo redempInfo = new RedemptionInfo();
			String businessId = UUIDGenerator.generateShortUuid();
			ProcessIns processIns = redempDto.initProcessIns(businessId);							   
			processIns.setCodeId(redempDto.getCodeId());
			Contract contract = contractDao.selectBy(redempDto.getContractId());
			contract.setRedempStartDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			contract.setRedempStatus(StaticVariable.INCONTRACTRED);
			
			Users user = userService.findUser(redempDto.getAssignUserId(), Constants.BUSINESS_PBS);
			Department department = departmentService.findDepartment(user.getDepartmentId());
			
			redempInfo.setBusinessId(businessId);
			redempInfo.setContractId(redempDto.getContractId());
			redempInfo.setCodeId(redempDto.getCodeId());
			
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("createUserId", redempDto.getUser().getUserId());
			ProcessInstance processInstance = processService.startProcess(RedempDto.processKey, variables);
			
			Task task0 = processService.findActivityTaskByProcessInsId(processInstance.getId());								
			TaskIns taskIns0 = redempDto.initStartTaskIns(task0, processInstance,redempDto.getUser(),redempDto.getDepartment(),user,department);					
			taskInsDao.insert(taskIns0);
			
			processService.completeTask(task0.getId(),redempDto.getUser().getUserId(),"提交");										
			
			Task task1 = processService.findActivityTaskByProcessInsId(processInstance.getId());	
			processService.taskAssign(task1.getId(), redempDto.getAssignUserId());						
			TaskIns taskIns1 = redempDto.initTaskIns(task1, processInstance.getId(), user,department,redempDto.getUser(),redempDto.getDepartment());
			taskInsDao.insert(taskIns1);
			
			processIns.setProcessInsId(processInstance.getId());
			processIns.setCurrentTaskId(task1.getId());	
			
			//设置当前办理人,办理机构
			processIns = redempDto.setProcessInsCurrentDealer(processIns,user,department);
			
			processIns.setStatus(StaticVariable.WAITSIGN);
			processIns.setStartTime(CalendarUtil.getDateTimeStr());
			processIns.setSignTime(StaticVariable.SIGNTIME);
			processIns.setIsActivity(StaticVariable.UNEND);
			processInsDao.insert(processIns);
			redempDao.insert(redempInfo);
			contractDao.update(contract);
		}catch (AppException ex) {
			throw ex;
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
	}
	@Override
	public RedemptionInfo findRedempInfo(String businessId) throws AppException {
		try{
			return redempDao.selectBy(businessId);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{businessId});
		}
	}
	@Override
	public String passTask(String taskId, String businessId, RedempDto redempDto)
			throws AppException {
		try{
			ProcessIns processIns = processInsDao.selectBy(businessId);
			RedemptionInfo redempInfo = redempDao.selectBy(businessId);
			redempInfo.setRemark(redempDto.getRemark());
			Contract contract = contractDao.selectBy(redempInfo.getContractId());
			contract.setRedempConformDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			contract.setRedempStatus(StaticVariable.CONFIRMRED);
			String redemptionIntervalId = redempDto.getRedemptionIntervalId();
			if(redemptionIntervalId!=null&&redemptionIntervalId.length()>0){
				ProductRedemptionInterval pri = productRedemptionIntervalDao.selectBy(redemptionIntervalId);
				contractDao.setContractRedempTime(pri,contract.getContractId());
				contract.setRedemptionIntervalId(redemptionIntervalId);
			}
			processService.completeTask(taskId,redempDto.getUser().getUserId(),StaticVariable.PASS);	//审核通过当前任务
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			
			Task task1 = processService.findActivityTaskByProcessInsId(processIns.getProcessInsId());	//查询下一活动节点任务
			Users user = userService.findUser(task1.getAssignee(), Constants.BUSINESS_PBS);
			Department department = departmentService.findDepartment(user.getDepartmentId());

			taskIns = redempDto.updateTaskIns(taskIns,user,department);
			taskInsDao.update(taskIns);
			
			TaskIns taskIns1 = redempDto.initTaskIns(task1, processIns.getProcessInsId(),user,department,redempDto.getUser(),redempDto.getDepartment());
			taskInsDao.insert(taskIns1);
			processIns.setStatus(StaticVariable.WAITSUBMIT);
			processIns.setCurrentTaskId(task1.getId());
			
			//设置当前办理人,办理机构
			processIns = redempDto.setProcessInsCurrentDealer(processIns,user,department);
			
			processInsDao.update(processIns);
			contractDao.update(contract);
			return task1.getId();
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
	}
	@Override
	public void rejectTask(String taskId, String businessId, RedempDto redempDto)
			throws AppException {
		try{
			String attitude = "";
			ProcessIns processIns = processInsDao.selectBy(businessId);
			RedemptionInfo redempInfo = redempDao.selectBy(businessId);
			redempInfo.setRemark(redempDto.getRemark());
			Contract contract = contractDao.selectBy(redempInfo.getContractId());
			if(redempDto.getUser().getUserId().equals(processIns.getCreateUserId())){
				attitude = StaticVariable.ENDCANCEL;
			}else{
				attitude = StaticVariable.REJECT;
			}
			processService.completeTask(taskId,redempDto.getUser().getUserId(),attitude);	
			if(attitude.equals(StaticVariable.ENDCANCEL)){
				contract.setRedempStatus(StaticVariable.UNCONTRACTRED);
				contract.setRedempStartDate("");
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
				taskIns = redempDto.updateTaskIns(taskIns, redempDto.getUser(), redempDto.getDepartment());
				taskInsDao.update(taskIns);
			}else{
				TaskIns taskIns = taskInsDao.selectBy(taskId);
				Task task1 = processService.findActivityTaskByProcessInsId(processIns.getProcessInsId());	//查询下一活动节点任务
				Users user = userService.findUser(task1.getAssignee(), Constants.BUSINESS_PBS);
				Department department = departmentService.findDepartment(user.getDepartmentId());
				taskIns = redempDto.updateTaskIns(taskIns, user, department);
				taskInsDao.update(taskIns);
				TaskIns taskIns1 = redempDto.initTaskIns(task1, processIns.getProcessInsId(),user,department,redempDto.getUser(),redempDto.getDepartment());
				taskInsDao.insert(taskIns1);
				processIns.setStatus(StaticVariable.BACKSIGN);
				processIns.setSignTime(StaticVariable.SIGNTIME);
				processIns.setCurrentTaskId(task1.getId());
				
				//设置当前办理人,办理机构
				processIns = redempDto.setProcessInsCurrentDealer(processIns,user,department);
				
				processInsDao.update(processIns);
			}
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E777777");
		}
	}
	@Override
	public void submitXinTuo(String taskId, String businessId,RedempDto redempDto) throws AppException {
		try{
			autoSignTask(taskId,businessId);
			ProcessIns processIns = processInsDao.selectBy(businessId);
			RedemptionInfo redempInfo = redempDao.selectBy(businessId);
			Contract contract = contractDao.selectBy(redempInfo.getContractId());
			contract.setRedempDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			contract.setRedempStatus(StaticVariable.CONTRACTRED);
			
			TaskIns taskIns = taskInsDao.selectBy(taskId);
			processService.completeTask(taskId,redempDto.getUser().getUserId(),StaticVariable.SUBMITXT);
			
			taskIns.setNextUserId("无");
			taskIns.setNextUserName("无");
			taskIns.setNextDepartmentId("无");
			taskIns.setNextDepartment("无");	
			taskIns.setStatus(StaticVariable.COMPLETE);												//设置任务完成状态
			taskIns.setIsActivity(StaticVariable.COMPLETED);
			taskIns.setCompleteTime(CalendarUtil.getDateTimeStr());
			taskInsDao.update(taskIns);
			
			
			processIns.setStatus(StaticVariable.CONTRACTRED);	
			processIns.setSignTime(StaticVariable.COMPLETE);
			processIns.setCurrentTaskId("");
			
			processIns.setCurrentUserId("");
			processIns.setCurrentUserName("");
			processIns.setCurrentDepartmentId("");
			processIns.setCurrentDepartmentName("");
			
			processIns.setCompleteTime(CalendarUtil.getDateTimeStr());
			processIns.setIsActivity(StaticVariable.END);
			processInsDao.update(processIns);
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
	public RedemptionInfo findRedempInfoByCode(String codeId)
			throws AppException {
		try{
			return redempDao.findRedempInfoByCode(codeId);
		}catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000011",new String[]{codeId});
		}
	}

}
