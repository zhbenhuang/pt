package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.pbms.bean.PbmsHospitalReg;
import com.cmbc.pbms.dao.HospitalRegDao;


public class HospitalRegDaoImpl extends EasyBaseDaoImpl<PbmsHospitalReg> implements HospitalRegDao {	
	protected Class<PbmsHospitalReg> getType() {
		return PbmsHospitalReg.class;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsHospitalReg selectHospitalReg(final String seqNo) {
		
		PbmsHospitalReg result = (PbmsHospitalReg)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_HOSPITAL_REG b ")
					.append(" where b.SEQ_NO=:seqNo ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsHospitalReg.class);
				queryResult.setString("seqNo",seqNo );
                return (PbmsHospitalReg) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteHospitalReg(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_HOSPITAL_REG where SEQ_NO in ( ")
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
				sql.append("select top 1 SEQ_NO from PBMS_HOSPITAL_REG order by SEQ_NO desc");
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
