package com.cmbc.funcmanage.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.dao.ProductRedemptionIntervalDao;
import com.cmbc.reportStatistic.bean.RedempIntervalReport;


public class ProductRedemptionIntervalDaoImpl extends EasyBaseDaoImpl<ProductRedemptionInterval> implements
		ProductRedemptionIntervalDao {
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(String productId) {

		String sql = "select count(*) from productRedemptionInterval where productId = '"+productId+"'";
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			String total = list.get(0).toString();
			return Integer.parseInt(total);
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductRedemptionInterval> findByPagenation(String productId,
			int currentPage, int pageSize) {
		List<ProductRedemptionInterval> redemptionIntervalList = new ArrayList<ProductRedemptionInterval>();
		String sql = "select * from productRedemptionInterval where productId = '"+productId+"'";
		sql +=" order by openDay ASC";
		redemptionIntervalList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(ProductRedemptionInterval.class).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return redemptionIntervalList;
	}

	@Override
	protected Class<ProductRedemptionInterval> getType() {

		return ProductRedemptionInterval.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteProRedemp(final String redemptionId) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from productRedemptionInterval where redemptionIntervalId=:redemptionIntervalId");
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );
				query.setString("redemptionIntervalId",redemptionId);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductRedemptionInterval> findRedempByProductId(
			String productId) {
		List<ProductRedemptionInterval> redemptionIntervalList = new ArrayList<ProductRedemptionInterval>();
		String sql = "select * from productRedemptionInterval where productId = '"+productId+"'";
		sql +=" order by openDay ASC";
		redemptionIntervalList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(ProductRedemptionInterval.class).list();
		return redemptionIntervalList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RedempIntervalReport> findRedempIntervalReportsByProductId(String productId) {
		String querySql = "select redemptionIntervalId,count(contractId) as redempBackNum from RedempIntervalReportView where productId = '"+productId+"' group by redemptionIntervalId";
		querySql = "select openDay,redeemBegin,redeemEnd,rollBenefit,redemptionStatus,redempBackNum from (productRedemptionInterval INNER JOIN ("+querySql+") AS T ON productRedemptionInterval.redemptionIntervalId=T.redemptionIntervalId)";
		querySql = querySql+" order by openDay asc";
		List listObject = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(querySql).list();
		List<RedempIntervalReport> RedempIntervalReportList = new ArrayList<RedempIntervalReport>();
		if(!listObject.isEmpty()){
			int listObjectSize = listObject.size();
			for(int i=0;i<listObjectSize;i++){
				Object[] object = (Object[])listObject.get(i);
				RedempIntervalReport redempIntervalReport = new RedempIntervalReport(
																object[0].toString(),
																object[1].toString(),
																object[2].toString(),
																object[3].toString(),
																object[4].toString(),
																object[5].toString());
				RedempIntervalReportList.add(redempIntervalReport);
			}
		}
		
		return RedempIntervalReportList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteProRedemps(final String productId) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("delete from productRedemptionInterval where productId=:productId");
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );
				query.setString("productId",productId);
				return query.executeUpdate();
			}
		});
	}
}
