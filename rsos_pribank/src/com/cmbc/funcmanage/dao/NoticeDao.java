package com.cmbc.funcmanage.dao;

import com.cmbc.funcmanage.bean.Notice;
import com.cmbc.funcmanage.bean.NoticeView;

import rsos.framework.db.EasyBaseDao;

public interface NoticeDao extends EasyBaseDao<Notice> {

	NoticeView findNoticeViewById(String noticeId);

}
