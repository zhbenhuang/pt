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

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.dto.QueryApproveInfoDto;
import com.cmbc.pbms.service.ApproveInfoService;
import com.cmbc.sa.bean.Users;
@Scope("prototype")
@Controller("approveInfoAction")
public class ApproveInfoAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private ApproveInfoService approveInfoService;
	private EasyResult ret;
	
	public void setApproveInfoService(ApproveInfoService approveInfoService) {
		this.approveInfoService = approveInfoService;
	}
	
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}	

	public void queryApproveInfoList()
	{
		try{
			log.info("-------queryApproveInfoList--------");
			printParameters();
			String apprId = getHttpRequest().getParameter("apprId");
			String apprType = getHttpRequest().getParameter("apprType");
			String apprStatus = getHttpRequest().getParameter("apprStatus");
			String applyUserId = getHttpRequest().getParameter("applyUserId");
			
			QueryApproveInfoDto queryDto = new QueryApproveInfoDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setApprId(apprId);
			queryDto.setApprType(apprType);
			queryDto.setApprStatus(apprStatus);
			queryDto.setApplyUserId(applyUserId);
			
			String path = getHttpRequest().getContextPath();
			EasyGridList<PbmsApproveInfo> ulist = approveInfoService.findApproveInfos(queryDto);
			if(ulist != null && ulist.getRows()!= null){
				for (PbmsApproveInfo temp : ulist.getRows()) {
					temp.setOpt("<button onclick=\"cancel('"+temp.getApprId()+"');\">终止</button>&nbsp;<button onclick=\"pass('"+temp.getApprId()+"');\">通过</button>&nbsp;<button onclick=\"pass('"+temp.getApprId()+"');\">不通过</button>");
				}
			}
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
	
	
	public void findApproveInfo(){
		try{
			log.info("-------findApproveInfo--------");
			printParameters();
			String apprId = getHttpRequest().getParameter("apprId");
			
			PbmsApproveInfo approveInfo = approveInfoService.findApproveInfo(apprId);
			EasyObject<PbmsApproveInfo> result = new EasyObject<PbmsApproveInfo>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), approveInfo);
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveApproveInfo(){
		try{
			log.info("-------saveApproveInfo--------");
			printParameters();
			int apprId = approveInfoService.nextId();
			String apprType = getHttpRequest().getParameter("apprType");
			String apprStatus = getHttpRequest().getParameter("apprStatus");
			int applyNo = 0;//Integer.parseInt(getHttpRequest().getParameter("applyNo"));
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String applyUserId = user.getUserId();
			String curUserId = user.getUserId();
			String applyTime = getHttpRequest().getParameter("applyTime");
			
			String apprStep = "";//生成申请单时，从审批参数表中获取审批步骤赋值到本字段。每次审批后，此字段减一。
			String userId1 = "";
			String apprTime1 = "";
			String procResult1 = "";
			String remark1 = "";
			String userId2 = "";
			String apprTime2 = "";
			String procResult2 = "";
			String remark2 = "";
			String userId3 = "";
			String apprTime3 = "";
			String procResult3 = "";
			String remark3 = "";
			String userId4 = "";
			String apprTime4 = "";
			String procResult4 = "";
			String remark4 = "";
			String userId5 = "";
			String apprTime5 = "";
			String procResult5 = "";
			String remark5 = "";
			String apprCont = getHttpRequest().getParameter("apprCont");
			
			String apprParam1 = "";
			String apprParam2 = "";
			String apprParam3 = "";
			String apprParam4 = "";
			String apprParam5 = "";
			
			PbmsApproveInfo oldApproveInfo = approveInfoService.findApproveInfo(apprId+"");
			if(oldApproveInfo!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				PbmsApproveInfo approveInfo = new PbmsApproveInfo();
				approveInfo.setApprId(apprId);
				approveInfo.setApprType(Integer.parseInt(apprType));
				approveInfo.setApprStatus(Integer.parseInt(apprStatus));
				approveInfo.setApplyNo(applyNo);
				approveInfo.setApplyUserId(applyUserId);
				approveInfo.setCurUserId(curUserId);
				approveInfo.setApplyTime(applyTime);
				approveInfo.setApprStep(Integer.parseInt(apprStep));
				approveInfo.setUserId1(userId1);
				approveInfo.setApprTime1(apprTime1);
				approveInfo.setProcResult1(Integer.parseInt(procResult1));
				approveInfo.setRemark1(remark1);
				
				approveInfo.setUserId2(userId2);
				approveInfo.setApprTime2(apprTime2);
				approveInfo.setProcResult2(Integer.parseInt(procResult2));
				approveInfo.setRemark1(remark2);
				
				approveInfo.setUserId3(userId3);
				approveInfo.setApprTime3(apprTime3);
				approveInfo.setProcResult3(Integer.parseInt(procResult3));
				approveInfo.setRemark3(remark3);
				
				approveInfo.setUserId4(userId4);
				approveInfo.setApprTime4(apprTime4);
				approveInfo.setProcResult4(Integer.parseInt(procResult4));
				approveInfo.setRemark4(remark4);
				
				approveInfo.setUserId5(userId5);
				approveInfo.setApprTime5(apprTime5);
				approveInfo.setProcResult5(Integer.parseInt(procResult5));
				approveInfo.setRemark1(remark5);
				
				approveInfo.setApprCont(apprCont);
				approveInfo.setApprParam1(apprParam1);
				approveInfo.setApprParam2(apprParam2);
				approveInfo.setApprParam3(apprParam3);
				approveInfo.setApprParam4(apprParam4);
				approveInfo.setApprParam5(apprParam5);
				approveInfoService.saveApproveInfo(approveInfo);	
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
	
	public void modifyApproveInfo(){
		try{
			log.info("-------modifyApproveInfo--------");
			printParameters();
			String apprId = getHttpRequest().getParameter("apprId");
			String apprType = getHttpRequest().getParameter("apprType");
			String apprStatus = getHttpRequest().getParameter("apprStatus");
			String applyNo = getHttpRequest().getParameter("applyNo");
			String apprUserId = getHttpRequest().getParameter("apprUserId");
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String curUserId = user.getUserId();
			String applyTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.UP_ITEM_DATEFORMAT_FULL);
			
			
			PbmsApproveInfo approveInfo = approveInfoService.findApproveInfo(apprId);
			approveInfo.setApprId(Integer.parseInt(apprId));
			approveInfo.setApprType(Integer.parseInt(apprType));
			approveInfo.setApprStatus(Integer.parseInt(apprStatus));
			
			approveInfoService.modifyApproveInfo(approveInfo);
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
	
	public void deleteApproveInfo(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
			approveInfoService.deleteApproveInfo(ids);
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
