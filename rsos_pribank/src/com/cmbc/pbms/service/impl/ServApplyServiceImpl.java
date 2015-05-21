package com.cmbc.pbms.service.impl;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dao.ServApplyDao;
import com.cmbc.pbms.dto.QueryServApplyDto;
import com.cmbc.pbms.service.ServApplyService;

public class ServApplyServiceImpl implements ServApplyService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ServApplyDao servApplyDao;
	
	public void setServApplyDao(ServApplyDao servApplyDao) {
		this.servApplyDao = servApplyDao;
	}
	
	public EasyGridList<PbmsServApply> findServApplys(QueryServApplyDto queryDto)
			throws AppException {
		try {
			return servApplyDao.selectServApplys(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	
	public PbmsServApply findServApply(String serNo)
			throws AppException {
		try {
			log.info("---findServApply by key serNo: "+serNo);
			return servApplyDao.selectServApply(serNo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{serNo});
		}
	}
	
	public void deleteServApply(String idstr)
			throws AppException {
		try {
			servApplyDao.deleteServApply(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveServApply(PbmsServApply servApply)
			throws AppException {
		try {
			servApplyDao.insert(servApply);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyServApply(PbmsServApply servApply)
			throws AppException {
		try {
			servApplyDao.update(servApply);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public int nextId() {
		try {
			int i = servApplyDao.getMaxId();
			return  ++i;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}


}
