package com.cmbc.flow.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import rsos.framework.constant.StaticVariable;

import com.cmbc.flow.bean.InstanceInformation;
import com.cmbc.flow.dao.ProcessDao;

public class ProcessDaoImpl extends HibernateDaoSupport implements ProcessDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<InstanceInformation> findWaitByConditions(String table,
			int currentPage, int pageSize,String userId,Map<String, Object> condition) {
		String sql = "select * from "+table+" where assignUserId ='"+userId+"' and taskIsActivity = '"+StaticVariable.UNCOMPLETED+"'";
		Set<Entry<String,Object>> set = condition.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if((entry.getKey().equals("topic")||entry.getKey().equals("processTypeName"))&&(!entry.getValue().equals(""))){
				sql += " and "+entry.getKey()+" like '%"+entry.getValue()+"%'";
			}else if(entry.getKey().contains("Time")&&(!entry.getValue().equals(""))){
				String[] str = (String[])entry.getValue();
				sql += " and convert(varchar(8),"+entry.getKey()+",112) between '"+str[0]+"' and '"+str[1]+"'";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		sql += " order by arriveTime DESC";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql)
																			 .addScalar("businessId", Hibernate.STRING)		//流水号
																			 .addScalar("taskId", Hibernate.STRING)			//任务Id
																			 .addScalar("topic", Hibernate.STRING)			//标题
																			 .addScalar("processTypeName", Hibernate.STRING)	//流程类型
																			 .addScalar("processStatus", Hibernate.STRING)      //流程状态
																			 .addScalar("createUserName", Hibernate.STRING)		//流程发起人
																			 .addScalar("department", Hibernate.STRING)			//流程发起机构
																			 .addScalar("startTime", Hibernate.STRING)			//发起日期
																			 .addScalar("arriveTime", Hibernate.STRING)			//到达日期
																			 .addScalar("signTime", Hibernate.STRING)			//签收日期
																			 .addScalar("preUserId", Hibernate.STRING)			//上一办理人Id
																			 .addScalar("preUserName", Hibernate.STRING)		//上一办理人
																			 .addScalar("preDepartment", Hibernate.STRING)		//上一办理机构
																			 .addScalar("codeId", Hibernate.STRING)				//条码号
																			 .setFirstResult((currentPage-1)*pageSize)
																			 .setMaxResults(pageSize)
																			 .list();
		List<InstanceInformation> instanceInfoList = new ArrayList<InstanceInformation>();
		if(!objectList.isEmpty()){
			int size = objectList.size();
			for(int i=0;i<size;i++){							
				Object[] object = (Object[])objectList.get(i);
				InstanceInformation instanceInfo = new InstanceInformation();
				instanceInfo.setBusinessId(object[0].toString());
				instanceInfo.setTaskId(object[1].toString());
				instanceInfo.setTitle(object[2].toString());
				instanceInfo.setProcessTypeName(object[3].toString());
				instanceInfo.setStatus(object[4].toString());
				instanceInfo.setUserName(object[5].toString());
				instanceInfo.setDepartment(object[6].toString());
				instanceInfo.setCreateTime(object[7].toString());
				
				if(object[8]==null){
					instanceInfo.setArriveTime("暂未到达");
				}else{
					instanceInfo.setArriveTime(object[8].toString());
				}
				if(object[9]==null){
					instanceInfo.setSignTime("暂未签收");
				}else{
					instanceInfo.setSignTime(object[9].toString());
				}
				
				instanceInfo.setPreUserId(object[10].toString());
				instanceInfo.setPreUserName(object[11].toString());
				instanceInfo.setPreDepartment(object[12].toString());
				if(object[13]!=null){
					instanceInfo.setCodeId(object[13].toString());
				}else{
					instanceInfo.setCodeId("");
				}
				
				instanceInfoList.add(instanceInfo);
			}
		}
		return instanceInfoList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getWaitTotal(String table,String userId, Map<String, Object> condition) {
		String sql = "select count(*) from "+table+" where assignUserId ='"+userId+"' and taskIsActivity = '"+StaticVariable.UNCOMPLETED+"'";
		Set<Entry<String,Object>> set = condition.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if((entry.getKey().equals("topic")||entry.getKey().equals("processTypeName"))&&(!entry.getValue().equals(""))){
				sql += " and "+entry.getKey()+" like '%"+entry.getValue()+"%'";
			}else if(entry.getKey().contains("Time")&&(!entry.getValue().equals(""))){
				String[] str = (String[])entry.getValue();
				sql += " and convert(varchar(8),"+entry.getKey()+",112) between '"+str[0]+"' and '"+str[1]+"'";
			}else if(!entry.getValue().equals("")){
				sql += " and "+entry.getKey()+" = '"+entry.getValue()+"'";
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
	public List<InstanceInformation> findCompleteByConditions(String table,int currentPage, int pageSize, String userId,Map<String, Object> condition) {
		String sql0 = "(select T1.* " +
					 	"from completedProcessTask_View AS T1 INNER JOIN " +
					 	"(select businessId,Max(arriveTime) AS arriveTime from completedProcessTask_View where assignUserId='"+userId+"' and processIsActivity = 'UNEND' group by businessId) as T2 "+ 
					 	"on T1.businessId=T2.businessId and T1.arriveTime = T2.arriveTime where T1.assignUserId='"+userId+"') as T ";
		String sql = "select * from "+sql0+" where T.taskIsActivity ='COMPLETED'";
		
		Set<Entry<String,Object>> set = condition.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if((entry.getKey().equals("topic")||entry.getKey().equals("processTypeName"))&&(!entry.getValue().equals(""))){
				sql += " and T."+entry.getKey()+" like '%"+entry.getValue()+"%'";
			}else if(entry.getKey().contains("Time")&&(!entry.getValue().equals(""))){
				String[] str = (String[])entry.getValue();
				sql += " and convert(varchar(8),T."+entry.getKey()+",112) between '"+str[0]+"' and '"+str[1]+"'";
			}else if(!entry.getValue().equals("")){
				sql += " and T."+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		sql += " order by T.completeTime DESC";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql)
																	 .addScalar("businessId", Hibernate.STRING)		//流水号
																	 .addScalar("taskId", Hibernate.STRING)			//任务Id
																	 .addScalar("topic", Hibernate.STRING)			//标题
																	 .addScalar("processTypeName", Hibernate.STRING)	//流程类型
																	 .addScalar("processStatus", Hibernate.STRING)      //流程状态
																	 .addScalar("createUserName", Hibernate.STRING)		//流程发起人
																	 .addScalar("department", Hibernate.STRING)			//流程发起机构
																	 .addScalar("startTime", Hibernate.STRING)			//发起日期
																	 .addScalar("arriveTime", Hibernate.STRING)			//到达日期
																	 .addScalar("signTime", Hibernate.STRING)			//签收日期
																	 .addScalar("completeTime", Hibernate.STRING)		//办理日期
																	 .addScalar("currentUserId", Hibernate.STRING)		//当前办理人
																	 .addScalar("currentUserName", Hibernate.STRING)		//当前办理人
																	 .addScalar("currentDepartmentName", Hibernate.STRING)		//当前办理机构
																	 .setFirstResult((currentPage-1)*pageSize)
																	 .setMaxResults(pageSize)
																	 .list();
		List<InstanceInformation> instanceInfoList = new ArrayList<InstanceInformation>();
		if(!objectList.isEmpty()){
			int size = objectList.size();
			for(int i=0;i<size;i++){										//针对每一个合同都生成一个通知,所以这里要进行循环操作
				Object[] object = (Object[])objectList.get(i);
				InstanceInformation instanceInfo = new InstanceInformation();
				instanceInfo.setBusinessId(object[0].toString());
				instanceInfo.setTaskId(object[1].toString());
				instanceInfo.setTitle(object[2].toString());
				instanceInfo.setProcessTypeName(object[3].toString());
				instanceInfo.setStatus(object[4].toString());
				instanceInfo.setUserName(object[5].toString());
				instanceInfo.setDepartment(object[6].toString());
				instanceInfo.setCreateTime(object[7].toString());
				if(object[8]==null){
					instanceInfo.setArriveTime("暂未到达");
				}else{
					instanceInfo.setArriveTime(object[8].toString());
				}
				if(object[9]==null){
					instanceInfo.setSignTime("暂未签收");
				}else{
					instanceInfo.setSignTime(object[9].toString());
				}
				if(object[10]==null){
					instanceInfo.setCompleteTime("暂未完成");
				}else{
					instanceInfo.setCompleteTime(object[10].toString());
				}
		
		
				instanceInfo.setCurrentUserId(object[11].toString());
				instanceInfo.setCurrentUserName(object[12].toString());
				instanceInfo.setCurrentDepartment(object[13].toString());
				instanceInfoList.add(instanceInfo);
			}
		}
		return instanceInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getCompleteTotal(String table, String userId,Map<String, Object> condition) {
		String sql0 = "(select T1.* " +
			"from completedProcessTask_View AS T1 INNER JOIN " +
			"(select businessId,Max(arriveTime) AS arriveTime from completedProcessTask_View where assignUserId='"+userId+"' and processIsActivity = 'UNEND' group by businessId) as T2 "+ 
			"on T1.businessId=T2.businessId and T1.arriveTime = T2.arriveTime where T1.assignUserId='"+userId+"') as T ";
		String sql = "select count(*) from "+sql0+" where T.taskIsActivity ='COMPLETED'";
		
		Set<Entry<String,Object>> set = condition.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if((entry.getKey().equals("topic")||entry.getKey().equals("processTypeName"))&&(!entry.getValue().equals(""))){
				sql += " and T."+entry.getKey()+" like '%"+entry.getValue()+"%'";
			}else if(entry.getKey().contains("Time")&&(!entry.getValue().equals(""))){
				String[] str = (String[])entry.getValue();
				sql += " and convert(varchar(8),T."+entry.getKey()+",112) between '"+str[0]+"' and '"+str[1]+"'";
			}else if(!entry.getValue().equals("")){
				sql += " and T."+entry.getKey()+" = '"+entry.getValue()+"'";
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
	public List<InstanceInformation> findFinishedByConditions(String table,int currentPage, int pageSize, String userId,Map<String, Object> condition) {
		String sql0 = "(select T1.* " +
						"from finishedProcessTask_View AS T1 INNER JOIN " +
						"(select businessId,Max(completeTime) AS completeTime from finishedProcessTask_View where assignUserId='"+userId+"' and processIsActivity = 'END' group by businessId) as T2 "+ 
						"on T1.businessId=T2.businessId and T1.completeTime = T2.completeTime) as T ";
		String sql = "select * from "+sql0+" where T.taskIsActivity ='COMPLETED'";
		
		Set<Entry<String,Object>> set = condition.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if((entry.getKey().equals("topic")||entry.getKey().equals("processTypeName"))&&(!entry.getValue().equals(""))){
				sql += " and T."+entry.getKey()+" like '%"+entry.getValue()+"%'";
			}else if(entry.getKey().contains("Time")&&(!entry.getValue().equals(""))){
				String[] str = (String[])entry.getValue();
				sql += " and convert(varchar(8),T."+entry.getKey()+",112) between '"+str[0]+"' and '"+str[1]+"'";
			}else if(!entry.getValue().equals("")){
				sql += " and T."+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		sql += " order by T.completeTime DESC";
		List objectList = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql)
																	 .addScalar("businessId", Hibernate.STRING)		//流水号
																	 .addScalar("taskId", Hibernate.STRING)			//任务Id
																	 .addScalar("topic", Hibernate.STRING)			//标题
																	 .addScalar("processTypeName", Hibernate.STRING)	//流程类型
																	 .addScalar("processStatus", Hibernate.STRING)      //流程状态
																	 .addScalar("createUserName", Hibernate.STRING)		//流程发起人
																	 .addScalar("department", Hibernate.STRING)			//流程发起机构
																	 .addScalar("startTime", Hibernate.STRING)			//发起日期
																	 .addScalar("arriveTime", Hibernate.STRING)			//到达日期
																	 .addScalar("signTime", Hibernate.STRING)			//签收日期
																	 .addScalar("completeTime", Hibernate.STRING)		//办理日期
																	 .addScalar("finishedTime", Hibernate.STRING)		//办结日期
																	 .addScalar("preUserId", Hibernate.STRING)			//上一办理人Id
																	 .addScalar("preUserName", Hibernate.STRING)		//上一办理人
																	 .addScalar("preDepartment", Hibernate.STRING)		//上一办理机构
																	 .addScalar("nextUserId", Hibernate.STRING)			//下一办理人Id
																	 .addScalar("nextUserName", Hibernate.STRING)		//下一办理人
																	 .addScalar("nextDepartment", Hibernate.STRING)		//下一办理机构
																	 .setFirstResult((currentPage-1)*pageSize)
																	 .setMaxResults(pageSize)
																	 .list();
		List<InstanceInformation> instanceInfoList = new ArrayList<InstanceInformation>();
		if(!objectList.isEmpty()){
			int size = objectList.size();
			for(int i=0;i<size;i++){										
				Object[] object = (Object[])objectList.get(i);
				InstanceInformation instanceInfo = new InstanceInformation();
				instanceInfo.setBusinessId(object[0].toString());
				instanceInfo.setTaskId(object[1].toString());
				instanceInfo.setTitle(object[2].toString());
				instanceInfo.setProcessTypeName(object[3].toString());
				instanceInfo.setStatus(object[4].toString());
				instanceInfo.setUserName(object[5].toString());
				instanceInfo.setDepartment(object[6].toString());
				instanceInfo.setCreateTime(object[7].toString());
				
				if(object[8]==null){
					instanceInfo.setArriveTime("暂未到达");
				}else{
					instanceInfo.setArriveTime(object[8].toString());
				}
				if(object[9]==null){
					instanceInfo.setSignTime("暂未签收");
				}else{
					instanceInfo.setSignTime(object[9].toString());
				}
				if(object[10]==null){
					instanceInfo.setCompleteTime("暂未完成");
				}else{
					instanceInfo.setCompleteTime(object[10].toString());
				}
				if(object[11]==null){
					instanceInfo.setFinishedTime("暂未办结");
				}else{
					instanceInfo.setFinishedTime(object[11].toString());
				}
				
				instanceInfo.setPreUserId(object[12].toString());
				instanceInfo.setPreUserName(object[13].toString());
				instanceInfo.setPreDepartment(object[14].toString());
				instanceInfo.setNextUserId(object[15].toString());
				instanceInfo.setNextUserName(object[16].toString());
				instanceInfo.setNextDepartment(object[17].toString());
		
				instanceInfoList.add(instanceInfo);
			}
		}
		return instanceInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getFinishedTotal(String table, String userId,Map<String, Object> condition) {
		String sql0 = "(select T1.* " +
						"from finishedProcessTask_View AS T1 INNER JOIN " +
						"(select businessId,Max(completeTime) AS completeTime from finishedProcessTask_View where assignUserId='"+userId+"' and processIsActivity = 'END' group by businessId) as T2 "+ 
						"on T1.businessId=T2.businessId and T1.completeTime = T2.completeTime) as T ";
		String sql = "select count(*) from "+sql0+" where T.taskIsActivity ='COMPLETED'";
		Set<Entry<String,Object>> set = condition.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iterator.next();
			if((entry.getKey().equals("topic")||entry.getKey().equals("processTypeName"))&&(!entry.getValue().equals(""))){
				sql += " and T."+entry.getKey()+" like '%"+entry.getValue()+"%'";
			}else if(entry.getKey().contains("Time")&&(!entry.getValue().equals(""))){
				String[] str = (String[])entry.getValue();
				sql += " and convert(varchar(8),T."+entry.getKey()+",112) between '"+str[0]+"' and '"+str[1]+"'";
			}else if(!entry.getValue().equals("")){
				sql += " and T."+entry.getKey()+" = '"+entry.getValue()+"'";
			}
		}
		List list = getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql).list();
		if(!list.isEmpty()){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return 0;
		}
	}
	
	

}
