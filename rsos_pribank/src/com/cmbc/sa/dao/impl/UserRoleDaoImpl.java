package com.cmbc.sa.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.sa.bean.UsersRole;
import com.cmbc.sa.dao.UserRoleDao;

public class UserRoleDaoImpl extends EasyBaseDaoImpl<UsersRole> implements UserRoleDao {	
	protected Class<UsersRole> getType() {
		return UsersRole.class;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteUserRoles(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from usersRole where userId+cast(business as varchar(10)) in ( ")
			.append(idstr).append(" ) ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );				
				return query.executeUpdate();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteUserRoles(final String userId,final Integer business) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from usersRole where userId=:userId and business=:business ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );
				query.setString("userId", userId);
				query.setInteger("business", business);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteUserRolesByRoleId(String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from usersRole where roleId+cast(business as varchar(10)) in ( ")
			.append(idstr).append(" ) ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );				
				return query.executeUpdate();
			}
		});
	}

}
