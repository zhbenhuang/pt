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
import com.cmbc.pbms.bean.PbmsApproveParmt;
import com.cmbc.pbms.bean.PbmsBoardingList;
import com.cmbc.pbms.bean.PbmsHospitalReg;
import com.cmbc.pbms.bean.PbmsPhysicalExam;
import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.dto.QueryServApplyDto;
import com.cmbc.pbms.service.ApproveInfoService;
import com.cmbc.pbms.service.ApproveParmtService;
import com.cmbc.pbms.service.ServApplyService;
import com.cmbc.pbms.service.ServInfoService;
import com.cmbc.sa.bean.Users;
@Scope("prototype")
@Controller("servApplyAction")
public class ServApplyAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private ServApplyService servApplyService;
	private ApproveInfoService approveInfoService;//审批
	private ApproveParmtService approveParmtService;
	private ServInfoService servInfoService;
	private EasyResult ret;
	
	public void setServInfoService(ServInfoService servInfoService) {
		this.servInfoService = servInfoService;
	}
	public void setServApplyService(ServApplyService servApplyService) {
		this.servApplyService = servApplyService;
	}
	public void setApproveInfoService(ApproveInfoService approveInfoService) {
		this.approveInfoService = approveInfoService;
	}
	public void setApproveParmtService(ApproveParmtService approveParmtService) {
		this.approveParmtService = approveParmtService;
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
	
	@Deprecated
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
	
	public void apply(){
		try{
			log.info("-------apply--------");
			printParameters();
			int seqNo = servApplyService.nextId();//报名seq no
			String serId = getHttpRequest().getParameter("serId");
			PbmsServInfo servInfo = servInfoService.findServInfo(serId);
			if(servInfo == null){
				log.error("when apply, can not find ServInfo(serId="+serId+")");
				writeErrors(Constants.RETCODE_999999);
				return ;
			}
			
			int apprId = approveInfoService.nextId();
			int apprType = 5;//活动报名审批
			PbmsApproveParmt approveParmt = approveParmtService.findApproveParmt(apprType+"");
			if(approveParmt == null){
				log.error("when apply, can not find ApproveParmt(apprType="+apprType+")");
				writeErrors(Constants.RETCODE_999999);
				return ;
			}
			
			
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String struId = user.getDepartmentId();
			String userId = user.getUserId();
			
			String clientId = getHttpRequest().getParameter("clientId");
			String pbclientName = getHttpRequest().getParameter("pbclientName");
			String mobilePhone = getHttpRequest().getParameter("mobilePhone");
			int applyQuatt = Integer.parseInt(getHttpRequest().getParameter("applyQuatt"));
			String remark = getHttpRequest().getParameter("remark");
			
			//board
			String clientName = getHttpRequest().getParameter("clientName");
			String servDate = getHttpRequest().getParameter("servDate");
			String fltNo = getHttpRequest().getParameter("fltNo");
			String takeoffTime = getHttpRequest().getParameter("takeoffTime");
			String arrivalTime = getHttpRequest().getParameter("arrivalTime");
			String provenace = getHttpRequest().getParameter("provenace");
			String peopleNum = getHttpRequest().getParameter("peopleNum");
			String destination = getHttpRequest().getParameter("destination");
			//ydj
			String licenseNumber = getHttpRequest().getParameter("licenseNumber");
			String consignLuggage = getHttpRequest().getParameter("consignLuggage");
			//lbc
			String pickTime = getHttpRequest().getParameter("pickTime");
			String pickAddr = getHttpRequest().getParameter("pickAddr");
			String carType = getHttpRequest().getParameter("carType");
			//tj
			String clientName_tj = getHttpRequest().getParameter("clientName_tj");
			String idcarNo_tj = getHttpRequest().getParameter("idcarNo_tj");
			String gender_tj = getHttpRequest().getParameter("gender_tj");
			String servDate_tj = getHttpRequest().getParameter("servDate_tj");
			String examType = getHttpRequest().getParameter("examType");
			
			//gh
			String clientName_gh = getHttpRequest().getParameter("clientName_gh");
			String idcarNo_gh = getHttpRequest().getParameter("idcarNo_gh");
			String gender_gh = getHttpRequest().getParameter("gender_gh");
			String servDate_gh = getHttpRequest().getParameter("servDate_gh");
			String hospital = getHttpRequest().getParameter("hospital");
			String medicalLabor = getHttpRequest().getParameter("medicalLabor");
			String docter = getHttpRequest().getParameter("docter");
			String illnessDes = getHttpRequest().getParameter("illnessDes");
			
			//String fileUrl1 = getHttpRequest().getParameter("fileUrl1");
			//String fileUrl2 = getHttpRequest().getParameter("fileUrl2");
			String applyTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.UP_ITEM_DATEFORMAT_FULL);
			String applyStatus = "1";//审批中
			
			
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
				servApply.setApplyQuatt(applyQuatt);
				servApply.setRemark(remark);
				
				//servApply.setFileUrl1(fileUrl1);
				//servApply.setFileUrl2(fileUrl2);
				servApply.setApplyTime(applyTime);
				servApply.setApplyStatus(applyStatus);
				
				PbmsApproveInfo approveInfo = new PbmsApproveInfo();
				approveInfo.setApprId(apprId);
				approveInfo.setApprType(apprType);
				approveInfo.setApprStatus(1);//审批中， 这里的status 与 servApply.applyStatus类似
				approveInfo.setApplyNo(seqNo);
				approveInfo.setApplyUserId(userId);
				approveInfo.setCurUserId(userId);
				approveInfo.setApplyTime(applyTime);
				approveInfo.setApprStep(approveParmt.getStepNum());
				
				
				if(servInfo.getBigType()==1 && servInfo.getSmlType()==1){
					PbmsBoardingList boarding = new PbmsBoardingList();
					boarding.setClientName(clientName);
					boarding.setServDate(servDate);
					boarding.setFltNo(fltNo);
					boarding.setTakeoffTime(takeoffTime);
					boarding.setArrivalTime(arrivalTime);
					boarding.setProvenace(provenace);
					boarding.setPeopleNum(Integer.parseInt(peopleNum));
					boarding.setDestination(destination);
					boarding.setLicenseNumber(licenseNumber);
					boarding.setConsignLuggage(Integer.parseInt(consignLuggage));
					servApplyService.apply(servApply, boarding, approveInfo);	
				}else if(servInfo.getBigType()==1 && servInfo.getSmlType()==2){
					PbmsBoardingList boarding = new PbmsBoardingList();
					boarding.setClientName(clientName);
					boarding.setServDate(servDate);
					boarding.setFltNo(fltNo);
					boarding.setTakeoffTime(takeoffTime);
					boarding.setArrivalTime(arrivalTime);
					boarding.setProvenace(provenace);
					boarding.setPeopleNum(Integer.parseInt(peopleNum));
					boarding.setDestination(destination);
					boarding.setPickTime(pickTime);
					boarding.setPickAddr(pickAddr);
					boarding.setCarType(Integer.parseInt(carType));
					servApplyService.apply(servApply, boarding, approveInfo);	
				}else if(servInfo.getBigType()==1 && servInfo.getSmlType()==3){
					PbmsPhysicalExam physicalExam = new PbmsPhysicalExam();
					physicalExam.setSeqNo(seqNo);
					physicalExam.setClientName(clientName_tj);
					physicalExam.setIdcarNo(idcarNo_tj);
					physicalExam.setGender(Integer.parseInt(gender_tj));
					physicalExam.setServDate(servDate_tj);
					physicalExam.setExamType(examType);
					servApplyService.apply(servApply, physicalExam, approveInfo);	
				}else if(servInfo.getBigType()==1 && servInfo.getSmlType()==4){
					PbmsHospitalReg hospitalReg = new PbmsHospitalReg();
					hospitalReg.setClientName(clientName_gh);
					hospitalReg.setIdcarNo(idcarNo_gh);
					hospitalReg.setGender(Integer.parseInt(gender_gh));
					hospitalReg.setServDate(servDate_gh);
					hospitalReg.setHospital(Integer.parseInt(hospital));
					hospitalReg.setMedicalLabor(Integer.parseInt(medicalLabor));
					hospitalReg.setDocter(docter);
					hospitalReg.setIllnessDes(illnessDes);
					servApplyService.apply(servApply, hospitalReg, approveInfo);	
				}
				
				
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
