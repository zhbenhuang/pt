package com.cmbc.attach.dao.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;

import rsos.framework.db.EasyBaseDaoImpl;
import com.cmbc.attach.dao.AttachInfoDao;
import com.cmbc.attach.bean.AttachInfo;


public class AttachInfoDaoImpl extends EasyBaseDaoImpl<AttachInfo> implements AttachInfoDao{

	protected static final Log logger = LogFactory.getLog(AttachInfoDaoImpl.class);
	
	@Override
	public List<AttachInfo> getAllAttach(String businessId){
		String hql = "select * from attachInfo where businessId=" + "'" + businessId + "'";
		@SuppressWarnings("unchecked")
		List<AttachInfo> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AttachInfo.class)).list();
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String findAttachNameByBusinessId(String attachId){
		String sql = "select attachName from AttachInfo where attachId=" + "'" + attachId + "'"  ;
		List list = getHibernateTemplate().find(sql);
		if(list.size()>0){
			String attachName = (String) list.get(0);
			return attachName;
		}else{
			return "";
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAttachInfoByAttachId(String attachId){
		String sql = "from AttachInfo where attachId=" + "'" + attachId + "'"  ;
		//List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		List list = getHibernateTemplate().find(sql);
		return list;
	}
	
	@Override
	public void deleteObject(Object object) {		
		logger.info("删除持久化数据...");
		try {
			getHibernateTemplate().delete(object);
		}catch(DataAccessException e){
			logger.info("数据持久化异常,检查数据库连接是否成功!");			
		}
	}
	
	@Override
	public boolean save(Object object) {
		// TODO Auto-generated method stub
		logger.info("保存数据持久化...");
		try {
			getHibernateTemplate().save(object);
		} catch(DataAccessException e){
			logger.info("数据持久化异常,检查数据库连接是否成功!");
			return false;
		}
		return true;
	}

	@Override
	protected Class<AttachInfo> getType() {
		return AttachInfo.class;
	}
	
}

