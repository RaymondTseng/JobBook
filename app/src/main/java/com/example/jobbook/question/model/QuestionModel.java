package com.example.jobbook.question.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface QuestionModel {

    void loadQuestions(String url, QuestionModelImpl.OnLoadQuestionsListListener listener);
}
