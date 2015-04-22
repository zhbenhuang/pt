package com.cmbc.sa.dao;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.sa.bean.UsersRole;

public interface UserRoleDao extends EasyBaseDao<UsersRole> {
	public void deleteUserRoles(String idstr);
	public void deleteUserRoles(String userId,Integer business);
	public void deleteUserRolesByRoleId(String idstr);
}
