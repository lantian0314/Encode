package com.example.encode;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import android.util.Base64;

import com.example.utils.Tools;

public class DH {

	private static final String ALGORITHM = "DH";
	private static final int KEY_SIZE = 1024;

	private static String publicKey = null;
	private static String privateKey = null;

	public void main() {
		String data = "hhhhhhhhhhhhhhhhhhhhhh";
		Tools.printLog("data"+data);
		
		Map<String, Object> Akey = initAkey();
		Tools.printLog("APublicKey:"+getPublicKey(Akey));
		Map<String, Object> bkey = initBkey(getPublicKey(Akey));
		
		Tools.printLog("BPrivateKey"+getPrivateKey(bkey));
		
		byte[] encodeBytes = encode(
				Base64.encode(data.getBytes(), Base64.DEFAULT),
				getPublicKey(Akey), getPrivateKey(bkey));
		Tools.printLog("Encode:"+new String(encodeBytes));
		
		Tools.printLog("BPublicKey"+getPublicKey(bkey));
		Tools.printLog("APrivateKey"+getPrivateKey(Akey));
		byte[] dencodeBytes=dencode(encodeBytes, getPublicKey(bkey), getPrivateKey(Akey));
		Tools.printLog("Dencode:"+new String(dencodeBytes));
	}

	/**
	 * �õ���Կ
	 * 
	 * @param map
	 * @return
	 */
	private String getPublicKey(Map<String, Object> map) {
		Key key = (Key) map.get(publicKey);
		byte[] byteKeys = Base64.encode(key.getEncoded(), Base64.DEFAULT);
		return new String(byteKeys);
	}

	/**
	 * �õ�˽Կ
	 * 
	 * @param map
	 * @return
	 */
	private String getPrivateKey(Map<String, Object> map) {
		Key key = (Key) map.get(privateKey);
		byte[] byteKeys = Base64.encode(key.getEncoded(), Base64.DEFAULT);
		return new String(byteKeys);
	}

	/**
	 * ��ʼ���׷���Կ
	 * 
	 * @return
	 */
	private Map<String, Object> initAkey() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance(ALGORITHM);
			keyPairGenerator.initialize(KEY_SIZE);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			// �׷���Կ
			DHPublicKey apublicKey = (DHPublicKey) keyPair.getPublic();
			// �׷�˽Կ
			DHPrivateKey aprivateKey = (DHPrivateKey) keyPair.getPrivate();

			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put(publicKey, apublicKey);
			map.put(privateKey, aprivateKey);
			return map;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}

	private Map<String, Object> initBkey(String Key) {
		try {
			// �����׷���Կ
			byte[] keyBytes = Base64.decode(Key.getBytes(),
					Base64.DEFAULT);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

			// �ɼ׷���Կ�����ҷ���Կ
			DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance(keyFactory.getAlgorithm());
			keyPairGenerator.initialize(dhParamSpec);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			// �ҷ���Կ
			DHPublicKey bPublicKey = (DHPublicKey) keyPair.getPublic();
			DHPrivateKey bPrivateKey = (DHPrivateKey) keyPair.getPrivate();

			Map<String, Object> keyMap = new HashMap<String, Object>(2);
			keyMap.put(publicKey, bPublicKey);
			keyMap.put(privateKey, bPrivateKey);
			return keyMap;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}

	/**
	 * ���ݼ���
	 * 
	 * @param data
	 *            ����
	 * @param apublicKey
	 *            �׷���Կ
	 * @param bprivateKey
	 *            �ҷ�˽Կ
	 */
	private byte[] encode(byte[] data, String apublicKey, String bprivateKey) {
		SecretKey secretKey = getSecretKey(apublicKey, bprivateKey);
		try {
			// ���ݼ���
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;

	}

	/**
	 * ��������
	 * 
	 * @param data
	 *            ����
	 * @param bpublicKey
	 *            �ҷ���Կ
	 * @param aprivateKey
	 *            �׷�˽Կ
	 * @return
	 */
	private byte[] dencode(byte[] data, String bpublicKey, String aprivateKey) {
		try {
			// ���ɱ�����Կ
			SecretKey secretKey = getSecretKey(bpublicKey, aprivateKey);
			// ���ݽ���
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}

	/**
	 * ���ɱ�����Կ
	 * 
	 * @param apublicKey
	 *            ��Կ
	 * @param bprivateKey
	 *            ˽Կ
	 * @return
	 */
	private SecretKey getSecretKey(String apublicKey, String bprivateKey) {
		try {
			// ��ʼ����Կ
			byte[] pubKeyBytes = Base64.decode(apublicKey.getBytes(),
					Base64.DEFAULT);

			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

			// ��ʼ��˽Կ
			byte[] priKeyBytes = Base64.decode(apublicKey.getBytes(),
					Base64.DEFAULT);

			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
					priKeyBytes);
			Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

			KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory
					.getAlgorithm());
			keyAgree.init(priKey);
			keyAgree.doPhase(pubKey, true);

			// ���ɱ�����Կ
			SecretKey secretKey = keyAgree.generateSecret("DES");

			return secretKey;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}

}
