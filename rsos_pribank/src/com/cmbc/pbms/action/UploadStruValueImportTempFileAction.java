package com.cmbc.pbms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.startup.FailedContext;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import rsos.framework.constant.StaticVariable;
import rsos.framework.struts2.BaseAction;
import rsos.framework.struts2.ImportXSL;
import rsos.framework.struts2.ImportXSL2;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;
import rsos.framework.utils.UploadConfigurationRead;

import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.bean.PbmsStruValue;
import com.cmbc.pbms.bean.PbmsStruValueId;
import com.cmbc.pbms.service.ServInfoService;
import com.cmbc.pbms.service.StruValueService;
public class UploadStruValueImportTempFileAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1896915260152387341L;
	private StruValueService struValueService;
	public void setStruValueService(StruValueService struValueService) {
		this.struValueService = struValueService;
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
		return UploadConfigurationRead.getInstance().getConfigItem("uploadStruValueImportTempFilePath").trim();
	}

	@Override
	public String execute() throws Exception {
		printParameters();
		File dir=new File(getSavePath());
		if(!dir.exists()){
			dir.mkdirs();
		}
		String term = getHttpRequest().getParameter("importTerm");
		String term2 = getHttpRequest().getParameter("term");
		
		List<File> files= getFileName();
		List<String> fileNames= getFileNameFileName();
		
		String fullFileName = "";
		for(int i=0;i<files.size();i++){
			fullFileName = getSavePath()+"/"+fileNames.get(i);
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
		
	
		Set<PbmsStruValue> struValues = new HashSet<PbmsStruValue>();
		try{
			
			//解析
			InputStream fis = new FileInputStream(new File(fullFileName));
			List<Map<String, String>> contractList= ImportXSL2.importRows(fis);//只是将excel表中的数据抽取出来,得继续进一步处理
			Iterator iterator = contractList.iterator();
			iterator.next();//discard first row
			while(iterator.hasNext()){
				Map<String, String> map = (Map<String, String>)iterator.next();
				PbmsStruValue psv = new PbmsStruValue();
				String struId = map.get("struId");
				PbmsStruValueId id = new PbmsStruValueId(struId, term);
				psv.setId(id);
				String struName = map.get("struName");
				psv.setStruName(struName);
				String begValueStr = map.get("begValue");
				if(begValueStr == null || begValueStr.trim().length() <=0){
					psv.setBegValue(0);
				}else{
					psv.setBegValue(Integer.parseInt(begValueStr));
				}
				psv.setSurpValue(psv.getBegValue());
				String alterTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD_HHmmss);
				psv.setAlterTime(alterTime);
				if(psv.getStruId() != null &&  psv.getTerm() != null){
					struValues.add(psv);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//update to db
		struValueService.importManyStruValue(struValues );
		
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
