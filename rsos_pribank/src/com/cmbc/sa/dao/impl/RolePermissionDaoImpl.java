package com.cmbc.sa.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.sa.bean.RolePermission;
import com.cmbc.sa.dao.RolePermissionDao;

public class RolePermissionDaoImpl extends EasyBaseDaoImpl<RolePermission> 
	implements RolePermissionDao {	
	protected Class<RolePermission> getType() {
		return RolePermission.class;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteRolePermissions(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from rolePermission where roleId+cast(business as varchar(10)) in ( ")
			.append(idstr).append(" ) ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );				
				return query.executeUpdate();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deletePermissions(final String roleId,final Integer business) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from rolePermission where roleId=:roleId and business=:business ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );
				query.setString("roleId", roleId);
				query.setInteger("business", business);
				return query.executeUpdate();
			}
		});
	}

}
