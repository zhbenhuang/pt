package com.cmbc.sa.dao;

import java.util.List;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyTreeMenuList;

import com.cmbc.sa.bean.Permission;

public interface PermissionDao extends EasyBaseDao<Permission> {
	public EasyTreeMenuList selectNodes(String userId,String parentNode);
	public List<Permission> selectPermissions(String roleId,Integer business);
	public List<Permission> selectAllPermissions();
	public List<Permission> selectPermissionsByBusiness(int business);
}
