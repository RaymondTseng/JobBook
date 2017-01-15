package com.example.jobbook.follow.presenter;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareFollowPresenter {

    void loadSquareFollows(int pageIndex, String name);

    void like(int squareId, String account);

    void unlike(int squareId, String account);
}
