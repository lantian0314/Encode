package com.example.utils;

import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class DeviceInfo {

	private Context mContext;

	public DeviceInfo(Context context) {
		mContext = context;
	}

	/**
	 * 得到Sim卡的国家
	 * @return
	 */
	public String getSimNation() {
		String nation = "";
		try {
			TelephonyManager tm = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			nation=tm.getSimCountryIso();
			return nation;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}

	/**
	 * 得到Sim卡运营商
	 * @return
	 */
	public String getOperator(){
		String operator="";
		try {
			TelephonyManager tm = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			operator=tm.getSimOperator();
			return operator;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;	
	}
	public String getTimeZone(){
		String timeZone="";
		try {
			timeZone=TimeZone.getDefault().toString();
			return timeZone;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	
	/**
	 * 判断是否是系统App
	 * @return
	 */
	public String isSystemApp(){
		String data="0";
		try {
			if (!UtilsPackage.isSystemApp(mContext)) {
				data="1";
			}
			String info=data.equals("0")?"is Sysem APP":"is Normal App";
			return info;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	
	/**
	 * 得到Android的ID
	 * @return
	 */
	public String getAndroidId(){
		String id="";
		try {
			id=Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
			return id;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	
	/**
	 * 得到手机中的国家信息
	 * @return
	 */
	public String getDNation(){
		String nation="";
		try {
			nation=Locale.getDefault().getCountry();
			return nation;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
	
	/**
	 * 得到国家信息
	 * @return
	 */
	public String getLanguage(){
		String language="";
		try {
			language=Locale.getDefault().getLanguage();
			return language;
		} catch (Exception e) {
			Tools.printLog(e);
		}
		return null;
	}
}
