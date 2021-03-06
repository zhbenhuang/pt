package com.cmbc.pbms.bean;

// Generated 2015-5-4 16:45:24 by Hibernate Tools 3.4.0.CR1

/**
 * PbmsBoardingList generated by hbm2java
 */
public class PbmsBoardingList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8066744740305386171L;
	private int seqNo;
	private String clientName;
	private String servDate;
	private String fltNo;
	private String takeoffTime;
	private String arrivalTime;
	private String provenace;
	private String destination;
	private String pickTime;
	private String pickAddr;
	private Integer carType;
	private String licenseNumber;
	private Integer consignLuggage;
	private Integer peopleNum;

	public PbmsBoardingList() {
	}

	public PbmsBoardingList(int seqNo) {
		this.seqNo = seqNo;
	}

	public PbmsBoardingList(int seqNo, String clientName, String servDate,
			String fltNo, String takeoffTime, String arrivalTime, String provenace,
			String destination, String pickTime, String pickAddr,
			Integer carType, String licenseNumber, Integer consignLuggage,
			Integer peopleNum) {
		this.seqNo = seqNo;
		this.clientName = clientName;
		this.servDate = servDate;
		this.fltNo = fltNo;
		this.takeoffTime = takeoffTime;
		this.arrivalTime = arrivalTime;
		this.provenace = provenace;
		this.destination = destination;
		this.pickTime = pickTime;
		this.pickAddr = pickAddr;
		this.carType = carType;
		this.licenseNumber = licenseNumber;
		this.consignLuggage = consignLuggage;
		this.peopleNum = peopleNum;
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

	public String getServDate() {
		return this.servDate;
	}

	public void setServDate(String servDate) {
		this.servDate = servDate;
	}

	public String getFltNo() {
		return this.fltNo;
	}

	public void setFltNo(String fltNo) {
		this.fltNo = fltNo;
	}

	public String getTakeoffTime() {
		return this.takeoffTime;
	}

	public void setTakeoffTime(String takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public String getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getProvenace() {
		return this.provenace;
	}

	public void setProvenace(String provenace) {
		this.provenace = provenace;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPickTime() {
		return this.pickTime;
	}

	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}

	public String getPickAddr() {
		return this.pickAddr;
	}

	public void setPickAddr(String pickAddr) {
		this.pickAddr = pickAddr;
	}

	public Integer getCarType() {
		return this.carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public String getLicenseNumber() {
		return this.licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Integer getConsignLuggage() {
		return this.consignLuggage;
	}

	public void setConsignLuggage(Integer consignLuggage) {
		this.consignLuggage = consignLuggage;
	}

	public Integer getPeopleNum() {
		return this.peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

}
