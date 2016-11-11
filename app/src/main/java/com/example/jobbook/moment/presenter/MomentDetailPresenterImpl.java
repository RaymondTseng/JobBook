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
        MomentDetailModelImpl.OnLoadQuestionCommentsListener, MomentDetailModelImpl.OnLoadQuestionListener,
    MomentDetailModelImpl.OnSendQuestionCommentListener, MomentDetailModelImpl.OnLikeListener, MomentDetailModelImpl.OnUnlikeListener{
    private MomentDetailView mView;
    private MomentDetailModel mModel;

    public MomentDetailPresenterImpl(MomentDetailView mView){
        this.mView = mView;
        mModel = new MomentDetailModelImpl();
    }
    @Override
    public void onSuccess(List<MomentCommentBean> mComments) {
        mView.hideProgress();
        mView.addComments(mComments);
    }

    @Override
    public void onSuccess(MomentBean mQuestion) {
        mView.hideProgress();
        mView.addQuestion(mQuestion);
    }

    @Override
    public void onSuccess() {
        mView.hideProgress();
        mView.sendSuccess();
    }


    @Override
    public void onFailure(String msg, Exception e, int error) {
        mView.hideProgress();
        mView.showLoadFailMsg(error);
    }


    @Override
    public void loadQuestion(MomentBean momentBean) {
        mView.showProgress();
        mModel.loadQuestion(momentBean, this);
    }

    @Override
    public void loadQuestionComments(int id) {
        mView.showProgress();
        mModel.loadQuestionComments(id, this);
    }

    @Override
    public void sendComment(MomentCommentBean momentCommentBean) {
        mView.showProgress();
        mModel.sendComment(momentCommentBean, this);
    }

    @Override
    public void commentLike(int com_id, String account) {
        mView.showProgress();
        mModel.commentLike(com_id, account, this);
    }

    @Override
    public void commentUnlike(int com_id, String account) {
        mView.showProgress();
        mModel.commentUnlike(com_id, account, this);
    }

    @Override
    public void onLikeSuccess(int num_like, int num_unlike) {
        mView.hideProgress();
        mView.commentLikeSuccess(num_like, num_unlike);
    }

    @Override
    public void onLikeFailure(String msg, Exception e, int error) {
        mView.hideProgress();
        mView.commentLikeFailure(msg);
    }

    @Override
    public void onUnlikeSuccess(int num_like, int num_unlike) {
        mView.hideProgress();
        mView.commentUnlikeSuccess(num_like, num_unlike);
    }

    @Override
    public void onUnlikeFailure(String msg, Exception e, int error) {
        mView.hideProgress();
        mView.commentUnlikeFailure(msg);
    }
}
