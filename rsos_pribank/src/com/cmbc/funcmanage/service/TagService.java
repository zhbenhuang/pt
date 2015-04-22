package com.cmbc.funcmanage.service;
import java.util.List;
import java.util.Map;

import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.bean.Tag;

public interface TagService{
	
		public List<Tag> findByPagination(int currentPage , int pageSize ,Map<String,Object> m)throws AppException;
		
		public int getTotal(Map<String,Object> m)throws AppException;
		
		public boolean isExistence(String prefix)throws AppException;
		
		@SuppressWarnings("unchecked")
		public List find(String table, String property, String value)throws AppException;
		
		public String findMaxEndCode(String printDate)throws AppException;

		@SuppressWarnings("unchecked")
		public void saveTag(Tag tag, List<String> list)throws AppException;
		
}
	