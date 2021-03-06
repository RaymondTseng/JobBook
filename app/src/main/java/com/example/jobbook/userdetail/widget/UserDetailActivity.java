package com.example.jobbook.userdetail.widget;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.userdetail.UserDetailPagerAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailPresenter;
import com.example.jobbook.userdetail.presenter.UserDetailPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailView;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 16-11-21.
 */

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, UserDetailView {
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
    private CircleImageView mHeadImageView;
    private TextView mNameTextView;
    private TextView mCompanyPositionTextView;
    private ViewStub mLoadingLinearLayout;
    private TextView mFocusTextView;
    private ImageView mFocusImageView;
    private int mCurrentIndex = 0;
    private TypePersonBean mPersonBean;
    private View view;
    private boolean isSelf = false;

    private static int UNFOLLOW = 1;
    private static int FOLLOW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        view = findViewById(android.R.id.content);
        initViews();
        initEvents();
    }

    private void initViews() {
        mCursorImageView = (ImageView) findViewById(R.id.user_detail_cursor_iv);
        mViewPager = (ViewPager) findViewById(R.id.user_detail_vp);
        mMomentTextView = (TextView) findViewById(R.id.user_detail_title_moment_tv);
        mFollowTextView = (TextView) findViewById(R.id.user_detail_title_follow_tv);
        mFollowerTextView = (TextView) findViewById(R.id.user_detail_title_follower_tv);
        mBackImageButton = (ImageButton) findViewById(R.id.user_detail_back_ib);
        mFollowLayout = (LinearLayout) findViewById(R.id.user_detail_follow_ll);
        mLoadingLinearLayout = (ViewStub) findViewById(R.id.activity_user_detail_loading);
        mLoadingLinearLayout.inflate();
        mHeadImageView = (CircleImageView) findViewById(R.id.user_detail_title_head_iv);
        mNameTextView = (TextView) findViewById(R.id.user_detail_title_head_name_tv);
        mCompanyPositionTextView = (TextView) findViewById(R.id.user_detail_title_company_position_tv);
        mFocusTextView = (TextView) findViewById(R.id.user_detail_focus_tv);
        mFocusImageView = (ImageView) findViewById(R.id.user_detail_focus_iv);
//        mSettingImageButton = (ImageButton) findViewById(R.id.user_detail_setting_ib);
//        mSettingPopupView = getLayoutInflater().inflate(R.layout.user_detail_setting_popupwindow, null);
    }

    private void initCursor() {
        mCursorWidth = BitmapFactory.decodeResource(getResources(),
                R.mipmap.triangle_white).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        initPosition = (int) (((double) screenW / 6) - ((double) mCursorWidth / 2));
        Matrix matrix = new Matrix();
        matrix.postTranslate(initPosition, 0);
        mCursorImageView.setImageMatrix(matrix);
        mSettingPopupWindow = new PopupWindow(mSettingPopupView, Util.getWidth(this) / 3,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mSettingPopupWindow.setTouchable(true);
        mSettingPopupWindow.setOutsideTouchable(true);
        mSettingPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//        mSettingImageButton.setOnClickListener(this);
    }

    private void initEvents() {
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
        mPersonBean = getIntent().getParcelableExtra("person_bean");
        if (mPersonBean != null) {
            L.i("userdetail", mPersonBean.toString());
            ((UserDetailMomentFragment) mFragemnts.get(0)).getAccount(mPersonBean.getAccount());
            ((UserDetailFollowFragment) mFragemnts.get(1)).getAccount(mPersonBean.getAccount());
            ((UserDetailFansFragment) mFragemnts.get(2)).getAccount(mPersonBean.getAccount());
            if (mPersonBean.getAccount().equals(MyApplication.getAccount())) {
                isSelf = true;
            }
            setData(mPersonBean);
        } else {
            String account = getIntent().getStringExtra("person_account_from_message");
            ((UserDetailMomentFragment) mFragemnts.get(0)).getAccount(account);
            ((UserDetailFollowFragment) mFragemnts.get(1)).getAccount(account);
            ((UserDetailFansFragment) mFragemnts.get(2)).getAccount(account);
            if (account.equals(MyApplication.getAccount())) {
                isSelf = true;
            }
            mPresenter.loadUserDetailByAccount(account);
        }
        mLoadingLinearLayout.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                Handler handler = myApplication.getHandler();
                if (handler != null) {
                    handler.sendEmptyMessage(1);
                    myApplication.setHandler(null);
                }
                finish();
                break;
            case R.id.user_detail_follow_ll:
                if (MyApplication.getmLoginStatus() != 0) {
                    L.i("userdetail", "personbean: " + mPersonBean.getAccount() + " localaccount:" + MyApplication.getAccount());
                    if (mPersonBean.getAccount().equals(MyApplication.getAccount())) {
                        Util.showSnackBar(view, "不能关注自己~");
                    } else {
                        if (mPersonBean.getType() == UNFOLLOW)
                            mPresenter.follow(MyApplication.getAccount(), mPersonBean.getAccount());
                        else
                            mPresenter.unfollow(MyApplication.getAccount(), mPersonBean.getAccount());
                        if(myApplication.getHandler() != null)
                            myApplication.getHandler().sendEmptyMessage(1);
                    }
                }
                break;
//            case R.id.user_detail_setting_ib:
//                mSettingPopupWindow.showAsDropDown(mSettingImageButton);
//                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int offset = (initPosition + (mCursorWidth / 2)) * 2;
        Animation animation = null;
        switch (position) {
            case 0:
                ((UserDetailMomentFragment) mFragemnts.get(0)).getAccount(mPersonBean.getAccount());
                if (mCurrentIndex == 1) {
                    animation = new TranslateAnimation(offset, 0, 0, 0);
                } else if (mCurrentIndex == 2) {
                    animation = new TranslateAnimation(offset * 2, 0, 0, 0);
                }
                break;
            case 1:
                ((UserDetailFollowFragment) mFragemnts.get(1)).getAccount(mPersonBean.getAccount());
                if (mCurrentIndex == 0) {
                    animation = new TranslateAnimation(0, offset, 0, 0);
                } else if (mCurrentIndex == 2) {
                    animation = new TranslateAnimation(offset * 2, offset, 0, 0);
                }
                break;
            case 2:
                ((UserDetailFansFragment) mFragemnts.get(2)).getAccount(mPersonBean.getAccount());
                if (mCurrentIndex == 0) {
                    animation = new TranslateAnimation(0, offset * 2, 0, 0);
                } else if (mCurrentIndex == 1) {
                    animation = new TranslateAnimation(offset, offset * 2, 0, 0);
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

    @Override
    public void followSuccess() {
        L.i("user_detail", "follow success");
        mPresenter.loadUserDetailByAccount(MyApplication.getAccount());
        mFocusTextView.setText("取消关注");
        mFocusTextView.setTextColor(this.getResources().getColor(R.color.colorPink));
        mFocusImageView.setImageResource(R.mipmap.close_red_24_dp);
        mPersonBean.setType(FOLLOW);
        Util.showSnackBar(view, "关注成功!");
    }

    @Override
    public void onFail(String msg) {
        Util.showSnackBar(view, msg);
    }

    @Override
    public void unfollowSuccess() {
        mPresenter.loadUserDetailByAccount(MyApplication.getAccount());
        L.i("user_detail", "unfollow success");
        mFocusTextView.setText("关注");
        mFocusTextView.setTextColor(this.getResources().getColor(R.color.colorBlue));
        mFocusImageView.setImageResource(R.mipmap.add_24_dp);
        mPersonBean.setType(UNFOLLOW);
        Util.showSnackBar(view, "取消关注成功!");
    }


    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadSuccess(TypePersonBean personBean) {
        setData(personBean);
    }

    @Override
    public void onRefreshSuccess(TypePersonBean personBean) {
        MyApplication.setmPersonBean(this, personBean);
    }


    private void setData(TypePersonBean personBean) {
        L.i("userdetail", "typeperson: " + personBean.toString());
        L.i("userdetail", "person:" + personBean.getAccount() + " myapp:" + MyApplication.getAccount());
        if (personBean.getAccount().equals(MyApplication.getAccount())) {
            MyApplication.setmPersonBean(this, personBean);
            if (isSelf) {
                ImageLoadUtils.display(this, mHeadImageView, personBean.getHead());
                mNameTextView.setText(personBean.getUsername());
                mCompanyPositionTextView.setText(personBean.getWorkSpace());
                mFollowTextView.setText("关注 " + personBean.getFollow());
                mFollowerTextView.setText("粉丝 " + personBean.getFans());
                if (personBean.getType() == FOLLOW) {
                    mFocusTextView.setText("取消关注");
                    mFocusTextView.setTextColor(this.getResources().getColor(R.color.colorPink));
                    mFocusImageView.setImageResource(R.mipmap.close_red_24_dp);
                }
                isSelf = false;
            }
        } else {
            ImageLoadUtils.display(this, mHeadImageView, personBean.getHead());
            mNameTextView.setText(personBean.getUsername());
            mCompanyPositionTextView.setText(personBean.getWorkSpace());
            mFollowTextView.setText("关注 " + personBean.getFollow());
            mFollowerTextView.setText("粉丝 " + personBean.getFans());
            if (personBean.getType() == FOLLOW) {
                mFocusTextView.setText("取消关注");
                mFocusTextView.setTextColor(this.getResources().getColor(R.color.colorPink));
                mFocusImageView.setImageResource(R.mipmap.close_red_24_dp);
            }
            mPersonBean = personBean;
        }
    }

    public interface OnGetAccountListener {
        void getAccount(String account);
    }
}
