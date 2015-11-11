package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * ѹ�������
 * @author xingyatong
 *
 */
public class UtilCompress {

	/**
	 * ѹ��
	 * @param data
	 * @return
	 */
	public static byte[] getGZipCompress(String data){
		byte[] buffer=null;
		try {
			byte[] byteData=data.getBytes();
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			Deflater compressor=new Deflater();
			compressor.setLevel(Deflater.BEST_COMPRESSION);//����ѹ���ļ���
			compressor.setInput(byteData, 0, byteData.length);
			compressor.finish();
			
			byte[] tempData=new byte[1024];
			while (!compressor.finished()) {
			int len=compressor.deflate(tempData);
			baos.write(tempData, 0, len);
			}
			compressor.end();//�رս�ѹ������������������
			buffer=baos.toByteArray();
			baos.close();
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return buffer;
	}
	
	/**
	 * ��ѹ
	 * @param data
	 * @return
	 */
	public static byte[] getGZipUncompress(byte[] data){
		byte[] unCompress=null;
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			Inflater inflater=new Inflater();
			inflater.setInput(data, 0, data.length);
			byte[] buffer=new byte[1024];
			while (!inflater.finished()) {
			int len=inflater.inflate(buffer);	
			baos.write(buffer, 0, len);
			}
			inflater.end();
			unCompress=baos.toByteArray();
			baos.close();
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return unCompress;
	}
}
