package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class UtilFile {

	public static boolean copyFile(String src, String disPath) {
		boolean result = false;
		try {
			File file = new File(disPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream fos = new FileOutputStream(file);
			fos.write(src.getBytes());
			fos.close();
			result = true;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return result;
	}

	public static long getFileLength(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				return file.length();
			}
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return 0;
	}
}
