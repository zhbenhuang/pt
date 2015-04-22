package com.cmbc.sa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyObject;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;

import com.cmbc.sa.bean.Department;
import com.cmbc.sa.dto.QueryDepartmentDto;
import com.cmbc.sa.service.DepartmentService;


public class DepartmentAction extends BaseAction {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private static final long serialVersionUID = 1L;
	private DepartmentService departmentService;
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void queryDepartmentList(){
		try {
			printParameters();
			String departmentId = getHttpRequest().getParameter("departmentId");
			String departmentName = getHttpRequest().getParameter("departmentName");
			QueryDepartmentDto queryDto = new QueryDepartmentDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setDepartmentId(departmentId);
			queryDto.setDepartmentName(departmentName);
			EasyGridList<Department> list = departmentService.findDepartments(queryDto);
			list.setRetCode(Constants.RETCODE_00000);
			list.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+list.toJson());
			writeJsonSuccess(list.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void queryBranchList(){
		try {
			printParameters();
			String departmentId = getHttpRequest().getParameter("departmentId");
			String departmentName = getHttpRequest().getParameter("departmentName");
			String type = getHttpRequest().getParameter("type");
			
			QueryDepartmentDto queryDto = new QueryDepartmentDto();
			queryDto.setPageDto(initPageParameters());
			queryDto.setDepartmentId(departmentId);
			queryDto.setDepartmentName(departmentName);
			queryDto.setType(type);
			
			EasyGridList<Department> list = departmentService.findDepartments(queryDto);
			list.setRetCode(Constants.RETCODE_00000);
			list.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+list.toJson());
			writeJsonSuccess(list.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void findDepartment(){
		try {
			printParameters();
			String departmentId = getHttpRequest().getParameter("departmentId");
			Department d = departmentService.findDepartment(departmentId);
			EasyObject<Department> result = new EasyObject<Department>(Constants.RETCODE_00000, getText(Constants.RETCODE_00000), d);
			System.out.println(getText(Constants.RETCODE_00000)+"---"+result.toJson());
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
		
	public void deleteDepartment(){
		try {
			printParameters();
			String ids = getHttpRequest().getParameter("ids");
			log.info("---ids is: "+ids);
			departmentService.deleteDepartments(ids);
			EasyResult result = new EasyResult(Constants.RETCODE_00000,
						getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+result.toJson());
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
		
	public void modifyDepartment(){
		try {
			printParameters();
			String departmentId = getHttpRequest().getParameter("departmentId");
			Department oldD = departmentService.findDepartment(departmentId);
			String departmentName = getHttpRequest().getParameter("departmentName");
			String anoDepartmentId = getHttpRequest().getParameter("anoDepartmentId");
			String anoDepartmentName = getHttpRequest().getParameter("anoDepartmentName");
			String type = getHttpRequest().getParameter("type");
			String remark = getHttpRequest().getParameter("remark");
			String parentId = getHttpRequest().getParameter("parentId");
			
			oldD.setDepartmentName(departmentName);
			oldD.setAnoDepartmentId(anoDepartmentId);
			oldD.setAnoDepartmentName(anoDepartmentName);
			oldD.setType(type);
			oldD.setRemark(remark);
			oldD.setParentId(parentId);
			
			departmentService.modifyDepartments(oldD);
			EasyResult result = new EasyResult(Constants.RETCODE_00000,
						getText(Constants.RETCODE_00000));
			System.out.println(getText(Constants.RETCODE_00000)+"---"+result.toJson());
			writeJsonSuccess(result.toJson());
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void saveDepartment(){
		try {
			printParameters();
			String departmentId = getHttpRequest().getParameter("departmentId");
			Department oldD = departmentService.findDepartment(departmentId);
			if(oldD!=null){
				EasyResult result = new EasyResult(Constants.RETCODE_000022,getText(Constants.RETCODE_000022));
				writeJsonSuccess(result.toJson());
			}else{
				String departmentName = getHttpRequest().getParameter("departmentName");
				String anoDepartmentId = getHttpRequest().getParameter("anoDepartmentId");
				String anoDepartmentName = getHttpRequest().getParameter("anoDepartmentName");
				String type = getHttpRequest().getParameter("type");
				String remark = getHttpRequest().getParameter("remark");
				String parentId = getHttpRequest().getParameter("parentId");
				
				Department d = new Department();
				d.setDepartmentId(departmentId);
				d.setDepartmentName(departmentName);
				d.setAnoDepartmentId(anoDepartmentId);
				d.setAnoDepartmentName(anoDepartmentName);
				d.setType(type);
				d.setRemark(remark);
				d.setParentId(parentId);
				
				departmentService.saveDepartments(d);
				EasyResult result = new EasyResult(Constants.RETCODE_00000,
							getText(Constants.RETCODE_00000));
				System.out.println(getText(Constants.RETCODE_00000)+"---"+result.toJson());
				writeJsonSuccess(result.toJson());
			}
		}catch(AppException e){
			writeErrors(e.getId());
			e.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择归属机构和签约机构
	 */
	public void getDepartList(){
		try{
			printParameters();
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			String types = ServletActionContext.getRequest().getParameter("type");
			String departmentId = ServletActionContext.getRequest().getParameter("departmentId");
			String departmentName = ServletActionContext.getRequest().getParameter("departmentName");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> m = new HashMap<String,Object>();
			if(departmentId!=null&&departmentId.length()>0){
				m.put("departmentId", departmentId);
			}
			if(departmentName!=null&&departmentName.length()>0){
				m.put("departmentName", departmentName);
			}
			if(types!=null&&types.length()>0){
				int type = Integer.parseInt(types);
				m.put("type",type);
			}
			List<Department> departmentList = this.departmentService.findByPage(currentPage , pageSize , m );
			int total = this.departmentService.getTotal(m);
			
			EasyGridList<Department> dlist = new EasyGridList<Department>();
			dlist.setRows(departmentList);
			dlist.setTotal(total);
			dlist.setRetCode(Constants.RETCODE_00000);
			dlist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+dlist.toJson());
			writeJsonSuccess(dlist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
		
}
