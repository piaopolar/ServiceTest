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
    
public class MyAdapter extends BaseAdapter {    
    private LayoutInflater mInflater;
    List<BasicProgramUtil> m_listProc;
    public MyAdapter(Context context, List<BasicProgramUtil> list) {    
        mInflater = LayoutInflater.from(context);
		this.m_listProc = list;
    }    
    
	public int getCount() {
		// TODO Auto-generated method stub
		return m_listProc.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return m_listProc.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
    
    public View getView(int position, View convertView, ViewGroup parent) {    
        ViewHolder holder = null;    
        //convertView为null的时候初始化convertView。    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.list, null);    
            holder.img = (ImageView) convertView.findViewById(R.id.img);    
            holder.title = (TextView) convertView.findViewById(R.id.title);    
            holder.cBox = (CheckBox) convertView.findViewById(R.id.cb);    
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }
        
		final BasicProgramUtil pUtils = m_listProc.get(position);
		// 设置图标
		holder.img.setImageDrawable(pUtils.getIcon());
		// 设置程序名
		holder.title.setText(pUtils.getProgramName());
        
        holder.cBox.setChecked(pUtils.bAutoClose);    
        return convertView;    
    }    
    
    public final class ViewHolder {    
        public ImageView img;    
        public TextView title;    
        public CheckBox cBox;    
    }    
}
