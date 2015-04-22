package com.cmbc.sa.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.dao.DepartmentDao;
import com.cmbc.sa.dto.QueryDepartmentDto;
import com.cmbc.sa.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService{
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	
	private DepartmentDao departmentDao;
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public EasyGridList<Department> findDepartments(QueryDepartmentDto queryDto)
			throws AppException {
		try {
			return departmentDao.selectDepartments(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	public Department findDepartment(String departmentId)
			throws AppException {
		try {
			log.info("---findDepartment by key "+departmentId);
			return departmentDao.selectBy(departmentId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000008",new String[]{departmentId});
		}
	}
	
	public void deleteDepartments(String idstr)
			throws AppException {
		try {
			departmentDao.deleteDepartment(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveDepartments(Department department)
			throws AppException {
		try {
			departmentDao.insert(department);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyDepartments(Department department)
			throws AppException {
		try {
			departmentDao.update(department);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public List<Department> findByPage(int currentPage, int pageSize,
			Map<String, Object> m) throws AppException {
		try {
			return departmentDao.findByPage(currentPage,pageSize,m);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}

	@Override
	public int getTotal(Map<String, Object> m) throws AppException {
		try {
			return departmentDao.getTotal(m);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
}
