package com.test.service;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class Desktops extends AppWidgetProvider {
	  @Override  
	    public void onUpdate(Context context, AppWidgetManager appWidgetManager,  
	            int[] appWidgetIds) {  
	        // TODO Auto-generated method stub  
	        final int N = appWidgetIds.length;  
	        for (int i = 0; i < N; i++) {  
	            int appWidgetId = appWidgetIds[i];  
	            Log.v("myLog", "this is [" + appWidgetId + "] onUpdate!");  
	  
	        }  
	    }  
	  
	    @Override  
	    public void onDeleted(Context context, int[] appWidgetIds) {  
	        // TODO Auto-generated method stub  
	        final int N = appWidgetIds.length;  
	        for (int i = 0; i < N; i++) {  
	            int appWidgetId = appWidgetIds[i];  
	            Log.v("myLog", "this is [" + appWidgetId + "] onDelete!");  
	        }  
	    }  
}
