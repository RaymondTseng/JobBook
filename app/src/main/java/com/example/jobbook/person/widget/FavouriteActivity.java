package com.example.jobbook.person.widget;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jobbook.person.FavouritePagerAdapter;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class FavouriteActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener{

    private ImageButton mBackImageButton;
    private ViewPager mViewPager;
    private FavouritePagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private int mCurrentIndex;
    private TextView mJobTextView;
    private TextView mArticleTextView;
    private ImageView mCursorImageView;
    private int mCursorWidth;
    private int initPosition;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_favourite);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.favourite_back_ib);
        mViewPager = (ViewPager) findViewById(R.id.favourite_vp);
        mJobTextView = (TextView) findViewById(R.id.favourite_job_tv);
        mArticleTextView = (TextView) findViewById(R.id.favourite_article_tv);
        mCursorImageView = (ImageView) findViewById(R.id.favourite_cursor_iv);
    }

    private void initEvents() {
        mFragments = new ArrayList<>();
        mBackImageButton.setOnClickListener(this);
        mJobTextView.setOnClickListener(this);
        mArticleTextView.setOnClickListener(this);
        mFragments.add(new FavouriteJobsFragment());
        mFragments.add(new FavouriteArticleFragment());
        mAdapter = new FavouritePagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        initCursor();
        mViewPager.setOnPageChangeListener(this);
    }
    private void initCursor(){
        mCursorWidth = BitmapFactory.decodeResource(getResources(),
                R.mipmap.line).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        initPosition = (int)((((double)screenW / 6) * 2.5) - ((double)mCursorWidth / 2));
        Matrix matrix = new Matrix();
        matrix.postTranslate(initPosition, 0);
        mCursorImageView.setImageMatrix(matrix);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favourite_back_ib:
                finish();
                break;
            case R.id.favourite_job_tv:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.favourite_article_tv:
                mViewPager.setCurrentItem(1);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int offset = ((initPosition + (mCursorWidth / 2)) * 2) / 5;
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
