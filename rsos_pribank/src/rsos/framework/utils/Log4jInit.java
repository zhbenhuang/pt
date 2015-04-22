package rsos.framework.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class Log4jInit extends HttpServlet {

	/**
	 * 用于生产web工程的日志文件
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Log4jInit.class);
	
	public void destroy(){
		super.destroy();
	}
	
	public Log4jInit(){
		super();
	}
	
	public void init()throws ServletException{
		String rootPath = this.getServletContext().getRealPath("/");
		logger.info(rootPath);
		/*从web.xml配置读取，名字一定要和web.xml配置的一致*/
		String log4jPath = this.getServletConfig().getInitParameter("log4j");
		log4jPath = (log4jPath==null||"".equals(log4jPath))?rootPath:log4jPath;
		System.setProperty("log4j", log4jPath);
		super.init();
	}
}
