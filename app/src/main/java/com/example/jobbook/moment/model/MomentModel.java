package com.example.jobbook.moment.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface MomentModel {

    void loadQuestions(int pageIndex, MomentModelImpl.OnLoadQuestionsListListener listener);
}
