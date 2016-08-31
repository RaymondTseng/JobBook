package com.example.jobbook.question.presenter;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.question.model.NewQuestionModel;
import com.example.jobbook.question.model.NewQuestionModelImpl;
import com.example.jobbook.question.view.NewQuestionView;
import com.example.jobbook.question.widget.QuestionFragment;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewQuestionPresenterImpl implements NewQuestionPresenter, NewQuestionModelImpl.OnNewQuestionListener {

    private NewQuestionModel mNewQuestionModel;
    private NewQuestionView mNewQuestionView;

    public NewQuestionPresenterImpl(NewQuestionView view) {
        mNewQuestionView = view;
        mNewQuestionModel = new NewQuestionModelImpl();
    }

    @Override
    public void newquestion(QuestionBean questionBean) {
        mNewQuestionModel.newquestion(questionBean, this);
    }

    @Override
    public void onSuccess() {
        mNewQuestionView.showSuccess();
        mNewQuestionView.close();

    }

    @Override
    public void onFailure() {
        mNewQuestionView.showError();
    }


}
