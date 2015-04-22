package com.cmbc.flow.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.flow.bean.ProcessIns;
import com.cmbc.flow.dao.ProcessInsDao;

public class ProcessInsDaoImpl extends EasyBaseDaoImpl<ProcessIns> implements ProcessInsDao {	
	protected Class<ProcessIns> getType() {
		return ProcessIns.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessIns findProcessInsByCode(final String codeId) {
		ProcessIns result = (ProcessIns)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from processIns b ")
					.append(" where b.codeId=:codeId");
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", ProcessIns.class);
				queryResult.setString("codeId",codeId );
                return (ProcessIns) queryResult.uniqueResult();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessIns findProcessInsByCode(final String codeId, final String processTypeName) {
		ProcessIns result = (ProcessIns)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from processIns b ")
					.append(" where b.codeId=:codeId and b.processTypeName=:processTypeName");
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", ProcessIns.class);
				queryResult.setString("codeId",codeId );
				queryResult.setString("processTypeName",processTypeName );
                return (ProcessIns) queryResult.uniqueResult();
			}
		});
		return result;
	}
}
