package com.example.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class Tools {

	public static void printLog(String info) {
		Log.d("encode.sky", info);
	}

	public static void printLog(Exception e) {
		printLog("had Exception");
		e.printStackTrace();
	}
	
	public static int[] getScreenSize(Context context){
		try {
			DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
			int[] size={displayMetrics.widthPixels,displayMetrics.heightPixels};
			return size;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
}
