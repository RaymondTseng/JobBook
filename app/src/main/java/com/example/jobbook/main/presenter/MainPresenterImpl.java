package com.example.jobbook.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.main.view.MainView;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.util.Util;

import rx.Subscriber;

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

//    @Override
//    public PersonBean loadPersonBean(Context context) {
//        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
//        PersonBean personBean = null;
//        if (share != null) {
//            personBean = Util.loadPersonBean(share);
//        }
//        return personBean;
//    }
//
//    @Override
//    public void savePersonBean(Context context, PersonBean personBean) {
//        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
//        Util.savePersonBean(share, personBean);
//        MyApplication.setmPersonBean(context, personBean);
//    }

    @Override
    public void loginCheck(Context context) {
        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
        final PersonBean personBean;
        if (share != null) {
            personBean = Util.loadPersonBean(share);
            if (personBean != null) {
                RetrofitService.loginCheck(personBean.getAccount())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(String resultBean) {
                                mMainView.loginCheckSuccess(personBean);
                            }
                        });
            }
        }
    }

}
