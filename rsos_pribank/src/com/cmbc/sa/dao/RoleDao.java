package com.cmbc.sa.dao;

import java.util.List;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dto.QueryRoleDto;

public interface RoleDao extends EasyBaseDao<Role> {
	public List<Role> selectRoles(String userId,Integer business);
	public List<Role> selectRoles(Integer business);
	public EasyGridList<Role> selectRoles(QueryRoleDto queryDto);
	public EasyGridList<Users> selectUsersByRole(QueryRoleDto queryDto);
	public Role selectRole(String roleId,Integer business);
	public void deleteRoles(String idstr);
	public UsersRole findUserRole(String userId, String business);
}
