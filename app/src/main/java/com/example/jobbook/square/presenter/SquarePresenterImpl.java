package com.example.jobbook.square.presenter;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.square.view.SquareView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquarePresenterImpl implements SquarePresenter {

    private SquareView mSquareView;

    public SquarePresenterImpl(SquareView view) {
        mSquareView = view;
    }

    @Override
    public void loadSquare(int pageIndex, String account) {
        RetrofitService.loadSquares(pageIndex)
                .subscribe(new BaseSubscriber<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mSquareView;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        mSquareView.addSquares(momentBeans);
                    }
                });
    }

    @Override
    public void like(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            mSquareView.hideProgress();
            mSquareView.NoLoginError();
            return;
        } else {
            account = MyApplication.getAccount();
        }
        RetrofitService.likeSquare(squareId, account)
                .subscribe(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mSquareView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareView.likeSuccess(momentBean, position);
                    }
                });
    }

    @Override
    public void unlike(int squareId, final int position) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            mSquareView.hideProgress();
            mSquareView.NoLoginError();
            return;
        } else {
            account = MyApplication.getAccount();
        }
        RetrofitService.unlikeSquare(squareId, account)
                .subscribe(new BaseSubscriber<MomentBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mSquareView;
                    }

                    @Override
                    public void onNext(MomentBean momentBean) {
                        mSquareView.unlikeSuccess(momentBean, position);
                    }
                });
    }

}
