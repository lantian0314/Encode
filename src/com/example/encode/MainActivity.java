package com.example.encode;

import com.example.utils.Tools;

import android.os.Bundle;
import android.app.Activity;
import android.util.Base64;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//异或加密
		String string="hhhhhhhhhhhhhhhhhhhhh";
//		String After=yihuo.encode(string);
//		Tools.printLog("After:"+After);
//		Tools.printLog("Result:"+yihuo.encode(After));
		/**
		 * Des加密
		 */
//		String key="abcdefghig01234567890123";
//		String ivKey="abcd1234";	
//		Tools.printLog("之前:"+string);
//		Des des=Des.getInstance(key,ivKey);
//		byte[] encodeByte=des.encode(string.getBytes());
//		Tools.printLog("After:"+new String(encodeByte));
//		
//		byte[] dencodeByte=des.dencode(encodeByte);
//		Tools.printLog("Result:"+new String(dencodeByte));
		
		/**
		 * AES加密
		 */
		String key="12345678";
		String ivKey="abcd1234";
		String data="hhhhhhhhhhhhhhhhh";
		Tools.printLog("Start:"+data);
		
		String encode=new AES(key, ivKey).encode(data.getBytes());
		Tools.printLog("Encode:"+encode);
		
		byte[] buffer=new AES(key, ivKey).dencode(encode);
		Tools.printLog("After:"+new String(buffer));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
