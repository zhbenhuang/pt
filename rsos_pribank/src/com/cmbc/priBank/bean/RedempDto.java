package com.cmbc.priBank.bean;

import java.io.Serializable;

import rsos.framework.constant.StaticVariable;
import rsos.framework.utils.CalendarUtil;

import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.flow.bean.ProcessPublicDto;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.bean.Users;

public class RedempDto extends ProcessPublicDto implements Serializable {

	private static final long serialVersionUID = 665207049782018138L;
	public static String processKey = "RP";	//标示流程类别
	public static String processTypeName = "理财产品赎回流程"; //流程类型名
	
	private String remark;
	private String contractId;
	private String codeId;
	private String assignUserId;
	private String customeId;
	private String customeName;
	private String redemptionIntervalId;
	private Users user;
	private Department department;
	
	
	
	
	public ProcessIns initProcessIns(String businessId) {
		ProcessIns processIns = new ProcessIns();
		processIns.setBusinessId(businessId);
		processIns.setCreateUserId(user.getUserId());																		//开发信息保存成功，然后保存"业务实例"的额外信息
		processIns.setCreateUserName(user.getUsername());															//记录流程的发起人
		processIns.setDepartmentId(user.getDepartmentId());
		processIns.setDepartment(department.getDepartmentName());										//发起机构
		processIns.setStatus(StaticVariable.SAVED);
		processIns.setProcessTypeName(processTypeName);														//记录流程类型
		processIns.setCreateTime(CalendarUtil.getDateTimeStr());																//记录流程开发新建时间
		processIns.setTopic(generateTopic());																								//流程刚启动，未结束
		return processIns;
	}
	
	private String generateTopic() {
		return "针对客户"+customeName+"("+customeId+")"+"的"+processTypeName;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Users getUser() {
		return user;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Department getDepartment() {
		return department;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}

	public String getAssignUserId() {
		return assignUserId;
	}

	public String getCustomeId() {
		return customeId;
	}

	public void setCustomeId(String customeId) {
		this.customeId = customeId;
	}

	public String getCustomeName() {
		return customeName;
	}

	public void setCustomeName(String customeName) {
		this.customeName = customeName;
	}

	public void setRedemptionIntervalId(String redemptionIntervalId) {
		this.redemptionIntervalId = redemptionIntervalId;
	}

	public String getRedemptionIntervalId() {
		return redemptionIntervalId;
	}
}
