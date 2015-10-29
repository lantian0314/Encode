package com.example.utils;

import android.util.Log;

public class Tools {

	public static void printLog(String info) {
		Log.d("encode.sky", info);
	}

	public static void printLog(Exception e) {
		printLog("had Exception");
		e.printStackTrace();
	}
}
