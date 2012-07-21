package com.test.service;

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Desktops extends AppWidgetProvider {
	private static final int REQUEST_ENABLE = 0;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub

		RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.desktops);
		Intent actClick = new Intent("lock_scr");
		PendingIntent pendingClick = PendingIntent.getBroadcast(context, 0,
				actClick, 0);
		Intent actClean = new Intent("Clean");
		PendingIntent pendingClean = PendingIntent.getBroadcast(context, 0,
				actClean, 0);

		rv.setOnClickPendingIntent(R.id.btnLockScr, pendingClick);
		rv.setOnClickPendingIntent(R.id.btnMode, pendingClean);
		appWidgetManager.updateAppWidget(appWidgetIds, rv);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Log.d("myLog", "this is [" + appWidgetId + "] onDelete!");
		}
	}

	void showToast(Context context, CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public void onReceive(Context paramContext, Intent paramIntent) {

		String strAction = paramIntent.getAction();

		if (strAction.equals("lock_scr")) {
			Lock(paramContext);
		} else if (strAction.equals("Clean")) {
			DoClean(paramContext);
		}

		super.onReceive(paramContext, paramIntent);
	}

	public void Lock(Context paramContext) {
		DevicePolicyManager policyManager = (DevicePolicyManager) paramContext
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(paramContext,
				AdminReceiver.class);

		boolean active = policyManager.isAdminActive(componentName);

		if (!active) {

			// 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
			Intent intent = new Intent(paramContext, DesktopsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			paramContext.startActivity(intent);
		} else {
			policyManager.lockNow();// 直接锁屏
		}
	}

	public void DoClean(Context paramContext) {
		SharedPreferences settingsMy = paramContext.getSharedPreferences(
				"MyConfig", 0);

		showToast(paramContext, "DoClean");

		Log.v("MyLog", "Try CloseWifi");

		String strPreName = settingsMy.getString("ConfigPrefName", "");
		SharedPreferences settings = paramContext.getSharedPreferences(
				strPreName, 0);

		if (settings.getBoolean("clean_close_wifi_switch", false)) {

			WifiManager wifiManager = (WifiManager) paramContext
					.getSystemService(Context.WIFI_SERVICE);
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
