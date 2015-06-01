package com.cmbc.sa.service;

import java.util.List;
import java.util.Map;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Permission;
import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.RolePermission;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.dto.QueryRoleDto;

public interface RoleService {
	public EasyGridList<Role> findRoles(QueryRoleDto queryDto)throws AppException;
	public EasyGridList<Users> findUsersByRole(QueryRoleDto queryDto)throws AppException;	
	public Role findRole(String userId,Integer business)throws AppException;
	public void deleteRole(String idstr) throws AppException;
	public void saveRole(Role role) throws AppException;
	public void modifyRole(Role role) throws AppException;
	public List<Permission> findPermissions(String userId,Integer business) throws AppException;
	public List<Permission> findAllPermissions() throws AppException;
	public void saveRolePermissions(List<RolePermission> rolePermissions,String roleId,Integer business) throws AppException;
	public List<Permission> findPermissionsByBusiness(int business)throws AppException;
	public List<Role> loadRoles();
}
