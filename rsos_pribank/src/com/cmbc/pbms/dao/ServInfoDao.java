package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.dto.QueryServInfoDto;

public interface ServInfoDao extends EasyBaseDao<PbmsServInfo> {
	public EasyGridList<PbmsServInfo> selectServInfos(QueryServInfoDto queryDto);
	public PbmsServInfo selectServInfo(String serId);
	public void deleteServInfo(String idstr);
	public int getMaxId();
}
