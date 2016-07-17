package com.example.jobbook.question.model;

import com.example.jobbook.bean.QuestionBean;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewQuestionModelImpl implements NewQuestionModel {
    @Override
    public void newquestion(QuestionBean questionBean, OnNewQuestionListener listener) {

    }

    public interface OnNewQuestionListener {
        void onSuccess();
        void onFailure();
    }
}
