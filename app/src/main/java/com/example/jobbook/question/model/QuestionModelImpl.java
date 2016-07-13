package com.example.jobbook.question.model;


import com.example.jobbook.bean.QuestionBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class QuestionModelImpl implements QuestionModel {

    @Override
    public void loadQuestions(String url, OnLoadQuestionsListListener listener) {

    }

    public interface OnLoadQuestionsListListener {
        void onSuccess(List<QuestionBean> list);
        void onFailure(String msg, Exception e);
    }
}
