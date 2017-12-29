package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/12/29.
 */

public interface UserDetailFollowContract {

    interface View extends IBaseView {
        void loadFollow(List<TypePersonBean> mFollow);

        void onError(String msg);

        void followSuccess();
    }

    interface Presenter extends IBasePresenter<View> {
        void loadFollow(String account);

        void follow(String myAccount, String hisAccount);
    }
}
