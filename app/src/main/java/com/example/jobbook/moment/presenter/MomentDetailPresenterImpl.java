package com.example.jobbook.moment.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;
import com.example.jobbook.moment.view.MomentDetailView;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

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
            mView.showLoadFailMsg("123");
            mView.hideProgress();
        }
    }

    @Override
    public void loadMomentById(int id, String account) {
        RetrofitService.loadMomentById(account, id)
                .subscribe(new BaseObserver<MomentBean>() {
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
                .subscribe(new BaseObserver<List<MomentCommentBean>>() {
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
                .subscribe(new BaseObserver<MomentBean>() {
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
                .subscribe(new BaseObserver<MomentBean>() {
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
                .subscribe(new BaseObserver<MomentBean>() {
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
