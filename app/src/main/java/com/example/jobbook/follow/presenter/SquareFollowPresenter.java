package com.example.jobbook.follow.presenter;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareFollowPresenter {

    void loadSquareFollows(int pageIndex, String name);

    void like(String squareId, String account);

    void unlike(String squareId, String account);
}
