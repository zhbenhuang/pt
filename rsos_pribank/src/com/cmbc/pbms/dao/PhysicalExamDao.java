package com.cmbc.pbms.dao;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.pbms.bean.PbmsPhysicalExam;

public interface PhysicalExamDao extends EasyBaseDao<PbmsPhysicalExam> {
	public PbmsPhysicalExam selectPhysicalExam(String seqNo);
	public void deletePhysicalExam(String idstr);
	public int getMaxId();
}
