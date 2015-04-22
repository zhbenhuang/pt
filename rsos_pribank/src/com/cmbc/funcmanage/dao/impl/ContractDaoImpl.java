package com.cmbc.funcmanage.dao.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.utils.UUIDGenerator;

import com.cmbc.funcmanage.bean.BranchContractView;
import com.cmbc.funcmanage.bean.Contract;
import com.cmbc.funcmanage.bean.NoticeView;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.bean.RedempBook;
import com.cmbc.funcmanage.dao.ContractDao;


public class ContractDaoImpl extends EasyBaseDaoImpl<Contract> implements ContractDao{

	protected static final Log logger = LogFactory.getLog(ContractDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> findByPagination(String table,int currentPage, int pageSize,Map<String, Object> m){
		String sql="select distinct contractId from "+table+" where 1=1";
		sql = generatorQuerySql(sql,m);
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		List<Contract> contractList = new ArrayList<Contract>();
		if(!list.isEmpty()){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				String contractId = iterator.next().toString();
				Contract contract = findContractByContractId(contractId);
				contractList.add(contract);
			}
		}
		return contractList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalNew(String table,Map<String, Object> m){
		String sql="select distinct contractId from "+table+" where 1=1 ";
		sql = generatorQuerySql(sql,m);
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			return list.size();
		}else{
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> findByPage(String table, int currentPage, int pageSize,Map<String, Object> m) {
		String sql = "select * from "+table+" where 1=1 ";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if("productName".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and productName like '%"+entry.getValue()+"%'";
			}else if("belongDepartment".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and belongDepartment like '%"+entry.getValue()+"%'";
			}else if("signDepartment".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and signDepartment like '%"+entry.getValue()+"%'";
			}else if("departmentId".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and (belongDepartmentId ='"+entry.getValue()+"' or signDepartmentId ='"+entry.getValue()+"')";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		List<Contract> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(Contract.class).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(String table, Map<String, Object> m) {
		String sql = "select * from "+table+" where 1=1 ";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if("productName".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and productName like '%"+entry.getValue()+"%'";
			}else if("belongDepartment".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and belongDepartment like '%"+entry.getValue()+"%'";
			}else if("signDepartment".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and signDepartment like '%"+entry.getValue()+"%'";
			}else if("departmentId".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and (belongDepartmentId ='"+entry.getValue()+"' or signDepartmentId ='"+entry.getValue()+"')";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		List<Contract> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			return list.size();
		}else{
			return 0;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeView> findNoticesByPage(String table,int currentPage, int pageSize,Map<String, Object> m) {
		String sql = "select * from "+table+" where 1=1 ";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> me = (Map.Entry<String, Object>)iterator.next();
			if("arriveDateBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeArriveTime >= '"+ me.getValue()  +"'" ;
			}else if("arriveDateEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeArriveTime <= '"+ me.getValue()  +"'" ;
			}else if("dealDateBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeDealTime >= '"+ me.getValue()  +"'" ;
			}else if("dealDateEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeDealTime <= '"+ me.getValue()  +"'" ;
			}else if((!me.getValue().equals(""))){
				sql += " and "+me.getKey()+" = '"+me.getValue()+"'";
			}
		}
		sql += "order by noticeArriveTime DESC";
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
																							.setFirstResult((currentPage-1)*pageSize)
																							.setMaxResults(pageSize)
																							.list();
		List<NoticeView> noticeViewList = new ArrayList<NoticeView>();
		if(!objectList.isEmpty()){
			int size = objectList.size();
			for(int i=0;i<size;i++){
				Object[] object = (Object[])objectList.get(i);
				NoticeView noticeView = new NoticeView();
				noticeView.setNoticeId(object[0].toString());
				noticeView.setNoticeType(object[1].toString());
				StringBuilder str = new StringBuilder();
				str.append(object[2].toString().substring(0, 30)).append("...");
				noticeView.setNoticeTitle(str.toString());
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
				noticeViewList.add(noticeView);
			}
		}
		return noticeViewList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getNoticeTotal(String table, Map<String, Object> m) {
		String sql = "select count(*) from "+table+" where 1=1 ";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> me = (Map.Entry<String, Object>)iterator.next();
			if("arriveDateBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeArriveTime >= '"+ me.getValue()  +"'" ;
			}else if("arriveDateEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeArriveTime <= '"+ me.getValue()  +"'" ;
			}else if("dealDateBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeDealTime >= '"+ me.getValue()  +"'" ;
			}else if("dealDateEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and noticeDealTime <= '"+ me.getValue()  +"'" ;
			}else if((!me.getValue().equals(""))){
				sql += " and "+me.getKey()+" = '"+me.getValue()+"'";
			}
		}
		List object = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!object.isEmpty()){
			return Integer.parseInt(object.get(0).toString());
		}else{
			return 0;
		}
		
	}

	@Override
	protected Class<Contract> getType() {
		return Contract.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteContract(final String contractId) {
		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" delete from contract where contractId=:contractId");
		this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery( sqlBuilder.toString() );
				query.setString("contractId",contractId);
				return query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contract findContractByCode(final String codeId) {
		Contract result = (Contract)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from contractView b ")
					.append(" where b.codeId=:codeId ");
				Query queryResult = session.createSQLQuery( sql.toString() )
										.addEntity("b", Contract.class);
				queryResult.setString("codeId",codeId );
                return (Contract) queryResult.uniqueResult();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contract findContractByBusId(final String businessId) {
		Contract result = (Contract)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from contractView b ")
					.append(" where b.businessId=:businessId ");
				Query queryResult = session.createSQLQuery( sql.toString())
										.addEntity("b", Contract.class);
				queryResult.setString("businessId",businessId );
                return (Contract) queryResult.uniqueResult();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contract findContractByContractId(final String contractId) {
		Contract result = (Contract)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select {b.*} from contractView b ")
					.append(" where b.contractId=:contractId ");
				Query queryResult = session.createSQLQuery( sql.toString())
										.addEntity("b", Contract.class);
				queryResult.setString("contractId",contractId );
                return (Contract) queryResult.uniqueResult();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RedempBook> findRedempBooks(String table, int currentPage,
			int pageSize, Map<String, Object> m) {
		String sql = "select * from "+table+" where 1=1 ";
		sql = generatorQuerySql(sql,m);
		sql += " order by productCode ASC";
		List<RedempBook> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).addEntity(RedempBook.class).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getBooksTotal(String table, Map<String, Object> m) {
		String sql = "select count(*) from "+table+" where 1=1 ";
		sql = generatorQuerySql(sql,m);
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchContractView> findBranchContractByPage(String table,
			int currentPage, int pageSize, Map<String, Object> m) {
		String sql = "select * from "+table+" where 1=1 ";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if("departmentId".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and (belongDepartmentId ='"+entry.getValue()+"' or signDepartmentId ='"+entry.getValue()+"')";
			}else if("productName".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and productName like '%"+entry.getValue()+"%'";
			}else if("customeName".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and customeName like '%"+entry.getValue()+"%'";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		sql += "order by productCode ASC";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql)
																							.addScalar("contractId", Hibernate.STRING)
																							.addScalar("customeId", Hibernate.STRING)
																							.addScalar("customeName", Hibernate.STRING)
																							.addScalar("productId", Hibernate.STRING)
																							.addScalar("productCode", Hibernate.STRING)
																							.addScalar("productName", Hibernate.STRING)
																							.addScalar("signAccount", Hibernate.STRING)
																							.addScalar("handStatus", Hibernate.STRING)
																							.addScalar("redempStatus", Hibernate.STRING)
																							.addScalar("benefitDate", Hibernate.STRING)
																							.addScalar("dueDate", Hibernate.STRING)
																							.addScalar("plannedBenefit", Hibernate.STRING)
																							.addScalar("isRoll", Hibernate.STRING)
																							
																							.addScalar("handDate", Hibernate.STRING)
																							.addScalar("getContractDate", Hibernate.STRING)
																							.addScalar("redempStartDate", Hibernate.STRING)
																							.addScalar("redempConformDate", Hibernate.STRING)
																							.addScalar("openDay", Hibernate.STRING)
																							.addScalar("redeemBegin", Hibernate.STRING)
																							.addScalar("redeemEnd", Hibernate.STRING)
																							.addScalar("rollBenefit", Hibernate.STRING)
																							.setFirstResult((currentPage-1)*pageSize)
																							.setMaxResults(pageSize)
																							.list();
		List<BranchContractView> branchContractViewList = new ArrayList<BranchContractView>();
		if(!objectList.isEmpty()){
			int size = objectList.size();
			for(int i=0;i<size;i++){
				Object[] object = (Object[])objectList.get(i);
				BranchContractView branchContractView = new BranchContractView(
																object[0].toString(),
																object[1].toString(),
																object[2].toString(),
																object[3].toString(),
																object[4].toString(),
																object[5].toString(),
																object[6].toString(),
																object[7].toString(),
																object[8].toString(),
																object[9].toString(),
																object[10].toString(),
																object[11].toString(),
																object[12].toString()
																);
				
				if(object[13]!=null){
					branchContractView.setHandDate(object[13].toString());
				}
				if(object[14]!=null){
					branchContractView.setGetContractDate(object[14].toString());
				}
				if(object[15]!=null){
					branchContractView.setRedempStartDate(object[15].toString());
				}
				if(object[16]!=null){
					branchContractView.setRedempConformDate(object[16].toString());
				}
				if(object[17]!=null){
					branchContractView.setOpenDay(object[17].toString());
					branchContractView.setRedeemBegin(object[18].toString());
					branchContractView.setRedeemEnd(object[19].toString());
					branchContractView.setRollBenefit(object[20].toString());
				}
				branchContractViewList.add(branchContractView);
			}
		}
		return branchContractViewList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getBranchContractTotal(String table, Map<String, Object> m) {
		String sql = "select count(*) from "+table+" where 1=1 ";
		Set<Entry<String,Object>> set = m.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if("departmentId".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and (belongDepartmentId ='"+entry.getValue()+"' or signDepartmentId ='"+entry.getValue()+"')";
			}else if("productName".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and productName like '%"+entry.getValue()+"%'";
			}else if("customeName".equals(entry.getKey())&&(!"".equals(entry.getValue()))){
				sql += " and customeName like '%"+entry.getValue()+"%'";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!objectList.isEmpty()){
			return Integer.parseInt(objectList.get(0).toString());
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchContractView> findBranchProductContract(int currentPage,int pageSize, Map<String, Object> m) {
		String sql="select distinct contractId from branchProductContractView where 1=1";
		sql = generatorQuerySql(sql,m);
		String queryInfoSql = "select * from branchProductContractShowView inner join ("+sql+") as T " +
				"on branchProductContractShowView.contractId=T.contractId order by branchProductContractShowView.productCode asc";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(queryInfoSql)
																					.addScalar("contractId", Hibernate.STRING)
																					.addScalar("customeId", Hibernate.STRING)
																					.addScalar("customeName", Hibernate.STRING)
																					.addScalar("productId", Hibernate.STRING)
																					.addScalar("productCode", Hibernate.STRING)
																					.addScalar("productName", Hibernate.STRING)
																					.addScalar("redempStatus", Hibernate.STRING)
																					.addScalar("dueDate", Hibernate.STRING)
																					.addScalar("isRoll", Hibernate.STRING)
																					.addScalar("productType", Hibernate.STRING)
																					
																					.addScalar("belongDepartment", Hibernate.STRING)
																					.addScalar("signDepartment", Hibernate.STRING)
																					.addScalar("openDay", Hibernate.STRING)
																					.addScalar("redeemBegin", Hibernate.STRING)
																					.addScalar("redeemEnd", Hibernate.STRING)
																					.addScalar("rollBenefit", Hibernate.STRING)
																					.setFirstResult((currentPage-1)*pageSize)
																					.setMaxResults(pageSize)
																					.list();
		List<BranchContractView> branchContractViewList = new ArrayList<BranchContractView>();
		if(!objectList.isEmpty()){
			int size = objectList.size();
			for(int i=0;i<size;i++){
				Object[] object = (Object[])objectList.get(i);
				BranchContractView branchContractView = new BranchContractView();
				branchContractView = generatorBranchContractView(branchContractView,object);
				branchContractViewList.add(branchContractView);
			}
		}
		return branchContractViewList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getTotalBranchProductContract(Map<String, Object> m) {
		String sql="select distinct contractId from branchProductContractView where 1=1";
		sql = generatorQuerySql(sql,m);
		List<Contract> list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			return list.size();
		}else{
			return 0;
		}
	}

	private BranchContractView generatorBranchContractView(BranchContractView branchContractView, Object[] object) {
		branchContractView = new BranchContractView(
													object[0].toString(),
													object[1].toString(),
													object[2].toString(),
													object[3].toString(),
													object[4].toString(),
													object[5].toString(),
													object[6].toString(),
													object[7].toString(),
													object[8].toString(),
													object[9].toString()
													);
		if(object[10]!=null&&object[10].toString().length()>0){
			branchContractView.setBelongDepartment(object[10].toString());
		}
		if(object[11]!=null&&object[11].toString().length()>0){
			branchContractView.setSignDepartment(object[11].toString());
		}
		if(object[12]!=null&&object[12].toString().length()>0){
			branchContractView.setOpenDay(object[12].toString());
			branchContractView.setRedeemBegin(object[13].toString());
			branchContractView.setRedeemEnd(object[14].toString());
			branchContractView.setRollBenefit(object[15].toString());
		}
		return branchContractView;
	}

	@SuppressWarnings("unchecked")
	private String generatorQuerySql(String sql, Map<String, Object> m) {
		Set<Entry<String, Object>> set = m.entrySet();
		Iterator io = set.iterator();
		while (io.hasNext()) {
			Map.Entry<String, Object> me = (Map.Entry<String, Object>) io.next();
			if("departmentId".equals(me.getKey())&&(!"".equals(me.getValue()))){
				sql += " and (belongDepartmentId ='"+me.getValue()+"' or signDepartmentId ='"+me.getValue()+"')";
			}else if("belongDepartment".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + "like '%"+ me.getValue()  +"%'" ;
			}else if("signDepartment".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
			}else if("openDayBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and openDay >= '"+ me.getValue()  +"'" ;
			}else if("openDayEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and openDay <= '"+ me.getValue()  +"'" ;
			}else if("dueDateBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and dueDate >= '"+ me.getValue()  +"'" ;
			}else if("dueDateEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and dueDate <= '"+ me.getValue()  +"'" ;
			}else if("addDateBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and addDate >= '"+ me.getValue()  +"'" ;
			}else if("addDateEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and addDate <= '"+ me.getValue()  +"'" ;
			}else if("productName".equals(me.getKey())&&(!"".equals(me.getValue()))){
				sql += " and productName like '%"+me.getValue()+"%'";
			}else if("customeName".equals(me.getKey())&&(!"".equals(me.getValue()))){
				sql += " and customeName like '%"+me.getValue()+"%'";
			}else if("confirmBegin".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and redempConformDate >= '"+ me.getValue()  +"'" ;
			}else if("confirmEnd".equals(me.getKey()) && !"".equals(me.getValue())){
				sql += " and redempConformDate <= '"+ me.getValue()  +"'" ;
			}else if((!me.getValue().equals(""))&&(!me.getKey().equals("redeemBegin"))&&(!me.getKey().equals("redeemEnd"))){
				sql += " and "+me.getKey()+" = '"+me.getValue()+"'";
			}
		}
		if(m.containsKey("redeemBegin")&&(!"".equals(m.get("redeemBegin")))&&m.containsKey("redeemEnd")&&(!"".equals(m.get("redeemEnd")))){
			String A = (String) m.get("redeemBegin");
    		String B = (String) m.get("redeemEnd");
    		sql += " and ((redeemBegin >= '" + A + "' and " + "redeemEnd <= '" + B + "')";
    		sql += " or (redeemBegin < '" + A + "' and " + "redeemEnd > '" + A + "')";
    		sql += " or (redeemBegin < '" + B + "' and " + "redeemEnd > '" + B + "'))";	
		}else if(m.containsKey("redeemBegin")&&(!"".equals(m.get("redeemBegin")))){
			String A = (String) m.get("redeemBegin");
			sql += " and ((redeemBegin >= '" + A + "')";
	    	sql += " or (redeemBegin <= '" + A + "' and redeemEnd >= '" + A +"'))";
		}else if(m.containsKey("redeemEnd")&&(!"".equals(m.get("redeemEnd")))){
			String B = (String) m.get("redeemEnd");
			sql += " and ((redeemEnd <= '" + B + "')";
	    	sql += " or (redeemEnd >= '" + B + "' and redeemBegin <= '" + B +"'))";
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setContractRedempTime(final ProductRedemptionInterval pri,final String contractId) {
		@SuppressWarnings("unused")
		List list= (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("insert contractRedempTime(id,contractId,openDay,redeemBegin,redeemEnd,rollBenefit,redemptionStatus) values(?,?,?,?,?,?,?)");
				Query queryResult = session.createSQLQuery(sql.toString());
				int index=0;
				queryResult.setParameter(index++,UUIDGenerator.generateShortUuid());
				queryResult.setParameter(index++,contractId);
				queryResult.setParameter(index++,pri.getOpenDay());
				queryResult.setParameter(index++,pri.getRedeemBegin());
				queryResult.setParameter(index++,pri.getRedeemEnd());
				queryResult.setParameter(index++,pri.getRollBenefit());
				queryResult.setParameter(index++,pri.getRedemptionStatus());
				queryResult.executeUpdate();
		        Object o=null;
		        return o;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductRedemptionInterval findContractRedempTimeById(
			String contractId) {
		String querySql = "select * from contractRedempTime where contractId='"+contractId+"'";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(querySql)
																				 .addScalar("openDay", Hibernate.STRING)		
																				 .addScalar("redeemBegin", Hibernate.STRING)			
																				 .addScalar("redeemEnd", Hibernate.STRING)		
																				 .addScalar("rollBenefit", Hibernate.STRING)			
																				 .addScalar("redemptionStatus", Hibernate.STRING)
																				 .list();
		ProductRedemptionInterval productRedemptionInterval = new ProductRedemptionInterval();
		if(!objectList.isEmpty()){										
			Object[] object = (Object[])objectList.get(0);
			productRedemptionInterval.setOpenDay(object[0].toString());
			productRedemptionInterval.setRedeemBegin(object[1].toString());
			productRedemptionInterval.setRedeemEnd(object[2].toString());
			productRedemptionInterval.setRollBenefit(object[3].toString());
			productRedemptionInterval.setRedemptionStatus(object[4].toString());
		}
		return productRedemptionInterval;
	}
}
