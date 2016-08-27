package com.example.jobbook.main.presenter;

import android.util.Log;

import com.example.jobbook.R;
import com.example.jobbook.main.view.MainView;

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
                if (getLoginStatus() == 1){
                    Log.i("mainpresenter:", "person");

                    mMainView.switch2Person();
                }
                else {
                    Log.i("mainpresenter:", "login");
                    mMainView.switch2Login();
                }
                break;
        }
    }

    @Override
    public int getLoginStatus() {
        return 0;
    }

}
