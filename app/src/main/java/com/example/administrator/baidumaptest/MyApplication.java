package com.example.administrator.baidumaptest;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyApplication extends Application {
    public LocationClient locationClient;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {

        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationClient = new LocationClient(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }
}
