package com.cmbc.funcmanage.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;


import rsos.framework.constant.StaticVariable;
import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.utils.CalendarUtil;

import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.ProductInfo;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.dao.ProductDao;
import com.cmbc.sa.bean.Users;



public class ProductDaoImpl extends EasyBaseDaoImpl<Product> implements ProductDao
{
	protected static final Log logger = LogFactory.getLog(ProductDaoImpl.class);
	
	@Override
	protected Class<Product> getType() {

		return Product.class;
	}
	
	/**
	 * 按照产品编号、产品名、赎回区间、开放日查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByPagination(String table,int currentPage,int pageSize,Map<String, Object> m){
		String sql = "select distinct productId from "+table+ " where 1=1 ";
		Set<Entry<String, Object>> set = m.entrySet();
		Iterator io = set.iterator();
		while (io.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
			if("productCode".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " = '"+ me.getValue()  +"'" ;
			}else if("productName".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
			}else if("openDayBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and openDay >= '"+ me.getValue()  +"'" ;
			}else if("openDayEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and openDay <= '"+ me.getValue()  +"'" ;
			}
		}
		if(m.containsKey("redeemBegin")&&(!"".equals(m.get("redeemBegin")))&&m.containsKey("redeemEnd")&&(!"".equals(m.get("redeemEnd")))){
			String A = (String) m.get("redeemBegin");
    		String B = (String) m.get("redeemEnd");
    		sql += " and (redeemBegin >= '" + A + "' and " + "redeemEnd <= '" + B + "')";
    		sql += " or (redeemBegin < '" + A + "' and " + "redeemEnd > '" + A + "')";
    		sql += " or (redeemBegin < '" + B + "' and " + "redeemEnd > '" + B + "')";	
		}else if(m.containsKey("redeemBegin")&&(!"".equals(m.get("redeemBegin")))){
			String A = (String) m.get("redeemBegin");
			sql += " and (redeemBegin >= '" + A + "')";
	    	sql += " or (redeemBegin <= '" + A + "' and redeemEnd >= '" + A +"')";
		}else if(m.containsKey("redeemEnd")&&(!"".equals(m.get("redeemEnd")))){
			String B = (String) m.get("redeemEnd");
			sql += " and (redeemEnd <= '" + B + "')";
	    	sql += " or (redeemEnd >= '" + B + "' and redeemBegin <= '" + B +"')";
		}
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		List<Product> productList = new ArrayList<Product>();
		if(!list.isEmpty()){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				String productId = iterator.next().toString();
				String hql = "from Product where productId='"+productId+"'";
				Product product = (Product)getHibernateTemplate().find(hql).get(0);
				productList.add(product);
			}
		}
		return productList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List findRedemptionIds(String productId) {
		String sql = "select redemptionIntervalId from productRedemptionInterval where productId = '"+productId+"'";
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		return list;
	}
	

	/**
	 * 更改与product关联的赎回区间的状态,同时将当前属于正在赎回期的赎回区间更新到product中
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateProRedem(Product product) {
		String sql = "select * from productRedemptionInterval where productId ='"+product.getProductId()+"'";
		List<ProductRedemptionInterval> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ProductRedemptionInterval.class)).list();
		if(!list.isEmpty()){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				ProductRedemptionInterval pri = (ProductRedemptionInterval)iterator.next();
				String redemptionStatus = redemptionStatus(pri);
				if(redemptionStatus.equals(StaticVariable.INREDEMPTION)){
					product.setOpenDay(pri.getOpenDay());
					product.setRedeemBegin(pri.getRedeemBegin());
					product.setRedeemEnd(pri.getRedeemEnd());
					product.setRollBenefit(pri.getRollBenefit());
					product.setRedemptionIntervalId(pri.getRedemptionIntervalId());
					getHibernateTemplate().update(product);
				}
				pri.setRedemptionStatus(redemptionStatus);
				getHibernateTemplate().update(pri);
			}
		}
	}

	/**
	 * 判断当前时间条件下,产品的赎回期是否发生变化
	 * @param pri
	 * @return
	 */
	private String redemptionStatus(ProductRedemptionInterval pri) {
		int openDay = Integer.parseInt(pri.getOpenDay());
		int redeemBegin = Integer.parseInt(pri.getRedeemBegin());
		int redeemEnd = Integer.parseInt(pri.getRedeemEnd());
		int currentDate = Integer.parseInt(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
		if(openDay<currentDate){
			return StaticVariable.PASTOPENDAY;
		}else if(currentDate<=openDay&&currentDate>redeemEnd){
			return StaticVariable.PASTREDEMPTION;
		}else if(currentDate<=redeemEnd&&currentDate>=redeemBegin){
			return StaticVariable.INREDEMPTION;
		}else{
			return StaticVariable.STEPINGREDEMPTION;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByPage(String table, int currentPage,
			int pageSize, Map<String, Object> m) {

		String sql = "select * from "+table+" where 1=1";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if(entry.getKey().equals("productName")&&(!entry.getValue().equals(""))){
				sql += " and productName like '%"+entry.getValue()+"%'";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+"= '"+entry.getValue()+"'";
			}
		}
		sql += " order by productCode ASC";
		List<Product> productList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(Product.class).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return productList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(String table, Map<String, Object> m) {

		String sql = "select distinct productId from "+table+ " where 1=1 ";
		Set<Entry<String, Object>> set = m.entrySet();
		Iterator io = set.iterator();
		while (io.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
			if("productCode".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " = '"+ me.getValue()  +"'" ;
			}
			if("productName".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
			}
			if("openDayBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and openDay >= '"+ me.getValue()  +"'" ;
			}
			if("openDayEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and openDay <= '"+ me.getValue()  +"'" ;
			}
		}
		if(m.containsKey("redeemBegin")&&(!"".equals(m.get("redeemBegin")))&&m.containsKey("redeemEnd")&&(!"".equals(m.get("redeemEnd")))){
			String A = (String) m.get("redeemBegin");
    		String B = (String) m.get("redeemEnd");
    		sql += " and (redeemBegin >= '" + A + "' and " + "redeemEnd <= '" + B + "')";
    		sql += " or (redeemBegin < '" + A + "' and " + "redeemEnd > '" + A + "')";
    		sql += " or (redeemBegin < '" + B + "' and " + "redeemEnd > '" + B + "')";	
		}else if(m.containsKey("redeemBegin")&&(!"".equals(m.get("redeemBegin")))){
			String A = (String) m.get("redeemBegin");
			sql += " and (redeemBegin >= '" + A + "')";
	    	sql += " or (redeemBegin <= '" + A + "' and redeemEnd >= '" + A +"')";
		}else if(m.containsKey("redeemEnd")&&(!"".equals(m.get("redeemEnd")))){
			String B = (String) m.get("redeemEnd");
			sql += " and (redeemEnd <= '" + B + "')";
	    	sql += " or (redeemEnd >= '" + B + "' and redeemBegin <= '" + B +"')";
		}
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		@SuppressWarnings("unused")
		List<Product> productList = new ArrayList<Product>();
		if(!list.isEmpty()){
			return list.size();
		}else{
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> m) {
		String sql = "select count(*) from financialProduct where 1=1";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if(entry.getKey().equals("productName")&&(!entry.getValue().equals(""))){
				sql += " and productName like '%"+entry.getValue()+"%'";
			}
			if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+"= '"+entry.getValue()+"'";
			}
		}
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteProduct(final String productId) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from financialProduct where productId=:productId");
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );
				query.setString("productId",productId);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findProductIdByName(final String productName) {
		String result = (String)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select b.productId from financialProduct b ")
					.append(" where b.productName=:productName ");
				Query queryResult = session.createSQLQuery( sql.toString());
				queryResult.setString("productName",productName );
				return queryResult.uniqueResult();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findProductIdByCode(final String productCode) {
		String result = (String)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select b.productId from financialProduct b ")
					.append(" where b.productCode=:productCode ");
				Query queryResult = session.createSQLQuery( sql.toString());
				queryResult.setString("productCode",productCode );
				return queryResult.uniqueResult();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAllRollProduct() {
		String querySql = "from Product where isRoll='是'";
		return getHibernateTemplate().find(querySql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findProductCodeByName(final String productName) {
		String result = (String)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select b.productCode from financialProduct b ")
					.append(" where b.productName=:productName ");
				Query queryResult = session.createSQLQuery( sql.toString());
				queryResult.setString("productName",productName );
				return queryResult.uniqueResult();
			}
		});
		return result;
	}

	@Override
	public void updateContractRedemptionIntervalId(String productId,ProductRedemptionInterval productRedemptionInterval) {
		String querySql = "select contractId from contractRedempUpdateView where productId='"+productId+"' " +
																			"and openDay='"+productRedemptionInterval.getOpenDay()+"' " +
																			"and redeemBegin='"+productRedemptionInterval.getRedeemBegin()+"' " +
																			"and redeemEnd='"+productRedemptionInterval.getRedeemEnd()+"'";
		String updateSql = "update contract set redemptionIntervalId = '"+productRedemptionInterval.getRedemptionIntervalId()+"' where contractId in("+querySql+")";
		getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(updateSql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> findAllProductInfo() {
		String queryProductInfos = "select * from productInfo where created='no'";
		List<ProductInfo> productInfoList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(queryProductInfos).addEntity(ProductInfo.class).list();
		return productInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isNotCreate(String productCode) {
		String queryProduct = "select * from financialProduct where productCode='"+productCode+"'";
		List<Product> productList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(queryProduct).addEntity(Product.class).list();
		if(productList==null||productList.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void updateProductInfoCreated(String productCode) {
		String updateProductInfo = "update productInfo set created='yes' where productCode='"+productCode+"'";
		getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(updateProductInfo).executeUpdate();
	}
	
	

}
