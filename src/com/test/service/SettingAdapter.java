package com.test.service;

import com.test.service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	List<SettingNode> m_listSetting;

	public SettingAdapter(Context context, List<SettingNode> list) {
		mInflater = LayoutInflater.from(context);
		this.m_listSetting = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return m_listSetting.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return m_listSetting.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// convertView为null的时候初始化convertView。
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.cBox = (CheckBox) convertView.findViewById(R.id.cb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final SettingNode pUtils = m_listSetting.get(position);
		holder.title.setText(pUtils.getDesc());
		holder.cBox.setChecked(pUtils.getSet());
		return convertView;
	}

	public final class ViewHolder {
		public TextView title;
		public CheckBox cBox;
	}
}
