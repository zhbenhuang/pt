package com.cmbc.sa.bean;

import java.io.Serializable;

public class Department implements Serializable {
	private static final long serialVersionUID = 1L;
    // Fields    

     private String departmentId;
     private String departmentName;
     private String anoDepartmentId;
     private String anoDepartmentName;
     private String type;
     private String remark;
     private String parentId;
     private String parentDepartmentName;


    // Constructors

    /** default constructor */
    public Department() {
    }

	/** minimal constructor */
    public Department(String departmentId) {
        this.departmentId = departmentId;
    }
    
    /** full constructor */
    public Department(String departmentId, String departmentName,String anoDepartmentId, String anoDepartmentName, String type, String remark, String parentId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.anoDepartmentId = anoDepartmentId;
        this.anoDepartmentName = anoDepartmentName;
        this.type = type;
        this.remark = remark;
        this.parentId = parentId;
    }


    public String getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getAnoDepartmentId() {
        return this.anoDepartmentId;
    }
    
    public void setAnoDepartmentId(String anoDepartmentId) {
        this.anoDepartmentId = anoDepartmentId;
    }

    public String getAnoDepartmentName() {
        return this.anoDepartmentName;
    }
    
    public void setAnoDepartmentName(String anoDepartmentName) {
        this.anoDepartmentName = anoDepartmentName;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public void setParentDepartmentName(String parentDepartmentName) {
		this.parentDepartmentName = parentDepartmentName;
	}

	public String getParentDepartmentName() {
		return parentDepartmentName;
	}

}