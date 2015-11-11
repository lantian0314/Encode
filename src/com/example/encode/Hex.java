package com.example.encode;

public class Hex {

	/**
	 * 字节数组转为Hex
	 * 
	 * @param src
	 *            数组
	 * @return
	 */
	public static String bytesToHex(byte[] src) {
		char[] result = new char[src.length * 2];
		char[] hexDigist = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0, j = 0; i < src.length; i++) {
			result[j++] = hexDigist[src[i] >>> 4 & 0x0f];
			result[j++] = hexDigist[src[i] & 0x0f];
		}
		return new String(result);
	}

	/**
	 * hex转换为字节数组
	 * 
	 * @param src
	 *            hex字符串
	 * @return
	 */
	public static byte[] hex2Bytes(String src) {
		byte[] res = new byte[src.length() / 2];
		char[] chs = src.toCharArray();
		for (int i = 0, j = 0; i < chs.length; i += 2, j++) {
			res[j] = (byte) Integer.parseInt(new String(chs, i, 2), 16);
		}
		return res;
	}

	/**
	 * hex转换为字节数组
	 * 
	 * @param src
	 *            hex字符串
	 * @return
	 */
	public static byte[] hex2BytesPro(String src) {
		byte[] res = new byte[src.length() / 2];
		char[] chs = src.toCharArray();
		int[] b = new int[2];
		for (int i = 0, j = 0; i < chs.length; i += 2, j++) {
			for (int k = 0; k < 2; k++) {
				if (chs[i + k] >= '0' && chs[i + k] <= '9') {
					b[k] = chs[i + k] - '0';
				} else if (chs[i + k] >= 'A' && chs[i + k] <= 'F') {
					b[k] = chs[i + k] - 'A' + 10;
				} else if (chs[i + k] >= 'a' && chs[i + k] <= 'f') {
					b[k] = chs[i + k] - 'a' + 10;
				}
			}
			b[0]=(b[0]&0x0f)<<4;
			b[1]=b[1]&0x0f;
			res[j]=(byte) (b[0]|b[1]);
		}
		return res;
	}
}
