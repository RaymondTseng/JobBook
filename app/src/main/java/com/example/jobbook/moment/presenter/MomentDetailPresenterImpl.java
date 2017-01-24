package com.example.jobbook.moment.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.moment.model.MomentDetailModel;
import com.example.jobbook.moment.model.MomentDetailModelImpl;
import com.example.jobbook.moment.view.MomentDetailView;


import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class MomentDetailPresenterImpl implements MomentDetailPresenter,
        MomentDetailModelImpl.OnLoadMomentCommentsListener, MomentDetailModelImpl.OnLoadMomentListener,
        MomentDetailModelImpl.OnSendMomentCommentListener, MomentDetailModelImpl.OnLikeListener, MomentDetailModelImpl.OnUnlikeListener {
    private MomentDetailView mView;
    private MomentDetailModel mModel;

    public MomentDetailPresenterImpl(MomentDetailView mView) {
        this.mView = mView;
        mModel = new MomentDetailModelImpl();
    }

    @Override
    public void onSuccess(List<MomentCommentBean> mComments) {
        mView.hideProgress();
        mView.addComments(mComments);
    }

    @Override
    public void onSuccess(MomentBean mMoment) {
        mView.hideProgress();
        mView.addMoment(mMoment);
    }

    @Override
    public void onSendSuccess(MomentBean momentBean) {
        mView.hideProgress();
        mView.sendSuccess(momentBean);
    }


    @Override
    public void onFailure(String msg, Exception e, int error) {
        mView.hideProgress();
        mView.showLoadFailMsg(error);
    }


    @Override
    public void loadMoment(MomentBean momentBean) {
        mView.showProgress();
        mModel.loadMoment(momentBean, this);
    }

    @Override
    public void loadMomentById(int id, String account) {
        mView.showProgress();
        mModel.loadMomentById(id, account, this);
    }

    @Override
    public void loadMomentComments(int id, int index) {
        mView.showProgress();
        mModel.loadMomentComments(id, index, this);
    }

    @Override
    public void sendComment(MomentCommentBean momentCommentBean) {
        mView.showProgress();
        mModel.sendComment(momentCommentBean, this);
    }

    @Override
    public void commentLike(int com_id, String account) {
        mView.showProgress();
        mModel.like(com_id, account, this);
    }

    @Override
    public void commentUnlike(int com_id, String account) {
        mView.showProgress();
        mModel.unlike(com_id, account, this);
    }

    @Override
    public void onLikeSuccess(MomentBean momentBean) {
        mView.hideProgress();
        mView.likeSuccess(momentBean);
    }

    @Override
    public void onLikeFailure(String msg, Exception e, int error) {
        mView.hideProgress();
        mView.likeFailure(msg);
    }

    @Override
    public void onUnlikeSuccess(MomentBean momentBean) {
        mView.hideProgress();
        mView.unlikeSuccess(momentBean);
    }

    @Override
    public void onUnlikeFailure(String msg, Exception e, int error) {
        mView.hideProgress();
        mView.unlikeFailure(msg);
    }
}
