package com.example.jobbook.square.view;


import com.example.jobbook.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareView {

    void showProgress();

    void addSquares(List<MomentBean> squareList);

    void hideProgress();

    void showLoadFailMsg();

    void like(int position);

    void unlike(int position);

    void NoLoginError();

    void likeSuccess(MomentBean momentBean, int position);

    void unlikeSuccess(MomentBean momentBean, int position);

    void likeError();

    void unlikeError();

}
