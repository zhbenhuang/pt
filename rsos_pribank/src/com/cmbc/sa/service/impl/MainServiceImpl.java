package com.cmbc.sa.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyTreeMenuList;
import rsos.framework.exception.AppException;
import rsos.framework.utils.SecurityUtils;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dao.DepartmentDao;
import com.cmbc.sa.dao.PermissionDao;
import com.cmbc.sa.dao.RoleDao;
import com.cmbc.sa.dao.UserDao;
import com.cmbc.sa.service.MainService;

public class MainServiceImpl implements MainService{
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private UserDao userDao;
	private PermissionDao permissionDao;
	private DepartmentDao departmentDao;
	private RoleDao roleDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public Users loginUser(String loginId,String passwd,Integer business) throws AppException {
		try{
			Users user = userDao.selectUser(loginId,business);
			
			if (user == null) {
				log.info("User not existd: " + loginId);
				throw new AppException("E000001",new String[]{loginId});
			}
			
			String encryptPassword = SecurityUtils.encrypt(passwd);
			log.info("Login encryptPassword: " + encryptPassword);
			if(!encryptPassword.equals(user.getPassword())){
				throw new AppException("E000002");
			}
			return user;
		} catch (AppException ex) {
			throw ex;
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E999999");
		}
	}
	
	public List<Department> findChildDepartments(String departmentId,Integer departmentType)
			throws AppException {
		try {
			log.info("---findChildDepartments by key "+departmentId);
			return departmentDao.selectChildDepartments(departmentId, departmentType);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000008",new String[]{departmentId});
		}
	}
	
	public Department findDepartment(String departmentId)
			throws AppException {
		try {
			log.info("---findDepartment by key "+departmentId);
			return departmentDao.selectBy(departmentId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000008",new String[]{departmentId});
		}
	}
	
	public EasyTreeMenuList findNodes(String loginId,String parentNodeId) throws AppException {
		try{
			return permissionDao.selectNodes(loginId, parentNodeId);
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000003");
		}
	}
	@Override
	public UsersRole findUserRole(String userId, String business)
			throws AppException {
		try{
			return roleDao.findUserRole(userId, business);
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000003");
		}
	}
	@Override
	public void alterPassword(String userId, String password) throws AppException {
		try{
			userDao.alterPassword(userId, password);
		} catch (Exception _ex) {
			_ex.printStackTrace();
			throw new AppException("E000003");
		}
		
	}
	
}
