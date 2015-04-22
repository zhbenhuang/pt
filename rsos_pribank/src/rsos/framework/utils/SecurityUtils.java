package rsos.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class SecurityUtils {
	public static final String INVALID_KEY = "INVALID_KEY";
	private static final String DEFAULT_AS_ALGORITHM = "RSA";
	private static final String KEY_PASS = "amssecret";
	private static final String ALIAS = "ams";

	private static final String DEFAULT_S_ALGORITHM = "DES/ECB/PKCS5Padding";
	private static final byte[] DEFAUL_KEY_BYTES = new byte[] { 0x08, 0x31,
			0x13, 0x17, (byte) 0x89, (byte) 0xba, (byte) 0xfc, (byte) 0xef };
	private static final Key DEFAULT_S_KEY = new SecretKeySpec(
			DEFAUL_KEY_BYTES, "DES");

	private static final String DIGITS = "0123456789abcdef";

	public static String bytesToHex(byte[] data, int length) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;

			buf.append(DIGITS.charAt(v >> 4));
			buf.append(DIGITS.charAt(v & 0xf));
		}

		return buf.toString();
	}

	public static String bytesToHex(byte[] data) {
		return bytesToHex(data, data.length);
	}

	public static byte[] base64ToBytes(String data) {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes = null;
		try {
			bytes = decoder.decodeBuffer(data);
		} catch (IOException e) {
			System.out.println("Decode to BASE64 failed, " + e.getMessage());
		}
		return bytes;
	}

	public static String bytesToBase64(byte[] data) {
		BASE64Encoder endecoder = new BASE64Encoder();
		return endecoder.encode(data);
	}

	public static Key getPublicKey(InputStream in)
			throws NoSuchAlgorithmException, CertificateException, IOException,
			KeyStoreException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, KEY_PASS.toCharArray());
		X509Certificate cert = (X509Certificate) ks.getCertificate(ALIAS);
		return cert.getPublicKey();
	}

	public static String encrypt(Key key, String plain)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		return encrypt(DEFAULT_AS_ALGORITHM, key, plain);
	}

	public static String encrypt(String plain) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		return encrypt(DEFAULT_S_ALGORITHM, DEFAULT_S_KEY, plain);
	}

	private static String encrypt(String algorithm, Key key, String plain)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(plain.getBytes());

		return bytesToBase64(bytes);
	}

	public static Key getPrivateKey(InputStream in) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, KEY_PASS.toCharArray());
		return ks.getKey(ALIAS, KEY_PASS.toCharArray());
	}

	public static String decrypt(Key key, String cipherText)
			throws BadPaddingException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException {
		return decrypt(DEFAULT_AS_ALGORITHM, key, cipherText);
	}

	public static String decrypt(String cipherText) throws InvalidKeyException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException {
		return decrypt(DEFAULT_S_ALGORITHM, DEFAULT_S_KEY, cipherText);
	}

	private static String decrypt(String algorithm, Key key, String cipherText)
			throws BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException {
		byte[] bytes = base64ToBytes(cipherText);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(bytes));
	}

	public static void main(String[] args) {
		try {
			String text = "kpi";
			
			String text2 = encrypt(text);
			System.out.println(text2);
			System.out.println(decrypt(text2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
