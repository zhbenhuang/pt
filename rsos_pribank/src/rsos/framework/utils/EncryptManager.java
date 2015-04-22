package rsos.framework.utils;

import java.security.MessageDigest;
import org.apache.log4j.*;

public class EncryptManager {


	public EncryptManager(){
		
	}

	private static Logger logger = Logger.getLogger(EncryptManager.class);
	
	private static EncryptManager instance = null;
	
	public static String encrypt(Object object) throws Exception {
		try {
			if (instance == null){
				instance = new EncryptManager();
			}
			MessageDigest sha = MessageDigest.getInstance("SHA");
			sha.update(object.toString().getBytes());
			return toHex(sha.digest());
		} catch (Exception e) {
			//LogManager.error(ConstantsUtil.LogTypeSys,"Encrypt password fail!!");
			logger.error("Encrypt password fail!!");
			throw new Exception(e.toString());
		}
	}
	
	private static String toHex(byte[] digest) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			buffer.append(Integer.toHexString((int) digest[i] & 0x00ff));
		}
		return buffer.toString();
	}
	public static void main(String args[]) {
		try {
			System.out.print("123-------->:"+EncryptManager.encrypt("123"));
		} catch (Exception e) {
			
		}
	}
}
