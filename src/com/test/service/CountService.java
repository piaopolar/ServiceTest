package com.test.service;

import com.test.service.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CountService extends Service {

	private int count;
	private boolean threadDisable;
	private long time_close_process;
	private BroadcastReceiver scr_off_receiver;
	private BroadcastReceiver scr_on_receiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressLint("ParserError")
	@Override
	public void onCreate() {
		super.onCreate();

		this.threadDisable = false;
		time_close_process = 0;

		scr_off_receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				SharedPreferences settingsName = getSharedPreferences(
						"MyConfig", 0);
				String strPreName = settingsName
						.getString("ConfigPrefName", "");
				SharedPreferences settings = getSharedPreferences(strPreName, 0);

				Log.v("CountService", "Receive Scr Off");

				if (settings.getBoolean("scr_off_clean_switch", false)) {
					String strDelay = settings.getString("scr_off_clean_delay",
							"0");

					Log.v("CountService", strDelay);
					int nDelay = Integer.parseInt(strDelay);
					if (nDelay > 0) {
						time_close_process = System.currentTimeMillis()
								+ nDelay * 1000;
					} else {
						DoClean();
					}
				}
			}
		};

		registerReceiver(scr_off_receiver, new IntentFilter(
				Intent.ACTION_SCREEN_OFF));

		scr_on_receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				time_close_process = 0;
			}
		};

		registerReceiver(scr_on_receiver, new IntentFilter(
				Intent.ACTION_SCREEN_ON));

		new Thread(new Runnable() {

			public void run() {
				while (!threadDisable) {
					if (time_close_process > 0) {
						long nTime = System.currentTimeMillis();
						if (nTime > time_close_process) {
							DoClean();
						}
					}
				}
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(scr_off_receiver);
		unregisterReceiver(scr_on_receiver);

		this.threadDisable = true;
		Log.v("CountService", "on destroy");
		super.onDestroy();
	}

	public int getCount() {
		return count;
	}

	void showToast(Context context, CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public void DoClean() {
		SharedPreferences settingsMy = getSharedPreferences("MyConfig", 0);

		// showToast(this, "DoClean");

		Log.v("MyLog", "Try CloseWifi");

		String strPreName = settingsMy.getString("ConfigPrefName", "");
		SharedPreferences settings = getSharedPreferences(strPreName, 0);

		if (settings.getBoolean("clean_close_wifi_switch", false)) {

			WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			if (wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(false);
			}
		}

		Log.v("MyLog", "Try CloseBlueTooth");

		if (settings.getBoolean("clean_close_bluetooth_switch", false)) {
			BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			int state = adapter.getState();
			if (state == BluetoothAdapter.STATE_ON) {
				adapter.disable();
			}
		}
	}

}