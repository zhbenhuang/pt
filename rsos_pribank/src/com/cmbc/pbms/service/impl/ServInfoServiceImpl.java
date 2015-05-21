package com.cmbc.pbms.service.impl;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.dao.ServInfoDao;
import com.cmbc.pbms.dto.QueryServInfoDto;
import com.cmbc.pbms.service.ServInfoService;

public class ServInfoServiceImpl implements ServInfoService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ServInfoDao servInfoDao;
	
	public void setServInfoDao(ServInfoDao servInfoDao) {
		this.servInfoDao = servInfoDao;
	}
	
	public EasyGridList<PbmsServInfo> findServInfos(QueryServInfoDto queryDto)
			throws AppException {
		try {
			return servInfoDao.selectServInfos(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	
	public PbmsServInfo findServInfo(String serId)
			throws AppException {
		try {
			log.info("---findServInfo by key serId: "+serId);
			return servInfoDao.selectServInfo(serId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{serId});
		}
	}
	
	public void deleteServInfo(String idstr)
			throws AppException {
		try {
			servInfoDao.deleteServInfo(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveServInfo(PbmsServInfo servInfo)
			throws AppException {
		try {
			servInfoDao.insert(servInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyServInfo(PbmsServInfo servInfo)
			throws AppException {
		try {
			servInfoDao.update(servInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public int nextId() {
		try {
			int i = servInfoDao.getMaxId();
			return  ++i;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
