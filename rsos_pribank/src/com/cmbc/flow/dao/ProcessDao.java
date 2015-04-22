package com.cmbc.flow.dao;

import java.util.List;
import java.util.Map;

import com.cmbc.flow.bean.InstanceInformation;

public interface ProcessDao{

	List<InstanceInformation> findWaitByConditions(String table,int currentPage, int pageSize,String userId,Map<String, Object> condition);

	int getWaitTotal(String table,String userId, Map<String, Object> condition);

	List<InstanceInformation> findCompleteByConditions(String table,int currentPage, int pageSize, String userId,Map<String, Object> condition);

	int getCompleteTotal(String table, String userId,Map<String, Object> condition);

	List<InstanceInformation> findFinishedByConditions(String table,int currentPage, int pageSize, String userId,Map<String, Object> condition);

	int getFinishedTotal(String table, String userId,Map<String, Object> condition);

}
