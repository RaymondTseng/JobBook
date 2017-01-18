package com.example.jobbook.person.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.person.presenter.UploadPresenter;
import com.example.jobbook.person.presenter.UploadPresenterImpl;
import com.example.jobbook.person.view.UploadView;
import com.example.jobbook.update.widget.UpdatePwdActivity;
import com.example.jobbook.update.widget.UpdateUsernameActivity;
import com.example.jobbook.upload.CropUtils;
import com.example.jobbook.upload.UploadManager;
import com.example.jobbook.upload.UploadPopupWindow;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xu on 2016/9/5.
 */
public class OldUserDetailActivity extends Activity implements View.OnClickListener, UploadView {

    private ImageButton mBackImageButton;
    private RelativeLayout mChangeHeadRelativeLayout;
    private CircleImageView mUserHeadImageView;
    private RelativeLayout mChangeUserNameRelativeLayout;
    private TextView mUserNameTextView;
//    private Button mChangePhoneButton;
    private Button mChangePwdButton;
    private UploadPopupWindow mPopupWindow;
    private PersonBean mPersonBean = MyApplication.getmPersonBean();
    private UploadPresenter presenter;
    private LinearLayout mLoadingLinearLayout;
    private MyApplication mMyApplication;
    private Uri mUri;
    private View view;

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
//        mChangePhoneButton = (Button) findViewById(R.id.person_userinfo_changephone_bt);
        mChangePwdButton = (Button) findViewById(R.id.person_userinfo_changepwd_bt);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        hideProgress();
        mBackImageButton.setOnClickListener(this);
        mChangeHeadRelativeLayout.setOnClickListener(this);
        mChangeUserNameRelativeLayout.setOnClickListener(this);
        mChangePwdButton.setOnClickListener(this);
        ImageLoadUtils.display(this, mUserHeadImageView, mPersonBean.getHead(), 0);
        mUserNameTextView.setText(mPersonBean.getUsername());
        mMyApplication = (MyApplication) getApplication();
        mUri = null;
        presenter = new UploadPresenterImpl(this);

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
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

            case R.id.person_userinfo_changeusername_rl:
                Util.toAnotherActivity(this, UpdateUsernameActivity.class);
                finish();
                break;

//            case R.id.person_userinfo_changephone_bt:
//                Util.toAnotherActivity(this, UpdatePhoneActivity.class);
//                finish();
//                break;

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
                    CropUtils.pickAvatarFromCamera(OldUserDetailActivity.this);
                    break;
                case R.id.person_upload_pickPhoto_bt:// 相册选择图片
                    CropUtils.pickAvatarFromGallery(OldUserDetailActivity.this);
                    break;
                case R.id.person_upload_cancel_bt:// 取消
//                    finish();
                    break;
            }
        }
    };

    private CropUtils.CropHandler handler = new CropUtils.CropHandler() {
        @Override
        public void handleCropResult(Uri uri, int tag) {
            //send Image to Server
            sendImage(UploadManager.getBitmapFromUri(getApplicationContext(), uri));
            mUri = uri;
//            ImageLoadUtils.display(OldUserDetailActivity.this , mUserHeadImageView, uri);
        }

        @Override
        public void handleCropError(Intent data) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropUtils.handleResult(this, handler, requestCode, resultCode, data);
    }

    private void sendImage(Bitmap bm) {
        L.i("photo", "sendImage");
        presenter.uploadImage(bm);
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void uploadSuccess() {
        Util.showSnackBar(view, "上传成功！");
    }

    @Override
    public void uploadFailure() {
        Util.showSnackBar(view, "上传失败，请重试！");
    }

    @Override
    public void loadHead(Bitmap bm) {
        L.i("photo", "loadHead1:" + mUri.toString());
        mMyApplication.getHandler().sendEmptyMessage(2);
//        mUserHeadImageView.setImageBitmap(bm);
        mUserHeadImageView.setImageURI(mUri);
        bm.recycle();
//        this.onCreate(null);
    }

    @Override
    protected void onPause(){
        L.i("photo", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop(){
        L.i("photo", "onStop");
        super.onStop();
    }

    @Override
    protected void onStart(){
        L.i("photo", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume(){
        if(mUserHeadImageView != null && mUri != null){
            mUserHeadImageView.setImageURI(mUri);
            Log.i("photo", "uri:" + mUri.toString());
        }
        L.i("photo", "onResume");
        super.onResume();
    }
}
