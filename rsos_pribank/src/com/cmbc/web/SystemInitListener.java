package com.cmbc.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cmbc.sa.bean.Role;
import com.cmbc.sa.dao.RoleDao;
/**
 * <p>Title: AppListener</p> 
 * <p>Copyright: www.grgbanking.com</p>
 * <p>@author huangzb create at 2014-7-14</p>
 *
 * <p>Description: </p> 
 */
public class SystemInitListener implements ServletContextListener {
	
	public static ApplicationContext appContext = null;
	
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		appContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		appContext = null;
	}
	
	
	public static List<Role> loadRoles(){
		RoleDao roleDao = (RoleDao)appContext.getBean("roleDao");
		List<Role> roles = roleDao.selectRoles();
		if(roles == null){
			return new ArrayList<Role>();
		}else{
			return roles;
		}
	}
}
