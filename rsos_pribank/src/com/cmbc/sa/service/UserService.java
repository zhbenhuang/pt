package com.cmbc.sa.service;

import java.util.List;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dto.QueryUserDto;

public interface UserService {
	public EasyGridList<Users> findUsers(QueryUserDto queryDto)throws AppException;	
	public Users findUser(String userId,Integer business)throws AppException;
	public void deleteUsers(String idstr) throws AppException;
	public void saveUser(Users user) throws AppException;
	public void modifyUser(Users user) throws AppException;
	public List<Role> findRoles(String userId,Integer business) throws AppException;
	public List<Role> findRoles(Integer business) throws AppException;
	public void saveUserRoles(List<UsersRole> userRoles,String userId,Integer business) throws AppException;
}
