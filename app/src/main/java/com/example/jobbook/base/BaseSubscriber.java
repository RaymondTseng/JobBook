package com.example.jobbook.base;

import com.example.jobbook.app.NetConstants;
import com.example.jobbook.util.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Xu on 2017/11/25.
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onComplete() {
        getBaseView().hideProgress();
    }

    public abstract IBaseView getBaseView();

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            getBaseView().showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
        } else if (e instanceof ConnectException) {
            getBaseView().showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
        } else {
            L.i("message-onerror: " + e.getMessage());
            getBaseView().showLoadFailMsg(e.getMessage());
        }
        getBaseView().hideProgress();
    }
}
