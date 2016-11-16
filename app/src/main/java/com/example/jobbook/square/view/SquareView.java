package com.example.jobbook.square.view;


import com.example.jobbook.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareView {

    void showProgress();

    void addQuestions(List<MomentBean> questionList);

    void hideProgress();

    void showLoadFailMsg();
}
