package com.cmbc.funcmanage.service;

import rsos.framework.exception.AppException;

public interface TimerTaskService {

	public void contractAlarmTask() throws AppException;

	public void cleanContractAlarmTask() throws AppException;

	public void contractRedempTask() throws AppException;

	public void updateProRedemTask() throws AppException;

	public void productStepInRedTask() throws AppException;

	public void productUpComingTask() throws AppException;
	
}
