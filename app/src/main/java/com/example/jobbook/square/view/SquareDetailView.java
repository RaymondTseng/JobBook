package com.example.jobbook.square.view;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;

import java.util.List;

/**
 * Created by root on 16-11-23.
 */

public interface SquareDetailView {
    void showProgress();

    void addSquareComments(List<MomentCommentBean> squareCommentList);

    void hideProgress();

    void showLoadFailMsg();
}
