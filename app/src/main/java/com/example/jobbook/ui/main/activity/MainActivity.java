package com.example.jobbook.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.main.MainContract;
import com.example.jobbook.login.widget.LoginActivity;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.moment.widget.MomentFragment;
import com.example.jobbook.person.widget.PersonFragment;
import com.example.jobbook.presenter.main.MainPresenter;
import com.example.jobbook.service.MyPushIntentService;
import com.example.jobbook.ui.article.fragment.ArticleFragment;
import com.example.jobbook.ui.job.fragment.JobFragment;
import com.example.jobbook.ui.main.adapter.MainFragmentPagerAdapter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, MainContract.View, MyPushIntentService.OnRefreshPersonBadgeViewListener {

    private ViewPager mFragmentContainer;
    private RadioButton mJobRadioButton;
    private RadioButton mArticleRadioButton;
    private RadioButton mQuestionRadioButton;
    private RadioButton mPersonRadioButton;
    private RadioGroup mRadioGroup;
    private MainFragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private MainPresenter mMainPresenter;
    public static BadgeView mBadgeView;
    private Button mButton;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    public static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);
        MyApplication.mSnackBarView = findViewById(R.id.main_layout);
        initViews();
        initList();
        initEvent();
        switch2Job();
        MyPushIntentService.listeners.add(this);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        L.i("main", "onStart");
//        mJobRadioButton.setChecked(true);
//        mFragmentContainer.setCurrentItem(0);
//    }

    private void initViews() {
        mFragmentContainer = (ViewPager) findViewById(R.id.fragment_container);
        mJobRadioButton = (RadioButton) findViewById(R.id.job_rb);
        mArticleRadioButton = (RadioButton) findViewById(R.id.article_rb);
        mQuestionRadioButton = (RadioButton) findViewById(R.id.question_rb);
        mPersonRadioButton = (RadioButton) findViewById(R.id.person_rb);
        mRadioGroup = (RadioGroup) findViewById(R.id.bottom_bar_rg);
        mButton = (Button) findViewById(R.id.main_bt);
        mMainPresenter = new MainPresenter(this);
        mBadgeView = new BadgeView(this);
    }

    private void initList() {
//        mFragments = new ArrayList<>();
        mFragments.add(new JobFragment());
        mFragments.add(new ArticleFragment());
        mFragments.add(new MomentFragment());
//        mFragments.add(new ContainerFragment());
        mFragments.add(new PersonFragment());
    }

    private void initEvent() {
        mJobRadioButton.setChecked(true);
        mFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mFragmentContainer.setAdapter(mFragmentPagerAdapter);
        mRadioGroup.setOnCheckedChangeListener(this);
        mFragmentContainer.addOnPageChangeListener(this);
        mMainPresenter.loginCheck(this);
//        MyApplication.setmPersonBean(mMainPresenter.loadPersonBean(this));
        if (getIntent().getExtras() != null) {
            String change_to = (String) getIntent().getExtras().get("CHANGE_TO");
            if (!TextUtils.isEmpty(change_to)) {
                switch (change_to) {
                    case "JOB":
                        mJobRadioButton.setChecked(true);
                        mFragmentContainer.setCurrentItem(0);
                        break;
                    case "ARTICLE":
                        break;
                }
            }
        }

        mBadgeView.setTargetView(mButton);

    }

    /**
     * 处理底部导航栏的fragment事务
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        mMainPresenter.switchNavigation(checkedId);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mJobRadioButton.setChecked(true);
                break;
            case 1:
                mArticleRadioButton.setChecked(true);
                break;
            case 2:
                mQuestionRadioButton.setChecked(true);
                break;
            case 3:
                mPersonRadioButton.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void switch2Job() {
        mFragmentContainer.setCurrentItem(0);
    }

    @Override
    public void switch2Article() {
        mFragmentContainer.setCurrentItem(1);
    }

    @Override
    public void switch2Question() {
        mFragmentContainer.setCurrentItem(2);
    }

    @Override
    public void switch2Container() {
//        mFragmentContainer.setCurrentItem(3);
        if (MyApplication.getmLoginStatus() == 0) {
            Util.toAnotherActivity(MainActivity.this, LoginActivity.class);
//            mFragmentContainer.setCurrentItem(0);
            finish();
        } else {
            mFragmentContainer.setCurrentItem(3);
        }
    }

    @Override
    public void loginCheckSuccess(PersonBean personBean) {
        MyApplication.setmPersonBean(this, personBean);
    }

    @Override
    public void loginCheckTimeOut() {
        L.i("timeout");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyPushIntentService.listeners.remove(this);
        MyApplication.mSnackBarView = null;
        mBadgeView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
//            Log.e(TAG, "exit application");
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.i("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh(final int num) {
        if (num != 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mBadgeView.getVisibility() == View.GONE) {
                        mBadgeView.setVisibility(View.VISIBLE);
                        mBadgeView.setText("" + num);
                    } else {
                        mBadgeView.setText("" + num);
                    }
                }
            });
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg(String msg) {

    }
}