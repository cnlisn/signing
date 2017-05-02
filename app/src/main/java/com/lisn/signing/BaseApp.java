package com.lisn.signing;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2017/4/19.
 */

public class BaseApp extends Application {
    public static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;// 赋值
        CrashHandler.getInstance().init(getApplicationContext());
    }

    /**
     * 获取上下文
     * @return
     */
    public static Context getInstance() {
        return instance;
    }
}
