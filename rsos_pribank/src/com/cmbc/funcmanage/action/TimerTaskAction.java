package com.cmbc.funcmanage.action;


import com.cmbc.funcmanage.service.TimerTaskService;

import rsos.framework.constant.Constants;
import rsos.framework.exception.AppException;

public class TimerTaskAction extends FuncBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9198002147948871977L;
	private TimerTaskService timerTaskService;
	
	public void setTimerTaskService(TimerTaskService timerTaskService) {
		this.timerTaskService = timerTaskService;
	}
	/**
	 * 每天凌晨00:30执行定时任务,生成逾期警告通知
	 */
	public void contractAlarmTask(){
		try {
			timerTaskService.contractAlarmTask();
		} catch (AppException ex) {
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch(Exception e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 每月最后一天的23:30执行，清除上一个月生成的合同逾期警告通知
	 */
	public void cleanContractAlarmTask(){
		try {
			timerTaskService.cleanContractAlarmTask();
		} catch (AppException ex) {
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch(Exception e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 每日01:10执行定时任务，生成客户已赎回的通知
	 */
	public void contractRedempTask(){
		try {
			timerTaskService.contractRedempTask();
		} catch (AppException ex) {
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch(Exception e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 每天凌晨01:30执行定时任务,更改与product关联的赎回区间的状态,
	 * 同时将当前属于正在赎回期的赎回区间更新到product中
	 */
	public void updateProRedemTask(){
		try {
			timerTaskService.updateProRedemTask();
		} catch (AppException ex) {
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch(Exception e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 每天凌晨02:00执行定时任务,生成理财产品进入赎回区间的通知
	 */
	public void productStepInRedTask(){
		try {
			timerTaskService.productStepInRedTask();
		} catch (AppException ex) {
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch(Exception e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 每天凌晨02:30执行定时任务,生成理财产品到期的通知
	 */
	public void productUpComingTask(){
		try {
			timerTaskService.productUpComingTask();
		} catch (AppException ex) {
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch(Exception e){
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}

	
}
