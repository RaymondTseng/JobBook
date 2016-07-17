package com.example.jobbook.question.model;

import com.example.jobbook.bean.QuestionBean;

/**
 * Created by Xu on 2016/7/16.
 */
public interface NewQuestionModel {

    void newquestion(QuestionBean questionBean, NewQuestionModelImpl.OnNewQuestionListener listener);
}
