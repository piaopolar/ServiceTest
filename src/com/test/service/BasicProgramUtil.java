/**
 * Ӧ�ó�����ļ�Ҫ��Ϣ
 */
package com.test.service;

import java.util.Calendar;

import com.test.*;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BasicProgramUtil{
    
    /*
     * ����Ӧ�ó���ļ�Ҫ��Ϣ����
     */
    private Drawable icon;                            // ����ͼ��
    private String programName;                        // ��������
    private String processName;
    private String packageName;
    public Boolean bAutoClose;
    
    private String cpuMemString;
    
    public BasicProgramUtil() {
        icon = null;
        programName = "";    
        processName = "";    
        cpuMemString = "";
    }
    
    public BasicProgramUtil(Context context, PackageInfo packageInfo)
    {
    	icon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
    	programName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
    	processName = packageInfo.applicationInfo.processName;
    	packageName = packageInfo.applicationInfo.packageName;
        cpuMemString = "";
    }
    
    public Drawable getIcon() {
        return icon;
    }
    
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    
    public String getProgramName() {
        return programName;
    }
    
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getCpuMemString() {
        return cpuMemString;
    }

    public void setCpuMemString(String cpuMemString) {
        this.cpuMemString = cpuMemString;
    }
    
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}