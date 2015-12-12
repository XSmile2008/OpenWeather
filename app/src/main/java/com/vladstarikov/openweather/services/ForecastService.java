package com.vladstarikov.openweather.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vladstarikov.openweather.activities.MainActivity;

import java.util.Date;

/**
 * Created by Starikov on 11.12.15.
 */
public class ForecastService extends Service {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i(MainActivity.LOG_TAG, "nyan");
            Log.i(MainActivity.LOG_TAG, new Date().toString());
            //long endTime = System.currentTimeMillis() / 3600000 * 3600000 + 3600000;
            long endTime = System.currentTimeMillis() + 10000;
            Log.i(MainActivity.LOG_TAG, new Date(endTime).toString());
                try {
                    Thread.sleep(endTime -  System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            Log.i(MainActivity.LOG_TAG, "punyan");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("nyan");
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(MainActivity.LOG_TAG, this.getClass().getName());
        mServiceHandler.handleMessage(new Message());
        return START_STICKY;//super.onStartCommand(intent, flags, startId);
    }

}
