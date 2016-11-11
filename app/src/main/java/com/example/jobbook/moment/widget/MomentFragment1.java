package com.example.jobbook.moment.widget;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.moment.MomentPagerAdapter;
import com.example.jobbook.util.L;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-8.
 */

public class MomentFragment1 extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private TabWidget mTabWidget;
    private MomentPagerAdapter mPagerAdapter;
    private View view;
    private String[] addresses = { "广场", "关注"};
    private TextView[] mTextTabs = new TextView[addresses.length];
    private List<Fragment> mFragemnts = new ArrayList<>();
    private ImageView mCursorImageView;
    private int mCursorWidth;
    private int initPosition;
    private int mCurrentIndex = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_moment, container, false);
        initViews(view);
        initEvents();
        L.i("momentfragment", "create");
        return view;
    }

    private void initViews(View view){
        mViewPager = (ViewPager) view.findViewById(R.id.moment_vp);
        mTabWidget = (TabWidget) view.findViewById(R.id.moment_tab);
        mCursorImageView = (ImageView) view.findViewById(R.id.moment_cursor);
    }

    private void initCursor(){
        mCursorWidth = BitmapFactory.decodeResource(getResources(),
                R.mipmap.line).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        initPosition = (int)(((((double)screenW / 360) * 248) / 4) - ((double)mCursorWidth / 2));
        Matrix matrix = new Matrix();
        matrix.postTranslate(initPosition, 0);
        mCursorImageView.setImageMatrix(matrix);
    }

    private void initEvents(){
        mTabWidget.setStripEnabled(false);
        for(int i = 0; i < mTextTabs.length; i++){
            mTextTabs[i] = new TextView(getActivity());
            mTextTabs[i].setTag(i);
            mTextTabs[i].setFocusable(true);
            mTextTabs[i].setText(addresses[i]);
            mTextTabs[i].setTextSize(16);
            mTextTabs[i].setBackgroundColor(getResources().getColor(R.color.colorBlue));
            mTextTabs[i].setGravity(Gravity.CENTER);
            mTextTabs[i].setTextColor(getResources().getColorStateList(R.color.colorWhite));
            mTabWidget.addView(mTextTabs[i]);
            mTextTabs[i].setOnClickListener(this);
            mFragemnts.add(new MomentFragment());
        }

        mPagerAdapter = new MomentPagerAdapter(getChildFragmentManager(), mFragemnts);
        mViewPager.setAdapter(mPagerAdapter);
        initCursor();
        mViewPager.setOnPageChangeListener(this);
        mTabWidget.setCurrentTab(0);


    }

    @Override
    public void onClick(View v) {
        switch ((int)v.getTag()){
            case 0:
                mViewPager.setCurrentItem(0);
                break;
            case 1:
                mViewPager.setCurrentItem(1);
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

        mTabWidget.setCurrentTab(position);
        mCurrentIndex = position;
        animation.setFillAfter(true);
        animation.setDuration(200);
        mCursorImageView.startAnimation(animation);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
