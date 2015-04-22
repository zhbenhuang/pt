package com.cmbc.funcmanage.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import rsos.framework.constant.StaticVariable;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;


import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.Notice;
import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.dao.TimerTaskDao;

public class TimerTaskDaoImpl extends HibernateDaoSupport implements TimerTaskDao {
	private Logger logger = Logger.getLogger(TimerTaskDaoImpl.class);
	private static boolean RUN = false;
	/**
	 * 每天凌晨00:30执行定时任务,生成逾期警告通知
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void contractAlarmTask() throws Exception {
		if(RUN) {
			logger.info("Contract Alarm Notice task has started!");
			return;		//避免多个任务同时启动
		}
		Date date = new Date();
		logger.info("Contract Alarm Notice task start!"+date.toString());
		RUN = true;
		try{
			String signDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), -6), CalendarUtil.DATEFORMAT_YYYYMMDD);
			String sql = "select * from contractView where contractInfoStatus='"+StaticVariable.FULL+"' and handStatus='"+StaticVariable.UNCONTRACTHAND+"'"+" and noticeStatus ='"+StaticVariable.UNNOTICE+"'"+" and saleDate <='"+signDate+"'";
			List<Contract> contractList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(Contract.class)).list();
			if(!contractList.isEmpty()){
				generatorAlarmNotice(contractList);
			}
			RUN = false;
			logger.info("Contract Alarm Notice task end!");
			return;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			RUN = false;
		}
		
	}
	@SuppressWarnings("unchecked")
	private void generatorAlarmNotice(List<Contract> contractList) {
		List<Notice> noticeList = new ArrayList<Notice>();
		Iterator iterator = contractList.iterator();
		while(iterator.hasNext()){								//针对每一个合同都生成一个通知,所以这里要进行循环操作
			Contract contract = (Contract)iterator.next();
			String noticeTitle = generatorTitle(contract); 
			noticeList = generatorNotice(contract,noticeTitle,StaticVariable.CONTRACTALARM,noticeList);
			contract.setNoticeStatus(StaticVariable.NOTICE);	//标记合同预警通知已发出
			getHibernateTemplate().merge(contract);
		}
		getHibernateTemplate().saveOrUpdateAll(noticeList);
	}
	private List<Notice> generatorNotice(Contract contract,String noticeTitle,String noticeType,List<Notice> noticeList) {
		if(contract.getBelongDepartmentId()!=null&&contract.getBelongDepartmentId().length()>0&&contract.getSignDepartmentId()!=null&&contract.getSignDepartmentId().length()>0){
			if(!contract.getBelongDepartmentId().equals(contract.getSignDepartmentId())){
				Notice belongDepartmentNotice = initNotice(noticeType,noticeTitle,contract.getContractId(),contract.getBelongDepartmentId());
				Notice signDepartmentNotice = initNotice(noticeType,noticeTitle,contract.getContractId(),contract.getSignDepartmentId());
				noticeList.add(belongDepartmentNotice);
				noticeList.add(signDepartmentNotice);
			}else{
				Notice belongDepartmentNotice = initNotice(noticeType,noticeTitle,contract.getContractId(),contract.getBelongDepartmentId());
				noticeList.add(belongDepartmentNotice);
			}
		}else if(contract.getBelongDepartmentId()!=null&&contract.getBelongDepartmentId().length()>0){
			Notice belongDepartmentNotice = initNotice(noticeType,noticeTitle,contract.getContractId(),contract.getBelongDepartmentId());
			noticeList.add(belongDepartmentNotice);
		}else if(contract.getSignDepartmentId()!=null&&contract.getSignDepartmentId().length()>0){
			Notice signDepartmentNotice = initNotice(noticeType,noticeTitle,contract.getContractId(),contract.getSignDepartmentId());
			noticeList.add(signDepartmentNotice);
		}
		return noticeList;
	}
	private String generatorTitle(Contract contract){
		String title = "您的客户"+contract.getCustomeName()+"("+contract.getCustomeId()+")办理的理财产品:"+contract.getProductName()+"("+contract.getProductCode()+")合同(合同号:"+contract.getSignAccount()+")即将逾期,请及时移交合同!";
		return title;
	}
	
	
	/**
	 * 每月最后一天的23:30执行，清除前3个月生成的合同逾期警告通知
	 * @throws Exception 
	 */
	@Override
	public void cleanContractAlarmTask() throws Exception {
		if(RUN) {
			logger.info("Clean Contract Alarm Notice task has started!");
			return;		//避免多个任务同时启动
		}
		Date date0 = new Date();
		logger.info("Clean Contract Alarm Notice task start!"+date0.toString());
		RUN = true;
		try{
			Calendar curr = Calendar.getInstance();
			curr.set(Calendar.MONTH,curr.get(Calendar.MONTH)-3);
			Date currentDate=curr.getTime();
			String date = CalendarUtil.formatDatetime(currentDate, CalendarUtil.DATEFORMAT_YYYYMMDD);
			String sql = "delete from notice where noticeDealStatus='"+StaticVariable.DEALED+"'"+" and convert(varchar(8),noticeArriveTime,112) <='"+date+"'";
			Query query = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql);
			query.executeUpdate();
			RUN = false;
			logger.info("Clean Contract Alarm Notice task end!");
			return;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			RUN = false;
		}
		
	}
	
	
	/**
	 * 每日01:10执行定时任务，生成客户已赎回的通知
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void contractRedempTask() throws Exception {
		if(RUN) {
			logger.info("Contract Redemp Notice task has started!");
			return;		//避免多个任务同时启动
		}
		Date date = new Date();
		logger.info("Contract Redemp Notice task start!"+date.toString());
		RUN = true;
		try{
			String currentDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), 1), CalendarUtil.DATEFORMAT_YYYYMMDD);
			String sql = "select * from contractRedemption_View where redempStatus='"+StaticVariable.CONTRACTRED+"'"+" and openDay ='"+currentDate+"'";
			List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql)
																				 .addScalar("contractId", Hibernate.STRING)
																				 .addScalar("customeName", Hibernate.STRING)
																				 .addScalar("customeId", Hibernate.STRING)
																				 .addScalar("belongDepartmentId", Hibernate.STRING)
																				 .addScalar("signDepartmentId", Hibernate.STRING)
																				 .addScalar("productName", Hibernate.STRING)
																				 .addScalar("productCode", Hibernate.STRING)
																				 .addScalar("openDay", Hibernate.STRING)
																				 .addScalar("redeemBegin", Hibernate.STRING)
																				 .addScalar("redeemEnd", Hibernate.STRING)
																				 .list();
			
			if(!objectList.isEmpty()){
				generatorRedempNotice(objectList);
			}
			RUN = false;
			logger.info("Contract Redemp Notice task end!");
			return;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			RUN = false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void generatorRedempNotice(List objectList) {
		List<Notice> noticeList = new ArrayList<Notice>();
		int size = objectList.size();
		for(int i=0;i<size;i++){										//针对每一个合同都生成一个通知,所以这里要进行循环操作
			Object[] object = (Object[])objectList.get(i);
			String noticeTitle = generatorRedempTitle(object);
			noticeList = generatorNotice(object,noticeTitle,StaticVariable.CUSTOMEREDEMPED,noticeList);
		}
		getHibernateTemplate().saveOrUpdateAll(noticeList);
	}
	private String generatorRedempTitle(Object[] object) {
		String title = "您的客户"+object[1].toString()+"("+object[2].toString()+")"+"办理的理财产品:"+object[5].toString()+"("+object[6].toString()+")" +
				"已赎回,对应的赎回区间为:["+object[8].toString()+","+object[9].toString()+"],开放日为:"+object[7].toString()+",请及时处理客户赎回后的相关事务,谢谢!";
		return title;
	}
	
	
	/**
	 * 每天凌晨01:30执行定时任务,更改与product关联的赎回区间的状态,
	 * 同时将当前属于正在赎回期的赎回区间更新到product中
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateProRedemTask() throws Exception {
		if(RUN) {
			logger.info("Change Product Redemption task has started!");
			return;		//避免多个任务同时启动
		}
		Date date = new Date();
		logger.info("Change Product Redemption task start!"+date.toString());
		RUN = true;
		try{
			String queryProdcutHql = "from Product where isRoll = '是'";
			List productList = getHibernateTemplate().find(queryProdcutHql);
			if(!productList.isEmpty()){
				Iterator iterator = productList.iterator();
				while(iterator.hasNext()){
					Product product = (Product)iterator.next();
					this.updateProRedem(product);
				}
			}
			RUN = false;
			logger.info("Change Product Redemption task end!");
			return;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			RUN = false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateProRedem(Product product) {
		String currentDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), 1), CalendarUtil.DATEFORMAT_YYYYMMDD);
		ProductRedemptionInterval productRedemptionInterval = null;
		String priId = product.getRedemptionIntervalId();
		String sql = "select * from productRedemptionInterval where productId ='" + product.getProductId() + "' and (redeemBegin <= '" + currentDate + "' and openDay >= '" + currentDate + "')";
		List<ProductRedemptionInterval> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ProductRedemptionInterval.class)).list();
		if(!list.isEmpty()){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				ProductRedemptionInterval pri = (ProductRedemptionInterval)iterator.next();
				if(!pri.getRedemptionStatus().equals(StaticVariable.WRONGREDEMP)){
					String redemptionStatus = redemptionStatus(pri);
					if(redemptionStatus.equals(StaticVariable.INREDEMPTION)){
						productRedemptionInterval = pri;
					}
					if(!pri.getRedemptionStatus().equals(redemptionStatus)){
						pri.setRedemptionStatus(redemptionStatus);
						getHibernateTemplate().update(pri);
					}
				}
			}
		}
		if(productRedemptionInterval!=null){
			product.setOpenDay(productRedemptionInterval.getOpenDay());
			product.setRedeemBegin(productRedemptionInterval.getRedeemBegin());
			product.setRedeemEnd(productRedemptionInterval.getRedeemEnd());
			product.setRollBenefit(productRedemptionInterval.getRollBenefit());
			product.setRedemptionIntervalId(productRedemptionInterval.getRedemptionIntervalId());
		}else{
			product.setOpenDay("");
			product.setRedeemBegin("");
			product.setRedeemEnd("");
			product.setRollBenefit("");
			product.setRedemptionIntervalId("");
		}
		if(!product.getRedemptionIntervalId().equals(priId)){
			product.setEditTime(CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD));
			getHibernateTemplate().update(product);
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
	
	
	/**
	 * 每天凌晨02:00执行定时任务,生成理财产品进入赎回区间的通知,将这条通知推送到办理该产品的合同的签约机构和签约机构那里(注:该合同对应的客户未赎回)
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void productStepInRedTask() throws Exception {
		if(RUN) {
			logger.info("Product Step in Redeemp Notice task has started!");
			return;		//避免多个任务同时启动
		}	
		Date date = new Date();
		logger.info("Product Step in Redeemp task start!"+date.toString());
		RUN = true;
		try{
			String redeemBeginDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), 1), CalendarUtil.DATEFORMAT_YYYYMMDD);
			String sql = "select * from productRedemptionInterval where redeemBegin='"+redeemBeginDate+"'";
			List<ProductRedemptionInterval> priList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(ProductRedemptionInterval.class).list();
			if(!priList.isEmpty()){
				Iterator iterator = priList.iterator();
				while(iterator.hasNext()){
					ProductRedemptionInterval pri = (ProductRedemptionInterval)iterator.next();
					String productId = pri.getProductId();
					String queryProductHql = "from Product where productId='"+productId+"'";
					Product product = (Product)getHibernateTemplate().find(queryProductHql).get(0);
					String queryContractHql = "from Contract where productCode='"+product.getProductCode()+"' and redempStatus ='"+StaticVariable.UNCONTRACTRED+"'";
					List<Contract> contractList = (List<Contract>)getHibernateTemplate().find(queryContractHql);
					generatorStepInRedNotice(contractList,product,pri);
				}
			}
			RUN = false;
			logger.info("Product Step in Redeemp task end!");
			return;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			RUN = false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void generatorStepInRedNotice(List<Contract> contractList, Product product,ProductRedemptionInterval pri) {
		List<Notice> noticeList = new ArrayList<Notice>();
		Iterator iterator = contractList.iterator();
		while(iterator.hasNext()){
			Contract contract = (Contract)iterator.next();
			String noticeTitle = generatorStepInRedTitle(contract,product,pri);
			noticeList = generatorNotice(contract,noticeTitle,StaticVariable.PROREDEMPING,noticeList);
		}
		getHibernateTemplate().saveOrUpdateAll(noticeList);
	}
	/**
	 * 生成生成理财产品进入赎回区间的通知内容
	 * @param product
	 * @param pri
	 * @return
	 */
	private String generatorStepInRedTitle(Contract contract,Product product, ProductRedemptionInterval pri) {
		String currentDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), 1), CalendarUtil.DATEFORMAT_YYYYMMDD);
		String title = "您的客户"+contract.getCustomeName()+"("+contract.getCustomeId()+")办理的理财产品:"+product.getProductName()+"("+product.getProductCode()+")将于明日"+currentDate+
				"进入开放日为:"+pri.getOpenDay()+",赎回区间为:["+pri.getRedeemBegin()+","+pri.getRedeemEnd()+"]的赎回期,请及时处理产品进入赎回期后的相关事务,谢谢!";
		return title;
	}

	
	/**
	 * 每天凌晨02:30执行定时任务,生成理财产品到期的通知,将这条通知推送到办理该产品的合同的签约机构和签约机构那里(注:该合同对应的客户未赎回)
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void productUpComingTask() throws Exception {
		if(RUN) {
			logger.info("Product Due Date Notice task has started!");
			return;		//避免多个任务同时启动
		}
		Date date = new Date();
		logger.info("Product Due Date task start!"+date.toString());
		RUN = true;
		try{
			String dueDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), 1), CalendarUtil.DATEFORMAT_YYYYMMDD);
			String querySql = "select * from productDueDateView where dueDate ='"+dueDate+"' and redempStatus ='"+StaticVariable.UNCONTRACTRED+"'";
			List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(querySql)
																							.addScalar("contractId", Hibernate.STRING)
																							.addScalar("customeName", Hibernate.STRING)
																							.addScalar("customeId", Hibernate.STRING)
																							.addScalar("belongDepartmentId", Hibernate.STRING)
																							.addScalar("signDepartmentId", Hibernate.STRING)
																							.addScalar("productCode", Hibernate.STRING)
																							.addScalar("productName", Hibernate.STRING)
																							.list();
			if(!objectList.isEmpty()){
				generatorUpComingNotice(objectList);
			}
			RUN = false;
			logger.info("Product Due Date task end!");
			return;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			RUN = false;
		}
	}
	/**
	 * 生成通知
	 * @param objectList
	 */
	@SuppressWarnings("unchecked")
	private void generatorUpComingNotice(List objectList) {
		List<Notice> noticeList = new ArrayList<Notice>();
		int size = objectList.size();
		for(int i=0;i<size;i++){										//针对每一个合同都生成一个通知,所以这里要进行循环操作
			Object[] object = (Object[])objectList.get(i);
			String noticeTitle = generatorUpComingTitle(object);
			noticeList = generatorNotice(object,noticeTitle,StaticVariable.PRODEADLINE,noticeList);
		}
		getHibernateTemplate().saveOrUpdateAll(noticeList);
	}
	/**
	 * 生成理财产品到期的通知内容
	 * @return
	 */
	private String generatorUpComingTitle(Object[] object) {
		String currentDate = CalendarUtil.formatDatetime(CalendarUtil.getRangeDate(new Date(), 1), CalendarUtil.DATEFORMAT_YYYYMMDD);
		String title = "您的客户"+object[1].toString()+"("+object[2].toString()+")办理的理财产品:"+object[6].toString()+"("+object[5].toString()+")将于明日"+currentDate+"到期,请及时处理产品到期后的相关事务,谢谢!";
		return title;
	}
	
	/**
	 * 生成通知
	 * @param object
	 * @param noticeList
	 * @param noticeTitle
	 * @return
	 */
	private List<Notice> generatorNotice(Object[] object,String noticeTitle,String noticeType,List<Notice> noticeList) {
		if(object[3]!=null&&object[3].toString().length()>0&&object[4]!=null&&object[4].toString().length()>0){
			if(!object[3].toString().equals(object[4].toString())){
				Notice belongDepartmentNotice = initNotice(noticeType,noticeTitle,object[0].toString(),object[3].toString());
				Notice signDepartmentNotice = initNotice(noticeType,noticeTitle,object[0].toString(),object[4].toString());
				noticeList.add(belongDepartmentNotice);
				noticeList.add(signDepartmentNotice);
			}else{
				Notice belongDepartmentNotice = initNotice(noticeType,noticeTitle,object[0].toString(),object[3].toString());
				noticeList.add(belongDepartmentNotice);
			}
		}else if(object[3]!=null&&object[3].toString().length()>0){
			Notice belongDepartmentNotice = initNotice(noticeType,noticeTitle,object[0].toString(),object[3].toString());
			noticeList.add(belongDepartmentNotice);
		}else if(object[4]!=null&&object[4].toString().length()>0){
			Notice signDepartmentNotice = initNotice(noticeType,noticeTitle,object[0].toString(),object[4].toString());
			noticeList.add(signDepartmentNotice);
		}
		return noticeList;
	}
	/**
	 * 初始化一条通知
	 * @param noticeType
	 * @param noticeTitle
	 * @param associateBusinessId
	 * @param departmentId
	 * @return
	 */
	private Notice initNotice(String noticeType, String noticeTitle,
			String associateBusinessId, String departmentId) {
		String currentDate = CalendarUtil.getDateTimeStr();
		Notice notice = new Notice();
		notice.setNoticeId(UUIDGenerator.generateShortUuid());
		notice.setNoticeType(noticeType);
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeViewStatus(StaticVariable.UNSCANED);
		notice.setNoticeDealStatus(StaticVariable.UNDEAL);			
		notice.setNoticeArriveTime(currentDate);				
		notice.setAssBusinessId(associateBusinessId);		
		notice.setDepartmentId(departmentId);
		notice.setNoticeFlag(StaticVariable.NOTICEONE);
		return notice;
	}
}
