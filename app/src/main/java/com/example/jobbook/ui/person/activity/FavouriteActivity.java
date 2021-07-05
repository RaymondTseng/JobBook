package com.example.jobbook.ui.person.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.jobbook.R;
import com.example.jobbook.ui.person.adapter.FavouritePagerAdapter;
import com.example.jobbook.ui.person.fragment.FavouriteArticleFragment;
import com.example.jobbook.ui.person.fragment.FavouriteJobsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class FavouriteActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.favourite_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.favourite_vp)
    ViewPager mViewPager;

    @BindView(R.id.favourite_job_tv)
    TextView mJobTextView;

    @BindView(R.id.favourite_article_tv)
    TextView mArticleTextView;

    @BindView(R.id.favourite_cursor_iv)
    ImageView mCursorImageView;

    private FavouritePagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private int mCurrentIndex;
    private int mCursorWidth;
    private int initPosition;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_favourite);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        mFragments = new ArrayList<>();
        mFragments.add(new FavouriteJobsFragment());
        mFragments.add(new FavouriteArticleFragment());
        mAdapter = new FavouritePagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        initCursor();
        mViewPager.setOnPageChangeListener(this);
    }

    private void initCursor() {
        mCursorWidth = BitmapFactory.decodeResource(getResources(),
                R.mipmap.line).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        initPosition = (int) ((((double) screenW / 6) * 2.5) - ((double) mCursorWidth / 2));
        Matrix matrix = new Matrix();
        matrix.postTranslate(initPosition, 0);
        mCursorImageView.setImageMatrix(matrix);
    }

    @OnClick(R.id.favourite_back_ib)
    public void back() {
        finish();
    }

    @OnClick(R.id.favourite_job_tv)
    public void favourite_job() {
        mViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.favourite_article_tv)
    public void favourite_article() {
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int offset = ((initPosition + (mCursorWidth / 2)) * 2) / 5;
        Animation animation = null;
        switch (position) {
            case 0:
                if (mCurrentIndex == 1) {
                    animation = new TranslateAnimation(offset, 0, 0, 0);
                }
                break;
            case 1:
                if (mCurrentIndex == 0) {
                    animation = new TranslateAnimation(0, offset, 0, 0);
                }
                break;
        }

        mCurrentIndex = position;
        if (animation != null) {
            animation.setFillAfter(true);
            animation.setDuration(200);
            mCursorImageView.startAnimation(animation);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
