package com.cmbc.flow.service;

import java.io.File;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.flow.bean.FlowDefinition;
import com.cmbc.flow.bean.QueryFlowDto;

public interface FlowService {
	public EasyGridList<FlowDefinition> queryFlowList(QueryFlowDto queryDto) throws AppException;
	public void deleteFlow(String idstr) throws AppException;
	public void deployFlowByName(String fileName) throws AppException;
	public void deployFlow(File uploadFile) throws AppException;
}
