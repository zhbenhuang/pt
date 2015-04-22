package rsos.framework.easyui;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

public class EasyGridList<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String retCode;
	private String message;
	private int total;
	private List<T> rows;
	
	public EasyGridList() { }
	
	public EasyGridList(String retCode, String message, int total, List<T> rows) {
		super();
		this.retCode = retCode;
		this.message = message;
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
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