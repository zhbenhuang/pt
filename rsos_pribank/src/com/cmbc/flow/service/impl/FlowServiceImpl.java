package com.cmbc.flow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;
import rsos.framework.utils.StringUtil;

import com.cmbc.flow.bean.FlowDefinition;
import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.service.FlowService;

public class FlowServiceImpl implements FlowService{
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ProcessEngine processEngine;
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public EasyGridList<FlowDefinition> queryFlowList(QueryFlowDto queryDto)
			throws AppException {
		try {
			log.info("----queryFlowList---");
			EasyGridList<FlowDefinition> ret = new EasyGridList<FlowDefinition>();
			RepositoryService repositoryService = processEngine.getRepositoryService();
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
			if(!StringUtil.isEmpty(queryDto.getFlowName())){
				query.processDefinitionNameLike(queryDto.getFlowName());
			}
			ret.setTotal(new Long(query.count()).intValue());
			
			int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
			query.page(startIndex, queryDto.getPageDto().getPageSize());
			
			List<ProcessDefinition> pdList = query.list();
			List<FlowDefinition> list = new ArrayList<FlowDefinition>();
			if(pdList!=null){
				for(ProcessDefinition p: pdList){
					FlowDefinition d = new FlowDefinition();
					d.setProcessDefinitionId(p.getId());
					d.setName(p.getName());
					d.setDescription(p.getDescription());
					d.setVersion(Integer.toString(p.getVersion()));
					d.setDeploymentId(p.getDeploymentId());
					d.setProcessPngName(p.getImageResourceName());
					list.add(d);
				}
			}
			ret.setRows(list);
			return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	public void deleteFlow(String idstr) throws AppException{
		try{
			RepositoryService repositoryService = processEngine.getRepositoryService();
			StringTokenizer st = new StringTokenizer(idstr, ",");
	        while(st.hasMoreTokens()){
	        	String deploymentId = st.nextToken();
	        	repositoryService.deleteDeploymentCascade(deploymentId);
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void deployFlowByName(String fileName) throws AppException{
		ZipInputStream zis = null;
		try{
			zis = new ZipInputStream(new FileInputStream(new File(fileName)));
			
			RepositoryService repositoryService = processEngine.getRepositoryService();
			NewDeployment d = repositoryService.createDeployment().addResourcesFromZipInputStream(zis);
			String deploymentId = d.deploy();
			
			if(deploymentId==null){
				throw new AppException("E000012", new String[]{fileName});				
			}
		} catch(AppException e){			
			e.printStackTrace();
			throw e;
		} catch (Exception e) {			
			throw new AppException(Constants.RETCODE_999999);
		}finally{
			try{
				zis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void deployFlow(File uploadFile) throws AppException{
		ZipInputStream zis = null;
		try{
			zis = new ZipInputStream(new FileInputStream(uploadFile));
			
			RepositoryService repositoryService = processEngine.getRepositoryService();
			NewDeployment d = repositoryService.createDeployment().addResourcesFromZipInputStream(zis);
			String deploymentId = d.deploy();
			
			if(deploymentId==null){
				throw new AppException("E000012", new String[]{"upload"});				
			}
		} catch(AppException e){			
			e.printStackTrace();
			throw e;
		} catch (Exception e) {			
			throw new AppException(Constants.RETCODE_999999);
		}finally{
			try{
				zis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
