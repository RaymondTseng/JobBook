package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/17.
 */

public interface MomentListContract {

    interface View extends IBaseView {
        void loadMomentList(List<MomentBean> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadMomentList(String hisAccount, String myAccount);
    }
}
