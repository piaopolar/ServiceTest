package com.test.service;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class AdminReceiver extends DeviceAdminReceiver {
	  
    /** 
     * ��ȡ�豸�洢����ֵ 
     *  
     * @param context 
     * @return 
     */  
//    public static SharedPreferences getDevicePreference(Context context) {  
//        return context.getSharedPreferences(  
//                DeviceAdminReceiver.class.getName(), 0);  
//    }  
//  
//    // ������ص�   
//    public static String PREF_PASSWORD_QUALITY = "password_quality";  
//    // ����ĳ���   
//    public static String PREF_PASSWORD_LENGTH = "password_length";  
//  
//    public static String PREF_MAX_FAILED_PW = "max_failed_pw";  
//  
//    void showToast(Context context, CharSequence text) {  
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();  
//    }  
//  
//    @Override  
//    public void onEnabled(Context context, Intent intent) {  
//        // TODO Auto-generated method stub   
//        showToast(context, "�豸��������");  
//    }  
//  
//    @Override  
//    public void onDisabled(Context context, Intent intent) {  
//        // TODO Auto-generated method stub   
//        showToast(context, "�豸����������");  
//    }  
//  
//    @Override  
//    public CharSequence onDisableRequested(Context context, Intent intent) {  
//        // TODO Auto-generated method stub   
//        return "����һ����ѡ����Ϣ�������йؽ�ֹ�û�������";  
//    }  
//  
//    @Override  
//    public void onPasswordChanged(Context context, Intent intent) {  
//        // TODO Auto-generated method stub   
//        showToast(context, "�豸�������뼺���ı�");  
//    }  
//  
//    @Override  
//    public void onPasswordFailed(Context context, Intent intent) {  
//        // TODO Auto-generated method stub   
//        showToast(context, "�豸�����ı�����ʧ��");  
//    }  
//  
//    @Override  
//    public void onPasswordSucceeded(Context context, Intent intent) {  
//        // TODO Auto-generated method stub   
//        showToast(context, "�豸�����ı�����ɹ�");  
//    }  
}
