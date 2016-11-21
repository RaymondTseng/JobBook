package com.example.jobbook.square.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.util.L;

import com.example.jobbook.square.model.SquareModel;
import com.example.jobbook.square.model.SquareModelImpl;
import com.example.jobbook.square.view.SquareView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquarePresenterImpl implements SquarePresenter,SquareModelImpl.OnLoadSquaresListListener {

    private SquareView mSquareView;
    private SquareModel mSquareModel;

    public SquarePresenterImpl(SquareView view) {
        mSquareView = view;
        mSquareModel = new SquareModelImpl();
    }

    @Override
    public void loadSquare(int pageIndex) {
        mSquareView.showProgress();
        L.i("square", "showprogress");
        mSquareModel.loadSquares(pageIndex, this);
    }

    @Override
    public void onSuccess(List<MomentBean> list) {
        mSquareView.hideProgress();
        mSquareView.addSquares(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mSquareView.hideProgress();
        mSquareView.showLoadFailMsg();
    }
}
