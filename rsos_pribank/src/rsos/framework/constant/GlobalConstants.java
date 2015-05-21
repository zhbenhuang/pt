package rsos.framework.constant;

import org.apache.commons.lang.StringUtils;

public class GlobalConstants {
	/** Admin Console: System logger name */
    public static final String SYSTEM_LOGGER = "sytem";
    /** Console: User access logger name */
    public static final String USER_ACCESS_LOGGER = "userAccess";
    /** Console: User access logger name */
    public static final String CLIENTFREEZE_ACCESS_LOGGER = "clientFreezeAccess";
    /** Console: Scheduler logger name */
    public static final String SCHEDULER_LOGGER = "scheduler";
    
    public static final String USER_INFORMATION_KEY = "USER_INFORMATION";
    public static final Integer ROOT_NODE=9999;
    public static final String NODE_CLOSED="closed";
    public static final String NODE_OPEN="open";
    public static final String ROOT_BRANCH="03";
    public static final String BRANCH_LIST="BRANCH_LIST";
    public static final String BUSINESS="BUSINESS";
    public static final String LOGIN_BRANCH="LOGIN_BRANCH";
    public static final String USERROLE_KEY = "USERROLE";
    
    public static final String RECONSIDERTOOLBAR_KEY = "RECONSIDERTOOLBAR";
    
    public static String decodeParam(String uri_encoding, String value) {
		if (StringUtils.isBlank(value) || StringUtils.isBlank(uri_encoding) || StringUtils.isNumeric(value))
			return value;		
		try{
			return new String(value.getBytes("iso-8859-1"), uri_encoding);
		} catch(Exception e) {
			return value;
		}
	}
    
    public static void main(String[] args){
    	System.out.println(decodeParam("gb2312", "¡°Èð¶û¡±½àÑÀ¿¨"));
    	System.out.println(decodeParam("gb2312", "âå­ç¦âéª¨ç·ç¢"));
    }
}
