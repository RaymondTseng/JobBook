package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.PersonBean;

/**
 * Created by Xu on 2017/12/17.
 */

public interface LoginContract {

    interface View extends IBaseView {
        void setNetworkError();

        void setUserError();

        void setAccountError();

        void setPasswordError();

        void switch2Person(PersonBean personBean);

        void switch2Register();

    }

    interface Presenter extends IBasePresenter<View> {
        void loginCheck(String account, String password);

        void destroy();
    }
}
