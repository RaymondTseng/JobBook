package com.example.jobbook.square.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareModel {

    void loadSquares(int pageIndex, String name, SquareModelImpl.OnLoadSquaresListListener listener);

    void like(int squareId, int postion, SquareModelImpl.OnLikeSquareListener listener);

    void unlike(int squareId, int position, SquareModelImpl.OnUnlikeSquareListener listener);

}
