package com.cmbc.sa.bean;

import java.io.Serializable;

public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	 private String userId;
     private String username;
     private String address;
     private String contact;
     private String sex;
     private String password;
     private String mail;
     private Integer enabled;
     private Integer business;
     private Integer rank;
     private String departmentId;
     private String departmentName;


    // Constructors

    /** default constructor */
    public Users() {
    }

	/** minimal constructor */
    public Users(String userId) {
        this.userId = userId;
    }
    
    /** full constructor */
    public Users(String userId, String username, String address, String contact, String sex, String password, String mail, Integer enabled, Integer business, Integer rank, String departmentId) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.contact = contact;
        this.sex = sex;
        this.password = password;
        this.mail = mail;
        this.enabled = enabled;
        this.business = business;
        this.rank = rank;
        this.departmentId = departmentId;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return this.mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getBusiness() {
        return this.business;
    }
    
    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getRank() {
        return this.rank;
    }
    
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Users) ) return false;
		 Users castOther = ( Users ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getAddress()==castOther.getAddress()) || ( this.getAddress()!=null && castOther.getAddress()!=null && this.getAddress().equals(castOther.getAddress()) ) )
 && ( (this.getContact()==castOther.getContact()) || ( this.getContact()!=null && castOther.getContact()!=null && this.getContact().equals(castOther.getContact()) ) )
 && ( (this.getSex()==castOther.getSex()) || ( this.getSex()!=null && castOther.getSex()!=null && this.getSex().equals(castOther.getSex()) ) )
 && ( (this.getPassword()==castOther.getPassword()) || ( this.getPassword()!=null && castOther.getPassword()!=null && this.getPassword().equals(castOther.getPassword()) ) )
 && ( (this.getMail()==castOther.getMail()) || ( this.getMail()!=null && castOther.getMail()!=null && this.getMail().equals(castOther.getMail()) ) )
 && ( (this.getEnabled()==castOther.getEnabled()) || ( this.getEnabled()!=null && castOther.getEnabled()!=null && this.getEnabled().equals(castOther.getEnabled()) ) )
 && ( (this.getBusiness()==castOther.getBusiness()) || ( this.getBusiness()!=null && castOther.getBusiness()!=null && this.getBusiness().equals(castOther.getBusiness()) ) )
 && ( (this.getRank()==castOther.getRank()) || ( this.getRank()!=null && castOther.getRank()!=null && this.getRank().equals(castOther.getRank()) ) )
 && ( (this.getDepartmentId()==castOther.getDepartmentId()) || ( this.getDepartmentId()!=null && castOther.getDepartmentId()!=null && this.getDepartmentId().equals(castOther.getDepartmentId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getAddress() == null ? 0 : this.getAddress().hashCode() );
         result = 37 * result + ( getContact() == null ? 0 : this.getContact().hashCode() );
         result = 37 * result + ( getSex() == null ? 0 : this.getSex().hashCode() );
         result = 37 * result + ( getPassword() == null ? 0 : this.getPassword().hashCode() );
         result = 37 * result + ( getMail() == null ? 0 : this.getMail().hashCode() );
         result = 37 * result + ( getEnabled() == null ? 0 : this.getEnabled().hashCode() );
         result = 37 * result + ( getBusiness() == null ? 0 : this.getBusiness().hashCode() );
         result = 37 * result + ( getRank() == null ? 0 : this.getRank().hashCode() );
         result = 37 * result + ( getDepartmentId() == null ? 0 : this.getDepartmentId().hashCode() );
         return result;
   }

public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}

public String getDepartmentName() {
	return departmentName;
}   
   








}