package com.cmbc.sa.service;

import java.util.List;

import rsos.framework.easyui.EasyTreeMenuList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;

public interface MainService {
	public Users loginUser(String loginId,String passwd,Integer business) throws AppException;
	public List<Department> findChildDepartments(String departmentId,Integer departmentType) throws AppException;
	public Department findDepartment(String departmentId) throws AppException;
	public EasyTreeMenuList findNodes(String loginId,String parentNodeId) throws AppException;
	public UsersRole findUserRole(String userId, String business)throws AppException;
	public void alterPassword(String userId, String password)throws AppException;
}
