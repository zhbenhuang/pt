package com.cmbc.flow.dao;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.flow.bean.ProcessIns;

public interface ProcessInsDao extends EasyBaseDao<ProcessIns> {

	ProcessIns findProcessInsByCode(String codeId);

	ProcessIns findProcessInsByCode(String codeId, String processTypeName);
}
