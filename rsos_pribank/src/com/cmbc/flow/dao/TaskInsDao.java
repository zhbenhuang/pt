package com.cmbc.flow.dao;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.bean.TaskIns;

public interface TaskInsDao extends EasyBaseDao<TaskIns> {
	public EasyGridList<TaskIns> selectTaskView(QueryFlowDto queryDto);
}
