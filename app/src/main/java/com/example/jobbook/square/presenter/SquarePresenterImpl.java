package com.example.jobbook.square.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.square.view.SquareView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquarePresenterImpl implements SquarePresenter {

    private SquareView mSquareView;

    public SquarePresenterImpl(SquareView view) {
        mSquareView = view;
    }

    @Override
    public void loadSquare(int pageIndex, String account) {
        RetrofitService.loadSquares(account, pageIndex)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSquareView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<MomentBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mSquareView.hideProgress();
                        mSquareView.showLoadFailMsg();
                    }

                    @Override
                    public void onNext(List<MomentBean> list) {
                        mSquareView.hideProgress();
                        mSquareView.addSquares(list);
                    }
                });
    }

    @Override
    public void like(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            mSquareView.hideProgress();
            mSquareView.NoLoginError();
            return;
        } else {
            account = MyApplication.getAccount();
        }
        RetrofitService.likeSquare(squareId, account)
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mSquareView.hideProgress();
                        mSquareView.likeError();
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareView.likeSuccess(momentBean, position);
                    }
                });
    }

    @Override
    public void unlike(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            mSquareView.hideProgress();
            mSquareView.NoLoginError();
            return;
        } else {
            account = MyApplication.getAccount();
        }
        RetrofitService.unlikeSquare(squareId, account)
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mSquareView.hideProgress();
                        mSquareView.unlikeError();
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareView.unlikeSuccess(momentBean, position);
                    }
                });
    }

}
