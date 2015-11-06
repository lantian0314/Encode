package com.example.encode;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.example.utils.Tools;

import android.util.Base64;

/**
 * AES加密
 * @author xingyatong
 *
 */
public class AES {

	/**
	 * 加密方式
	 */
	private static final String ALGORITHM = "AES";
	
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	
	private Key mkey;
	
	private IvParameterSpec miv;
	
	private Cipher cipher;
	
	private static AES aes=null;
	
	/**
	 * 初始化
	 * @param key 
	 * @param ivKey
	 * @return
	 */
//	public static AES getInstance(String key,String ivKey){
//		if (aes==null) {
//			aes=new AES(key, ivKey);
//		}
//		return aes;
//	}
	/**
	 * 带参数的构造方法
	 * @param key
	 * @param ivKey
	 */
	public AES(String key,String ivKey){
		try {
			this.mkey = new SecretKeySpec(getHash("MD5", key), ALGORITHM);
			if (ivKey!=null) {
				this.miv=new IvParameterSpec(getHash("MD5", ivKey));
			}else {
				this.miv = getDefaultIvParam();
			}
			init();
		} catch (Exception e) {
			Tools.printLog(e);
		}
	}


	
	/**
	 * 初始化
	 */
	private void init() {
		try {
			cipher=Cipher.getInstance(TRANSFORMATION);
		} catch (Exception e) {
			Tools.printLog(e);
		}
	}

	/**
	 * 得到HAS值
	 * @param algorithm MD5
	 * @param key key值
	 * @return
	 */
	private byte[] getHash(String algorithm, String key) {
		try {
			MessageDigest digest=MessageDigest.getInstance(algorithm);
			digest.update(key.getBytes("UTF-8"));
			return digest.digest();
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	
	/**
	 * 加密方法
	 * @param data
	 * @return
	 */
	public String encode(byte[] data){
		try {
			cipher.init(Cipher.ENCRYPT_MODE, mkey, miv);
			byte[] encryptData = cipher.doFinal(data);
			return new String(Base64.encode(encryptData, Base64.NO_WRAP),
					"UTF-8");
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	
	public byte[] dencode(String string){
		try {
			byte[] data=Base64.decode(string.getBytes(), Base64.NO_WRAP);
			return dencode(data);
		} catch (Exception e) {
		Tools.printLog(e);
		}
		return null;
	}
	/**
	 * 解密方法
	 * @param data
	 * @return
	 */
	public byte[] dencode(byte[] data){
		try {
			cipher.init(Cipher.DECRYPT_MODE, mkey, miv);
			byte[] dencryptData = cipher.doFinal(data);
			return dencryptData;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	/**
	 * 默认的ivKEY
	 * @return
	 */
	private IvParameterSpec getDefaultIvParam() {
		byte[] bt = new byte[8];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = 0;
		}
		return new IvParameterSpec(bt);
	}
}
