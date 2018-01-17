package com.ycj.videostream.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 12:08
 */

public class JackVideoView extends VideoView {
    public JackVideoView(Context context) {
        super(context);
    }

    public JackVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JackVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(1080, widthMeasureSpec);
        int height = getDefaultSize(1920, heightMeasureSpec);//
        // 返回一个固定的比例再赋值给 width height。
        setMeasuredDimension(width, height);
    }
}
