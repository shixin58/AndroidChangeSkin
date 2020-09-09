package com.zhy.skinchangenow.sample;

import android.app.Application;

import com.zhy.changeskin.SkinManager;

public class MyApplication extends Application {
    public static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        SkinManager.getInstance().init(this);
    }
}
