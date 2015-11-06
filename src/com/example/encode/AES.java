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
	 * 鍔犲瘑鍚嶇О涓篈ES
	 */
	private static final String ALGORITHM = "AES";
	/**
	 * AES浣跨敤CBC妯″紡
	 */
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	
	private Key mkey;
	
	private IvParameterSpec miv;
	
	private Cipher cipher;
	
	private static AES aes=null;
	
	/**
	 * 鍗曚緥
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
	 * 甯﹀弬鐨勬瀯閫犲嚱鏁�
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
	 * 鏁版嵁鐨勫垵濮嬪寲
	 */
	private void init() {
		try {
			cipher=Cipher.getInstance(TRANSFORMATION);
		} catch (Exception e) {
			Tools.printLog(e);
		}
	}

	/**
	 * 寰楀埌MD5鏈哄瘑鐨勫�
	 * @param algorithm MD5
	 * @param key key鍊�
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
	 * 鏁版嵁鍔犲瘑
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
	
	public byte[] dencode(String data){
		try {
			byte[] buffer=Base64.decode(data.getBytes(), Base64.NO_WRAP);
			return dencode(buffer);
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	/**
	 * 解密
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
	 * 榛樿鐨刬vKEY
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
