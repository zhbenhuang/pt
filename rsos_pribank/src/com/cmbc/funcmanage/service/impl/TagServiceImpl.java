package com.cmbc.funcmanage.service.impl;


import java.util.List;
import java.util.Map;

import rsos.framework.exception.AppException;
import rsos.framework.utils.OneBarcodeUtil;

import com.cmbc.funcmanage.bean.Tag;
import com.cmbc.funcmanage.dao.TagDao;
import com.cmbc.funcmanage.service.TagService;


public class TagServiceImpl implements TagService {
	
	private TagDao tagDao;
	
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public List<Tag> findByPagination(int currentPage , int pageSize ,Map<String,Object> m)throws AppException{
		try{
			return tagDao.findByPagination(currentPage,pageSize,m); 
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	
	public int getTotal(Map<String,Object> m)throws AppException{
		try{
			return tagDao.getTotal(m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}

	@Override
	public boolean isExistence(String prefix)throws AppException{

		try{
			return tagDao.isExistence(prefix);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{prefix});
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List find(String table, String property, String value)throws AppException {

		try{
			return tagDao.find(table, property, value);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{property});
		}
	}
	
	@Override
	public String findMaxEndCode(String prefix)throws AppException {
		try{
			return tagDao.findMaxEndCode(prefix);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{prefix});
		}
	}

	@Override
	public void saveTag(Tag tag, List<String> list) throws AppException {

		try {
			tagDao.insert(tag);			
			String fileName = tag.getPrefix() + tag.getStartCode();
			OneBarcodeUtil oneBarcodeUtil = new OneBarcodeUtil(list,fileName);
			oneBarcodeUtil.createBarcode();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
}
