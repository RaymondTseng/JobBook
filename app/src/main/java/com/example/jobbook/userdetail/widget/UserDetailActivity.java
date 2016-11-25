package com.example.jobbook.userdetail.widget;


import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.userdetail.UserDetailPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-21.
 */

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private ImageView mCursorImageView;
    private ViewPager mViewPager;
    private int mCursorWidth;
    private int initPosition;
    private UserDetailPagerAdapter mPagerAdapter;
    private List<Fragment> mFragemnts = new ArrayList<>();
    private TextView mMomentTextView;
    private TextView mFollowTextView;
    private TextView mFollowerTextView;
    private MyApplication myApplication;
    private int mCurrentIndex = 0;

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
    }

    private void initCursor(){
        mCursorWidth = BitmapFactory.decodeResource(getResources(),
                R.mipmap.line).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        initPosition = (int)(((((double)screenW / 360) * 248) / 4) - ((double)mCursorWidth / 2));
        Matrix matrix = new Matrix();
        matrix.postTranslate(initPosition, 0);
        mCursorImageView.setImageMatrix(matrix);
        myApplication = (MyApplication) getApplication();
    }

    private void initEvents(){
        mFragemnts.add(new UserDetailMomentFragment());
        mFragemnts.add(new UserDetailMomentFragment());
        mFragemnts.add(new UserDetailMomentFragment());
        mMomentTextView.setOnClickListener(this);
        mFollowTextView.setOnClickListener(this);
        mFollowerTextView.setOnClickListener(this);
        mPagerAdapter = new UserDetailPagerAdapter(getSupportFragmentManager(), mFragemnts);
        initCursor();
        mViewPager.setOnPageChangeListener(this);
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
                if(mCurrentIndex == 1){
                    animation = new TranslateAnimation(offset, 0, 0, 0);
                }
                break;
            case 1:
                if(mCurrentIndex == 0){
                    animation = new TranslateAnimation(0, offset, 0, 0);
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
}
