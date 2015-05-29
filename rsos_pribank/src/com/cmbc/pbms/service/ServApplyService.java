package com.cmbc.pbms.service;


import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.bean.PbmsBoardingList;
import com.cmbc.pbms.bean.PbmsHospitalReg;
import com.cmbc.pbms.bean.PbmsPhysicalExam;
import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dto.QueryServApplyDto;

public interface ServApplyService {
	public EasyGridList<PbmsServApply> findServApplys(QueryServApplyDto queryDto)throws AppException;
	public PbmsServApply findServApply(String seqNo)throws AppException;
	public void deleteServApply(String idstr) throws AppException;
	public void saveServApply(PbmsServApply servApply) throws AppException;
	public void modifyServApply(PbmsServApply servApply) throws AppException;
	public int nextId();
	public void apply(PbmsServApply servApply, PbmsHospitalReg hospitalReg, PbmsApproveInfo approveInfo)throws AppException;
	public void apply(PbmsServApply servApply, PbmsPhysicalExam physicalExam, PbmsApproveInfo approveInfo)throws AppException;
	public void apply(PbmsServApply servApply, PbmsBoardingList boarding, PbmsApproveInfo approveInfo)throws AppException;
	public void apply(PbmsServApply servApply, PbmsApproveInfo approveInfo)throws AppException;
}
