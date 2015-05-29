package com.cmbc.sa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.sa.bean.Role;
import com.cmbc.sa.bean.Users;
import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dao.RoleDao;
import com.cmbc.sa.dto.QueryRoleDto;

public class RoleDaoImpl extends EasyBaseDaoImpl<Role> implements RoleDao {	
	protected Class<Role> getType() {
		return Role.class;
	}
	
	@SuppressWarnings({ "unchecked"})
	public List<Role> selectRoles(final String userId,final Integer business) {
		List<Role> result = getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {b.*} from usersRole a,role b ")
					.append(" where a.roleId=b.roleId and b.business=:business and a.userId=:userId ");
			
				Query queryResult = session.createSQLQuery( sql.toString() )
									.addEntity("b", Role.class);
				queryResult.setString("userId",userId );
				queryResult.setInteger("business", business);
				return queryResult.list();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public List<Role> selectRoles(final Integer business) {
		List<Role> result = getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {b.*} from role b ")
					.append(" where b.business=:business ");
			
				Query queryResult = session.createSQLQuery( sql.toString() )
									.addEntity("b", Role.class);
				queryResult.setInteger("business", business);
				return queryResult.list();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public List<Role> selectRoles() {
		List<Role> result = getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {b.*} from role b ")
				.append(" where 1=1 ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
						.addEntity("b", Role.class);
				return queryResult.list();
			}
		});
		return result;
	}
	
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<Role> selectRoles(final QueryRoleDto queryDto) {
		final EasyGridList<Role> result = new EasyGridList<Role>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("roleId");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from role b ");
				hasWhere = appendBusinessPermission(fromSql, "b.business", queryDto.getBusiness(),hasWhere);
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.roleId", queryDto.getRoleId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.roleName", queryDto.getRoleName(),hasWhere);
				            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", Role.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<Role> list = (List<Role>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<Users> selectUsersByRole(final QueryRoleDto queryDto) {
		final EasyGridList<Users> result = new EasyGridList<Users>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("userId");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from userDepartmentView b join usersRole a on a.userId=b.userId ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.userId", queryDto.getUserId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.username", queryDto.getUserName(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.departmentId", queryDto.getDepartmentId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "a.roleId", queryDto.getRoleId(),hasWhere);
            	
				            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", Users.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<Users> list = (List<Users>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}

	@SuppressWarnings({ "unchecked"})
	public Role selectRole(final String roleId,final Integer business) {
		
		Role result = (Role)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from role b ")
					.append(" where b.roleId=:roleId and b.business=:business ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", Role.class);
				queryResult.setString("roleId",roleId );
				queryResult.setInteger("business", business);
                return (Role) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteRoles(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from role where roleId+cast(business as varchar(10)) in ( ")
			.append(idstr).append(" ) ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );				
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsersRole findUserRole(final String userId, final String business) {
		UsersRole result = (UsersRole)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from usersRole b ").append(" where b.userId=:userId");
				Query queryResult = session.createSQLQuery(sql.toString()).addEntity("b", UsersRole.class);
				queryResult.setString("userId",userId );
                return (UsersRole) queryResult.uniqueResult();
			}
		});
		return result;
	}
}
