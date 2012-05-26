package com.test.service;

import com.test.service.MyAdapter.ViewHolder;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ServiceTestActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView list = (ListView) findViewById(R.id.lv);
		MyAdapter adapter = new MyAdapter(this);
		list.setAdapter(adapter);
		list.setItemsCanFocus(false);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();
				// 在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。
				vHollder.cBox.toggle();
				MyAdapter.isSelected.put(position, vHollder.cBox.isChecked());
			}
		});

		this.startService(new Intent(this, CountService.class));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.stopService(new Intent(this, CountService.class));
	}
}