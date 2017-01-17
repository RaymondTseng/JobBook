package com.example.jobbook.square.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.square.model.SquareModel;
import com.example.jobbook.square.model.SquareModelImpl;
import com.example.jobbook.square.view.SquareView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquarePresenterImpl implements SquarePresenter,SquareModelImpl.OnLoadSquaresListListener, SquareModelImpl.OnLikeSquareListener, SquareModelImpl.OnUnlikeSquareListener {

    private SquareView mSquareView;
    private SquareModel mSquareModel;

    public SquarePresenterImpl(SquareView view) {
        mSquareView = view;
        mSquareModel = new SquareModelImpl();
    }

    @Override
    public void loadSquare(int pageIndex, String name) {
        mSquareView.showProgress();
        mSquareModel.loadSquares(pageIndex, name, this);
    }

    @Override
    public void like(int squareId) {
        mSquareModel.like(squareId, this);
    }

    @Override
    public void unlike(int squareId) {
        mSquareModel.unlike(squareId, this);
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

    @Override
    public void onLikeSuccess() {
        mSquareView.likeSuccess();
    }

    @Override
    public void onLikeSquareFailure(String msg, Exception e) {
        mSquareView.hideProgress();
        mSquareView.likeError();
    }

    @Override
    public void onLikeSquareNoLoginError() {
        mSquareView.hideProgress();
        mSquareView.NoLoginError();
    }

    @Override
    public void onUnlikeSuccess() {
        mSquareView.unlikeSuccess();
    }

    @Override
    public void onUnlikeSquareFailure(String msg, Exception e) {
        mSquareView.hideProgress();
        mSquareView.unlikeError();
    }

    @Override
    public void onUnlikeSquareNoLoginError() {
        mSquareView.hideProgress();
        mSquareView.NoLoginError();
    }
}
