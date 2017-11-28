package com.example.jobbook.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhaoxuzhang on 2017/11/28.
 */

public class RxPresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void attach(T view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
        unSubscribe();
    }
}
