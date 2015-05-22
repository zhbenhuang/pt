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
import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dto.QueryServApplyDto;
import com.cmbc.pbms.service.ApproveInfoService;
import com.cmbc.pbms.service.ServApplyService;
import com.cmbc.sa.bean.Users;
import com.opensymphony.xwork2.Action;
@Scope("prototype")
@Controller("servApplyAction")
public class ServApplyAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private ServApplyService servApplyService;
	private ApproveInfoService approveInfoService;
	private EasyResult ret;
	
	public void setServApplyService(ServApplyService servApplyService) {
		this.servApplyService = servApplyService;
	}
	public void setApproveInfoService(ApproveInfoService approveInfoService) {
		this.approveInfoService = approveInfoService;
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
			String seqNo = getHttpRequest().getParameter("seqNo");
			String serId = getHttpRequest().getParameter("serId");
			String apprId = getHttpRequest().getParameter("apprId");
			
			QueryServApplyDto queryDto = new QueryServApplyDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setSeqNo(seqNo);
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
			String seqNo = getHttpRequest().getParameter("seqNo");
			
			PbmsServApply servApply = servApplyService.findServApply(seqNo);
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
			int seqNo = servApplyService.nextId();
			String serId = getHttpRequest().getParameter("serId");
			int apprId = -1;//待被审批时填入此值
			
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
			
			//create servApply
			PbmsServApply oldServApply = servApplyService.findServApply(seqNo+"");
			if(oldServApply!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				PbmsServApply servApply = new PbmsServApply();
				servApply.setSeqNo(seqNo);
				servApply.setSerId(Integer.parseInt(serId));
				servApply.setApprId(apprId);
				servApply.setStruId(struId);
				servApply.setUserId(userId);
				servApply.setClientId(clientId);
				servApply.setPbclientName(pbclientName);
				servApply.setMobilePhone(mobilePhone);
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
			String seqNo = getHttpRequest().getParameter("seqNo");
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
			
			PbmsServApply servApply = servApplyService.findServApply(seqNo);
			servApply.setSeqNo(Integer.parseInt(seqNo));
			servApply.setSerId(Integer.parseInt(serId));
			servApply.setApprId(Integer.parseInt(apprId));
			servApply.setStruId(struId);
			servApply.setUserId(userId);
			
			servApply.setClientId(clientId);
			servApply.setPbclientName(pbclientName);
			servApply.setMobilePhone(mobilePhone);
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
	 * ids为组合键（seqNo,business).
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
