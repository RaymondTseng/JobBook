package com.example.jobbook.follow.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareFollowModel {

    void loadSquareFollows(int pageIndex, String name, SquareFollowModelImpl.OnLoadSquareFollowListListener listener);
}
