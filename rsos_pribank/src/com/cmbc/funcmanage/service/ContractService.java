package com.cmbc.funcmanage.service;
import java.io.File;
import java.util.List;
import java.util.Map;

import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.bean.BranchContractView;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.NoticeView;
import com.cmbc.funcmanage.bean.RedempBook;


public interface ContractService{
	
		
		public List<Contract> findByPagination(String table,int currentPage , int pageSize ,Map<String,Object> m)throws AppException;
		
		public int getTotalNew(String table,Map<String,Object> m)throws AppException;

		public List<Contract> findByPage(String table,int currentPage, int pageSize,Map<String, Object> m)throws AppException;

		public List<NoticeView> findNoticesByPage(String table,int currentPage, int pageSize,Map<String, Object> m)throws AppException;

		public int getTotal(String table, Map<String, Object> m)throws AppException;

		public int getNoticeTotal(String table, Map<String, Object> m)throws AppException;

		public Contract findById(String contractId)throws AppException;

		public void saveContract(Contract contract)throws AppException;

		public void modifyContract(Contract contract)throws AppException;

		public void deleteContract(String[] contractBusIds)throws AppException;

		public void inputContractByPIO(File file)throws AppException;

		public NoticeView viewNotice(String noticeId)throws AppException;

		public List<RedempBook> findRedempBooks(String table, int currentPage,
				int pageSize, Map<String, Object> m)throws AppException;

		public int getBooksTotal(String table, Map<String, Object> m)throws AppException;

		public List<BranchContractView> findBranchContractByPage(String table,
				int currentPage, int pageSize, Map<String, Object> m)throws AppException;

		public int getBranchContractTotal(String table, Map<String, Object> m)throws AppException;

		public Contract findContractByContractId(String contractId)throws AppException;

		public void dealNotice(String noticeId)throws AppException;

		public List<BranchContractView> findBranchProductContract(
				int currentPage, int pageSize, Map<String, Object> m)throws AppException;

		public int getTotalBranchProductContract(Map<String, Object> m)throws AppException;

		public NoticeView reViewNotice(String noticeId)throws AppException;
}
	