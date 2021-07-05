package com.example.jobbook.presenter.square;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.square.SquareFollowContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/2/9.
 */

public class SquareFollowPresenter extends RxPresenter<SquareFollowContract.View> implements SquareFollowContract.Presenter {

    public SquareFollowPresenter(SquareFollowContract.View view) {
        attach(view);
    }

    @Override
    public void loadSquareFollows(int pageIndex) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        addSubscribe(RetrofitService.getFollowSquare(account, pageIndex)
                .subscribeWith(new BaseSubscriber<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        mView.addSquareFollows(momentBeans);
                    }
                }));
    }

    @Override
    public void like(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        } else {
            mView.NoLoginError();
            return;
        }
        addSubscribe(RetrofitService.likeSquare(squareId, account)
                .subscribeWith(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.likeSuccess(momentBean, position);
                    }
                }));
    }

    @Override
    public void unlike(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        } else {
            mView.NoLoginError();
            return;
        }
        addSubscribe(RetrofitService.unlikeSquare(squareId, account)
                .subscribeWith(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mView.unlikeSuccess(momentBean, position);
                    }
                }));
    }
}
