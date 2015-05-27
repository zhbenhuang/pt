package rsos.framework.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author 80071236
 * 
 */
public class CalendarUtil {
	public static final String REGEX_VALID_HHMM = "^([0-1]\\d|[2][0-3]|\\d)\\:([0-5]\\d)$";
	/**
	 * Months define
	 */
	public static final String[] GC_MONTHS = new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	
	/**
	 * Day of week define 
	 */
	public static final String[] GC_DAYS_OF_WEEK = new String[]{"SUN","MON","TUE","WED","THU","FRI","SAT"};    
	
	/**
	 * Day of ISO week 
	 */
	public static final String[] GC_DAYS_OF_ISO_WEEK = new String[]{"MON","TUE","WED","THU","FRI","SAT","SUN"};
	
	public static final String UP_ITEM_DATEFORMAT_FULL_1 = "yyyy-MM-dd hh:mm:ss";
	public static final String UP_ITEM_DATEFORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String UP_ITEM_DATEFORMAT_DATE = "yyyy-MM-dd";
//	public static final String UP_ITEM_DATEFORMAT_DATE_1 = "yyyy/M/d";
//	public static final String UP_ITEM_DATEFORMAT_DATE_2 = "yyyy/M/d HH:mm:ss";
	public static final String DATEFORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATEFORMAT_YYYYMMDD_HHmmss = "yyyyMMdd HH:mm:ss";
	public static final String UP_ITEM_DATEFORMAT_TIME = "hh:mm:ss";
	public static final String UP_ITEM_DATEFORMAT_HALF_TIME = "yyyy-MM-dd HH:mm";
	public static final String DATEFORMAT_TIME = "yyyyMMdd HH:mm:ss";
		
	/** 时间日期常量 */
	public static final int DAY = 0;
	
	/** 小时 */
	public static final int HOUR = 1;
	
	/** 分钟 */
	public static final int MINUTE = 2;
	
	/** 周 */
	public static final int WEEK = 3;
	
	/** 月份 */
	public static final int MONTH = 4;
	
	/** 年 */
	public static final int YEAR = 5;

    private CalendarUtil() {
    }
    
    /**
     * 根据时间格式把Date转换成String
     * @param date 日期
     * @param format 格式，e.g. "yyyy-MM-dd HH:mm:ss"
     * @return 以格式提供日期时间的字符串
     */
    public static String transformToFormattedString(Date date, String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	return sdf.format(date);
    }
    
    /**
     * 字符串日期由一种格式T转换为另一种格式W,并且字符串的格式要和T相同
     * @param dateStr
     * @param oldFormat
     * @param newFormat
     * @return
     * @throws ParseException
     */
    public static String transformStringToFormattedString(String dateStr,String oldFormat,String newFormat) throws ParseException{
    	Date date = new SimpleDateFormat(oldFormat).parse(dateStr);
    	String newDateStr = new SimpleDateFormat(newFormat).format(date);
    	return newDateStr;
    }
    
    /**
     * 字符串日期由一种格式T转换为日期格式,并且字符串的格式要和T相同
     * @param dateStr
     * @param oldFormat
     * @return
     * @throws ParseException
     */
    public static Date transformStringToDate(String dateStr,String oldFormat) throws ParseException{
    	Date date = new SimpleDateFormat(oldFormat).parse(dateStr);
    	return date;
    }

    /**
     * 将Timestamp转换为Date
     * @param ts
     * @return
     */
    public static Date transformTimestampToDate(Timestamp ts){
        if(ts==null) return null;
        return new java.util.Date(ts.getTime());
    }
    
    /**
     * 比较两个日期大小
     * @param date1
     * @param date2
     * @return 1:date1>date2;
     *        -1:date1<date2;
     *         0:date1 or date2 =null
     */
    public static int compareDate(Date date1,Date date2){
        if(date1==null||date2==null)
            return 0;
        if(date1.after(date2))
            return 1;
        if(date2.after(date1))
            return -1;
        return 0;
    }
    
    /**
     * 获取两个日期的时间差
     * @param dateFrom
     * @param dateTo
     * @return
     * @throws Exception
     */
    public static String getDatetimeDif(Date dateFrom,Date dateTo) throws Exception {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(dateTo.getTime() - dateFrom.getTime());
        int yy = c.get(java.util.Calendar.YEAR) - 1970;
        int mm = c.get(java.util.Calendar.MONTH);
        int dd = c.get(java.util.Calendar.DAY_OF_MONTH) - 1;
        int hh = c.get(java.util.Calendar.HOUR_OF_DAY);
        int min= c.get(java.util.Calendar.MINUTE);
        System.out.println("YEARS:"+yy+":"+mm+":"+dd+":"+hh+":"+min); 
        return yy+":"+mm+":"+dd+":"+hh+":"+min;
    }   

    
    /**
     * 将页面的日期和时间字符串合成一个Date
     * @param date1
     * @param timeStr
     * @return
     */
    public static Date transformStrToDate(Date date1,String timeStr){
        try {
            if(date1==null) return null;
            SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
            String dateStr=df1.format(date1);
            if(timeStr==null||timeStr.equals("")) timeStr="00:00";
            SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate=df2.parse(dateStr+" "+timeStr);
            return newDate;
        } catch (ParseException e) {
            return null;
        }
    }
    /**
     * 获取datetime类型日期的时间
     * @param date datetime类型
     * @return 时间字符串
     */
    public static String getHHMM(Date date) {
    	if (date == null){
    		return null;
    	}
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String hh = Integer.valueOf(c.get(Calendar.HOUR_OF_DAY)).toString();
        hh = hh.length() < 2 ? "0" + hh : hh;
        String mm = Integer.valueOf(c.get(Calendar.MINUTE)).toString();
        mm = mm.length() < 2 ? "0" + mm : mm;
        return hh + ":" + mm;
    }
    
    /**
     * get current system date time
     * 
     * @return
     */
    public static Date getSystemDatetime() {
        return Calendar.getInstance().getTime();

    }
    
    /**
     * get mill time.
     * @return
     */
    public static String getDateTimes() {  
    	Calendar calendar = Calendar.getInstance();  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss-sss");  
    	String result = sdf.format(calendar.getTime());  
    	return result; 
    }

    /**
     * 获取以curDate为起点在dayRange范围内的另一个时间点
     * 
     * @param curDate
     * @param dayRange:数值为正，表示从curDate往后推算日期；数值为负，表示从curDate往前推算日期
     * @return
     */
    public static Date getRangeDate(Date curDate, int dayRange) {
        /*if (dayRange > 0)
            dayRange = dayRange - 1;
        if (dayRange < 0)
            dayRange = dayRange + 1;*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        //System.out.println(curDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + dayRange);
        Date ret = calendar.getTime();
        //System.out.println(ret);
        return ret;
    }
    
    /**
     * format system date time by date time format setting
     * 
     * @param date
     * @param format:
     *            like "yyyy-MM-dd hh:mm:ss"
     * @return dateStr
     */
    public static String formatDatetime(Date date, String format) {
        if(date==null) return null;
        String dateStr = new SimpleDateFormat(format).format(date);
        return dateStr;

    }

    public static Date string2Datetime(String date, String format) {
    	if(date==null) return null;
        Date d = null;
        try {
        	date = date.trim();
            d = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
        return d;

    }
    /**
     * translate a string into system date time //String to Calendar datetime
     * 
     * @param str
     * @return date
     * @throws 
     */
    public static Date string2Datetime(String str) {
        if(str==null) return null;
        Date date = null;
        try {
            str = str.trim();
            int length = str.length();
            if (str.contains("-")) {
                if (length <= 10)
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                else /*if (length == 19)*/
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str);
            } else if (str.contains("/")) {
                if (length <= 10)
                    date = new SimpleDateFormat("yyyy/MM/dd").parse(str);
                else /*if (length == 19)*/
                    date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(str);
                // ... here's other situation such as mm/dd/yyy
            } else{
            	if (length==8)
            		date = new SimpleDateFormat("yyyyMMdd").parse(str);
            }
        } catch (ParseException e) {
            return null;
        }
        return date;

    }
    
    /**
     * get date time format setting
     * 
     * @return
     */
//    public static String getDatetimeFormatSetting(String pattern) {
//
//        return "dd/MM/yyyy";
//
//    }

    /**
     * translate convertible date into a calendar date such as: Yesterday,
     * Today, Last day of the Month, First day of the Month, Last Month, Last
     * Year
     * 
     * @return date
     */
    public static Date translate2CalendarDate(String convertDate) {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        convertDate = convertDate.trim();
        Date today = c.getTime();

        // 昨天
        if (convertDate.equalsIgnoreCase("Yesterday")) {
            date.setTime(today.getTime() - 60 * 24 * 60 * 1000);
        } else if (convertDate.equalsIgnoreCase("Today"))
            date = today;
        else if (convertDate.equalsIgnoreCase("Last day of the Month")) {
            c.set(Calendar.DAY_OF_MONTH, c
                    .getActualMaximum(Calendar.DAY_OF_MONTH));
            date = c.getTime();
        } else if (convertDate.equalsIgnoreCase("First day of the Month")) {
            c.set(Calendar.DAY_OF_MONTH, c
                    .getActualMinimum(Calendar.DAY_OF_MONTH));
            date = c.getTime();
        } else if (convertDate.equalsIgnoreCase("Last Month")) {
            int month = c.get(Calendar.MONTH);
            c.set(Calendar.MONTH, month - 1);
            date = c.getTime();
        } else if (convertDate.equalsIgnoreCase("Last Year")) {
            int year = c.get(Calendar.YEAR);
            c.set(Calendar.YEAR, year - 1);
            date = c.getTime();
        }

        return date;
    }
    
    /**
	 * Adds a number of days to a date
	 * 
	 * @param date
	 *            the start date
	 * @param amount
	 *            the number of days
	 * @return
	 */
	public static Date addDays(Date date, Long amount) {
		//modified by cary
		return DateUtils.addDays(date, amount.intValue());
	}
	
	/***
	 * 增加（或减少）相应的时间(分钟、小时、日、周、月、年)数值到指定日期
	 * @param aDate 指定的日期
	 * @param aType 指定的数值类型（可从常量读取）
	 * @param aMount 增加的数值（减少用负数）
	 * @return 已增加数值的日期对象
	 */
	public static Date add(Date aDate, int aType, int aMount)
	{
		switch(aType)
		{
		case CalendarUtil.DAY:
			return DateUtils.addDays(aDate, aMount);
		case CalendarUtil.HOUR:
			return DateUtils.addHours(aDate, aMount);
		case CalendarUtil.WEEK:
			return DateUtils.addWeeks(aDate, aMount);
		case CalendarUtil.MINUTE:
			return DateUtils.addMinutes(aDate, aMount);
		case CalendarUtil.MONTH:
			return DateUtils.addMonths(aDate, aMount);
		case CalendarUtil.YEAR:
			return DateUtils.addYears(aDate, aMount);	
		default:
			return aDate;
		}
	}
    
	/**
	 * 获取当前的日期（不包括时间部分）
	 * @return
	 */
	public static Date getCurrentDate() {
		return getDateOnly(new Date());
	}
	
	/**
	 * 获取时间的日期部分
	 * @param d - 日期
	 * @return
	 */
	public static Date getDateOnly(Date d) {
		if (d == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("current date: " + CalendarUtil.getCurrentDate());
//		 Date first= new SimpleDateFormat("yyyy-MM-dd").parse("2007-01-10");
//         Date second= new SimpleDateFormat("yyyy-MM-dd").parse("2007-01-20");
//		System.out.println(getDaysBetweenTwoDates(first, second));
//	}
	
	public static Long convertDateString2Int(String weekDate){
		Long ret = null;
		if(null == weekDate || "".equals(weekDate)){
			ret = null;
		}else if("MON".equals(weekDate.toUpperCase())){
			ret = new Long(1);
		}else if("TUE".equals(weekDate.toUpperCase())){
			ret = new Long(2);
		}else if("WED".equals(weekDate.toUpperCase())){
			ret = new Long(3);
		}else if("THR".equals(weekDate.toUpperCase())){
			ret = new Long(4);
		}else if("FRI".equals(weekDate.toUpperCase())){
			ret = new Long(5);
		}else if("SAT".equals(weekDate.toUpperCase())){
			ret = new Long(6);
		}else if("SUN".equals(weekDate.toUpperCase())){
			ret = new Long(7);
		}
	    return ret;		
	}
	
	public static String getWeekDayName(String weekDay){
		if(weekDay!=null){
			switch(Integer.valueOf(weekDay))
			{
			case 1:
				return "星期天";
			case 2:
				return "星期一";
			case 3:
				return "星期二";
			case 4:
				return "星期三";
			case 5:
				return "星期四";
			case 6:
				return "星期五";
			case 7:
				return "星期六";
			default:
				return "";
			}
		}else{
			return "";
		}
	}
	
	public static String getQuarterName(String quarter){
		if(quarter!=null){
			if(quarter.length()<5){
				return "";
			}else{
				return quarter.substring(0,4)+"年"+quarter.substring(4)+"季度";
			}
		}else{
			return "";
		}
	}
	
	public static String getMonthName(String month){
		if(month!=null){
			if(month.length()<6){
				return "";
			}else{
				return month.substring(0,4)+"年"+month.substring(4)+"月";
			}
		}else{
			return "";
		}
	}
	
	public static String getWeekName(String week){
		if(week!=null){
			return "第"+week+"周";
		}else{
			return "";
		}
	}
	
	 /**
     * 检查输入的时间是否满足HH:mm的格式
     * @param time - example 12:30
     * @return
     */
    public static boolean isValidHHmmStr(String time){
       if(StringUtils.isNotBlank(time)){
           return Pattern.compile(REGEX_VALID_HHMM).matcher(time).find();
       }
       return false;
    }   

    /**
     * 将HH:mm格式的时间字符串转换为Double
     * 如01:30->90.00D
     * @param timeStr - example 01:30
     * @return
     */
    public static Double convertHHmmStrToDouble(String timeStr){
       if(isValidHHmmStr(timeStr)){
           String[] str = StringUtils.split(timeStr,":");
           return (new Double(str[0]) * 60 + (new Double(str[1])));
       }
       return null;
    }

    /**
     * 将Double转换为HH:mm格式的字符串
     * 如90.00D->01:30
     * @param d - example 12.3
     * @return
     */
    public static String convertDoubleToHHmmStr(Double d){
       if(d!=null && d.doubleValue()>0){
           int minutes =  Math.abs(d.intValue());
           int hour = minutes / 60;
           int minute = minutes % 60;
           String hh = hour >= 10 ? "" + hour : "0" + hour;
           String mm = minute >= 10 ? "" + minute : "0" + minute;
           return hh + ":" + mm;
       }
       return null;
    }
    
    /**
     * 计算两个日期之间的天数
     * 如：first = '2007-01-23' ,second = '2007-01-20'
     * 返回天数为 3
     * @param first
     * @param second
     * @return long 类型的天数
     */
    public static long getDaysBetweenTwoDates(Date first ,Date second ){
    	long days = 0;
    	if(first != null && second != null ){
    		long times = first.getTime() - second.getTime();
        	days = times/1000/60/60/24 ;
    	}
    	if(days < 0 ){
    		days = -days;
    	}
    	return days;
    }
    
    public static long getDaysBetweenTwoDates(String firstDate ,String secondDate ){
    	long days = 0;
    	Date first = string2Datetime(firstDate);
    	Date second = string2Datetime(secondDate);
    	if(first != null && second != null ){
    		long times = first.getTime() - second.getTime();
        	days = times/1000/60/60/24 ;
    	}
    	if(days < 0 ){
    		days = -days;
    	}
    	return days;
    }
    /**
     * 返回日期所在的年
     * 
     * @param date
     * 
     * @return int类型的年
     */
    public static int getYearOfDate(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.YEAR);
    }
    /**
     * 返回日期所在的月
     * 
     * @param date
     * 
     * @return int类型的月
     */
    public static int getMonthOfDate(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.MONTH);
    }
    
    /**   
     *   
     * 1 第一季度  2 第二季度 3 第三季度 4 第四季度   
     *   
     * @param date   
     * @return   
     */   
    public static int getSeasonOfDate(Date date) {    
   
        int season = 0;    
   
        Calendar c = Calendar.getInstance();    
        c.setTime(date);    
        int month = c.get(Calendar.MONTH);    
        switch (month) {    
            case Calendar.JANUARY:    
            case Calendar.FEBRUARY:    
            case Calendar.MARCH:    
                season =  1;    
                break;    
            case Calendar.APRIL:    
            case Calendar.MAY:    
            case Calendar.JUNE:    
                season =  2;    
                break;    
            case Calendar.JULY:    
            case Calendar.AUGUST:    
            case Calendar.SEPTEMBER:    
                season =  3;    
                break;    
            case Calendar.OCTOBER:    
            case Calendar.NOVEMBER:    
            case Calendar.DECEMBER:    
                season =  4;    
                break;    
            default:    
                break;    
        }    
        return season;    
    }
    
    /**   
     *   
     * 上半年，下半年  
     *   
     * @param date   
     * @return   
     */   
    public static String getHalfYearOfDate(Date date) {    
   
        String season = "";    
   
        Calendar c = Calendar.getInstance();    
        c.setTime(date);    
        int month = c.get(Calendar.MONTH);    
        switch (month) {    
            case Calendar.JANUARY:    
            case Calendar.FEBRUARY:    
            case Calendar.MARCH:
            case Calendar.APRIL:    
            case Calendar.MAY:    
            case Calendar.JUNE:    
                season = "上半年";    
                break;    
            case Calendar.JULY:    
            case Calendar.AUGUST:    
            case Calendar.SEPTEMBER:
            case Calendar.OCTOBER:    
            case Calendar.NOVEMBER:    
            case Calendar.DECEMBER:    
                season = "下半年";    
                break;    
            default:    
                break;    
        }    
        return season;    
    }

    /**
     * 返回参数年月日所对应的日期
     * 
     * @param year
     * @param month
     * @param day
     * 
     * @return date
     */
    public static Date getDate(int year, int month, int day) {
    	Calendar c = Calendar.getInstance();
    	c.set(year, month, day);
    	return c.getTime();
    }
    
    /**
     * 根据月份code返回对应的Long
     * 如"JAN"返回1L,"DEC",返回12L
     * @param month - example: JAN
     * @return
     */
    public static Long getMonthNum(String month){
    	if(StringUtils.isNotBlank(month) && ArrayUtils.contains(GC_MONTHS, month.toUpperCase())){
    		return Long.valueOf(ArrayUtils.indexOf(GC_MONTHS, month.toUpperCase()) + 1);
    	}
    	return null;
    }
    
    /**
     * 根据月份的Long值返回对应的Code
     * 如1L返回"JAN",12L返回"DEC"
     * @param month - example: 1L
     * @return
     */
    public static String getMonthCd(Long month){
    	if(month!=null && month.longValue() >=1 && month.longValue()<=12){
    		return GC_MONTHS[month.intValue()-1];
    	}
    	return null;
    }
    
    /**
     * 根据星期几的Code值返回对应的Long
     * 如"SUN"返回1L,"SAT"返回7
     * @param dayOfWeek - example: SUN
     * @return
     */
    public static Long getDayOfWeekNum(String dayOfWeek){
    	if(StringUtils.isNotBlank(dayOfWeek) && ArrayUtils.contains(GC_DAYS_OF_WEEK, dayOfWeek.toUpperCase())){
    		return Long.valueOf(ArrayUtils.indexOf(GC_DAYS_OF_WEEK, dayOfWeek.toUpperCase()) + 1);
    	}
    	return null;
    }
    
    /**
     * 根据星期几的Long值返回对应的Code
     * 如1L返回"SUN",7L返回"SAT"
     * @param dayOfWeek - example: 1L
     * @return
     */
    public static String getDayOfWeekCd(Long dayOfWeek){
    	if(dayOfWeek!=null && dayOfWeek.longValue() >=1 && dayOfWeek.longValue()<=7){
    		return GC_DAYS_OF_WEEK[dayOfWeek.intValue()-1];
    	}
    	return null;
    }
    
    
    /**
     * ISO week
     * 根据星期几的Code值返回对应的Long
     * 如"MON"返回1L,"SUN"返回7
     * @param dayOfWeek - example: SUN
     * @return
     */
    public static Long getDayOfISOWeekNum(String dayOfWeek){
    	if(StringUtils.isNotBlank(dayOfWeek) && ArrayUtils.contains(GC_DAYS_OF_ISO_WEEK, dayOfWeek.toUpperCase())){
    		return Long.valueOf(ArrayUtils.indexOf(GC_DAYS_OF_ISO_WEEK, dayOfWeek.toUpperCase()) + 1);
    	}
    	return null;
    }
    
    /**
     * ISO week
     * 根据星期几的Long值返回对应的Code
     * 如1L返回"MON",7L返回"SUN"
     * @param dayOfWeek - example: 1L
     * @return
     */
    public static String getDayOfISOWeekCd(Long dayOfWeek){
    	if(dayOfWeek!=null && dayOfWeek.longValue() >=1 && dayOfWeek.longValue()<=7){
    		return GC_DAYS_OF_ISO_WEEK[dayOfWeek.intValue()-1];
    	}
    	return null;
    }
    
    
    /**
     * get the date which is in the same month of "date" parameter, 
     * @param date
     * @param dateNo
     * @return
     */
    public static Date getDateOfMonth(Date date, int dateNo){
    	Calendar gc=Calendar.getInstance();
    	gc.setTime( date );
    	gc.set( Calendar.DAY_OF_MONTH, dateNo );
    	return gc.getTime();
    }
    
   
    /**
     * get the date which is in the same week of "date" parameter, 
     * @param date
     * @param dateNo
     * @return
     */
    public static Date getDateOfWeek(Date date, int dateNo){
    	Calendar gc=Calendar.getInstance();
    	gc.setTime( date );
    	gc.set( Calendar.DAY_OF_WEEK, dateNo);
    	return gc.getTime();
    }
    
    /**
     * get the last date of a year,
     * @param date
     * @param yearVariance
     * @return
     */
    public static Date getLastDayOfYear(Date date, int yearVariance){
    	Calendar gc=Calendar.getInstance();
    	gc.setTime( date );
    	int year=gc.get(Calendar.YEAR);    	
    	gc.set(year+yearVariance-1, Calendar.DECEMBER, 31);
    	
    	return gc.getTime();
    }
    /**
     * get the last date of a month of same input date,     * 
     * @param the date want to get its last date of the month
     * @return the last date of the month
     */
    public static Date getLastDateTimeOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.SECOND, -1);
		return c.getTime();

    }
    
    public static Date getLastDayOfMonth(Date date){
    	Calendar gc=Calendar.getInstance();
    	gc.setTime( date );
    	gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
    	return gc.getTime();
    }
    public static int getHoursFromTime(String time){
    	if(!isValidHHmmStr(time)){
    		return 0;
    	}
    	String[] str = StringUtils.split(time,":");
    	return Integer.valueOf(str[0]);
    }
    public static int getMinutesFromTime(String time){
    	if(!isValidHHmmStr(time)){
    		return 0;
    	}
    	String[] str = StringUtils.split(time,":");
    	return Integer.valueOf(str[1]);
    }
    
    public static String getDateTimeStr(){
    	return CalendarUtil.formatDatetime(CalendarUtil.getSystemDatetime(),CalendarUtil.DATEFORMAT_TIME);
    }
    
    public static void main(String[] args){
    	/*String strDate = CalendarUtil.formatDatetime(string2Datetime("20080110"), "yyyy-MM-");
    	Date retDate = CalendarUtil.string2Datetime(strDate+"01", "yyyyMMdd");
    	System.out.println(strDate+"<---->after invoke string2Datetime:"+retDate);*/
    	//int m = CalendarUtil.getMonthOfDate(CalendarUtil.getCurrentDate()); 
    	//Date t = string2Datetime("20130101");
    	
    	//System.out.println(getDaysBetweenTwoDates(CalendarUtil.getCurrentDate(),t));
    	//System.out.println(CalendarUtil.formatDatetime(CalendarUtil.getCurrentDate(), "yyyyMM"));
    	//System.out.println("-->"+m+","+CalendarUtil.getYearOfDate(t));;
    	
    	/*Calendar afterTime = Calendar.getInstance();  
    	afterTime.add(Calendar.MINUTE, 5);
    	String s = CalendarUtil.formatDatetime(afterTime.getTime(), CalendarUtil.UP_ITEM_DATEFORMAT_FULL);
    	java.sql.Date afterDate = new java.sql.Date(afterTime.getTime().getTime()); 
    	System.out.println(afterDate.toLocaleString());
    	System.out.println("---"+CalendarUtil.getCurrentDate()+","+CalendarUtil.addDays(CalendarUtil.getCurrentDate(), -1L));*/
    	
    	System.out.println(CalendarUtil.getRangeDate(new Date(), -6));
    	
    }
}
