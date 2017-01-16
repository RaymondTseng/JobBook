package com.example.jobbook.main.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.article.widget.ArticleFragment;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.job.widget.JobFragment;
import com.example.jobbook.login.widget.LoginActivity;
import com.example.jobbook.main.MainFragmentPagerAdapter;
import com.example.jobbook.main.presenter.MainPresenter;
import com.example.jobbook.main.presenter.MainPresenterImpl;
import com.example.jobbook.main.view.MainView;
import com.example.jobbook.moment.widget.MomentFragment;
import com.example.jobbook.person.widget.PersonFragment;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, MainView {

    private ViewPager mFragmentContainer;
    private RadioButton mJobRadioButton;
    private RadioButton mArticleRadioButton;
    private RadioButton mQuestionRadioButton;
    private RadioButton mPersonRadioButton;
    private RadioGroup mRadioGroup;
    private MainFragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initList();
        initEvent();
        switch2Job();
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
        mMainPresenter = new MainPresenterImpl(this);
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
        Log.i("logincheck", "timeout");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("main", "ondestory");
    }
}