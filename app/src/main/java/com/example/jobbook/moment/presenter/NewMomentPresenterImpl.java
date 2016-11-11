package com.example.jobbook.moment.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.moment.model.NewMomentModel;
import com.example.jobbook.moment.model.NewMomentModelImpl;
import com.example.jobbook.moment.view.NewMomentView;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewMomentPresenterImpl implements NewMomentPresenter, NewMomentModelImpl.OnNewMomentListener {

    private NewMomentModel mNewQuestionModel;
    private NewMomentView mNewMomentView;

    public NewMomentPresenterImpl(NewMomentView view) {
        mNewMomentView = view;
        mNewQuestionModel = new NewMomentModelImpl();
    }

    @Override
    public void newmoment(MomentBean momentBean) {
        mNewMomentView.showProgress();
        mNewQuestionModel.newmoment(momentBean, this);
    }

    @Override
    public void onSuccess() {
        mNewMomentView.hideProgress();
        mNewMomentView.showSuccess();
        mNewMomentView.close();

    }

    @Override
    public void onFailure() {
        mNewMomentView.hideProgress();
        mNewMomentView.showError();
    }


}
