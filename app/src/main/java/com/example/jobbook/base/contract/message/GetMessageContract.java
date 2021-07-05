package com.example.jobbook.base.contract.message;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MessageBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/17.
 */

public interface GetMessageContract {

    interface View extends IBaseView {
        void getMessage(List<MessageBean> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void getMessage(String account);
    }
}
