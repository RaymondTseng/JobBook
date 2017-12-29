package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/12/29.
 */

public interface UserDetailMomentContract {

    interface View extends IBaseView {
        void loadMoments(List<MomentBean> moments);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadMoments(String hisAccount, String myAccount);
    }
}
