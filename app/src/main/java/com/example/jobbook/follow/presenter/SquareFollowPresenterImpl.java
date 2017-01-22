package com.example.jobbook.follow.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.follow.model.SquareFollowModel;
import com.example.jobbook.follow.model.SquareFollowModelImpl;
import com.example.jobbook.follow.view.SquareFollowView;
import com.example.jobbook.square.model.SquareModelImpl;
import com.example.jobbook.util.L;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquareFollowPresenterImpl implements SquareFollowPresenter,SquareFollowModelImpl.OnLoadSquareFollowListListener, SquareModelImpl.OnLikeSquareListener, SquareModelImpl.OnUnlikeSquareListener {

    private SquareFollowView mSquareFollowView;
    private SquareFollowModel mSquareFollowModel;

    public SquareFollowPresenterImpl(SquareFollowView view) {
        mSquareFollowView = view;
        mSquareFollowModel = new SquareFollowModelImpl();
    }

    @Override
    public void loadSquareFollows(int pageIndex) {
        mSquareFollowView.showProgress();
        L.i("square", "showprogress");
        mSquareFollowModel.loadSquareFollows(pageIndex, this);
    }

    @Override
    public void like(int squareId, int position) {
        mSquareFollowModel.like(squareId, position, this);
    }

    @Override
    public void unlike(int squareId, int position) {
        mSquareFollowModel.unlike(squareId, position, this);
    }

    @Override
    public void onSuccess(List<MomentBean> list) {
        mSquareFollowView.hideProgress();
        mSquareFollowView.addSquareFollows(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mSquareFollowView.hideProgress();
        mSquareFollowView.showLoadFailMsg();
    }

    @Override
    public void onLikeSuccess(MomentBean momentBean, int position) {
        mSquareFollowView.likeSuccess(momentBean, position);
    }

    @Override
    public void onLikeSquareFailure(String msg, Exception e) {
        mSquareFollowView.hideProgress();
        mSquareFollowView.likeError();
    }

    @Override
    public void onLikeSquareNoLoginError() {
        mSquareFollowView.hideProgress();
        mSquareFollowView.likeError();
    }

    @Override
    public void onUnlikeSuccess(MomentBean momentBean, int position) {
        mSquareFollowView.unlikeSuccess(momentBean, position);
    }

    @Override
    public void onUnlikeSquareFailure(String msg, Exception e) {
        mSquareFollowView.hideProgress();
        mSquareFollowView.unlikeError();
    }

    @Override
    public void onUnlikeSquareNoLoginError() {
        mSquareFollowView.hideProgress();
        mSquareFollowView.NoLoginError();
    }
}
