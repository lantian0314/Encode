package com.example.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.content.Context;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class Tools {

	public static void printLog(String info) {
		Log.d("encode.sky", info);
	}

	public static void printLog(Exception e) {
		// printLog("had Exception");
		printLog(getStackTrace(e));
		e.printStackTrace();
	}

	private static String getStackTrace(Exception e) {
		Writer result = new StringWriter();
		PrintWriter writer = new PrintWriter(result);
		e.printStackTrace(writer);
		return result.toString();
	}

	public static int[] getScreenSize(Context context) {
		try {
			DisplayMetrics displayMetrics = context.getResources()
					.getDisplayMetrics();
			int[] size = { displayMetrics.widthPixels,
					displayMetrics.heightPixels };
			return size;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}

	public static boolean isScreenLock(Context context) {
		boolean result = false;
		try {
			result = ((PowerManager) context
					.getSystemService(Context.POWER_SERVICE)).isScreenOn();
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return result;
	}
}
