package com.example.jobbook.follow.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.follow.view.SquareFollowView;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquareFollowPresenterImpl implements SquareFollowPresenter {

    private SquareFollowView mSquareFollowView;
//    private SquareFollowModel mSquareFollowModel;

    public SquareFollowPresenterImpl(SquareFollowView view) {
        mSquareFollowView = view;
//        mSquareFollowModel = new SquareFollowModelImpl();
    }

    @Override
    public void loadSquareFollows(int pageIndex) {
//        mSquareFollowView.showProgress();
//        mSquareFollowModel.loadSquareFollows(pageIndex, this);
//        L.i("square", "showprogress");
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.getFollowSquare(account, pageIndex)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSquareFollowView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<MomentBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mSquareFollowView.hideProgress();
                        mSquareFollowView.showLoadFailMsg();
                    }

                    @Override
                    public void onNext(List<MomentBean> list) {
                        mSquareFollowView.hideProgress();
                        mSquareFollowView.addSquareFollows(list);
                    }
                });
    }

    @Override
    public void like(int squareId, final int position) {
//        mSquareFollowModel.like(squareId, position, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        } else {
            mSquareFollowView.NoLoginError();
        }
        RetrofitService.likeSquare(squareId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSquareFollowView.showProgress();
                    }
                })
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mSquareFollowView.hideProgress();
                        mSquareFollowView.likeError();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareFollowView.hideProgress();
                        mSquareFollowView.likeSuccess(momentBean, position);
                    }
                });
    }

    @Override
    public void unlike(int squareId, final int position) {
//        mSquareFollowModel.unlike(squareId, position, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        } else {
            mSquareFollowView.NoLoginError();
        }
        RetrofitService.unlikeSquare(squareId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mSquareFollowView.showProgress();
                    }
                })
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mSquareFollowView.hideProgress();
                        mSquareFollowView.unlikeError();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareFollowView.hideProgress();
                        mSquareFollowView.unlikeSuccess(momentBean, position);
                    }
                });
    }

}
