package com.cmbc.sa.service;

import java.util.List;
import java.util.Map;

import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.dto.QueryDepartmentDto;

public interface DepartmentService {
	public EasyGridList<Department> findDepartments(QueryDepartmentDto queryDto) throws AppException;
	public Department findDepartment(String departmentId) throws AppException;
	public void deleteDepartments(String idstr) throws AppException;
	public void saveDepartments(Department department) throws AppException;
	public void modifyDepartments(Department department) throws AppException;
	public List<Department> findByPage(int currentPage, int pageSize,Map<String, Object> m)throws AppException;
	public int getTotal(Map<String, Object> m)throws AppException;
}
