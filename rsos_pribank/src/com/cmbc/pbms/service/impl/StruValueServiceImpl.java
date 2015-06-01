package com.cmbc.pbms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsStruValue;
import com.cmbc.pbms.bean.PbmsStruValueId;
import com.cmbc.pbms.dao.StruValueDao;
import com.cmbc.pbms.dto.QueryStruValueDto;
import com.cmbc.pbms.service.StruValueService;

public class StruValueServiceImpl implements StruValueService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private StruValueDao struValueDao;
	
	public void setStruValueDao(StruValueDao struValueDao) {
		this.struValueDao = struValueDao;
	}
	
	public EasyGridList<PbmsStruValue> findStruValues(QueryStruValueDto queryDto)
			throws AppException {
		try {
			return struValueDao.selectStruValues(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	
	public PbmsStruValue findStruValue(String struId, String term)
			throws AppException {
		try {
			log.info("---findStruValue by key struId: "+struId);
			return struValueDao.selectStruValue(struId, term);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{struId, term});
		}
	}
	
	public void deleteStruValue(String idstr)
			throws AppException {
		try {
			struValueDao.deleteStruValue(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveStruValue(PbmsStruValue struValue)
			throws AppException {
		try {
			struValueDao.insert(struValue);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyStruValue(PbmsStruValue struValue)
			throws AppException {
		try {
			struValueDao.update(struValue);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public int nextId() {
		try {
			int i = struValueDao.getMaxId();
			return  ++i;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public void importManyStruValue(Set<PbmsStruValue> struValues)
			throws AppException {
		for (PbmsStruValue psv : struValues) {
			struValueDao.insert(psv);
		}
	}

}
