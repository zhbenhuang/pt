package rsos.framework.easyui;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class EasyResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String retCode;
	private String message;
	
	public EasyResult() {
	}

	public EasyResult(String retCode, String message) {
		this.retCode = retCode;
		this.message = message;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson(){
		return JSONObject.fromObject(this).toString();
	}
}
