package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.pbms.bean.PbmsPhysicalExam;
import com.cmbc.pbms.dao.PhysicalExamDao;

import rsos.framework.db.EasyBaseDaoImpl;


public class PhysicalExamDaoImpl extends EasyBaseDaoImpl<PbmsPhysicalExam> implements PhysicalExamDao {	
	protected Class<PbmsPhysicalExam> getType() {
		return PbmsPhysicalExam.class;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsPhysicalExam selectPhysicalExam(final String seqNo) {
		
		PbmsPhysicalExam result = (PbmsPhysicalExam)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_PHYSICAL_EXAM b ")
					.append(" where b.SEQ_NO=:seqNo ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsPhysicalExam.class);
				queryResult.setString("seqNo",seqNo );
                return (PbmsPhysicalExam) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deletePhysicalExam(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_PHYSICAL_EXAM where SEQ_NO in ( ")
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
				sql.append("select top 1 SEQ_NO from PBMS_PHYSICAL_EXAM order by SEQ_NO desc");
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
