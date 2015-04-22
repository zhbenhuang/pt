package com.cmbc.sa.action;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyTreeMenuList;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;
import rsos.framework.utils.SecurityUtils;
import rsos.framework.utils.StringUtil;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.service.MainService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@Scope("prototype")
@Controller("mainAction")
public class MainAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(MainAction.class.getName());
	private String business;
	private String userName;
	private String msg;
	private MainService mainService;
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String login(){
		try{
			printParameters();
			ActionContext.getContext().getSession().clear();
			StringBuilder brchList = new StringBuilder("");
			String loginId = getHttpRequest().getParameter("userId");
			String loginPwd = getHttpRequest().getParameter("password");
			business = getHttpRequest().getParameter("business");
			Users user = mainService.loginUser(loginId, loginPwd, Integer.parseInt(business));
			Department department = mainService.findDepartment(user.getDepartmentId());
			UsersRole userRole = mainService.findUserRole(user.getUserId(),business);
			/* 初始化数据 */			
			if(user.getEnabled()>0 && !GlobalConstants.ROOT_BRANCH.equals(user.getDepartmentId().trim())){
		    	List<Department> list = mainService.findChildDepartments(user.getDepartmentId(), user.getBusiness());
		    	if(list!=null){
		    		for(Department d : list){
		    			brchList.append("'").append(d.getDepartmentId().trim()).append("',");
		    		}
		        }
		    	getSession().setAttribute(GlobalConstants.BRANCH_LIST, 
						StringUtil.isEmpty(brchList.toString())?brchList.toString():brchList.substring(0, brchList.length()-1));
	    	}
			getSession().setAttribute(GlobalConstants.USER_INFORMATION_KEY, user);
			getSession().setAttribute(GlobalConstants.USERROLE_KEY, userRole);
			getSession().setAttribute(GlobalConstants.LOGIN_BRANCH, department);
			getSession().setAttribute(GlobalConstants.BUSINESS, user.getBusiness());
			log.info("用户["+loginId+"]登录成功,"+"所在机构["+user.getDepartmentId()+"]!");
			log.info("管辖机构列表["+(String)getSession().getAttribute(GlobalConstants.BRANCH_LIST)+"]");
			return Action.SUCCESS;
		}catch(AppException e){
			addErrors(e);
			return Action.ERROR;
		} catch (Exception e) {			
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
			return Action.ERROR;
		}		
	}

	public String validatePerson(){
		try{
			Users u = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			userName = u.getUsername();
			return Action.SUCCESS;
		} catch (Exception e) {			
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
			return Action.ERROR;
		}
	}
	
	public String logout(){
		ActionContext.getContext().getSession().clear();
		getSession().invalidate();
		return Action.SUCCESS;
	}
	
	public void alterPassword(){
		try{
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String originalpassword = getHttpRequest().getParameter("originalpassword");
			String newpassword = getHttpRequest().getParameter("newpassword");
			String encryptPassword = SecurityUtils.encrypt(originalpassword);
			log.info("Edit encryptPassword: " + encryptPassword);
			if(!encryptPassword.equals(user.getPassword())){
				String message = "{'message':'原始密码填写错误,请重新填写!','retCode':'0000'}";
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
				this.writeJsonSuccess(jsonObject.toString());
			}
			mainService.alterPassword(user.getUserId(),SecurityUtils.encrypt(newpassword));
			String message = "{'message':'密码修改成功,以后请用新密码登录!','retCode':'1111'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(Exception ex){
			ex.printStackTrace();
			String message = "{'message':'系统错误,请联系管理员!','retCode':'2222'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
		}
	}
	
	public void showPermission(){
		try{
			printParameters();
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			if(user==null){
				throw new AppException(Constants.RETCODE_888888);
			}
			String id = (String)getHttpRequest().getParameter("id");

			EasyTreeMenuList treeList = mainService.findNodes(user.getUserId(), id);

			writeJsonSuccess(treeList.toJson());
			
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/*对于角色不同，待办任务返回的jsp不同做出的不同处理*/
	public String waitTaskAction(){
		try {
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			UsersRole userRole = (UsersRole)getSession().getAttribute(GlobalConstants.USERROLE_KEY);
			business = user.getBusiness().toString();
			if(user!=null&&userRole!=null){
				int userRoleId = Integer.parseInt(userRole.getRoleId());
				switch(userRoleId){
					case 3	:return "managerTasks";
					case 12 :return "branchPresident";
					case 4	:return "afterServiceTasks";
					case 5	:return "xweiTasks";
					case 6	:return "marketTasks";
					case 7	:return "contractMan";
					case 8	:return "productManager";
					case 9	:return "branchManager";
					default	:return "reLogin";
				}
			}else{
					return "reLogin";
			}
		}catch (Exception e) {
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
			return "reLogin";
		}
	}
	/**
	 * 根据当前登录的用户信息，决定需要显示的通知消息
	 * @return
	 */
	public String noticeAction(){
		Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
		if(user!=null){
			int businessType = user.getBusiness();				//business字段记录用户所在业务类型(例如：售后服务部、私银理财部)
			if(businessType==1){								//1:售后服务部;2:私银理财部
				return "afterServiceDep";
			}else if(businessType==2){
				return "privateBankDep";
			}else{
				return "reLogin";
			}
		}else{
			return "reLogin";
		}
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBusiness() {
		return business;
	}
}
