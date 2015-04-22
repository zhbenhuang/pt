package com.cmbc.funcmanage.dao;
import java.util.List;
import java.util.Map;

import com.cmbc.funcmanage.bean.BranchContractView;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.NoticeView;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.bean.RedempBook;

import rsos.framework.db.EasyBaseDao;


public interface ContractDao extends EasyBaseDao<Contract>{

	public int getTotalNew(String table,Map<String, Object> m);

	public List<Contract> findByPage(String table,int currentPage, int pageSize,Map<String, Object> m);

	public List<NoticeView> findNoticesByPage(String table,int currentPage, int pageSize,Map<String, Object> m);

	public List<Contract> findByPagination(String table,int currentPage, int pageSize,Map<String, Object> m);

	public int getTotal(String table, Map<String, Object> m);

	public int getNoticeTotal(String table, Map<String, Object> m);

	public void deleteContract(String contractBusId);

	public Contract findContractByCode(String codeId);

	public Contract findContractByBusId(String businessId);
	
	public Contract findContractByContractId(String contractId);

	public List<RedempBook> findRedempBooks(String table, int currentPage,
			int pageSize, Map<String, Object> m);

	public int getBooksTotal(String table, Map<String, Object> m);

	public List<BranchContractView> findBranchContractByPage(String table,
			int currentPage, int pageSize, Map<String, Object> m);

	public int getBranchContractTotal(String table, Map<String, Object> m);

	public List<BranchContractView> findBranchProductContract(int currentPage,
			int pageSize, Map<String, Object> m);

	public int getTotalBranchProductContract(Map<String, Object> m);

	public void setContractRedempTime(ProductRedemptionInterval pri,String contractId);

	public ProductRedemptionInterval findContractRedempTimeById(
			String contractId);
}