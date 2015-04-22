package com.cmbc.sa.bean;

import java.io.Serializable;

public class Permission  implements Serializable {
	private static final long serialVersionUID = 1L;
    // Fields    

     private String permissionId;
     private String permissionName;
     private String url;
     private String imageUrl;
     private Integer checked;
     private String parentId;
     private Integer type;


    // Constructors

    /** default constructor */
    public Permission() {
    }

	/** minimal constructor */
    public Permission(String permissionId) {
        this.permissionId = permissionId;
    }
    
    /** full constructor */
    public Permission(String permissionId, String permissionName, String url, String imageUrl, Integer checked, String parentId, Integer type) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.url = url;
        this.imageUrl = imageUrl;
        this.checked = checked;
        this.parentId = parentId;
        this.type = type;
    }

   
    // Property accessors

    public String getPermissionId() {
        return this.permissionId;
    }
    
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return this.permissionName;
    }
    
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getChecked() {
        return this.checked;
    }
    
    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }


}