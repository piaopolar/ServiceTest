package com.test.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

public class CountService extends Service {

	private boolean threadDisable;

	private int count;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				if (wifiManager.isWifiEnabled()) {
					wifiManager.setWifiEnabled(false);
				}
			}
		}, new IntentFilter(Intent.ACTION_SCREEN_OFF));

		new Thread(new Runnable() {

			public void run() {
				while (!threadDisable) {
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//					}
//					count++;
//					Log.v("CountService", "Count is " + count);
				}
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.threadDisable = true;
		Log.v("CountService", "on destroy");
	}

	public int getCount() {
		return count;
	}
}