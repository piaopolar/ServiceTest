package com.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CountService extends Service {

    private boolean threadDisable;

    private int count;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {

            public void run() {
                while (!threadDisable) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    count++;
                    Log.v("CountService", "Count is " + count);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.threadDisable = true;
        Log.v("CountService", "on destroy");
    }

    public int getCount() {
        return count;
    }
}