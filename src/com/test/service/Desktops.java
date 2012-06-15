package com.test.service;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Desktops extends AppWidgetProvider {
	  @Override  
	    public void onUpdate(Context context, AppWidgetManager appWidgetManager,  
	            int[] appWidgetIds) {  
	        // TODO Auto-generated method stub  
	        final int N = appWidgetIds.length;  
	        for (int i = 0; i < N; i++) {  
	            int appWidgetId = appWidgetIds[i];  
	            Log.d("myLog", "this is [" + appWidgetId + "] onUpdate!");  
	  
	        }  
	    }  
	  
	    @Override  
	    public void onDeleted(Context context, int[] appWidgetIds) {  
	        // TODO Auto-generated method stub  
	        final int N = appWidgetIds.length;  
	        for (int i = 0; i < N; i++) {  
	            int appWidgetId = appWidgetIds[i];  
	            Log.d("myLog", "this is [" + appWidgetId + "] onDelete!");  
	        }  
	    }
	    
	    void showToast(Context context, CharSequence text) {  
		      Toast.makeText(context, text, Toast.LENGTH_SHORT).show();  
		  }  

	    
	    public void onReceive(Context paramContext, Intent paramIntent)  {
	    	
	    	showToast(paramContext, "receive");  
	      int i = 0;
	      String str1 = paramIntent.getAction();
	      String str2 = "LockWidget::onReceive(" + str1 + ")";
	      Log.d("ToggleWidget", str2);
	      if (paramIntent.getAction() == null)
	      {
//	        Intent localIntent = new Intent(paramContext, UpdateService.class);
//	        paramContext.startService(localIntent);
	      }
	       super.onReceive(paramContext, paramIntent); 
	    }
}
