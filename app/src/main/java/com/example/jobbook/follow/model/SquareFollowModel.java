package com.example.jobbook.follow.model;

import com.example.jobbook.square.model.SquareModelImpl;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareFollowModel {

    void loadSquareFollows(int pageIndex, SquareFollowModelImpl.OnLoadSquareFollowListListener listener);

    void like(int squareId, int position, SquareModelImpl.OnLikeSquareListener listener);

    void unlike(int squareId, int position, SquareModelImpl.OnUnlikeSquareListener listener);

}
