package com.cmbc.priBank.service;

import rsos.framework.exception.AppException;

import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.priBank.bean.ContractDto;
import com.cmbc.sa.bean.Users;

public interface ContractProcessService {

	public void startProcess(ContractDto contractDto)throws AppException;

	public ProcessIns findProcessInsByCode(String codeId)throws AppException;

	public Contract findContractByCode(String codeId)throws AppException;

	public void signTask(String taskId, String businessId, Users user)throws AppException;

	public String passTask(String taskId, String businessId, ContractDto contractDto)throws AppException;

	public void rejectTask(String taskId, String businessId,ContractDto contractDto)throws AppException;

	public void submitXinTuo(String taskId, String businessId,ContractDto contractDto)throws AppException;

	public void xinTuoBack(String taskId, String businessId,ContractDto contractDto)throws AppException;

	public void getContract(String taskId, String businessId,
			ContractDto contractDto)throws AppException;

	public Contract findContractByContractId(String contractId)throws AppException;

	public ProcessIns findProcessInsByCode(String codeId, String processTypeName)throws AppException;

	public ProductRedemptionInterval findContractRedempTimeById(String contractId)throws AppException;

}
