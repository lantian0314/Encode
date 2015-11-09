package com.example.encode;

import com.example.utils.DeviceInfo;
import com.example.utils.Tools;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ������
		String string = "hhhhhhhhhhhhhhhhhhhhh";
		// String After=yihuo.encode(string);
		// Tools.printLog("After:"+After);
		// Tools.printLog("Result:"+yihuo.encode(After));
		/**
		 * Des����
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
		 * AES����
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
		 * ����
		 */
		//�ж�Sim���Ĺ���
		String nation=new DeviceInfo(getApplicationContext()).getSimNation();
		Tools.printLog("Nation:"+nation);
		//ʱ������
		String timeZone=new DeviceInfo(getApplicationContext()).getTimeZone();
		Tools.printLog("timeZone:"+timeZone);
		//�Ƿ���ϵͳApp
		String app=new DeviceInfo(getApplicationContext()).isSystemApp();
		Tools.printLog("App:"+app);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
