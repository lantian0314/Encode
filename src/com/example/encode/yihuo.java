package com.example.encode;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.nio.charset.Charset;

import com.example.utils.Tools;

public class yihuo {

	@SuppressLint("NewApi")
	public static String encode(String string) {
		Tools.printLog("Before:"+string);
		byte[] data = string.getBytes();
		String s="FECOI()*&<MNCXZPKL";
		byte[] key=s.getBytes(Charset.forName("UTF-8"));
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < key.length; j++) {
				data[i] = (byte) (data[i] ^key[j]);
			}
			
		}
		return new String(data);
	}
}
