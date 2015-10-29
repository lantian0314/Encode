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
			// Ϊ��һ��Կ����һ��ָ���� DESSede key
			DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
			// �õ� DESSede keys
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			// ����һ�� DESede ��Կ����
			theKey = keyFactory.generateSecret(spec);
			// ����һ�� DESede ����
			cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			// Ϊ CBC ģʽ����һ�����ڳ�ʼ���� vector ����
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
			// �Լ���ģʽ��ʼ����Կ
			cipher.init(Cipher.ENCRYPT_MODE, theKey, paramSpec);
			// ��������
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  byte[] dencode(byte[] data) {
		try {
			// �Լ���ģʽ��ʼ����Կ
			cipher.init(Cipher.DECRYPT_MODE, theKey, paramSpec);
			// ��������
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
