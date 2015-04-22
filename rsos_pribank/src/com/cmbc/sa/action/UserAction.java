package com.cmbc.sa.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyObject;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;
import rsos.framework.utils.SecurityUtils;
import rsos.framework.utils.UUIDGenerator;

import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dto.QueryUserDto;
import com.cmbc.sa.service.UserService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("userAction")
public class UserAction extends BaseAction{
	private Logger log = Logger.getLogger(UserAction.class.getName());
	private static final long serialVersionUID = 1L;	
	private List<Role> roleList;
	private List<Role> ownRoleList;	
	private UserService userService;
	private EasyResult ret;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public List<Role> getOwnRoleList() {
		return ownRoleList;
	}
	public void setOwnRoleList(List<Role> ownRoleList) {
		this.ownRoleList = ownRoleList;
	}
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}
	
	public void queryUserList()
	{
		try{
			log.info("-------queryUserList--------");
			printParameters();
			String userId = getHttpRequest().getParameter("userId");
			String username = getHttpRequest().getParameter("username");
			String departmentId = getHttpRequest().getParameter("departmentId");
			String departmentName = getHttpRequest().getParameter("departmentName");
			Integer business = (Integer)getSession().getAttribute(GlobalConstants.BUSINESS);
			
			QueryUserDto queryDto = new QueryUserDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setUserId(userId);
			queryDto.setUserName(username);
			queryDto.setDepartmentId(departmentId);
			queryDto.setDepartmentName(departmentName);
			queryDto.setBusiness(business);
			
			EasyGridList<Users> ulist = userService.findUsers(queryDto);
			ulist.setRetCode(Constants.RETCODE_00000);
			ulist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+ulist.toJson());
			writeJsonSuccess(ulist.toJson());			
			
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public String queryRoles(){
		try{
			log.info("-------queryRoles--------");
			printParameters();
			String userId = getHttpRequest().getParameter("userId");
			String business = (String)getHttpRequest().getParameter("business");
			
			roleList = userService.findRoles(Integer.parseInt(business));
			ownRoleList = userService.findRoles(userId, Integer.parseInt(business));			
			setRet(returnSuccessMessage());
		}catch(AppException e){
			setRet(returnErrorMessage(e));
			e.printStackTrace();
		} catch (Exception e) {
			setRet(returnErrorMessage(Constants.RETCODE_999999));
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public void findUser(){
		try{
			log.info("-------findUser--------");
			printParameters();
			String userId = getHttpRequest().getParameter("userId");
			String business = (String)getHttpRequest().getParameter("business");
			
			Users user = userService.findUser(userId, Integer.parseInt(business));
			String password = SecurityUtils.decrypt(user.getPassword());
			user.setPassword(password);
			EasyObject<Users> result = new EasyObject<Users>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), user);
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveUser(){
		try{
			log.info("-------saveUser--------");
			printParameters();
			String userId = getHttpRequest().getParameter("userId");
			String username = getHttpRequest().getParameter("username");
			String address = getHttpRequest().getParameter("address");
			String contact = getHttpRequest().getParameter("contact");
			String sex = getHttpRequest().getParameter("sex");
			String password = getHttpRequest().getParameter("password");
			String mail = getHttpRequest().getParameter("mail");
			String business = (String)getHttpRequest().getParameter("business");
			String rank = (String)getHttpRequest().getParameter("rank");
			String enabled = (String)getHttpRequest().getParameter("enabled");
			String departmentId = (String)getHttpRequest().getParameter("departmentId");
			Users userOld = userService.findUser(userId, Integer.parseInt(business));
			if(userOld!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				Users user = new Users();
				user.setUserId(userId);
				user.setUsername(username);
				user.setAddress(address);
				user.setContact(contact);
				user.setSex(sex);
				user.setPassword(SecurityUtils.encrypt(password));
				user.setMail(mail);
				user.setBusiness(Integer.parseInt(business));
				user.setRank(Integer.parseInt(rank));
				user.setEnabled(Integer.parseInt(enabled));
				user.setDepartmentId(departmentId);
				
				userService.saveUser(user);		
				EasyResult result = new EasyResult(Constants.RETCODE_00000,
						getText(Constants.RETCODE_00000));
				writeJsonSuccess(result.toJson());
			}
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void modifyUser(){
		try{
			log.info("-------modifyUser--------");
			printParameters();
			String userId = getHttpRequest().getParameter("userId");
			String business = (String)getHttpRequest().getParameter("business");
			Users user = userService.findUser(userId, Integer.parseInt(business));
			
			String username = getHttpRequest().getParameter("username");
			String address = getHttpRequest().getParameter("address");
			String contact = getHttpRequest().getParameter("contact");
			String sex = getHttpRequest().getParameter("sex");
			String password = getHttpRequest().getParameter("password");
			String mail = getHttpRequest().getParameter("mail");
			String rank = (String)getHttpRequest().getParameter("rank");
			String enabled = (String)getHttpRequest().getParameter("enabled");
			String departmentId = (String)getHttpRequest().getParameter("departmentId");
			
			user.setUsername(username);
			user.setAddress(address);
			user.setContact(contact);
			user.setSex(sex);
			user.setPassword(SecurityUtils.encrypt(password));
			user.setMail(mail);
			user.setRank(Integer.parseInt(rank));
			user.setEnabled(Integer.parseInt(enabled));
			user.setDepartmentId(departmentId);
			
			userService.modifyUser(user);		
			EasyResult result = new EasyResult(Constants.RETCODE_00000,getText(Constants.RETCODE_00000));
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * ids为组合键（userId,business).
	 */
	public void deleteUser(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
			userService.deleteUsers(ids);
			EasyResult result = new EasyResult(Constants.RETCODE_00000,
						getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+result.toJson());
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public String saveUserRoles(){
		try{
			log.info("-------saveUserRoles--------");
			printParameters();
			String userId = getHttpRequest().getParameter("userId");
			String business = (String)getHttpRequest().getParameter("business");
			String roleString = (String)getHttpRequest().getParameter("roleString");
						
			List<UsersRole> userRoles = new ArrayList<UsersRole>();
			StringTokenizer st = new StringTokenizer(roleString, ",");
	        while(st.hasMoreTokens()){
	        	String roleId = st.nextToken();	        	
	        	UsersRole userRole = new UsersRole();
	        	userRole.setId(UUIDGenerator.generateShortUuid());
	        	userRole.setRoleId(roleId);
	        	userRole.setUserId(userId);
	        	userRole.setBusiness(Integer.parseInt(business));
	        	userRoles.add(userRole);	        	
	        	log.info("-->add role--roleId:"+roleId+",userId:"+userId+",business:"+business);
	        }
			
			userService.saveUserRoles(userRoles,userId,Integer.parseInt(business));
			setRet(returnSuccessMessage());
		}catch(AppException e){
			setRet(returnErrorMessage(e));
			e.printStackTrace();
		} catch (Exception e) {
			setRet(returnErrorMessage(Constants.RETCODE_999999));
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
}
