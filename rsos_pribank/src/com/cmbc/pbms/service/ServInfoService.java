package com.cmbc.pbms.service;

import java.util.List;
import java.util.Map;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.dto.QueryServInfoDto;

public interface ServInfoService {
	public EasyGridList<PbmsServInfo> findServInfos(QueryServInfoDto queryDto)throws AppException;
	public PbmsServInfo findServInfo(String serId)throws AppException;
	public void deleteServInfo(String idstr) throws AppException;
	public void saveServInfo(PbmsServInfo servInfo) throws AppException;
	public void modifyServInfo(PbmsServInfo servInfo) throws AppException;
	public int nextId();
}
