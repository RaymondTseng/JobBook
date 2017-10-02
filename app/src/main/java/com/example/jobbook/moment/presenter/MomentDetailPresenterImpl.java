package com.example.jobbook.moment.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.moment.view.MomentDetailView;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class MomentDetailPresenterImpl implements MomentDetailPresenter {
    private MomentDetailView mView;

    public MomentDetailPresenterImpl(MomentDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void loadMoment(MomentBean momentBean) {
        mView.showProgress();
        if (momentBean != null) {
            mView.addMoment(momentBean);
            mView.hideProgress();
        } else {
            mView.showLoadFailMsg(0);
            mView.hideProgress();
        }
    }

    @Override
    public void loadMomentById(int id, String account) {
        RetrofitService.loadMomentById(account, id)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.showLoadFailMsg(0);
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.hideProgress();
                        mView.addMoment(momentBean);
                    }
                });

    }

    @Override
    public void loadMomentComments(int id, int index) {
        RetrofitService.loadMomentComments(id, index)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<MomentCommentBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.showLoadFailMsg(0);
                    }

                    @Override
                    public void onNext(List<MomentCommentBean> mComments) {
                        mView.hideProgress();
                        mView.addComments(mComments);
                    }
                });
    }

    @Override
    public void sendComment(MomentCommentBean momentCommentBean) {
        RetrofitService.sendComment(momentCommentBean)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.showLoadFailMsg(0);
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.hideProgress();
                        mView.sendSuccess(momentBean);
                    }
                });
    }

    @Override
    public void commentLike(int s_id, String account) {
        RetrofitService.likeSquare(s_id, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.likeFailure(throwable.getMessage());
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.hideProgress();
                        mView.likeSuccess(momentBean);
                    }
                });
    }

    @Override
    public void commentUnlike(int s_id, String account) {
        RetrofitService.unlikeSquare(s_id, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<MomentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.unlikeFailure(throwable.getMessage());
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.hideProgress();
                        mView.unlikeSuccess(momentBean);
                    }
                });
    }

}
