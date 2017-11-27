package com.example.jobbook.base;

/**
 * Created by zhaoxuzhang on 2017/11/27.
 */

public interface IBasePresenter<T extends IBaseView> {

    void attach(T view);

    void detach();
}
