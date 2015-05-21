package com.cmbc.pbms.service;

import java.util.List;
import java.util.Map;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsApproveParmt;
import com.cmbc.pbms.dto.QueryApproveParmtDto;

public interface ApproveParmtService {
	public EasyGridList<PbmsApproveParmt> findApproveParmts(QueryApproveParmtDto queryDto)throws AppException;
	public PbmsApproveParmt findApproveParmt(String apprType)throws AppException;
	public void deleteApproveParmt(String idstr) throws AppException;
	public void saveApproveParmt(PbmsApproveParmt approveParmt) throws AppException;
	public void modifyApproveParmt(PbmsApproveParmt approveParmt) throws AppException;
	public int nextId();
}
