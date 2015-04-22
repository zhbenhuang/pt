package com.cmbc.flow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.task.Task;

import com.cmbc.flow.bean.InstanceInformation;
import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.bean.TaskView;
import com.cmbc.flow.service.ProcessService;
import com.cmbc.funcmanage.service.ContractService;
import com.cmbc.funcmanage.service.ProductRedemptionIntervalService;
import com.cmbc.priBank.service.ContractProcessService;
import com.cmbc.priBank.service.RedempProcessService;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;

public class ProcessBaseAction extends BaseAction {

	private Logger logger = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 3861708495959715049L;
	protected ProcessService processService;
	
	protected ContractProcessService contractProcessService;
	protected RedempProcessService redempProcessService;
	protected ContractService contractService;
	protected ProductRedemptionIntervalService  productRedemptionIntervalService;
	
	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
	public void setContractProcessService(
			ContractProcessService contractProcessService) {
		this.contractProcessService = contractProcessService;
	}
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public void setRedempProcessService(RedempProcessService redempProcessService) {
		this.redempProcessService = redempProcessService;
	}


	public void setProductRedemptionIntervalService(
			ProductRedemptionIntervalService productRedemptionIntervalService) {
		this.productRedemptionIntervalService = productRedemptionIntervalService;
	}


	protected int total;
	protected int currentPages;
	protected int pageSize;
	private List<InstanceInformation> instanceInformationList = new ArrayList<InstanceInformation>();
	private String businessId;
	private String taskId;
	private String status;
	private String business;
	/**
	 * 
	 * 查询当前登录用户待处理的任务列表
	 */
	public void waitDealTasksAction(){
		logger.info("待处理任务列表...");
		try{
			printParameters();
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> condition = new HashMap<String,Object>();
			condition = setCondition(condition);
			List<InstanceInformation> instanceInformationList = new ArrayList<InstanceInformation>();
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			instanceInformationList = processService.findWaitByConditions("waitDealProcessTask_View",currentPage,pageSize,user.getUserId(),condition);
			int total = processService.getWaitTotal("waitDealProcessTask_View",user.getUserId(),condition);			
			
			EasyGridList<InstanceInformation> instancelist = new EasyGridList<InstanceInformation>();
			instancelist.setRows(instanceInformationList);
			instancelist.setTotal(total);
			instancelist.setRetCode(Constants.RETCODE_00000);
			instancelist.setMessage(getText(Constants.RETCODE_00000));
			logger.info(getText(Constants.RETCODE_00000)+"---"+instancelist.toJson());
			writeJsonSuccess(instancelist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 查询当前登录用户已处理但未完结的任务列表
	 */
	public void completedTasksAction(){
		logger.info("已处理任务列表...");
		try{
			printParameters();
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> condition = new HashMap<String,Object>();
			condition = setCondition(condition);
			List<InstanceInformation> instanceInformationList = new ArrayList<InstanceInformation>();
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			instanceInformationList = processService.findCompleteByConditions("completedProcessTask_View",currentPage,pageSize,user.getUserId(),condition);
			int total = processService.getCompleteTotal("completedProcessTask_View",user.getUserId(),condition);
			
			EasyGridList<InstanceInformation> instancelist = new EasyGridList<InstanceInformation>();
			instancelist.setRows(instanceInformationList);
			instancelist.setTotal(total);
			instancelist.setRetCode(Constants.RETCODE_00000);
			instancelist.setMessage(getText(Constants.RETCODE_00000));
			logger.info(getText(Constants.RETCODE_00000)+"---"+instancelist.toJson());
			writeJsonSuccess(instancelist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询当前登录用户处理过，并且完结的任务列表
	 */
	public void finishedTasksAction(){
		logger.info("已处理任务列表...");
		try{
			printParameters();
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> condition = new HashMap<String,Object>();
			condition = setCondition(condition);
			List<InstanceInformation> instanceInformationList = new ArrayList<InstanceInformation>();
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			instanceInformationList = processService.findFinishedByConditions("finishedProcessTask_View",currentPage,pageSize,user.getUserId(),condition);
			int total = processService.getFinishedTotal("finishedProcessTask_View",user.getUserId(),condition);
			
			EasyGridList<InstanceInformation> instancelist = new EasyGridList<InstanceInformation>();
			instancelist.setRows(instanceInformationList);
			instancelist.setTotal(total);
			instancelist.setRetCode(Constants.RETCODE_00000);
			instancelist.setMessage(getText(Constants.RETCODE_00000));
			logger.info(getText(Constants.RETCODE_00000)+"---"+instancelist.toJson());
			writeJsonSuccess(instancelist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 查询当前登录用户待处理的任务列表
	 */
	public String waitTasks(){
		try{
			printParameters();
			Map<String,Object> condition = new HashMap<String,Object>();
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			instanceInformationList =processService.findWaitByConditions("waitDealProcessTask_View",currentPages,pageSize,user.getUserId(),condition);
			total = processService.getWaitTotal("waitDealProcessTask_View",user.getUserId(),condition);
			business = user.getBusiness().toString();
			return "waitDealTasks";
		}catch(Exception ex){
			ex.printStackTrace();
			return "waitDealTasks";
		}
	}
	
	public void isMarketUser(){
		try{
			String message = "";
			UsersRole userRole = (UsersRole)getSession().getAttribute(GlobalConstants.USERROLE_KEY);
			int userRoleId = Integer.parseInt(userRole.getRoleId());
			if(userRoleId==6){  //处于"市场人员补资料"
				message = "{'retCode':'A000000','isMarket':'true'}";
			}else{
				message = "{'retCode':'A000000','isMarket':'false'}";
			}
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch (Exception e) {
			String message = "{'retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	public String dealWaitTask(){
		try{
			printParameters();
			Task task = processService.findTaskByTaskId(taskId);
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			business = user.getBusiness().toString();
			UsersRole userRole = (UsersRole)getSession().getAttribute(GlobalConstants.USERROLE_KEY);
			if(task==null){
				return "success";
			}else if(userRole!=null){
				int userRoleId = Integer.parseInt(userRole.getRoleId());
				ProcessIns processIns = (ProcessIns)processService.findProcessIns(businessId);
				status = processIns.getStatus();
				if(userRoleId==9){
					return "branchManager";
				}else if(userRoleId==8){
					return "redemption";
				}else if(userRoleId==7){
					return "contract";
				}
				return "dealWaitTask";
			}else{
				return "error";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	public void findProcessInsId(){
		try{
			printParameters();
			String businessId = getHttpRequest().getParameter("businessId");
			ProcessDefinition processDefinition = null;
			ProcessIns processIns = processService.findProcessIns(businessId);
			
			if(processIns.getIsActivity().equals(StaticVariable.UNEND)){
				processDefinition = processService.findProcessDefinition(processIns.getProcessInsId());
			}else{
				processDefinition = processService.findProcessDefinitionByHistoryIns(processIns.getProcessInsId());
			}
			String deploymentId = processDefinition.getDeploymentId();
			String processPngName = processDefinition.getImageResourceName();
			String result = "{'retCode':'"+Constants.RETCODE_00000+"'," +
							"'processInsId':'"+processIns.getProcessInsId()+"'," +
							"'deploymentId':'"+deploymentId+"'," +
							"'processPngName':'"+processPngName+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(result);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void tracingProcess(){
		try{
			logger.info("-------tracingProcess--------");
			printParameters();
			String taskId = getHttpRequest().getParameter("taskId");
			String businessId = getHttpRequest().getParameter("businessId");
			ProcessIns processIns = processService.findProcessIns(businessId);
			QueryFlowDto queryDto = new QueryFlowDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setTaskId(taskId);
			queryDto.setProcessInsId(processIns.getProcessInsId());
			EasyGridList<TaskView> list = processService.tracingProcess(queryDto);
			list.setRetCode(Constants.RETCODE_00000);
			list.setMessage(getText(Constants.RETCODE_00000));
			logger.info(getText(Constants.RETCODE_00000)+"---"+list.toJson());
			writeJsonSuccess(list.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 检测条码是否可用
	 */
	public void checkCodeId(){
		String message = "";
		try {
			String codeId = getHttpRequest().getParameter("codeId");
			ProcessIns processIns = contractProcessService.findProcessInsByCode(codeId);
			if(processIns!=null){
				message = "{'message':'条码已被使用,不可重复使用!','retCode':'"+Constants.RETCODE_888888+"'}";
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
				this.writeJsonSuccess(jsonObject.toString());
			}else{
				message = "{'message':'条码可正常使用!','retCode':'"+Constants.RETCODE_00000+"'}";
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
				this.writeJsonSuccess(jsonObject.toString());
			}
		} catch(AppException e){
			message = "{'retCode':'"+Constants.RETCODE_999999+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		} catch (Exception e) {
			message = "{'retCode':'"+Constants.RETCODE_999999+"'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	
	
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setInstanceInformationList(List<InstanceInformation> instanceInformationList) {
		this.instanceInformationList = instanceInformationList;
	}
	public List<InstanceInformation> getInstanceInformationList() {
		return instanceInformationList;
	}
	public int getCurrentPages() {
		return currentPages;
	}
	public void setCurrentPages(int currentPages) {
		this.currentPages = currentPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
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
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getBusiness() {
		return business;
	}



}
