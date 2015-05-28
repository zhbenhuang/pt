package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.pbms.bean.PbmsHospitalReg;

public interface HospitalRegDao extends EasyBaseDao<PbmsHospitalReg> {
	public PbmsHospitalReg selectHospitalReg(String seqNo);
	public void deleteHospitalReg(String idstr);
	public int getMaxId();
}
