package com.cmbc.funcmanage.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;




import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;
import rsos.framework.utils.UploadConfigurationRead;

import com.cmbc.funcmanage.bean.BranchContractView;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.NoticeView;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.service.MainService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ContractAction extends FuncBaseAction {

	private static final long serialVersionUID = 1L;
	protected static final Log log = LogFactory.getLog(ContractAction.class);
	private String businessId;
	private String customeId;
	private String customeName;
	private String saleDate;
	private String productType;
	private String productCode;
	private String productName;
	private String money;
	private String signAccount;
	private String saleManager;
	private String businessManager;
	private String belongDepartment;
	private String belongDepartmentId;
	private String signDepartment;
	private String signDepartmentId;
	
	private String fileName;
	private String fileNewName;

	private int currentPages;
	private int pageSize;
	private int total;
	private String business;
	private String message;
	public List<NoticeView> noticeViewList = new ArrayList<NoticeView>();
	/** 文件对象 */
	private List<File> Filedata;
	private MainService mainService;

	/**
	 * 按条件查询合同信息
	 */
	public void getContractList(){
		try{
			String openDayBegin = getHttpRequest().getParameter("openDayBegin");
			String openDayEnd = getHttpRequest().getParameter("openDayEnd");
			String redeemBegin = getHttpRequest().getParameter("redeemBegin");
			String redeemEnd = getHttpRequest().getParameter("redeemEnd");
			String pages = getHttpRequest().getParameter("page");
			String rows = getHttpRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> m = new HashMap<String,Object>();
			m = m1(m);				//添加查询条件
			List<Contract> clist = new ArrayList<Contract>();
			if((openDayBegin!=null&&openDayBegin.length()>0)||(openDayEnd!=null&&openDayEnd.length()>0)
					   ||(redeemBegin!=null&&redeemBegin.length()>0)||(redeemEnd!=null&&redeemEnd.length()>0)){
				clist = this.contractService.findByPagination("productContract_View",currentPage , pageSize , m);
				total = this.contractService.getTotalNew("productContract_View",m);
			}else{
				clist = this.contractService.findByPage("contractView",currentPage,pageSize,m);
				total = this.contractService.getTotal("contractView",m);
			}
			EasyGridList<Contract> plist = new EasyGridList<Contract>();
			plist.setRows(clist);
			plist.setTotal(total);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存合同信息
	 */
	public void saveContract(){
		try{
			Contract contract = new Contract();
			contract = generatorObject();
			contract.setAddDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			contract.setModifyDate("");
			contract.setHandStatus(StaticVariable.UNCONTRACTHAND);
			contract.setRedempStatus(StaticVariable.UNCONTRACTRED);
			contract.setNoticeStatus(StaticVariable.UNNOTICE);
			contractService.saveContract(contract);
			String message = "{'message':'合同信息保存成功!','retCode':'A000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			String message = "{'message':'合同信息保存失败!','retCode':'E000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}catch (Exception e) {
			String message = "{'message':'系统故障,请联系管理员!','retCode':'E000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 系统导入合同
	 */
	public String inputContract(){
		try{			
			printParameters();
			int size = Filedata.size();
			for(int i=0;i<size;i++){
				contractService.inputContractByPIO(Filedata.get(i));
			}			
			return "successTwo";
		} catch(AppException e){			
			e.printStackTrace();
			addErrors(e);
			return Action.ERROR;
		} catch (Exception e) {			
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
			return Action.ERROR;
		}
		
	}
	
	/*下载合同导入模板*/
	public InputStream getDownloadFile() throws Exception {			
		fileName = "合同导入模板示例.xls";
		fileNewName = URLEncoder.encode(fileName, "utf-8");
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/"
					+ UploadConfigurationRead.getInstance()
							.getConfigItem("cfTemplatePath").trim());
		File file = new File(targetDirectory + "/" + fileName);
		InputStream inputStream = new FileInputStream(file);
		return inputStream;
	}
	/**
	 * 修改更新合同信息
	 */
	public void updateContract(){
		try{
			Contract contract = new Contract();
			contract = generatorObject();
			contract.setModifyDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			contractService.modifyContract(contract);
			String message = "{'message':'合同信息更新成功!','retCode':'A000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			String message = "{'message':'合同信息更新失败!','retCode':'E000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch (Exception e) {
			String message = "{'message':'系统故障,请联系管理员!','retCode':'E000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除合同信息
	 */
	@SuppressWarnings("unchecked")
	public void deleteContract(){	
		try{
			Map maps = (Map)ActionContext.getContext().getParameters();
			String[] contractIds = (String[])maps.get("contractIds");
			contractService.deleteContract(contractIds);
			String message = "{'message':'删除成功!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			String message = "{'message':'删除失败!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch (Exception e) {
			String message = "{'message':'系统故障,请联系管理员!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据客户号查询用户的签约合同信息,用以启动合同流程
	 */
	public void getCustomeConList(){
		try{
			printParameters();
			String pages = getHttpRequest().getParameter("page");
			String rows = getHttpRequest().getParameter("rows");
			String table = getHttpRequest().getParameter("table");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Department department = (Department)ActionContext.getContext().getSession().get(GlobalConstants.LOGIN_BRANCH);
			Map<String,Object> m = new HashMap<String,Object>();
			m=m1(m);
			m.put("contractInfoStatus", StaticVariable.FULL);
			m.put("departmentId", department.getDepartmentId());
			List<Contract> clist = this.contractService.findByPage(table,currentPage,pageSize,m);
			int total = this.contractService.getTotal(table,m);
			
			EasyGridList<Contract> contractlist = new EasyGridList<Contract>();
			contractlist.setRows(clist);
			contractlist.setTotal(total);
			contractlist.setRetCode(Constants.RETCODE_00000);
			contractlist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+contractlist.toJson());
			writeJsonSuccess(contractlist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void getBranchContractList(){
		try{
			printParameters();
			String pages = getHttpRequest().getParameter("page");
			String rows = getHttpRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Department department = (Department)ActionContext.getContext().getSession().get(GlobalConstants.LOGIN_BRANCH);
			Map<String,Object> m = new HashMap<String,Object>();
			m=m1(m);
			m.put("contractInfoStatus", StaticVariable.FULL);
			m.put("departmentId", department.getDepartmentId());
			List<BranchContractView> clist = this.contractService.findBranchContractByPage("branchContractView",currentPage,pageSize,m);
			int total = this.contractService.getBranchContractTotal("branchContractView",m);
			EasyGridList<BranchContractView> contractlist = new EasyGridList<BranchContractView>();
			contractlist.setRows(clist);
			contractlist.setTotal(total);
			contractlist.setRetCode(Constants.RETCODE_00000);
			contractlist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+contractlist.toJson());
			writeJsonSuccess(contractlist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void getBranchContractProductList(){
		try{
			printParameters();
			String pages = getHttpRequest().getParameter("page");
			String rows = getHttpRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Department department = (Department)ActionContext.getContext().getSession().get(GlobalConstants.LOGIN_BRANCH);
			Map<String,Object> m = new HashMap<String,Object>();
			m = m1(m);				//添加查询条件
			m.put("contractInfoStatus", StaticVariable.FULL);
			m.put("departmentId", department.getDepartmentId());
			List<BranchContractView> clist = new ArrayList<BranchContractView>();
			clist = this.contractService.findBranchProductContract(currentPage , pageSize , m);
			total = this.contractService.getTotalBranchProductContract(m);
			EasyGridList<BranchContractView> plist = new EasyGridList<BranchContractView>();
			plist.setRows(clist);
			plist.setTotal(total);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void getContractByContractId(){
		try{
			printParameters();
			String contractId = getHttpRequest().getParameter("contractId");
			Contract contract = new Contract();
			contract = this.contractService.findContractByContractId(contractId);
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(contract);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * form表单传入数据形成Contract对象
	 * @return
	 */
	private Contract generatorObject() throws AppException{
		Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY); 
		Contract contract = new Contract();
		String contractId = getHttpRequest().getParameter("contractId");
		if(contractId==null||contractId.equals("")){
			contractId = UUIDGenerator.generateShortUuid();
			contract.setContractId(contractId);
		}else{
			contract = contractService.findById(contractId);
		}
		
		contract.setCustomeId(customeId);
		contract.setProductCode(productCode);
		contract.setProductName(productName);
		
		contract.setCustomeName(customeName);
		contract.setSaleDate(saleDate);
		contract.setProductType(productType);
		contract.setMoney(money);
		contract.setSignAccount(signAccount);
		contract.setSaleManager(saleManager);
		contract.setBusinessManager(businessManager);
		contract.setBelongDepartment(belongDepartment);
		contract.setBelongDepartmentId(belongDepartmentId);
		contract.setSignDepartment(signDepartment);
		contract.setSignDepartmentId(signDepartmentId);

		contract.setOperatorName(user.getUsername());
		contract.setContractInfoStatus(StaticVariable.FULL);
		return contract;
	}
	
	public void getNoticesList(){
		try{
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			UsersRole uerRole = (UsersRole)ActionContext.getContext().getSession().get(GlobalConstants.USERROLE_KEY);
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			int roleId 		= Integer.parseInt(uerRole.getRoleId());
			Users users = (Users)ActionContext.getContext().getSession().get(GlobalConstants.USER_INFORMATION_KEY);
			String departmentId = users.getDepartmentId();	//获取当前用户所在的机构	
			List<NoticeView> nlist = new ArrayList<NoticeView>();
			Map<String,Object> m = new HashMap<String,Object>();
			m=m1(m);
			if(roleId==9){
				m.put("departmentId", departmentId);			//下面是查询和合同有关的通知,那么这些合同通知就会通知到签约机构、归属机构下面的支行理财经理
				m.put("noticeFlag", "1");
				m.put("noticeDealStatus", StaticVariable.UNDEAL);
				nlist = this.contractService.findNoticesByPage("noticeView",currentPage,pageSize,m);
				total = this.contractService.getNoticeTotal("noticeView", m);
			}else if(roleId==7||roleId==8){
				m.put("departmentId", departmentId);
				m.put("noticeDealStatus", StaticVariable.UNDEAL);
				nlist = this.contractService.findNoticesByPage("noticeView",currentPage,pageSize,m);
				total = this.contractService.getNoticeTotal("noticeView", m);
			}
			EasyGridList<NoticeView> noticelist = new EasyGridList<NoticeView>();
			noticelist.setRows(nlist);
			noticelist.setTotal(total);
			noticelist.setRetCode(Constants.RETCODE_00000);
			noticelist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+noticelist.toJson());
			writeJsonSuccess(noticelist.toJson());				
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 获取已处理了的通知列表
	 */
	public void getDealedNoticesList(){
		try{
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			UsersRole uerRole = (UsersRole)getSession().getAttribute(GlobalConstants.USERROLE_KEY);
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			int roleId 		= Integer.parseInt(uerRole.getRoleId());
			Users users = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String departmentId = users.getDepartmentId();	//获取当前用户所在的机构	
			List<NoticeView> nlist = new ArrayList<NoticeView>();
			Map<String,Object> m = new HashMap<String,Object>();
			m=m1(m);
			if(roleId==9){
				m.put("departmentId", departmentId);			//下面是查询和合同有关的通知,那么这些合同通知就会通知到签约机构、归属机构下面的支行理财经理
				m.put("noticeFlag", "1");
				m.put("noticeDealStatus", StaticVariable.DEALED);
				nlist = this.contractService.findNoticesByPage("noticeView",currentPage,pageSize,m);
				total = this.contractService.getNoticeTotal("noticeView", m);
			}else if(roleId==7||roleId==8){
				m.put("departmentId", departmentId);
				m.put("noticeDealStatus", StaticVariable.DEALED);
				nlist = this.contractService.findNoticesByPage("noticeView",currentPage,pageSize,m);
				total = this.contractService.getNoticeTotal("noticeView", m);
			}
			EasyGridList<NoticeView> noticelist = new EasyGridList<NoticeView>();
			noticelist.setRows(nlist);
			noticelist.setTotal(total);
			noticelist.setRetCode(Constants.RETCODE_00000);
			noticelist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+noticelist.toJson());
			writeJsonSuccess(noticelist.toJson());				
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	
	public String getNotices(){
		try {
			Users users = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			UsersRole uerRole = (UsersRole)getSession().getAttribute(GlobalConstants.USERROLE_KEY);
			String departmentId = users.getDepartmentId();	//获取当前用户所在的机构	
			Map<String,Object> m = new HashMap<String,Object>();
			int roleId = Integer.parseInt(uerRole.getRoleId());
			if(roleId==9){							//roleId为9是支行理财经理角色
				m.put("departmentId", departmentId);			//下面是查询和合同有关的通知,那么这些合同通知就会通知到签约机构、归属机构下面的支行理财经理
				m.put("noticeFlag", "1");
				m.put("noticeDealStatus", StaticVariable.UNDEAL);
				noticeViewList = this.contractService.findNoticesByPage("noticeView",currentPages,pageSize,m);
				//查询到合同即将逾期的通知
				total = this.contractService.getNoticeTotal("noticeView", m);
			}else{
				m.put("departmentId", departmentId);
				m.put("noticeDealStatus", StaticVariable.UNDEAL);
				noticeViewList = this.contractService.findNoticesByPage("noticeView",currentPages,pageSize,m);
				//查询到合同即将逾期的通知
				total = this.contractService.getNoticeTotal("noticeView", m);
			}
			business = users.getBusiness().toString();
			message = "true";
		} catch (AppException e) {
			e.printStackTrace();
			message = "false";
		} catch (Exception e){
			e.printStackTrace();
			message = "false";
		}
		return "waitNotices";
	}
	
	public void getNoticesViewList(){
		try{
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			List<NoticeView> nlist = new ArrayList<NoticeView>();
			Map<String,Object> m = new HashMap<String,Object>();
			m=m1(m);
			m.put("noticeFlag", "1");
			nlist = this.contractService.findNoticesByPage("noticeView",currentPage,pageSize,m);
			total = this.contractService.getNoticeTotal("noticeView", m);
			EasyGridList<NoticeView> noticelist = new EasyGridList<NoticeView>();
			noticelist.setRows(nlist);
			noticelist.setTotal(total);
			noticelist.setRetCode(Constants.RETCODE_00000);
			noticelist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+noticelist.toJson());
			writeJsonSuccess(noticelist.toJson());				
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 查阅通知
	 */
	public void viewNoticeAction(){
		try{
			printParameters();
			String noticeId = getHttpRequest().getParameter("noticeId");
			NoticeView noticeView = new NoticeView();
			noticeView = contractService.viewNotice(noticeId);
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(noticeView);
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
	 * 处理通知
	 */
	public void dealNoticeAction(){
		try{
			printParameters();
			String noticeId = getHttpRequest().getParameter("noticeId");
			contractService.dealNotice(noticeId);
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
	 * 复查通知
	 */
	public void reViewNoticeAction(){
		try{
			printParameters();
			String noticeId = getHttpRequest().getParameter("noticeId");
			NoticeView noticeView = new NoticeView();
			noticeView = contractService.reViewNotice(noticeId);
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(noticeView);
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
	 * 设置查询条件
	 * @param m
	 * @return
	 */
	private Map<String, Object> m1(Map<String, Object> m) {
		String openDayBegin = getHttpRequest().getParameter("openDayBegin");
		String openDayEnd = getHttpRequest().getParameter("openDayEnd");
		String redeemBegin = getHttpRequest().getParameter("redeemBegin");
		String redeemEnd = getHttpRequest().getParameter("redeemEnd");
		String dueDateBegin = getHttpRequest().getParameter("dueDateBegin");
		String dueDateEnd = getHttpRequest().getParameter("dueDateEnd");
		String belongDepartment = getHttpRequest().getParameter("belongDepartment");
		String signDepartment = getHttpRequest().getParameter("signDepartment");
		
		String customeId = getHttpRequest().getParameter("customeId");
		String customeName = getHttpRequest().getParameter("customeName");
		String productCode = getHttpRequest().getParameter("productCode");
		String productName = getHttpRequest().getParameter("productName");
		String handStatus = getHttpRequest().getParameter("handStatus");
		String redempStatus = getHttpRequest().getParameter("redempStatus");
		
		String noticeType = getHttpRequest().getParameter("noticeType");
		String noticeViewStatus = getHttpRequest().getParameter("noticeViewStatus");
		String arriveDateBegin = getHttpRequest().getParameter("arriveDateBegin");
		String arriveDateEnd = getHttpRequest().getParameter("arriveDateEnd");
		String dealDateBegin = getHttpRequest().getParameter("dealDateBegin");
		String dealDateEnd = getHttpRequest().getParameter("dealDateEnd");
		
		String addDateBegin = getHttpRequest().getParameter("addDateBegin");
		String addDateEnd = getHttpRequest().getParameter("addDateEnd");
		String contractInfoStatus = getHttpRequest().getParameter("contractInfoStatus");
		
		String redemptionStatus = getHttpRequest().getParameter("redemptionStatus");
		
		
		
		if(dueDateBegin!=null&&dueDateBegin.length()>0){
			m.put("dueDateBegin", dueDateBegin);
		}
		if(dueDateEnd!=null&&dueDateEnd.length()>0){
			m.put("dueDateEnd", dueDateEnd);
		}
		if(addDateBegin!=null&&addDateBegin.length()>0){
			m.put("addDateBegin", addDateBegin);
		}
		if(addDateEnd!=null&&addDateEnd.length()>0){
			m.put("addDateEnd", addDateEnd);
		}
		if(contractInfoStatus!=null&&contractInfoStatus.length()>0){
			m.put("contractInfoStatus", contractInfoStatus);
		}
		
		if(openDayBegin!=null&&openDayBegin.length()>0){
			m.put("openDayBegin", openDayBegin);
		}
		if(openDayEnd!=null&&openDayEnd.length()>0){
			m.put("openDayEnd", openDayEnd);
		}
		if(redeemBegin!=null&&redeemBegin.length()>0){
			m.put("redeemBegin", redeemBegin);
		}
		if(redeemEnd!=null&&redeemEnd.length()>0){
			m.put("redeemEnd", redeemEnd);
		}
		if(belongDepartment!=null&&belongDepartment.length()>0){
			m.put("belongDepartment", belongDepartment);
		}
		if(signDepartment!=null&&signDepartment.length()>0){
			m.put("signDepartment", signDepartment);
		}
		if(customeId!=null&&customeId.length()>0){
			m.put("customeId", customeId);
		}
		if(customeName!=null&&customeName.length()>0){
			m.put("customeName", customeName);
		}
		if(productCode!=null&&productCode.length()>0){
			m.put("productCode", productCode);
		}
		if(productName!=null&&productName.length()>0){
			m.put("productName", productName);
		}
		if(noticeType!=null&&noticeType.length()>0){
			m.put("noticeType", noticeType);
		}
		if(arriveDateBegin!=null&&arriveDateBegin.length()>0){
			m.put("arriveDateBegin", arriveDateBegin);
		}
		if(arriveDateEnd!=null&&arriveDateEnd.length()>0){
			m.put("arriveDateEnd", arriveDateEnd);
		}
		if(noticeViewStatus!=null&&noticeViewStatus.length()>0){
			m.put("noticeViewStatus", noticeViewStatus);
		}
		if(dealDateBegin!=null&&dealDateBegin.length()>0){
			m.put("arriveDateBegin", dealDateBegin);
		}
		if(dealDateEnd!=null&&dealDateEnd.length()>0){
			m.put("dealDateEnd", dealDateEnd);
		}
		if(handStatus!=null&&handStatus.length()>0){
			m.put("handStatus", handStatus);
		}
		if(redempStatus!=null&&redempStatus.length()>0){
			m.put("redempStatus", redempStatus);
		}
		if(redemptionStatus!=null&&redemptionStatus.length()>0){
			m.put("redemptionStatus", redemptionStatus);
		}
		return m;
	}


	public String getCustomeId() {
		return customeId;
	}

	public void setCustomeId(String customeId) {
		this.customeId = customeId;
	}

	public String getCustomeName() {
		return customeName;
	}

	public void setCustomeName(String customeName) {
		this.customeName = customeName;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSignAccount() {
		return signAccount;
	}

	public void setSignAccount(String signAccount) {
		this.signAccount = signAccount;
	}

	public String getSaleManager() {
		return saleManager;
	}

	public void setSaleManager(String saleManager) {
		this.saleManager = saleManager;
	}

	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	public String getBelongDepartment() {
		return belongDepartment;
	}

	public void setBelongDepartment(String belongDepartment) {
		this.belongDepartment = belongDepartment;
	}

	public String getSignDepartment() {
		return signDepartment;
	}

	public void setSignDepartment(String signDepartment) {
		this.signDepartment = signDepartment;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setSignDepartmentId(String signDepartmentId) {
		this.signDepartmentId = signDepartmentId;
	}
	public String getSignDepartmentId() {
		return signDepartmentId;
	}
	public String getBelongDepartmentId() {
		return belongDepartmentId;
	}
	public void setBelongDepartmentId(String belongDepartmentId) {
		this.belongDepartmentId = belongDepartmentId;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	public MainService getMainService() {
		return mainService;
	}
	public void setCurrentPages(int currentPages) {
		this.currentPages = currentPages;
	}
	public int getCurrentPages() {
		return currentPages;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return total;
	}

	public void setNoticeViewList(List<NoticeView> noticeViewList) {
		this.noticeViewList = noticeViewList;
	}

	public List<NoticeView> getNoticeViewList() {
		return noticeViewList;
	}

	public void setFiledata(List<File> filedata) {
		Filedata = filedata;
	}

	public List<File> getFiledata() {
		return Filedata;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}

	public String getFileNewName() {
		return fileNewName;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBusiness() {
		return business;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
