package com.cmbc.sa.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import rsos.framework.flow.ProService;

import com.opensymphony.xwork2.ActionSupport;

@Component("processDeployAction")
@Scope("prototype")
public class ProcessDeployAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processDepFile;
	private Logger logger = Logger.getLogger(ProcessDeployAction.class);
	private ProService proService;
	

	/**
	 * 发布创建的流程
	 * @return
	 */
	public String processDeploy(){
		try {
			if (processDepFile != null && processDepFile != "") {
				//将ZIP文件封转到IO流里
				InputStream is = new FileInputStream(new File(processDepFile));
				ZipInputStream zis = new ZipInputStream(is);
				//将ZIP流程文件发布到pvm（流程虚拟机中，他会把ZIP包中的xml文件和png图片存储到数据库中）
				String deploymentId = proService.deployProcess(zis);//发布流程,使用流程定义的*.jpdl.xml文件
				zis.close();
				is.close();
				if(deploymentId==null){
					logger.info("发布流程失败");
					return "error";
				}
				logger.info("发布流程成功");
				return "deploysuccess";
			}else{
				logger.info("发布流程失败");
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发布流程失败");
			return "error";
		}
	}
	
	public String getProcessDepFile() {
		return processDepFile;
	}

	public void setProcessDepFile(String processDepFile) {
		this.processDepFile = processDepFile;
	}
	

}
