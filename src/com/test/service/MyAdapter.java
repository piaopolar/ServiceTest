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
    private List<Map<String, Object>> mData;    
    public static Map<Integer, Boolean> isSelected;    
    
    public MyAdapter(Context context, List<BasicProgramUtil> list) {    
        mInflater = LayoutInflater.from(context);
		this.m_listProc = list;
    }    
    
    //初始化    
    private void init() {    
        mData=new ArrayList<Map<String, Object>>();    
        for (int i = 0; i < 5; i++) {    
            Map<String, Object> map = new HashMap<String, Object>();    
            map.put("img", android.R.drawable.btn_star);    
            map.put("title", "第" + (i + 1) + "行的标题");    
            mData.add(map);    
        }    
        //这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。    
        isSelected = new HashMap<Integer, Boolean>();    
        for (int i = 0; i < mData.size(); i++) {    
            isSelected.put(i, false);    
        }    
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
        
        holder.cBox.setChecked(false);    
        return convertView;    
    }    
    
    public final class ViewHolder {    
        public ImageView img;    
        public TextView title;    
        public CheckBox cBox;    
    }    
}
