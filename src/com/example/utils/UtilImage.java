package com.example.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class UtilImage {

	/**
	 * 设定图片压缩的比例
	 * @param options
	 * @param reqWidth 需求的宽
	 * @param reqHeigh 需求的高
	 * @return
	 */
	private static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeigh){
		int inSampleSize=1; //1表示不缩放，2表示为原来宽高的1/2
		if (options!=null&&reqWidth>0&&reqHeigh>0) {
			//获得源图片的宽和高
			int width=options.outWidth;
			int heigh=options.outHeight;
			if (width>reqWidth&&heigh>reqHeigh) {
			//计算实际宽高和目标宽高的比率
			int heightRatio=Math.round((float)heigh/(float)reqHeigh);
			int widthRatio=Math.round((float)width/(float)reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize，这样可以保证最终的图片的宽高大于等于目标宽高
			inSampleSize = heightRatio < widthRatio ? heightRatio
					: widthRatio;
			}
		}
		return inSampleSize;
	}
	
	/**
	 * 得到压缩后的图片
	 * @param context 上下文
	 * @param fileName 文件名
	 * @param reqWidth 需求的宽
	 * @param reqHeigh 需求的高
	 * @return
	 */
	public static Bitmap getSmallBitmap(Context context,String fileName,int reqWidth,int reqHeigh){
		// 第一次解析将inJustDecodeBounds设置为true,只是获取图片大小，不会加载图片到内存
		try {
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inJustDecodeBounds=true;
			BitmapFactory.decodeStream(context.getAssets().open(fileName), null, options);
			options.inSampleSize=caculateInSampleSize(options, reqWidth, reqHeigh);
			//使用获取到的inSampleSimple，在此解析图片
			options.inJustDecodeBounds=false;
			return BitmapFactory.decodeStream(context.getAssets().open(fileName), null, options);
		} catch (Exception e) {
			Tools.printLog(e);
		}
		
		return null;
	}
	
	/**
	 * bitmap转换为字符串
	 * @param context 上下文
	 * @param fileName 文件名
	 * @param reqWidth 需求宽
	 * @param reqHeigh 需求高
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
