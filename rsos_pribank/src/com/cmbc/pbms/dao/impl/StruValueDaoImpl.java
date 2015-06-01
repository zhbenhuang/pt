package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.pbms.bean.PbmsStruInfo;
import com.cmbc.pbms.bean.PbmsStruValue;
import com.cmbc.pbms.bean.PbmsStruValueId;
import com.cmbc.pbms.dao.StruValueDao;
import com.cmbc.pbms.dto.QueryStruValueDto;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;


public class StruValueDaoImpl extends EasyBaseDaoImpl<PbmsStruValue> implements StruValueDao {	
	protected Class<PbmsStruValue> getType() {
		return PbmsStruValue.class;
	}
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<PbmsStruValue> selectStruValues(final QueryStruValueDto queryDto) {
		final EasyGridList<PbmsStruValue> result = new EasyGridList<PbmsStruValue>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("STRU_ID");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from PBMS_STRU_VALUE b ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.STRU_ID", queryDto.getStruId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.TERM", queryDto.getTerm(),hasWhere);
				            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", PbmsStruValue.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<PbmsStruValue> list = (List<PbmsStruValue>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsStruValue selectStruValue(final String struId, final String term) {
		
		PbmsStruValue result = (PbmsStruValue)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_STRU_VALUE b ")
					.append(" where b.STRU_ID=:struId and b.TERM=:term");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsStruValue.class);
				queryResult.setString("struId",struId );
				queryResult.setString("term",term );
                return (PbmsStruValue) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteStruValue(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_STRU_VALUE where STRU_ID in ( ")
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
				sql.append("select top 1 STRU_ID from PBMS_STRU_VALUE order by STRU_ID desc");
				return session.createSQLQuery(sql.toString()).uniqueResult();
			}
		});
		if(obj == null){
			return 0;
		}else{
			return Integer.parseInt(obj.toString());
		}
	}

	@Override
	public void batchDeleteStruValues(List<PbmsStruValueId> ids) {
		final StringBuilder sqlBuilder = new StringBuilder();
		String idstr = "";
		for (PbmsStruValueId pbmsStruValueId : ids) {
			
		}
		sqlBuilder.append(" delete from PBMS_STRU_VALUE where STRU_ID in ( ")
			.append(idstr ).append(" ) ");
		
		this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return null;
			}
		});
	}

	@Override
	public void batchInsertStruValues(List<PbmsStruValue> list) {
		// TODO Auto-generated method stub
		
	}

	
	
}
