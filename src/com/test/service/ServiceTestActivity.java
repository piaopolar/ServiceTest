package com.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.test.service.*;
import com.test.service.MyAdapter.ViewHolder;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ServiceTestActivity extends Activity {

	private static final int REQUEST_ENABLE = 0;

	private static List<BasicProgramUtil> infoList = new ArrayList<BasicProgramUtil>();;

	SharedPreferences settings;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		settings = getSharedPreferences("MyConfig", 0);

		if (settings.getBoolean("Service On", true)) {
			// this.startService(new Intent(this, CountService.class));
		}

		ListView list = (ListView) findViewById(R.id.lv);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();
				// 在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。
				vHollder.cBox.toggle();
				infoList.get(position).bAutoClose = !infoList.get(position).bAutoClose;

				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(infoList.get(position).getProcessName(),
						infoList.get(position).bAutoClose);
				editor.commit();
			}
		});

		findViewById(R.id.reflash).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
				ComponentName componentName = new ComponentName(
						ServiceTestActivity.this, AdminReceiver.class);

				boolean active = policyManager.isAdminActive(componentName);

				if (active) {

					policyManager.removeActiveAdmin(componentName);

				}

				ReflashProcList();
			}
		});

		findViewById(R.id.setting).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				 Intent SecondPage = new Intent(ServiceTestActivity.this,
				 SettingActivity.class);
				 startActivity(SecondPage);
			}
		});
	}

	protected void onResume() {

		ReflashProcList();
		super.onResume();
		Log.v("CountService", "onResume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// SaveAutoCloseInfo();

		// this.stopService(new Intent(this, CountService.class));
	}

	public void ReflashProcList() {

		if (!infoList.isEmpty()) {
			infoList.clear();
		}

		List<PackageInfo> lstPackageInfo = getPackageManager()
				.getInstalledPackages(0);

		for (Iterator<PackageInfo> iterator = lstPackageInfo.iterator(); iterator
				.hasNext();) {
			PackageInfo packageInfo = iterator.next();
			BasicProgramUtil programUtil = new BasicProgramUtil(this,
					packageInfo);
			programUtil.bAutoClose = settings.getBoolean(
					programUtil.getProcessName(), false);

			if (settings.getBoolean("ShowSystemApp", false)) {
				infoList.add(programUtil);
			} else if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				infoList.add(programUtil);
			}
		}

		ListView list = (ListView) findViewById(R.id.lv);
		MyAdapter adapter = new MyAdapter(this, infoList);
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	private BasicProgramUtil BasicProgramUtil(
			ServiceTestActivity serviceTestActivity, PackageInfo next) {
		// TODO Auto-generated method stub
		return null;
	}

	public void SaveAutoCloseInfo() {
		SharedPreferences.Editor editor = settings.edit();
		for (Iterator<BasicProgramUtil> iterator = infoList.iterator(); iterator
				.hasNext();) {
			BasicProgramUtil info = iterator.next();
			if (info.bAutoClose) {
				editor.putBoolean(info.getProcessName(), true);
			}
		}

		editor.commit();
	}
}