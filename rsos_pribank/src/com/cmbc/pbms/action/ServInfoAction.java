package com.cmbc.pbms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import rsos.framework.utils.UploadConfigurationRead;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.dto.QueryServInfoDto;
import com.cmbc.pbms.service.ServInfoService;
import com.cmbc.sa.bean.Users;
@Scope("prototype")
@Controller("servInfoAction")
public class ServInfoAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private ServInfoService servInfoService;
	private EasyResult ret;
	
	public void setServInfoService(ServInfoService servInfoService) {
		this.servInfoService = servInfoService;
	}
	
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}	

	public void queryServInfoList()
	{
		try{
			log.info("-------queryServInfoList--------");
			printParameters();
			String serId = getHttpRequest().getParameter("serId");
			String serName = getHttpRequest().getParameter("serName");
			
			QueryServInfoDto queryDto = new QueryServInfoDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setSerId(serId);
			if(serName != null && serName.length() > 0) serName = "%"+serName+"%";
			queryDto.setSerName(serName);
			
			String path = getHttpRequest().getContextPath();
			EasyGridList<PbmsServInfo> ulist = servInfoService.findServInfos(queryDto);
			/*if(ulist != null && ulist.getRows()!= null){
				for (PbmsServInfo temp : ulist.getRows()) {
					String paramsStr = "";
					paramsStr += "serId="+temp.getSerId();
					paramsStr += "&serName="+temp.getSerName();
					paramsStr += "&serPic="+temp.getSerPic();
					paramsStr += "&serValue="+temp.getSerValue();
					paramsStr += "&bigType="+temp.getBigType();
					paramsStr += "&smlType="+temp.getSmlType();
					temp.setOpt("<img src=\""+path+"/img/edit.gif\" align=\"absmiddle\" /><a onclick=\"newServApply('"+temp.getSerId()+"');\">申请</a>");
				}
			}*/
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
	
	
	public void findServInfo(){
		try{
			log.info("-------findServInfo--------");
			printParameters();
			String serId = getHttpRequest().getParameter("serId");
			
			PbmsServInfo servInfo = servInfoService.findServInfo(serId);
			EasyObject<PbmsServInfo> result = new EasyObject<PbmsServInfo>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), servInfo);
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveServInfo(){
		try{
			log.info("-------saveServInfo--------");
			printParameters();
			int serId = servInfoService.nextId();
			String serName = getHttpRequest().getParameter("serName");
			String serDes = getHttpRequest().getParameter("serDes");
			int bigType = Integer.parseInt(getHttpRequest().getParameter("bigType"));
			int smlType = Integer.parseInt(getHttpRequest().getParameter("smlType"));
			int serValue = Integer.parseInt(getHttpRequest().getParameter("serValue"));
			int serAmount = Integer.parseInt(getHttpRequest().getParameter("serAmount"));
			String begTime = getHttpRequest().getParameter("begTime");
			String endTime = getHttpRequest().getParameter("endTime");
			String serPic = getHttpRequest().getParameter("serPic");
			String fileUrl1 = getHttpRequest().getParameter("fileUrl1");
			String fileUrl2 = getHttpRequest().getParameter("fileUrl2");
			
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String userId = user.getUserId();//getHttpRequest().getParameter("userId");
			String alterTime_DateTimeString = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD_HHmmss);
			String alterTime = alterTime_DateTimeString;//getHttpRequest().getParameter("alterTime");
			
			if(begTime!=null && endTime!=null && begTime.compareTo(endTime) > 0){
				log.error("when add, begTime can not bigger than endTime");
				writeErrors(Constants.RETCODE_999999);
				return ;
			}
			
			PbmsServInfo oldServInfo = servInfoService.findServInfo(serId+"");
			if(oldServInfo!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				PbmsServInfo servInfo = new PbmsServInfo();
				servInfo.setSerId(serId);
				servInfo.setSerName(serName);
				servInfo.setSerDes(serDes);
				servInfo.setBigType(bigType);
				servInfo.setSmlType(smlType);
				servInfo.setSerValue(serValue);
				servInfo.setSerAmount(serAmount);
				servInfo.setBegTime(begTime);
				servInfo.setEndTime(endTime);
				servInfo.setSerPic(serPic);
				servInfo.setFileUrl1(fileUrl1);
				servInfo.setFileUrl2(fileUrl2);
				servInfo.setUserId(userId);
				servInfo.setAlterTime(alterTime);
				servInfoService.saveServInfo(servInfo);	
				//copy file
				File fileDir_new = new File(getSavePath()+"/new");
				if(fileDir_new.exists()){
					File fileDir_xx = new File(getSavePath()+"/"+servInfo.getSerId());
					if(!fileDir_xx.exists()){
						fileDir_xx.mkdirs();
					}else{
						UploadServInfoFileAction.cleanDir(fileDir_xx);
					}
					String serPic_sourceFullPath = getSavePath()+"/new/"+UploadServInfoFileAction.getTmpPath("0");
					String serPic_targetFullPath = getSavePath()+"/"+servInfo.getSerId()+"/"+UploadServInfoFileAction.getTmpPath("0");
					copyFile(servInfo.getSerPic(), serPic_sourceFullPath, serPic_targetFullPath);
					
					String fileUrl1_sourceFullFileName = getSavePath()+"/new/"+UploadServInfoFileAction.getTmpPath("1");
					String fileUrl1_targetFullFileName = getSavePath()+"/"+servInfo.getSerId()+"/"+UploadServInfoFileAction.getTmpPath("1");
					copyFile(servInfo.getFileUrl1(), fileUrl1_sourceFullFileName, fileUrl1_targetFullFileName);
					
					String fileUrl2_sourceFullFileName = getSavePath()+"/new/"+UploadServInfoFileAction.getTmpPath("2");
					String fileUrl2_targetFullFileName = getSavePath()+"/"+servInfo.getSerId()+"/"+UploadServInfoFileAction.getTmpPath("2");
					copyFile(servInfo.getFileUrl2(), fileUrl2_sourceFullFileName, fileUrl2_targetFullFileName);
					
					UploadServInfoFileAction.cleanDir(fileDir_new);
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
	private String getSavePath() {
		return UploadConfigurationRead.getInstance().getConfigItem("uploadServInfoPath").trim();
	}

	public static void copyFile(String fileName, String sourceFullPath,
			String targetFullPath) throws FileNotFoundException,
			IOException {
		File sourceDir = new File(sourceFullPath);
		if(!sourceDir.exists()){
			System.out.println("when copy file, souce dir("+sourceFullPath+") not found!");
			return ;
		}
		File targetDir = new File(targetFullPath);
		if(!targetDir.exists()){
			targetDir.mkdirs();
		}
		FileOutputStream fos=new FileOutputStream(targetFullPath+"/"+fileName);
		FileInputStream fis=new FileInputStream(sourceFullPath+"/"+fileName);
		byte []buffers=new byte[1024];
		int len=0;
		while((len=fis.read(buffers))!=-1){
			fos.write(buffers,0,len);
		}
		fos.flush();
		fos.close();
		fis.close();
	}
	
	public void modifyServInfo(){
		try{
			log.info("-------modifyServInfo--------");
			printParameters();
			String serId = getHttpRequest().getParameter("serId");
			String serName = getHttpRequest().getParameter("serName");
			String serDes = getHttpRequest().getParameter("serDes");
			String bigType = getHttpRequest().getParameter("bigType");
			String smlType = getHttpRequest().getParameter("smlType");
			String serValue = getHttpRequest().getParameter("serValue");
			String serAmount = getHttpRequest().getParameter("serAmount");
			String begTime = getHttpRequest().getParameter("begTime");
			String endTime = getHttpRequest().getParameter("endTime");
			String serPic = getHttpRequest().getParameter("serPic");
			String fileUrl1 = getHttpRequest().getParameter("fileUrl1");
			String fileUrl2 = getHttpRequest().getParameter("fileUrl2");
			Users user = (Users)getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY);
			String userId = user.getUserId();//getHttpRequest().getParameter("userId");
			String alterTime_DateTimeString = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD_HHmmss);
			String alterTime = alterTime_DateTimeString;//getHttpRequest().getParameter("alterTime");
			
			PbmsServInfo servInfo = servInfoService.findServInfo(serId);
			if(servInfo == null){
				log.error("when modify, can not find serId="+serId);
				writeErrors(Constants.RETCODE_999999);
				return ;
			}
			if(begTime!=null && endTime!=null && begTime.compareTo(endTime) > 0){
				log.error("when add, begTime can not bigger than endTime");
				writeErrors(Constants.RETCODE_999999);
				return ;
			}
			
			servInfo.setSerId(Integer.parseInt(serId));
			servInfo.setSerName(serName);
			servInfo.setSerDes(serDes);
			servInfo.setBigType(Integer.parseInt(bigType));
			servInfo.setSmlType(Integer.parseInt(smlType));
			servInfo.setSerValue(Integer.parseInt(serValue));
			servInfo.setSerAmount(Integer.parseInt(serAmount));
			servInfo.setBegTime(begTime);
			servInfo.setEndTime(endTime);
			//servInfo.setSerPic(serPic);
			//servInfo.setFileUrl1(fileUrl1);
			//servInfo.setFileUrl2(fileUrl2);
			servInfo.setUserId(userId);
			servInfo.setAlterTime(alterTime);
			
			servInfoService.modifyServInfo(servInfo);
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
	 * ids为组合键（serId,business).
	 */
	public void deleteServInfo(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
		    servInfoService.deleteServInfo(ids);
		    if(ids != null){
		    	String[] arrays = ids.split(",");
		    	for (String serId : arrays) {
		    		File fileDir_xx = new File(getSavePath()+"/"+serId);
		    		UploadServInfoFileAction.cleanDir(fileDir_xx);
		    		fileDir_xx.delete();
		    	}
		    }
		    
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
