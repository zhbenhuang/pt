package com.cmbc.sa.dao;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.sa.bean.RolePermission;

public interface RolePermissionDao extends EasyBaseDao<RolePermission> {
	public void deleteRolePermissions(String idstr);
	public void deletePermissions(String roleId,Integer business);
}
