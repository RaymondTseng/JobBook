package com.example.jobbook.base.contract.main;

import android.content.Context;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.PersonBean;

/**
 * Created by zhaoxuzhang on 2017/12/29.
 */

public interface MainContract {

    interface View extends IBaseView {
        void loginCheckSuccess(PersonBean personBean);

        void loginCheckTimeOut();
    }

    interface Presenter extends IBasePresenter<View> {
        void loginCheck(Context context);
    }
}
