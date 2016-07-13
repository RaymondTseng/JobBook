package com.example.jobbook.question.presenter;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.question.model.QuestionModelImpl;
import com.example.jobbook.question.view.QuestionView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class QuestionPresenterImpl implements QuestionPresenter,QuestionModelImpl.OnLoadQuestionsListListener {

    private QuestionView mQuestionView;

    public QuestionPresenterImpl(QuestionView view) {
        mQuestionView = view;
    }

    @Override
    public void loadQuestion() {

    }

    @Override
    public void onSuccess(List<QuestionBean> list) {

    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}
