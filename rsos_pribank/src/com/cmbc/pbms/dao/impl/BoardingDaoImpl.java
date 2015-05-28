package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.pbms.bean.PbmsBoardingList;
import com.cmbc.pbms.dao.BoardingDao;


public class BoardingDaoImpl extends EasyBaseDaoImpl<PbmsBoardingList> implements BoardingDao {	
	protected Class<PbmsBoardingList> getType() {
		return PbmsBoardingList.class;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsBoardingList selectBoarding(final String seqNo) {
		
		PbmsBoardingList result = (PbmsBoardingList)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_BOARDING_LIST b ")
					.append(" where b.SEQ_NO=:seqNo ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsBoardingList.class);
				queryResult.setString("seqNo",seqNo );
                return (PbmsBoardingList) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteBoarding(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_BOARDING_LIST where SEQ_NO in ( ")
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
	@SuppressWarnings({ "unchecked"})
	public int getMaxId() {
		Object obj = getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select top 1 SEQ_NO from PBMS_BOARDING_LIST order by SEQ_NO desc");
				return session.createSQLQuery(sql.toString()).uniqueResult();
			}
		});
		if(obj == null){
			return 0;
		}else{
			return Integer.parseInt(obj.toString());
		}
	}

	
}
