package com.example.jobbook.follow.view;


import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareFollowView extends IBaseView {

    void addSquareFollows(List<MomentBean> squareFollowList);

    void showLoadFailMsg();

    void like(int position);

    void unlike(int position);

    void NoLoginError();

    void likeSuccess(MomentBean momentBean, int position);

    void unlikeSuccess(MomentBean momentBean, int position);

    void likeError();

    void unlikeError();
}
