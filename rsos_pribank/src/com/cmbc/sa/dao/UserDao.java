package com.cmbc.sa.dao;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.sa.bean.Users;
import com.cmbc.sa.dto.QueryUserDto;

public interface UserDao extends EasyBaseDao<Users> {
	public EasyGridList<Users> selectUsers(QueryUserDto queryDto);
	public Users selectUser(String loginId,Integer business);
	public void deleteUsers(String idstr);
	public void alterPassword(String userId, String password);
}
