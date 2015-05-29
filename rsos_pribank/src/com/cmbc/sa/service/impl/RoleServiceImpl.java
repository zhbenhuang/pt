package com.cmbc.sa.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Permission;
import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.RolePermission;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.dao.PermissionDao;
import com.cmbc.sa.dao.RoleDao;
import com.cmbc.sa.dao.RolePermissionDao;
import com.cmbc.sa.dao.UserRoleDao;
import com.cmbc.sa.dto.QueryRoleDto;
import com.cmbc.sa.service.RoleService;

public class RoleServiceImpl implements RoleService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private RoleDao roleDao;
	private PermissionDao permissionDao;
	private RolePermissionDao rolePermissionDao;
	private UserRoleDao userRoleDao;
	
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
	public void setRolePermissionDao(RolePermissionDao rolePermissionDao) {
		this.rolePermissionDao = rolePermissionDao;
	}
	
	public List<Role> loadRoles() {
		return roleDao.selectRoles();
	}
	public EasyGridList<Role> findRoles(QueryRoleDto queryDto)
			throws AppException {
		try {
			return roleDao.selectRoles(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	public EasyGridList<Users> findUsersByRole(QueryRoleDto queryDto)
			throws AppException {
		try {
			return roleDao.selectUsersByRole(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	public List<Permission> findPermissions(String roleId,Integer business)
			throws AppException {
		try {
			log.info("---findPermissions by roleId:"+roleId+",business:"+business);
			return permissionDao.selectPermissions(roleId, business);
		} catch (Exception ex) {			
			throw new AppException("E000013",new String[]{roleId,business.toString()});
		}
	}
	
	public List<Permission> findAllPermissions()
			throws AppException {
		try {
			return permissionDao.selectAllPermissions();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000012");
		}
	}
	
	public Role findRole(String roleId,Integer business)
			throws AppException {
		try {
			log.info("---findRole by key roleId: "+roleId+",business:"+business);
			return roleDao.selectRole(roleId, business);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{roleId,business.toString()});
		}
	}
	
	public void deleteRole(String idstr)
			throws AppException {
		try {
			roleDao.deleteRoles(idstr);
			rolePermissionDao.deleteRolePermissions(idstr);
			userRoleDao.deleteUserRolesByRoleId(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveRole(Role role)
			throws AppException {
		try {
			roleDao.insert(role);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyRole(Role role)
			throws AppException {
		try {
			roleDao.update(role);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}
	
	public void saveRolePermissions(List<RolePermission> rolePermissions,String roleId,Integer business)
			throws AppException {
		try {
			rolePermissionDao.deletePermissions(roleId, business);
			for(RolePermission r : rolePermissions){
				rolePermissionDao.insert(r);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	@Override
	public List<Permission> findPermissionsByBusiness(int business)
			throws AppException {
		try {
			return permissionDao.selectPermissionsByBusiness(business);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000012");
		}
	}

}
