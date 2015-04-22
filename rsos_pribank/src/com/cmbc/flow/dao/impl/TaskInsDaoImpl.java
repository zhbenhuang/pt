package com.cmbc.flow.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyGridList;

import com.cmbc.flow.bean.QueryFlowDto;
import com.cmbc.flow.bean.TaskIns;
import com.cmbc.flow.dao.TaskInsDao;

public class TaskInsDaoImpl extends EasyBaseDaoImpl<TaskIns> implements TaskInsDao {	
	protected Class<TaskIns> getType() {
		return TaskIns.class;
	}
	@SuppressWarnings("unchecked")
	public EasyGridList<TaskIns> selectTaskView(final QueryFlowDto queryDto) {
		final EasyGridList<TaskIns> result = new EasyGridList<TaskIns>();
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				int startIndex = (queryDto.getPageDto().getPage() - 1) * queryDto.getPageDto().getPageSize();
				Boolean hasWhere = Boolean.FALSE;
				StringBuilder fromSql = new StringBuilder();
				fromSql.append(" from taskIns b ");
				
            	ArrayList<Object> params = new ArrayList<Object>();
            	hasWhere = appendLikeOrEquals(fromSql, params, "b.processInsId", queryDto.getProcessInsId(),hasWhere);
            	            	
            	StringBuilder sqlCount = new StringBuilder("select count(1) as rowCnt ").append(fromSql.toString());
				Query queryCount = session.createSQLQuery(sqlCount.toString())
										.addScalar("rowCnt", Hibernate.LONG);
				setParameters(queryCount,params);
				result.setTotal(((Long) queryCount.uniqueResult()).intValue());
				
				StringBuilder sql = new StringBuilder(" select {b.*} ").append(fromSql.toString()).append(" ORDER BY CAST(b.taskId AS DECIMAL)");	
				appendOrderSql(sql,"b",queryDto.getPageDto().getOrderColumn(),queryDto.getPageDto().getOrder());
				
                Query queryResult = session.createSQLQuery(sql.toString())
					  						.addEntity("b", TaskIns.class);
                setParameters(queryResult,params);
                queryResult.setFirstResult(startIndex);
                queryResult.setMaxResults(queryDto.getPageDto().getPageSize());
				
                
                List<TaskIns> list = queryResult.list();
                result.setRows(list);
				return null;
			}
		});
		return result;
	}
	
	
}
