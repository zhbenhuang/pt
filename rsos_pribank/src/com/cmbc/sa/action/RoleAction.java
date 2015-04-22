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
import rsos.framework.utils.StringUtil;
import rsos.framework.utils.UUIDGenerator;

import com.cmbc.sa.bean.Permission;
import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.RolePermission;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.dto.QueryRoleDto;
import com.cmbc.sa.service.RoleService;
import com.opensymphony.xwork2.Action;
@Scope("prototype")
@Controller("roleAction")
public class RoleAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private List<Permission> permissionList;
	private List<Permission> ownPermissionList;
	private RoleService roleService;
	private EasyResult ret;
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public List<Permission> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	public List<Permission> getOwnPermissionList() {
		return ownPermissionList;
	}
	public void setOwnPermissionList(List<Permission> ownPermissionList) {
		this.ownPermissionList = ownPermissionList;
	}
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}	

	public void queryRoleList()
	{
		try{
			log.info("-------queryRoleList--------");
			printParameters();
			String roleId = getHttpRequest().getParameter("roleId");
			String roleName = getHttpRequest().getParameter("roleName");
			Integer business = (Integer)getSession().getAttribute(GlobalConstants.BUSINESS);
			
			QueryRoleDto queryDto = new QueryRoleDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setRoleId(roleId);
			queryDto.setRoleName(roleName);
			queryDto.setBusiness(business);
			
			EasyGridList<Role> ulist = roleService.findRoles(queryDto);
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
	
	public void queryUsersByRole()
	{
		try{
			log.info("-------queryUsersByRole--------");
			printParameters();
			Users currentUser = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String userId = getHttpRequest().getParameter("userId");
			String username = getHttpRequest().getParameter("username");
			String roleId = getHttpRequest().getParameter("roleId");
			String department = getHttpRequest().getParameter("department");
			QueryRoleDto queryDto = new QueryRoleDto();
			if(StringUtil.isEmpty(roleId)){
				roleId = getHttpRequest().getParameter("roleIdInDlg");
			}
			if(department!=null&&department.equals("true")){
				queryDto.setDepartmentId(currentUser.getDepartmentId());
			}
			queryDto.setPageDto(initPageParameters());
			queryDto.setUserId(userId);
			queryDto.setUserName(username);
			queryDto.setRoleId(roleId);
			
			EasyGridList<Users> ulist = roleService.findUsersByRole(queryDto);
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
	
	public String getPermission(){
		try{
			log.info("-------getPermission--------");
			printParameters();
			String roleId = getHttpRequest().getParameter("roleId");
			String business = (String)getHttpRequest().getParameter("business");
			permissionList = roleService.findPermissionsByBusiness(Integer.parseInt(business));
			ownPermissionList = roleService.findPermissions(roleId, Integer.parseInt(business));			
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
	
	public void findRole(){
		try{
			log.info("-------findRole--------");
			printParameters();
			String roleId = getHttpRequest().getParameter("roleId");
			String business = (String)getHttpRequest().getParameter("business");
			
			Role role = roleService.findRole(roleId, Integer.parseInt(business));
			EasyObject<Role> result = new EasyObject<Role>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), role);
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveRole(){
		try{
			log.info("-------saveRole--------");
			printParameters();
			String roleId = getHttpRequest().getParameter("roleId");
			String roleName = getHttpRequest().getParameter("roleName");
			String business = (String)getHttpRequest().getParameter("business");
			Role oldRole = roleService.findRole(roleId, Integer.parseInt(business));
			if(oldRole!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				Role role = new Role();
				role.setRoleId(roleId);
				role.setRoleName(roleName);
				role.setType("");
				role.setBusiness(Integer.parseInt(business));
				roleService.saveRole(role);	
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
	
	public void modifyRole(){
		try{
			log.info("-------modifyRole--------");
			printParameters();
			String roleId = getHttpRequest().getParameter("roleId");
			String roleName = getHttpRequest().getParameter("roleName");
			String business = (String)getHttpRequest().getParameter("business");
			
			Role role = new Role();
			role.setRoleId(roleId);
			role.setRoleName(roleName);
			role.setBusiness(Integer.parseInt(business));
			
			roleService.modifyRole(role);
			EasyResult result = new EasyResult(Constants.RETCODE_00000,
					getText(Constants.RETCODE_00000));
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
	 * ids为组合键（roleId,business).
	 */
	public void deleteRole(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
			roleService.deleteRole(ids);
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
	
	public String savePermissions(){
		try{
			log.info("-------saveRolePermissions--------");
			printParameters();
			String roleId = getHttpRequest().getParameter("roleId");
			String business = (String)getHttpRequest().getParameter("business");
			String permissionString = (String)getHttpRequest().getParameter("permissionString");
						
			List<RolePermission> rolePermissions = new ArrayList<RolePermission>();
			StringTokenizer st = new StringTokenizer(permissionString, ",");
	        while(st.hasMoreTokens()){
	        	String permissionId = st.nextToken();	        	
	        	RolePermission rolePermission = new RolePermission();
	        	rolePermission.setId(UUIDGenerator.generateShortUuid());
	        	rolePermission.setRoleId(roleId);
	        	rolePermission.setPermissionId(permissionId);
	        	rolePermission.setBusiness(Integer.parseInt(business));
	        	rolePermissions.add(rolePermission);	        	
	        	log.info("-->add permission--roleId:"+roleId+",id:"+rolePermission.getId()+",permissionId:"+permissionId+",business:"+business);
	        }
			
			roleService.saveRolePermissions(rolePermissions, roleId, Integer.parseInt(business));
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
