package com.cmbc.funcmanage.dao;

public interface TimerTaskDao{

	public void contractAlarmTask()throws Exception;

	public void cleanContractAlarmTask()throws Exception;

	public void contractRedempTask()throws Exception;

	public void updateProRedemTask()throws Exception;

	public void productStepInRedTask()throws Exception;

	public void productUpComingTask()throws Exception;
	
}
