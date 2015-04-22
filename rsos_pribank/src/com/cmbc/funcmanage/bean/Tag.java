package com.cmbc.funcmanage.bean;

import java.io.Serializable;


public class Tag implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String tagId;
	private String prefix;
	private String startCode;
	private String endCode;
	private String path;
	private String printer;
	private String printDate;
	private String date;
	

	public String getTagId() {
		return tagId;
	}


	public void setTagId(String tagId) {
		this.tagId = tagId;
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getStartCode() {
		return startCode;
	}


	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}


	public String getEndCode() {
		return endCode;
	}


	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getPrinter() {
		return printer;
	}


	public void setPrinter(String printer) {
		this.printer = printer;
	}


	public String getPrintDate() {
		return printDate;
	}


	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
}