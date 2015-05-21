package com.cmbc.sa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.pbms.bean.PbmsStruInfo;
import com.cmbc.sa.bean.Department;
import com.cmbc.sa.dao.DepartmentDao;
import com.cmbc.sa.dto.QueryDepartmentDto;

public class DepartmentDaoImpl extends EasyBaseDaoImpl<Department> implements DepartmentDao {	
	protected Class<Department> getType() {
		return Department.class;
	}

	@SuppressWarnings("unchecked")
	public EasyGridList<Department> selectDepartments(final QueryDepartmentDto queryDto) {
		final EasyGridList<Department> result = new EasyGridList<Department>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("departmentId");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from departmentView b ");
				if(queryDto.getType()!=null&&queryDto.getType().length()>0){
            		fromSql.append("where b.type!='"+queryDto.getType()+"'");
            		hasWhere = Boolean.TRUE;
            	}
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.departmentId", queryDto.getDepartmentId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.departmentName", queryDto.getDepartmentName(),hasWhere);     	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", Department.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<Department> list = (List<Department>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteDepartment(String ids) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from department where departmentId in ( ")
			.append(ids).append(" )");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );				
				return query.executeUpdate();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public Department selectDepartment(final String departmentId,final Integer business) {
		Department result = (Department)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from department b ")
					.append(" where b.departmentId=:departmentId and b.departmentType=:business ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", Department.class);
				queryResult.setString("departmentId",departmentId );
				queryResult.setInteger("business", business);
                return (Department) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> selectChildDepartments(final String departmentId,final Integer departmentType) {
		final List<Department> result = new ArrayList<Department>();
		
		getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("with departmentCTE(departmentId,departmentName,parentId) ")
					.append(" as ( Select departmentId,departmentName,parentId From department Where parentId=:departmentId ")
					.append("      Union All ")
					.append("      Select department.departmentId, department.departmentName,department.parentId From departmentCTE ")
					.append("      Inner Join department On departmentCTE.departmentId = department.parentId ")
					.append(" ) select departmentId,departmentName,parentId from departmentCTE ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addScalar("departmentId",Hibernate.STRING)
										.addScalar("departmentName",Hibernate.STRING)
										.addScalar("parentId",Hibernate.STRING);
				queryResult.setString("departmentId",departmentId );
				List<Object[]> list = queryResult.list();                
				if (list != null) {
					for(Object[] obj : list){
						Department d = new Department();
						d.setDepartmentId((String)obj[0]);
						d.setDepartmentName((String)obj[1]);
						d.setParentId((String)obj[2]);
						result.add(d);
					}                
				}
				return null;
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findByPage(int currentPage, int pageSize,
		Map<String, Object> m) {
		String sql = "select * from department where 1=1" ;
		Set<Entry<String, Object>> set = m.entrySet();
		Iterator io = set.iterator();
		while (io.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
			if("departmentId".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + "='"+ me.getValue()+"'" ;
			}else if("departmentName".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'";
			}else if(!me.getValue().equals("")){
				sql += " and "+me.getKey()+" = '"+me.getValue()+"'";
			}
		}
		List<Department> dlist = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(Department.class).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return dlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> m) {
		String sql = " select count(*) from department  where 1=1 ";
		Set<Entry<String, Object>> set = m.entrySet();
		Iterator io = set.iterator();
		while (io.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
			if("departmentId".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " = '"+ me.getValue()  +"'" ;
			}else if("departmentName".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'";
			}else if(!me.getValue().equals("")){
				sql += " and "+me.getKey()+" = '"+me.getValue()+"'";
			}
		}
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			String total = list.get(0).toString();
			return Integer.parseInt(total);
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findIdByName(final String departmentName) {
		String result = (String)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select b.departmentId from department b ")
					.append(" where b.departmentName=:departmentName or b.anoDepartmentName=:anoDepartmentName");
				Query queryResult = session.createSQLQuery( sql.toString());
				queryResult.setString("departmentName",departmentName );
				queryResult.setString("anoDepartmentName",departmentName );
                return queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDepartments() {
		 return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select * from department b");
				List<Department> list0 = (List<Department>)session.createSQLQuery(sql.toString()).addEntity("b", Department.class).list();
				return list0;
			}
		});
	}
}
