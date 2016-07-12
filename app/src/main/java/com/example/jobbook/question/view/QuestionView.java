package com.example.jobbook.question.view;


import com.example.jobbook.bean.QuestionBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface QuestionView {

    void showProgress();

    void addQuestions(List<QuestionBean> questionList);

    void hideProgress();

    void showLoadFailMsg();
}
