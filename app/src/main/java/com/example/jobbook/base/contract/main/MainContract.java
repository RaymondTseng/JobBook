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
        void switch2Job();

        void switch2Article();

        void switch2Question();

        void switch2Container();

        void loginCheckSuccess(PersonBean personBean);

        void loginCheckTimeOut();
    }

    interface Presenter extends IBasePresenter<View> {
        void switchNavigation(int id);

//    PersonBean loadPersonBean(Context context);
//
//    void savePersonBean(Context context, PersonBean personBean);

        void loginCheck(Context context);
    }
}
