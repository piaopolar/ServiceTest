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

		setResult(RESULT_CANCELED);
		// Find the widget id from the intent.
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		// If they gave us an intent without the widget id, just bail.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}

		mImBtn1 = (ImageButton) findViewById(R.id.btnLockScr);
		mImBtn2 = (ImageButton) findViewById(R.id.btnMode);
		
		findViewById(R.id.btnLockScr).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Lock(v);
			}
		});		
		
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
		}
	}

	public void Lock(View view) {
		DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName componentName = new ComponentName(this,
				AdminReceiver.class);

		boolean active = policyManager.isAdminActive(componentName);

		if (!active) {

			// �����豸����(��ʽIntent) - ��AndroidManifest.xml���趨��Ӧ������
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

			// Ȩ���б�
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					componentName);

			// ����(additional explanation)
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"------ �������� ------");

			startActivityForResult(intent, REQUEST_ENABLE);
		} else {
			policyManager.lockNow();// ֱ������
		}
	}


//	OnClickListener mBtnClick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			int srcId = R.drawable.sketchy_paper_008;
//			switch (v.getId()) {
//			case R.id.ImageButton01:
//				srcId = R.drawable.sketchy_paper_003;
//				break;
//			case R.id.ImageButton02:
//				srcId = R.drawable.sketchy_paper_004;
//				break;
//			case R.id.ImageButton03:
//				srcId = R.drawable.sketchy_paper_007;
//				break;
//			case R.id.ImageButton04:
//				srcId = R.drawable.sketchy_paper_011;
//				break;
//			}
//			Log.i("myLog", "mAppWidgetId is: " + mAppWidgetId);
//
//			RemoteViews views = new RemoteViews(MyNoteConf.this
//					.getPackageName(), R.layout.my_note_widget);
//			views.setImageViewResource(R.id.my_widget_img, srcId);
//
//			AppWidgetManager appWidgetManager = AppWidgetManager
//					.getInstance(MyNoteConf.this);
//			appWidgetManager.updateAppWidget(mAppWidgetId, views);
//
//			// return OK
//			Intent resultValue = new Intent();
//			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//					mAppWidgetId);
//
//			setResult(RESULT_OK, resultValue);
//			finish();
//		}
//	};
}


