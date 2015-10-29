package com.example.encode;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des {

	private static Des des = null;

	private static AlgorithmParameterSpec paramSpec = null;
	private static Cipher cipher = null;
	private static SecretKey theKey = null;

	private Des(String key,String ivKey) {
		init(key, ivKey);
	}

	public static Des getInstance(String key,String ivKey){
		if (des==null) {
			des=new Des(key, ivKey);
		}
		return des;
	}
	private void init(String key, String ivKey) {
		try {
			// 为上一密钥创建一个指定的 DESSede key
			DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
			// 得到 DESSede keys
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			// 生成一个 DESede 密钥对象
			theKey = keyFactory.generateSecret(spec);
			// 创建一个 DESede 密码
			cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			// 为 CBC 模式创建一个用于初始化的 vector 对象
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			paramSpec = iv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  byte[] encode(byte[] data) {
		// byte[] key="abcdefghig01234567890123".getBytes();
		// byte[] ivKey="abcd1234".getBytes();
		try {
			// 以加密模式初始化密钥
			cipher.init(Cipher.ENCRYPT_MODE, theKey, paramSpec);
			// 加密密码
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  byte[] dencode(byte[] data) {
		try {
			// 以加密模式初始化密钥
			cipher.init(Cipher.DECRYPT_MODE, theKey, paramSpec);
			// 加密密码
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
