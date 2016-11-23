package com.example.jobbook.follow.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.follow.model.SquareFollowModel;
import com.example.jobbook.follow.model.SquareFollowModelImpl;
import com.example.jobbook.follow.view.SquareFollowView;
import com.example.jobbook.util.L;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquareFollowPresenterImpl implements SquareFollowPresenter,SquareFollowModelImpl.OnLoadSquareFollowListListener {

    private SquareFollowView mSquareFollowView;
    private SquareFollowModel mSquareFollowModel;

    public SquareFollowPresenterImpl(SquareFollowView view) {
        mSquareFollowView = view;
        mSquareFollowModel = new SquareFollowModelImpl();
    }

    @Override
    public void loadSquareFollows(int pageIndex, String name) {
        mSquareFollowView.showProgress();
        L.i("square", "showprogress");
        mSquareFollowModel.loadSquareFollows(pageIndex, name, this);
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
}
