package com.cmbc.pbms.service;

import java.util.List;
import java.util.Map;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsStruInfo;
import com.cmbc.pbms.bean.PbmsStruValue;
import com.cmbc.pbms.dto.QueryStruValueDto;

public interface StruValueService {
	public EasyGridList<PbmsStruValue> findStruValues(QueryStruValueDto queryDto)throws AppException;
	public PbmsStruValue findStruValue(String struId, String term)throws AppException;
	public void deleteStruValue(String idstr) throws AppException;
	public void saveStruValue(PbmsStruValue struValue) throws AppException;
	public void modifyStruValue(PbmsStruValue struValue) throws AppException;
	public int nextId();
}
