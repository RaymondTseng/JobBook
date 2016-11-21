package com.example.jobbook.square.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface SquareModel {

    void loadSquares(int pageIndex, SquareModelImpl.OnLoadSquaresListListener listener);
}
