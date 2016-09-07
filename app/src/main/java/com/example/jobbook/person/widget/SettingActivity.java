package com.example.jobbook.person.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.search.widget.SearchActivity;
import com.example.jobbook.util.DataCleanManager;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageButton mBackImageButton;
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
        mClearCacheButton = (Button) findViewById(R.id.person_clearcache_bt);
        mAboutButton = (Button) findViewById(R.id.person_about_bt);
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

            case R.id.person_clearcache_bt:
                if (mCacheTextView.getText().toString().equals("0.0B")) {
                    Toast.makeText(SettingActivity.this, "不需要清理哦", Toast.LENGTH_LONG).show();
                } else {
                    createCacheDialog();
                }
                break;

            case R.id.person_about_bt:
                break;
        }
    }

    private void createCacheDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
        alertDialog.show();
        alertDialog.setCancelable(true);
        Window window = alertDialog.getWindow();
        window.setLayout(Util.dip2px(SettingActivity.this, 315), Util.dip2px(SettingActivity.this, 137));
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
