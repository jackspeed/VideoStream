package com.ycj.videostream.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.blankj.utilcode.util.ScreenUtils;
import com.ycj.videostream.R;
import com.ycj.videostream.dao.OnDismissListener;
import com.ycj.videostream.entity.PersonInfo;
import com.ycj.videostream.request.Info;
import com.ycj.videostream.request.InputData;
import com.ycj.videostream.utils.RtspSurfaceRender;
import com.ycj.videostream.view.JackVideoView;

import java.lang.ref.WeakReference;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-16 20:06
 */

public class VideoActivity extends BaseActivity implements View.OnClickListener, OnDismissListener {
    public String streamUrl;
    private GLSurfaceView mSurfaceView;
    private RtspSurfaceRender mRender;
    private FrameLayout frameLayout;
    private VideoView videoView;
    private Button playBtn;
    private long delayMillis = 1000;
    private InputData inputData;

    private void messageAction() {
        String result;
        if (inputData == null) {
            result = Info.testInfo();
        } else {
            result = Info.getInfo(inputData.getServerUrl(), inputData.getUserName(), inputData.getPassword());
        }
        if (videoView.isPlaying()) {

        } else if (result == null && videoView.getVisibility() == View.VISIBLE) {
            stopPlaybackVideo();
            startStreamVideo();
        } else if ("1".equals(result)) {
            showImageDialog(new PersonInfo(1), this);
        } else if ("2".equals(result)) {
            showImageDialog(new PersonInfo(2), this);
        } else if ("3".equals(result)) {
            onDismiss(1);
        }
        System.out.println(result + "    **********************");
        mHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    private MyHandler mHandler = new MyHandler(this);

    @Override
    public void onDismiss(int showType) {
        switch (showType) {
            case 1:
                closeStreamVideo();
                startLocalView(1);
                break;
            case 2:
                closeStreamVideo();
                startLocalView(2);
                break;
            default:
                closeStreamVideo();
                startLocalView(1);
                showLongToast("服务器地址不正确或账号密码错误");
                break;
        }
    }

    private class MyHandler extends Handler {
        /**
         * 弱引用 ，防止内存泄露
         */
        private WeakReference<VideoActivity> weakReference;

        MyHandler(VideoActivity handlerMemoryActivity) {
            weakReference = new WeakReference<>(handlerMemoryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VideoActivity handlerMemoryActivity = weakReference.get();
            if (handlerMemoryActivity != null) {
                messageAction();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setFullScreen(this);
        inputData = (InputData) getIntent().getSerializableExtra("inputData");
        if (inputData == null) {
            streamUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        } else {
            streamUrl = inputData.getStreamUrl();
        }
        mHandler.sendEmptyMessageDelayed(0, 1000);
        initView();
        startStreamVideo();
    }

    private void startStreamVideo() {
        closeStreamVideo();
        videoView.setVisibility(View.GONE);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setLayoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mSurfaceView.setEGLContextClientVersion(3);
        mSurfaceView.refreshDrawableState();
        mRender = new RtspSurfaceRender(mSurfaceView);
        mRender.setRtspUrl(streamUrl);
        mSurfaceView.setRenderer(mRender);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        frameLayout.removeAllViews();
        frameLayout.addView(mSurfaceView, 0);
    }

    private void closeStreamVideo() {
        try {
            mSurfaceView.destroyDrawingCache();
            mRender.onSurfaceDestoryed();
            mSurfaceView = null;
            mRender = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_video);

        frameLayout = findViewById(R.id.frame_layout);
        playBtn = findViewById(R.id.btn_play);
        playBtn.setOnClickListener(this);
        playBtn.setVisibility(View.GONE);
        videoView = new JackVideoView(this);
        videoView.setVisibility(View.GONE);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);
        videoView.setKeepScreenOn(true);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaybackVideo();
                startStreamVideo();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopPlaybackVideo();
                startStreamVideo();
                return true;
            }
        });
    }

    private void startLocalView(int type) {
        try {
            if (videoView.getVisibility() == View.VISIBLE) {
                return;
            }
            Uri uri;
            if (type == 2) {
                uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.female);
            } else {
                uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.male);
            }
            videoView.setVisibility(View.VISIBLE);
            frameLayout.removeAllViews();
            frameLayout.addView(videoView, 0);
            videoView.setVideoURI(uri);
            videoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopPlaybackVideo() {
        try {
            videoView.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        try {
            mHandler.removeCallbacksAndMessages(null);
            stopPlaybackVideo();
            closeStreamVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        try {
            if (videoView.getVisibility() == View.GONE) {
                closeStreamVideo();
                startLocalView(1);
                return;
            }
            stopPlaybackVideo();
            startStreamVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
