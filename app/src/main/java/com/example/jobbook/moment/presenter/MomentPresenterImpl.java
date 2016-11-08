package com.example.jobbook.moment.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.util.L;

import com.example.jobbook.moment.model.MomentModel;
import com.example.jobbook.moment.model.MomentModelImpl;
import com.example.jobbook.moment.view.MomentView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class MomentPresenterImpl implements MomentPresenter,MomentModelImpl.OnLoadQuestionsListListener {

    private MomentView mMomentView;
    private MomentModel mMomentModel;

    public MomentPresenterImpl(MomentView view) {
        mMomentView = view;
        mMomentModel = new MomentModelImpl();
    }

    @Override
    public void loadQuestion(int pageIndex) {
        mMomentView.showProgress();
        L.i("question", "showprogress");
        mMomentModel.loadQuestions(pageIndex, this);
    }

    @Override
    public void onSuccess(List<MomentBean> list) {
        mMomentView.hideProgress();
        mMomentView.addQuestions(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mMomentView.hideProgress();
        mMomentView.showLoadFailMsg();
    }
}
