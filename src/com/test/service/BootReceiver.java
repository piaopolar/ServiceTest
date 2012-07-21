package com.test.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context arg0, Intent arg1) {

		Log.v("CountService", "bootRev");
		// TODO Auto-generated method stub
		if (arg1.getAction().equals(ACTION)) {

			SharedPreferences settingsName = arg0.getSharedPreferences(
					"MyConfig", 0);
			String strPreName = settingsName.getString("ConfigPrefName", "");
			SharedPreferences settings = arg0.getSharedPreferences(strPreName,
					0);

			if (settings.getBoolean("scr_off_clean_switch", false)) {
				Intent intent = new Intent(arg0, CountService.class);
				intent.setAction(".com.test.service");
				arg0.startService(intent);
			}
		}
	}
}
