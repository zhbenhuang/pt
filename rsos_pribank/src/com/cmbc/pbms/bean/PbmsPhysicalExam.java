package com.cmbc.pbms.bean;

// Generated 2015-5-4 16:45:24 by Hibernate Tools 3.4.0.CR1

/**
 * PbmsPhysicalExam generated by hbm2java
 */
public class PbmsPhysicalExam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8582735349473867360L;
	private int seqNo;
	private String clientName;
	private Integer idcarNo;
	private Integer gender;
	private String servDate;
	private String examType;

	public PbmsPhysicalExam() {
	}

	public PbmsPhysicalExam(int seqNo) {
		this.seqNo = seqNo;
	}

	public PbmsPhysicalExam(int seqNo, String clientName, Integer idcarNo,
			Integer gender, String servDate, String examType) {
		this.seqNo = seqNo;
		this.clientName = clientName;
		this.idcarNo = idcarNo;
		this.gender = gender;
		this.servDate = servDate;
		this.examType = examType;
	}

	public int getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getIdcarNo() {
		return this.idcarNo;
	}

	public void setIdcarNo(Integer idcarNo) {
		this.idcarNo = idcarNo;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getServDate() {
		return this.servDate;
	}

	public void setServDate(String servDate) {
		this.servDate = servDate;
	}

	public String getExamType() {
		return this.examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

}
