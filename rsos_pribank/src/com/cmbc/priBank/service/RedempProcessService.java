package com.cmbc.priBank.service;

import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.bean.RedemptionInfo;
import com.cmbc.priBank.bean.RedempDto;

public interface RedempProcessService {

	public void startProcess(RedempDto redempDto)throws AppException;

	public RedemptionInfo findRedempInfo(String businessId)throws AppException;

	public String passTask(String taskId, String businessId, RedempDto redempDto)throws AppException;

	public void rejectTask(String taskId, String businessId, RedempDto redempDto)throws AppException;

	public void submitXinTuo(String taskId, String businessId,
			RedempDto redempDto)throws AppException;

	public RedemptionInfo findRedempInfoByCode(String codeId)throws AppException;

}
