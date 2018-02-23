package com.example.jobbook.presenter.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jobbook.R;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.main.MainContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.Util;

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        attach(view);
    }
    
    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.job_rb:
                mView.switch2Job();
                break;
            case R.id.article_rb:
                mView.switch2Article();
                break;
            case R.id.question_rb:
                mView.switch2Question();
                break;
            case R.id.person_rb:
                mView.switch2Container();
                break;
        }
    }

    @Override
    public void loginCheck(Context context) {
        SharedPreferences share = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        final PersonBean personBean;
        if (share != null) {
            personBean = Util.loadPersonBean(share);
            if (personBean != null) {
                addSubscribe(RetrofitService.loginCheck(personBean.getAccount())
                        .subscribeWith(new BaseSubscriber<String>() {
                            @Override
                            public IBaseView getBaseView() {
                                return mView;
                            }

                            @Override
                            public void onNext(String s) {
                                mView.loginCheckSuccess(personBean);
                            }
                        }));
            } else {
                return;
            }
        }
    }
}
