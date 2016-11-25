package com.example.jobbook.square.presenter;

import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.square.model.SquareDetailModel;
import com.example.jobbook.square.model.SquareDetailModelImpl;
import com.example.jobbook.square.view.SquareDetailView;

import java.util.List;

/**
 * Created by root on 16-11-23.
 */

public class SquareDetailPresenterImpl implements SquareDetailPresenter,
        SquareDetailModelImpl.OnLoadSquareDetailCommentListener {
    private SquareDetailView mView;
    private SquareDetailModel mModel;

    public SquareDetailPresenterImpl(SquareDetailView mView){
        this.mView = mView;
        mModel = new SquareDetailModelImpl();
    }
    @Override
    public void loadSquareComments(int id) {
        mView.showProgress();
        mModel.loadSquareComments(id, this);
    }

    @Override
    public void onSuccess(List<MomentCommentBean> list) {
        mView.addSquareComments(list);
        mView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.showLoadFailMsg();
    }
}
