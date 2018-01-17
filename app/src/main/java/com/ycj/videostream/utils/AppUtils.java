package com.ycj.videostream.utils;

import android.content.Context;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 00:38
 */

public class AppUtils {
    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context context) {
        mContext = context;
    }
}
