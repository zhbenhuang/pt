package com.cmbc.funcmanage.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.ImportXSL;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;

import com.cmbc.funcmanage.bean.BranchContractView;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.Notice;
import com.cmbc.funcmanage.bean.NoticeView;
import com.cmbc.funcmanage.bean.RedempBook;
import com.cmbc.funcmanage.dao.ContractDao;
import com.cmbc.funcmanage.dao.NoticeDao;
import com.cmbc.funcmanage.dao.ProductDao;
import com.cmbc.funcmanage.service.ContractService;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.dao.DepartmentDao;


public class ContractServiceImpl implements ContractService {
	private ContractDao contractDao;
	private ProductDao productDao;
	private DepartmentDao departmentDao;
	private NoticeDao noticeDao;
	
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	@Override
	public List<Contract> findByPagination(String table,int currentPage , int pageSize ,Map<String,Object> m)throws AppException{
		try{
			return contractDao.findByPagination(table,currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
		
	}
	@Override
	public int getTotalNew(String table,Map<String,Object> m)throws AppException{
		try{
			return contractDao.getTotalNew(table,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}

	@Override
	public List<Contract> findByPage(String table,int currentPage, int pageSize,
			Map<String, Object> m) throws AppException{
		try{
			return contractDao.findByPage(table,currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public List<NoticeView> findNoticesByPage(String table,int currentPage, int pageSize,
			Map<String, Object> m)throws AppException{
		try{
			return contractDao.findNoticesByPage(table,currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}

	@Override
	public int getTotal(String table, Map<String, Object> m)throws AppException {
		
		try{
			return contractDao.getTotal(table,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}

	@Override
	public int getNoticeTotal(String table, Map<String, Object> m)throws AppException {
		
		try{
			return contractDao.getNoticeTotal(table,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
		
	}

	@Override
	public Contract findById(String contractId)throws AppException {
		try{
			return contractDao.selectBy(contractId);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{contractId});
		}
		
	}

	@Override
	public void saveContract(Contract contract) throws AppException {
		try {
			contractDao.insert(contract);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}

	@Override
	public void modifyContract(Contract contract) throws AppException {
		try {
			contractDao.update(contract);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public void deleteContract(String[] contractIds) throws AppException {
		try {
			for(String contractId:contractIds){
				contractDao.deleteContract(contractId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputContractByPIO(File file) throws AppException {
		try{
			Users user = (Users)ServletActionContext.getRequest().getSession().getAttribute(GlobalConstants.USER_INFORMATION_KEY); 
			InputStream fis = new FileInputStream(file);
			List<Contract> contractList= ImportXSL.importContract(fis);//只是将excel表中的数据抽取出来,得继续进一步处理
			Iterator iterator = contractList.iterator();
			while(iterator.hasNext()){
				Contract contract = (Contract)iterator.next();
				String productCode = productDao.findProductCodeByName(contract.getProductName());
				String belongDepartmentId = departmentDao.findIdByName(contract.getBelongDepartment());
				String signDepartmentId = departmentDao.findIdByName(contract.getSignDepartment());
				if(productCode!=null){
					contract.setProductCode(productCode);
					contract.setProductName(contract.getProductName());
				}else{
					contract.setProductName("");
					contract.setContractInfoStatus(StaticVariable.UNFULL);
				}
				if(belongDepartmentId!=null){
					contract.setBelongDepartmentId(belongDepartmentId);
				}else{
					contract.setBelongDepartmentId("");
					contract.setBelongDepartment("");
					contract.setContractInfoStatus(StaticVariable.UNFULL);
				}
				if(signDepartmentId!=null){
					contract.setSignDepartmentId(signDepartmentId);
				}else{
					contract.setSignDepartmentId("");
					contract.setSignDepartment("");
					contract.setContractInfoStatus(StaticVariable.UNFULL);
				}
				contract.setContractId(UUIDGenerator.generateShortUuid());
				if(contract.getContractInfoStatus()==null){
					contract.setContractInfoStatus(StaticVariable.FULL);
				}
				contract.setAddDate(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
				contract.setModifyDate("");
				contract.setOperatorName(user.getUsername());
				contract.setHandStatus(StaticVariable.UNCONTRACTHAND);
				contract.setRedempStatus(StaticVariable.UNCONTRACTRED);
				contract.setNoticeStatus(StaticVariable.UNNOTICE);
				
				contractDao.insert(contract);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	@Override
	public NoticeView viewNotice(String noticeId) throws AppException {
		try{
			Notice notice = noticeDao.selectBy(noticeId);
			notice.setNoticeViewTime(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			notice.setNoticeViewStatus(StaticVariable.SCANED);
			noticeDao.update(notice);
			NoticeView noticeView = new NoticeView();
			noticeView = noticeDao.findNoticeViewById(noticeId);
			return noticeView;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000020");
		}
	}
	@Override
	public List<RedempBook> findRedempBooks(String table, int currentPage,
			int pageSize, Map<String, Object> m) throws AppException {
		try{
			return contractDao.findRedempBooks(table,currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public int getBooksTotal(String table, Map<String, Object> m)
			throws AppException {
		try{
			return contractDao.getBooksTotal(table,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public List<BranchContractView> findBranchContractByPage(String table,
			int currentPage, int pageSize, Map<String, Object> m)
			throws AppException {
		try{
			return contractDao.findBranchContractByPage(table,currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public int getBranchContractTotal(String table, Map<String, Object> m)
			throws AppException {
		try{
			return contractDao.getBranchContractTotal(table,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public Contract findContractByContractId(String contractId) throws AppException {
		try{
			return contractDao.findContractByContractId(contractId);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{contractId});
		}
	}
	@Override
	public void dealNotice(String noticeId) throws AppException {
		try{
			Notice notice = noticeDao.selectBy(noticeId);
			notice.setNoticeDealTime(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			notice.setNoticeDealStatus(StaticVariable.DEALED);
			noticeDao.update(notice);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000023");
		}
	}
	@Override
	public List<BranchContractView> findBranchProductContract(int currentPage,
			int pageSize, Map<String, Object> m) throws AppException {
		try{
			return contractDao.findBranchProductContract(currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public int getTotalBranchProductContract(Map<String, Object> m)
			throws AppException {
		try{
			return contractDao.getTotalBranchProductContract(m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	@Override
	public NoticeView reViewNotice(String noticeId) throws AppException {
		try{
			NoticeView noticeView = new NoticeView();
			noticeView = noticeDao.findNoticeViewById(noticeId);
			return noticeView;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000020");
		}
	}
	
}
