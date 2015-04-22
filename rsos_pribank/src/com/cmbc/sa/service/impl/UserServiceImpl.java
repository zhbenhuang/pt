package com.cmbc.sa.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dao.RoleDao;
import com.cmbc.sa.dao.UserDao;
import com.cmbc.sa.dao.UserRoleDao;
import com.cmbc.sa.dto.QueryUserDto;
import com.cmbc.sa.service.UserService;

public class UserServiceImpl implements UserService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private UserDao userDao;
	private RoleDao roleDao;
	private UserRoleDao userRoleDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	
	public EasyGridList<Users> findUsers(QueryUserDto queryDto)
			throws AppException {
		try {
			return userDao.selectUsers(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	public List<Role> findRoles(String userId,Integer business)
			throws AppException {
		try {
			log.info("---findRoles by userId:"+userId+",business:"+business);
			return roleDao.selectRoles(userId, business);
		} catch (Exception ex) {			
			throw new AppException("E000009",new String[]{userId,business.toString()});
		}
	}
	
	public List<Role> findRoles(Integer business)
			throws AppException {
		try {
			return roleDao.selectRoles(business);
		} catch (Exception ex) {			
			throw new AppException("E000010",new String[]{business.toString()});
		}
	}
	
	public Users findUser(String userId,Integer business)
			throws AppException {
		try {
			log.info("---findUser by key "+userId+",business:"+business);
			return userDao.selectUser(userId, business);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{userId,business.toString()});
		}
	}
	
	public void deleteUsers(String idstr)
			throws AppException {
		try {
			userDao.deleteUsers(idstr);
			userRoleDao.deleteUserRoles(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveUser(Users user)
			throws AppException {
		try {
			userDao.insert(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyUser(Users user)
			throws AppException {
		try {
			userDao.update(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}
	
	public void saveUserRoles(List<UsersRole> userRoles,String userId,Integer business)
			throws AppException {
		try {
			userRoleDao.deleteUserRoles(userId, business);
			for(UsersRole r : userRoles){
				userRoleDao.insert(r);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}

}
