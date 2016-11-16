package com.example.jobbook.square.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.square.model.SquareDetailModel;
import com.example.jobbook.square.model.SquareDetailModelImpl;
import com.example.jobbook.square.view.SquareDetailView;


import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class SquareDetailPresenterImpl implements SquareDetailPresenter,
        SquareDetailModelImpl.OnLoadQuestionCommentsListener, SquareDetailModelImpl.OnLoadQuestionListener,
    SquareDetailModelImpl.OnSendQuestionCommentListener, SquareDetailModelImpl.OnLikeListener, SquareDetailModelImpl.OnUnlikeListener{
    private SquareDetailView mView;
    private SquareDetailModel mModel;

    public SquareDetailPresenterImpl(SquareDetailView mView){
        this.mView = mView;
        mModel = new SquareDetailModelImpl();
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
