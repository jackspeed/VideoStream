package com.ycj.videostream.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ycj.videostream.R;
import com.ycj.videostream.dao.InputCallBack;
import com.ycj.videostream.request.InputData;

public class MainActivity extends BaseActivity implements View.OnClickListener, InputCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_tv1).setOnClickListener(this);
        findViewById(R.id.main_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        showInputDialog(this);

    }

    @Override
    public void onClick(InputData input) {
        if (input.getServerUrl() == null || "".equals(input.getServerUrl())) {
            showToast("服务器地址不正确");
        }
        if (input.getUserName() == null || "".equals(input.getUserName())) {
            showToast("账号有误");
        }
        if (input.getPassword() == null || "".equals(input.getPassword())) {
            showToast("密码有误");
        }
        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        intent.putExtra("inputData", input);
        startActivity(intent);
    }

}
