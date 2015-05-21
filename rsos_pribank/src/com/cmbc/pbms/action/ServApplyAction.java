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

import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dto.QueryServApplyDto;
import com.cmbc.pbms.service.ServApplyService;
import com.cmbc.sa.bean.Users;
import com.opensymphony.xwork2.Action;
@Scope("prototype")
@Controller("servApplyAction")
public class ServApplyAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private ServApplyService servApplyService;
	private EasyResult ret;
	
	public void setServApplyService(ServApplyService servApplyService) {
		this.servApplyService = servApplyService;
	}
	
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}	

	public void queryServApplyList()
	{
		try{
			log.info("-------queryServApplyList--------");
			printParameters();
			String serNo = getHttpRequest().getParameter("serNo");
			String serId = getHttpRequest().getParameter("serId");
			String apprId = getHttpRequest().getParameter("apprId");
			
			QueryServApplyDto queryDto = new QueryServApplyDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setSerNo(serNo);
			queryDto.setSerId(serId);
			queryDto.setApprId(apprId);
			
			EasyGridList<PbmsServApply> ulist = servApplyService.findServApplys(queryDto);
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
	
	
	public void findServApply(){
		try{
			log.info("-------findServApply--------");
			printParameters();
			String serNo = getHttpRequest().getParameter("serNo");
			
			PbmsServApply servApply = servApplyService.findServApply(serNo);
			EasyObject<PbmsServApply> result = new EasyObject<PbmsServApply>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), servApply);
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void newServApply(){
		try{
			log.info("-------newServApply--------");
			printParameters();
			String serId = getHttpRequest().getParameter("serId");
			
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String struId = user.getDepartmentId();
			String userId = user.getUserId();
			
			EasyResult result = new EasyResult(Constants.RETCODE_00000, getText(Constants.RETCODE_00000));
			writeJsonSuccess(result.toJson());
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveServApply(){
		try{
			log.info("-------saveServApply--------");
			printParameters();
			int serNo = servApplyService.nextId();
			String serId = getHttpRequest().getParameter("serId");
			String apprId = "";//create a appr id
			
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String struId = user.getDepartmentId();
			String userId = user.getUserId();
			String clientId = getHttpRequest().getParameter("clientId");
			String pbclientName = getHttpRequest().getParameter("pbclientName");
			String mobilePhone = getHttpRequest().getParameter("mobilePhone");
			int applyQuatt = Integer.parseInt(getHttpRequest().getParameter("applyQuatt"));
			String applyTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.UP_ITEM_DATEFORMAT_FULL);
			String fileUrl1 = getHttpRequest().getParameter("fileUrl1");
			String fileUrl2 = getHttpRequest().getParameter("fileUrl2");
			String remark = getHttpRequest().getParameter("remark");
			String applyStatus = "1";
			
			
			PbmsServApply oldServApply = servApplyService.findServApply(serNo+"");
			if(oldServApply!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				PbmsServApply servApply = new PbmsServApply();
				servApply.setSeqNo(serNo);
				servApply.setSerId(Integer.parseInt(serId));
				servApply.setApprId(Integer.parseInt(apprId));
				servApply.setStruId(struId);
				servApply.setUserId(Integer.parseInt(userId));
				servApply.setClientId(Integer.parseInt(clientId));
				servApply.setPbclientName(pbclientName);
				servApply.setMobilePhone(Integer.parseInt(mobilePhone));
				servApply.setApplyTime(applyTime);
				servApply.setApplyQuatt(applyQuatt);
				servApply.setFileUrl1(fileUrl1);
				servApply.setFileUrl2(fileUrl2);
				servApply.setRemark(remark);
				servApply.setApplyStatus(applyStatus);
				servApplyService.saveServApply(servApply);	
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
	
	public void modifyServApply(){
		try{
			log.info("-------modifyServApply--------");
			printParameters();
			String serNo = getHttpRequest().getParameter("serNo");
			String serId = getHttpRequest().getParameter("serId");
			String apprId = getHttpRequest().getParameter("apprId");
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String struId = user.getDepartmentId();
			String userId = user.getUserId();
			String clientId = getHttpRequest().getParameter("clientId");
			String pbclientName = getHttpRequest().getParameter("pbclientName");
			String mobilePhone = getHttpRequest().getParameter("mobilePhone");
			int applyQuatt = Integer.parseInt(getHttpRequest().getParameter("applyQuatt"));
			String fileUrl1 = getHttpRequest().getParameter("fileUrl1");
			String fileUrl2 = getHttpRequest().getParameter("fileUrl2");
			String remark = getHttpRequest().getParameter("remark");
			
			PbmsServApply servApply = servApplyService.findServApply(serNo);
			servApply.setSeqNo(Integer.parseInt(serNo));
			servApply.setSerId(Integer.parseInt(serId));
			servApply.setApprId(Integer.parseInt(apprId));
			servApply.setStruId(struId);
			servApply.setUserId(Integer.parseInt(userId));
			
			servApply.setClientId(Integer.parseInt(clientId));
			servApply.setPbclientName(pbclientName);
			servApply.setMobilePhone(Integer.parseInt(mobilePhone));
			servApply.setApplyQuatt(applyQuatt);
			servApply.setFileUrl1(fileUrl1);
			servApply.setFileUrl2(fileUrl2);
			servApply.setRemark(remark);
			
			//servApply.setApplyStatus(applyStatus);
			
			servApplyService.modifyServApply(servApply);
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
	 * ids为组合键（serNo,business).
	 */
	public void deleteServApply(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
		    servApplyService.deleteServApply(ids);
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
