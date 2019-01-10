package com.demon.mvprr.app;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}
