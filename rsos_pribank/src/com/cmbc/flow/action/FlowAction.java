package com.cmbc.flow.action;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;

import com.cmbc.flow.bean.FlowDefinition;
import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.service.FlowService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("flowAction")
public class FlowAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private FlowService flowService;
	/** 文件对象 */
	private List<File> Filedata;
	/** 文件名 */
	private List<String> FiledataFileName;
	/** 文件内容类型 */
	private List<String> FiledataContentType;
	private EasyResult ret;
	
	public void queryFlowList(){
		try{
			log.info("-------queryFlowList--------");
			printParameters();
			String flowName = getHttpRequest().getParameter("flowName");
			QueryFlowDto queryDto = new QueryFlowDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setFlowName(flowName);
			EasyGridList<FlowDefinition> ulist = flowService.queryFlowList(queryDto);
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
	
	public void deleteFlow(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
			flowService.deleteFlow(ids);
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
	
	public String deployFlow(){		
		try{			
			printParameters();
			flowService.deployFlow(Filedata.get(0));			
			return Action.SUCCESS;
		} catch(AppException e){			
			e.printStackTrace();
			addErrors(e);
			return Action.ERROR;
		} catch (Exception e) {			
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
			return Action.ERROR;
		}
		
	}
	
	public String deployFlowByName(){		
		
		try{
			log.info("-------file:"+FiledataFileName.get(0));
			printParameters();
			flowService.deployFlowByName(FiledataFileName.get(0));			
			return Action.SUCCESS;
		} catch(AppException e){			
			e.printStackTrace();
			addErrors(e);
			return Action.ERROR;
		} catch (Exception e) {			
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
			return Action.ERROR;
		}
		
	}

	
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}
	public List<File> getFiledata() {
		return Filedata;
	}
	public void setFiledata(List<File> filedata) {
		Filedata = filedata;
	}
	public List<String> getFiledataFileName() {
		return FiledataFileName;
	}
	public void setFiledataFileName(List<String> filedataFileName) {
		FiledataFileName = filedataFileName;
	}
	public List<String> getFiledataContentType() {
		return FiledataContentType;
	}
	public void setFiledataContentType(List<String> filedataContentType) {
		FiledataContentType = filedataContentType;
	}
}
