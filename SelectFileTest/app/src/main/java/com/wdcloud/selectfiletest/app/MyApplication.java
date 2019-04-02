package com.wdcloud.selectfiletest.app;

import android.app.Application;

/**
 * Created by Umbrella on 2019/3/22.
 */

public class MyApplication extends Application{
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static MyApplication getInstance() {
        return instance;
    }
}
