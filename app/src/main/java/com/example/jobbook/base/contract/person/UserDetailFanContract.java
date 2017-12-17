package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/17.
 */

public interface UserDetailFanContract {

    interface View extends IBaseView {
        void loadFans(List<TypePersonBean> mFans);

        void followSuccess();
    }

    interface Presenter extends IBasePresenter<View> {
        void loadFans(String account);

        void follow(String myAccount, String hisAccount);
    }
}
