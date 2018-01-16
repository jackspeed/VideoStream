package com.ycj.videostream;

import android.app.Application;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 00:37
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setmContext(this);
    }
}
