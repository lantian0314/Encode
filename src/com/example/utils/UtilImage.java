package com.example.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class UtilImage {

	/**
	 * �趨ͼƬѹ���ı���
	 * @param options
	 * @param reqWidth ����Ŀ�
	 * @param reqHeigh ����ĸ�
	 * @return
	 */
	private static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeigh){
		int inSampleSize=1; //1��ʾ�����ţ�2��ʾΪԭ����ߵ�1/2
		if (options!=null&&reqWidth>0&&reqHeigh>0) {
			//���ԴͼƬ�Ŀ�͸�
			int width=options.outWidth;
			int heigh=options.outHeight;
			if (width>reqWidth&&heigh>reqHeigh) {
			//����ʵ�ʿ�ߺ�Ŀ���ߵı���
			int heightRatio=Math.round((float)heigh/(float)reqHeigh);
			int widthRatio=Math.round((float)width/(float)reqWidth);
			// ѡ���͸�����С�ı�����ΪinSampleSize���������Ա�֤���յ�ͼƬ�Ŀ�ߴ��ڵ���Ŀ����
			inSampleSize = heightRatio < widthRatio ? heightRatio
					: widthRatio;
			}
		}
		return inSampleSize;
	}
	
	/**
	 * �õ�ѹ�����ͼƬ
	 * @param context ������
	 * @param fileName �ļ���
	 * @param reqWidth ����Ŀ�
	 * @param reqHeigh ����ĸ�
	 * @return
	 */
	public static Bitmap getSmallBitmap(Context context,String fileName,int reqWidth,int reqHeigh){
		// ��һ�ν�����inJustDecodeBounds����Ϊtrue,ֻ�ǻ�ȡͼƬ��С���������ͼƬ���ڴ�
		try {
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inJustDecodeBounds=true;
			BitmapFactory.decodeStream(context.getAssets().open(fileName), null, options);
			options.inSampleSize=caculateInSampleSize(options, reqWidth, reqHeigh);
			//ʹ�û�ȡ����inSampleSimple���ڴ˽���ͼƬ
			options.inJustDecodeBounds=false;
			return BitmapFactory.decodeStream(context.getAssets().open(fileName), null, options);
		} catch (Exception e) {
			Tools.printLog(e);
		}
		
		return null;
	}
	
	/**
	 * bitmapת��Ϊ�ַ���
	 * @param context ������
	 * @param fileName �ļ���
	 * @param reqWidth �����
	 * @param reqHeigh �����
	 * @return
	 */
	public static String bitmapToString(Context context,String fileName,int reqWidth,int reqHeigh){
		Bitmap bitmap=getSmallBitmap(context, fileName, reqWidth, reqHeigh);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
		byte[] b=baos.toByteArray();
		return Base64.encodeToString(b, Base64.NO_WRAP);
	}
	
}
