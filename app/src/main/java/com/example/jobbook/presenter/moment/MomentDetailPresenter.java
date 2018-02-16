package com.example.jobbook.presenter.moment;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.moment.MomentDetailContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by Xu on 2018/2/16.
 */

public class MomentDetailPresenter extends RxPresenter<MomentDetailContract.View> implements MomentDetailContract.Presenter {

    public MomentDetailPresenter(MomentDetailContract.View view) {
        attach(view);
    }

    @Override
    public void loadMoment(MomentBean momentBean) {
        mView.showProgress();
        if (momentBean != null) {
            mView.addMoment(momentBean);
            mView.hideProgress();
        } else {
            mView.showLoadFailMsg("123");
            mView.hideProgress();
        }
    }

    @Override
    public void loadMomentById(int id, String account) {
        RetrofitService.loadMomentById(account, id)
                .subscribe(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.addMoment(momentBean);
                    }
                });

    }

    @Override
    public void loadMomentComments(int id, int index) {
        RetrofitService.loadMomentComments(id, index)
                .subscribe(new BaseSubscriber<List<MomentCommentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<MomentCommentBean> momentCommentBeans) {
                        mView.addComments(momentCommentBeans);
                    }
                });
    }

    @Override
    public void sendComment(MomentCommentBean momentCommentBean) {
        RetrofitService.sendComment(momentCommentBean)
                .subscribe(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.sendSuccess(momentBean);
                    }
                });
    }

    @Override
    public void commentLike(int s_id, String account) {
        RetrofitService.likeSquare(s_id, account)
                .subscribe(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.likeSuccess(momentBean);
                    }
                });
    }

    @Override
    public void commentUnlike(int s_id, String account) {
        RetrofitService.unlikeSquare(s_id, account)
                .subscribe(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.unlikeSuccess(momentBean);
                    }
                });
    }
}
