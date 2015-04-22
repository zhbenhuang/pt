package com.cmbc.sa.dao;

import java.util.List;
import java.util.Map;

import rsos.framework.db.EasyBaseDao;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.dto.QueryDepartmentDto;

public interface DepartmentDao extends EasyBaseDao<Department> {
	public EasyGridList<Department> selectDepartments(QueryDepartmentDto queryDto);
	public Department selectDepartment(String departmentId,Integer business);
	public List<Department> selectChildDepartments(String departmentId,Integer departmentType);
	public void deleteDepartment(String ids);
	public List<Department> findByPage(int currentPage, int pageSize,
			Map<String, Object> m);
	public int getTotal(Map<String, Object> m);
	public String findIdByName(String departmentName);
}
