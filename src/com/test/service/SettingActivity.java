package com.test.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.test.service.MyAdapter.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingActivity extends Activity {

	static List<SettingNode> settingList = new ArrayList<SettingNode>();
	SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		settings = getSharedPreferences("MyConfig", 0);

		ReflashSettingList();

		ListView list = (ListView) findViewById(R.id.lstSetting);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();
				vHollder.cBox.toggle();
				settingList.get(position).setSet(
						!settingList.get(position).getSet());

				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean(settingList.get(position).getDesc(),
						settingList.get(position).getSet());
				editor.commit();

				if (settingList.get(position).getDesc() == "Service On") {
					if (settingList.get(position).getSet()) {
						SettingActivity.this.startService(new Intent(
								SettingActivity.this, CountService.class));
					} else {
						SettingActivity.this.stopService(new Intent(
								SettingActivity.this, CountService.class));
					}
				}
			}
		});

		findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent SecondPage = new Intent(SettingActivity.this,
						ServiceTestActivity.class);
				startActivity(SecondPage);
			}
		});
	}

	public void ReflashSettingList() {

		settingList.clear();

		SettingNode node = new SettingNode();
		node.setDesc("Service On");
		node.setSet(settings.getBoolean(node.getDesc(), true));
		settingList.add(node);

		node = new SettingNode();
		node.setDesc("Close Wifi");
		node.setSet(settings.getBoolean(node.getDesc(), true));
		settingList.add(node);

		ListView list = (ListView) findViewById(R.id.lstSetting);
		SettingAdapter adapter = new SettingAdapter(this, settingList);
		list.setAdapter(adapter);
		// list.setItemsCanFocus(false);
		// list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
}
