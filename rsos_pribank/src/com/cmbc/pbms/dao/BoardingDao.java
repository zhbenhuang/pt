package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.pbms.bean.PbmsBoardingList;

public interface BoardingDao extends EasyBaseDao<PbmsBoardingList> {
	public PbmsBoardingList selectBoarding(String seqNo);
	public void deleteBoarding(String idstr);
	public int getMaxId();
}
