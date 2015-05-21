package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.dao.ServInfoDao;
import com.cmbc.pbms.dto.QueryServInfoDto;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;


public class ServInfoDaoImpl extends EasyBaseDaoImpl<PbmsServInfo> implements ServInfoDao {	
	protected Class<PbmsServInfo> getType() {
		return PbmsServInfo.class;
	}
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<PbmsServInfo> selectServInfos(final QueryServInfoDto queryDto) {
		final EasyGridList<PbmsServInfo> result = new EasyGridList<PbmsServInfo>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("SER_ID");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from PBMS_SERV_INFO b ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.SER_ID", queryDto.getSerId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.SER_NAME", queryDto.getSerName(),hasWhere);
				            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", PbmsServInfo.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<PbmsServInfo> list = (List<PbmsServInfo>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsServInfo selectServInfo(final String serId) {
		
		PbmsServInfo result = (PbmsServInfo)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_SERV_INFO b ")
					.append(" where b.SER_ID=:serId ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsServInfo.class);
				queryResult.setString("serId",serId );
                return (PbmsServInfo) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteServInfo(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_SERV_INFO where SER_ID in ( ")
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
				sql.append("select top 1 SER_ID from PBMS_SERV_INFO order by SER_ID desc");
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
