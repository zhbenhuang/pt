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
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.bean.RedemptionInfo;
import com.cmbc.priBank.bean.RedempDto;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;

public class ProductRedempAction extends ProcessBaseAction implements
		Serializable {
	private static final long serialVersionUID = -8017648443299767400L;
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	
	private String message;
	private String taskId;
	private String status;
	private String redCode;
	private Contract contract;
	private RedemptionInfo redemptionInfo;
	private ProductRedemptionInterval productRedemptionInterval;
	private String businessId;
	
	/**
	 * 启动理财产品赎回流程
	 * @return
	 */
	public void startRedempAction(){
		try {
			printParameters();
			String codeId = getHttpRequest().getParameter("codeId");
			ProcessIns processIns = contractProcessService.findProcessInsByCode(codeId);
			if(processIns==null){
				RedempDto redempDto = new RedempDto();
				redempDto = this.generatorRedempDto(redempDto);
				redempProcessService.startProcess(redempDto);
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

	private RedempDto generatorRedempDto(RedempDto redempDto) {
		String remark  = getHttpRequest().getParameter("remark");
		String customeId = getHttpRequest().getParameter("customeId");
		String customeName = getHttpRequest().getParameter("customeName");
		String assignUserId = getHttpRequest().getParameter("assignUserId");
		String contractId = getHttpRequest().getParameter("contractId");
		String codeId = getHttpRequest().getParameter("codeId");
		String redemptionIntervalId = getHttpRequest().getParameter("redemptionIntervalId");
		if(assignUserId!=null&&assignUserId.length()>0){
			int indexBegin = assignUserId.indexOf("(");															//由界面传入的assignUserId格式为：assignUserName(assignUserId)
			int indexEnd = assignUserId.indexOf(")");
			assignUserId = assignUserId.substring(indexBegin+1, indexEnd);
			redempDto.setAssignUserId(assignUserId);
		}
		if(remark!=null&&remark.length()>0){
			redempDto.setRemark(remark);
		}else{
			redempDto.setRemark("");
		}
		Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
		Department department = (Department)getSession().getAttribute(GlobalConstants.LOGIN_BRANCH);
		redempDto.setCustomeId(customeId);
		redempDto.setCustomeName(customeName);
		redempDto.setUser(user);
		redempDto.setDepartment(department);
		redempDto.setContractId(contractId);
		redempDto.setCodeId(codeId);
		redempDto.setRedemptionIntervalId(redemptionIntervalId);
		return redempDto;
	}
	
	/**
	 * 查询需要显示的审核信息
	 * @return
	 */
	public String showRedempInfoAction(){
		String currentPage = getHttpRequest().getParameter("currentPage");
		try{
			printParameters();
			message = "";
			taskId = "";
			status = "";
			redCode = "";
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String codeId = getHttpRequest().getParameter("codeId");
			ProcessIns processIns = contractProcessService.findProcessInsByCode(codeId,RedempDto.processTypeName);
			if(processIns!=null){
				if(processIns.getIsActivity().equals(StaticVariable.END)){
					message = "该赎回流程已经结束!";
					redCode = Constants.RETCODE_999999;
				}else{
					taskId = processIns.getCurrentTaskId();
					Task task = processService.findTaskByTaskId(taskId);
					if(user.getUserId().equals(task.getAssignee())){
						status = processIns.getStatus();
						redemptionInfo = redempProcessService.findRedempInfo(processIns.getBusinessId());
						contract = contractProcessService.findContractByContractId(redemptionInfo.getContractId());
						if(contract.getRedempStatus().equals(StaticVariable.CONFIRMRED)||contract.getRedempStatus().equals(StaticVariable.CONTRACTRED)){
							productRedemptionInterval = contractProcessService.findContractRedempTimeById(contract.getContractId());
						}
						redCode = Constants.RETCODE_00000;
					}else{
						message = "您不具备处理该赎回流程的权限!";
						redCode = Constants.RETCODE_999999;
					}
				}
			}else{
				message = "无此赎回流程信息!";
				redCode = Constants.RETCODE_999999;
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "系统异常,请联系管理员!";
			redCode = Constants.RETCODE_999999;
		}
		return currentPage;
	}
	
	public String viewRedempInfoAction(){
		String currentPage = getHttpRequest().getParameter("currentPage");
		try{
			printParameters();
			String businessId = getHttpRequest().getParameter("businessId");
			ProcessIns processIns = processService.findProcessIns(businessId); 
			redemptionInfo = redempProcessService.findRedempInfo(businessId);
			contract = contractProcessService.findContractByContractId(redemptionInfo.getContractId());
			
			if(contract.getRedempStatus().equals(StaticVariable.CONFIRMRED)||contract.getRedempStatus().equals(StaticVariable.CONTRACTRED)){
				productRedemptionInterval = contractProcessService.findContractRedempTimeById(contract.getContractId());
			}
			
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
	 * 通过审核
	 */
	public void passAction(){
		try{
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			RedempDto redempDto = new RedempDto();
			redempDto = this.generatorRedempDto(redempDto);
			String newTaskId = redempProcessService.passTask(taskId,businessId,redempDto);
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
			RedempDto redempDto = new RedempDto();
			redempDto = this.generatorRedempDto(redempDto);
			redempProcessService.rejectTask(taskId,businessId,redempDto);
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
			RedempDto redempDto = new RedempDto();
			redempDto = this.generatorRedempDto(redempDto);
			redempProcessService.submitXinTuo(taskId,businessId,redempDto);
			String json = "{'message':'提交信托成功,流程结束!','retCode':'"+Constants.RETCODE_00000+"'}";
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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setRedemptionInfo(RedemptionInfo redemptionInfo) {
		this.redemptionInfo = redemptionInfo;
	}

	public RedemptionInfo getRedemptionInfo() {
		return redemptionInfo;
	}
	public void setProductRedemptionInterval(ProductRedemptionInterval productRedemptionInterval) {
		this.productRedemptionInterval = productRedemptionInterval;
	}

	public ProductRedemptionInterval getProductRedemptionInterval() {
		return productRedemptionInterval;
	}
}
