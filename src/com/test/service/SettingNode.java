/**
 * 应用程序包的简要信息
 */
package com.test.service;

import java.util.Calendar;

import com.test.*;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class SettingNode {

	private String desc;
	public Boolean set;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getSet() {
		return set;
	}

	public void setSet(Boolean set) {
		this.set = set;
	}

}