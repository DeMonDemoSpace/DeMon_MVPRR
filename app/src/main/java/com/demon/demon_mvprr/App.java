package com.demon.demon_mvprr;

import com.demon.mvprr.BaseApp;

/**
 * @author DeMon
 * @date 2019/8/15
 * @email 757454343@qq.com
 * @description
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //如果你Application已经继承，可以如下设置
        //BaseApp.setContext(getApplicationContext());
    }
}
