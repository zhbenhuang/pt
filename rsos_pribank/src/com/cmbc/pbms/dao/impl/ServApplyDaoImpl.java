package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.pbms.bean.PbmsServApply;
import com.cmbc.pbms.dao.ServApplyDao;
import com.cmbc.pbms.dto.QueryServApplyDto;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;


public class ServApplyDaoImpl extends EasyBaseDaoImpl<PbmsServApply> implements ServApplyDao {	
	protected Class<PbmsServApply> getType() {
		return PbmsServApply.class;
	}
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<PbmsServApply> selectServApplys(final QueryServApplyDto queryDto) {
		final EasyGridList<PbmsServApply> result = new EasyGridList<PbmsServApply>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("SER_NO");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from PBMS_SERV_APPLY b ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.SER_NO", queryDto.getSerNo(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.SER_ID", queryDto.getSerId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPLY_ID", queryDto.getApprId(),hasWhere);
				            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", PbmsServApply.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<PbmsServApply> list = (List<PbmsServApply>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsServApply selectServApply(final String serNo) {
		
		PbmsServApply result = (PbmsServApply)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_SERV_APPLY b ")
					.append(" where b.SER_NO=:serNo ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsServApply.class);
				queryResult.setString("serNo",serNo );
                return (PbmsServApply) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteServApply(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_SERV_APPLY where SER_NO in ( ")
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
				sql.append("select top 1 SER_NO from PBMS_SERV_APPLY order by SER_NO desc");
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
