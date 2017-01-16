package com.example.jobbook.userdetail.widget;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.userdetail.UserDetailPagerAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailPresenter;
import com.example.jobbook.userdetail.presenter.UserDetailPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailView;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-21.
 */

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, UserDetailView{
    private ImageView mCursorImageView;
    private ViewPager mViewPager;
    private int mCursorWidth;
    private int initPosition;
    private UserDetailPagerAdapter mPagerAdapter;
    private List<Fragment> mFragemnts = new ArrayList<>();
    private TextView mMomentTextView;
    private TextView mFollowTextView;
    private TextView mFollowerTextView;
    private LinearLayout mFollowLayout;
    private MyApplication myApplication;
    private ImageButton mBackImageButton;
    private UserDetailPresenter mPresenter;
    private View mSettingPopupView;
    private PopupWindow mSettingPopupWindow;
    private ImageButton mSettingImageButton;
    private int mCurrentIndex = 0;
    private PersonBean mPersonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initViews();
        initEvents();
    }

    private void initViews(){
        mCursorImageView = (ImageView) findViewById(R.id.user_detail_cursor_iv);
        mViewPager = (ViewPager) findViewById(R.id.user_detail_vp);
        mMomentTextView = (TextView) findViewById(R.id.user_detail_title_moment_tv);
        mFollowTextView = (TextView) findViewById(R.id.user_detail_title_follow_tv);
        mFollowerTextView = (TextView) findViewById(R.id.user_detail_title_follower_tv);
        mBackImageButton = (ImageButton) findViewById(R.id.user_detail_back_ib);
        mFollowLayout = (LinearLayout) findViewById(R.id.user_detail_follow_ll);
        mSettingImageButton = (ImageButton) findViewById(R.id.user_detail_setting_ib) ;
        mSettingPopupView = getLayoutInflater().inflate(R.layout.user_detail_setting_popupwindow, null);
    }

    private void initCursor(){
        mCursorWidth = BitmapFactory.decodeResource(getResources(),
                R.mipmap.triangle_white).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        initPosition = (int)(((double)screenW / 6) - ((double)mCursorWidth / 2));
        Matrix matrix = new Matrix();
        matrix.postTranslate(initPosition, 0);
        mCursorImageView.setImageMatrix(matrix);
        mSettingPopupWindow = new PopupWindow(mSettingPopupView, Util.getWidth(this)/3,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mSettingPopupWindow.setTouchable(true);
        mSettingPopupWindow.setOutsideTouchable(true);
        mSettingPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap)null));
        mSettingImageButton.setOnClickListener(this);
    }

    private void initEvents(){
        mPersonBean = (PersonBean) getIntent().getSerializableExtra("person_bean");
        mFragemnts.add(new UserDetailMomentFragment());
        mFragemnts.add(new UserDetailFollowFragment());
        mFragemnts.add(new UserDetailFansFragment());
        mMomentTextView.setOnClickListener(this);
        mFollowTextView.setOnClickListener(this);
        mFollowerTextView.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
        mFollowLayout.setOnClickListener(this);
        myApplication = (MyApplication) getApplication();
        mPagerAdapter = new UserDetailPagerAdapter(getSupportFragmentManager(), mFragemnts);
        initCursor();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        mPresenter = new UserDetailPresenterImpl(this);
        ((UserDetailMomentFragment) mFragemnts.get(0)).getAccount(mPersonBean.getAccount());
        ((UserDetailFollowFragment) mFragemnts.get(1)).getAccount(mPersonBean.getAccount());
        ((UserDetailFansFragment) mFragemnts.get(2)).getAccount(mPersonBean.getAccount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_detail_title_moment_tv:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.user_detail_title_follow_tv:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.user_detail_title_follower_tv:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.user_detail_back_ib:
                finish();
                break;
            case R.id.user_detail_follow_ll:
                if(MyApplication.getmLoginStatus() == 0){

                }else{
                    mPresenter.follow(MyApplication.getAccount(), mPersonBean.getAccount());
                }
                break;
            case R.id.user_detail_setting_ib:
                mSettingPopupWindow.showAsDropDown(mSettingImageButton);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int offset = (initPosition + (mCursorWidth / 2)) * 2;
        Animation animation = null;
        switch (position){
            case 0:
                ((UserDetailMomentFragment) mFragemnts.get(0)).getAccount(mPersonBean.getAccount());
                if(mCurrentIndex == 1){
                    animation = new TranslateAnimation(offset, 0, 0, 0);
                }else if(mCurrentIndex == 2){
                    animation = new TranslateAnimation(offset*2 , 0, 0, 0);
                }
                break;
            case 1:
                ((UserDetailFollowFragment) mFragemnts.get(1)).getAccount(mPersonBean.getAccount());
                if(mCurrentIndex == 0){
                    animation = new TranslateAnimation(0, offset, 0, 0);
                }else if(mCurrentIndex == 2){
                    animation = new TranslateAnimation(offset*2, offset, 0, 0);
                }
                break;
            case 2:
                ((UserDetailFansFragment) mFragemnts.get(2)).getAccount(mPersonBean.getAccount());
                if(mCurrentIndex == 0){
                    animation = new TranslateAnimation(0, offset*2, 0, 0);
                }else if(mCurrentIndex == 1){
                    animation = new TranslateAnimation(offset, offset*2 , 0, 0);
                }
                break;
        }

        mCurrentIndex = position;
        animation.setFillAfter(true);
        animation.setDuration(200);
        mCursorImageView.startAnimation(animation);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void followSuccess() {
        L.i("user_detail", "follow success");
    }

    @Override
    public void followFail() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    public interface OnGetAccountListener{
        void getAccount(String account);
    }
}
