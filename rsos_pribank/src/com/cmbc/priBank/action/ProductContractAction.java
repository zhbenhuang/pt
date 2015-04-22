package com.cmbc.priBank.action;

import java.io.Serializable;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;

import com.cmbc.flow.action.ProcessBaseAction;
import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.RedemptionInfo;
import com.cmbc.priBank.bean.ContractDto;
import com.cmbc.priBank.bean.RedempDto;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;

public class ProductContractAction extends ProcessBaseAction implements Serializable {
	private static final long serialVersionUID = -6159247847363230774L;
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	
	private String message;
	private String taskId;
	private String status;
	private String redCode;
	private Contract contract;
	private RedemptionInfo redemptionInfo;
	private String businessId;
	private String processTypeName;
	
	/**
	 * 启动理财产品合同流程
	 * @return
	 */
	public void startContractAction(){
		try {
			printParameters();
			String codeId = getHttpRequest().getParameter("codeId");
			ProcessIns processIns = contractProcessService.findProcessInsByCode(codeId);
			if(processIns==null){
				ContractDto contractDto = new ContractDto();
				contractDto = this.generatorContractDto(contractDto);
				contractProcessService.startProcess(contractDto);
				EasyResult result = new EasyResult(Constants.RETCODE_00000, getText(Constants.RETCODE_00000));
				writeJsonSuccess(result.toJson());
			}else{
				EasyResult result = new EasyResult(Constants.RETCODE_000019, getText(Constants.RETCODE_000019));
				writeJsonSuccess(result.toJson());
			}
		} catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	private ContractDto generatorContractDto(ContractDto contractDto) {
		String remark  = getHttpRequest().getParameter("remark");
		String customeId = getHttpRequest().getParameter("customeId");
		String customeName = getHttpRequest().getParameter("customeName");
		String assignUserId = getHttpRequest().getParameter("assignUserId");
		String contractId = getHttpRequest().getParameter("contractId");
		String codeId = getHttpRequest().getParameter("codeId");
		if(assignUserId!=null&&assignUserId.length()>0){
			int indexBegin = assignUserId.indexOf("(");															//由界面传入的assignUserId格式为：assignUserName(assignUserId)
			int indexEnd = assignUserId.indexOf(")");
			assignUserId = assignUserId.substring(indexBegin+1, indexEnd);
			contractDto.setAssignUserId(assignUserId);
		}
		if(remark!=null&&remark.length()>0){
			contractDto.setRemark(remark);
		}else{
			contractDto.setRemark("");
		}
		Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
		Department department = (Department)getSession().getAttribute(GlobalConstants.LOGIN_BRANCH);
		contractDto.setCustomeId(customeId);
		contractDto.setCustomeName(customeName);
		contractDto.setUser(user);
		contractDto.setDepartment(department);
		contractDto.setContractId(contractId);
		contractDto.setCodeId(codeId);
		return contractDto;
	}

	/**
	 * 查询需要显示的审核信息
	 * @return
	 */
	public String showContractInfoAction(){
		String currentPage = getHttpRequest().getParameter("currentPage");
		try{
			printParameters();
			message = "";
			taskId = "";
			status = "";
			redCode = "";
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String codeId = getHttpRequest().getParameter("codeId");
			ProcessIns processIns = contractProcessService.findProcessInsByCode(codeId,ContractDto.processTypeName);
			if(processIns!=null){
				if(processIns.getIsActivity().equals(StaticVariable.END)){
					message = "该合同流程已经结束!";
					redCode = Constants.RETCODE_999999;
				}else{
					taskId = processIns.getCurrentTaskId();
					Task task = processService.findTaskByTaskId(taskId);
					if(user.getUserId().equals(task.getAssignee())){
						status = processIns.getStatus();
						contract = contractProcessService.findContractByCode(codeId);
						redCode = Constants.RETCODE_00000;
					}else{
						message = "您不具备处理该合同的权限!";
						redCode = Constants.RETCODE_999999;
					}
				}
			}else{
					message = "无此合同流程信息!";
					redCode = Constants.RETCODE_999999;
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "系统异常,请联系管理员!";
			redCode = Constants.RETCODE_999999;
		}
		return currentPage;
	}
	
	public String viewContractInfoAction(){
		String currentPage = getHttpRequest().getParameter("currentPage");
		try{
			printParameters();
			String businessId = getHttpRequest().getParameter("businessId");
			ProcessIns processIns = processService.findProcessIns(businessId);
			contract = contractProcessService.findContractByCode(processIns.getCodeId());
			status = processIns.getStatus();
			redCode = Constants.RETCODE_00000;
		}catch(Exception e){
			e.printStackTrace();
			message = "系统异常,请联系管理员!";
			redCode = Constants.RETCODE_999999;
		}
		return currentPage;
	}
	
	/**
	 * 查询需要显示的审核信息
	 * @return
	 */
	public String showProcessInfoAction(){
		String currentPage = getHttpRequest().getParameter("currentPage");
		try{
			printParameters();
			message = "";
			taskId = "";
			status = "";
			redCode = "";
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String codeId = getHttpRequest().getParameter("codeId");
			ProcessIns processIns = contractProcessService.findProcessInsByCode(codeId);
			if(processIns!=null){
				if(processIns.getIsActivity().equals(StaticVariable.END)){
					message = "该流程已经结束!";
					redCode = Constants.RETCODE_999999;
				}else{
					taskId = processIns.getCurrentTaskId();
					Task task = processService.findTaskByTaskId(taskId);
					if(user.getUserId().equals(task.getAssignee())){
						status = processIns.getStatus();
						if(processIns.getProcessTypeName().equals(ContractDto.processTypeName)){
							contract = contractProcessService.findContractByCode(codeId);
							processTypeName = ContractDto.processTypeName;
						}else{
							redemptionInfo = redempProcessService.findRedempInfoByCode(codeId);
							contract = contractProcessService.findContractByContractId(redemptionInfo.getContractId());
							processTypeName = RedempDto.processTypeName;
						}
						redCode = Constants.RETCODE_00000;
					}else{
						message = "您不具备处理该流程的权限!";
						redCode = Constants.RETCODE_999999;
					}
				}
			}else{
					message = "无此流程信息!";
					redCode = Constants.RETCODE_999999;
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "系统异常,请联系管理员!";
			redCode = Constants.RETCODE_999999;
		}
		return currentPage;
	}
	
	
	/**
	 * 签收任务
	 */
	public void signTaskAction(){
		try {
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			contractProcessService.signTask(taskId,businessId,user);
			EasyResult result = new EasyResult(Constants.RETCODE_00000, getText(Constants.RETCODE_00000));
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过审核
	 */
	public void passAction(){
		try{
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			ContractDto contractDto = new ContractDto();
			contractDto = this.generatorContractDto(contractDto);
			String newTaskId = contractProcessService.passTask(taskId,businessId,contractDto);
			String json = "{'status':'待提交信托', 'message':'审核成功!','taskId':'"+newTaskId+"','retCode':'"+Constants.RETCODE_00000+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(json);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 未通过审核
	 * 
	 */
	public void rejectAction(){
		try{
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			ContractDto contractDto = new ContractDto();
			contractDto = this.generatorContractDto(contractDto);
			contractProcessService.rejectTask(taskId,businessId,contractDto);
			EasyResult result = new EasyResult(Constants.RETCODE_00000, getText(Constants.RETCODE_00000));
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 提交信托
	 */
	public void submitXinTuoAction(){
		try{
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			ContractDto contractDto = new ContractDto();
			contractDto = this.generatorContractDto(contractDto);
			contractProcessService.submitXinTuo(taskId,businessId,contractDto);
			String json = "{'message':'提交信托成功!','retCode':'"+Constants.RETCODE_00000+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(json);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 信托返回
	 */
	public void xinTuoBackAction(){
		try{
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			ContractDto contractDto = new ContractDto();
			contractDto = this.generatorContractDto(contractDto);
			contractProcessService.xinTuoBack(taskId,businessId,contractDto);
			String json = "{'message':'信托返回成功!','retCode':'"+Constants.RETCODE_00000+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(json);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
		
	/**
	 * 信托返回后,支行需要将合同领走	
	 */
	public void getContractAction(){
		try{
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			ContractDto contractDto = new ContractDto();
			contractDto = this.generatorContractDto(contractDto);
			contractProcessService.getContract(taskId,businessId,contractDto);
			String json = "{'message':'合同领取成功,流程结束!','retCode':'"+Constants.RETCODE_00000+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(json);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	



	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getRedCode() {
		return redCode;
	}
	public void setRedCode(String redCode) {
		this.redCode = redCode;
	}

	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public RedemptionInfo getRedemptionInfo() {
		return redemptionInfo;
	}

	public void setRedemptionInfo(RedemptionInfo redemptionInfo) {
		this.redemptionInfo = redemptionInfo;
	}

	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}

	public String getProcessTypeName() {
		return processTypeName;
	}


}
