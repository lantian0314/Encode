package com.example.encode;

import com.example.utils.DeviceInfo;
import com.example.utils.Tools;
import com.example.utils.UtilCompress;
import com.example.utils.UtilFile;
import com.example.utils.UtilImage;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 异或加密
		//String string = "hhhhhhhhhhhhhhhhhhhhh";
		// String After=yihuo.encode(string);
		// Tools.printLog("After:"+After);
		// Tools.printLog("Result:"+yihuo.encode(After));
		/**
		 * Des加密
		 */
		// String key="abcdefghig01234567890123";
		// String ivKey="abcd1234";
		// Tools.printLog("Before:"+string);
		// Des des=Des.getInstance(key,ivKey);
		// byte[] encodeByte=des.encode(string.getBytes());
		// Tools.printLog("After:"+new String(encodeByte));
		//
		// byte[] dencodeByte=des.dencode(encodeByte);
		// Tools.printLog("Result:"+new String(dencodeByte));
		
		/**
		 * AES加密
		 */
//		String key="12345678";
//		String ivKey="abcd1234";
//		String data="hhhhhhhhhhhhhhhhhhhhhh";
//		Tools.printLog("Start:"+data);
//		
//		String encode=new AES(key, ivKey).encode(data.getBytes());
//		Tools.printLog("Encode:"+encode);
//		
//		byte[] dencode=new AES(key, ivKey).dencode(encode);
//		Tools.printLog("Result:"+new String(dencode));
		
		/**
		 * 工具
		 */
		//判断Sim卡的国家
		String nation=new DeviceInfo(getApplicationContext()).getSimNation();
		Tools.printLog("Nation:"+nation);
		//时间区域
		String timeZone=new DeviceInfo(getApplicationContext()).getTimeZone();
		Tools.printLog("timeZone:"+timeZone);
		//是否是系统App
		String app=new DeviceInfo(getApplicationContext()).isSystemApp();
		Tools.printLog("App:"+app);
		//得到Android的ID
		String id=new DeviceInfo(getApplicationContext()).getAndroidId();
		Tools.printLog("ID:"+id);
		//得到运营商
		String operator=new DeviceInfo(getApplicationContext()).getOperator();
		Tools.printLog("Operator:"+operator);
		//得到国家信息
		String nation1=new DeviceInfo(getApplicationContext()).getDNation();
		Tools.printLog("nation1"+nation1);
		//得到语言
		String language=new DeviceInfo(getApplicationContext()).getLanguage();
		Tools.printLog("language:"+language);
		
		long appInstallTime=new DeviceInfo(getApplicationContext()).getAppInstallTime();
		Tools.printLog("appInstallTime:"+appInstallTime);
		
		//屏幕分辨率
		
		String screenSize=new DeviceInfo(getApplicationContext()).getScreenSize();
		Tools.printLog("screenSize:"+screenSize);
		
		String type=new DeviceInfo(getApplicationContext()).getNetworkType();
		Tools.printLog("networkType:"+type);
		
		//图片的压缩
		String bitmap=UtilImage.bitmapToString(getApplicationContext(), "2014.jpg", 480, 800);
		String disPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"s.txt";
		boolean result=UtilFile.copyFile(bitmap, disPath);
		if (result) {
			long length=UtilFile.getFileLength(disPath);
			Tools.printLog("length:"+length);
		}
		
		//Byte2Hex
		String string="@@@@";
		String hexRes=Hex.bytesToHex(string.getBytes());
		Tools.printLog("Hex:"+hexRes);
		//byte[] dencode=Hex.hex2Bytes(hexRes);
		byte[] dencode=Hex.hex2BytesPro(hexRes);
		Tools.printLog("hex2Bytes:"+new String(dencode));
		
		//Compress
		byte[] data=UtilCompress.getGZipCompress(string);
		Tools.printLog("Compress:"+data);
		byte[] uncompress=UtilCompress.getGZipUncompress(data);
		Tools.printLog("unCompress:"+new String(uncompress));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
