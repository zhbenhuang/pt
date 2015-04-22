package com.cmbc.funcmanage.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.funcmanage.bean.RedemptionInfo;
import com.cmbc.funcmanage.dao.RedempDao;

import rsos.framework.db.EasyBaseDaoImpl;

public class RedempDaoImpl extends EasyBaseDaoImpl<RedemptionInfo> implements RedempDao {

	@Override
	protected Class<RedemptionInfo> getType() {
		return RedemptionInfo.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RedemptionInfo findRedempInfoByCode(final String codeId) {
		RedemptionInfo result = (RedemptionInfo)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from redemptionInfo b ")
					.append(" where b.codeId=:codeId ");
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", RedemptionInfo.class);
				queryResult.setString("codeId",codeId );
                return (RedemptionInfo) queryResult.uniqueResult();
			}
		});
		return result;
	}

}
