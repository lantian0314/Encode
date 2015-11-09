package com.example.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

public class UtilsPackage {

	/**
	 * �Ƿ�ΪϵͳApp
	 * @param context
	 * @return
	 */
	public static boolean isSystemApp(Context context) {
		String packageName = context.getPackageName();
		try {
			return isSystemApp(context, packageName);
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return false;
	}

	/**
	 * ���ݰ����ж��Ƿ�ΪϵͳApp
	 * 
	 * @param context
	 * @param packageName
	 *            ����
	 * @return
	 */
	public static boolean isSystemApp(Context context, String packageName) {
		boolean isSystemApp = false;
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(packageName, 0);
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				isSystemApp = false;
			} else {
				isSystemApp = true;
			}
			return isSystemApp;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return false;
	}

}
