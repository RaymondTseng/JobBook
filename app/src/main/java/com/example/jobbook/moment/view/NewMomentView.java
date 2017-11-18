package com.example.jobbook.moment.view;

import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2016/7/16.
 */
public interface NewMomentView extends IBaseView{

    void showError();

    void showSuccess();

    void publishNoLoginError();

    void close();

}
