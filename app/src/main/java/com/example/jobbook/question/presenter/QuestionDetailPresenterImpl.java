package com.example.jobbook.question.presenter;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;
import com.example.jobbook.question.model.QuestionDetailModel;
import com.example.jobbook.question.model.QuestionDetailModelImpl;
import com.example.jobbook.question.view.QuestionDetailView;


import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class QuestionDetailPresenterImpl implements QuestionDetailPresenter,
        QuestionDetailModelImpl.OnLoadQuestionCommentsListener, QuestionDetailModelImpl.OnLoadQuestionListener,
    QuestionDetailModelImpl.OnSendQuestionCommentListener, QuestionDetailModelImpl.OnLikeListener, QuestionDetailModelImpl.OnUnlikeListener{
    private QuestionDetailView mView;
    private QuestionDetailModel mModel;

    public QuestionDetailPresenterImpl(QuestionDetailView mView){
        this.mView = mView;
        mModel = new QuestionDetailModelImpl();
    }
    @Override
    public void onSuccess(List<QuestionCommentBean> mComments) {
        mView.hideProgress();
        mView.addComments(mComments);
    }

    @Override
    public void onSuccess(QuestionBean mQuestion) {
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
    public void loadQuestion(QuestionBean questionBean) {
        mView.showProgress();
        mModel.loadQuestion(questionBean, this);
    }

    @Override
    public void loadQuestionComments(int id) {
        mView.showProgress();
        mModel.loadQuestionComments(id, this);
    }

    @Override
    public void sendComment(QuestionCommentBean questionCommentBean) {
        mView.showProgress();
        mModel.sendComment(questionCommentBean, this);
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
