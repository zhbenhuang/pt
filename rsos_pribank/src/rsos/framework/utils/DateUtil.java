package rsos.framework.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
    
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DEFAULT_DAY_FROMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DAY_FROMAT1 = "yyyyMMdd";

    private DateUtil() {
    }
    
    /**
     * Get current time for SQL
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
    
    public static String formateDateStr(String dateStr,String fromFormat,String toFormat){
    	if(dateStr!=null){
	    	Date tmp = DateUtil.parse(dateStr,fromFormat);
			return DateUtil.dateToString(tmp, toFormat);
    	}else{
    		return null;
    	}
    }
    
    /**
     * Convert java.util.Date to a string
     * @param d Date
     * @param format format
     * @return
     */
    public static String dateToString(Date d, String format) {
        if (d == null)
            return "";

        if (format == null)
            format = DateUtil.DEFAULT_DATE_FORMAT;
        else
            format = format.trim();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }
    
    /**
     * Parse a string to java.util.Date
     * @param date
     * @return
     */
    public static Date parse(String date) {
        if (date == null)
            return null;
        
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DAY_FROMAT);
        try {
            Date d = df.parse(date);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date parse(String date,String format) {
        if (date == null)
            return null;
        
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date d = df.parse(date);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date parseDateTime(String date) {
        if (date == null)
            return null;
        
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            Date d = df.parse(date);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date getSystemDatetime() {
    	return Calendar.getInstance().getTime();
    }
    
    public static void main(String[] args) {
        /*Date d = Calendar.getInstance().getTime();
        System.out.println(DateUtil.dateToString(d, DEFAULT_DAY_FROMAT));
        System.out.println(DateUtil.parse("1982-2-4"));
        System.out.println(DateUtil.parseDateTime("2012-05-08 23:59:59"));*/
    	Date d = DateUtil.parse("20120506", DateUtil.DEFAULT_DAY_FROMAT1);
    	System.out.println(DateUtil.dateToString(d, DEFAULT_DAY_FROMAT));
    }
}
