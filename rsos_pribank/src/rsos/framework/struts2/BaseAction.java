package rsos.framework.struts2;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.Constants;
import rsos.framework.db.PageDto;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.StringUtil;

import com.cmbc.reportStatistic.excelTool.ExcelUtils;
import com.cmbc.reportStatistic.excelTool.JsGridReportBase;
import com.cmbc.reportStatistic.excelTool.TableData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	/* 以下为基于STRUTS2的JSON返回机制增加的配置*/
	protected EasyResult returnSuccessMessage(){
		return new EasyResult(Constants.RETCODE_00000,getText(Constants.RETCODE_00000));
	}
	protected EasyResult returnErrorMessage(String errorCode){
		return new EasyResult(errorCode,getText(errorCode));
	}
	protected EasyResult returnErrorMessage(AppException ex){
		return new EasyResult(ex.getId(),getText(ex.getId(), ex.getDetails()));
	}
	
	protected HttpServletRequest getHttpRequest(){
		return ServletActionContext.getRequest();
	}
	protected HttpServletResponse getHttpResponse(){
		return ServletActionContext.getResponse();
	}
	protected HttpSession getSession(){
		return getHttpRequest().getSession();
	}
	protected PageDto initPageParameters(){
		PageDto dto = new PageDto();
		String pagesize = getHttpRequest().getParameter("rows");
		String page = getHttpRequest().getParameter("page");
		if(StringUtil.isEmpty(pagesize)){
			dto.setPageSize(PageDto.PAGE_SIZE);
		}else{
			dto.setPageSize(Integer.parseInt(pagesize));
		}
		if(StringUtil.isEmpty(page)){
			dto.setPage(1);
		}else{
			dto.setPage(Integer.parseInt(page));
		}
        
        if (dto.getPage() < 1)
        	dto.setPage(1);
		if (dto.getPageSize() < 2)
			dto.setPageSize(PageDto.PAGE_SIZE);
		return dto;
	}
	
	protected String getParameter(String key){
		Map<String,Object> maps = (Map<String,Object>)ActionContext.getContext().getParameters();
		String[] values = (String[]) maps.get(key);
		return values!=null?values[0]:"";
	}
	
	@SuppressWarnings("unchecked")
	protected void printParameters(){
		Enumeration en=getHttpRequest().getParameterNames();
		System.out.println("--------------------------------");
		System.out.println("Parameters:");
		System.out.println("--------------------------------");
		while(en.hasMoreElements()){   
			String paramName=(String)en.nextElement();                       
			String[] values=getHttpRequest().getParameterValues(paramName);   
			
			for(int i=0;i<values.length;i++){   
				System.out.println("["+i+"] "+paramName+" = "+values[i]);  
			}			
		}
		System.out.println("--------------------------------");
	}
	
	protected void writeErrors(String key){
		try{
			getHttpResponse().setContentType("text/html;charset=utf-8");
			String message = this.getText(key);
			EasyResult r = new EasyResult(key,message);
			System.out.println("-----return message:"+r.toJson());
			getHttpResponse().getWriter().write(r.toJson());
			getHttpResponse().getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void writeErrors(AppException ex){		
		try{
			getHttpResponse().setContentType("text/html;charset=utf-8");
			String message = this.getText(ex.getId(), ex.getDetails());
			EasyResult r = new EasyResult(ex.getId(),message);
			System.out.println("-----return message:"+r.toJson());
			getHttpResponse().getWriter().write(r.toJson());
			getHttpResponse().getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void writeSuccess(String key){
		try{
			getHttpResponse().setContentType("text/html;charset=utf-8");
			String message = this.getText(key);
			EasyResult r = new EasyResult(key,message);
			System.out.println("-----return message:"+r.toJson());
			getHttpResponse().getWriter().write(r.toJson());
			getHttpResponse().getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void writeJsonSuccess(String jsonStr){
		try{
			getHttpResponse().setContentType("text/html;charset=utf-8");
			getHttpResponse().getWriter().write(jsonStr);
			System.out.println("-----return message:"+jsonStr);
			getHttpResponse().getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void addErrors(AppException ex){
		String message = this.getText(ex.getId(), ex.getDetails());
		addFieldError("message", message);
	}
	protected void addErrors(String messageKey){
		String message = this.getText(messageKey);
		addFieldError("message", message);
	}
	
	@SuppressWarnings("unchecked")
	protected void exportReport(List list,String title,String[] hearders,String[] fields) throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/msexcel;charset=UTF-8");
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
	    report.exportToExcel(title, "admin", td);
	}
	
	@SuppressWarnings("unchecked")
	protected void exportReport(List list,String title,String[] parentHearders,String[][] childHearders,String[] fields) throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/msexcel;charset=UTF-8");
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(parentHearders,childHearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
	    report.exportToExcel(title, "admin", td);
	}
	
	protected void exportReport(String title,TableData td)throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/msexcel;charset=UTF-8");
		JsGridReportBase report = new JsGridReportBase(request, response);
	    report.exportToExcel(title, "admin", td);
	}
	
	
	public Map<String, Object> setCondition(Map<String, Object> condition) {
		String currentTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD);
		String createUserId = getHttpRequest().getParameter("createUserId");					//发起人Id
		String topic = getHttpRequest().getParameter("topic");									//流程标题
		String processTypeName = getHttpRequest().getParameter("processTypeName");				//流程类型
		String status = getHttpRequest().getParameter("status");								//流程状态
		String preUserId = getHttpRequest().getParameter("preUserId");							//上一办理人
		
		//设置其他查询条件
		if(createUserId!=null&&createUserId.length()>0){
			condition.put("createUserId", createUserId);
		}
		if(topic!=null&&topic.length()>0){
			condition.put("topic", topic);
		}
		if(processTypeName!=null&&processTypeName.length()>0){
			condition.put("processTypeName", processTypeName);
		}
		if(status!=null&&status.length()>0){
			condition.put("processStatus", status);
		}
		if(preUserId!=null&&preUserId.length()>0){
			condition.put("preUserId", preUserId);
		}
		
		String startTime1 = getHttpRequest().getParameter("startTime1");				//发起时间---区间起点
		String startTime2 = getHttpRequest().getParameter("startTime2");				//发起时间---区间终点
								
		//设置启动时间查询的时间区间
		String[] startTime = new String[2];
		if((startTime1==null&&startTime2==null)||(startTime1!=null&&startTime1.length()==0&&startTime2!=null&&startTime2.length()==0)){//区间两端都为空
			int i=0;
		}else if(startTime1!=null&&startTime1.length()>0&&startTime2!=null&&startTime2.length()>0){	//区间两端都不为空
			startTime[0] = startTime1;
			startTime[1] = startTime2;
			condition.put("startTime", startTime);
		}else{
			if(startTime1!=null&&startTime1.length()>0){
				startTime[0] = startTime1;
			}else{
				startTime[0] = "";
			}
			if(startTime2!=null&&startTime2.length()>0){
				startTime[1] = startTime2;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				startTime[1] = currentTime;
			}
			condition.put("startTime", startTime);
		}
		
		//设置完成时间查询的时间区间
		String finishedTime1 = getHttpRequest().getParameter("finishedTime1");				//办结时间---区间起点
		String finishedTime2 = getHttpRequest().getParameter("finishedTime2");				//办结时间---区间终点
		String[] finishedTime = new String[2];
		if((finishedTime1==null&&finishedTime2==null)||(finishedTime1!=null&&finishedTime1.length()==0&&finishedTime2!=null&&finishedTime2.length()==0)){
			int i=0;
		}else if(finishedTime1!=null&&finishedTime1.length()>0&&finishedTime2!=null&&finishedTime2.length()>0){
			finishedTime[0] = finishedTime1;
			finishedTime[1] = finishedTime2;
			condition.put("completeTime", finishedTime);
		}else{
			if(finishedTime1!=null&&finishedTime1.length()>0){
				finishedTime[0] = finishedTime1;
			}else{
				finishedTime[0] = "";
			}
			if(finishedTime2!=null&&finishedTime2.length()>0){
				finishedTime[1] = finishedTime2;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				finishedTime[1] = currentTime;
			}
			condition.put("completeTime", finishedTime);
		}
		
		//设置创建时间查询的时间区间
		String createTime1 = getHttpRequest().getParameter("createTime1");				//创建时间---区间起点
		String createTime2 = getHttpRequest().getParameter("createTime2");				//创建时间---区间终点
		String[] createTime = new String[2];
		if((createTime1==null&&createTime2==null)||(createTime1!=null&&createTime1.length()==0&&createTime2!=null&&createTime2.length()==0)){
			int i=0;
		}else if(createTime1!=null&&createTime1.length()>0&&createTime2!=null&&createTime2.length()>0){
			createTime[0] = createTime1;
			createTime[1] = createTime2;
			condition.put("createTime", createTime);
		}else{
			if(createTime1!=null&&createTime1.length()>0){
				createTime[0] = createTime1;
			}else{
				createTime[0] = "";
			}
			if(createTime2!=null&&createTime2.length()>0){
				createTime[1] = createTime2;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				createTime[1] = currentTime;
			}
			condition.put("createTime", createTime);
		}
		
		
		//设置到达时间查询的时间区间
		String arriveTime1 = getHttpRequest().getParameter("arriveTime1");				//任务到达时间---区间起点
		String arriveTime2 = getHttpRequest().getParameter("arriveTime2");				//任务到达时间---区间终点
		String[] arriveTime = new String[2];
		if((arriveTime1==null&&arriveTime2==null)||(arriveTime1!=null&&arriveTime1.length()==0&&arriveTime2!=null&&arriveTime2.length()==0)){
			int i=0;
		}else if(arriveTime1!=null&&arriveTime1.length()>0&&arriveTime2!=null&&arriveTime2.length()>0){
			arriveTime[0] = arriveTime1;
			arriveTime[1] = arriveTime2;
			condition.put("arriveTime", arriveTime);
		}else{
			if(arriveTime1!=null&&arriveTime1.length()>0){
				arriveTime[0] = arriveTime1;
			}else{
				arriveTime[0] = "";
			}
			if(arriveTime2!=null&&arriveTime2.length()>0){
				arriveTime[1] = arriveTime[0];
			}else{		//如果时间查询的终点未填，则默认为当前时间
				arriveTime[1] = currentTime;
			}
			condition.put("arriveTime", arriveTime);
		}
		//设置签收时间的时间查询区间
		
		String signTime1 = getHttpRequest().getParameter("signTime1");				//任务签收时间---区间起点
		String signTime2 = getHttpRequest().getParameter("signTime2");				//任务签收时间---区间终点					
		String[] signTime = new String[2];
		if((signTime1==null&&signTime2==null)||signTime1!=null&&signTime1.length()==0&&signTime2!=null&&signTime2.length()==0){
			int i=0;
		}else if(signTime1!=null&&signTime1.length()>0&&signTime2!=null&&signTime2.length()>0){
			signTime[0] = signTime1;
			signTime[1] = signTime2;
			condition.put("signTime", signTime);
		}else{
			if(signTime1!=null&&signTime1.length()>0){
				signTime[0] = signTime1;
			}else{
				signTime[0] = "";
			}
			if(signTime2!=null&&signTime2.length()>0){
				signTime[1] = signTime2;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				signTime[1] = currentTime;
			}
			condition.put("signTime", signTime);
		}
		
		//设置任务完成时间的时间查询区间
		String completeTime1 = getHttpRequest().getParameter("completeTime1");				//任务完成时间---区间起点
		String completeTime2 = getHttpRequest().getParameter("completeTime2");				//任务完成时间---区间终点				
		String[] completeTime = new String[2];
		if((completeTime1==null&&completeTime2==null)||(completeTime1!=null&&completeTime1.length()==0&&completeTime2!=null&&completeTime2.length()==0)){
			int i=0;
		}else if(completeTime1!=null&&completeTime1.length()>0&&completeTime2!=null&&completeTime2.length()>0){
			completeTime[0] = completeTime1;
			completeTime[1] = completeTime2;
			condition.put("completeTime", completeTime);
		}else{
			if(completeTime1!=null&&completeTime1.length()>0){
			completeTime[0] = completeTime1;
			}else{
				completeTime[0] = "";
			}
			if(completeTime2!=null&&completeTime2.length()>0){
				completeTime[1] = completeTime2;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				completeTime[1] = currentTime;
			}
			condition.put("completeTime", completeTime);
		}
		
		return condition;
	}
	
	public Map<String, Object> setConditionTwo(Map<String, Object> condition) {
		String currentTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD);
		
		String clientId = getHttpRequest().getParameter("clientId");
		String clientName = getHttpRequest().getParameter("clientName");									
		String agentName = getHttpRequest().getParameter("agentName");
		String createUserName = getHttpRequest().getParameter("createUserName");
		String applyDepartmentName = getHttpRequest().getParameter("applyDepartmentName");
		String department = getHttpRequest().getParameter("department");
		
		//设置其他查询条件
		if(clientId!=null&&clientId.length()>0){
			condition.put("clientId", clientId);
		}
		if(clientName!=null&&clientName.length()>0){
			condition.put("clientName", clientName);
		}
		if(agentName!=null&&agentName.length()>0){
			condition.put("agentName", agentName);
		}
		if(createUserName!=null&&createUserName.length()>0){
			condition.put("createUserName", createUserName);
		}
		if(applyDepartmentName!=null&&applyDepartmentName.length()>0){
			condition.put("applyDepartmentName", applyDepartmentName);
		}
		if(department!=null&&department.length()>0){
			condition.put("department", department);
		}
		
		String applyBegin = getHttpRequest().getParameter("applyBegin");			//发起时间---区间起点
		String applyEnd = getHttpRequest().getParameter("applyEnd");				//发起时间---区间终点
								
		//设置启动时间查询的时间区间
		String[] startTime = new String[2];
		if((applyBegin==null&&applyEnd==null)||(applyBegin!=null&&applyBegin.length()==0&&applyEnd!=null&&applyEnd.length()==0)){//区间两端都为空
			int i=0;
		}else if(applyBegin!=null&&applyBegin.length()>0&&applyEnd!=null&&applyEnd.length()>0){	//区间两端都不为空
			startTime[0] = applyBegin;
			startTime[1] = applyEnd;
			condition.put("startTime", startTime);
		}else{
			if(applyBegin!=null&&applyBegin.length()>0){
				startTime[0] = applyBegin;
			}else{
				startTime[0] = "";
			}
			if(applyEnd!=null&&applyEnd.length()>0){
				startTime[1] = applyEnd;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				startTime[1] = currentTime;
			}
			condition.put("startTime", startTime);
		}
		
		//设置完成时间查询的时间区间
		String completeBegin = getHttpRequest().getParameter("completeBegin");				//办结时间---区间起点
		String completeEnd = getHttpRequest().getParameter("completeEnd");					//办结时间---区间终点
		String[] finishedTime = new String[2];
		if((completeBegin==null&&completeEnd==null)||(completeBegin!=null&&completeBegin.length()==0&&completeEnd!=null&&completeEnd.length()==0)){
			int i=0;
		}else if(completeBegin!=null&&completeBegin.length()>0&&completeEnd!=null&&completeEnd.length()>0){
			finishedTime[0] = completeBegin;
			finishedTime[1] = completeEnd;
			condition.put("completeTime", finishedTime);
		}else{
			if(completeBegin!=null&&completeBegin.length()>0){
				finishedTime[0] = completeBegin;
			}else{
				finishedTime[0] = "";
			}
			if(completeEnd!=null&&completeEnd.length()>0){
				finishedTime[1] = completeEnd;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				finishedTime[1] = currentTime;
			}
			condition.put("completeTime", finishedTime);
		}
		return condition;
	}
	public Map<String, Object> setConditionThree(Map<String, Object> condition) {
		String currentTime = CalendarUtil.formatDatetime(new Date(), CalendarUtil.DATEFORMAT_YYYYMMDD);
		Date currentDate = CalendarUtil.getCurrentDate();
		Date dateCondition = CalendarUtil.add(currentDate, CalendarUtil.DAY, -30);
		String timeCondition = CalendarUtil.formatDatetime(dateCondition, CalendarUtil.DATEFORMAT_YYYYMMDD);
		
		String createUserId = getHttpRequest().getParameter("createUserId");
		String status = getHttpRequest().getParameter("status");									
		String topic = getHttpRequest().getParameter("topic");											
		
		//设置其他查询条件
		if(createUserId!=null&&createUserId.length()>0){
			condition.put("createUserId", createUserId);
		}
		if(status!=null&&status.length()>0){
			condition.put("reConStatus", status);
		}
		if(topic!=null&&topic.length()>0){
			condition.put("topic", topic);
		}
		
		//设置完成时间查询的时间区间
		String finishedTime1 = getHttpRequest().getParameter("finishedTime1");				//办结时间---区间起点
		String finishedTime2 = getHttpRequest().getParameter("finishedTime2");				//办结时间---区间终点
		String[] finishedTime = new String[2];
		if((finishedTime1==null&&finishedTime2==null)||(finishedTime1!=null&&finishedTime1.length()==0&&finishedTime2!=null&&finishedTime2.length()==0)){
			finishedTime[0] = "";
			finishedTime[1] = timeCondition;
			condition.put("completeTime", finishedTime);
		}else if(finishedTime1!=null&&finishedTime1.length()>0&&finishedTime2!=null&&finishedTime2.length()>0){
			finishedTime[0] = finishedTime1;
			finishedTime[1] = finishedTime2;
			condition.put("completeTime", finishedTime);
		}else{
			if(finishedTime1!=null&&finishedTime1.length()>0){
				finishedTime[0] = finishedTime1;
			}else{
				finishedTime[0] = "";
			}
			if(finishedTime2!=null&&finishedTime2.length()>0){
				finishedTime[1] = finishedTime2;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				finishedTime[1] = currentTime;
			}
			condition.put("completeTime", finishedTime);
		}
		String applyBegin = getHttpRequest().getParameter("applyBegin");			//发起时间---区间起点
		String applyEnd = getHttpRequest().getParameter("applyEnd");				//发起时间---区间终点
								
		//设置启动时间查询的时间区间
		String[] startTime = new String[2];
		if((applyBegin==null&&applyEnd==null)||(applyBegin!=null&&applyBegin.length()==0&&applyEnd!=null&&applyEnd.length()==0)){//区间两端都为空
			int i=0;
		}else if(applyBegin!=null&&applyBegin.length()>0&&applyEnd!=null&&applyEnd.length()>0){	//区间两端都不为空
			startTime[0] = applyBegin;
			startTime[1] = applyEnd;
			condition.put("startTime", startTime);
		}else{
			if(applyBegin!=null&&applyBegin.length()>0){
				startTime[0] = applyBegin;
			}else{
				startTime[0] = "";
			}
			if(applyEnd!=null&&applyEnd.length()>0){
				startTime[1] = applyEnd;
			}else{		//如果时间查询的终点未填，则默认为当前时间
				startTime[1] = currentTime;
			}
			condition.put("startTime", startTime);
		}
		return condition;
	}
	
	public Map<String, Object> setConditionFour(Map<String, Object> condition) {
		String productCode = getHttpRequest().getParameter("productCode");
		String customeId = getHttpRequest().getParameter("customeId");									
		String productName = getHttpRequest().getParameter("productName");				
		String customeName = getHttpRequest().getParameter("customeName");	
		String redempStatus = getHttpRequest().getParameter("redempStatus");
		
		String openDayBegin = getHttpRequest().getParameter("openDayBegin");		
		String openDayEnd = getHttpRequest().getParameter("openDayEnd");
		String confirmBegin = getHttpRequest().getParameter("confirmBegin");		
		String confirmEnd = getHttpRequest().getParameter("confirmEnd");
		String redeemBegin = getHttpRequest().getParameter("redeemBegin");		
		String redeemEnd = getHttpRequest().getParameter("redeemEnd");
		
		//设置其他查询条件
		if(productCode!=null&&productCode.length()>0){
			condition.put("productCode", productCode);
		}
		if(customeId!=null&&customeId.length()>0){
			condition.put("customeId", customeId);
		}
		if(productName!=null&&productName.length()>0){
			condition.put("productName", productName);
		}
		if(customeName!=null&&customeName.length()>0){
			condition.put("customeName", customeName);
		}
		if(redempStatus!=null&&redempStatus.length()>0){
			condition.put("redempStatus", redempStatus);
		}
		if(openDayBegin!=null&&openDayBegin.length()>0){
			condition.put("openDayBegin", openDayBegin);
		}
		if(openDayEnd!=null&&openDayEnd.length()>0){
			condition.put("openDayEnd", openDayEnd);
		}
		if(confirmBegin!=null&&confirmBegin.length()>0){
			condition.put("confirmBegin", confirmBegin);
		}
		if(confirmEnd!=null&&confirmEnd.length()>0){
			condition.put("confirmEnd", confirmEnd);
		}
		if(redeemBegin!=null&&redeemBegin.length()>0){
			condition.put("redeemBegin", redeemBegin);
		}
		if(redeemEnd!=null&&redeemEnd.length()>0){
			condition.put("redeemEnd", redeemEnd);
		}
		
		String completeMonthBegin = ServletActionContext.getRequest().getParameter("completeMonthBegin");
		String completeMonthEnd = ServletActionContext.getRequest().getParameter("completeMonthEnd");
		String departmentName = ServletActionContext.getRequest().getParameter("departmentName");
		if(completeMonthBegin!=null&&completeMonthBegin.length()>0){
			condition.put("completeMonthBegin", completeMonthBegin);
		}
		if(completeMonthEnd!=null&&completeMonthEnd.length()>0){
			condition.put("completeMonthEnd", completeMonthEnd);
		}
		if(departmentName!=null&&departmentName.length()>0){
			condition.put("departmentName", departmentName);
		}
		
		return condition;
	}
}
