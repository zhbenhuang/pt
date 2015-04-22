package rsos.framework.utils;

public class UploadFiles {

	private String uploadFileName;
	private String uploadContentType;
	private String uploadRealName;

	public String getUploadFileName()
	{
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName)
	{
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType()
	{
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType)
	{
		this.uploadContentType = uploadContentType;
	}
	public String getUploadRealName()
	{
		return uploadRealName;
	}
	public void setUploadRealName(String uploadRealName)
	{
		this.uploadRealName = uploadRealName;
	}
}
