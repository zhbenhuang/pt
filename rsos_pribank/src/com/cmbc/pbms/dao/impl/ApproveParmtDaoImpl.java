package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.pbms.bean.PbmsApproveParmt;
import com.cmbc.pbms.dao.ApproveParmtDao;
import com.cmbc.pbms.dto.QueryApproveParmtDto;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;


public class ApproveParmtDaoImpl extends EasyBaseDaoImpl<PbmsApproveParmt> implements ApproveParmtDao {	
	protected Class<PbmsApproveParmt> getType() {
		return PbmsApproveParmt.class;
	}
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<PbmsApproveParmt> selectApproveParmts(final QueryApproveParmtDto queryDto) {
		final EasyGridList<PbmsApproveParmt> result = new EasyGridList<PbmsApproveParmt>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("APPR_TYPE");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from PBMS_APPROVE_PARMT b ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPR_TYPE", queryDto.getApprType(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPR_NAME", queryDto.getApprName(),hasWhere);
				            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", PbmsApproveParmt.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<PbmsApproveParmt> list = (List<PbmsApproveParmt>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsApproveParmt selectApproveParmt(final String apprType) {
		
		PbmsApproveParmt result = (PbmsApproveParmt)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_APPROVE_PARMT b ")
					.append(" where b.APPR_TYPE=:apprType ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsApproveParmt.class);
				queryResult.setString("apprType",apprType );
                return (PbmsApproveParmt) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteApproveParmt(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_APPROVE_PARMT where APPR_TYPE in ( ")
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
				sql.append("select top 1 APPR_TYPE from PBMS_APPROVE_PARMT order by APPR_TYPE desc");
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
