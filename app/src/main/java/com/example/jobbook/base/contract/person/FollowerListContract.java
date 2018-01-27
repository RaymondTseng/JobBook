package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/17.
 */

public interface FollowerListContract {

    interface View extends IBaseView {
        void loadFanList(List<TypePersonBean> list);

        void followSuccess();
    }

    interface Presenter extends IBasePresenter<View> {
        void loadFollwers(String account);

        void follow(String myAccount, String hisAccount);
    }
}