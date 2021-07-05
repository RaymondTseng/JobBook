package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;

/**
 * Created by zhaoxuzhang on 2017/12/29.
 */

public interface UserDetailBriefContract {

    interface View extends IBaseView {
        void followSuccess();

        void unfollowSuccess();

        void loadSuccess(TypePersonBean personBean);

        void onRefreshSuccess(TypePersonBean personBean);
    }

    interface Presenter extends IBasePresenter<View> {
        void follow(String myAccount, String hisAccount);

        void loadUserDetailByAccount(String account);

        void unfollow(String myAccount, String hisAccount);

        void refreshPersonBean();
    }

}
