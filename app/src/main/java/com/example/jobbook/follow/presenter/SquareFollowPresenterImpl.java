package com.example.jobbook.follow.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.follow.view.SquareFollowView;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquareFollowPresenterImpl implements SquareFollowPresenter {

    private SquareFollowView mSquareFollowView;

    public SquareFollowPresenterImpl(SquareFollowView view) {
        mSquareFollowView = view;
    }

    @Override
    public void loadSquareFollows(int pageIndex) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.getFollowSquare(account, pageIndex)
                .subscribe(new BaseObserver<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mSquareFollowView;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        mSquareFollowView.addSquareFollows(momentBeans);
                    }
                });
    }

    @Override
    public void like(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        } else {
            mSquareFollowView.NoLoginError();
        }
        RetrofitService.likeSquare(squareId, account)
                .subscribe(new BaseObserver<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mSquareFollowView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareFollowView.likeSuccess(momentBean, position);
                    }
                });
    }

    @Override
    public void unlike(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        } else {
            mSquareFollowView.NoLoginError();
        }
        RetrofitService.unlikeSquare(squareId, account)
                .subscribe(new BaseObserver<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mSquareFollowView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareFollowView.unlikeSuccess(momentBean, position);
                    }
                });
    }

}
