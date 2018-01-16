package com.ycj.videostream;

import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.VideoView;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-16 20:06
 */

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    public static String URL = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private GLSurfaceView mSurfaceView;
    private RtspSurfaceRender mRender;
    private FrameLayout frameLayout;
    private VideoView videoView;
    private int gender;
    private Button playBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mRender = new RtspSurfaceRender(mSurfaceView);
        mRender.setRtspUrl(URL);
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
        videoView = new VideoView(this);
        videoView.setVisibility(View.GONE);
        videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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

    private void startLocalView() {
        try {
            Uri uri;
            if (gender == 1) {
                uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.female);
                gender = 2;
            } else {
                uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.male);
                gender = 1;
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
    protected void onResume() {
        super.onResume();
//        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
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
                startLocalView();
                return;
            }
            stopPlaybackVideo();
            startStreamVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
