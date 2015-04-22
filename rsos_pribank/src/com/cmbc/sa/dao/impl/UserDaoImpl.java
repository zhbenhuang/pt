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

import com.cmbc.sa.bean.Users;
import com.cmbc.sa.dao.UserDao;
import com.cmbc.sa.dto.QueryUserDto;

public class UserDaoImpl extends EasyBaseDaoImpl<Users> implements UserDao {	
	protected Class<Users> getType() {
		return Users.class;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EasyGridList<Users> selectUsers(final QueryUserDto queryDto) {
		final EasyGridList<Users> result = new EasyGridList<Users>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("userId");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from userDepartmentView b ");
				hasWhere = appendBusinessPermission(fromSql, "b.business", queryDto.getBusiness(),hasWhere);
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.userId", queryDto.getUserId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.username", queryDto.getUserName(),hasWhere);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Users selectUser(final String loginId,final Integer business) {
		
		Users result = (Users)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				System.out.println(loginId);
				System.out.println(business);
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from users b ")
					.append(" where b.userid=:userId and b.business=:business ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", Users.class);
				queryResult.setString("userId",loginId );
				queryResult.setInteger("business", business.intValue());
                return (Users) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteUsers(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from users where userId+cast(business as varchar(10)) in ( ")
			.append(idstr).append(" ) ");
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );				
				return query.executeUpdate();
			}
		});
	}

	@Override
	public void alterPassword(String userId, String password) {
		String sql = "update users set password = '"+password+"' where userId = '"+userId+"'";
		Session session = getSessionFactory().openSession();
		session.createSQLQuery(sql).executeUpdate();
	}
}
