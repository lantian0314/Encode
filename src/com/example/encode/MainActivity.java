package com.example.encode;

import com.example.utils.Tools;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// “ÏªÚº”√‹
		String string = "hhhhhhhhhhhhhhhhhhhhh";
		// String After=yihuo.encode(string);
		// Tools.printLog("After:"+After);
		// Tools.printLog("Result:"+yihuo.encode(After));
		/**
		 * Desº”√‹
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
		
		RSA.main();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
