package com.cmbc.sa.bean;

import java.io.Serializable;


public class RolePermission  implements Serializable {
	private static final long serialVersionUID = 1L;

    // Fields    

     private String id;
     private String roleId;
     private String permissionId;
     private Integer business;


    // Constructors

    /** default constructor */
    public RolePermission() {
    }

	/** minimal constructor */
    public RolePermission(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public RolePermission(String id, String roleId, String permissionId,Integer business) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.business = business;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return this.permissionId;
    }
    
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

	public Integer getBusiness() {
		return business;
	}

	public void setBusiness(Integer business) {
		this.business = business;
	}
   








}