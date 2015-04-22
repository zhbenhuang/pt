package com.cmbc.attach.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.cmbc.attach.bean.AttachInfo;
import com.cmbc.attach.service.AttachService;

import rsos.framework.struts2.BaseAction;
import rsos.framework.utils.UUIDGenerator;
import rsos.framework.utils.UploadConfigurationRead;
import rsos.framework.utils.UploadFiles;

public class UploadAttachAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    
	protected String[] FileType = { "application/pdf","application/acrobat","application/x-pdf","applications/vnd.pdf","text/pdf","text/x-pdf",			
									"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document",
									"text/plain",
									"application/vnd.openxmlformats-officedocument.presentationml.presentation","application/vnd ms-powerpoint",
									"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","application/vnd ms-excel",
									"application/zip","application/x-compressed","application/octet-stream","application/x-zip","application/x-zip-compressed","application/x-rar-compressed",
									"image/bmp","image/gif","image/jpeg","image/pjpeg","image/x-icon","image/png"
								  };
	
	protected String[] newFileType = {"PDF","PDF","PDF","PDF","PDF","PDF",
										"WORD","WORD",
										"TEXT",
										"PPT","PPT",
										"EXCEL","EXCEL",
										"ZIP","ZIP","ZIP","ZIP","ZIP","RAR",
										"IMAGE","IMAGE","IMAGE","IMAGE","IMAGE","IMAGE"
									  };
    
    private File[] upload;// 实际上传文件
	private String[] uploadContentType; // 文件的内容类型
    private String[] uploadFileName; // 上传文件名
    private List<UploadFiles> uploadFiles = new ArrayList<UploadFiles>();

	private String attachId;	//附件ID
	private String businessId = ServletActionContext.getRequest().getParameter("businessId");
    
    private String attachNewName;	//附件新名
    private String attachNewType;	//附件新类型

    private AttachService attachService;
    
    public String execute() throws Exception {
    	
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/"
								+ UploadConfigurationRead.getInstance()
										.getConfigItem("uploadAttachPath").trim());// 获得路径
		for (int i = 0; i < upload.length; i++) {
			InputStream is = new FileInputStream(upload[i]);

			String fileName = uploadFileName[i];// 上传的文件名
			String type = uploadContentType[i];// 文件类型
			String realName = UUID.randomUUID().toString() + getExt(fileName);// 保存的文件名称，使用UUID+后缀进行保存
			String tmpName = System.currentTimeMillis() + "_" + fileName; //时间戳+”_“+文件名

			File file2 = new File(targetDirectory, tmpName);
			OutputStream os = new FileOutputStream(file2);
			byte[] bu = new byte[400];
			int length = 0;
			while ((length = is.read(bu)) > 0) {
				os.write(bu, 0, length);
			}

			UploadFiles uf = new UploadFiles();// 创建文件
			uf.setUploadContentType(type);
			uf.setUploadFileName(fileName);
			uf.setUploadRealName(realName);

			uploadFiles.add(uf);// 添加到需要下载文件的List集合中

			/* 文件类型显示转换 */
			String filetype = uploadContentType[i]; // 文件类型
			for (int j = 0; j < FileType.length; j++) {
				if (filetype.equals(FileType[j])) {
					filetype = newFileType[j];
					break;
				} else
					continue;
			}
			/* 获取文件信息，存入数据库 */
			AttachInfo attachInfo = new AttachInfo();
			attachInfo.setBusinessId(businessId);			
			attachInfo.setAttachId(UUIDGenerator.generateShortUuid());
			attachInfo.setAttachName(tmpName); //插入数据库使用时间戳
			/* 将文件信息存入数据库 */
			if(attachService.save(attachInfo)){
				continue;
			} 
		}
		ServletActionContext.getRequest().setAttribute("uploadFiles", uploadFiles);
		return SUCCESS;
 
    }    

    public File[] getUpload() {
		return upload;
	}
    
	public void setUpload(File[] upload) {
		this.upload = upload;
	}
 
    public String[] getUploadContentType()
    {
       return uploadContentType;
    }
 
    public void setUploadContentType(String[] uploadContentType)
    {
       this.uploadContentType = uploadContentType;
    }
 
    public String[] getUploadFileName()
    {
       return uploadFileName;
    }
 
    public void setUploadFileName(String[] uploadFileName)
    {
       this.uploadFileName = uploadFileName;
    }
 
    public static String getExt(String fileName) //获取文件后缀
    {
       return fileName.substring(fileName.lastIndexOf("."));
    }

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getAttachNewName() {
		return attachNewName;
	}

	public void setAttachNewName(String attachNewName) {
		this.attachNewName = attachNewName;
	}

	public String getAttachNewType() {
		return attachNewType;
	}

	public void setAttachNewType(String attachNewType) {
		this.attachNewType = attachNewType;
	}

	public void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}

 
}


