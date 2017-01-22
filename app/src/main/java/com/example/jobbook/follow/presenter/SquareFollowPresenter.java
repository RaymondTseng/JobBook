package com.example.jobbook.follow.presenter;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareFollowPresenter {

    void loadSquareFollows(int pageIndex);

    void like(int squareId, int position);

    void unlike(int squareId, int position);
}
