package com.cmbc.reportStatistic.bean;

import java.io.Serializable;

public class FdevMonthlySummary implements Serializable {

	private static final long serialVersionUID = 6493203595225864540L;

	private int serialNumber;
	private String departmentId;
	private String departmentName;
	private String yearMonth;
	private int fdevTotal;
	private int fdevPassTotal;
	private int fdevUnPassTotal;
	private int fdevObjectTotal;
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public int getFdevTotal() {
		return fdevTotal;
	}
	public void setFdevTotal(int fdevTotal) {
		this.fdevTotal = fdevTotal;
	}
	public int getFdevPassTotal() {
		return fdevPassTotal;
	}
	public void setFdevPassTotal(int fdevPassTotal) {
		this.fdevPassTotal = fdevPassTotal;
	}
	public int getFdevUnPassTotal() {
		return fdevUnPassTotal;
	}
	public void setFdevUnPassTotal(int fdevUnPassTotal) {
		this.fdevUnPassTotal = fdevUnPassTotal;
	}
	public int getFdevObjectTotal() {
		return fdevObjectTotal;
	}
	public void setFdevObjectTotal(int fdevObjectTotal) {
		this.fdevObjectTotal = fdevObjectTotal;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	
	
	
	
}
