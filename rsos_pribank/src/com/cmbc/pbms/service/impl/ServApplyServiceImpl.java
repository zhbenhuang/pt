package com.cmbc.pbms.service.impl;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.bean.PbmsBoardingList;
import com.cmbc.pbms.bean.PbmsHospitalReg;
import com.cmbc.pbms.bean.PbmsPhysicalExam;
import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dao.ApproveInfoDao;
import com.cmbc.pbms.dao.BoardingDao;
import com.cmbc.pbms.dao.HospitalRegDao;
import com.cmbc.pbms.dao.PhysicalExamDao;
import com.cmbc.pbms.dao.ServApplyDao;
import com.cmbc.pbms.dto.QueryServApplyDto;
import com.cmbc.pbms.service.ServApplyService;

public class ServApplyServiceImpl implements ServApplyService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ServApplyDao servApplyDao;
	private PhysicalExamDao physicalExamDao;
	private HospitalRegDao hospitalRegDao;
	private BoardingDao boardingDao;
	private ApproveInfoDao approveInfoDao;
	
	public void setServApplyDao(ServApplyDao servApplyDao) {
		this.servApplyDao = servApplyDao;
	}
	public void setPhysicalExamDao(PhysicalExamDao physicalExamDao) {
		this.physicalExamDao = physicalExamDao;
	}
	public void setHospitalRegDao(HospitalRegDao hospitalRegDao) {
		this.hospitalRegDao = hospitalRegDao;
	}
	public void setBoardingDao(BoardingDao boardingDao) {
		this.boardingDao = boardingDao;
	}
	public void setApproveInfoDao(ApproveInfoDao approveInfoDao) {
		this.approveInfoDao = approveInfoDao;
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
	
	
	public PbmsServApply findServApply(String seqNo)
			throws AppException {
		try {
			log.info("---findServApply by key seqNo: "+seqNo);
			return servApplyDao.selectServApply(seqNo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{seqNo});
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
	
	@Override
	public void apply(PbmsServApply servApply, PbmsApproveInfo approveInfo) throws AppException {
		try {
			servApplyDao.insert(servApply);
			approveInfoDao.insert(approveInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	@Override
	public void apply(PbmsServApply servApply,
			PbmsHospitalReg hospitalReg, PbmsApproveInfo approveInfo) throws AppException {
		try {
			servApplyDao.insert(servApply);
			hospitalRegDao.insert(hospitalReg);
			approveInfoDao.insert(approveInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}

	@Override
	public void apply(PbmsServApply servApply,
			PbmsPhysicalExam physicalExam, PbmsApproveInfo approveInfo) throws AppException{
		try {
			servApplyDao.insert(servApply);
			physicalExamDao.insert(physicalExam);
			approveInfoDao.insert(approveInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}

	@Override
	public void apply(PbmsServApply servApply, PbmsBoardingList boarding, PbmsApproveInfo approveInfo) throws AppException{
		try {
			servApplyDao.insert(servApply);
			boardingDao.insert(boarding);
			approveInfoDao.insert(approveInfo);
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
