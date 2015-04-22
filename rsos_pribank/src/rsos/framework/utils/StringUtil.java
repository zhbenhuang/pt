package rsos.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	/**
	 * 在数字字符串前增加一个非数字字符，防止在页面获取该值操作显示为科学计数。
	 */
	public static final String addCharacter(String s){
		return "S"+s;
	}
	public static final String rmCharacter(String s){
		return s.substring(1);
	}
	
	/**
     * check whether the search criteria indicates to perform a fuzzy query. 
     * @param searchCriteria
     * @return
     */
    public static final boolean isFuzzyQuery(String searchCriteria){
    	
    	if(StringUtils.isEmpty(searchCriteria)) return false;
    	if(searchCriteria.indexOf("%")!=-1) return true;
    	   	    	
    	return false;
    }
    
	public static String unicorde2GB(String str){
		try{
			String  temp_p=str;  
			byte[]  temp_t=temp_p.getBytes("ISO8859-1");
			String  temp=new  String(temp_t);  
			return  temp;  
		}catch(Exception  e){}
		return null;  
	}
	
	public static String utf2GB(String str){
		try{
			String  temp_p=str;  
			byte[]  temp_t=temp_p.getBytes("UTF-8");
			String  temp=new  String(temp_t,"GBK");  
			return  temp;  
		}catch(Exception  e){}
		return null;  
	}
	
	public static String utf8Togb2312(String str){   
		StringBuffer sb = new StringBuffer();   
		for ( int i=0; i<str.length(); i++) {   
		    char c = str.charAt(i);   
		    switch (c) {   
		       case '+' :   
		           sb.append( ' ' );   
		       break ;   
		       case '%' :   
		           try {   
		                sb.append(( char )Integer.parseInt (   
		                str.substring(i+1,i+3),16));   
		           }   
		           catch (NumberFormatException e) {   
		               throw new IllegalArgumentException();   
		          }   
		          i += 2;   
		          break ;   
		            
		       default :   
		          sb.append(c);   
		          break ;   
		     }   
		}   
		String result = sb.toString();   
		String res= null ;   
		try {   
		     byte [] inputBytes = result.getBytes( "GBK" );   
		    res= new String(inputBytes, "UTF-8" );
		}   
		catch (Exception e){}   
		return res;   
		}  

		//GB2312->UTF-8   
		public static String gb2312ToUtf8(String str) {   
		String urlEncode = "" ;   
		try {   
		    urlEncode = URLEncoder.encode (str, "UTF-8" );   
		} catch (UnsupportedEncodingException e) {   
		    e.printStackTrace();   
		}   
		return urlEncode;   
	}  
		public static boolean allNoneZeros(int ai[])
	    {
	        for(int i = 0; i < ai.length; i++)
	            if(ai[i] == 0)
	                return false;

	        return true;
	    }

	    public static final String canonical(String s)
	    {
	        if(s == null)
	            return "";
	        else
	            return s.trim();
	    }

	    public static final boolean isEmpty(String s)
	    {
	        return s == null||"null".equals(s) ? true : s.trim().length() == 0;
	    }

	    public static boolean isIn(String s, String s1)
	    {
	        for(StringTokenizer stringtokenizer = new StringTokenizer(s, ", "); stringtokenizer.hasMoreTokens();)
	            if(stringtokenizer.nextToken().equals(s1))
	                return true;

	        return false;
	    }

		public static boolean isDouble(String value) { 
			try { 
				Double.parseDouble(value); 
				return true; 
			} catch (NumberFormatException e) { 
				return false; 
			} 
		}
		
		public static boolean isInteger(String value) { 
			try { 
				Integer.parseInt(value); 
				return true; 
			} catch (NumberFormatException e) { 
				return false; 
			} 
		}
		
	    public static boolean isInRange(String s, String s1)
	    {
	        if(s.indexOf("-") == -1)
	            return isIn(s, s1);
	        for(StringTokenizer stringtokenizer = new StringTokenizer(s, ", "); stringtokenizer.hasMoreTokens();)
	        {
	            String s2 = stringtokenizer.nextToken();
	            int i = s2.indexOf("-");
	            if(i == -1)
	            {
	                if(s2.equals(s1))
	                    return true;
	            } else
	            {
	                try
	                {
	                    String s3 = s2.substring(0, i);
	                    String s4 = s2.substring(i + 1);
	                    int j = s3.length();
	                    if(j == s4.length() && j == s1.length() && s3.compareTo(s1) <= 0 && s4.compareTo(s1) >= 0)
	                        return true;
	                }
	                catch(Exception exception) { }
	            }
	        }

	        return false;
	    }

	    public static final String makeString(char c, int i)
	    {
	        StringBuffer stringbuffer = new StringBuffer(i);
	        stringbuffer.setLength(i);
	        for(int j = 0; j < i; j++)
	            stringbuffer.setCharAt(j, c);

	        return stringbuffer.toString().intern();
	    }

	    public static String null2EmptyString(String s)
	    {
	        return s != null ? s : "";
	    }

	    public static int occurs(char c, String s)
	    {
	        int i = 0;
	        for(int j = 0; j < s.length(); j++)
	            if(s.charAt(j) == c)
	                i++;

	        return i;
	    }

	    public static String rmCharacter(String s, char c)
	    {
	        if(s == null || s.equals(""))
	            return s;
	        byte abyte0[] = s.getBytes();
	        int i = abyte0.length;
	        byte abyte1[] = new byte[i];
	        int j = 0;
	        for(int k = 0; k < i; k++)
	            if(abyte0[k] != c)
	                abyte1[j++] = abyte0[k];

	        return new String(abyte1, 0, j);
	    }

	    public static String rmDecimalPoint(String s, int i)
	        throws Exception
	    {
	        StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
	        String s1 = stringtokenizer.nextToken();
	        String s2 = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken() : "";
	        int j = s2.length();
	        StringBuffer stringbuffer = new StringBuffer(s1);
	        int k = j - i;
	        if(k == 0)
	            stringbuffer.append(s2);
	        else
	        if(k > 0)
	        {
	            stringbuffer.append(s2.substring(0, i));
	        } else
	        {
	            k = -k;
	            stringbuffer.append(s2);
	            for(int l = 0; l < k; l++)
	                stringbuffer.append('0');

	        }
	        return stringbuffer.toString();
	    }

	    public static final String[] string2Array(String s, char c)
	    {
	        int i = 0;
	        int j = 0;
	        int k = 1;
	        for(i = s.indexOf(c, 0); i != -1; i = s.indexOf(c, i + 1))
	            k++;

	        String as[] = new String[k];
	        j = 0;
	        i = s.indexOf(c, 0);
	        k = 0;
	        for(; i != -1; i = s.indexOf(c, i + 1))
	        {
	            as[k] = s.substring(j, i);
	            j = i + 1;
	            k++;
	        }

	        as[k] = s.substring(j);
	        return as;
	    }

	    public static final String toSQLString(String s)
	    {
	        if(s == null)
	            return "";
	        String s1 = s.trim();
	        int i = s1.length();
	        if(i == 0)
	            return "";
	        StringBuffer stringbuffer = new StringBuffer();
	        stringbuffer.ensureCapacity(i * 3);
	        int j = 0;
	        for(int k = s1.indexOf("'"); k != -1; k = s1.indexOf("'", j))
	        {
	            stringbuffer.append(s1.substring(j, k) + "''");
	            j = k + 1;
	        }

	        stringbuffer.append(s1.substring(j));
	        return stringbuffer.toString();
	    }

	    public static final boolean isAsciiString(String s)
	    {
	        for(int i = 0; i < s.length(); i++)
	            if(s.charAt(i) > '\177')
	                return false;

	        return true;
	    }

	    public static final String prefixChar(String s, int numberOfZero, char aChar)
	    {
	        StringBuffer stringbuffer = new StringBuffer(numberOfZero);
	        for(int j = 0; j < numberOfZero; j++)
	            stringbuffer.append(aChar);

	        stringbuffer.append(s);
	        return stringbuffer.toString();
	    }

	    public static final String prefixChar2FixedLen(String s, int len, char aChar)
	    {
	        if(len > s.length())
	            return prefixChar(s, len - s.length(), aChar);
	        else
	            return s;
	    }

	    public static final String prefixZero(String s, int numberOfZero)
	    {
	        return prefixChar(s, numberOfZero, '0');
	    }

	    public static final String prefixZero2FixedLen(String s, int len)
	    {
	        return prefixChar2FixedLen(s, len, '0');
	    }

	    public static final String suffixChar(String s, int numberOfChar, char aChar)
	    {
	        StringBuffer stringbuffer = new StringBuffer(s);
	        for(int j = 0; j < numberOfChar; j++)
	            stringbuffer.append(aChar);

	        return stringbuffer.toString();
	    }

	    public static final String suffixChar2FixedLen(String s, int len, char aChar)
	    {
	        if(len > s.length())
	            return suffixChar(s, len - s.length(), aChar);
	        else
	            return s;
	    }

	    public static final boolean isNumericString(String s)
	    {
	        try
	        {
	            Double.valueOf(s);
	            return true;
	        }
	        catch(Exception _ex)
	        {
	            return false;
	        }
	    }

	    public static final String toHex(byte buffer[])
	    {
	        StringBuffer sb = new StringBuffer();
	        String s = null;
	        for(int i = 0; i < buffer.length; i++)
	        {
	            s = Integer.toHexString(buffer[i] & 0xff);
	            if(s.length() < 2)
	                sb.append('0');
	            sb.append(s);
	        }

	        return sb.toString();
	    }
	    
	    /**
	     * 验证邮箱地址是否正确
	     * @param email
	     * @return
	     */
		public static boolean checkEmail(String email){
			boolean flag = true;
			try{
				String check = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
				//"^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";
				Pattern regex = Pattern.compile(check);
				Matcher matcher = regex.matcher(email);
				flag = matcher.matches();
			}catch(Exception e){
				flag = false;
			}
	     
			return flag;
		}
		/**
	     * 验证手机号码
	     * @param mobiles
	     * @return
	     */
	   public static boolean isMobileNO(String mobiles){
		   boolean flag = false;
		   try{
			   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
			   Matcher m = p.matcher(mobiles);
			   flag = m.matches();
		   }catch(Exception e){
			   flag = false;
		   }
		   return flag;
		}

		public static void main(String[] args) {
			String workDir = System.getProperty("user.dir");
			System.out.println("workDir:"+workDir);
	        String s = "13632323105";
	        if(isMobileNO(s)){
	        	System.out.println("true");
	        }else{
	        	System.out.println("flase");
	        }
	        String s1 = "13632323105@mobile.com.cn";
	        if(checkEmail(s1)){
	        	System.out.println("true");
	        }else{
	        	System.out.println("flase");
	        }
	    }
	
}
