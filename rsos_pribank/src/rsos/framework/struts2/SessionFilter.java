package rsos.framework.struts2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import rsos.framework.constant.GlobalConstants;


public class SessionFilter extends OncePerRequestFilter {
	
	private static Log log = LogFactory.getLog(SessionFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String[] unfilteredURIs = new String[]{"/login.jsp","/session.jsp","login.action"};			// 包含以下字符串的uri无需过滤
		String uri = request.getRequestURI();			// 当前请求uri
		boolean doFilter = true;
		for(String unfilteredURI:unfilteredURIs){
			if(uri.indexOf(unfilteredURI) != -1){			// 判断当前uri是否需要过滤
				doFilter = false;
				break;
			}
		}
	
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Cache-Control","no-store"); 
		response.setDateHeader("Expires", 0); 
		response.setHeader("Pragma","no-cache"); 

		if (doFilter) {									// 执行过滤说明该请求URI需要用户先登录
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute(GlobalConstants.USER_INFORMATION_KEY)==null) {						// 取不到用户说明未登录
				//如果是ajax请求响应头会有,x-requested-with				
				if (request.getHeader("X-Requested-With") != null  
	                && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
					//在响应头设置session状态 
					response.setHeader("sessionstatus", "timeout");
					PrintWriter printWriter = response.getWriter(); 
					printWriter.print("timeout"); 
					printWriter.flush(); 
					printWriter.close();
					return;
				}else{
					response.sendRedirect(request.getContextPath()+"/session.jsp");
					return;
				}
				
			} else {
				log.debug("已经登录url:"+uri);
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
