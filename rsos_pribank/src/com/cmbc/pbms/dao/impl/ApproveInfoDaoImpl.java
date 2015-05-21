package com.cmbc.pbms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.dao.ApproveInfoDao;
import com.cmbc.pbms.dto.QueryApproveInfoDto;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;


public class ApproveInfoDaoImpl extends EasyBaseDaoImpl<PbmsApproveInfo> implements ApproveInfoDao {	
	protected Class<PbmsApproveInfo> getType() {
		return PbmsApproveInfo.class;
	}
	
	@SuppressWarnings({ "unchecked"})
	public EasyGridList<PbmsApproveInfo> selectApproveInfos(final QueryApproveInfoDto queryDto) {
		final EasyGridList<PbmsApproveInfo> result = new EasyGridList<PbmsApproveInfo>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (queryDto.getPageDto().getOrderColumn() == null)
					queryDto.getPageDto().setOrderColumn("APPR_ID");
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append("from PBMS_APPROVE_INFO b ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPR_ID", queryDto.getApprId(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPR_TYPE", queryDto.getApprType(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPR_STATUS", queryDto.getApprStatus(),hasWhere);
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.APPLY_USER_ID", queryDto.getApplyUserId(),hasWhere);
				//TODO 日期
            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder("select {b.*} ").append(fromSql.toString());	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("b", PbmsApproveInfo.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                List<PbmsApproveInfo> list = (List<PbmsApproveInfo>) queryResult.list();
				result.setRows(list);
            	
				return null;
			}
		});
		return result;
	}
	

	@SuppressWarnings({ "unchecked"})
	public PbmsApproveInfo selectApproveInfo(final String apprType) {
		
		PbmsApproveInfo result = (PbmsApproveInfo)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from PBMS_APPROVE_INFO b ")
					.append(" where b.APPR_ID=:apprId ");
				
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", PbmsApproveInfo.class);
				queryResult.setString("apprId",apprType );
                return (PbmsApproveInfo) queryResult.uniqueResult();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void deleteApproveInfo(final String idstr) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from PBMS_APPROVE_INFO where APPR_ID in ( ")
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
				sql.append("select top 1 APPR_ID from PBMS_APPROVE_INFO order by APPR_ID desc");
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
