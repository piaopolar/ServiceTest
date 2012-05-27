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

	// ��ȡ���������ͻ��������ʵ��
	private static PackageManager packageManager = null;
	private static ActivityManager activityManager = null;

	// �������еĽ����б�
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

		// ��ȡ������������Ҫͨ������������ȡ�����ͼ��ͳ�����
		packageManager = this.getPackageManager();
		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		packageUtil = new PackageUtil(this);

		// ��ȡ�������еĽ����б�
		runningProcessList = new ArrayList<RunningAppProcessInfo>();
		infoList = new ArrayList<BasicProgramUtil>();

		settings = getSharedPreferences("MyConfig", 0);

		ListView list = (ListView) findViewById(R.id.lv);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();
				// ��ÿ�λ�ȡ�����itemʱ�����ڵ�checkbox״̬�ı䣬ͬʱ�޸�map��ֵ��
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

		// ��ȡ�������еĽ����б�
		runningProcessList = activityManager.getRunningAppProcesses();
		// ��ȡ��ǰ���̵��ڴ��CPU��Ϣ
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
	 * Ϊ���̻�ȡ��������Ϣ
	 */
	public BasicProgramUtil buildProgramUtilSimpleInfo(int procId,
			String procNameString) {

		BasicProgramUtil programUtil = new BasicProgramUtil();
		programUtil.setProcessName(procNameString);

		// ���ݽ���������ȡӦ�ó����ApplicationInfo����
		ApplicationInfo tempAppInfo = packageUtil
				.getApplicationInfo(procNameString);

		if (tempAppInfo != null) {
			// Ϊ���̼���ͼ��ͳ�������
			programUtil.setIcon(tempAppInfo.loadIcon(packageManager));
			programUtil.setProgramName(tempAppInfo.loadLabel(packageManager)
					.toString());
		} else {
			// �����ȡʧ�ܣ���ʹ��Ĭ�ϵ�ͼ��ͳ�����
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