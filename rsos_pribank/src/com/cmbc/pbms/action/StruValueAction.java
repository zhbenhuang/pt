package com.cmbc.pbms.action;

import java.util.Map;

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

import com.cmbc.pbms.bean.PbmsStruValue;
import com.cmbc.pbms.dto.QueryStruValueDto;
import com.cmbc.pbms.service.StruValueService;
import com.cmbc.sa.service.DepartmentService;
@Scope("prototype")
@Controller("struValueAction")
public class StruValueAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;	
	
	private StruValueService struValueService;
	private DepartmentService departmentService;
	private EasyResult ret;
	
	public void setStruValueService(StruValueService struValueService) {
		this.struValueService = struValueService;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public EasyResult getRet() {
		return ret;
	}
	public void setRet(EasyResult ret) {
		this.ret = ret;
	}	

	public void queryStruValueList()
	{
		try{
			log.info("-------queryStruValueList--------");
			printParameters();
			String struId = getHttpRequest().getParameter("struId");
			String term = getHttpRequest().getParameter("term");
			
			QueryStruValueDto queryDto = new QueryStruValueDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setStruId(struId);
			queryDto.setTerm(term);
			
			EasyGridList<PbmsStruValue> ulist = struValueService.findStruValues(queryDto);
			Map<String, String> map = departmentService.getDepartmentMap();
			for (PbmsStruValue temp : ulist.getRows()) {
				String struName  = map.get(temp.getStruId());
				temp.setStruName(struName == null?"":struName);
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
	
	public void findStruValue(){
		try{
			log.info("-------findStruValue--------");
			printParameters();
			String struId = getHttpRequest().getParameter("struId");
			String term = getHttpRequest().getParameter("term");
			
			PbmsStruValue struValue = struValueService.findStruValue(struId, term);
			EasyObject<PbmsStruValue> result = new EasyObject<PbmsStruValue>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), struValue);
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
