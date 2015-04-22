package com.cmbc.funcmanage.dao;
import java.util.List;
import java.util.Map;

import com.cmbc.funcmanage.bean.Tag;

import rsos.framework.db.EasyBaseDao;


public interface TagDao extends EasyBaseDao<Tag>{

	public String findDevStatusByBusinessId(String sql);
	
	@SuppressWarnings("unchecked")
	public List findFreezeEnd(String sql);
	
	public boolean isExistence(String prefix);
	
	@SuppressWarnings("unchecked")
	public List find(String table,String property,String value);
	
	public String findMaxEndCode(String printDate);

	public List<Tag> findByPagination(int currentPage, int pageSize,Map<String, Object> m);
	public int getTotal(Map<String, Object> m);
	
}