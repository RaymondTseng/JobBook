package com.example.jobbook.main.widget;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.article.widget.ArticleFragment;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.job.widget.JobFragment;
import com.example.jobbook.main.MainFragmentPagerAdapter;
import com.example.jobbook.main.presenter.MainPresenter;
import com.example.jobbook.main.presenter.MainPresenterImpl;
import com.example.jobbook.main.view.MainView;
import com.example.jobbook.login.widget.LoginFragment;
import com.example.jobbook.person.widget.PersonFragment;
import com.example.jobbook.question.widget.QuestionFragment;
import com.example.jobbook.register.widget.RegisterFragment;
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
    private List<Fragment> mFragments;
    private MainPresenter mMainPresenter;
    private int mShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initList();
        initEvent();

        switch2Job();
    }

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
        mFragments = new ArrayList<>();
        mFragments.add(new JobFragment());
        mFragments.add(new ArticleFragment());
        mFragments.add(new QuestionFragment());
//        mFragments.add(new LoginFragment());
        mFragments.add(new ContainerFragment());
    }

    private void initEvent() {
        mJobRadioButton.setChecked(true);
        mFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mFragmentContainer.setAdapter(mFragmentPagerAdapter);
        mRadioGroup.setOnCheckedChangeListener(this);
        mFragmentContainer.addOnPageChangeListener(this);
        mShowFragment = 0;
        MyApplication.setmPersonBean(mMainPresenter.loadPersonBean(this));
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

//    @Override
//    public void changeViewPager() {
//        Log.i("TAG", "4");
//        mFragmentPagerAdapter.toPersonFragment();
//    }

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
        mFragmentContainer.setCurrentItem(3);
    }

}