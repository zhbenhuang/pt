package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dto.QueryServApplyDto;

public interface ServApplyDao extends EasyBaseDao<PbmsServApply> {
	public EasyGridList<PbmsServApply> selectServApplys(QueryServApplyDto queryDto);
	public PbmsServApply selectServApply(String seqNo);
	public void deleteServApply(String idstr);
	public int getMaxId();
}
