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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ServiceTestActivity extends Activity {

	// 获取包管理器和活动管理器的实例
	private static PackageManager packageManager = null;
	private static ActivityManager activityManager = null;

	// 正在运行的进程列表
	private static List<RunningAppProcessInfo> runningProcessList = null;
	private static List<BasicProgramUtil> infoList = null;
	private static PackageUtil packageUtil = null;
	ProcessMemoryUtil processMemoryUtil = null;

	SharedPreferences settings;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.startService(new Intent(this, CountService.class));

		// 获取包管理器，主要通过包管理器获取程序的图标和程序名
		packageManager = this.getPackageManager();
		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		packageUtil = new PackageUtil(this);

		// 获取正在运行的进程列表
		runningProcessList = new ArrayList<RunningAppProcessInfo>();
		infoList = new ArrayList<BasicProgramUtil>();

		settings = getSharedPreferences("MyConfig", 0);

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

	}

	protected void onResume() {
		super.onResume();  

		ReflashProcList();
		Log.v("CountService", "onResume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

//		SaveAutoCloseInfo();

		// this.stopService(new Intent(this, CountService.class));
	}

	public void ReflashProcList() {

		if (!runningProcessList.isEmpty()) {
			runningProcessList.clear();
		}
		
		if (!infoList.isEmpty()) {
			infoList.clear();
		}

		// 获取正在运行的进程列表
		runningProcessList = activityManager.getRunningAppProcesses();
		// 获取当前进程的内存和CPU信息
		processMemoryUtil = new ProcessMemoryUtil();
		processMemoryUtil.initPMUtil();

		RunningAppProcessInfo procInfo = null;
		for (Iterator<RunningAppProcessInfo> iterator = runningProcessList
				.iterator(); iterator.hasNext();) {
			procInfo = iterator.next();
			BasicProgramUtil programUtil = buildProgramUtilSimpleInfo(
					procInfo.pid, procInfo.processName);
			programUtil.bAutoClose = settings.getBoolean(procInfo.processName,
					false);
			infoList.add(programUtil);
		}

		ListView list = (ListView) findViewById(R.id.lv);
		MyAdapter adapter = new MyAdapter(this, infoList);
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		Log.v("CountService", "RunList " + String.valueOf(runningProcessList.size()));
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

	/*
	 * 为进程获取基本的信息
	 */
	public BasicProgramUtil buildProgramUtilSimpleInfo(int procId,
			String procNameString) {

		BasicProgramUtil programUtil = new BasicProgramUtil();
		programUtil.setProcessName(procNameString);

		// 根据进程名，获取应用程序的ApplicationInfo对象
		ApplicationInfo tempAppInfo = packageUtil
				.getApplicationInfo(procNameString);

		if (tempAppInfo != null) {
			// 为进程加载图标和程序名称
			programUtil.setIcon(tempAppInfo.loadIcon(packageManager));
			programUtil.setProgramName(tempAppInfo.loadLabel(packageManager)
					.toString());
		} else {
			// 如果获取失败，则使用默认的图标和程序名
			programUtil.setIcon(getApplicationContext().getResources()
					.getDrawable(android.R.drawable.btn_star));
			programUtil.setProgramName(procNameString);
			// Log.v(ProcMgrActivity.TAG_SYSTEMASSIST, procNameString);
		}

		String str = processMemoryUtil.getMemInfoByPid(procId);
		// Log.v(TAG_SYSTEMASSIST, "Time --- > " +
		// Calendar.getInstance().getTimeInMillis());
		programUtil.setCpuMemString(str);
		return programUtil;
	}
}