package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.update.widget.UpdatePhoneActivity;
import com.example.jobbook.update.widget.UpdatePwdActivity;
import com.example.jobbook.update.widget.UpdateUsernameActivity;
import com.example.jobbook.upload.widget.UploadPopupWindow;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xu on 2016/9/5.
 */
public class UserDetailActivity extends Activity implements View.OnClickListener{

    private ImageButton mBackImageButton;
    private RelativeLayout mChangeHeadRelativeLayout;
    private CircleImageView mUserHeadImageView;
    private RelativeLayout mChangeUserNameRelativeLayout;
    private TextView mUserNameTextView;
    private Button mChangePhoneButton;
    private Button mChangePwdButton;
    private UploadPopupWindow mPopupWindow;
    private PersonBean mPersonBean = MyApplication.getmPersonBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_userinfo);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.person_userinfo_back_ib);
        mChangeHeadRelativeLayout = (RelativeLayout) findViewById(R.id.person_userinfo_changehead_rl);
        mUserHeadImageView = (CircleImageView) findViewById(R.id.person_userinfo_logo_iv);
        mChangeUserNameRelativeLayout = (RelativeLayout) findViewById(R.id.person_userinfo_changeusername_rl);
        mUserNameTextView = (TextView) findViewById(R.id.person_userinfo_changeusername_tv);
        mChangePhoneButton = (Button) findViewById(R.id.person_userinfo_changephone_bt);
        mChangePwdButton = (Button) findViewById(R.id.person_userinfo_changepwd_bt);
    }

    private void initEvents() {
        mBackImageButton.setOnClickListener(this);
        mChangeHeadRelativeLayout.setOnClickListener(this);
        mChangeUserNameRelativeLayout.setOnClickListener(this);
        mChangePhoneButton.setOnClickListener(this);
        mChangePwdButton.setOnClickListener(this);
        ImageLoadUtils.display(this, mUserHeadImageView, mPersonBean.getHead());
//        Log.i("userdetail", mPersonBean.getUsername());
        mUserNameTextView.setText(mPersonBean.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_userinfo_back_ib:
                finish();
                break;

            case R.id.person_userinfo_changehead_rl:
                mPopupWindow = new UploadPopupWindow(this, itemsOnClick);
                mPopupWindow.showAtLocation(findViewById(R.id.activity_person_userinfo_ll),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.person_userinfo_changeusername_rl:
                Util.toAnotherActivity(this, UpdateUsernameActivity.class);
                finish();
                break;

            case R.id.person_userinfo_changephone_bt:
                Util.toAnotherActivity(this, UpdatePhoneActivity.class);
                finish();
                break;

            case R.id.person_userinfo_changepwd_bt:
                Util.toAnotherActivity(this, UpdatePwdActivity.class);
                finish();
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 隐藏弹出窗口
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.person_upload_takePhoto_bt:// 拍照
//                    takePhoto();
                    break;
                case R.id.person_upload_pickPhoto_bt:// 相册选择图片
//                    pickPhoto();
                    break;
                case R.id.person_upload_cancel_bt:// 取消
                    finish();
                    break;
            }
        }
    };
}
