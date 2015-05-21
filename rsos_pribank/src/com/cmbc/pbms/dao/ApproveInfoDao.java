package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.dto.QueryApproveInfoDto;

public interface ApproveInfoDao extends EasyBaseDao<PbmsApproveInfo> {
	public EasyGridList<PbmsApproveInfo> selectApproveInfos(QueryApproveInfoDto queryDto);
	public PbmsApproveInfo selectApproveInfo(String apprType);
	public void deleteApproveInfo(String idstr);
	public int getMaxId();
}
