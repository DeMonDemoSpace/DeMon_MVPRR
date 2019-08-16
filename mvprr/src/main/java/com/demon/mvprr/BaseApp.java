package com.demon.mvprr;

import android.app.Application;
import android.content.Context;

/**
 * @author DeMon
 * @date 2019/8/15
 * @email 757454343@qq.com
 * @description
 */
public class BaseApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApp.context = context;
    }
}
