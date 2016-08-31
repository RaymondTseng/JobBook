package com.example.jobbook.question.presenter;

import android.util.Log;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.question.model.QuestionModel;
import com.example.jobbook.question.model.QuestionModelImpl;
import com.example.jobbook.question.view.QuestionView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class QuestionPresenterImpl implements QuestionPresenter,QuestionModelImpl.OnLoadQuestionsListListener {

    private QuestionView mQuestionView;
    private QuestionModel mQuestionModel;

    public QuestionPresenterImpl(QuestionView view) {
        mQuestionView = view;
        mQuestionModel = new QuestionModelImpl();
    }

    @Override
    public void loadQuestion(int pageIndex) {
        mQuestionView.showProgress();
        Log.i("question", "showprogress");
        mQuestionModel.loadQuestions(pageIndex, this);
    }

    @Override
    public void onSuccess(List<QuestionBean> list) {
        mQuestionView.hideProgress();
        mQuestionView.addQuestions(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mQuestionView.hideProgress();
        mQuestionView.showLoadFailMsg();
    }
}
