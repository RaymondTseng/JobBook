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
    QuestionDetailModelImpl.OnSendQuestionCommentListener{
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
}
