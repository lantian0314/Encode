package com.example.encode;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import android.util.Base64;

import com.example.utils.Tools;

public class RSA {

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static String publicKey;
	private static String privateKey;

	/**
	 * @param args
	 */
	public static void main() {
		Map<String, Object> keyMap = initKey();
		Key pubkey = (Key) keyMap.get(PUBLIC_KEY);
		publicKey = new String(Base64.encode(pubkey.getEncoded(),
				Base64.DEFAULT));
		
		Key privkey = (Key) keyMap.get(PRIVATE_KEY);
		privateKey = new String(Base64.encode(privkey.getEncoded(),
				Base64.DEFAULT));

		Tools.printLog("gongyao:" + publicKey);
		Tools.printLog("siyao:" + privateKey);

		String inputStr = "hhhhhhhhhhhhhhhhhhhhhhhhhhh";
		byte[] data = inputStr.getBytes();
		byte[] encodedData = encodedByPublicKey(data, publicKey);
		System.out.println(encodedData);
		Tools.printLog("jiamishuju:" + new String(encodedData));
		byte[] decodedData = dencodeByPrivateKey(encodedData, privateKey);
		String outputStr = new String(decodedData);
		Tools.printLog("After.........:" + outputStr);

		System.err.println("˽Կǩ��������Կ��֤ǩ��");
		// ����ǩ��
		String sign = sign(encodedData, privateKey);
		Tools.printLog("qianming:" + sign);

		// ��֤ǩ��
		boolean status = verify(encodedData, publicKey, sign);
		Tools.printLog("status....:" + status);

	}

	/**
	 * ��ʼ����Կ�����浽Map������
	 * @return
	 */
	public static Map<String, Object> initKey() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// ��Կ
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// ˽Կ
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			Map<String, Object> keyMap = new HashMap<String, Object>(2);
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			return keyMap;

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��Լ��ܵ����ݽ���ǩ��
	 * @param data ����
	 * @param privateKey ˽Կ
	 * @return
	 */
	private static String sign(byte[] data, String privateKey) {
		// �ֽ�����
		//byte[] keyBytes = privateKey.getBytes();
		byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
		try {
			// ����PKCS8EncodedKeySpec����
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM ָ���ļ����㷨
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// ȡ˽Կ�׶���
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// ��˽Կ����Ϣ��������ǩ��
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(data);
			return new String(Base64.encode(signature.sign(), Base64.DEFAULT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �������ǩ���Ƿ����仯
	 * @param data ����
	 * @param publicKey ��Կ
	 * @param sign ǩ��
	 * @return
	 */
	private static boolean verify(byte[] data, String publicKey, String sign) {
		//byte[] keyBytes = publicKey.getBytes();
		byte[] keyBytes = Base64.decode(publicKey, Base64.DEFAULT);
		try {
			// ����X509EncodedKeySpec����
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM ָ���ļ����㷨
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// ȡ��Կ�׶���
			PublicKey pubKey = keyFactory.generatePublic(keySpec);

			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(data);
			// ��֤ǩ���Ƿ�����
			return signature.verify(Base64.decode(sign, Base64.DEFAULT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��Կ����
	 * @param data ����
	 * @param key ��Կ
	 * @return
	 */
	private static byte[] encodedByPublicKey(byte[] data, String key) {
		// byte[] keyBytes = key.getBytes();
		byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
		try {
			// ȡ�ù�Կ
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// �����ݼ���
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ˽Կ����
	 * @param data ����
	 * @param key ˽Կ
	 * @return
	 */
	private static byte[] dencodeByPrivateKey(byte[] data, String key) {
		// byte[] keyBytes = key.getBytes();
		byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
		try {
			// ȡ��˽Կ
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// �����ݽ���
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
