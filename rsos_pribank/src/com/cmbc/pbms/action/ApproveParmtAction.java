package com.cmbc.pbms.action;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyObject;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;
import rsos.framework.utils.CalendarUtil;

import com.cmbc.pbms.bean.PbmsApproveParmt;
import com.cmbc.pbms.dto.QueryApproveParmtDto;
import com.cmbc.pbms.service.ApproveParmtService;
import com.cmbc.sa.bean.Users;
@Scope("prototype")
@Controller("approveParmtAction")
public class ApproveParmtAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private ApproveParmtService approveParmtService;
	private EasyResult ret;
	
	public void setApproveParmtService(ApproveParmtService approveParmtService) {
		this.approveParmtService = approveParmtService;
	}
	
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}	

	public void queryApproveParmtList()
	{
		try{
			log.info("-------queryApproveParmtList--------");
			printParameters();
			String apprType = getHttpRequest().getParameter("apprType");
			
			QueryApproveParmtDto queryDto = new QueryApproveParmtDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setApprType(apprType);
			
			EasyGridList<PbmsApproveParmt> ulist = approveParmtService.findApproveParmts(queryDto);
			ulist.setRetCode(Constants.RETCODE_00000);
			ulist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+ulist.toJson());
			writeJsonSuccess(ulist.toJson());			
			
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	
	public void findApproveParmt(){
		try{
			log.info("-------findApproveParmt--------");
			printParameters();
			String apprType = getHttpRequest().getParameter("apprType");
			
			PbmsApproveParmt approveParmt = approveParmtService.findApproveParmt(apprType);
			EasyObject<PbmsApproveParmt> result = new EasyObject<PbmsApproveParmt>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), approveParmt);
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveApproveParmt(){
		try{
			log.info("-------saveApproveParmt--------");
			printParameters();
			int apprType = approveParmtService.nextId();
			String apprName = getHttpRequest().getParameter("apprName");
			String apprSwitch = getHttpRequest().getParameter("apprSwitch");
			int stepNum = Integer.parseInt(getHttpRequest().getParameter("stepNum"));
			String stepRole1 = getHttpRequest().getParameter("stepRole1");
			String stepRole2 = getHttpRequest().getParameter("stepRole2");
			String stepRole3 = getHttpRequest().getParameter("stepRole3");
			String stepRole4 = getHttpRequest().getParameter("stepRole4");
			String stepRole5 = getHttpRequest().getParameter("stepRole5");
			
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String userId = user.getUserId();//getHttpRequest().getParameter("userId");
			String alterTime_DateTimeString = CalendarUtil.formatDatetime(new Date(), CalendarUtil.UP_ITEM_DATEFORMAT_FULL);
			String alterTime = alterTime_DateTimeString;//getHttpRequest().getParameter("alterTime");
			
			PbmsApproveParmt oldApproveParmt = approveParmtService.findApproveParmt(apprType+"");
			if(oldApproveParmt!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				PbmsApproveParmt approveParmt = new PbmsApproveParmt();
				approveParmt.setApprType(apprType);
				approveParmt.setApprName(apprName);
				approveParmt.setApprSwitch(Integer.parseInt(apprSwitch));
				approveParmt.setStepNum(stepNum);
				approveParmt.setStepRole1(stepRole1);
				approveParmt.setStepRole2(stepRole2);
				approveParmt.setStepRole3(stepRole3);
				approveParmt.setStepRole4(stepRole4);
				approveParmt.setStepRole5(stepRole5);
				approveParmt.setUserId(userId);
				approveParmt.setAlterTime(alterTime);
				approveParmtService.saveApproveParmt(approveParmt);	
				EasyResult result = new EasyResult(Constants.RETCODE_00000,
						getText(Constants.RETCODE_00000));
				writeJsonSuccess(result.toJson());
			}
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void modifyApproveParmt(){
		try{
			log.info("-------modifyApproveParmt--------");
			printParameters();
			String apprType = getHttpRequest().getParameter("apprType");
			String apprName = getHttpRequest().getParameter("apprName");
			String apprSwitch = getHttpRequest().getParameter("apprSwitch");
			String stepNum = getHttpRequest().getParameter("stepNum");
			String stepRole1 = getHttpRequest().getParameter("stepRole1");
			String stepRole2 = getHttpRequest().getParameter("stepRole2");
			String stepRole3 = getHttpRequest().getParameter("stepRole3");
			String stepRole4 = getHttpRequest().getParameter("stepRole4");
			String stepRole5 = getHttpRequest().getParameter("stepRole5");
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String userId = user.getUserId();//getHttpRequest().getParameter("userId");
			String alterTime_DateTimeString = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD_HHmmss);
			String alterTime = alterTime_DateTimeString;//getHttpRequest().getParameter("alterTime");
			
			PbmsApproveParmt approveParmt = approveParmtService.findApproveParmt(apprType);
			approveParmt.setApprType(Integer.parseInt(apprType));
			approveParmt.setApprName(apprName);
			approveParmt.setApprSwitch(Integer.parseInt(apprSwitch));
			approveParmt.setStepNum(Integer.parseInt(stepNum));
			approveParmt.setStepRole1(stepRole1);
			approveParmt.setStepRole2(stepRole2);
			approveParmt.setStepRole3(stepRole3);
			approveParmt.setStepRole4(stepRole4);
			approveParmt.setStepRole5(stepRole5);
			approveParmt.setUserId(userId);
			approveParmt.setAlterTime(alterTime);
			
			approveParmtService.modifyApproveParmt(approveParmt);
			EasyResult result = new EasyResult(Constants.RETCODE_00000,
					getText(Constants.RETCODE_00000));
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * ids为组合键（apprType,business).
	 */
	public void deleteApproveParmt(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
			approveParmtService.deleteApproveParmt(ids);
			EasyResult result = new EasyResult(Constants.RETCODE_00000,
						getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+result.toJson());
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
}
