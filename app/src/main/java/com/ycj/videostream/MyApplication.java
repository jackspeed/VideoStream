package com.ycj.videostream;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.ycj.videostream.utils.AppUtils;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 00:37
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppUtils.setmContext(this);
    }
}
