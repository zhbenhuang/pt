package com.cmbc.funcmanage.dao;

import com.cmbc.funcmanage.bean.RedemptionInfo;

import rsos.framework.db.EasyBaseDao;

public interface RedempDao extends EasyBaseDao<RedemptionInfo> {

	RedemptionInfo findRedempInfoByCode(String codeId);

}
