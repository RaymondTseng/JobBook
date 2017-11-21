package com.example.jobbook.network;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.commons.NetConstants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Xu on 2017/11/18.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    public abstract IBaseView getBaseView();

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            getBaseView().showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
        } else if (e instanceof ConnectException) {
            getBaseView().showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
        } else {
            getBaseView().showLoadFailMsg(e.getMessage());
        }
        getBaseView().hideProgress();
    }

    @Override
    public void onSubscribe(Disposable d) {
        getBaseView().showProgress();
    }

    @Override
    public void onComplete() {
        getBaseView().hideProgress();
    }

}
