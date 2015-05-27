package com.cmbc.pbms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.catalina.startup.FailedContext;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import rsos.framework.struts2.BaseAction;
import rsos.framework.utils.UploadConfigurationRead;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.service.ServInfoService;
public class UploadServInfoFileAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1896915260152387341L;
	private ServInfoService servInfoService;
	public void setServInfoService(ServInfoService servInfoService) {
		this.servInfoService = servInfoService;
	}
	
	private List<File> fileName;//这里的"fileName"一定要与表单中的文件域名相同
	private List<String> fileNameContentType;//格式同上"fileName"+ContentType
	private List<String> fileNameFileName;//格式同上"fileName"+FileName
	
	public List<File> getFileName() {
		return fileName;
	}

	public void setFileName(List<File> fileName) {
		this.fileName = fileName;
	}

	public List<String> getFileNameContentType() {
		return fileNameContentType;
	}

	public void setFileNameContentType(List<String> fileNameContentType) {
		this.fileNameContentType = fileNameContentType;
	}

	public List<String> getFileNameFileName() {
		return fileNameFileName;
	}

	public void setFileNameFileName(List<String> fileNameFileName) {
		this.fileNameFileName = fileNameFileName;
	}

	private String getSavePath() {
		return UploadConfigurationRead.getInstance().getConfigItem("uploadServInfoPath").trim();
	}

	@Override
	public String execute() throws Exception {
		printParameters();
		File dir=new File(getSavePath());
		if(!dir.exists()){
			dir.mkdirs();
		}
		String serId = getHttpRequest().getParameter("serId");
		String fileType = getHttpRequest().getParameter("fileType");
		fileType = fileType == null?"":fileType;
		String tmpPath = "";
		tmpPath = getTmpPath(fileType);
		String fullPath = "";
		boolean isNew = false;
		PbmsServInfo servInfo = null;
		if(serId == null || serId.length()<=0 || serId.equalsIgnoreCase("-1") || serId.equalsIgnoreCase("null")){
			System.out.println("serId("+serId+") is null, maybe new");  
			fullPath = getSavePath()+"/new/"+tmpPath;
			//return ERROR;
			isNew = true;
		}else{
			servInfo = servInfoService.findServInfo(serId);
			if(servInfo == null) return ERROR;
			fullPath = getSavePath()+"/"+servInfo.getSerId()+"/"+tmpPath;
			isNew = false;
		}
		File subDir=new File(fullPath);
		if(!subDir.exists()){
			subDir.mkdirs();
		}else if(subDir.listFiles().length>0){
			cleanDir(subDir);
		}
		List<File> files= getFileName();
		List<String> fileNames= getFileNameFileName();
		for(int i=0;i<files.size();i++){
			String fullFileName = fullPath+"/"+fileNames.get(i);
			FileOutputStream fos=new FileOutputStream(fullFileName);
			FileInputStream fis=new FileInputStream(files.get(i));
			byte []buffers=new byte[1024];
			int len=0;
			while((len=fis.read(buffers))!=-1){
				fos.write(buffers,0,len);
			}
			fos.flush();
			fos.close();
			fis.close();
		}
		
		//at least one file
		if(isNew){
			//save in session, wait for add
		}else{
			if(servInfo == null) return ERROR;
			for (int i = 0; i < fileNames.size(); i++) {
				String fileName = fileNames.get(i);
				if(fileType.equalsIgnoreCase("0")){
					servInfo.setSerPic(fileName);
					servInfoService.saveServInfo(servInfo);
				}else if(fileType.equalsIgnoreCase("1")){
					servInfo.setFileUrl1(fileName);
					servInfoService.saveServInfo(servInfo);
				}else if(fileType.equalsIgnoreCase("2")){
					servInfo.setFileUrl2(fileName);
					servInfoService.saveServInfo(servInfo);
				}else{
					System.out.println("not match fileType="+fileType);
					cleanDir(subDir);
					return SUCCESS;
				}
			}
		}
		return SUCCESS;
	}

	public static void cleanDir(File subDir) throws IOException {
		if(subDir == null) return ;
		if(!subDir.exists()) return ;
		try{
			FileUtils.cleanDirectory(subDir);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String getTmpPath(String fileType) {
		String tmpPath;
		if(fileType.equalsIgnoreCase("0")){
			tmpPath = "newPic";
		}else if(fileType.equalsIgnoreCase("1")){
			tmpPath = "newFile1";
		}else if(fileType.equalsIgnoreCase("2")){
			tmpPath = "newFile2";
		}else{
			tmpPath = "unknow";
		}
		return tmpPath;
	}
	protected void printParameters(){
		Enumeration en=getHttpRequest().getParameterNames();
		System.out.println("--------------------------------");
		System.out.println("Parameters:");
		System.out.println("--------------------------------");
		while(en.hasMoreElements()){   
			String paramName=(String)en.nextElement();                       
			String[] values=getHttpRequest().getParameterValues(paramName);   
			
			for(int i=0;i<values.length;i++){   
				System.out.println("["+i+"] "+paramName+" = "+values[i]);  
			}			
		}
		System.out.println("--------------------------------");
	}

	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
