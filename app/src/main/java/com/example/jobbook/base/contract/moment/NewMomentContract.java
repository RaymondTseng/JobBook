package com.example.jobbook.base.contract.moment;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

/**
 * Created by Xu on 2018/2/16.
 */

public interface NewMomentContract {

    interface View extends IBaseView {
        void publishNoLoginError();

        void showSuccess();

        void close();
    }

    interface Presenter extends IBasePresenter<View> {
        void newmoment(MomentBean momentBean);
    }

}
