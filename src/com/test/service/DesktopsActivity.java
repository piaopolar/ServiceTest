package com.test.service;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class DesktopsActivity extends Activity {
	private static final int REQUEST_ENABLE = 0;
	int mAppWidgetId;
	ImageButton mImBtn1, mImBtn2, mImBtn3, mImBtn4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desktops);
		Log.v("myLog", " on WidgetConf ... ");
		
		Lock();
				

//		setResult(RESULT_CANCELED);
//		// Find the widget id from the intent.
//		Intent intent = getIntent();
//		Bundle extras = intent.getExtras();
//		if (extras != null) {
//			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
//					AppWidgetManager.INVALID_APPWIDGET_ID);
//		}
//
//		// If they gave us an intent without the widget id, just bail.
//		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//			finish();
//		}

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_ENABLE == requestCode) {
			if (resultCode == Activity.RESULT_OK) {
				DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
				ComponentName componentName = new ComponentName(this,
						AdminReceiver.class);

				boolean active = policyManager.isAdminActive(componentName);
				if (active) {
					policyManager.lockNow();
				}
			} else {
			}
			
			finish();
		}
	}

	public void Lock() {
		DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(this,
				AdminReceiver.class);

		boolean active = policyManager.isAdminActive(componentName);

		if (!active) {

			// 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

			// 权限列表
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					componentName);

			// 描述(additional explanation)
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"------ 其他描述 ------");

			startActivityForResult(intent, REQUEST_ENABLE);
		} else {
			policyManager.lockNow();// 直接锁屏
		}
	}
}


