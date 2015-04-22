package rsos.framework.easyui;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class EasyObject<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String retCode;
	private String message;
	private T obj;
	
	public EasyObject() {
	}

	public EasyObject(String retCode, String message, T obj) {		super();
		this.retCode = retCode;
		this.message = message;
		this.obj = obj;
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

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String toJson(){
		return JSONObject.fromObject(this).toString();
	}
}
