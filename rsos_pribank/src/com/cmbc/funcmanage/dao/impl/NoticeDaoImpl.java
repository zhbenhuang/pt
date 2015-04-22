package com.cmbc.funcmanage.dao.impl;

import java.util.List;
import org.hibernate.Hibernate;

import rsos.framework.db.EasyBaseDaoImpl;

import com.cmbc.funcmanage.bean.Notice;
import com.cmbc.funcmanage.bean.NoticeView;
import com.cmbc.funcmanage.dao.NoticeDao;

public class NoticeDaoImpl extends EasyBaseDaoImpl<Notice> implements NoticeDao {

	@Override
	protected Class<Notice> getType() {
		return Notice.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NoticeView findNoticeViewById(String noticeId) {
		String sql = "select * from noticeView where noticeId = '"+noticeId+"'";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql)
																							.addScalar("noticeId", Hibernate.STRING)
																							.addScalar("noticeType", Hibernate.STRING)
																							.addScalar("noticeTitle", Hibernate.STRING)
																							.addScalar("noticeArriveTime", Hibernate.STRING)
																							.addScalar("noticeViewTime", Hibernate.STRING)
																							.addScalar("noticeViewStatus", Hibernate.STRING)
																							.addScalar("departmentName", Hibernate.STRING)
																							.addScalar("noticeDealStatus", Hibernate.STRING)
																							.addScalar("noticeDealTime", Hibernate.STRING)
																							.list();
		NoticeView noticeView = new NoticeView();
		if(!objectList.isEmpty()){
			Object[] object = (Object[])objectList.get(0);
			noticeView.setNoticeId(object[0].toString());
			noticeView.setNoticeType(object[1].toString());
			noticeView.setNoticeTitle(object[2].toString());
			noticeView.setNoticeArriveTime(object[3].toString());
			if(object[4]!=null){
				noticeView.setNoticeViewTime(object[4].toString());
			}else{
				noticeView.setNoticeViewTime(object[5].toString());
			}
			noticeView.setNoticeViewStatus(object[5].toString());
			noticeView.setBelongDepartment(object[6].toString());
			noticeView.setNoticeDealStatus(object[7].toString());
			if(object[8]!=null){
				noticeView.setNoticeDealTime(object[8].toString());
			}else{
				noticeView.setNoticeDealStatus(object[7].toString());
			}
		}
		return noticeView;
	}
	
	

}
