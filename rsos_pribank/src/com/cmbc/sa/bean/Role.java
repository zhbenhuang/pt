package com.cmbc.sa.bean;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

    // Fields    

     private String roleId;
     private String roleName;
     private String type;
     private Integer business;


    // Constructors

    /** default constructor */
    public Role() {
    }

	/** minimal constructor */
    public Role(String roleId) {
        this.roleId = roleId;
    }
    
    /** full constructor */
    public Role(String roleId, String roleName, String type, Integer business) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.type = type;
        this.business = business;
    }

   
    // Property accessors

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public Integer getBusiness() {
        return this.business;
    }
    
    public void setBusiness(Integer business) {
        this.business = business;
    }
   








}