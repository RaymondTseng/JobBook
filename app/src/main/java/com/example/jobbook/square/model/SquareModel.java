package com.example.jobbook.square.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareModel {

    void loadQuestions(int pageIndex, SquareModelImpl.OnLoadQuestionsListListener listener);
}
