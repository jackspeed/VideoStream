package com.ycj.videostream.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.ycj.videostream.request.InputData;
import com.ycj.videostream.R;
import com.ycj.videostream.dao.InputCallBack;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 10:46
 */

public class BaseActivity extends AppCompatActivity {


    private Dialog inputDialog;
    private Toast longToast;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        longToast = Toast.makeText(this, null, Toast.LENGTH_LONG);
        toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
    }

    public void showInputDialog(final InputCallBack inputCallBack) {
        try {
            hideInputDialog();
            inputDialog = new Dialog(this);
            inputDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            inputDialog.setCanceledOnTouchOutside(true);
            inputDialog.setCancelable(true);
            inputDialog.show();
            Window window = inputDialog.getWindow();
            View alertView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null);
            Button btnClose = alertView.findViewById(R.id.btn_close_dialog);
            Button btnOk = alertView.findViewById(R.id.btn_ok_dialog);
            final EditText serverStream = alertView.findViewById(R.id.edx_stream);
            final EditText serverUrlEdx = alertView.findViewById(R.id.edx_server);
            final EditText userNameEdx = alertView.findViewById(R.id.edx_user_name);
            final EditText passwordEdx = alertView.findViewById(R.id.edx_password);
            window.setContentView(alertView);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideInputDialog();
                }
            });
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stream = serverStream.getText().toString();
                    String serverUrl = serverUrlEdx.getText().toString();
                    String userName = userNameEdx.getText().toString();
                    String password = passwordEdx.getText().toString();
                    InputData inputData = new InputData(serverUrl, userName, password, stream);
                    inputCallBack.onClick(inputData);
                    hideInputDialog();
                }
            });
            /** 3.自动弹出软键盘 **/
            inputDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(serverUrlEdx, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hideInputDialog() {
        if (inputDialog != null) {
            inputDialog.dismiss();
            inputDialog = null;
        }
    }

    public void showToast(String msg) {
        ToastUtils.cancel();
        ToastUtils.showShort(msg);
    }


    public void showLongToast(String msg) {
        ToastUtils.cancel();
        ToastUtils.showLong(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (longToast != null) {
            longToast.cancel();
        }
        if (toast != null) {
            toast.cancel();
        }
    }
}
