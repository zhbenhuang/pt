package com.cmbc.pbms.dao;


import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.pbms.bean.PbmsStruValue;
import com.cmbc.pbms.dto.QueryStruValueDto;

public interface StruValueDao extends EasyBaseDao<PbmsStruValue> {
	public EasyGridList<PbmsStruValue> selectStruValues(QueryStruValueDto queryDto);
	public PbmsStruValue selectStruValue(String struId, String term);
	public void deleteStruValue(String idstr);
	public int getMaxId();
}
