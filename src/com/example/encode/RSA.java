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

		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = sign(encodedData, privateKey);
		Tools.printLog("qianming:" + sign);

		// 验证签名
		boolean status = verify(encodedData, publicKey, sign);
		Tools.printLog("status....:" + status);

	}

	/**
	 * 初始化密钥，保存到Map集合中
	 * @return
	 */
	public static Map<String, Object> initKey() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
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
	 * 针对加密的数据进行签名
	 * @param data 数据
	 * @param privateKey 私钥
	 * @return
	 */
	private static String sign(byte[] data, String privateKey) {
		// 字节数组
		//byte[] keyBytes = privateKey.getBytes();
		byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
		try {
			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 取私钥匙对象
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 用私钥对信息生成数字签名
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
	 * 检查数据签名是否发生变化
	 * @param data 数据
	 * @param publicKey 公钥
	 * @param sign 签名
	 * @return
	 */
	private static boolean verify(byte[] data, String publicKey, String sign) {
		//byte[] keyBytes = publicKey.getBytes();
		byte[] keyBytes = Base64.decode(publicKey, Base64.DEFAULT);
		try {
			// 构造X509EncodedKeySpec对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 取公钥匙对象
			PublicKey pubKey = keyFactory.generatePublic(keySpec);

			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(data);
			// 验证签名是否正常
			return signature.verify(Base64.decode(sign, Base64.DEFAULT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 公钥加密
	 * @param data 数据
	 * @param key 公钥
	 * @return
	 */
	private static byte[] encodedByPublicKey(byte[] data, String key) {
		// byte[] keyBytes = key.getBytes();
		byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
		try {
			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据加密
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
	 * 私钥解密
	 * @param data 数据
	 * @param key 私钥
	 * @return
	 */
	private static byte[] dencodeByPrivateKey(byte[] data, String key) {
		// byte[] keyBytes = key.getBytes();
		byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
		try {
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 对数据解密
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
