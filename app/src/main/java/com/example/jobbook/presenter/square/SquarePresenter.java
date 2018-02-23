package com.example.jobbook.presenter.square;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.square.SquareContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/2/9.
 */

public class SquarePresenter extends RxPresenter<SquareContract.View> implements SquareContract.Presenter {

    public SquarePresenter(SquareContract.View view) {
        attach(view);
    }

    @Override
    public void loadSquare(int pageIndex, String account) {
        addSubscribe(RetrofitService.loadSquares(pageIndex)
                .subscribeWith(new BaseSubscriber<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        mView.addSquares(momentBeans);
                    }
                }));
    }

    @Override
    public void like(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            mView.hideProgress();
            mView.NoLoginError();
            return;
        } else {
            account = MyApplication.getAccount();
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
        if (MyApplication.getmLoginStatus() == 0) {
            mView.hideProgress();
            mView.NoLoginError();
            return;
        } else {
            account = MyApplication.getAccount();
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
