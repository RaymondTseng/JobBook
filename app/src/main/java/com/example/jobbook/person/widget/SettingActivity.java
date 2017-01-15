package com.example.jobbook.person.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.update.widget.UpdatePwdActivity;
import com.example.jobbook.util.DataCleanManager;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageButton mBackImageButton;
    private Button mChangePhoneButton;
    private Button mChangePwdButton;
//    private Button mNodificationButton;
    private Button mClearCacheButton;
    private Button mAboutButton;
    private TextView mCacheTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.setting_back_ib);
        mChangePhoneButton = (Button) findViewById(R.id.setting_change_phone_bt);
        mChangePwdButton = (Button) findViewById(R.id.setting_change_password_bt);
//        mNodificationButton = (Button) findViewById(R.id.setting_nodification_bt);
        mClearCacheButton = (Button) findViewById(R.id.setting_clearcache_bt);
        mAboutButton = (Button) findViewById(R.id.setting_about_bt);
        mCacheTextView = (TextView) findViewById(R.id.show_cache_tv);
        mBackImageButton.setOnClickListener(this);
        mClearCacheButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
        try {
            mCacheTextView.setText(DataCleanManager.getCacheSize(this.getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_back_ib:
                finish();
                break;

            case R.id.setting_clearcache_bt:
                if (mCacheTextView.getText().toString().equals("0.0B")) {
                    Toast.makeText(SettingActivity.this, "不需要清理哦", Toast.LENGTH_LONG).show();
                } else {
                    createCacheDialog();
                }
                break;

            case R.id.setting_change_phone_bt:
                break;

            case R.id.setting_change_password_bt:
                Util.toAnotherActivity(this, UpdatePwdActivity.class);
                finish();
                break;

//            case R.id.setting_nodification_bt:
//                break;

            case R.id.setting_about_bt:
                break;
        }
    }

    private void createCacheDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//        p.width = Util.dip2px(this, 280);
//        p.height = Util.dip2px(this, 109);
        p.width = Util.dip2px(this, 300);
        p.height = Util.dip2px(this, 140);
//        window.setLayout(Util.dip2px(this, Util.getWidth(this) * 1 / 4), Util.dip2px(this, Util.getHeight(this) * 1 / 13));
        window.setAttributes(p);
        window.setContentView(R.layout.clear_cache_layout);
        TextView mSureTextView = (TextView) window.findViewById(R.id.clear_cache_sure_tv);
        TextView mCancelTextView = (TextView) window.findViewById(R.id.clear_cache_cancel_tv);
        mSureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.cleanInternalCache(SettingActivity.this);
                alertDialog.dismiss();
                onCreate(null);
            }
        });
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                onCreate(null);
            }
        });
    }
}
