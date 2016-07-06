package com.example.jobbook.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jobbook.R;
import com.example.jobbook.adapter.MainFragmentPagerAdapter;
import com.example.jobbook.fragment.ArticleFragment;
import com.example.jobbook.fragment.JobFragment;
import com.example.jobbook.fragment.LoginFragment;
import com.example.jobbook.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, LoginFragment.transferData{
    private ViewPager mFragmentContainer;
    private RadioButton mJobRadioButton;
    private RadioButton mArticleRadioButton;
    private RadioButton mQuestionRadioButton;
    private RadioButton mPersonRadioButton;
    private JobFragment mJobFragment;
    private ArticleFragment mArticleFragment;
    private QuestionFragment mQuestionFragment;
    private RadioGroup mRadioGroup;
    private MainFragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initList();
        initEvent();

    }
    private void initViews(){
        mFragmentContainer = (ViewPager) findViewById(R.id.fragment_container);
        mJobRadioButton = (RadioButton) findViewById(R.id.job_rb);
        mArticleRadioButton = (RadioButton) findViewById(R.id.article_rb);
        mQuestionRadioButton = (RadioButton) findViewById(R.id.question_rb);
        mPersonRadioButton = (RadioButton) findViewById(R.id.person_rb);
        mRadioGroup = (RadioGroup) findViewById(R.id.bottom_bar_rg);
    }
    private void initList(){
        mFragments = new ArrayList<>();
        mFragments.add(new JobFragment());
        mFragments.add(new ArticleFragment());
        mFragments.add(new QuestionFragment());
        mFragments.add(new LoginFragment());
    }
    private void initEvent(){
        mJobRadioButton.setChecked(true);
        mFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mFragmentContainer.setAdapter(mFragmentPagerAdapter);
        mRadioGroup.setOnCheckedChangeListener(this);
        mFragmentContainer.addOnPageChangeListener(this);
    }

    /**
     * 处理底部导航栏的fragment事务
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            case R.id.job_rb:
                mFragmentContainer.setCurrentItem(0);
                break;
            case R.id.article_rb:
                mFragmentContainer.setCurrentItem(1);
                break;
            case R.id.question_rb:
                mFragmentContainer.setCurrentItem(2);
                break;
            case R.id.person_rb:
                mFragmentContainer.setCurrentItem(3);
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
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
    public void changeViewPager() {
        mFragmentPagerAdapter.toPersonFragment();
    }
}