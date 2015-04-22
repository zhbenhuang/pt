package com.cmbc.funcmanage.service.impl;

import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.dao.TimerTaskDao;
import com.cmbc.funcmanage.service.TimerTaskService;


public class TimerTaskServiceImpl implements TimerTaskService {
	private TimerTaskDao timerTaskDao;

	public void setTimerTaskDao(TimerTaskDao timerTaskDao){
		this.timerTaskDao = timerTaskDao;
	}
	
	@Override
	public void contractAlarmTask()throws AppException{
		try{
			timerTaskDao.contractAlarmTask();
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000017",new String[]{"合同逾期通知"});
		}
	}
	@Override
	public void cleanContractAlarmTask()throws AppException{
		try{
			timerTaskDao.cleanContractAlarmTask();
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000017",new String[]{"清理过期通知"});
		}
	}
	@Override
	public void contractRedempTask()throws AppException {
		try{
			timerTaskDao.contractRedempTask();
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000017",new String[]{"合同赎回通知"});
		}
	}

	@Override
	public void updateProRedemTask()throws AppException {
		try{
			timerTaskDao.updateProRedemTask();
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000017",new String[]{"更新赎回区间状态"});
		}
	}

	@Override
	public void productStepInRedTask()throws AppException {
		try{
			timerTaskDao.productStepInRedTask();
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000017",new String[]{"产品进入赎回区间通知"});
		}
	}

	@Override
	public void productUpComingTask()throws AppException {

		try{
			timerTaskDao.productUpComingTask();
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000017",new String[]{"产品到期通知"});
		}
	}
	

}
