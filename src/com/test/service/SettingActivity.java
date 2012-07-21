package com.test.service;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SettingActivity extends PreferenceActivity implements
		OnPreferenceClickListener {

	private CheckBoxPreference m_prefChkReq;
	private CheckBoxPreference m_prefNeedService;
	DevicePolicyManager policyManager;
	ComponentName componentName;
	SharedPreferences AppSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);

		String preferencesName = this.getPreferenceManager()
				.getSharedPreferencesName();
		SharedPreferences NameSettings = getSharedPreferences("MyConfig", 0);
		SharedPreferences.Editor editor = NameSettings.edit();
		editor.putString("ConfigPrefName", preferencesName);
		editor.commit();

		AppSettings = getSharedPreferences(preferencesName, 0);
		
	}

	protected void onResume() {

		m_prefNeedService = (CheckBoxPreference) findPreference("scr_off_clean_switch");
		m_prefNeedService.setOnPreferenceClickListener(this);

		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, AdminReceiver.class);

		boolean active = policyManager.isAdminActive(componentName);

		m_prefChkReq = (CheckBoxPreference) findPreference("req_admin_switch");
		m_prefChkReq.setOnPreferenceClickListener(this);
		m_prefChkReq.setChecked(active);
		super.onResume();
		Log.v("CountService", "onResume");
	}

	public boolean onPreferenceClick(Preference preference) {
		Log.v("CountService", "onPreferenceClick");
		if (preference == m_prefChkReq) {
			boolean active = policyManager.isAdminActive(componentName);
			if (active) {
				policyManager.removeActiveAdmin(componentName);
			} else {
				Intent intent = new Intent(
						DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
						componentName);
				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
						"One key lock screen need to active");
				startActivity(intent);
			}
		} else if (preference == m_prefNeedService) {
			boolean bServiceOn = AppSettings.getBoolean("scr_off_clean_switch", false);
			if (bServiceOn) {
				Log.v("CountService", "Start service");
				this.startService(new Intent(this, CountService.class));
			} else {
				Log.v("CountService", "Stop service");
				this.stopService(new Intent(this, CountService.class));
			}
		}

		return true;
	}
}
