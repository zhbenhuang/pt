package rsos.framework.struts2;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharsetEncodingFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest)request;
        hrequest.setCharacterEncoding("UTF-8");
        HttpServletResponse sresponse = (HttpServletResponse) response;
        response.setCharacterEncoding("UTF-8");
        sresponse.addHeader("expires", "0");
        
        chain.doFilter(request, response);
    }

	public void destroy() {		
	}

	public void init(FilterConfig cfg) throws ServletException {		
	}

}