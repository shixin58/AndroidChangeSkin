package com.zhy.skinchangenow.sample;

import android.app.Application;

import com.zhy.changeskin.SkinManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
