package com.cmbc.pbms.service;

import java.util.List;
import java.util.Map;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.dto.QueryApproveInfoDto;

public interface ApproveInfoService {
	public EasyGridList<PbmsApproveInfo> findApproveInfos(QueryApproveInfoDto queryDto)throws AppException;
	public PbmsApproveInfo findApproveInfo(String apprId)throws AppException;
	public void deleteApproveInfo(String idstr) throws AppException;
	public void saveApproveInfo(PbmsApproveInfo approveInfo) throws AppException;
	public void modifyApproveInfo(PbmsApproveInfo approveInfo) throws AppException;
	public int nextId();
}
