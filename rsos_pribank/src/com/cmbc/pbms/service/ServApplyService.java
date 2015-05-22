package com.cmbc.pbms.service;


import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dto.QueryServApplyDto;

public interface ServApplyService {
	public EasyGridList<PbmsServApply> findServApplys(QueryServApplyDto queryDto)throws AppException;
	public PbmsServApply findServApply(String seqNo)throws AppException;
	public void deleteServApply(String idstr) throws AppException;
	public void saveServApply(PbmsServApply servApply) throws AppException;
	public void modifyServApply(PbmsServApply servApply) throws AppException;
	public int nextId();
}
