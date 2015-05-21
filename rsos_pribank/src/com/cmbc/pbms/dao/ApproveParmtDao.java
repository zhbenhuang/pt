package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.pbms.bean.PbmsApproveParmt;
import com.cmbc.pbms.dto.QueryApproveParmtDto;

public interface ApproveParmtDao extends EasyBaseDao<PbmsApproveParmt> {
	public EasyGridList<PbmsApproveParmt> selectApproveParmts(QueryApproveParmtDto queryDto);
	public PbmsApproveParmt selectApproveParmt(String apprType);
	public void deleteApproveParmt(String idstr);
	public int getMaxId();
}
