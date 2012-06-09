package com.test.service;

import com.test.service.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CountService extends Service {

	private int count;
	private boolean threadDisable;

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
				
				SharedPreferences settings;
				settings  = getSharedPreferences("MyConfig", 0);
				if (settings.getBoolean("Close Wifi", true)) {

					WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
					if (wifiManager.isWifiEnabled()) {
						wifiManager.setWifiEnabled(false);
					}
				}
				
				ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

				List<RunningAppProcessInfo> runningProcessList = null;

				// 获取正在运行的进程列表
				runningProcessList = activityManager.getRunningAppProcesses();
				RunningAppProcessInfo procInfo = null;



//				ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//				Method forceStopPackage;

				

				
				PackageUtil packageUtil = new PackageUtil(context);
				for (Iterator<RunningAppProcessInfo> iterator = runningProcessList
						.iterator(); iterator.hasNext();) {
					procInfo = iterator.next();
					Boolean bAutoClose = settings.getBoolean(
							procInfo.processName, false);

					if (bAutoClose) {
						Log.v("CountService", procInfo.processName + " true");
						ApplicationInfo tempAppInfo = packageUtil
								.getApplicationInfo(procInfo.processName);
						if (tempAppInfo == null) {
							return;
						}

						Log.v("CountService", "kill " + tempAppInfo.packageName
								+ " true");
						

//						try {
//							forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
//							forceStopPackage.setAccessible(true);  
//							forceStopPackage.invoke(am, tempAppInfo.packageName);
//						} catch (SecurityException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (NoSuchMethodException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IllegalArgumentException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IllegalAccessException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (InvocationTargetException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}  

						activityManager
								.killBackgroundProcesses(tempAppInfo.packageName);
					} else {
						// Log.v("CountService", procInfo.processName +
						// " false");
					}
				}
			}
		}, new IntentFilter(Intent.ACTION_SCREEN_OFF));

		new Thread(new Runnable() {

			public void run() {
				while (!threadDisable) {
					// try {
					// Thread.sleep(1000);
					// } catch (InterruptedException e) {
					// }
					// count++;
					// Log.v("CountService", "Count is " + count);
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