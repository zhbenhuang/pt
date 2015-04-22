package rsos.framework.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class EasyuiExceptionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actioninvocation) throws Exception {
		System.out.println("---拦截器开始运行---");		
		String ret = actioninvocation.invoke();
		System.out.println("---拦截器结束运行---");
		return ret;
	}

}
