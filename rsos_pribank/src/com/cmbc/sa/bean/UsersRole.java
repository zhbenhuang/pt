package com.cmbc.sa.bean;

import java.io.Serializable;

public class UsersRole  implements Serializable {
	private static final long serialVersionUID = 1L;
    // Fields    

     private String id;
     private String userId;
     private String roleId;
     private Integer business;


    // Constructors

    /** default constructor */
    public UsersRole() {
    }

	/** minimal constructor */
    public UsersRole(String id, String userId) {
        this.id = id;
        this.userId = userId;
    }
    
    /** full constructor */
    public UsersRole(String id, String userId, String roleId,Integer business) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.business = business;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

	public Integer getBusiness() {
		return business;
	}

	public void setBusiness(Integer business) {
		this.business = business;
	}
   








}