package com.cmbc.funcmanage.dao.impl;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


import org.apache.log4j.Logger;
import org.hibernate.transform.Transformers;

import com.cmbc.funcmanage.bean.Tag;
import com.cmbc.funcmanage.dao.TagDao;

import rsos.framework.db.EasyBaseDaoImpl;



public class TagDaoImpl extends EasyBaseDaoImpl<Tag> implements TagDao{
	private Logger logger = Logger.getLogger(TagDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findByPagination(int currentPage, int pageSize,Map<String, Object> m){
		String hql="select * from tagInfo where 1=1 ";
		 Set<Entry<String, Object>> set = m.entrySet();
		 Iterator io = set.iterator();
		 while (io.hasNext()){
		 	Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
		 	if("printDate".equals(me.getKey()) && !"".equals(me.getValue())){
		 		hql += " and " + me.getKey() + " = '"+ me.getValue()  +"'";
		 	}
		 	if("printer".equals(me.getKey()) && !"".equals(me.getValue())){
		 		hql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'";
		 	}
		 }
		List<Tag> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(Tag.class)).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> m){
		String sql = " select count(*) from tagInfo where 1=1 ";
		
		 Set<Entry<String, Object>> set = m.entrySet();
		 Iterator io = set.iterator();
		 while (io.hasNext()) {
		 	Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
		 	if("printDate".equals(me.getKey()) && !"".equals(me.getValue())){
		 		sql += " and " + me.getKey() + " = '"+ me.getValue()  +"'" ;
		 	}
		 	if("printer".equals(me.getKey()) && !"".equals(me.getValue())){
		 		sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'";
		 	}
		 }
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(list!=null&&(!list.isEmpty())){
			String total = list.get(0).toString();
			return Integer.parseInt(total);
		}else{
			return 0;
		}
		
	}
	@SuppressWarnings("unchecked")
	public String findDevStatusByBusinessId(String sql){
		List list = getHibernateTemplate().find(sql);
		if(list.size()>0){
			String status = (String) list.get(0);
			return status;
		}else{
			return "";
		}
		
	}
	@SuppressWarnings("unchecked")
	public List findFreezeEnd(String sql) {
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean isExistence(String prefix) {

		String sql = "from Tag where prefix ="+ "'" + prefix + "'";
		List list = getHibernateTemplate().find(sql);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List find(String table,String property,String value) {

		logger.info("数据查询...");
		String queryString = "from " +table + " as t where t." + property +"=?";
		List tagList = getHibernateTemplate().find(queryString, value);
		return tagList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String findMaxEndCode(String prefix) {
		String sql = "select max(endCode) from Tag where prefix = '" + prefix + "'";
		List list = getHibernateTemplate().find(sql);
		if(list.size()>0){
			String endCode = (String) list.get(0);
			return endCode;
		}else{
			return "000000";
		}
	}
	@Override
	protected Class<Tag> getType() {

		return Tag.class;
	}
	
}
