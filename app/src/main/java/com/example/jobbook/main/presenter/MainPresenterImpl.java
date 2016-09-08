package com.example.jobbook.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.main.view.MainView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/7/5.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView view) {
        mMainView = view;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.job_rb:
                mMainView.switch2Job();
                break;
            case R.id.article_rb:
                mMainView.switch2Article();
                break;
            case R.id.question_rb:
                mMainView.switch2Question();
                break;
            case R.id.person_rb:
                mMainView.switch2Container();
                break;
        }
    }

    @Override
    public PersonBean loadPersonBean(Context context) {
        SharedPreferences share = context.getSharedPreferences("test", context.MODE_PRIVATE);
        PersonBean personBean = null;
        if(share != null){
            Util.loadPersonBean(share, personBean);
        }
        return personBean;
    }

    @Override
    public void savePersonBean(Context context, PersonBean personBean) {
        SharedPreferences share = context.getSharedPreferences("test", context.MODE_PRIVATE);
        Util.savePersonBean(share, personBean);
    }


}
