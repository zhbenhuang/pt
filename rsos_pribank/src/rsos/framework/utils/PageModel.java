package rsos.framework.utils;

import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

  public class PageModel {  
	  // 和jqGrid组件相关的参数属性  
      private Integer rows = 0;  
      private Integer page = 0;  
      private Integer total = 0;  
      private Integer record = 0;  
      private String sord;  
      private String sidx;  
      private String search;  
        
      public Integer getRows() {  
          return rows;  
      }  
      public void setRows(Integer rows) {  
          this.rows = rows;  
      }  
      public Integer getPage() {  
          return page;  
      }  
      public void setPage(Integer page) {  
          this.page = page;  
      }  
      public Integer getTotal() {  
          return total;  
      }  
      public void setTotal() {  
          this.total = (int) Math.ceil((double) record / (double) rows);  
      }  
      public Integer getRecord() {  
          return record;  
      }  
      public void setRecord(Integer record) {  
          this.record = record;  
      }  
      public String getSord() {  
          return sord;  
      }  
      public void setSord(String sord) {  
          this.sord = sord;  
      }  
      public String getSidx() {  
          return sidx;  
      }  
      public void setSidx(String sidx) {  
          this.sidx = sidx;  
      }  
      public String getSearch() {  
          return search;  
      }  
      public void setSearch(String search) {  
          this.search = search;  
      }  
  }  